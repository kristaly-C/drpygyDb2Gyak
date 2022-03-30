DECLARE
    vezeteknev VARCHAR2(100);
    keresztnev VARCHAR2(100);
begin
    keresztnev:=:keresztnev;
    vezeteknev:=:vezeteknev;
    for i in 1..3 loop
        if keresztnev like 'sín' then
            dbms_output.put_line('Ez nem rendes név');
          else
            dbms_output.put_line(vezeteknev || ' ' || keresztnev);
        end if;
        
    end loop
  
end;



--valami rendes feladat

select * from car

alter table car add(kor number(2));
alter table car add(year number(4));

DECLARE
    new_year number(2);
    color varchar2(10);
begin
  update car
     set year=:new_year
   where color=:color;
end;

--táblakezelés

DECLARE

begin
  update car set kor=year-to_number(sysdate,'9999'); --ezt le kell futtatni ha van rá idő
  update car set kor=year-to_char(sysdate,'YYYY');
end;


--valami

DECLARE
    c constant car.color%type:='white';
begin
  update car set manufacturer='Suzuki' where color=c;
end;

--karakter tömbben lévő parancs futtatása

alter table car add(temp number(2));

DECLARE
    stmt char(100):='alter table car drop(temp)';
begin
  execute immediate stmt;
end;

--insertálás ennek így nincs sok értelme 

DECLARE
    new_row car%rowtype;
begin
  new_row.id:=14;
  new_row.manufacturer:='Seat';
  new_row.color:='white';
  new_row.price:=100;
  insert into car(id,manufacturer,color,price) values (new_row.id,new_row.manufacturer,new_row.color,new_row.price);
end;


--lekérdezés rowtype segítségével

DECLARE
    c car%rowtype;
    c_id car.id%type;
begin
  select * into c from car where id=c_id;
  dbms_output.put_line(c.id || ' ' || c.manufacturer || ' ' || c.color || ' ' || c.price);
end;