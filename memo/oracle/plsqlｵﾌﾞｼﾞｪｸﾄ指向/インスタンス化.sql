SET SERVEROUTPUT ON
DECLARE
	INS_OT01 OBJ_OT01;
	INS_OT02 OBJ_OT01;
BEGIN
	INS_OT01 := OBJ_OT01('テキスト1');
	INS_OT02 := OBJ_OT01('テキスト2');
	
	dbms_output.put_line(INS_OT01.text);
	dbms_output.put_line(INS_OT02.text);
END;