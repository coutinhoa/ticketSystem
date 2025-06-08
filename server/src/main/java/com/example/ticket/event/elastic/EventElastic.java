package com.example.ticket.event.elastic;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Document(indexName = "event_index")
public class EventElastic {

    @Id
    @Field(type = FieldType.Text)
    private String id;

    @Field(type = FieldType.Text)
    private String location;

    @Field(type = FieldType.Date)
    private LocalDate date;

    @Field(type = FieldType.Text)
    private String artist;

    @Field(type = FieldType.Integer)
    private int availableTickets;

    @Field(type = FieldType.Date)
    private LocalDate createdAt;
}
