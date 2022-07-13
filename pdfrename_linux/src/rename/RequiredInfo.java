package rename;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class RequiredInfo {

    // returns author and year and partial conferece name
    public String[] authorOptConfAndYear(String s1) {
        String[] authorYear = new String[3];
        String s = s1;
        s = s.trim().replaceAll(" +", " ");
        int l1 = s.length();
        int comma[] = new int[20];
        int c = 0;
        for (int i = 0; i < l1; i++) {
            if (s.charAt(i) == ',') {
                comma[c] = i;
                c++;
            }
        }
        int dash[] = new int[6];
        int d = 0;
        for (int i = 0; i < l1; i++) {
            if (s.charAt(i) == '-') {
                dash[d] = i;
                d++;
            }
        }
        int space = s.indexOf(32);
        String auth = "";
        if (comma[0] < space && c != 0) {
            auth = s.substring(StringUtils.ordinalIndexOf(s, " ", 2), comma[1]) + "_";
        } else {
            if (comma[0] < dash[0] && c != 0) {
                auth = s.substring(space + 1, comma[0]) + "_";
            }
            if (comma[0] > dash[0] || c == 0) {
                auth = s.substring(space + 1, dash[0]) + "_";
            }
        }
        authorYear[0] = auth;
        System.out.println("Surname of first author " + auth);

        String year = "";
        if (s.matches(".*\\d+.*")) // check if string has number or not
        {
            if (c >= 1) {
                year = s.substring(s.lastIndexOf(","), s.lastIndexOf("-"));
                year = year.replaceAll(",", " ");
                year = year.trim().replaceAll(" +", " ");
                year = year.trim();
                if (StringUtils.isNumeric(year)) {
                    year = year + "_";
                } else {
                    List<Integer> m = extractNumbers(s);
                    Object[] objects = m.toArray();
                    String[] findYear = new String[objects.length];
                    for (int i = 0; i < objects.length; i++) {
                        findYear[i] = objects[i].toString();
                        findYear[i] = findYear[i].trim().replaceAll(" +", " ");
                        if (findYear[i].length() == 4 && (findYear[i].charAt(0) == '1' || findYear[i].charAt(0) == '2')) {
                            year = findYear[i] + "_";
                            break;
                        }
                    }
                    if (year.equals("")) {
                        System.out.println("Unable to find year");
                    }
                }
            } else {
                List<Integer> m = extractNumbers(s);
                Object[] objects = m.toArray();
                String[] findYear = new String[objects.length];
                for (int i = 0; i < objects.length; i++) {
                    findYear[i] = objects[i].toString();
                    if (findYear[i].length() == 4 && (findYear[i].charAt(0) == '1' || findYear[i].charAt(0) == '2')) {
                        year = findYear[i] + "_";
                        break;
                    }
                }
                if (year.equals("")) {
                    System.out.println("Unable to find year");
                }
            }
            authorYear[1] = year;
            System.out.println("year  = " + authorYear[1]);
        } else {
            authorYear[1] = "";
            System.out.println("Sorry unable to find year");
        }
        String optionalFullForm = "";
        if (d > 0) {
            try {
                optionalFullForm = s.substring(s.indexOf('-') + 1, s.lastIndexOf("."));
                optionalFullForm = optionalFullForm.replaceAll("\\.", " ");
                optionalFullForm = optionalFullForm.trim().replaceAll(" +", " ");
            } catch (Exception eee) {
                optionalFullForm = "";
            }
        }
        if (d > 0 && c > 0 && dash[0] < comma[c - 1]) {
            try {
                optionalFullForm = s.substring(s.indexOf('-') + 1, s.lastIndexOf(","));
            } catch (Exception eee) {
                optionalFullForm = "";
            }
        }
        authorYear[2] = optionalFullForm;
        return authorYear;
    }
    //extracts number from strings
    public static List<Integer> extractNumbers(String s) {
        List<Integer> numbers = new ArrayList<Integer>();
        Pattern p = Pattern.compile("\\d+");
        Matcher m = p.matcher(s);
        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }
        return numbers;
    }

}
