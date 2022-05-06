--nevesített függvény/eljárás

CREATE OR REPLACE PROCEDURE pauto IS  
a auto%rowtype;
begin
  select *
    into a
    from auto
   where szin ='feher';
   dbms_output.put_line('auto: ' || a.szin || a.tipus);
end;

begin
  pauto;        --így kell futtatni 
end;




CREATE OR REPLACE PROCEDURE autoszin(AUTO_SZIN in VARCHAR2) IS  
a auto%rowtype;
begin
    select * into a from auto where szin = AUTO_SZIN;
    exception
      when too_many_rows then
        dbms_output.put_line('TÚL SOK SOR');
        when no_data_found then 
        dbms_output.put_line('NINCS ADAT');
        
end;

--autok árának alsó határát paraméterként
--autók árának felsó határát paraméterként
--autók új árát paraméterként
--in out mennyit kellene módosítani

CREATE OR REPLACE PROCEDURE arazo(ALSOAR in number, FELSOAR in number, UJAR in number, preferealmodositasok in out number) is
begin
  update auto set ar=UJAR where ar BETWEEN ALSOAR AND FELSOAR;
  if sql%rowcount=preferealmodositasok then
  dbms_output.put_line('jo')
  else
    preferealmodositasok:= sql%rowcount;
  end if;
end;

declare
pref number(2):=2;
begin
  arazo(10000,200000,100000,pref);
  dbms_output.put_line('valós változások: ' || pref);
end;


--speciálisabb adattípusok pl tömbök


create or replace procedure tomb(mini out integer, maxi out integer) is
    type tombtipus is varray(10) of integer;
    elemek tombtipus = elemek();
begin
  l_seed:= tochar(systimestamp,'YYYYDDMMHH');
  for i in 1..10 loop
      elemek.extend(1);
    select dbms_random.value(-100,100) into elemek(i) from dual;
    dbms_output.put_line(i || '. elem: ' || elemek(i));
  end loop

    mini:=elemek(1);
    maxi:=elemek(1);

    for i in 1..10 loop
      if elemek(i) > maxi then
        maxi:= elemek(i);
      end if;
      if elemek(i)< mini then
        mini:= elemek(i);
      end if;
    end loop

end;

declare
mini integer;
maxi integer;
begin
  tomb(mini,maxi);
end;



create or replace function faktorialis(n in number) return number is 
    fakt number(8) := 1;
begin
    for i in 1..n loop
      fakt:=fakt*i;
    end loop
  return fakt;
end;

select faktorialis(10) from dual;

declare
f number(10);
begin
  f:=faktorialis(4);
  dbms_output.put_line(f);
end;

--tárolt eljárás törlése

drop function faktorialis;


--tárolt eljárások lekérdezése

select object_name from user_objects where object_type like 'procedure' or object_type like 'FUNCTION';

--feladat bekerni egy szamot és hozzadni +2 -t

create or replace hozzaad(n in number ) return number is
begin
  n:=n+2;
  return n;
end;

declare
 vissza number(10);
begin
    vissza = hozzaad(10);
    dbms_output.put_line(vissza);
end;