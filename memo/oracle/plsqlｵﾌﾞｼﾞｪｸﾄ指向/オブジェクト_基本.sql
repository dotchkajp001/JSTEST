create or replace
TYPE OBJ_MAP01 AS OBJECT
(
	no		number,
	name	varchar2(100),
	member	function display return varchar2
);

create or replace
TYPE BODY OBJ_MAP01 AS
	member function display return varchar2
	is
	begin
		return 'noÅF' || no || ' nameÅF' || name;
	end display;
end;