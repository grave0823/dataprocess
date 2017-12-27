--行业分布
CREATE TABLE `infosystem_industryratio` (
  `id` varchar(255) NOT NULL,
  `industry` varchar(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--地域分布
CREATE TABLE `infosystem_arearatio` (
  `id` varchar(255) NOT NULL,
  `province` varchar(20) DEFAULT NULL,
  `city` varchar(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--代理商、运营商分布
CREATE TABLE `infosystem_agentratio` (
  `id` varchar(255) NOT NULL,
  `agentname` varchar(100) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--it隶属部门分布
CREATE TABLE `infosystem_itdepartmentratio` (
  `id` varchar(255) NOT NULL,
  `itdepartment` varchar(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--产品类型分布
CREATE TABLE `infosystem_producttyperatio` (
  `id` varchar(255) NOT NULL,
  `producttype` varchar(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--注册用户总数
CREATE TABLE `infosystem_registercount` (
  `id` varchar(255) NOT NULL,
  `count` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--网站提交问题模块分布
CREATE TABLE `mainweb_workorderratio` (
  `id` varchar(255) NOT NULL,
  `modulename` varchar(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;