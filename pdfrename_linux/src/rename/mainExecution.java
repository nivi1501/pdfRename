package rename;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class mainExecution {
    static InitialSearch srch = new InitialSearch();
    static boolean namecheck = true;
    static int checkCount = 0;
    static int flagAuthor = 1;
    static int flagYear = 1;
    static int flagOpt = 1;
    static int flagInitialIttalics = 1;
    static int flagConference = 1;
    static Tools mat = new Tools();
    static String unEditedSearchQuery = null;
    static Scholar sclr = new Scholar();
    static String[] info = null;
    static RequiredInfo reqInfo = new RequiredInfo();
    static String[] authYearConf = null;
    static String title = "";
    static String author = "";
    static String year = "";
    static String optionalConf = "";
    static ToolExecution te = new ToolExecution();
    static webAsk web = new webAsk();
    static String stringFromWebSearch = "";
    static String fileName ="";
    static boolean comp;
    public void Execute(boolean proxy, String url, String port, String imparg, boolean force, boolean complete) {
        fileName = imparg;
        comp = complete;
        verifyConnection concn = new verifyConnection();
        concn.checkConnection(proxy, url, port);
        if (!force) {
            mat.executeAddVal(imparg);
        }
        if (namecheck == true) {
            unEditedSearchQuery = srch.execInitialSearch(imparg);
        }
        if (namecheck == false && checkCount == 1) {
            String[] wrds = srch.requiredText[0].split("\\s+");
            for (int i = 0; i < wrds.length; i++) {
                wrds[i] = wrds[i].replaceAll("[^\\w]", "");
            }
            String f = " ";
            for (int i = wrds.length - 2; i >= wrds.length - 11; i--) {
                f = wrds[i] + " " + f;
            }
            unEditedSearchQuery = f;
        }
        if (namecheck == false && checkCount == 2) {
            unEditedSearchQuery = srch.reInitialSearch(srch.requiredText[1]);
        }
        info = sclr.executeInfoScholar(unEditedSearchQuery, proxy, url, port);
        if(info == null)
        {
            System.out.println("Unable to rename file");
            System.exit(0);
        }
        if (complete) {
            title = info[3];
        } else {
            title = info[2];
        }
        authYearConf = reqInfo.authorOptConfAndYear(info[0]);
        author = authYearConf[0];
        if (author.equals("")) {
            flagAuthor = 0;
        }
        year = authYearConf[1];
        if (year.equals("")) {
            flagYear = 0;
        }
        optionalConf = authYearConf[2];
        if (optionalConf.equals("")) {
            flagOpt = 0;
        }
        if (!srch.authCheck(author.substring(0, author.indexOf("_"))) && checkCount < 2) {
            checkCount++;
            namecheck = false;
            System.out.println("Some issues were found while renaming, Retrying");
            Execute(proxy, url, port, imparg, force, complete);
            System.exit(0);
        } else {
            ExecuteNext(proxy, url, port, imparg, force, complete);
        }
    }

    public static void ExecuteNext(boolean proxy, String url, String port, String imparg, boolean force, boolean complete) {
        String initialIttalicsConference = "";
        if (!info[1].equals("")) {
            initialIttalicsConference = sclr.unEditedConference(info[1], proxy, url, Integer.valueOf(port));
            if (initialIttalicsConference.equals("")) {
                flagInitialIttalics = 0;
            }
        } else {
            flagInitialIttalics = 0;
        }
        String finalConference = "";
        if (flagInitialIttalics == 0 && flagOpt == 1) {
            finalConference = optionalConf;
        } else if (flagInitialIttalics == 1) {
            finalConference = initialIttalicsConference;
        } else if (flagInitialIttalics == 0 && flagOpt == 0) {
            flagConference = 0;
        } else {
            //
        }
        String modifiedForDatabase = "";
        String resultFromDatabase = "";
        String conference = "";
        String capitals = "";
        String stringInsideBracket = "";
        confDataBase cnf = new confDataBase();
        if (flagConference == 1) {
            modifiedForDatabase = mat.editForDataBase(finalConference);
            modifiedForDatabase = modifiedForDatabase.trim();
            if (modifiedForDatabase.equals("")) {
                //not gonaa happen ever
            } else {
                resultFromDatabase = cnf.searchFromTable(modifiedForDatabase);
            }
            if (!resultFromDatabase.equals("")) {
                conference = resultFromDatabase;
                System.out.println("conference : " + conference);
            } else if (!modifiedForDatabase.contains(" ") && resultFromDatabase.equals("")) {
                conference = modifiedForDatabase;
                System.out.println("conference : " + conference);
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
        }
        String renameFile = "";
        if (flagYear == 1) {
            renameFile = renameFile + year;
        }
        if (flagConference == 1) {
            renameFile = renameFile + conference.toUpperCase() + "_";
        }
        if (flagAuthor == 1) {
            renameFile = renameFile + author;
        }
        if (complete == true) {
            title = title.trim().replaceAll(" +", " ");;
            title = title.replaceAll(" ", "\\-");
        }
        renameFile = renameFile + title;
        renameFile = te.execRename(renameFile);
        Path source = Paths.get(imparg);
		renameFile = renameFile.replace(".","");
        renameFile = renameFile + ".pdf";
        renameFile = renameFile.replace("\n","").replace("\r", "");
        renameFile = renameFile.replaceAll("\\s","");
        try {
            Files.move(source, source.resolveSibling(renameFile));
        } catch (IOException ex) {
             System.out.println("Unable to rename file");
        }
    }
}
