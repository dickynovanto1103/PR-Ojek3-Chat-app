--
-- Table structure modification for table `users`
--

ALTER TABLE `test_wbd2_web`.`users`
  DROP COLUMN `password`,
  DROP COLUMN `username`,
  DROP INDEX `username` ;
