package rename;
import java.io.BufferedReader;
import java.io.FileReader;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scholar {
    //from web finds title and other information
    public static String[] infoScholar(String s, boolean proxy, String url, int port) {
        String[] info = new String[4];
        org.jsoup.nodes.Document doc = null;
        try {
            String searchAddress = "https://scholar.google.co.in/scholar?q=" + s + "&num=1";
            if (proxy == true) {
                doc = Jsoup.connect(searchAddress).proxy(url, port).get();
            } else {
                doc = Jsoup.connect(searchAddress).get();
            }
        } catch (Exception E3) {
            return null;
        }
        if (doc != null) {
            Elements titleElement = doc.select(".gs_rt a");
            if (!titleElement.isEmpty()) {
                String title = titleElement.text();
                ToolExecution tle = new ToolExecution();
                info[3] = tle.capitalizeString(title);
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
                String titleFinal = title1 + "-" + title2;
                info[2] = titleFinal;
            } else {
                return null;
            }
            Element body = doc.body();
            if (body != null) {
                Elements NameYear = body.getElementsByClass("gs_a");
                if (!NameYear.isEmpty()) {
                    info[0] = NameYear.text();
                    info[0] = info[0].replace("\n", " ").replace("\r", " ");
                } else {
                    return null;
                }
            } else {
                return null;
            }
            Elements PublishName = doc.getElementsByClass("gs_r gs_or gs_scl");
            if (!PublishName.isEmpty()) {
                info[1] = PublishName.attr("data-did");
            } else {
                return null;
            }
            return info;
        } else {
            return null;
        }
    }
    //find important words from title
    public static boolean modifyTitle(String s) {
        try {
            BufferedReader in = new BufferedReader(new FileReader("./TitleModify.txt"));
            String str;
            while ((str = in.readLine()) != null) {
                if (s.equalsIgnoreCase(str)) {
                    return true;
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Warning error while reading TitleModify.txt. Rebuilding the project may fix the error");
            System.exit(0);
        }
        return false;
    }
   //finds information from web
    public String[] executeInfoScholar(String unEditedSearchQuery, boolean proxy, String url, String port) {
        String[] info = null;
        String EditedSearchQuery = null;
        if (unEditedSearchQuery.equals("")) {
            System.out.println("not enough words to search web");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
        unEditedSearchQuery = unEditedSearchQuery.trim().replaceAll(" +", " ");
        String trim = unEditedSearchQuery.trim();
        int countwords = trim.isEmpty() ? 0 : trim.split("\\s+").length;
        if (countwords >= 22) {
            EditedSearchQuery = unEditedSearchQuery.substring(0, StringUtils.ordinalIndexOf(unEditedSearchQuery, " ", 20));
            EditedSearchQuery = EditedSearchQuery.trim().replaceAll(" +", " ");
            info = infoScholar(EditedSearchQuery, proxy, url, Integer.valueOf(port));
            if (info == null) {
                EditedSearchQuery = unEditedSearchQuery.substring(0, StringUtils.ordinalIndexOf(unEditedSearchQuery, " ", 11));
                EditedSearchQuery = EditedSearchQuery.trim().replaceAll(" +", " ");
                info = infoScholar(EditedSearchQuery, proxy, url, Integer.valueOf(port));
                if (info == null) {
                    info = infoScholar(lastWords(unEditedSearchQuery), proxy, url, Integer.valueOf(port));
                    if (info == null) {
                        System.out.println("search did not match any result on web");
                        verifyConnection vc = new verifyConnection();
                        if (!vc.checkScholar(proxy, url, Integer.parseInt(port))) {
                            System.out.println("Trying to access DBLP now, this may take few seconds please be patient");
                            System.out.println("If the result is not as expected you may try after 12-24 hrs");
                            DblpSearch srchDBLP = new DblpSearch();
                            srchDBLP.searchDblp(EditedSearchQuery.trim(), proxy, url, Integer.parseInt(port));
                        }
                        System.exit(0);
                    }
                }
            }
        } else if (countwords <= 4) {
            System.out.println("not enough words to search web");
            System.out.println("Unable to rename file");
            System.exit(0);
        } else {
            EditedSearchQuery = unEditedSearchQuery.substring(0, StringUtils.ordinalIndexOf(unEditedSearchQuery, " ", countwords - 2));
            EditedSearchQuery = EditedSearchQuery.trim().replaceAll(" +", " ");
            info = infoScholar(EditedSearchQuery, proxy, url, Integer.valueOf(port));
            if (info == null) {
                info = infoScholar(lastWords(unEditedSearchQuery), proxy, url, Integer.valueOf(port));
                if (info == null) {
                    System.out.println("search did not match any result on web");
                    verifyConnection vc = new verifyConnection();
                    if (!vc.checkScholar(proxy, url, Integer.parseInt(port))) {
                        System.out.println("Trying to access DBLP now");
                        System.out.println("If the result is not as expected you may try after 12-24 hrs");
                        DblpSearch srchDBLP = new DblpSearch();
                        srchDBLP.searchDblp(EditedSearchQuery.trim(), proxy, url, Integer.parseInt(port));
                    }
                    System.exit(0);
                }
            }
        }
        return info;
    }

    public static String lastWords(String unEditedSearchQuery) {
        String[] wrds = unEditedSearchQuery.split("\\s+");
        for (int i = 0; i < wrds.length; i++) {
            wrds[i] = wrds[i].replaceAll("[^\\w]", "");
        }
        String f = " ";
        int grt = 0; 
        if(wrds.length-11 > 0)grt = wrds.length-11;
        for (int i = wrds.length - 2; i >=0; i--) {
            f = wrds[i] + " " + f;
        }
        return f;
    }

    public String unEditedConference(String s, boolean proxy, String url, int port) {
        String address = "https://scholar.google.co.in/scholar?q=info:8qN-0ohMMjAJ:scholar.google.com/&output=cite&scirp=0&hl=en";
        int lAddress = address.length();
        int addressArray[] = new int[3];
        int posn = 0;
        for (int i = 0; i < lAddress; i++) {
            if (address.charAt(i) == ':') {
                addressArray[posn] = i;
                posn++;
            }
        }
        String substring1 = address.substring(0, addressArray[1] + 1);
        String substring2 = address.substring(addressArray[2]);
        String finalAddress = substring1 + s + substring2;
        String ittalicsFULLFORM = "";
        try {

            org.jsoup.nodes.Document doc2 = null;
            if (proxy == true) {
                doc2 = Jsoup.connect(finalAddress).proxy(url, port).get();
            } else {
                doc2 = Jsoup.connect(finalAddress).get();
            }
            if (doc2 != null) {
                Element ele = doc2.select("div.gs_citr i").first();
                if (ele != null) {
                    ittalicsFULLFORM = ele.text();
                } else {
                }
                if (ittalicsFULLFORM.length() == 0 || ittalicsFULLFORM == null || ittalicsFULLFORM.equals("")) {
                    ittalicsFULLFORM = "";
                }
            } else {
            }
            return ittalicsFULLFORM;
        } catch (Exception E4) {
            ittalicsFULLFORM = "";
            return ittalicsFULLFORM;
        }
    }
}
