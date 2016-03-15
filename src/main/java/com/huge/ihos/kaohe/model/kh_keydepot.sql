/*
Navicat SQL Server Data Transfer

Source Server         : ihos_server
Source Server Version : 90000
Source Host           : 192.168.1.109:1433
Source Database       : iHOS
Source Schema         : dbo

Target Server Type    : SQL Server
Target Server Version : 90000
File Encoding         : 65001

Date: 2013-01-18 13:58:08
*/


-- ----------------------------
-- Table structure for [dbo].[KH_KEYDEPOT]
-- ----------------------------
DROP TABLE [dbo].[KH_KEYDEPOT]
GO
CREATE TABLE [dbo].[KH_KEYDEPOT] (
[id] numeric(19) NOT NULL IDENTITY(1,1) ,
[level] int NULL DEFAULT ((0)) ,
[lft] int NULL DEFAULT ((1)) ,
[rgt] int NULL DEFAULT ((1)) ,
[disabled] tinyint NOT NULL ,
[dOrder] int NULL DEFAULT ((0)) ,
[inputType] varchar(50) NULL ,
[keyCode] varchar(50) NOT NULL ,
[keyName] varchar(50) NOT NULL ,
[leaf] tinyint NOT NULL ,
[pattern] varchar(50) NULL ,
[periodType] varchar(10) NOT NULL ,
[isUsed] tinyint NOT NULL ,
[parent_id] numeric(19) NULL ,
[actualFormula_id] varchar(30) NULL ,
[execDept_id] varchar(255) NULL ,
[scoreFormula_id] varchar(30) NULL ,
[targetFormula_id] varchar(30) NULL 
)


GO
DBCC CHECKIDENT(N'[dbo].[KH_KEYDEPOT]', RESEED, 40)
GO

-- ----------------------------
-- Records of KH_KEYDEPOT
-- ----------------------------
SET IDENTITY_INSERT [dbo].[KH_KEYDEPOT] ON
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'-4', N'0', N'30000', N'30021', N'0', N'0', null, N'MANAGER', N'负责人指标', N'0', null, N'月', N'1', null, null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'-3', N'0', N'20000', N'20021', N'0', N'0', null, N'PERSON', N'人员指标', N'0', null, N'月', N'1', null, null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'-2', N'0', N'10000', N'10021', N'0', N'0', null, N'ORG', N'全院指标', N'0', null, N'月', N'1', null, null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'-1', N'0', N'0', N'21', N'0', N'0', null, N'DEPT', N'部门指标', N'0', null, N'月', N'1', null, null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'1', N'1', N'1', N'20', N'0', N'0', null, N'F01', N'F01', N'1', null, N'月', N'1', N'-1', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'2', N'2', N'2', N'19', N'0', N'0', null, N'F0101', N'F0101', N'1', null, N'月', N'1', N'1', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'3', N'3', N'17', N'18', N'0', N'0', null, N'F010101', N'F010101', N'1', null, N'月', N'1', N'2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'4', N'3', N'15', N'16', N'0', N'0', null, N'F010102', N'F010102', N'1', null, N'月', N'1', N'2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'5', N'3', N'13', N'14', N'0', N'0', null, N'F010103', N'F010103', N'1', null, N'月', N'1', N'2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'6', N'3', N'11', N'12', N'0', N'0', null, N'F010104', N'F010104', N'1', null, N'月', N'1', N'2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'7', N'3', N'9', N'10', N'0', N'0', null, N'F010105', N'F010105', N'1', null, N'月', N'1', N'2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'8', N'3', N'7', N'8', N'0', N'0', null, N'F010106', N'F010106', N'1', null, N'月', N'1', N'2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'9', N'3', N'5', N'6', N'0', N'0', null, N'F010107', N'F010107', N'1', null, N'月', N'1', N'2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'10', N'3', N'3', N'4', N'0', N'0', null, N'F010108', N'F010108', N'1', null, N'月', N'1', N'2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'11', N'1', N'10001', N'10020', N'0', N'0', null, N'F01', N'F01', N'1', null, N'月', N'1', N'-2', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'12', N'2', N'10002', N'10019', N'0', N'0', null, N'F0101', N'F0101', N'1', null, N'月', N'1', N'11', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'13', N'3', N'10017', N'10018', N'0', N'0', null, N'F010101', N'F010101', N'1', null, N'月', N'1', N'12', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'14', N'3', N'10015', N'10016', N'0', N'0', null, N'F010102', N'F010102', N'1', null, N'月', N'1', N'12', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'15', N'3', N'10013', N'10014', N'0', N'0', null, N'F010103', N'F010103', N'1', null, N'月', N'1', N'12', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'16', N'3', N'10011', N'10012', N'0', N'0', null, N'F010104', N'F010104', N'1', null, N'月', N'1', N'12', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'17', N'3', N'10009', N'10010', N'0', N'0', null, N'F010105', N'F010105', N'1', null, N'月', N'1', N'12', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'18', N'3', N'10007', N'10008', N'0', N'0', null, N'F010106', N'F010106', N'1', null, N'月', N'1', N'12', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'19', N'3', N'10005', N'10006', N'0', N'0', null, N'F010107', N'F010107', N'1', null, N'月', N'1', N'12', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'20', N'3', N'10003', N'10004', N'0', N'0', null, N'F010108', N'F010108', N'1', null, N'月', N'1', N'12', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'21', N'1', N'20001', N'20020', N'0', N'0', null, N'F01', N'F01', N'1', null, N'月', N'1', N'-3', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'22', N'2', N'20002', N'20019', N'0', N'0', null, N'F0101', N'F0101', N'1', null, N'月', N'1', N'21', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'23', N'3', N'20017', N'20018', N'0', N'0', null, N'F010101', N'F010101', N'1', null, N'月', N'1', N'22', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'24', N'3', N'20015', N'20016', N'0', N'0', null, N'F010102', N'F010102', N'1', null, N'月', N'1', N'22', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'25', N'3', N'20013', N'20014', N'0', N'0', null, N'F010103', N'F010103', N'1', null, N'月', N'1', N'22', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'26', N'3', N'20011', N'20012', N'0', N'0', null, N'F010104', N'F010104', N'1', null, N'月', N'1', N'22', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'27', N'3', N'20009', N'20010', N'0', N'0', null, N'F010105', N'F010105', N'1', null, N'月', N'1', N'22', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'28', N'3', N'20007', N'20008', N'0', N'0', null, N'F010106', N'F010106', N'1', null, N'月', N'1', N'22', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'29', N'3', N'20005', N'20006', N'0', N'0', null, N'F010107', N'F010107', N'1', null, N'月', N'1', N'22', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'30', N'3', N'20003', N'20004', N'0', N'0', null, N'F010108', N'F010108', N'1', null, N'月', N'1', N'22', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'31', N'1', N'30001', N'30020', N'0', N'0', null, N'F01', N'F01', N'1', null, N'月', N'1', N'-4', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'32', N'2', N'30002', N'30019', N'0', N'0', null, N'F0101', N'F0101', N'1', null, N'月', N'1', N'31', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'33', N'3', N'30017', N'30018', N'0', N'0', null, N'F010101', N'F010101', N'1', null, N'月', N'1', N'32', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'34', N'3', N'30015', N'30016', N'0', N'0', null, N'F010102', N'F010102', N'1', null, N'月', N'1', N'32', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'35', N'3', N'30013', N'30014', N'0', N'0', null, N'F010103', N'F010103', N'1', null, N'月', N'1', N'32', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'36', N'3', N'30011', N'30012', N'0', N'0', null, N'F010104', N'F010104', N'1', null, N'月', N'1', N'32', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'37', N'3', N'30009', N'30010', N'0', N'0', null, N'F010105', N'F010105', N'1', null, N'月', N'1', N'32', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'38', N'3', N'30007', N'30008', N'0', N'0', null, N'F010106', N'F010106', N'1', null, N'月', N'1', N'32', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'39', N'3', N'30005', N'30006', N'0', N'0', null, N'F010107', N'F010107', N'1', null, N'月', N'1', N'32', null, null, null, null);
GO
INSERT INTO [dbo].[KH_KEYDEPOT] ([id], [level], [lft], [rgt], [disabled], [dOrder], [inputType], [keyCode], [keyName], [leaf], [pattern], [periodType], [isUsed], [parent_id], [actualFormula_id], [execDept_id], [scoreFormula_id], [targetFormula_id]) VALUES (N'40', N'3', N'30003', N'30004', N'0', N'0', null, N'F010108', N'F010108', N'1', null, N'月', N'1', N'32', null, null, null, null);
GO
SET IDENTITY_INSERT [dbo].[KH_KEYDEPOT] OFF
GO

-- ----------------------------
-- Indexes structure for table KH_KEYDEPOT
-- ----------------------------

-- ----------------------------
-- Primary Key structure for table [dbo].[KH_KEYDEPOT]
-- ----------------------------
ALTER TABLE [dbo].[KH_KEYDEPOT] ADD PRIMARY KEY ([id])
GO

-- ----------------------------
-- Foreign Key structure for table [dbo].[KH_KEYDEPOT]
-- ----------------------------
ALTER TABLE [dbo].[KH_KEYDEPOT] ADD FOREIGN KEY ([parent_id]) REFERENCES [dbo].[KH_KEYDEPOT] ([id]) ON DELETE NO ACTION ON UPDATE NO ACTION
GO
