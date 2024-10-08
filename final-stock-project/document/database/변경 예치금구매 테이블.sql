ALTER TABLE `stockandfund`.`deposit_order` 
ADD COLUMN `do_tel` VARCHAR(255) NOT NULL AFTER `do_status`,
ADD COLUMN `do_email` VARCHAR(255) NULL AFTER `do_tel`;