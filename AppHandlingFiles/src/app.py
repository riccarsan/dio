import logging
from flask import Flask, render_template, request
from werkzeug.utils import secure_filename
from service.upload_service import upload_file
from service.passport_service import analyze_passport

app = Flask(__name__)
logging.basicConfig(level=logging.INFO)


@app.route('/', methods=['GET', 'POST'])
def home():
    if request.method == 'POST':
        image = request.files.get('imagem')
        if not image or image.filename == '':
            return render_template('index.html', error="Nenhum arquivo selecionado.")

        file_name = secure_filename(image.filename)

        # 1) Upload para o Azure Blob
        blob_url = upload_file(image, file_name)
        if not blob_url:
            logging.error(f"Erro ao enviar {file_name} para o Azure Blob Storage.")
            return render_template('index.html', error=f"Erro ao enviar {file_name} para o Azure Blob Storage")

        logging.info(f"Arquivo {file_name} enviado com sucesso para Azure Blob: {blob_url}")

        # 2) Analisar documento com Azure Document Intelligence
        passport_info = analyze_passport(blob_url)

        if not passport_info:
            logging.warning(f"Nenhuma informação de passaporte encontrada em {file_name}.")
            return render_template(
                'index.html',
                image_url=blob_url,
                file_name=file_name,
                error="Nenhuma informação válida de passaporte encontrada."
            )

        # 3) Renderizar resultado com os dados do passaporte
        return render_template(
            'index.html',
            image_url=blob_url,
            file_name=file_name,
            passport_info=passport_info
        )

    # Caso GET, apenas carrega a página inicial
    return render_template('index.html')


if __name__ == '__main__':
    app.run(debug=True)