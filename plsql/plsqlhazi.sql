--1. feladat

DECLARE
    a number(3) :=10;
    b number(3) :=8;
begin
  if a = b then
    dbms_output.put_line('A ' || a || ' és a ' || b || ' egyenlőek');
  elsif a > b then
     dbms_output.put_line('A ' || a || ' a nagyobb szam');
     else
    dbms_output.put_line('A ' || b || ' a nagyobb szam');
  end if;
end;


--2. feladat

DECLARE
    minszam number;
    maxszam number;
    szamom number;
begin
    minszam:=:minszam;
    maxszam:=:maxszam;
    szamom:=:szamom;
    dbms_output.put_line('Tartomány alsó határa: ' || minszam);
    dbms_output.put_line('Tartomány felső határa: ' || maxszam);
    dbms_output.put_line('En szamom: '|| szamom);
    
    if szamom > minszam and szamom < maxszam then
       dbms_output.put_line('A szamod benne van a tartományban');
    else
      dbms_output.put_line('A szamod nincs benne a tartományban');
    end if;
end;

--case szerkezet

DECLARE
    a number(3) := 1;
    b number(3) :=2;
begin
  case 
    when a>b then   
    dbms_output.put_line('A'); 
    when b>a then
    dbms_output.put_line('B');
    else
    dbms_output.put_line('A=B');  
  end CASE;
end;

--3. feladat

DECLARE
    a number(3);
    b number(3);
    c number(3);
begin
  a:=:a;
  b:=:b;
  c:=:c;
  if a+b > c and a+c > b and b+c > a then
    dbms_output.put_line('Lehet háromszög');  
  else
    dbms_output.put_line('Nemlehet háromszög');  
  end if;
end;

--4.feladat

DECLARE
    an number(4);
    bn number(4);
    cn number(4);
    sn number(4);
    eredmeny number(8);
begin
  an:=:an;
  bn:=:bn;
  cn:=:cn;
  if an+bn > cn and an+cn > bn and bn+cn > an then
    sn:=(an+bn+cn)/2;
    eredmeny:=SQRT(sn*(sn-an)*(sn-bn)*(sn-cn));
    dbms_output.put_line('Alakalmas adatok. területe pedig: ' || eredmeny);  
  else
    dbms_output.put_line('Nemlehet háromszög');  
  end if;
end;


--5.feladat


DECLARE
 n number := 10;
begin
  for i in 1..n loop
    dbms_output.put_line(i);  
  end loop;
end;

--6.feladat fibo

DECLARE 
  fiboDb number := 20;
  fibnum number := 1;
  fibprev number := 0;
  fibclone number;
begin
  for i in 1..fiboDb loop
    dbms_output.put_line(i || '.fibonacci-szam: ' || fibnum);
    fibclone:= fibnum + fibprev;  
    fibprev:= fibnum;
    fibnum:=fibclone;
  end loop;
end;

--7.feladat

DECLARE 
    szam number;
    sqrtszam number;
    primc number := 0;
    osztas number;
begin
    szam:=:szam;
    if szam = 1 then
    dbms_output.put_line('nem prim szam');
    else
        sqrtszam := SQRT(szam);
        for i in 2..sqrtszam loop
          osztas := REMAINDER(szam,i);
          if  osztas = 0 then
            primc:=primc+1;
          end if;
        end loop; 
    end if;
    if primc > 0 then
      dbms_output.put_line('nem prim szam');
      else
      dbms_output.put_line('prim szam: ' || primc);
    end if;
end;