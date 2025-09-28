import logging
from azure.storage.blob import BlobServiceClient
from utility.Configuration import Configuration

def upload_file(file, file_name):
    try:
        blob_service_client = BlobServiceClient.from_connection_string(Configuration.STORAGE_CONNECTION)
        blob_client = blob_service_client.get_blob_client(container=Configuration.CONTAINER, blob=file_name)
        blob_client.upload_blob(file, overwrite=True)
        return blob_client.url
    except Exception as ex:
        logging.error(f"Erro ao enviar o arquivo para o Azure Blob Storage: {ex}")
        return None