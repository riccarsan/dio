package com.filmcatalog.function;

import com.filmcatalog.model.Movie;
import com.filmcatalog.repository.CosmosMovieRepository;
import com.google.gson.Gson;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import java.util.Optional;

public class RegisterMovieFunction {

    @FunctionName("RegisterMovie")
    public HttpResponseMessage run(

            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.POST},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "movies")
            HttpRequestMessage<Optional<String>> request,

            final ExecutionContext context) {

        Gson gson = new Gson();

        Optional<String> body = request.getBody();

        if (!body.isPresent()) {

            return request.createResponseBuilder(HttpStatus.BAD_REQUEST)
                    .body("Body is required")
                    .build();
        }

        Movie movie = gson.fromJson(body.get(), Movie.class);

        String endpoint = System.getenv("COSMOS_ENDPOINT");
        String key = System.getenv("COSMOS_KEY");
        String database = System.getenv("COSMOS_DATABASE");
        String container = System.getenv("COSMOS_CONTAINER");

        CosmosMovieRepository repo =
                new CosmosMovieRepository(endpoint, key, database, container);

        repo.save(movie);

        return request.createResponseBuilder(HttpStatus.OK)
                .body("Movie saved")
                .build();
    }
}
