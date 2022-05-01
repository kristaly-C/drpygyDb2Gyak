package adatb.kristaly.menus;

import adatb.kristaly.domain.Menu;
import adatb.kristaly.domain.Szoba;
import adatb.kristaly.services.EmberService;
import adatb.kristaly.services.SzobaService;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class SzobaMenu {

    private Connection conn;
    private Menu almenu = new Menu();


    public SzobaMenu(Connection connection){
        this.conn = connection;
        almenu.addMenuItem("Szoba regisztrálása");
        almenu.addMenuItem("Szoba törlése");
        almenu.addMenuItem("Rendelkezésre álló szobák listája");
        almenu.addMenuItem("Szoba keresése");
        almenu.addMenuItem("Szobában lakók");
        almenu.addBackButton("VISSZA");
    }


    public Menu getMenu(){
        return almenu;
    }

    public void insertNewSzoba() {
        SzobaService szS = new SzobaService(conn);
        Scanner sc = new Scanner(System.in);

        int roomID;
        int roomsize = 0;
        boolean wc = false;
        boolean shower = false;

        System.out.println("Új szoba regisztrálása");
        System.out.print("Adja meg a szoba szamat: ");
        try {
            roomID = sc.nextInt();
        }catch (Exception e){
            System.out.println("Sajnos ezzel az azonosítóval nem lehetséges a szobaregisztráció" );
            return;
        }
        if(!szS.szobaRegistrated(roomID)){
            //maximális hely meghatározása
            boolean goodData = true;
            do {
                   try {
                       System.out.print("Adja meg a hány férőhelyes a szoba: ");
                       roomsize =  sc.nextInt();
                       goodData = true;
                   }catch (Exception e){
                       System.out.println("Csak szamot adhat meg");
                       goodData = false;
                       sc.next();
                   }
                   }while (!goodData);
            //WC bekérése
                System.out.println("Tartozik a szobához WC?");
                Menu kismenu = new Menu();
                kismenu.addMenuItem("Igen");
                kismenu.addMenuItem("Nem");
                kismenu.drawVisibleMenu();
                switch(kismenu.pickItem()){
                    case 1 : wc = true;
                        break;
                    case 2 : wc = false;
                        break;
                }
            //Zuhanyzo bekerese
            System.out.println("Tartozik a szobához zuhanyzo?");
            kismenu.drawVisibleMenu();
            switch(kismenu.pickItem()){
                case 1 : shower = true;
                break;
                case 2 : shower = false;
                    break;
            }
            Szoba newszoba = new Szoba(roomID,roomsize,wc,shower,roomsize);
            System.out.println("Új szoba: " + newszoba.toString());
            szS.registerNewSzoba(newszoba);
        }else {
            System.out.println("Már a listában ez a szoba\n");
        }

    }

    public void deleteSzoba(){
        SzobaService szS = new SzobaService(conn);
        Scanner sc = new Scanner(System.in);
        int szobak = szS.howManySzoba();
        if (szobak > 0) {
            int roomID;
            //Mennyi szobat akar törölni
            System.out.println("Egy adatot vagy többet akar törölni?");
            Menu delmenu = new Menu();
            delmenu.addMenuItem("Egy szobát akarok törölni ID alapján. (csak üres szobát)");
            delmenu.addMenuItem("Minden szobát törölni akarok. (csak ha minden szoba üres)");
            delmenu.drawVisibleMenu();
            switch (delmenu.pickItem()) {
                //Egy szoba törlése
                case 1:
                    System.out.print("Adja meg a szoba szamat: ");
                    try {
                        roomID = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Ezzel az azonosítóval biztosan nincs szoba regisztrálva.");
                        return;
                    }
                    if (szS.isSzobaEmpty(roomID)) {
                        szS.deleteSzoba(roomID);
                    }
                    break;
                //Minden szoba törlése
                case 2:
                    System.out.println("Biztos?");
                    Menu bmenu = new Menu();
                    bmenu.addMenuItem("Igen");
                    bmenu.addMenuItem("Mégsem");
                    bmenu.drawVisibleMenu();
                    switch (bmenu.pickItem()) {
                        case 1:
                            List<Integer> torlendo = szS.getAllSzoba();
                            if (torlendo.isEmpty()) {
                                System.out.println("Minden adat torolve");
                            } else {
                                szS.deleteSzoba(torlendo);
                            }
                            break;
                        case 2:
                            break;
                    }
                    break;
            }
        }else {
            System.out.println("Nincsenek szobak");
            return;
        }

    }

    public void getAllSzoba() {
        SzobaService szobaService = new SzobaService(conn);
        szobaService.allSzoba();
    }

    public void getSzobaInfo() {
        Scanner sc = new Scanner(System.in);
        SzobaService szobaService = new SzobaService(conn);

            int roomID;

            System.out.print("Adja meg a megtekinteni kívánt szoba számát: ");
            try {
                roomID = sc.nextInt();
            } catch (Exception e) {
                System.out.println("Ezzel az azonosítóval biztosan nincs szoba regisztrálva.");
                return;
            }
            szobaService.getSzoba(roomID);
    }

    public void whoAreInSzoba() {
        Scanner sc = new Scanner(System.in);
        EmberService emberService = new EmberService(conn);
        SzobaService szobaService = new SzobaService(conn);
        int roomID;

        System.out.print("Adja meg a megtekinteni kívánt szoba számát: ");
        try {
            roomID = sc.nextInt();
        }catch (Exception e){
            System.out.println("Ezzel az azonosítóval biztosan nincs szoba regisztrálva." );
            return;
        }
        if(szobaService.szobaRegistrated(roomID)){
            emberService.getEmberByRoomID(roomID);
        }
    }

    public void getStatictic() {
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
        SzobaService szobaService = new SzobaService(conn);
        szobaService.getStat();
        System.out.println("+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
    }
}
