create or replace
procedure PRO_OT01(
	PI_TEXT		IN	varchar2
	)
is
begin
	dbms_output.put_line(PI_TEXT);
end PRO_OT01;