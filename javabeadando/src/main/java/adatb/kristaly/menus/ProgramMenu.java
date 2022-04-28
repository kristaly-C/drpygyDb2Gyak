package adatb.kristaly.menus;

import adatb.kristaly.domain.Menu;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.Date;
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
        almenu.addMenuItem("Program szerkesztése");
        almenu.addBackButton("VISSZA");
    }

    public Menu getMenu(){
        return almenu;
    }

    public void registNewProgram() {
        Scanner sc = new Scanner(System.in);
        String name;
        LocalDateTime date;

        System.out.println("Esemény megnevezése: ");
        name = sc.nextLine();
        System.out.println("Esemény idopontja ");
        date = getDateFromUser();

    }

    private LocalDateTime getDateFromUser() {
        Scanner sc = new Scanner(System.in);
        LocalDateTime ldt;
        boolean exit = false;
        while(!exit)
        try {
            System.out.print("Év:");
            int dyear = sc.nextInt();
            System.out.print("Hónap:");
            int dmonth = sc.nextInt();
            System.out.print("Nap:");
            int dday = sc.nextInt();
            System.out.print("Óra:");
            int dhour = sc.nextInt();
            System.out.print("Perc:");
            int dmin = sc.nextInt();
            ldt = LocalDateTime.of(dyear,dmonth,dday,dhour,dmin);
            return ldt;
        }catch (Exception e)
        {
            System.out.println("Hibás adatok");
        }
        return null;
    }

    public void registPersonToProgram() {
    }

    public void showProgramok() {
    }

    public void removePersonFromProgram() {
    }

    public void editProgram() {
        Menu menu = new Menu();
        menu.addMenuItem("Törlés");
        menu.addMenuItem("Szerkesztés");
        menu.addBackButton("Mégsem");
        menu.drawVisibleMenu();
        switch (menu.pickItem()){
            case 1:
                break;
            case 2:
                break;
            case 3: return;
        }
    }
}
