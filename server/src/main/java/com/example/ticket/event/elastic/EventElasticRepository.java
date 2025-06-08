package com.example.ticket.event.elastic;


import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface EventElasticRepository extends ElasticsearchRepository<EventElastic, String> {
    EventElastic save(EventElastic eventElastic);

    @Query("""
        {
          "bool": {
            "should": [
              {
                "bool": {
                  "must_not": {
                    "term": { "artist": null }
                  },
                  "must": {
                    "match": {
                      "artist": {
                        "query": "?0",
                        "fuzziness": "AUTO"
                      }
                    }
                  }
                }
              },
              {
                "bool": {
                  "must_not": {
                    "term": { "location": null }
                  },
                  "must": {
                    "match": {
                      "location": {
                        "query": "?1",
                        "fuzziness": "AUTO"
                      }
                    }
                  }
                }
              }
            ]
          }
        }
    """)
    List<EventElastic> findByArtistOrLocation(String artist, String location);//both need to be present
}
