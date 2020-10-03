CREATE TABLE `board_tbl` (
  `idx` int(11) NOT NULL AUTO_INCREMENT,
  `title` longtext NOT NULL,
  `content` longtext NOT NULL,
  `writer` varchar(45) NOT NULL,
  `reg_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `upd_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `hits` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`idx`),
  KEY `INDEX_REG_DATE` (`reg_date`) COMMENT '페이징 처리를 위한 인덱스.'
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `board_attach_tbl` (
  `uuid` varchar(100) NOT NULL,
  `upload_path` varchar(200) NOT NULL,
  `file_name` varchar(100) NOT NULL,
  `file_type` char(1) NOT NULL DEFAULT 'I',
  `board_idx` int(11) NOT NULL,
  PRIMARY KEY (`uuid`),
  KEY `board_idx_idx` (`board_idx`),
  CONSTRAINT `board_idx` FOREIGN KEY (`board_idx`) REFERENCES `board_tbl` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

CREATE TABLE `count_board_tbl` (
  `idx` int(11) NOT NULL,
  `cnt` int(11) DEFAULT '0',
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8