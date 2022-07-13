package rename;
import org.apache.commons.lang.StringUtils;

public class InitialSearch {

    public String requiredText[] = new String[2];

    public static String initialSrch(String[] s) {
        String findOne = "abstract";
        String findTwo = "introduction";
        String reqString = null;
        for (int i = 0; i < s.length; i++) {
            s[i] = s[i].toLowerCase();
            s[i] = s[i].replace("\n", " ").replace("\r", " ");
            s[i] = s[i].trim().replaceAll(" +", " ");
        }
        for (int i = 0; i < s.length; i++) {
            if (s[i].contains(findOne.toLowerCase())) {
                reqString = StringUtils.substringBefore(s[i], findOne);
                break;
            }
            if (s[i].contains(findTwo.toLowerCase())) {
                reqString = StringUtils.substringBefore(s[i], findTwo);
                break;
            }
        }
        if (reqString == null) {
            if (s[0].trim().replaceAll(" +", " ").equals("")) {
                System.out.println("unable to find relevant words for web search");
                System.out.println("Unable to rename file");
                System.exit(0);
            }

            String trim = s[0].trim();
            int countwords = trim.isEmpty() ? 0 : trim.split("\\s+").length;
            if (countwords >= 22) {
                reqString = trim.substring(0, StringUtils.ordinalIndexOf(trim, " ", 20));
            } else if (countwords <= 4) {
                System.out.println("unable to find relevant words for web search unable to encode pdf");
                
                System.out.println("Unable to rename file");
                System.exit(0);
            } else {
                reqString = trim.substring(0, StringUtils.ordinalIndexOf(trim, " ", countwords - 2));
            }
        }
        reqString = reqString.replace("\n", " ").replace("\r", " ");
        reqString = reqString.replaceAll(":", " ");
        reqString = reqString.replaceAll("&", " ");
        reqString = reqString.trim().replaceAll(" +", " ");
        if (reqString == null) {
            System.out.println("unable to get valid string for Scholar search");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
        return reqString;
    }

    public String execInitialSearch(String s) {
        pdfToText obj1 = new pdfToText();
        String pdfFiles[] = obj1.pdfBreaker(s);
        for (int i = 0; i < 2; i++) {
            requiredText[i] = obj1.impText(pdfFiles[i]);
        }
        return initialSrch(requiredText);
    }

    public String reInitialSearch(String s) {
        String findOne = "abstract";
        String findTwo = "introduction";
        String reqString = null;
        s = s.toLowerCase();
        if (s.contains(findOne.toLowerCase())) {
            reqString = StringUtils.substringBefore(s, findOne);
        } else if (s.contains(findTwo.toLowerCase())) {
            reqString = StringUtils.substringBefore(s, findTwo);
        } else {
            String trim = s.trim();
            int countwords = trim.isEmpty() ? 0 : trim.split("\\s+").length;
            if (countwords >= 22) {
                reqString = trim.substring(0, StringUtils.ordinalIndexOf(trim, " ", 20));
            } else {
                reqString = trim.substring(0, StringUtils.ordinalIndexOf(trim, " ", countwords - 2));
            }
        }
        reqString = reqString.replace("\n", " ").replace("\r", " ");
        reqString = reqString.replaceAll(":", " ");
        reqString = reqString.replaceAll("&", " ");
        reqString = reqString.trim().replaceAll(" +", " ");
        if (reqString == null) {
            System.out.println("unable to get valid string for Scholar search");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
        return reqString;
    }

    public boolean authCheck(String s) {
        s = s.toLowerCase();
        int count = 0;
        for (int i = 0; i < requiredText.length; i++) {
            if (!requiredText[i].toLowerCase().contains(s)) {
                count++;
            }
        }
        if (count > 1) {
            System.out.println("WARNING FILE MAY HAVE BEEN RENAMED IN-CORRECTLY");
            return false;
        }
        return true;
    }

}
