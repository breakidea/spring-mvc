DROP TABLE IF EXISTS `CONFIG`;

CREATE TABLE `CONFIG` (
  `ID` int(3) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `FIELD` varchar(255) NOT NULL COMMENT '键名',
  `VALUE` longtext NOT NULL,
  `ENABLE` tinyint(1) NOT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY (`FIELD`)
) ENGINE=InnoDB  DEFAULT CHARSET=UTF8 COMMENT='网站配置' AUTO_INCREMENT=1 ;
