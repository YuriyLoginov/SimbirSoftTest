CREATE TABLE public.word
(
    word varchar,
    count integer
)

    TABLESPACE pg_default;

ALTER TABLE public.word
    OWNER to postgres;