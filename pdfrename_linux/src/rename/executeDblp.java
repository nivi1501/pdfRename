package rename;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.apache.commons.lang.StringUtils;
import static rename.Scholar.modifyTitle;
import static rename.mainExecution.comp;
import static rename.mainExecution.fileName;

public class executeDblp {
   //renames file from DBLP data
    public void DblpExecute(boolean proxy, String url, String port, String[] arr) {
        String author = arr[1];
        String title = arr[3];
        String finalConference = arr[2];
        String year = arr[0] + "_";
        String titleFinal = titleDBLP(title);
        author = StringUtils.substringAfterLast(author.trim(), " ");
        InitialSearch srch = new InitialSearch();
        author = author + "_";
        String conference = confDBLP(finalConference, proxy, url,port);
        ToolExecution te = new ToolExecution();
        String renameFile = "";
        renameFile = renameFile + year;
        renameFile = renameFile + conference.toUpperCase() + "_";
        renameFile = renameFile + author;
        renameFile = renameFile + titleFinal;
        renameFile = te.execRename(renameFile);
        Path source = Paths.get(fileName);
		renameFile = renameFile.replace (".","");
        renameFile = renameFile + ".pdf";
        renameFile = renameFile.replace("\n","").replace("\r", "");
        renameFile = renameFile.replaceAll("\\s","");
        try {
            Files.move(source, source.resolveSibling(renameFile));
        } catch (Exception ex) {
            System.out.println("Unable to rename file");
        }
    }
    //modifies title from dblp
    public static String titleDBLP(String s)
    {
        ToolExecution te = new ToolExecution();
        String title = s;
        String titleFinal ="";
         if (comp) {
            title = title.trim().replaceAll(" +", " ");
            title = te.capitalizeString(title);
            titleFinal = title.replaceAll(" ", "\\-");
        } else {
            String title1 = title.substring(0, title.indexOf(' '));
            while (modifyTitle(title1)) {
                     title1 = StringUtils.substringAfter(title, title1);
                    title1 = title1.trim();
                    title1 = title1.substring(0, title1.indexOf(" "));
            }
            title = title.trim();
             String title2 = title.substring(title.lastIndexOf(" ") + 1, title.length());
            while (modifyTitle(title2)) {
                title = StringUtils.substringBefore(title, title2);
                title = title.trim();
                title2 = title.substring(title.lastIndexOf(" ") + 1, title.length());
            }
            title1 = title1.toLowerCase();
            title2 = title2.toLowerCase();
            title1 = title1.substring(0,1).toUpperCase() + title1.substring(1).toLowerCase();
            title2 = title2.substring(0,1).toUpperCase() + title2.substring(1).toLowerCase();
            titleFinal = title1 + "-" + title2;
        }
         return titleFinal;
    }
    //finds conference from dblp
    public static String confDBLP(String s,boolean proxy, String url, String port)
    {
        String finalConference = s;
        String modifiedForDatabase = "";
        String resultFromDatabase = "";
        String conference = "";
        String capitals = "";
        String stringInsideBracket = "";
        String stringFromWebSearch = "";
        Tools mat = new Tools();
        ToolExecution te = new ToolExecution();
        webAsk web = new webAsk();
        modifiedForDatabase = mat.editForDataBase(finalConference);
        modifiedForDatabase = modifiedForDatabase.trim();
        confDataBase cnf = new confDataBase();
        if (modifiedForDatabase.equals("")) {
        } else {
            resultFromDatabase = cnf.searchFromTable(modifiedForDatabase);
        }
        if (!resultFromDatabase.equals("")) {
            conference = resultFromDatabase;
            System.out.println("conference " + conference);
        } else if (!modifiedForDatabase.contains(" ") && resultFromDatabase.equals("")) {
            conference = modifiedForDatabase;
            System.out.println("conference " + conference);
        } else if (resultFromDatabase.equals("")) {
            stringInsideBracket = te.execBracketSearch(finalConference);
            if (!stringInsideBracket.equals("")) {
                conference = stringInsideBracket;
                System.out.println("conference " + conference);
            } else {
                try {
                    stringFromWebSearch = web.webSearch(finalConference, proxy, url, Integer.valueOf(port));
                } catch (Exception ex) {
                    stringFromWebSearch = "";
                }
                if (!stringFromWebSearch.equals("")) {
                    conference = stringFromWebSearch;
                    String trimmed = modifiedForDatabase.trim();
                    int words = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;
                    if (words >= 3) {
                        cnf.insertIntoTable(modifiedForDatabase, conference.toUpperCase());
                        System.out.println("A new rule was made when " + modifiedForDatabase + " is seen it will be abbrevited as " + conference.toUpperCase());
                    }
                } else {
                    capitals = te.execCapitalFinder(finalConference);
                    if (!capitals.equals("")) {
                        conference = capitals;
                    } else {

                        conference = te.execSelfCreation(finalConference);
                    }
                }
            }
        }
        return conference;
    }
}
