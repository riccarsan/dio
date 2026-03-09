package com.filmcatalog.repository;

import com.azure.cosmos.*;
import com.azure.cosmos.models.*;
import com.azure.cosmos.util.CosmosPagedIterable;
import com.filmcatalog.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class CosmosMovieRepository {

    private CosmosContainer container;

    public CosmosMovieRepository(String endpoint, String key, String databaseName, String containerName) {

        CosmosClient client = new CosmosClientBuilder()
                .endpoint(endpoint)
                .key(key)
                .consistencyLevel(ConsistencyLevel.EVENTUAL)
                .buildClient();

        CosmosDatabase database = client.getDatabase(databaseName);

        this.container = database.getContainer(containerName);
    }

    public void save(Movie movie) {
        container.createItem(movie);
    }

    public List<Movie> findAll() {

        String query = "SELECT * FROM c";

        CosmosPagedIterable<Movie> items =
                container.queryItems(query, new CosmosQueryRequestOptions(), Movie.class);

        List<Movie> movies = new ArrayList<>();

        for (Movie movie : items) {
            movies.add(movie);
        }

        return movies;
    }
}