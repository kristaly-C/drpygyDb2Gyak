package adatb.kristaly.menus;

import adatb.kristaly.domain.Menu;
import adatb.kristaly.services.PDFService;
import adatb.kristaly.services.ProgramService;

import java.sql.Connection;
import java.util.Scanner;

public class PDFMenu {
    private Connection conn;
    private Menu almenu = new Menu();

    public PDFMenu(Connection conn) {
        this.conn = conn;
        this.almenu.addMenuItem("Vendégek listája");
        this.almenu.addMenuItem("Program résztvevők");
        this.almenu.addBackButton("VISSZA");
    }

    public Menu getMenu(){
        return almenu;
    }


    public void createGuestPDF() {
        PDFService pdfSer = new PDFService(conn);
        pdfSer.createGuestPDF("elso");
    }

    public void createProgramPDF() {
        PDFService pdfSer = new PDFService(conn);
        ProgramService pS = new ProgramService(conn);
        Scanner sc = new Scanner(System.in);
        int pick;
        boolean okay = false;
        do{
            System.out.print("Adja meg melyik programból készüljön dokumentum: ");
            try {
                pick = sc.nextInt();
                if(pS.eventExists(pick)){
                    pdfSer.createProgramPDF("programok", pick);
                    okay = true;
                }
            }catch (Exception e){
                System.out.println("Szamot adj meg");
            }

        }while (!okay);

    }

}
