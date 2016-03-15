
--两行注释掉的语句是为SqlServer准备的，当目标数据库为SqlServer时请解除这两句上的注释
--SET IDENTITY_INSERT t_dictionary ON
INSERT INTO t_datasourcetype (dataSourceTypeId,datasource_type_name,helperclass_name,is_needfile,fileType) VALUES (1, 'Excel File ', 'com.huge.ecis.inter.helper.ExcelImport', 1,'xls');
INSERT INTO t_datasourcetype (dataSourceTypeId,datasource_type_name,helperclass_name,is_needfile,fileType) VALUES (2, 'Oracle ', 'com.huge.ecis.inter.helper.OracleImport', 0,null);
INSERT INTO t_datasourcetype (dataSourceTypeId,datasource_type_name,helperclass_name,is_needfile,fileType) VALUES (3, 'SQL Server2005 ', 'com.huge.ecis.inter.helper.SQLServer2005Import', 0,null);
--SET IDENTITY_INSERT t_dictionary OFF	