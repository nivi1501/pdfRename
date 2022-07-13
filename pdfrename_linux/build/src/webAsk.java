package rename;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class webAsk {
    //search web for aronym of conference
    public String webSearch(String s, boolean proxy, String url, int port) {
        Tools tls = new Tools();
        String address = "https://www.ask.com/web?q=invisimem&";
        String attr1 = s;
        String substring1 = StringUtils.substringBefore(address, "=");
        String substring2 = "=" + attr1;
        String substring3 = StringUtils.substringAfter(address, "&");
        String finalAddress = substring1 + substring2 + substring3;
        org.jsoup.nodes.Document doc = null;
        String output = "";
        try {
            if (proxy == true) {
                doc = Jsoup.connect(finalAddress).proxy(url, port).get();
            } else {
                doc = Jsoup.connect(finalAddress).get();
            }
        } catch (Exception e) {
            System.out.println("Unable to search for query on web");
            return output;
        }
        if (doc != null) {
            Element body = doc.body();
            String linkText = "";
            if (body != null) {
                Elements results = body.getElementsByClass("PartialSearchResults-item-title-link result-link");

                if (!results.isEmpty()) {
                    int count = 0;
                    for (Element result : results) {
                        linkText = result.text();
                        linkText = linkText.replaceAll(":", " ");
                        linkText = linkText.replaceAll("\\-", " ");
                        linkText = linkText.replaceAll(",", " ");
                        linkText = linkText.replaceAll(":", " ");
                        linkText = linkText.replaceAll("\\.", " ");
                        linkText = linkText.replaceAll("[0-9]", " ");
                        linkText = linkText.replaceAll("\\.", " ");
                        int cnt = 0;
                        try {
                            BufferedReader in = new BufferedReader(new FileReader("./webconference.txt"));
                            String str;
                            while ((str = in.readLine()) != null) {
                                cnt++;
                            }
                            in.close();
                        } catch (Exception e) {
                            System.out.println("unable to read from webconference file");
                            return output;
                        }
                        String[] arr = new String[cnt];
                        try {
                            BufferedReader in = new BufferedReader(new FileReader("./webconference.txt"));
                            String str;
                            int n = -1;
                            while ((str = in.readLine()) != null) {
                                n++;
                                if (n >= cnt) {
                                    break;
                                }
                                arr[n] = str;
                            }
                            in.close();
                        } catch (Exception e) {
                            System.out.println("unable to read from webconference file");
                            return output;
                        }
                        for (int i = 0; i < count; i++) {
                            String[] ar = arr[i].split(",");
                            linkText = linkText.replaceAll(ar[0], ar[1]);
                        }
                        linkText = linkText.trim();
                        linkText = linkText.trim().replaceAll(" +", " ");
                        linkText = tls.editForDataBase(linkText);
                        output = tls.wordInsideBracket(linkText);
                        if (output.equals("")) {
                            linkText = linkText.replaceAll("[^\\p{L}\\p{Z}]", "");
                            output = tls.capitalFinder(linkText);
                            if (output.equals("")) {
                            } else {
                                break;
                            }
                        } else {
                            break;
                        }
                        count++;
                        if (count == 5) {
                            break;
                        }
                    }
                }
            }
        }
        return output;
    }
    //seqarch dblp
    public String askForDBLP(String s, boolean proxy, String url, int port) {
        Tools tls = new Tools();
        String address = "https://www.ask.com/web?q=invisimem&";
        String attr1 = s;
        String substring1 = StringUtils.substringBefore(address, "=");
        String substring2 = "=" + attr1;
        String substring3 = StringUtils.substringAfter(address, "&");
        String finalAddress = substring1 + substring2 + substring3;
        org.jsoup.nodes.Document doc = null;
        String output = "";
        try {
            if (proxy == true) {
                doc = Jsoup.connect(finalAddress).proxy(url, port).get();
            } else {
                doc = Jsoup.connect(finalAddress.trim().replaceAll(" ", "%20")).get();
            }
        } catch (Exception e) {
            System.out.println("Unable to search for query on web");
            return output;
        }
        if (doc != null) {
            Element body = doc.body();
            String linkText = "";

            if (body != null) {
                Elements results = body.getElementsByClass("PartialSearchResults-item-title-link result-link");

                if (!results.isEmpty()) {
                    int count = 0;
                    for (Element result : results) {
                        linkText = result.text();
                        linkText = linkText.replaceAll(":", " ");
                        linkText = linkText.replaceAll("\\-", " ");
                        linkText = linkText.replaceAll(",", " ");
                        linkText = linkText.replaceAll(":", " ");
                        linkText = linkText.replaceAll("\\.", " ");
                        linkText = linkText.replaceAll("[0-9]", " ");
                        linkText = linkText.replaceAll("\\.", " ");
                        int cnt = 0;
                        try {
                            BufferedReader in = new BufferedReader(new FileReader("./webconference.txt"));
                            String str;
                            while ((str = in.readLine()) != null) {
                                cnt++;
                            }
                            in.close();
                        } catch (Exception e) {
                            System.out.println("unable to read from webconference file");
                            return output;
                        }
                        String[] arr = new String[cnt];
                        try {
                            BufferedReader in = new BufferedReader(new FileReader("./webconference.txt"));
                            String str;
                            int n = -1;
                            while ((str = in.readLine()) != null) {
                                n++;
                                if (n >= cnt) {
                                    break;
                                }
                                arr[n] = str;
                            }
                            in.close();
                        } catch (Exception e) {
                            System.out.println("unable to read from webconference file");
                            return output;
                        }
                        for (int i = 0; i < count; i++) {
                            String[] ar = arr[i].split(",");
                            linkText = linkText.replaceAll(ar[0], ar[1]);
                        }
                        linkText = linkText.trim();
                        linkText = linkText.trim().replaceAll(" +", " ");
                        output = linkText;
                        break;
                    }
                }
            }
        }
        return output;
    }
}
