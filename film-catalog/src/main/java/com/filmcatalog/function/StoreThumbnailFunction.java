package com.filmcatalog.function;

import com.filmcatalog.storage.BlobStorageService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

public class StoreThumbnailFunction {

    @FunctionName("StoreThumbnail")
    public HttpResponseMessage run(

            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "thumbnails/{fileName}")
            HttpRequestMessage<Optional<byte[]>> request,

            @BindingName("fileName") String fileName,

            final ExecutionContext context) {

        byte[] body = request.getBody().orElse(null);

        if (body == null) {
            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Thumbnail body missing")
                    .build();
        }

        String connection = System.getenv("STORAGE_CONNECTION");

        BlobStorageService storage = new BlobStorageService(connection);

        String url = storage.upload("thumbnails", fileName, body);

        return request.createResponseBuilder(HttpStatus.OK)
                .body(url)
                .build();
    }
}
