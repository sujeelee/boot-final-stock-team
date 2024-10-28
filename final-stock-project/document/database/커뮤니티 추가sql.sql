CREATE TABLE `stockandfund`.`community_action` (
  `cg_no` INT NOT NULL AUTO_INCREMENT,
  `cg_num` INT NULL COMMENT '게시판 이거나 댓글 기본키',
  `cg_type` CHAR(4) NULL COMMENT '게시판이면 없음 댓글이면 c',
  `st_code` VARCHAR(255) NULL COMMENT '주식코드',
  `mb_id` VARCHAR(255) NULL COMMENT '회원아이디',
  `cg_datetime` DATETIME NULL,
  `cg_action` VARCHAR(50) NULL COMMENT '신고인지 좋아요인지',
  PRIMARY KEY (`cg_no`))
COMMENT = '게시판과 댓글의 좋아요 수 로그 테이블';
