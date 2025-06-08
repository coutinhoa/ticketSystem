CREATE TABLE T_TICKET(
    id bigserial primary key,
    user_id bigint REFERENCES T_USER(id) DEFAULT NULL,
    event_id bigint NOT NULL REFERENCES T_EVENT(id),
    status character VARYING NOT NULL,
    type VARCHAR(255) NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    first_name character VARYING,
    last_name character VARYING,
    email character VARYING,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
CREATE INDEX idx_ticket_status ON T_TICKET(status);