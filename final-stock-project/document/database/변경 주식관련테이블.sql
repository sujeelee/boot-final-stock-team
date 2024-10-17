ALTER TABLE `stockandfund`.`stock` 
ADD COLUMN `st_issue` VARCHAR(255) NULL AFTER `st_status`,
ADD COLUMN `st_type` VARCHAR(255) NULL AFTER `st_issue`;

ALTER TABLE `stockandfund`.`stock_info` drop column `si_mrktCtq` ;
