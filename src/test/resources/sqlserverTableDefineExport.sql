SELECT 
   
    ���   = a.colorder,
    �ֶ���     = a.name,
   �ֶ�˵��   = isnull(g.[value],''),
    ��ʶ       = case when COLUMNPROPERTY( a.id,a.name,'IsIdentity')=1 then '��'else '' end,
    ����       = case when exists(SELECT 1 FROM sysobjects where xtype='PK' and parent_obj=a.id and name in (
                     SELECT name FROM sysindexes WHERE indid in( SELECT indid FROM sysindexkeys WHERE id = a.id AND colid=a.colid))) then '��' else '' end,
    �����     = case when a.isnullable=1 then '��'else '' end,
    Ĭ��ֵ     = isnull(e.text,'') ,
	����       = b.name,
    ռ���ֽ��� = a.length,
    ����       = COLUMNPROPERTY(a.id,a.name,'PRECISION'),
    С��λ��   = isnull(COLUMNPROPERTY(a.id,a.name,'Scale'),0)

 
FROM 
    syscolumns a
left join 
    systypes b 
on 
    a.xusertype=b.xusertype
inner join 
    sysobjects d 
on 
    a.id=d.id  and d.xtype='U' and  d.name<>'dtproperties'
left join 
    syscomments e 
on 
    a.cdefault=e.id
left join 
sys.extended_properties   g 
on 
    a.id=g.major_id and a.colid=g.minor_id  
left join 

sys.extended_properties f
on 
    d.id=f.major_id and f.minor_id=0
where 
    d.name='t_period'    --���ֻ��ѯָ����,���ϴ�����
order by 
    a.id,a.colorder