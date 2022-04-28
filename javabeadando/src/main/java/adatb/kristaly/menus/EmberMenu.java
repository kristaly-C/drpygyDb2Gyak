package adatb.kristaly.menus;

import adatb.kristaly.domain.Ember;
import adatb.kristaly.domain.Menu;
import adatb.kristaly.services.EmberService;
import adatb.kristaly.services.SzobaService;

import java.sql.Connection;
import java.util.Scanner;

public class EmberMenu {

    private Connection conn;
    private Menu almenu = new Menu();


    public EmberMenu(Connection connection){
        this.conn = connection;
        almenu.addMenuItem("Új vendég felvétele");
        almenu.addMenuItem("Vendég törlése");
        almenu.addMenuItem("Vendégek listája");
        almenu.addBackButton("VISSZA");
    }


    public Menu getMenu(){
        return almenu;
    }


    public void insertNewPerson() {
        SzobaService szobaService = new SzobaService(conn);
        if (!szobaService.isThereNotFullSzoba()){
            System.out.println("--------------------Nincs szabad szoba--------------------");
            return;
        }

        Scanner sc = new Scanner(System.in);
        boolean idok = false;
        int id = 0;
        int roomid = 0;
        String firstname;
        String lastname;
        int rank = 0;
        boolean male = true;
        boolean isnum = false;

        EmberService emberService = new EmberService(conn);
        System.out.println("Új személy felvétele az adatbázisba...");

        while (!idok){

            do{
                System.out.print("Adja meg az egyedi meghívókódot: ");
                try {
                    id = sc.nextInt();
                    isnum = true;
                }catch (Exception e){
                    System.out.println("Csak szamot adhat meg!");
                }

            }while (!isnum);
            if (!emberService.userInTable(id)){
            idok = true;
            }else{
                System.out.println("Hasznalatban lévő meghívószám");
            }
        }

        System.out.print("Adja meg a vezetéknevet: ");
        lastname = sc.next();
        System.out.print("Adja meg a keresztnevet: ");
        firstname = sc.next();

        System.out.println("Adja meg a vendég státuszát: ");
        Menu rankmenu = new Menu();
        rankmenu.addMenuItem("Alap");
        rankmenu.addMenuItem("Kiemelt");
        rankmenu.addMenuItem("Prémium");

        rankmenu.drawVisibleMenu();
        switch (rankmenu.pickItem()){
            case 1 : rank = 1;
                break;
            case 2 : rank = 2;
                break;
            case 3 : rank = 3;
                break;
        }

        idok = false;
        isnum = false;
        szobaService.showNotFullSzoba();

        while (!idok){

            do{
                System.out.print("Adja meg melyik szobába kerüljön a vendég: ");
                try {
                    roomid = sc.nextInt();
                    isnum = true;
                }catch (Exception e){
                    System.out.println("Csak szamot adhat meg!");
                }

            }while (!isnum);
            if (szobaService.szobaRegistrated(roomid)){
               if (szobaService.howMuchSpaceIsThere(roomid) > 0){
                   idok = true;
               }else {
                   System.out.println("Ebben a szobában nincs elég hely");
               }
            }else{
                System.out.println("Nincs ilyen szoba");
            }
        }

        Menu nemMenu = new Menu();
        nemMenu.addMenuItem("Nő");
        nemMenu.addMenuItem("Férfi");
        nemMenu.drawVisibleMenu();
        switch (nemMenu.pickItem()){
            case 1 : male = false;
                break;
            case 2 : male = true;
                break;
        }
        emberService.addNewPerson(id,firstname,lastname,rank,roomid,male);
        szobaService.decreaseFreeSlot(roomid);
    }


    public void showAllEmber() {
        EmberService emberService = new EmberService(conn);
        emberService.showAllPerson();
    }

    public void deletePeople() {
        boolean isnum = false;
        EmberService emberService = new EmberService(conn);
        SzobaService szobaService = new SzobaService(conn);

        if(emberService.howManyPeople() > 0){

        Scanner sc = new Scanner(System.in);
        do{
            System.out.print("Adja meg a vendég szobaszámát: ");
            try {
                int roomid =  sc.nextInt();
                emberService.getEmberByRoomID(roomid);
                isnum = true;
            }catch (Exception e){
                System.out.println("Csak szamot adhat meg!");
            }

        }while (!isnum);
        isnum = false;
        do{
            System.out.print("Adja meg a vendég azonosítóját (vagy kilépés -1 beírásával): ");
            try {
                int userid =  sc.nextInt();
                if(userid == -1){return;}
                if(emberService.userInTable(userid)){
                    Ember vendeg = emberService.getEmberByID(userid);
                    emberService.delUser(userid);

                    szobaService.increaseFreeSlot(vendeg.getRoomID());
                    isnum = true;
                }else {
                    System.out.println("Nincs vendég ezzel az azonosítóval.");
                }

            }catch (Exception e){
                System.out.println("Csak szamot adhat meg!");
            }

        }while (!isnum);
        }
        System.out.println("Nincsenek vendégek regisztrálva");
    }
}
