package com.filmcatalog.function;

import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import java.util.Optional;

import com.filmcatalog.model.Movie;
import com.filmcatalog.repository.CosmosMovieRepository;
import com.google.gson.Gson;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;

import java.util.List;
import java.util.Optional;

public class ListMoviesFunction {

    @FunctionName("ListMovies")
    public HttpResponseMessage run(

            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "movies")
            HttpRequestMessage<Optional<String>> request,

            final ExecutionContext context) {

        String endpoint = System.getenv("COSMOS_ENDPOINT");
        String key = System.getenv("COSMOS_KEY");
        String database = System.getenv("COSMOS_DATABASE");
        String container = System.getenv("COSMOS_CONTAINER");

        CosmosMovieRepository repo =
                new CosmosMovieRepository(endpoint, key, database, container);

        List<Movie> movies = repo.findAll();

        Gson gson = new Gson();

        return request.createResponseBuilder(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(gson.toJson(movies))
                .build();
    }
}