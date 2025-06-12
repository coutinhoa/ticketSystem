CREATE TABLE T_EVENT(
    id bigserial primary key,
    location character VARYING NOT NULL,
    date TIMESTAMP NOT NULL,
    artist character VARYING NOT NULL,
    available_tickets integer NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted boolean NOT NULL DEFAULT false
);

INSERT INTO T_EVENT (location, date, artist, available_tickets) values ('New York','2025-07-20 19:00:00' , 'Taylor Swift', 30);
INSERT INTO T_EVENT (location, date, artist, available_tickets) values ('Miami', '2025-12-01 16:00:00' , 'NFL match', 3);



