package rename;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Document;

public class verifyConnection {

    // check if connected to internet
    public void checkConnection(boolean proxy, String url, String port) {
        boolean connected = true;
        if (proxy == true) {
            try {
                Document doc = Jsoup.connect("https://www.ask.com/web?q=invisimem&").proxy(url, Integer.valueOf(port)).get();
                System.out.println("Connection established");
            } catch (Exception eu) {
                System.out.println("Not connected to internet");
                System.exit(0);
            }
        }
        if (proxy == false) {
            try {

                Document doc = Jsoup.connect("https://www.ask.com/web?q=invisimem&").get();
                System.out.println("connection established");
            } catch (Exception eu) {
                System.out.println("Not connected to internet");
                System.exit(0);
            }
        }
    }
    //check if working or not
    public boolean checkScholar(boolean proxy, String url, int port) {
        Document doc = null;
        if (proxy == true) {
            try {
                doc = Jsoup.connect("https://scholar.google.co.in/scholar?q=invisimem&num=1").proxy(url, port).get();
            } catch (Exception eu) {
                System.out.println("Google Scholar is blocking the search");
                return false;
            }
            Element body = doc.body();
            if (body != null) {
                Elements NameYear = body.getElementsByClass("gs_a");
                if (!NameYear.isEmpty()) {
                } else {
                    System.out.println("google scholar is blocking the search");
                    return false;
                }
            } else {
                System.out.println("google scholar is blocking the search");
                return false;
            }
        }
        if (proxy == false) {
            try {

                doc = Jsoup.connect("https://scholar.google.co.in/scholar?q=invisimem&num=1").get();
            } catch (Exception eu) {
                System.out.println("google scholar is blocking the result");
                return false;
            }
            Element body = doc.body();
            if (body != null) {
                Elements NameYear = body.getElementsByClass("gs_a");
                if (!NameYear.isEmpty()) {
                } else {
                    System.out.println("google scholar is blocking the search");
                    return false;
                }
            } else {
                System.out.println("google scholar is blocking the search");
                return false;
            }
        }
        return true;
    }
}
