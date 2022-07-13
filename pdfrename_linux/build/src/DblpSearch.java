package rename;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.ParserConfigurationException;

public class DblpSearch {
    //search dblp
    public void searchDblp(String s, boolean proxy, String url, int port) {
        webAsk wa = new webAsk();
        String queryForDBLP = wa.askForDBLP(s, proxy, url, port);
        if (queryForDBLP.equals("")) {
            System.out.println("Error : unable to search DBLP");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
        resultDBLP(queryForDBLP, proxy, url, port, 4);
    }
    //xml parsing of dblp
    public void resultDBLP(String s, boolean proxy, String url, int port, int wordCount) {
        String author = "";
        String title = "";
        String conference = "";
        String year = "";
        String addr = "http://dblp.org/search/publ/api?q=";
        String finalAddr = addr + urlBuild(s, wordCount).trim().replaceAll(" ", "%20");
        URLConnection conn = null;
        if (proxy == false) {
            URL Url = null;
            try {
                Url = new URL(finalAddr);
            } catch (MalformedURLException e) {
                System.out.println("Error : unable to access DBLP");
                System.out.println("Unable to rename file");
                System.exit(0);
            }
            try {
                conn = Url.openConnection();
            } catch (IOException e) {
                System.out.println("Error : unable to access DBLP");
                System.out.println("Unable to rename file");
                System.exit(0);
            }
        } else {
            Proxy prxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(url, port));
            try {
                conn = new URL(finalAddr).openConnection(prxy);
            } catch (Exception ex) {
                System.out.println("Error : unable to access DBLP");
                System.out.println("Unable to rename file");
                System.exit(0);
            }
        }
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException ex) {
            System.out.println("Error : unable to access DBLP");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
        Document doc = null;
        try {
            doc = builder.parse(conn.getInputStream());
        } catch (Exception ex) {
            System.out.println("Error : unable to access DBLP");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
        try {
            NodeList hits = doc.getElementsByTagName("hits");
            Node h = hits.item(0);
            if (h.getNodeType() == Node.ELEMENT_NODE) {
                Element hitCount = (Element) h;
                String num = hitCount.getAttribute("total");
                if (Integer.parseInt(num) == 1) {
                    NodeList name = doc.getElementsByTagName("author");
                    Node n = name.item(0);
                    if (n.getNodeType() == Node.ELEMENT_NODE) {
                        Element auth = (Element) n;
                        author = auth.getTextContent();
                    }
                    NodeList ttl = doc.getElementsByTagName("title");
                    Node t = ttl.item(0);
                    if (t.getNodeType() == Node.ELEMENT_NODE) {
                        Element head = (Element) t;
                        title = head.getTextContent();
                    }
                    NodeList date = doc.getElementsByTagName("year");
                    Node d = date.item(0);
                    if (d.getNodeType() == Node.ELEMENT_NODE) {
                        Element yr = (Element) d;
                        year = yr.getTextContent();
                    }
                    NodeList venue = doc.getElementsByTagName("venue");
                    Node v = venue.item(0);
                    if (v.getNodeType() == Node.ELEMENT_NODE) {
                        Element ven = (Element) v;
                        conference = ven.getTextContent();
                    }
                    String[] arr = new String[4];
                    arr[0] = year;
                    arr[1] = author;
                    arr[2] = conference;
                    arr[3] = title;
                    executeDblp runDblp = new executeDblp();
                    runDblp.DblpExecute(proxy, url, Integer.toString(port), arr);
                } else {
                    resultDBLP(s, proxy, url, port, wordCount + 1);
                }
            }
        } catch (Exception e) {
            System.out.println("Error : unable to access DBLP");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
    }
    //url for dblp search
    public String urlBuild(String s, int wordCnt) {
        String[] arry = s.split("\\s+");
        int N = wordCnt; // NUMBER OF WORDS THAT YOU NEED
        String nWords = "";
        if (wordCnt <= arry.length) {
            for (int i = 0; i < N; i++) {
                nWords = nWords + " " + arry[i];
            }
        } else {
            System.out.println("Generalised output : large number of possible hits");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
        return nWords;
    }
}
