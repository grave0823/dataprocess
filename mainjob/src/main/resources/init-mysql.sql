--行业分布
CREATE TABLE `infosystem_industryratio` (
  `id` varchar(255) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
  `industry` varchar(20) DEFAULT NULL,
  `count` int(11) DEFAULT NULL,
  `date` varchar(20) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--地域分布
CREATE TABLE `infosystem_arearatio` (
  `id` varchar(255) NOT NULL,
  `type` varchar(20) DEFAULT NULL,
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
  `type` varchar(20) DEFAULT NULL,
  `agentname` varchar(20) DEFAULT NULL,
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