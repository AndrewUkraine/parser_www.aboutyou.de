DROP TABLE IF EXISTS offer;
DROP SEQUENCE IF EXISTS global_seq;

CREATE SEQUENCE global_seq START 100000;

CREATE TABLE offer
(
  id            INTEGER PRIMARY KEY DEFAULT nextval('global_seq'),
  name          VARCHAR(200)        DEFAULT 'no result',
  brand         VARCHAR(100)        DEFAULT 'no result',
  color         VARCHAR(100)        DEFAULT 'no result',
  price         VARCHAR(100)        DEFAULT NULL,
  initialPrice  VARCHAR(100)        DEFAULT NULL,
  description   VARCHAR(500)        DEFAULT 'no result',
  articleId     VARCHAR(100)        DEFAULT NULL,
  shippingCosts VARCHAR(100)        DEFAULT NULL
);

