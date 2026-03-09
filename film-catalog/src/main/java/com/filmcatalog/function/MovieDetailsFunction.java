package com.filmcatalog.function;

import com.filmcatalog.storage.BlobStorageService;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

public class MovieDetailsFunction {

    @FunctionName("MovieDetails")
    public HttpResponseMessage run(

            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "moviedetails/{fileName}")
            HttpRequestMessage<Optional<String>> request,

            @BindingName("fileName") String fileName,

            final ExecutionContext context) {

        Optional<String> body = request.getBody();

        if (!body.isPresent()) {

            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Details missing")
                    .build();
        }

        String connection = System.getenv("STORAGE_CONNECTION");

        BlobStorageService storage = new BlobStorageService(connection);

        String url = storage.upload(
                "moviedetails",
                fileName,
                body.get().getBytes()
        );

        return request.createResponseBuilder(HttpStatus.OK)
                .body(url)
                .build();
    }
}
