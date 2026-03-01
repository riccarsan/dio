from azure.core.credentials import AzureKeyCredential
from azure.ai.documentintelligence import DocumentIntelligenceClient
from azure.ai.documentintelligence.models import AnalyzeDocumentRequest
from utility.Configuration import Configuration
from datetime import datetime
import logging


def analyze_passport(passport_url: str):
    try:
        credential = AzureKeyCredential(Configuration.KEY)
        document_client = DocumentIntelligenceClient(
            Configuration.ENDPOINT,
            credential
        )

        # Envia para análise usando o modelo pré-treinado de documentos de identidade
        poller = document_client.begin_analyze_document(
            "prebuilt-idDocument",
            AnalyzeDocumentRequest(url_source=passport_url)
        )
        result = poller.result()

        # Pega o primeiro documento encontrado
        if not result.documents:
            logging.warning("Nenhum documento encontrado na análise.")
            return None

        document = result.documents[0]
        fields = document.fields

        # === Tratamento do nome ===
        passport_name = None
        if fields.get("PassportHolderName"):
            passport_name = fields.get("PassportHolderName").content
        else:
            first_name = fields.get("FirstName").content if fields.get("FirstName") else None
            last_name = fields.get("LastName").content if fields.get("LastName") else None
            if first_name or last_name:
                passport_name = f"{first_name or ''} {last_name or ''}".strip()

        # === Outros campos ===
        document_number = fields.get("DocumentNumber").content if fields.get("DocumentNumber") else None
        nationality = fields.get("Nationality").content if fields.get("Nationality") else None
        issue_date = fields.get("DateOfIssue").content if fields.get("DateOfIssue") else None

        # === Tratamento da data de expiração ===
        expiry_date = None
        expired = None
        field_expiry = fields.get("DateOfExpiration")

        if field_expiry:
            # 1) Se o Azure normalizou, usa o value_date (já é datetime.date)
            if hasattr(field_expiry, "value_date") and field_expiry.value_date:
                expiry_date = field_expiry.value_date.strftime("%Y-%m-%d")
                expired = field_expiry.value_date < datetime.today().date()
            else:
                # 2) Se não, tenta interpretar o content manualmente
                expiry_raw = field_expiry.content
                if expiry_raw:
                    for fmt in ("%Y-%m-%d", "%d %b %Y", "%d %B %Y"):
                        try:
                            dt = datetime.strptime(expiry_raw, fmt).date()
                            expiry_date = dt.strftime("%Y-%m-%d")
                            expired = dt < datetime.today().date()
                            break
                        except ValueError:
                            continue
                    if not expiry_date:
                        logging.error(f"Formato de data inesperado: {expiry_raw}")

        # === Regras de validação do passport ===
        is_valid = expiry_date is not None and not expired

        return {
            "passport_name": passport_name,
            "document_number": document_number,
            "country_name": nationality,
            "issue_date": issue_date,
            "expiry_date": expiry_date,
            "expired": expired,
            "is_valid": is_valid
        }

    except Exception as ex:
        logging.error(f"Erro ao analisar passaporte: {ex}")
        return None