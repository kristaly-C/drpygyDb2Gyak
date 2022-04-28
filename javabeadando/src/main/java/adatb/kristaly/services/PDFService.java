package adatb.kristaly.services;

import adatb.kristaly.domain.Ember;
import com.itextpdf.barcodes.BarcodePDF417;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.xobject.PdfFormXObject;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import org.bouncycastle.jcajce.provider.symmetric.DES;

import java.io.File;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class PDFService {
    private  String DEST = "./dpfek/";
    private Connection conn;

    public PDFService(Connection conn) {
        this.conn = conn;
    }

    public void createPDF(String pdfname){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmm");
        Date date = new Date();
        String datumstring = "Vendegek"+formatter.format(date) + ".pdf";
        DEST = DEST.concat(datumstring);
        System.out.println(DEST);
        File file = new File(DEST);
        file.getParentFile().mkdirs();


        this.makeUserTablePDF(DEST);
        //new PDFService().manipulatePdf(DEST);
    }

    private void makeUserTablePDF(String dest) {
        EmberService emberService = new EmberService(conn);
        List<Ember> emberList = emberService.getAllEmber();

            try {
                PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
                Document doc = new Document(pdfDoc);
                doc.setMargins(20, 50, 20, 50);

                float[] columnWidths = {30, 40, 30};
                Table table = new Table(UnitValue.createPercentArray(columnWidths)).useAllAvailableWidth();
                //table.setWidth(pdfDoc.getDefaultPageSize().getWidth() - 80);
                Cell cim = new Cell(1, 3).setTextAlignment(TextAlignment.CENTER).add(new Paragraph("Regisztralt vendégek"));

                table.addHeaderCell(cim);
                table.addCell(new Cell().add(new Paragraph("ID")));
                table.addCell(new Cell().add(new Paragraph("Név")));
                table.addCell(new Cell().add(new Paragraph("Szoba")));

                Iterator<Ember> iter = emberList.iterator();
                while (iter.hasNext()) {
                    Ember ember = iter.next();
                    table.addCell(new Cell().add(new Paragraph(String.valueOf(ember.getID()))));
                    table.addCell(new Cell().add(new Paragraph(ember.getLastname() + " " + ember.getFirstname())));
                    table.addCell(new Cell().setTextAlignment(TextAlignment.CENTER).add(new Paragraph(String.valueOf(ember.getRoomID()))));
                }

                doc.add(table);
                doc.close();
            } catch (Exception e) {

            }

    }

    private void manipulatePdf(String dest){
        try {
            PdfDocument pdfDoc = new PdfDocument(new PdfWriter(dest));
            Document doc = new Document(pdfDoc);

            String kod = "321201012";
            Image img = createBarcode(pdfDoc, kod);
            doc.add(img);
            doc.close();
        }catch (Exception e){

        }

    }

    private Image createBarcode(PdfDocument pdfDoc,String code) {
        BarcodePDF417 barcode = new BarcodePDF417();
        barcode.setCode(code);
        PdfFormXObject barcodeobj = barcode.createFormXObject(ColorConstants.BLACK, pdfDoc);
        Image barcodeImage = new Image(barcodeobj);
        return barcodeImage;
    }
}
