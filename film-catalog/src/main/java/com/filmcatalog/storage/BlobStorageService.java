package com.filmcatalog.storage;

import com.azure.storage.blob.*;
import com.azure.storage.blob.models.*;

import java.io.ByteArrayInputStream;

public class BlobStorageService {

    private final BlobServiceClient blobServiceClient;

    public BlobStorageService(String connectionString) {

        this.blobServiceClient =
                new BlobServiceClientBuilder()
                        .connectionString(connectionString)
                        .buildClient();
    }

    public String upload(String containerName, String fileName, byte[] data) {

        BlobContainerClient container =
                blobServiceClient.getBlobContainerClient(containerName);

        if (!container.exists()) {
            container.create();
        }

        BlobClient blob = container.getBlobClient(fileName);

        blob.upload(new ByteArrayInputStream(data), data.length, true);

        return blob.getBlobUrl();
    }
}