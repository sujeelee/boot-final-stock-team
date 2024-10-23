
DROP TABLE `community_action`
CREATE TABLE `stockandfund`.`community_action` (
  `cg_no` INT NOT NULL AUTO_INCREMENT,
  `cg_num` INT NULL COMMENT '게시판 이거나 댓글 기본키',
  `cg_type` varchar(10) NULL COMMENT '게시판이면 없음 댓글이면 c',
  `st_code` VARCHAR(255) NULL COMMENT '주식코드',
  `mb_id` VARCHAR(255) NULL COMMENT '회원아이디',
  `cg_datetime` DATETIME NULL,
  `cg_like` varchar(10) NULL,
  `cg_report` varchar(10) NULL,
  PRIMARY KEY (`cg_no`));