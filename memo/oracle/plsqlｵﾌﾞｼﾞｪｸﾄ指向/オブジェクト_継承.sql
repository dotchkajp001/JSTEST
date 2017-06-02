create or replace
TYPE OBJ_MAP02 AS OBJECT
(
	no		number,
	name	varchar2(100),
	member	function display return varchar2,
	NOT FINAL member function display2 return varchar2
) not final;

create or replace
TYPE BODY OBJ_MAP02 AS
	member function display return varchar2
	is
	begin
		return 'no：' || no || ' name：' || name;
	end display;
	
	member function display2 return varchar2
	is
	begin
		return 'display2';
	end display2;
end;


create or replace
TYPE OBJ_MAP02_KO UNDER OBJ_MAP02
(
	bunrui varchar2(100),
	overriding member function display2 return varchar2
)

create or replace
TYPE BODY OBJ_MAP02_KO AS
	overriding member function display2 return varchar2
	is
	begin
		return 'no：' || no || ' name：' || name || ' BUNRUI:' || bunrui;
	end display2;
end;