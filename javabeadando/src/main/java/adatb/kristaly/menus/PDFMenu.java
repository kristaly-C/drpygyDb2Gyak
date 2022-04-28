package adatb.kristaly.menus;

import adatb.kristaly.domain.Menu;
import adatb.kristaly.services.PDFService;

import java.sql.Connection;

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
        pdfSer.createPDF("elso");
    }
}
