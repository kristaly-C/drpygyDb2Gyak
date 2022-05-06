--TRIGGEREK

create table naplo (muvelet char(50),datum date, felhasznalo char(20));

create or replace trigger auto_del after delete on auto
begin
  insert into naplo values(('delete', :old.rsz), sysdate, user);
end;

select * from auto;


delete from auto where szin = 'zöld';

select * from naplo;

insert into values auto(210210,'mercedes','sarga',2013,1000000);
insert into values auto(238921,'audi','sarga',2022,732100);



create or replace trigger auto_del after delete on auto for each row
begin
  insert into naplo values(('delete', :old.rsz), sysdate, user);
end;

delete from auto where szin='sarga';

select * from naplo;


create or replace trigger auto_del after delete on auto for each row when (old.tipus = 'mercedes')
begin
  insert into naplo values(('delete', :old.rsz), sysdate, user);
end;



--sequence


create or replace rsz_n start with 100;

begin 
    dbms_output.put_line(rsz_n.nextval);
end;


create or replace trigger auto_key before insert or update of rsz on auto for each row
declare
type a_rszt is record(
    betuk char(3),
    szamok char(3)
);
rendszam a_rszt;
begin
  select SUBSRT(:new.rsz, 1,3) into rendszam.betuk from DUAL;
  select SUBSRT(:new.rsz, 4,3) into rendszam.szamok from DUAL;
  if rendszam.szamok = 100 then
    rendszam.szamok := rsz.nextval;
  end if;
  :new.rsz := rendszam.betuk || rendszam.szamok;
  dbms_output.put_line('felvitt rendszam' || :new.rsz);
end;


insert into auto values('abd210','mazda','ezüst',2011,2101100);
insert into auto values('abd100','mazda','ezüst',2011,2101100);



-- trigger művelet szerinti feladatvégzés

create or replace trigger auto_t before insert or update of ar on auto for each row
begin
  if deleting then 
  dbms_output.put_line('delete');
  elsif updating then 
    dbms_output.put_line('update');
  end if;
end;

select * from auto;

update auto set ar=100 where szin='zöld';

delete auto where evjarat < 2010


create or update view autovive as select rsz, to_char(sysdate,'yyyy')-evjarat kor from auto;

create or replace trigger autotorles instead of delete on autovive
begin
    raise_application_error(-21001,'Nem szabad');
end;


-- v-array

create or replace procedure test1 as
type varray_t is varray(4) of varchar2(100);
tomb varray_t := varray_t();
begin
  tomb(1) := 'alma';
  tomb(2) := 'kókusz';
  tomb(3) := 'banán';
  tomb(4) := 'cseresznye';
  for i in tomb.size loop
  dbms_output.put_line(tomb(i));  
  end loop
  
end;

begin
  test1;
end;



create or replace package My_type is
TYPE alma is table of varchar2(20) index by string(100);
Function init return alma;
end;

create or replace package body My_type is
Function init return alma is
    ret alma;
    begin
        ret('a') := 'asd';
        ret('b') := 'qwe';
        return ret;
    end;
end;

declare 
    v My_type.alma := My_type.init;
begin
  dbms_output.put_line(v('b'));
end;


-- v-array elemtörlés