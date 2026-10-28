-- Live server projection published by LiveStatusPublisher: who is online and what each instance is doing.
CREATE TABLE live_status (
    server text PRIMARY KEY CHECK (server = 'minicat'),
    server_session uuid NOT NULL,
    published_at timestamptz NOT NULL,
    document jsonb NOT NULL
);
-- The one ranking definition: in-game /top, the MOTD and the Telegram bot read it.
CREATE VIEW ranking AS
SELECT row_number() OVER (ORDER BY elo DESC, player_id) AS position, player_id, username, elo
FROM players
WHERE last_played > statement_timestamp() - INTERVAL '15 days';
