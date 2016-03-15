/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50513
Source Host           : localhost:3306
Source Database       : ihos

Target Server Type    : MYSQL
Target Server Version : 50513
File Encoding         : 65001

Date: 2012-06-01 14:01:09
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `t_deptkey`
-- ----------------------------
DROP TABLE IF EXISTS `t_deptkey`;
CREATE TABLE `t_deptkey` (
  `deptKeyId` int(11) NOT NULL,
  `checkPeriod` varchar(6) NOT NULL,
  `personNum` decimal(10,1) NOT NULL,
  `ratio` decimal(18,6) NOT NULL,
  `payin` decimal(18,6) NOT NULL,
  `cost` decimal(18,6) NOT NULL,
  `state` varchar(10) NOT NULL,
  `field1` decimal(18,6) NOT NULL,
  `field2` decimal(18,6) NOT NULL,
  `field3` decimal(18,6) NOT NULL,
  `field4` decimal(18,6) NOT NULL,
  `field5` decimal(18,6) NOT NULL,
  `field6` decimal(18,6) NOT NULL,
  `field7` decimal(18,6) NOT NULL,
  `field8` decimal(18,6) NOT NULL,
  `field9` decimal(18,6) NOT NULL,
  `field10` decimal(18,6) NOT NULL,
  `field11` decimal(18,6) NOT NULL,
  `field12` decimal(18,6) NOT NULL,
  `field13` decimal(18,6) NOT NULL,
  `field14` decimal(18,6) NOT NULL,
  `field15` decimal(18,6) NOT NULL,
  `field16` decimal(18,6) NOT NULL,
  `field17` decimal(18,6) NOT NULL,
  `field18` decimal(18,6) NOT NULL,
  `field19` decimal(18,6) NOT NULL,
  `field20` decimal(18,6) NOT NULL,
  `deptId` varchar(20) NOT NULL,
  `field21` decimal(18,6) NOT NULL,
  `field22` decimal(18,6) NOT NULL,
  `field23` decimal(18,6) NOT NULL,
  `field24` decimal(18,6) NOT NULL,
  `field25` decimal(18,6) NOT NULL,
  `field26` decimal(18,6) NOT NULL,
  `field27` decimal(18,6) NOT NULL,
  `field28` decimal(18,6) NOT NULL,
  `field29` decimal(18,6) NOT NULL,
  `field30` decimal(18,6) NOT NULL,
  `field31` decimal(18,6) NOT NULL,
  `field32` decimal(18,6) NOT NULL,
  `field33` decimal(18,6) NOT NULL,
  `field34` decimal(18,6) NOT NULL,
  `field35` decimal(18,6) NOT NULL,
  `field36` decimal(18,6) NOT NULL,
  `field37` decimal(18,6) NOT NULL,
  `field38` decimal(18,6) NOT NULL,
  `field39` decimal(18,6) NOT NULL,
  `field40` decimal(18,6) NOT NULL,
  `field41` decimal(18,6) NOT NULL,
  `field42` decimal(18,6) NOT NULL,
  `field43` decimal(18,6) NOT NULL,
  `field44` decimal(18,6) NOT NULL,
  `field45` decimal(18,6) NOT NULL,
  `field46` decimal(18,6) NOT NULL,
  `field47` decimal(18,6) NOT NULL,
  `field48` decimal(18,6) NOT NULL,
  `field49` decimal(18,6) NOT NULL,
  `field50` decimal(18,6) NOT NULL,
  `field51` decimal(18,6) NOT NULL,
  `field52` decimal(18,6) NOT NULL,
  `field53` decimal(18,6) NOT NULL,
  `field54` decimal(18,6) NOT NULL,
  `field55` decimal(18,6) NOT NULL,
  `field56` decimal(18,6) NOT NULL,
  `field57` decimal(18,6) NOT NULL,
  `field58` decimal(18,6) NOT NULL,
  `field59` decimal(18,6) NOT NULL,
  `field60` decimal(18,6) NOT NULL,
  `field61` decimal(18,6) NOT NULL,
  `field62` decimal(18,6) NOT NULL,
  `field63` decimal(18,6) NOT NULL,
  `field64` decimal(18,6) NOT NULL,
  `field65` decimal(18,6) NOT NULL,
  `field66` decimal(18,6) NOT NULL,
  `field67` decimal(18,6) NOT NULL,
  `field68` decimal(18,6) NOT NULL,
  `field69` decimal(18,6) NOT NULL,
  `field70` decimal(18,6) NOT NULL,
  `field71` decimal(18,6) NOT NULL,
  `field72` decimal(18,6) NOT NULL,
  `field73` decimal(18,6) NOT NULL,
  `field74` decimal(18,6) NOT NULL,
  `field75` decimal(18,6) NOT NULL,
  `field76` decimal(18,6) NOT NULL,
  `field77` decimal(18,6) NOT NULL,
  `field78` decimal(18,6) NOT NULL,
  `field79` decimal(18,6) NOT NULL,
  `field80` decimal(18,6) NOT NULL,
  `field81` decimal(18,6) NOT NULL,
  `field82` decimal(18,6) NOT NULL,
  `field83` decimal(18,6) NOT NULL,
  `field84` decimal(18,6) NOT NULL,
  `field85` decimal(18,6) NOT NULL,
  `field86` decimal(18,6) NOT NULL,
  `field87` decimal(18,6) NOT NULL,
  `field88` decimal(18,6) NOT NULL,
  `field89` decimal(18,6) NOT NULL,
  `field90` decimal(18,6) NOT NULL,
  `field91` decimal(18,6) NOT NULL,
  `field92` decimal(18,6) NOT NULL,
  `field93` decimal(18,6) NOT NULL,
  `field94` decimal(18,6) NOT NULL,
  `field95` decimal(18,6) NOT NULL,
  `field96` decimal(18,6) NOT NULL,
  `field97` decimal(18,6) NOT NULL,
  `field98` decimal(18,6) NOT NULL,
  `field99` decimal(18,6) NOT NULL,
  `field100` decimal(18,6) NOT NULL,
  `field101` decimal(18,6) NOT NULL,
  `field102` decimal(18,6) NOT NULL,
  `field103` decimal(18,6) NOT NULL,
  `field104` decimal(18,6) NOT NULL,
  `field105` decimal(18,6) NOT NULL,
  `field106` decimal(18,6) NOT NULL,
  `field107` decimal(18,6) NOT NULL,
  `field108` decimal(18,6) NOT NULL,
  `field109` decimal(18,6) NOT NULL,
  `field110` decimal(18,6) NOT NULL,
  `field111` decimal(18,6) NOT NULL,
  `field112` decimal(18,6) NOT NULL,
  `field113` decimal(18,6) NOT NULL,
  `field114` decimal(18,6) NOT NULL,
  `field115` decimal(18,6) NOT NULL,
  `field116` decimal(18,6) NOT NULL,
  `field117` decimal(18,6) NOT NULL,
  `field118` decimal(18,6) NOT NULL,
  `field119` decimal(18,6) NOT NULL,
  `field120` decimal(18,6) NOT NULL,
  `field121` decimal(18,6) NOT NULL,
  `field122` decimal(18,6) NOT NULL,
  `field123` decimal(18,6) NOT NULL,
  `field124` decimal(18,6) NOT NULL,
  `field125` decimal(18,6) NOT NULL,
  `field126` decimal(18,6) NOT NULL,
  `field127` decimal(18,6) NOT NULL,
  `field128` decimal(18,6) NOT NULL,
  `field129` decimal(18,6) NOT NULL,
  `field130` decimal(18,6) NOT NULL,
  `field131` decimal(18,6) NOT NULL,
  `field132` decimal(18,6) NOT NULL,
  `field133` decimal(18,6) NOT NULL,
  `field134` decimal(18,6) NOT NULL,
  `field135` decimal(18,6) NOT NULL,
  `field136` decimal(18,6) NOT NULL,
  `field137` decimal(18,6) NOT NULL,
  `field138` decimal(18,6) NOT NULL,
  `field139` decimal(18,6) NOT NULL,
  `field140` decimal(18,6) NOT NULL,
  `field141` decimal(18,6) NOT NULL,
  `field142` decimal(18,6) NOT NULL,
  `field143` decimal(18,6) NOT NULL,
  `field144` decimal(18,6) NOT NULL,
  `field145` decimal(18,6) NOT NULL,
  `field146` decimal(18,6) NOT NULL,
  `field147` decimal(18,6) NOT NULL,
  `field148` decimal(18,6) NOT NULL,
  `field149` decimal(18,6) NOT NULL,
  `field150` decimal(18,6) NOT NULL,
  `field151` decimal(18,6) NOT NULL,
  `field152` decimal(18,6) NOT NULL,
  `field153` decimal(18,6) NOT NULL,
  `field154` decimal(18,6) NOT NULL,
  `field155` decimal(18,6) NOT NULL,
  `field156` decimal(18,6) NOT NULL,
  `field157` decimal(18,6) NOT NULL,
  `field158` decimal(18,6) NOT NULL,
  `field159` decimal(18,6) NOT NULL,
  `field160` decimal(18,6) NOT NULL,
  `field161` decimal(18,6) NOT NULL,
  `field162` decimal(18,6) NOT NULL,
  `field163` decimal(18,6) NOT NULL,
  `field164` decimal(18,6) NOT NULL,
  `field165` decimal(18,6) NOT NULL,
  `field166` decimal(18,6) NOT NULL,
  `field167` decimal(18,6) NOT NULL,
  `field168` decimal(18,6) NOT NULL,
  `field169` decimal(18,6) NOT NULL,
  `field170` decimal(18,6) NOT NULL,
  `field171` decimal(18,6) NOT NULL,
  `field172` decimal(18,6) NOT NULL,
  `field173` decimal(18,6) NOT NULL,
  `field174` decimal(18,6) NOT NULL,
  `field175` decimal(18,6) NOT NULL,
  `field176` decimal(18,6) NOT NULL,
  `field177` decimal(18,6) NOT NULL,
  `field178` decimal(18,6) NOT NULL,
  `field179` decimal(18,6) NOT NULL,
  `field180` decimal(18,6) NOT NULL,
  `field181` decimal(18,6) NOT NULL,
  `field182` decimal(18,6) NOT NULL,
  `field183` decimal(18,6) NOT NULL,
  `field184` decimal(18,6) NOT NULL,
  `field185` decimal(18,6) NOT NULL,
  `field186` decimal(18,6) NOT NULL,
  `field187` decimal(18,6) NOT NULL,
  `field188` decimal(18,6) NOT NULL,
  `field189` decimal(18,6) NOT NULL,
  `field190` decimal(18,6) NOT NULL,
  `field191` decimal(18,6) NOT NULL,
  `field192` decimal(18,6) NOT NULL,
  `field193` decimal(18,6) NOT NULL,
  `field194` decimal(18,6) NOT NULL,
  `field195` decimal(18,6) NOT NULL,
  `field196` decimal(18,6) NOT NULL,
  `field197` decimal(18,6) NOT NULL,
  `field198` decimal(18,6) NOT NULL,
  `field199` decimal(18,6) NOT NULL,
  `field200` decimal(18,6) NOT NULL,
  `field201` decimal(18,6) NOT NULL,
  `field202` decimal(18,6) NOT NULL,
  `field203` decimal(18,6) NOT NULL,
  `field204` decimal(18,6) NOT NULL,
  `field205` decimal(18,6) NOT NULL,
  `field206` decimal(18,6) NOT NULL,
  `field207` decimal(18,6) NOT NULL,
  `field208` decimal(18,6) NOT NULL,
  `field209` decimal(18,6) NOT NULL,
  `field210` decimal(18,6) NOT NULL,
  `field211` decimal(18,6) NOT NULL,
  `field212` decimal(18,6) NOT NULL,
  `field213` decimal(18,6) NOT NULL,
  `field214` decimal(18,6) NOT NULL,
  `field215` decimal(18,6) NOT NULL,
  `field216` decimal(18,6) NOT NULL,
  `field217` decimal(18,6) NOT NULL,
  `field218` decimal(18,6) NOT NULL,
  `field219` decimal(18,6) NOT NULL,
  `field220` decimal(18,6) NOT NULL,
  `field221` decimal(18,6) NOT NULL,
  `field222` decimal(18,6) NOT NULL,
  `field223` decimal(18,6) NOT NULL,
  `field224` decimal(18,6) NOT NULL,
  `field225` decimal(18,6) NOT NULL,
  `field226` decimal(18,6) NOT NULL,
  `field227` decimal(18,6) NOT NULL,
  `field228` decimal(18,6) NOT NULL,
  `field229` decimal(18,6) NOT NULL,
  `field230` decimal(18,6) NOT NULL,
  `field231` decimal(18,6) NOT NULL,
  `field232` decimal(18,6) NOT NULL,
  `field233` decimal(18,6) NOT NULL,
  `field234` decimal(18,6) NOT NULL,
  `field235` decimal(18,6) NOT NULL,
  `field236` decimal(18,6) NOT NULL,
  `field237` decimal(18,6) NOT NULL,
  `field238` decimal(18,6) NOT NULL,
  `field239` decimal(18,6) NOT NULL,
  `field240` decimal(18,6) NOT NULL,
  `field241` decimal(18,6) NOT NULL,
  `field242` decimal(18,6) NOT NULL,
  `field243` decimal(18,6) NOT NULL,
  `field244` decimal(18,6) NOT NULL,
  `field245` decimal(18,6) NOT NULL,
  `field246` decimal(18,6) NOT NULL,
  `field247` decimal(18,6) NOT NULL,
  `field248` decimal(18,6) NOT NULL,
  `field249` decimal(18,6) NOT NULL,
  `field250` decimal(18,6) NOT NULL,
  `deptclass` varchar(10) DEFAULT NULL,
  `field251` decimal(18,6) NOT NULL,
  `field252` decimal(18,6) NOT NULL,
  `field253` decimal(18,6) DEFAULT NULL,
  `field254` decimal(18,6) NOT NULL,
  `field255` decimal(18,6) NOT NULL,
  `field256` decimal(18,6) NOT NULL,
  `field257` decimal(18,6) NOT NULL,
  `field258` decimal(18,6) NOT NULL,
  `field259` decimal(18,6) NOT NULL,
  `field260` decimal(18,6) NOT NULL,
  `field261` decimal(18,6) NOT NULL,
  `field262` decimal(18,6) NOT NULL,
  `field263` decimal(18,6) NOT NULL,
  `field264` decimal(18,6) NOT NULL,
  `field265` decimal(18,6) NOT NULL,
  `field266` decimal(18,6) NOT NULL,
  `field267` decimal(18,6) NOT NULL,
  `field268` decimal(18,6) NOT NULL,
  `field269` decimal(18,6) NOT NULL,
  `field270` decimal(18,6) NOT NULL,
  `field271` decimal(18,6) NOT NULL,
  `field272` decimal(18,6) NOT NULL,
  `field273` decimal(18,6) NOT NULL,
  `field274` decimal(18,6) NOT NULL,
  `field275` decimal(18,6) NOT NULL,
  `field276` decimal(18,6) NOT NULL,
  `field277` decimal(18,6) NOT NULL,
  `field278` decimal(18,6) NOT NULL,
  `field279` decimal(18,6) NOT NULL,
  `field280` decimal(18,6) NOT NULL,
  `field281` decimal(18,6) NOT NULL,
  `field282` decimal(18,6) NOT NULL,
  `field283` decimal(18,6) NOT NULL,
  `field284` decimal(18,6) NOT NULL,
  `field285` decimal(18,6) NOT NULL,
  `field286` decimal(18,6) NOT NULL,
  `field287` decimal(18,6) NOT NULL,
  `field288` decimal(18,6) NOT NULL,
  `field289` decimal(18,6) NOT NULL,
  `field290` decimal(18,6) NOT NULL,
  `field291` decimal(18,6) NOT NULL,
  `field292` decimal(18,6) NOT NULL,
  `field293` decimal(18,6) NOT NULL,
  `field294` decimal(18,6) NOT NULL,
  `field295` decimal(18,6) NOT NULL,
  `field296` decimal(18,6) NOT NULL,
  `field297` decimal(18,6) NOT NULL,
  `field298` decimal(18,6) NOT NULL,
  `field299` decimal(18,6) NOT NULL,
  `field300` decimal(18,6) NOT NULL,
  `field301` decimal(18,6) NOT NULL,
  `field302` decimal(18,6) NOT NULL,
  `field303` decimal(18,6) NOT NULL,
  `field304` decimal(18,6) NOT NULL,
  `field305` decimal(18,6) NOT NULL,
  `field306` decimal(18,6) NOT NULL,
  `field307` decimal(18,6) NOT NULL,
  `field308` decimal(18,6) NOT NULL,
  `field309` decimal(18,6) NOT NULL,
  `field310` decimal(18,6) NOT NULL,
  `field311` decimal(18,6) NOT NULL,
  `field312` decimal(18,6) NOT NULL,
  `field313` decimal(18,6) NOT NULL,
  `field314` decimal(18,6) NOT NULL,
  `field315` decimal(18,6) NOT NULL,
  `field316` decimal(18,6) NOT NULL,
  `field317` decimal(18,6) NOT NULL,
  `field318` decimal(18,6) NOT NULL,
  `field319` decimal(18,6) NOT NULL,
  `field320` decimal(18,6) NOT NULL,
  `field321` decimal(18,6) NOT NULL,
  `field322` decimal(18,6) NOT NULL,
  `field323` decimal(18,6) NOT NULL,
  `field324` decimal(18,6) NOT NULL,
  `field325` decimal(18,6) NOT NULL,
  `field326` decimal(18,6) NOT NULL,
  `field327` decimal(18,6) NOT NULL,
  `field328` decimal(18,6) NOT NULL,
  `field329` decimal(18,6) NOT NULL,
  `field330` decimal(18,6) NOT NULL,
  `field331` decimal(18,6) NOT NULL,
  `field332` decimal(18,6) NOT NULL,
  `field333` decimal(18,6) NOT NULL,
  `field334` decimal(18,6) NOT NULL,
  `field335` decimal(18,6) NOT NULL,
  `field336` decimal(18,6) NOT NULL,
  `field337` decimal(18,6) NOT NULL,
  `field338` decimal(18,6) NOT NULL,
  `field339` decimal(18,6) NOT NULL,
  `field340` decimal(18,6) NOT NULL,
  `field341` decimal(18,6) NOT NULL,
  `field342` decimal(18,6) NOT NULL,
  `field343` decimal(18,6) NOT NULL,
  `field344` decimal(18,6) NOT NULL,
  `field345` decimal(18,6) NOT NULL,
  `field346` decimal(18,6) NOT NULL,
  `field347` decimal(18,6) NOT NULL,
  `field348` decimal(18,6) NOT NULL,
  `field349` decimal(18,6) NOT NULL,
  `field350` decimal(18,6) NOT NULL,
  `field351` decimal(18,6) NOT NULL,
  `field352` decimal(18,6) NOT NULL,
  `field353` decimal(18,6) NOT NULL,
  `field354` decimal(18,6) NOT NULL,
  `field355` decimal(18,6) NOT NULL,
  `field356` decimal(18,6) NOT NULL,
  `field357` decimal(18,6) NOT NULL,
  `field358` decimal(18,6) NOT NULL,
  `field359` decimal(18,6) NOT NULL,
  `field360` decimal(18,6) NOT NULL,
  `field361` decimal(18,6) NOT NULL,
  `field362` decimal(18,6) NOT NULL,
  `field363` decimal(18,6) NOT NULL,
  `field364` decimal(18,6) NOT NULL,
  `field365` decimal(18,6) NOT NULL,
  `field366` decimal(18,6) NOT NULL,
  `field367` decimal(18,6) NOT NULL,
  `field368` decimal(18,6) NOT NULL,
  `field369` decimal(18,6) NOT NULL,
  `field370` decimal(18,6) NOT NULL,
  `field371` decimal(18,6) NOT NULL,
  `field372` decimal(18,6) NOT NULL,
  `field373` decimal(18,6) NOT NULL,
  `field374` decimal(18,6) NOT NULL,
  `field375` decimal(18,6) NOT NULL,
  `field376` decimal(18,6) NOT NULL,
  `field377` decimal(18,6) NOT NULL,
  `field378` decimal(18,6) NOT NULL,
  `field379` decimal(18,6) NOT NULL,
  `field380` decimal(18,6) NOT NULL,
  `field381` decimal(18,6) NOT NULL,
  `field382` decimal(18,6) NOT NULL,
  `field383` decimal(18,6) NOT NULL,
  `field384` decimal(18,6) NOT NULL,
  `field385` decimal(18,6) NOT NULL,
  `field386` decimal(18,6) NOT NULL,
  `field387` decimal(18,6) NOT NULL,
  `field388` decimal(18,6) NOT NULL,
  `field389` decimal(18,6) NOT NULL,
  `field390` decimal(18,6) NOT NULL,
  `field391` decimal(18,6) NOT NULL,
  `field392` decimal(18,6) NOT NULL,
  `field393` decimal(18,6) NOT NULL,
  `field394` decimal(18,6) NOT NULL,
  `field395` decimal(18,6) NOT NULL,
  `field396` decimal(18,6) NOT NULL,
  `field397` decimal(18,6) NOT NULL,
  `field398` decimal(18,6) NOT NULL,
  `field399` decimal(18,6) NOT NULL,
  `field400` decimal(18,6) NOT NULL,
  `outin` varchar(10) DEFAULT NULL,
  `hleaf` tinyint(4) DEFAULT NULL,
  `leaf` tinyint(4) DEFAULT NULL,
  `clevel` smallint(6) DEFAULT NULL,
  `hlevel` smallint(6) DEFAULT NULL,
  `field401` decimal(18,6) DEFAULT NULL,
  `field402` decimal(18,6) DEFAULT NULL,
  `field403` decimal(18,6) DEFAULT NULL,
  `field404` decimal(18,6) DEFAULT NULL,
  `field405` decimal(18,6) DEFAULT NULL,
  `field406` decimal(18,6) DEFAULT NULL,
  `field407` decimal(18,6) DEFAULT NULL,
  `field408` decimal(18,6) DEFAULT NULL,
  `field409` decimal(18,6) DEFAULT NULL,
  `field410` decimal(18,6) DEFAULT NULL,
  `field411` decimal(18,6) DEFAULT NULL,
  `field412` decimal(18,6) DEFAULT NULL,
  `field413` decimal(18,6) DEFAULT NULL,
  `field414` decimal(18,6) DEFAULT NULL,
  `field415` decimal(18,6) DEFAULT NULL,
  `field416` decimal(18,6) DEFAULT NULL,
  `field417` decimal(18,6) DEFAULT NULL,
  `field418` decimal(18,6) DEFAULT NULL,
  `field419` decimal(18,6) DEFAULT NULL,
  `field420` decimal(18,6) DEFAULT NULL,
  `field421` decimal(18,6) DEFAULT NULL,
  `field422` decimal(18,6) DEFAULT NULL,
  `field423` decimal(18,6) DEFAULT NULL,
  `field424` decimal(18,6) DEFAULT NULL,
  `field425` decimal(18,6) DEFAULT NULL,
  `field426` decimal(18,6) DEFAULT NULL,
  `field427` decimal(18,6) DEFAULT NULL,
  `field428` decimal(18,6) DEFAULT NULL,
  `field429` decimal(18,6) DEFAULT NULL,
  `field430` decimal(18,6) DEFAULT NULL,
  `field431` decimal(18,6) DEFAULT NULL,
  `field432` decimal(18,6) DEFAULT NULL,
  `field433` decimal(18,6) DEFAULT NULL,
  `field434` decimal(18,6) DEFAULT NULL,
  `field435` decimal(18,6) DEFAULT NULL,
  `field436` decimal(18,6) DEFAULT NULL,
  `field437` decimal(18,6) DEFAULT NULL,
  `field438` decimal(18,6) DEFAULT NULL,
  `field439` decimal(18,6) DEFAULT NULL,
  `field440` decimal(18,6) DEFAULT NULL,
  `field441` decimal(18,6) DEFAULT NULL,
  `field442` decimal(18,6) DEFAULT NULL,
  `field443` decimal(18,6) DEFAULT NULL,
  `field444` decimal(18,6) DEFAULT NULL,
  `field445` decimal(18,6) DEFAULT NULL,
  `field446` decimal(18,6) DEFAULT NULL,
  `field447` decimal(18,6) DEFAULT NULL,
  `field448` decimal(18,6) DEFAULT NULL,
  `field449` decimal(18,6) DEFAULT NULL,
  `field450` decimal(18,6) DEFAULT NULL,
  `field451` decimal(18,6) DEFAULT NULL,
  `field452` decimal(18,6) DEFAULT NULL,
  `field453` decimal(18,6) DEFAULT NULL,
  `field454` decimal(18,6) DEFAULT NULL,
  `field455` decimal(18,6) DEFAULT NULL,
  `field456` decimal(18,6) DEFAULT NULL,
  `field457` varchar(150) DEFAULT NULL,
  `field458` varchar(150) DEFAULT NULL,
  `field459` decimal(18,6) DEFAULT NULL,
  `field460` decimal(18,6) DEFAULT NULL,
  `field461` decimal(18,6) DEFAULT NULL,
  `field462` decimal(18,6) DEFAULT NULL,
  `field463` decimal(18,6) DEFAULT NULL,
  `field464` decimal(18,6) DEFAULT NULL,
  `field465` decimal(18,6) DEFAULT NULL,
  `field466` decimal(18,6) DEFAULT NULL,
  `field467` decimal(18,6) DEFAULT NULL,
  `field468` decimal(18,6) DEFAULT NULL,
  `field469` decimal(18,6) DEFAULT NULL,
  `field470` decimal(18,6) DEFAULT NULL,
  `field471` decimal(18,6) DEFAULT NULL,
  `field472` decimal(18,6) DEFAULT NULL,
  `field473` decimal(18,6) DEFAULT NULL,
  `field474` decimal(18,6) DEFAULT NULL,
  `field475` decimal(18,6) DEFAULT NULL,
  `field476` decimal(18,6) DEFAULT NULL,
  `field477` decimal(18,0) DEFAULT NULL,
  `field478` decimal(18,0) DEFAULT NULL,
  `field479` decimal(18,6) DEFAULT NULL,
  `field480` decimal(18,6) DEFAULT NULL,
  `field481` decimal(18,6) DEFAULT NULL,
  `field482` decimal(18,6) DEFAULT NULL,
  `field483` decimal(18,6) DEFAULT NULL,
  `field484` decimal(18,6) DEFAULT NULL,
  `field485` decimal(18,6) DEFAULT NULL,
  `field486` decimal(18,6) DEFAULT NULL,
  `field487` decimal(18,6) DEFAULT NULL,
  `field488` decimal(18,6) DEFAULT NULL,
  `field489` decimal(18,6) DEFAULT NULL,
  `field490` decimal(18,6) DEFAULT NULL,
  `field491` decimal(18,6) DEFAULT NULL,
  `field492` decimal(18,6) DEFAULT NULL,
  `field493` decimal(18,6) DEFAULT NULL,
  `field494` decimal(18,6) DEFAULT NULL,
  `field495` decimal(18,6) DEFAULT NULL,
  `field496` decimal(18,6) DEFAULT NULL,
  `field497` decimal(18,6) DEFAULT NULL,
  `field498` decimal(18,6) DEFAULT NULL,
  `field499` decimal(18,6) DEFAULT NULL,
  `field500` decimal(18,6) DEFAULT NULL,
  `secondDeptId` varchar(20) DEFAULT NULL,
  `field501` decimal(18,6) DEFAULT NULL,
  `field502` decimal(18,6) DEFAULT NULL,
  `field503` decimal(18,6) DEFAULT NULL,
  `field504` decimal(18,6) DEFAULT NULL,
  `field505` decimal(18,6) DEFAULT NULL,
  `field506` decimal(18,6) DEFAULT NULL,
  `field507` decimal(18,6) DEFAULT NULL,
  `field508` decimal(18,6) DEFAULT NULL,
  `field509` decimal(18,6) DEFAULT NULL,
  `field510` decimal(18,6) DEFAULT NULL,
  `field511` decimal(18,6) DEFAULT NULL,
  `field512` decimal(18,6) DEFAULT NULL,
  `field513` decimal(18,6) DEFAULT NULL,
  `field514` decimal(18,6) DEFAULT NULL,
  `field515` decimal(18,6) DEFAULT NULL,
  `field516` decimal(18,6) DEFAULT NULL,
  `field517` decimal(18,6) DEFAULT NULL,
  `field518` decimal(18,6) DEFAULT NULL,
  `field519` decimal(18,6) DEFAULT NULL,
  `field520` decimal(18,6) DEFAULT NULL,
  `field521` decimal(18,6) DEFAULT NULL,
  `field522` decimal(18,6) DEFAULT NULL,
  `field523` decimal(18,6) DEFAULT NULL,
  `field524` decimal(18,6) DEFAULT NULL,
  `field525` decimal(18,6) DEFAULT NULL,
  `field526` decimal(18,6) DEFAULT NULL,
  `field527` decimal(18,6) DEFAULT NULL,
  `field528` decimal(18,6) DEFAULT NULL,
  `field529` decimal(18,6) DEFAULT NULL,
  `field530` decimal(18,6) DEFAULT NULL,
  `field531` decimal(18,6) DEFAULT NULL,
  `field532` decimal(18,6) DEFAULT NULL,
  `field533` decimal(18,6) DEFAULT NULL,
  `field534` decimal(18,6) DEFAULT NULL,
  `field535` decimal(18,6) DEFAULT NULL,
  `field536` decimal(18,6) DEFAULT NULL,
  `field537` decimal(18,6) DEFAULT NULL,
  `field538` decimal(18,6) DEFAULT NULL,
  `field539` decimal(18,6) DEFAULT NULL,
  `field540` decimal(18,6) DEFAULT NULL,
  `field541` decimal(18,6) DEFAULT NULL,
  `field542` decimal(18,6) DEFAULT NULL,
  `field543` decimal(18,6) DEFAULT NULL,
  `field544` decimal(18,6) DEFAULT NULL,
  `field545` decimal(18,6) DEFAULT NULL,
  `field546` decimal(18,6) DEFAULT NULL,
  `field547` decimal(18,6) DEFAULT NULL,
  `field548` decimal(18,6) DEFAULT NULL,
  `field549` decimal(18,6) DEFAULT NULL,
  `field550` decimal(18,6) DEFAULT NULL,
  `field551` decimal(18,6) DEFAULT NULL,
  `field552` decimal(18,6) DEFAULT NULL,
  `field553` decimal(18,6) DEFAULT NULL,
  `field554` decimal(18,6) DEFAULT NULL,
  `field555` decimal(18,6) DEFAULT NULL,
  `field556` decimal(18,6) DEFAULT NULL,
  `field557` decimal(18,6) DEFAULT NULL,
  `field558` decimal(18,6) DEFAULT NULL,
  `field559` decimal(18,6) DEFAULT NULL,
  `field560` decimal(18,6) DEFAULT NULL,
  `field561` decimal(18,6) DEFAULT NULL,
  `field562` decimal(18,6) DEFAULT NULL,
  `field563` decimal(18,6) DEFAULT NULL,
  `field564` decimal(18,6) DEFAULT NULL,
  `field565` decimal(18,6) DEFAULT NULL,
  `field566` decimal(18,6) DEFAULT NULL,
  `field567` decimal(18,6) DEFAULT NULL,
  `field568` decimal(18,6) DEFAULT NULL,
  `field569` decimal(18,6) DEFAULT NULL,
  `field570` decimal(18,6) DEFAULT NULL,
  `field571` decimal(18,6) DEFAULT NULL,
  `field572` decimal(18,6) DEFAULT NULL,
  `field573` decimal(18,6) DEFAULT NULL,
  `field574` decimal(18,6) DEFAULT NULL,
  `field575` decimal(18,6) DEFAULT NULL,
  `field576` decimal(18,6) DEFAULT NULL,
  `field577` decimal(18,6) DEFAULT NULL,
  `field578` decimal(18,6) DEFAULT NULL,
  `field579` decimal(18,6) DEFAULT NULL,
  `field580` decimal(18,6) DEFAULT NULL,
  `field581` decimal(18,6) DEFAULT NULL,
  `field582` decimal(18,6) DEFAULT NULL,
  `field583` decimal(18,6) DEFAULT NULL,
  `field584` decimal(18,6) DEFAULT NULL,
  `field585` decimal(18,6) DEFAULT NULL,
  `field586` decimal(18,6) DEFAULT NULL,
  `field587` decimal(18,6) DEFAULT NULL,
  `field588` decimal(18,6) DEFAULT NULL,
  `field589` decimal(18,6) DEFAULT NULL,
  `field590` decimal(18,6) DEFAULT NULL,
  `field591` decimal(18,6) DEFAULT NULL,
  `field592` decimal(18,6) DEFAULT NULL,
  `field593` decimal(18,6) DEFAULT NULL,
  `field594` decimal(18,6) DEFAULT NULL,
  `field595` decimal(18,6) DEFAULT NULL,
  `field596` decimal(18,6) DEFAULT NULL,
  `field597` decimal(18,6) DEFAULT NULL,
  `field598` decimal(18,6) DEFAULT NULL,
  `field599` decimal(18,6) DEFAULT NULL,
  `field600` decimal(18,6) DEFAULT NULL,
  `field601` decimal(18,6) DEFAULT NULL,
  `field602` decimal(18,6) DEFAULT NULL,
  `field603` decimal(18,6) DEFAULT NULL,
  `field604` decimal(18,6) DEFAULT NULL,
  `field605` decimal(18,6) DEFAULT NULL,
  `field606` decimal(18,6) DEFAULT NULL,
  `field607` decimal(18,6) DEFAULT NULL,
  `field608` decimal(18,6) DEFAULT NULL,
  `field609` decimal(18,6) DEFAULT NULL,
  `field610` decimal(18,6) DEFAULT NULL,
  `field611` decimal(18,6) DEFAULT NULL,
  `field612` decimal(18,6) DEFAULT NULL,
  `field613` decimal(18,6) DEFAULT NULL,
  `field614` decimal(18,6) DEFAULT NULL,
  `field615` decimal(18,6) DEFAULT NULL,
  `field616` decimal(18,6) DEFAULT NULL,
  `field617` decimal(18,6) DEFAULT NULL,
  `field618` decimal(18,6) DEFAULT NULL,
  `field619` decimal(18,6) DEFAULT NULL,
  `field620` decimal(18,6) DEFAULT NULL,
  `field621` decimal(18,6) DEFAULT NULL,
  `field622` decimal(18,6) DEFAULT NULL,
  `field623` decimal(18,6) DEFAULT NULL,
  `field624` decimal(18,6) DEFAULT NULL,
  `field625` decimal(18,6) DEFAULT NULL,
  `field626` decimal(18,6) DEFAULT NULL,
  `field627` decimal(18,6) DEFAULT NULL,
  `field628` decimal(18,6) DEFAULT NULL,
  `field629` decimal(18,6) DEFAULT NULL,
  `field630` decimal(18,6) DEFAULT NULL,
  `field631` decimal(18,6) DEFAULT NULL,
  `field632` decimal(18,6) DEFAULT NULL,
  `field633` decimal(18,6) DEFAULT NULL,
  `field634` decimal(18,6) DEFAULT NULL,
  `field635` decimal(18,6) DEFAULT NULL,
  `field636` decimal(18,6) DEFAULT NULL,
  `field637` decimal(18,6) DEFAULT NULL,
  `field638` decimal(18,6) DEFAULT NULL,
  `field639` decimal(18,6) DEFAULT NULL,
  `field640` decimal(18,6) DEFAULT NULL,
  `field641` decimal(18,6) DEFAULT NULL,
  `field642` decimal(18,6) DEFAULT NULL,
  `field643` decimal(18,6) DEFAULT NULL,
  `field644` decimal(18,6) DEFAULT NULL,
  `field645` decimal(18,6) DEFAULT NULL,
  `field646` decimal(18,6) DEFAULT NULL,
  `field647` decimal(18,6) DEFAULT NULL,
  `field648` decimal(18,6) DEFAULT NULL,
  `field649` decimal(18,6) DEFAULT NULL,
  `field650` decimal(18,6) DEFAULT NULL,
  `field651` decimal(18,6) DEFAULT NULL,
  `field652` decimal(18,6) DEFAULT NULL,
  `field653` decimal(18,6) DEFAULT NULL,
  `field654` decimal(18,6) DEFAULT NULL,
  `field655` decimal(18,6) DEFAULT NULL,
  `field656` decimal(18,6) DEFAULT NULL,
  `field657` decimal(18,6) DEFAULT NULL,
  `field658` decimal(18,6) DEFAULT NULL,
  `field659` decimal(18,6) DEFAULT NULL,
  `field660` decimal(18,6) DEFAULT NULL,
  `field661` decimal(18,6) DEFAULT NULL,
  `field662` decimal(18,6) DEFAULT NULL,
  `field663` decimal(18,6) DEFAULT NULL,
  `field664` decimal(18,6) DEFAULT NULL,
  `field665` decimal(18,6) DEFAULT NULL,
  `field666` decimal(18,6) DEFAULT NULL,
  `field667` decimal(18,6) DEFAULT NULL,
  `field668` decimal(18,6) DEFAULT NULL,
  `field669` decimal(18,6) DEFAULT NULL,
  `field670` decimal(18,6) DEFAULT NULL,
  `field671` decimal(18,6) DEFAULT NULL,
  `field672` decimal(18,6) DEFAULT NULL,
  `field673` decimal(18,6) DEFAULT NULL,
  `field674` decimal(18,6) DEFAULT NULL,
  `field675` decimal(18,6) DEFAULT NULL,
  `field676` decimal(18,6) DEFAULT NULL,
  `field677` decimal(18,6) DEFAULT NULL,
  `field678` decimal(18,6) DEFAULT NULL,
  `field679` decimal(18,6) DEFAULT NULL,
  `field680` decimal(18,6) DEFAULT NULL,
  `field681` decimal(18,6) DEFAULT NULL,
  `field682` decimal(18,6) DEFAULT NULL,
  `field683` decimal(18,6) DEFAULT NULL,
  `field684` decimal(18,6) DEFAULT NULL,
  `field685` decimal(18,6) DEFAULT NULL,
  `field686` decimal(18,6) DEFAULT NULL,
  `field687` decimal(18,6) DEFAULT NULL,
  `field688` decimal(18,6) DEFAULT NULL,
  `field689` decimal(18,6) DEFAULT NULL,
  `field690` decimal(18,6) DEFAULT NULL,
  `field691` decimal(18,6) DEFAULT NULL,
  `field692` decimal(18,6) DEFAULT NULL,
  `field693` decimal(18,6) DEFAULT NULL,
  `field694` decimal(18,6) DEFAULT NULL,
  `field695` decimal(18,6) DEFAULT NULL,
  `field696` decimal(18,6) DEFAULT NULL,
  `field697` decimal(18,6) DEFAULT NULL,
  `field698` decimal(18,6) DEFAULT NULL,
  `field699` decimal(18,6) DEFAULT NULL,
  `field700` decimal(18,6) DEFAULT NULL,
  PRIMARY KEY (`deptKeyId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of t_deptkey
-- ----------------------------
