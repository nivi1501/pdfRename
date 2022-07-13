package rename;
import com.lowagie.text.Document;
import com.lowagie.text.pdf.PdfCopy;
import com.lowagie.text.pdf.PdfImportedPage;
import com.lowagie.text.pdf.PdfReader;
import java.io.File;
import java.io.FileOutputStream;
import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class pdfToText
{
    //breaks pdf into 2 pdf files consisting of 1st and 2nd page of original pdf file respectively 
    public String[] pdfBreaker(String s) {
        String inputFileBreaker = s;
        System.out.println("Reading " + inputFileBreaker);
        PdfReader reader = null;
        PdfCopy writer = null;
        Document document = null;
        String outputFileBreaker[] = new String[2];
        FileOutputStream closeFile = null;
        int i = 0;
        while(i < 2)
        {
        try {
            reader = new PdfReader(inputFileBreaker);
            int n = reader.getNumberOfPages();
            outputFileBreaker[i] = inputFileBreaker.substring(0, inputFileBreaker.indexOf(".pdf")) + "-" + String.format("%03d", i + 1) + ".pdf";
            document = new Document(reader.getPageSizeWithRotation(1));
            closeFile = new FileOutputStream(outputFileBreaker[i]);
            writer = new PdfCopy(document, closeFile);
            document.open();
            PdfImportedPage page = writer.getImportedPage(reader, ++i);
            writer.addPage(page);
        } catch (Exception ee1) {
            System.out.println("entered exception case why");
            System.out.println("unable to access file please make sure that file is not being accessed by another program and is a pdf");
            System.out.println("Unable to rename file");
            System.exit(0);
        } finally {
            reader.close();
            document.close();
            writer.close();
        }
        }
        return outputFileBreaker;
    }
    
    //converts pdf to text
    public String impText(String s)
    {
        String fromText = null ;
        PDFParser parser = null;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;
        PDFTextStripper pdfStripper;
        String parsedText  = null;
        File file = new File(s);
        try {
            parser = new PDFParser(new RandomAccessFile(file, "r"));
            parser.parse();
            cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);
            fromText = parsedText;
            cosDoc.close();
            pdDoc.close();
        } catch (Exception e) {
            try {
                if (cosDoc != null) {
                    cosDoc.close();
                }
                if (pdDoc != null) {
                    pdDoc.close();
                }
            } catch (Exception e1) {
            }
            System.out.println("unable to encode pdf ");
            System.out.println("Unable to rename file");
            System.exit(0);
        } finally {

            try {
                cosDoc.close();
                pdDoc.close();
            } catch(Exception ex) { 

            } 
        }
        return fromText;
    }
}
    

