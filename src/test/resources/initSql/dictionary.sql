--两行注释掉的语句是为SqlServer准备的，当目标数据库为SqlServer时请解除这两句上的注释
--SET IDENTITY_INSERT t_dictionary ON
--性别
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (1,'sex','性别');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('男','男',1,1);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('女','女',2,1);
	
	
--城乡
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (2,'urban','城乡');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('城市','城市',1,2);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('农村','农村',2,2);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其他','其他',3,2);

	
--出生地
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (3,'birthplace','出生地');	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('北京','北京',1,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('上海','上海',2,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('天津','天津',3,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('重庆','重庆',4,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('辽宁','辽宁',5,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('广东','广东',6,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('浙江','浙江',7,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('江苏','江苏',8,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('山东','山东',9,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('四川','四川',10,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('黑龙江','黑龙江',11,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('湖南','湖南',12,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('湖北','湖北',13,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('福建','福建',14,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('陕西','陕西',15,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('河南','河南',16,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('安徽','安徽',17,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('河北','河北',18,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('吉林','吉林',19,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('江西','江西',20,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('广西','广西',21,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('山西','山西',22,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('云南','云南',23,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('内蒙古','内蒙古',24,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('甘肃','甘肃',25,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('贵州','贵州',26,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('新疆','新疆',27,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('宁夏','宁夏',28,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('海南','海南',29,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('青海','青海',30,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('西藏','西藏',31,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('港澳台','港澳台',32,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('海外','海外',33,3);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其它','其它',34,3);

--岗位
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (4,'postType','岗位');	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('医技人员','医技人员',1,4);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('医疗人员','医疗人员',2,4);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('护理人员','护理人员',3,4);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('工程技术人员','工程技术人员',4,4);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('后勤人员','后勤人员',5,4);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其他人员','其他人员',6,4);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('药剂人员','药剂人员',7,4);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('行政人员','行政人员',8,4);
	

	
	
--岗位职务
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (5,'postDuty','岗位职务');		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('院长','院长',1,5);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('副院长','副院长',2,5);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('政委','政委',3,5);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('副政委','副政委',4,5);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主任','主任',5,5);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('副主任','副主任',6,5);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('护士长','护士长',7,5);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('科员','科员',8,5);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('兼核算员','兼核算员',9,5);

--密级
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (6,'secretLevel','密级');	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('内部','内部',1,6);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('公开','公开',2,6);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('秘密','秘密',3,6);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('机密','机密',4,6);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('绝密','绝密',5,6);

--民族
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (7,'nation','民族');	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('汉族','汉族',1,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('蒙古族','蒙古族',2,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('回族','回族',3,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('藏族','藏族',4,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('维吾尔族','维吾尔族',5,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('苗族','苗族',6,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('彝族','彝族',7,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('壮族','壮族',8,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('布依族','布依族',9,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('朝鲜族','朝鲜族',10,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('满族','满族',11,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('侗族','侗族',12,7);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其他','其他',13,7);
	
--年级
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (8,'grade','年级');	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('大一','大一',1,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('大二','大二',2,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('大三','大三',3,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('大四','大四',4,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高一','高一',5,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高二','高二',6,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高三','高三',7,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('初一','初一',8,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('初二','初二',9,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('初三','初三',10,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('一年级','一年级',11,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('二年级','二年级',12,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('三年级','三年级',13,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('四年级','四年级',14,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('五年级','五年级',15,8);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其他','其他',16,8);
	
--学历
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (9,'education','学历');		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('中专','中专',1,9);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('大专','大专',2,9);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('本科','本科',3,9);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('研究生','研究生',4,9);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('博士','博士',5,9);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('博士后','博士后',6,9);
	
--学历
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (10,'jobTitle','职称');			
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主任医师','主任医师',1,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('副主任医师','副主任医师',2,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主治医师','主治医师',3,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('医师','医师',4,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('医士','医士',5,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助医','助医',6,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主任药师','主任药师',7,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('副主任药师','副主任药师',8,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主管药师','主管药师',9,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('药剂师','药剂师',10,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('药剂士','药剂士',11,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('药剂员','药剂员',12,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主任护师','主任护师',13,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('副主任护师','副主任护师',14,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主管护师','主管护师',15,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('护师','护师',16,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('护士','护士',17,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助护','助护',18,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主任技师','主任技师',19,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('副主任技师','副主任技师',20,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主管技师','主管技师',21,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('技师','技师',22,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('技士','技士',23,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高级工程师','高级工程师',24,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('工程师','工程师',25,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助理工程师','助理工程师',26,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('技术员','技术员',27,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高级政工师','高级政工师',28,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('政工师','政工师',29,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高级统计师','高级统计师',30,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('统计师','统计师',31,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助理统计师','助理统计师',32,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('统计员','统计员',33,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高级经济师','高级经济师',34,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('经济师','经济师',35,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助理经济师','助理经济师',36,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('经济员','经济员',37,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高级会计师','高级会计师',38,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('会计师','会计师',39,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助理会计师','助理会计师',40,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('会计员','会计员',41,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('研究馆员','研究馆员',42,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('副研究馆员','副研究馆员',43,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('馆员','馆员',44,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助理馆员','助理馆员',45,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助产师','助产师',46,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助产士','助产士',47,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('高级审计师','高级审计师',48,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('助理审计师','助理审计师',49,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('审计师','审计师',50,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('审计员','审计员',51,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('检验师','检验师',52,10);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('主管检验师','主管检验师',53,10);	
	
--职工类别
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (11,'empType','职工类别');			

	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('在职','在职',1,11);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('合同','合同',2,11);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('离休','离休',3,11);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('退休','退休',4,11);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('临时','临时',5,11);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('虚拟','虚拟',6,11);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('院外','院外',7,11);
	
--待办工作类型
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (12,'waitJobType','待办工作类型');		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('报告审查','报告审查',1,12);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('单据审批','单据审批',2,12);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('计划审批','计划审批',3,12);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('事务提醒','事务提醒',4,12);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其它事务','其它事务',5,12);

--单位性质
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (13,'orgNature','单位性质');			
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('国营企业','国营企业',1,13);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('集体企业','集体企业',2,13);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('民营企业','民营企业',3,13);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('合资企业','合资企业',4,13);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('外资企业','外资企业',5,13);	

--收入
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (14,'income','收入');		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('1000元以内','1000元以内',1,14);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('1000元--3000元','1000元--3000元',2,14);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('3000元--5000元','3000元--5000元',3,14);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('5000元--10000元','5000元--10000元',4,14);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('10000元--30000元','10000元--30000元',5,14);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('30000元--50000元','30000元--50000元',6,14);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('50000元--100000元','50000元--100000元',7,14);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('100000元以上','100000元以上',8,14);	

--职业
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (15,'career','职业');			
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('学生','学生',1,15);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('专业人士','专业人士',2,15);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('经理','经理',3,15);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('公务员','公务员',4,15);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('职员','职员',5,15);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('私营主','私营主',6,15);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('待业','待业',7,15);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('退休','退休',8,15);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其它','其它',9,15);
	
--通讯录分类
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (16,'ContactsGroup','通讯录分类');		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('家人','家人',1,16);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('朋友','朋友',2,16);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('同学','同学',3,16);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('同事','同事',4,16);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其它','其它',5,16);

--模板分类
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (17,'templateType','模板分类');		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('数据采集','数据采集',1,17);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('工作表格','工作表格',2,17);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其它','其它',3,17);
	
--科室类别
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (18,'deptClass','科室类别');		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('公用','公用',1,18);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('管理','管理',2,18);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('医辅','医辅',3,18);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('科研','科研',4,18);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('医技','医技',5,18);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('医疗', '医疗',6,18);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('未纳入','未纳入',7,18);

--住院门诊	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (19,'outin','住院门诊');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('住院','住院',1,19);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('门诊','门诊',2,19);

--专业系别	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (20,'subClass','专业系别');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('内科','内科',1,20);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('外科','外科',2,20);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('妇产科','妇产科',3,20);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('儿科','儿科',4,20);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('中医科','中医科',5,20);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('五官科', '五官科',6,20);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其他','其他',7,20);

--是否
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (21,'yesno','是否');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('是','是',1,21);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('否','否',2,21);
	
--是否
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (22,'radioyesno','Radio是否');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('true','是',1,22);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('false','否',2,22);	

INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (23,'radioOpenClose','Radio开闭');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('true','开',1,23);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('false','关',2,23);		
	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (24,'dataCollectionStepType','数据采集步骤类型');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('remote_pre_process','远程预处理',0,24);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('pre_process','预处理',1,24);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('collect','采集',2,24);		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('verify','校验',3,24);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('prompt','提示',4,24);	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('import','导入',5,24);
	--INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('store_procedure','存储过程',6,24);	
	--INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('non_transaction','非事务处理',7,24);
	--INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('other','其他',8,24);	
	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (25,'dataCollectionTaskStatus','数据采集任务状态');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('prepared','未处理',1,25);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('completed','成功',2,25);		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('failure','失败',3,25);
	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (26,'medOrLee','医疗/药品');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('医疗','医疗',1,26);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('药品','药品',2,26);	
	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (27,'costItem_behaviour','成本习性');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('固定成本','固定成本',1,27);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('变动成本','变动成本',2,27);	
		
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (28,'search_fieldtype','字段类型');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('String','String',0,28);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Integer','Integer',1,28);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Number','Number',2,28);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Date','Date',3,28);
--	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Time','Time',4,28);
--	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('TimeStamp','TimeStamp',5,28);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Boolean','Boolean',6,28);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Money','Money',7,28);
--	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Image','Image',8,28);
--INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Percent','Percent',9,28);
--INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Link','Link',10,28);

	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (29,'search_edittype','编辑类型');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Label','Label',0,29);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('Input','Input',1,29);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('CheckBox','CheckBox',3,29);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('stringSelect','stringSelect',5,29);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('stringSelectR','stringSelectR',6,29);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('sqlSelect','sqlSelect',7,29);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('sqlSelectR','sqlSelectR',8,29);

INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (30,'search_calctype','计算类型');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('sum','sum',0,30);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('max','max',1,30);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('min','min',2,30);	

INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (31,'searchOption_alignType','对齐方式');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('center','center',0,31);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('left','left',1,31);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('right','right',2,31);	

--是否
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (32,'booleanSel','布尔选择');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('1','真',1,32);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('0','假',2,32);	
--病人类型
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (33,'patientType','病人类型');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('patientTypeOne','重症患者',1,33);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('patientTypeTwo','轻微患者',2,33);	
--病人类型
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (34,'paytype','费用类别');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('paytypeOne','医疗业务成本',1,34);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('paytypeTwo','管理费用',2,34);	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('paytypeThree','待冲财政基金',3,34);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('paytypeFour','待冲科教项目基金',4,34);	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('paytypeFive','医疗成本',5,34);	
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('paytypeSix','医疗全成本',6,34);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('paytypeSeven','医院全成本',7,34);
	
	
--报表类型	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (35,'reportType','报表类型');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('月报','月报',1,35);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('季报','季报',2,35);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('年报','年报',3,35);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('综合报表','综合报表',4,35);		
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('其他','其他',5,35);	
	
INSERT INTO t_dictionary (dictionaryId,code,name) VALUES (36,'searchBarType','查询条显示方式');
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('0','无',1,36);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('1','简单',2,36);
	INSERT INTO t_dictionary_items (value,displayContent,orderNo,dictionary_id) VALUES ('2','完全',3,36);
	
	
--SET IDENTITY_INSERT t_dictionary OFF		

	
	