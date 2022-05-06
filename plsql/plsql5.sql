create or replace package autohandler is
type autorow is record (rsz varchar(6), szin varchar(100));
function get_car_by_evjarat(evjarat number) return autorow;
function get_avg_car_evjarat return number;
procedure add_car(rsz char, szin varchar2, tipus varchar2, evjarat number, ar number);
procedure 

create or replace package body autohandler is
function get_car_by_evjarat(evjaraat number) return autorow is
begin
end;
function get_avg_car_evjarat return number is
    avg_evjarat number;
begin
    select avg(evjarat) into avg_evjarat from auto;
    return avg_evjarat;
end;

procedure add_car(rsz char, szin varchar2, tipus varchar2, evjarat number, ar number) is
begin

end;

procedure delete_car(rsz char) is
begin
    delete from auto where rsz = rsz;
end;



begin
  autohandler.delete_car
end;


--TRIGGER
--                                mikor fusson le     milyen hívásra fusson le Or-al lehet több is
create or replace trigger nev before / after / instead of insert or update or delete ON auto
for each row when 
--minden sorra külön


create or replace trigger delete_on_auto after delete on auto for each row
declare 
msg char(50) := concat(:old.rsz, ' kocsi törölve');
begin
    dbms_output.put_line(msg);
end;