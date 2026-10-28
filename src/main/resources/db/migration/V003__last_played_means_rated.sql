-- last_played is the time of the player's last rated or scored result (DataAPI sets it on
-- every elo/score write); a player who has none yet has no value, so the ranking leaves them out.
ALTER TABLE players ALTER COLUMN last_played DROP DEFAULT;
ALTER TABLE players ALTER COLUMN last_played DROP NOT NULL;
-- Rows created before this rule kept their creation time. A rating needs two participants,
-- so nobody outside every roster of two or more players has had one.
WITH rated AS (
    SELECT DISTINCT member::integer AS player_id
    FROM match_history h
    CROSS JOIN LATERAL (
        SELECT array_agg(DISTINCT id) AS roster FROM regexp_split_to_table(h.teams, '[;,]') AS id WHERE id ~ '^[0-9]+$'
    ) r
    CROSS JOIN LATERAL unnest(r.roster) AS member
    WHERE cardinality(r.roster) >= 2
)
UPDATE players SET last_played = NULL WHERE player_id NOT IN (SELECT player_id FROM rated);
