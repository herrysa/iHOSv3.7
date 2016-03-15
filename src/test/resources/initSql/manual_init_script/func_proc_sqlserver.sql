
if exists (select 1 from dbo.sysobjects where id=object_id(N'func_getpycodes'))
DROP FUNCTION func_getpycodes; 
go

CREATE   function dbo.func_getpycodes(@str nvarchar(255))
returns nvarchar(255)
as
begin
declare @word nchar(1),@PY nvarchar(255)
set @PY=''
while len(@str)>0
begin
set @word=left(@str,1)
--如果非汉字字符，返回原字符
set @PY=@PY+(case when unicode(@word) between 19968 and 19968+20901
then (select top 1 PY from (
select 'A' as PY,N'驁' as word
union all select 'B',N'簿'
union all select 'C',N'錯'
union all select 'D',N'鵽'
union all select 'E',N'樲'
union all select 'F',N'鰒'
union all select 'G',N'腂'
union all select 'H',N'夻'
union all select 'J',N'攈'
union all select 'K',N'穒'
union all select 'L',N'鱳'
union all select 'M',N'旀'
union all select 'N',N'桛'
union all select 'O',N'漚'
union all select 'P',N'曝'
union all select 'Q',N'囕'
union all select 'R',N'鶸'
union all select 'S',N'蜶'
union all select 'T',N'籜'
union all select 'W',N'鶩'
union all select 'X',N'鑂'
union all select 'Y',N'韻'
union all select 'Z',N'咗'
) T 
where word>=@word collate Chinese_PRC_CS_AS_KS_WS 
order by PY ASC) else @word end)
set @str=right(@str,len(@str)-1)
end
return @PY
end

GO

UPDATE t_chargeitem  set pyCode = dbo.func_getpycodes(chargeItem);
GO
UPDATE t_chargetype  set cnCode = dbo.func_getpycodes(chargeType);
GO
UPDATE t_costitem set cnCode = dbo.func_getpycodes(costitem);
GO
UPDATE t_department set cnCode = dbo.func_getpycodes(name);
GO