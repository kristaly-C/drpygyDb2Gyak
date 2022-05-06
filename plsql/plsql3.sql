create table auto(
    rsz varchar(6) primary key,
    tipus char(20) not null,
    szin char(10) default 'fehér',
    evjarat number(4),
    ar number(8) check(ar>0)
);

insert into auto values('AAD421','Mazda','zöld',2008,1200000),
    ('ACD843','Mazda','kék',2006,600000),
    ('KLA125','Mercedes','kék',2013,1900000),
    ('KKL931','Skoda','ezüst',2018,5000000);


--CURSOR használat

declare
    a auto%rowtype;
    szin auto.szin%type:='piros';
    CURSOR sauto is select * from auto;
begin
    open sauto;
    loop
      fetch sauto into a;
      exit when sauto%notfound;
      dbms_output.put_line(a.rsz || ' ' || a.tipus || ' ' || a.szin);
    end loop;
end;

-- implicit

declare
    a auto%rowtype;
begin
  for a in (select * from auto) loop
    dbms_output.put_line(a.rsz || ' ' || a.tipus || ' ' || a.szin);
  end loop
end;

-- explicit értékadással

declare 
    CURSOR pauto (sz char) is select * from auto where szin = sz;
    a auto%rowtype;
begin
    open pauto('piros');
      loop
        fetch pauto into a;
        exit when pauto%notfound;
        dbms_output.put_line(a.rsz || ' ' || a.tipus || ' ' || a.szin);
      end loop  
end;



--implicit értékadással

declare
    a auto%rowtype;
    sz auto.szin%type;
begin
    sz:=:sz;
  for a in (select * from auto where szin=sz)loop
    dbms_output.put_line(a.rsz || ' ' || a.tipus || ' ' || a.szin);
  end loop
end;


--lekérdezni az autókat és kiiratni hogy hány évesek

declare
  CURSOR pauto is select * from auto;
  a auto%rowtype;
begin
  open pauto;
  loop
    fetch pauto into a;
    exit when pauto%notfound;
    dbms_output.put_line(a.rsz || ' : ' || TO_CHAR(SYSDATE,'YYYY') - a.evjarat);
  end loop
end;

--

declare 
    a auto%rowtype;
begin
  for a in (select * from auto) loop
    dbms_output.put_line(a.rsz || ' : ' || TO_CHAR(SYSDATE,'YYYY') - a.evjarat);
  end loop
end;

--növelni az autók árát 10%-kal ha fiatalabbak 12 évnél


declare
  ev auto.evjarat%type;
  szorzo number;
begin
    szorzo:=:szorzo;
  update auto set auto.ar = auto.ar * szorzo where (TO_CHAR(SYSDATE,'YYYY') - auto.evjarat) < ev ;
end;

--cursoros tanáros megoldás

declare
    CURSOR pauto is select * from auto for update of ar;
a auto%rowtype;
begin
  open pauto;
  loop
    fetch pauto into a;
    exit when pauto%notfound;
    if to_char(sysdate,'YYYY')-a.evjarat < 16 then
        update auto
           set ar=ar*1.1
         where current of pauto;      
    else
      
    end if;
  end loop
end;


--hibakezelés

declare
    a auto%rowtype;
begin
    select * into a from auto where rsz='1';
exception
  when no_data_found then
  dbms_output.put_line('Nincs ilyen')
    ;
end;

declare
    e EXCEPTION;
    x number;
begin
    if :x<0 then
        RAISE e;
    end if;
    dbms_output.put_line('Vége');
exception
  when e then
  dbms_output.put_line('Nincs ilyen');
end;

--tárolt eljárások

CREATE OR REPLACE PROCEDURE proba IS
    a auto%rowtype;
begin
  select * into a from auto where szin='feher';
  dbms_output.put_line(a.rsz || '' || a.szin)
    exception
    when too_many_rows then
dbms_output.put_line('sok adat');
    when no_data_found then
        dbms_output.put_line('nincs adat');
end;

begin
  proba();
end;


CREATE OR REPLACE PROCEDURE oregauto(hatar IN number, rek_szam OUT number) IS
    zero_record exception;
begin
  update auto set ar=ar*0.8 where to_char(sysdate,'YYYY')-auto.evjarat > hatar;
  rek_szam:=sql%rowcount;
  if condition then
    raise zero_record;
  else if sql%found then
    dbms_output.put_line('Siker');
  end if;
  exception
    when zero_record then
      dbms_output.put_line('hiba');
end;

declare 
    rszam number(2);
begin
  oregauto(1,rszan);
  dbms_output.put_line('Modosítva:' || rszam);
end;