create SCHEMA IF NOT EXISTS tsms_schema;
create TABLE IF NOT EXISTS tsms_schema.user_table(
    user_id serial primary key,
    postal_code integer,
    phone_number VARCHAR(13),
    last_name VARCHAR(100),
    interest VARCHAR(1000),
    first_name VARCHAR(100) NOT NULL,
    email_id VARCHAR(100),
    address VARCHAR(200),
    age integer
);
create TABLE IF NOT EXISTS tsms_schema.series(
    series_id serial primary key,
    series_name VARCHAR(100),
    genre VARCHAR(100) NOT NULL,
    rating numeric(2,1),
    casting VARCHAR(1000),
    age_preference integer
);
create TABLE IF NOT EXISTS tsms_schema.season(
season_id VARCHAR(100) primary key,
series_id integer,
season_name VARCHAR(100) NOT NULL,
genre VARCHAR(100),rating numeric(2,1),
casting VARCHAR(1000),
age_preference integer,
CONSTRAINT series_id_forigen_key FOREIGN KEY (series_id) REFERENCES tsms_schema.series (series_id)
);
create TABLE IF NOT EXISTS tsms_schema.episode(
    episode_id VARCHAR(100) primary key,
    season_id VARCHAR(100),
    episode_name VARCHAR(100) NOT NULL,
    duration integer,
    genre VARCHAR(100),
    rating numeric(2,1),
    casting VARCHAR(1000),
    age_preference integer,
    CONSTRAINT season_id_forigen_key FOREIGN KEY (season_id)
        REFERENCES tsms_schema.season (season_id)
);
create TABLE IF NOT EXISTS tsms_schema.watch_status(
    user_id integer,
    series_id integer,
    season_id VARCHAR(100),
    episode_id VARCHAR(100),
    watched_till integer,
    status VARCHAR(10),
    CONSTRAINT user_id_fk FOREIGN KEY (user_id)
        REFERENCES tsms_schema.user_table (user_id),
    CONSTRAINT series_id_fk FOREIGN KEY (series_id)
        REFERENCES tsms_schema.series (series_id),
    CONSTRAINT season_id_fk FOREIGN KEY (season_id)
        REFERENCES tsms_schema.season (season_id),
    CONSTRAINT episode_id_fk FOREIGN KEY (episode_id)
        REFERENCES tsms_schema.episode (episode_id)
);
create TABLE IF NOT EXISTS tsms_schema.series_rating(
    user_id integer,
    series_id integer,
    rating numeric(2, 1),
    CONSTRAINT series_id_rating_fk FOREIGN KEY (series_id)
        REFERENCES tsms_schema.series (series_id),
    CONSTRAINT user_id_series_rating_fk FOREIGN KEY (user_id)
        REFERENCES tsms_schema.user_table (user_id)
);
create TABLE IF NOT EXISTS tsms_schema.season_rating(
    user_id integer,
    season_id VARCHAR(100),
    rating numeric(2, 1),
    CONSTRAINT season_id_rating_fk FOREIGN KEY (season_id)
        REFERENCES tsms_schema.season (season_id),
    CONSTRAINT user_id_season_rating_fk FOREIGN KEY (user_id)
        REFERENCES tsms_schema.user_table (user_id)
);
create TABLE IF NOT EXISTS tsms_schema.episode_rating(
    user_id integer,
    episode_id VARCHAR(100),
    rating numeric(2, 1),
    CONSTRAINT episode_id_rating_fk FOREIGN KEY (episode_id)
        REFERENCES tsms_schema.episode (episode_id),
    CONSTRAINT user_id_episode_rating_fk FOREIGN KEY (user_id)
        REFERENCES tsms_schema.user_table (user_id)
);