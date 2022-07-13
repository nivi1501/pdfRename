package rename;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.commons.lang.*;
import org.jsoup.*;
import com.lowagie.text.*;
import org.apache.pdfbox.cos.*;
import org.apache.pdfbox.io.RandomAccessFile;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.net.*;
import java.util.jar.*;

public class Rename {

    public static void main(String[] args) {
        mainExecution obj = new mainExecution();

        if (args.length == 4) {

            if (args[3].equals("--print") || (args[3].equals("-p"))) {
                confDataBase cnf = new confDataBase();
                cnf.printAll();
            } else if (args[3].equals("--help") || (args[3].equals("-h"))) {
                try {

                    BufferedReader in = new BufferedReader(new FileReader("./helptext.txt"));
                    String str;
                    while ((str = in.readLine()) != null) {
                        System.out.println(str);
                    }
                    in.close();
                } catch (Exception e) {
                    System.out.println("Error while reading the helptext file please rebuild the project");
                }
                System.exit(0);
            } else if (args[3].equals("--version") || (args[3].equals("-v"))) {
                System.out.println("Version: 1.0.0");
            } else {
                if (args[0].equals("false")) {
                    obj.Execute(false, "z", "0", args[3], false, false);
                } else {
                    obj.Execute(true, args[1], args[2], args[3], false, false);
                }
            }
        } else if (args.length == 5) {
            if (args[3].equals("--delete") || args[3].equals("-d")) {
                confDataBase cnf = new confDataBase();
                cnf.delete("./database.txt", args[4]);
            } else if (args[3].equals("--force") || args[3].equals("-f")) {
                if (args[0].equals("false")) {
                    obj.Execute(false, "z", "0", args[4], true, false);
                } else {
                    obj.Execute(true, args[1], args[2], args[4], true, false);
                }
            } else if (args[3].equals("-c") || args[3].equals("--complete")) {
                if (args[0].equals("false")) {
                    obj.Execute(false, "z", "0", args[4], false, true);
                } else {
                    obj.Execute(true, args[1], args[2], args[4], false, true);
                }

            } else {
                System.out.println("Enter proper parameters");
            }
        } else if (args.length == 6) {
            if (args[3].equals("--update") || args[3].equals("-u")) {
                confDataBase cnf = new confDataBase();
                cnf.insertIntoTable(args[4], args[5]);
            } else if ((args[3].equals("-c") || args[3].equals("--complete") || args[3].equals("-f") || args[3].equals("--force")) && (args[4].equals("-c") || args[4].equals("--complete") || args[4].equals("-f") || args[4].equals("--force"))) {
                if (args[0].equals("false")) {
                    obj.Execute(false, "z", "0", args[5], true, true);
                } else {
                    obj.Execute(true, args[1], args[2], args[5], true, true);
                }
            } else {
                System.out.println("Improper parameters or option");
            }
        } else {
            System.out.println("Improper parameters or option");
        }
    }
}
