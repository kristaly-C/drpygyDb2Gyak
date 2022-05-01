package adatb.kristaly.menus;

import adatb.kristaly.domain.Menu;
import adatb.kristaly.domain.Program;
import adatb.kristaly.services.EmberService;
import adatb.kristaly.services.ProgramService;

import java.sql.Connection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class ProgramMenu {

    private Connection conn;
    private Menu almenu = new Menu();


    public ProgramMenu(Connection connection){
        this.conn = connection;
        almenu.addMenuItem("Program létrehozása");
        almenu.addMenuItem("Vendég feliratkoztatás");
        almenu.addMenuItem("Vendég leiratkoztatás");
        almenu.addMenuItem("Programok");
        almenu.addMenuItem("Aktuális programok");
        almenu.addMenuItem("Programre regisztraltak listaja");
        almenu.addBackButton("VISSZA");
    }

    public Menu getMenu(){
        return almenu;
    }

    public void registNewProgram() {
        Scanner sc = new Scanner(System.in);
        int id;
        String name;
        Date date;
        int duration;
        int maxperson;

        System.out.print("Esemény megnevezése: ");
        name = sc.nextLine();
        System.out.print("Adja meg az esemény időpontját ebben a formában(év/hónap(számmal)/nap/óra/perc");
        date = getDateFromUser();
        duration = getProgramDuration("Adja meg milyen a program hosszát ebben a formában(óra:perc): ",date);
        maxperson = pickPlace();

        ProgramService pS = new ProgramService(conn);
        id = pS.pickID();

        Program program = new Program(id,name,date,duration,maxperson);
        pS.addNewProgram(program);
    }

    private int pickPlace() {
        Menu teremM = new Menu();
        teremM.addMenuItem("Terem 1 (50 fő)");
        teremM.addMenuItem("Terem 2 (60 fő)");
        teremM.addMenuItem("Terem 3 (110 fő)");
        teremM.addMenuItem("Terem 4 (160 fő)");
        teremM.drawVisibleMenu();
        switch (teremM.pickItem()){
            case 1 : return 50;
            case 2 : return 60;
            case 3 : return 110;
            case 4 : return 160;
            default: return 50;
        }
    }

    private int getProgramDuration(String question,Date date) {
        boolean okay = false;
        int dayLenght = 1440;
        int startprogram = date.getHours() * 60 + date.getMinutes();
        int maxlenght = dayLenght - startprogram;
        int dur = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("Maximális időtartam: " + (long)maxlenght/60);
            do{
                System.out.print(question);
                try {
                    String hossz = sc.nextLine();
                    String[] arrayOfString = hossz.split(":",2);
                    dur = Integer.valueOf(arrayOfString[0]) * 60 + Integer.valueOf(arrayOfString[1]);
                    if (dur > maxlenght){
                        System.out.println("Nem lehet ilyen hosszú a program");
                    }else {
                        okay = true;
                    }
                }catch (Exception e){
                    System.out.println("Hibas adat");
                }
            }while (!okay);
            return dur;
    }

    private Date getDateFromUser() {
        Scanner sc = new Scanner(System.in);
        Date ldt ;
        boolean exit = false;
        while(!exit)
        try {
            String datum = sc.nextLine();
            String[] arrayOfString = datum.split("/",5);
            if (Integer.valueOf(arrayOfString[3]) < 8){
                System.out.println("Reggel 8 előtt nem kezdődhet program!");
            }else {
                ldt = new Date(Integer.valueOf(arrayOfString[0]),Integer.valueOf(arrayOfString[1]),Integer.valueOf(arrayOfString[2]),Integer.valueOf(arrayOfString[3]),Integer.valueOf(arrayOfString[4]));
                return ldt;
            }


        }catch (Exception e)
        {
            System.out.println("Hibás adatok");
        }
        return null;
    }

    public void registPersonToProgram() {
        ProgramService pS = new ProgramService(conn);
        EmberService eS = new EmberService(conn);
        int programID;
        int guest;
        boolean exit = false;
        if(pS.howManyProgram() > 0){
            Scanner sc = new Scanner(System.in);
            this.actualPPrograms();
            System.out.print("Adja meg az esemény azaonosítóját: ");
            try {
                programID = sc.nextInt();
            }catch (Exception e){
                System.out.println("Hibás adat");
                return;
            }
            if(!pS.eventExists(programID)){
                System.out.println("Sajnos nincs ilyen azonosítóval rendezvény");
                return;
            }
            do{
                System.out.print("Résztvevő azonosítója\n(ha nem akar többet akkor írja ba a -1 -et): ");
                try {
                    guest = sc.nextInt();
                }catch (Exception e){
                    System.out.println("Hibás adat");
                    return;
                }
                if (guest == -1){exit = true;}
                if(eS.userInTable(guest)){
                    pS.addPersonToProgram(programID,guest);
                }

            } while (!exit);

        }

    }

    public void showProgramok() {
        ProgramService pS = new ProgramService(conn);
        List<Program> lista = pS.getprograms();
        if (lista.isEmpty()){
            System.out.println("Nincsen megrendezésre kerülő program");
        }else {
            Iterator<Program> iter = lista.iterator();
            while (iter.hasNext()){
                System.out.println(iter.next().toString());
            }
        }
    }

    public void removePersonFromProgram() {
        ProgramService pS = new ProgramService(conn);
        EmberService eS = new EmberService(conn);
        int programID;
        int guest;
        boolean exit = false;
        if(pS.howManyProgram() > 0){
            Scanner sc = new Scanner(System.in);
            System.out.print("Adja meg az esemény azaonosítóját: ");
            try {
                programID = sc.nextInt();
            }catch (Exception e){
                System.out.println("Hibás adat");
                return;
            }
            if(!pS.eventExists(programID)){
                System.out.println("Sajnos nincs ilyen azonosítóval rendezvény");
                return;
            }
            pS.ShowProgramGuestList(programID);
            do{
                System.out.print("Résztvevő azonosítója\n(ha nem akar többet akkor írja ba a -1 -et): ");
                try {
                    guest = sc.nextInt();
                }catch (Exception e){
                    System.out.println("Hibás adat");
                    return;
                }
                if (guest == -1){exit = true;}
                    pS.removeUserFromProgram(programID,guest);

            } while (!exit);

        }


    }

    public void actualPPrograms() {
        ProgramService pS = new ProgramService(conn);
        pS.getNextPrograms();
    }

    public void whoRegistratedTheProgram() {
        ProgramService pS = new ProgramService(conn);
        Scanner sc = new Scanner(System.in);
        boolean exit = false;
        int picked;
        do{
            System.out.print("Program azonosítója: ");
            try {
              picked = sc.nextInt();
              pS.ShowProgramGuestList(picked);
              exit = true;
            }catch (Exception e){
              System.out.println("Hibas adat");
            }

        }while (!exit);

    }
}
