--
-- Query for materialized view `popularities`
--

DROP VIEW IF EXISTS `popularities`;

CREATE VIEW `popularities` AS
  SELECT
    `users`.`id` AS `driver_id`,
    AVG(`transactions`.`rating`) AS `rating`,
    COUNT(`transactions`.`rating`) AS `vote_count`
  FROM
    (`users`
    LEFT JOIN `transactions` ON ((`users`.`id` = `transactions`.`driver_id`)))
  GROUP BY `transactions`.`driver_id`;
