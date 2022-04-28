package adatb.kristaly.menus;

import adatb.kristaly.domain.Menu;
import adatb.kristaly.services.EmberService;
import adatb.kristaly.services.ProgramService;
import adatb.kristaly.services.SzobaService;

import java.sql.Connection;

public class ClearMenu {

    private Connection conn;
    private Menu almenu = new Menu();


    public ClearMenu(Connection connection){
        this.conn = connection;
        almenu.addMenuItem("Adatokat és adatbázis törlése");
        almenu.addBackButton("Mégsem törölnék");
    }


    public Menu getMenu(){
        return almenu;
    }

    public void deleteData() {
        SzobaService szobaService = new SzobaService(conn);
        EmberService emberService = new EmberService(conn);
        ProgramService programService = new ProgramService(conn);
        programService.deleteTables();
        emberService.deleteTable();
        szobaService.deleteTable();

        /*Menu delmenu = new Menu();
        delmenu.addMenuItem("Csak az adatokat akarom törölni");
        delmenu.addMenuItem("Adatokat és adatbázist is törölni akarom");
        delmenu.addBackButton("VISSZA");
        delmenu.drawVisibleMenu();
        switch (delmenu.pickItem()){
            case 1: programService.deleteAll();
                    emberService.deleteAll();
                    //szobaService.deleteAll();
                break;
            case 2: programService.deleteTables();
                    emberService.deleteTable();
                    szobaService.deleteTable();

                break;
            case 3 : return;
        }
        */
    }
}
