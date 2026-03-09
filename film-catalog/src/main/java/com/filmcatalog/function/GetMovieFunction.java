package com.filmcatalog.function;

import com.azure.cosmos.util.CosmosPagedIterable;
import com.filmcatalog.model.Movie;
import com.azure.cosmos.*;
import com.azure.cosmos.models.*;
import com.microsoft.azure.functions.*;
import com.microsoft.azure.functions.annotation.*;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetMovieFunction {

    @FunctionName("GetMovie")
    public HttpResponseMessage run(

            @HttpTrigger(
                    name = "req",
                    methods = {HttpMethod.GET},
                    authLevel = AuthorizationLevel.ANONYMOUS,
                    route = "movies/{id}")
            HttpRequestMessage<Optional<String>> request,

            @BindingName("id") String id,

            final ExecutionContext context) {

        String endpoint = System.getenv("COSMOS_ENDPOINT");
        String key = System.getenv("COSMOS_KEY");
        String database = System.getenv("COSMOS_DATABASE");
        String container = System.getenv("COSMOS_CONTAINER");

        CosmosClient client = new CosmosClientBuilder()
                .endpoint(endpoint)
                .key(key)
                .buildClient();

        CosmosContainer cosmosContainer =
                client.getDatabase(database).getContainer(container);

        String query = "SELECT * FROM c WHERE c.id = @id";

        List<SqlParameter> params = new ArrayList<>();
        params.add(new SqlParameter("@id", id));

        SqlQuerySpec querySpec = new SqlQuerySpec(query, params);

        CosmosPagedIterable<Movie> results =
                cosmosContainer.queryItems(querySpec, new CosmosQueryRequestOptions(), Movie.class);

        Movie movie = results.iterator().hasNext() ? results.iterator().next() : null;

        if (movie == null) {

            return request.createResponseBuilder(HttpStatus.NOT_FOUND)
                    .body("Movie not found")
                    .build();
        }

        Gson gson = new Gson();

        return request.createResponseBuilder(HttpStatus.OK)
                .header("Content-Type", "application/json")
                .body(gson.toJson(movie))
                .build();
    }
}
