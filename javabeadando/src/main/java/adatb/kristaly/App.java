package adatb.kristaly;

import adatb.kristaly.domain.DbOracle;
import adatb.kristaly.domain.Menu;
import adatb.kristaly.interfaces.DbInterface;
import adatb.kristaly.menus.*;
import adatb.kristaly.services.DatabaseService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final String sqlServerAddress = "jdbc:oracle:thin:@193.6.5.58:1521:XE";
    private static Connection conndb;
    private static boolean exit = false;

    public static void main( String[] args )
    {
        while (!logInDataBase());
        DatabaseService dbservice = new DatabaseService();
        dbservice.checkTables(conndb);

        Menu fomenu = new Menu();
        fomenu.addMenuItem("Vendégek kezelése");
        fomenu.addMenuItem("Szobak kezelése");
        fomenu.addMenuItem("Programok kezelése");
        fomenu.addMenuItem("Minden adat törlése");
        fomenu.addMenuItem("PDF");
        fomenu.addBackButton("EXIT");
        while (!exit){
            fomenu.drawVisibleMenu();

            switch (fomenu.pickItem()){
                case 1 : boolean exit1 = false;                             //EMBEREK
                    while(!exit1){
                    EmberMenu eM = new EmberMenu(conndb);
                    eM.getMenu().drawVisibleMenu();
                    switch(eM.getMenu().pickItem()){
                        case 1 : eM.insertNewPerson();
                            break;
                        case 2 : eM.logoutPeople();
                            break;
                        case 3 : eM.showAllEmber();
                            break;
                        case 4 : exit1 = true;
                            break;
                    }
                    }
                    break;
                case 2 : boolean exit2 = false;                             //SZOBAK
                    while(!exit2){
                        SzobaMenu szM = new SzobaMenu(conndb);
                        szM.getStatictic();
                        szM.getMenu().drawVisibleMenu();
                        switch(szM.getMenu().pickItem()){
                            case 1 : szM.insertNewSzoba();
                                break;
                            case 2 : szM.deleteSzoba();
                                break;
                            case 3 : szM.getAllSzoba();
                                break;
                            case 4 : szM.getSzobaInfo();
                                break;
                            case 5 : szM.whoAreInSzoba();
                                break;
                            case 6 : exit2 = true;
                                break;
                        }
                    }
                    break;
                case 3 : boolean exit5 = false;                              //PROGRAMOK
                    while (!exit5){
                        ProgramMenu prM = new ProgramMenu(conndb);
                        prM.getMenu().drawVisibleMenu();
                        switch (prM.getMenu().pickItem()){
                            case 1: prM.registNewProgram();
                                break;
                            case 2: prM.registPersonToProgram();
                                break;
                            case 3: prM.removePersonFromProgram();
                                break;
                            case 4: prM.showProgramok();
                                break;
                            case 5: prM.actualPPrograms();
                                break;
                            case 6: prM.whoRegistratedTheProgram();
                                break;
                            case 7: exit5 = true;
                                break;
                        }
                    }
                    break;
                case 4 : boolean exit3 = false;                             //TORLES
                    while(!exit3){
                        ClearMenu cM = new ClearMenu(conndb);
                        cM.getMenu().drawVisibleMenu();
                        switch(cM.getMenu().pickItem()) {
                            case 1:
                                cM.deleteData();
                                exit3 = true;
                                try {
                                    conndb.close();
                                    exit = true;
                                }catch (SQLException e){
                                    System.out.println("Sikertelen megszakítás");
                                }
                                break;
                            case 2: exit3 = true;
                                break;
                        }
                    }
                    break;
                case 5 : boolean exit4 = false;                             //PDF
                    while(!exit4){
                        PDFMenu pM = new PDFMenu(conndb);
                        pM.getMenu().drawVisibleMenu();
                        switch (pM.getMenu().pickItem()){
                            case 1 : pM.createGuestPDF();
                                break;
                            case 2 : pM.createProgramPDF();
                                break;
                            case 3 : exit4 = true;
                                break;
                        }
                    }

                    break;
                case 6 : System.out.println("Adatbáziskapcsolat zárása");   //KILÉPÉS
                    try {
                        conndb.close();
                        exit = true;
                    }catch (SQLException e){
                        System.out.println("Sikertelen megszakítás");
                    }

            }
        }

    }

    private static boolean logInDataBase() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Add meg az sql server felhasznaloneved: ");
        String username = sc.nextLine();

        System.out.print("Add meg az sql server jelszavadat: ");
        String password = sc.nextLine();
        try {
            DbInterface database = new DbOracle(sqlServerAddress,password,username);
            conndb = database.getConnection();
            return true;
        }catch (SQLException sqle){
            System.out.println(sqle.getMessage());
            return false;
        }catch (ClassNotFoundException cnf){
            System.out.println(cnf.getMessage());
            return false;
        }
    }
}
