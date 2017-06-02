drop type obj03_plus;
drop type obj03_multi;
drop type obj03;

create or replace
type OBJ03 as object
(
	val1 number,
	val2 number,
	not final member function calc return number,
	member procedure display
) not final;
/

create or replace
type body OBJ03
AS
	not final member function calc return number
	is
	begin
		return 0;
	end calc;
	
	not final member procedure display
	is
	begin
		dbms_output.put_line('val1:' || val1 || ' val2:' || val2);
	end display;
end;
/

create or replace
type OBJ03_PLUS under OBJ03
(
	overriding member function calc return number
);
/

create or replace
type body OBJ03_PLUS as
	overriding member function calc return number
	is
	begin
		return val1 + val2;
	end calc;
end;
/

create or replace
type OBJ03_MULTI UNDER OBJ03
(
	overriding member function calc return number
);
/
create or replace
type body OBJ03_MULTI
as
	overriding member function calc return number
	is
	begin
		return val1 * val2;
	end calc;
end;
/


set serveroutput on
declare
	OBJ	OBJ03;
begin
	if 1 = 2 then
		OBJ	:= OBJ03_PLUS(50,50);
	else
		OBJ	:= OBJ03_MULTI(50,50);
	end if;
	--OBJ := OBJ03(50,50);
	OBJ.VAL1 := 100;
	OBJ.display;
	dbms_output.put_line(OBJ.VAL1);
	dbms_output.put_line(OBJ.CALC);
end;