package com.example.ticket.event.elastic;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventElasticRepositoryImpl {

    private final ElasticsearchOperations elasticsearchOperations;


    public List<EventElastic> findByArtistOrLocation(String artist, String location) {
        if ((artist == null || artist.isBlank()) && (location == null || location.isBlank())) {
            return new ArrayList<>();
        }

        BoolQuery.Builder boolQueryBuilder = new BoolQuery.Builder();

        if (artist != null && !artist.isBlank()) {
            boolQueryBuilder.should(s -> s.match(m -> m.field("artist").query(artist).fuzziness("AUTO")));
        }

        if (location != null && !location.isBlank()) {
            boolQueryBuilder.should(s -> s.match(m -> m.field("location").query(location).fuzziness("AUTO")));
        }

        Query boolQuery = new Query.Builder().bool(boolQueryBuilder.build()).build();

        NativeQuery searchQuery = new NativeQueryBuilder()
                .withQuery(boolQuery)
                .build();

        SearchHits<EventElastic> searchHits = elasticsearchOperations.search(searchQuery, EventElastic.class);

        return searchHits.stream()
                .map(SearchHit::getContent)
                .toList();
    }
}
