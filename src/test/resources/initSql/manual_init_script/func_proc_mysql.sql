

DELIMITER $$
DROP FUNCTION IF EXISTS func_getpycodes;
CREATE FUNCTION func_getpycodes( words   varchar(255)) RETURNS varchar(255) CHARSET utf8
BEGIN   
  declare wordspy varchar(255) ;
  declare fpy char(1);   
  declare pc char(1);   
  declare cc char(4);   
  
	set @wordspy='';
	
	while CHAR_LENGTH(words)>0 do
	  set @fpy = UPPER(left(words,1));   
	  set @pc = (CONVERT(@fpy   USING   gbk));   
	  set @cc = hex(@pc);   
	  if @cc >= "8140" and @cc <="FEA0" then  
	    begin   
	      select PY from t_hz2py where hz>=@pc limit 1 into @fpy;
	    end;   
	  end   if;  
	set @wordspy = CONCAT(@wordspy,@fpy);
	set words=right(words,CHAR_LENGTH(words)-1);
	end while;
  Return    @wordspy;  
END


UPDATE t_chargeitem  set pyCode = func_getpycodes(chargeItem);

UPDATE t_chargetype  set cnCode = func_getpycodes(chargeType);

UPDATE t_costitem set cnCode = func_getpycodes(costitem);


UPDATE t_department set cnCode = func_getpycodes(name);