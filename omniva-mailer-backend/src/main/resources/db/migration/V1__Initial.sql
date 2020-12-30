CREATE TABLE public.messages (
    id integer PRIMARY KEY NOT NULL,
    message_receiver VARCHAR(50) NOT NULL,
    message_subject VARCHAR(128) NOT NULL,
    message VARCHAR(500) NOT NULL,
    delivered BOOLEAN
);

ALTER TABLE public.messages OWNER TO omniva;

CREATE SEQUENCE public.messages_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER TABLE public.messages_id_seq OWNER TO omniva;
ALTER SEQUENCE public.messages_id_seq OWNED BY public.messages.id;