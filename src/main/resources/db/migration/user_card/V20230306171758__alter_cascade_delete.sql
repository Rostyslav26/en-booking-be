ALTER TABLE user_card
DROP CONSTRAINT user_card_card_id_fkey;

ALTER TABLE user_card
ADD CONSTRAINT user_card_card_id_fkey
FOREIGN KEY (card_id)
REFERENCES card(id)
ON DELETE CASCADE;