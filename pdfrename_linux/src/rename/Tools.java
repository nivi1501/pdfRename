package rename;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang.StringUtils;

public class Tools {
    //validate file is already in proper format or not
    public static boolean addressValidator(String s) {
        String initialFileAddress = s;
        int lastforwardslash = initialFileAddress.lastIndexOf('/');
        int lastbackwardslash = initialFileAddress.lastIndexOf('\\');
        int check1 = 0;
        if (lastforwardslash != -1) {
            check1 = lastforwardslash;
        } else {
            check1 = lastbackwardslash;
        }
        int chkNumCount = 0;
        for (int ch = check1 + 1; ch < check1 + 5; ch++) {
            if (initialFileAddress.charAt(ch) == '0' || initialFileAddress.charAt(ch) == '1' || initialFileAddress.charAt(ch) == '2'
                    || initialFileAddress.charAt(ch) == '3' || initialFileAddress.charAt(ch) == '4' || initialFileAddress.charAt(ch) == '5'
                    || initialFileAddress.charAt(ch) == '6' || initialFileAddress.charAt(ch) == '7' || initialFileAddress.charAt(ch) == '8'
                    || initialFileAddress.charAt(ch) == '9') {
                chkNumCount++;
            }
        }
        if (chkNumCount == 4 && initialFileAddress.charAt(check1 + 5) == '_') {
            return true;
        } else {
            return false;
        }
    }
    //runs address validator function
    public void executeAddVal(String s) {
        if (addressValidator(s) == true) {
            System.out.println("File is already in the desired name format if you still want to rename the file run the command with -force [-f] option");
            System.out.println("Unable to rename file");
            System.exit(0);
        }
    }
    //edit string for database search
    public  String editForDataBase(String s) {
        String s2 = s;
        s2 = s2.replaceAll("'", "");
        s2 = s2.replaceAll("\"", "");
        s2 = s2.replaceAll("\\-", "");
        s2 = s2.replaceAll("[^\\p{L}\\p{Z}]", "");
        s2 = s2.replaceAll("[0-9]", " ");
        s2 = s2.replaceAll(":", "");
        s2 = s2.replaceAll("\\p{P}", " ");
        s2 = s2.replaceAll("\\.", " ");
        int count = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader("./editDatabase.txt"));
            String str;
            while ((str = in.readLine()) != null)
            {
                count++;
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error while accessing Database");
        }
        String[] arr = new String[count];
        try {
            BufferedReader in = new BufferedReader(new FileReader("./editDatabase.txt"));
            String str;
            int n = -1;
            while ((str = in.readLine()) != null)
            {
                n++;
                if(n >= count)break;
                arr[n] = str;
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error while accessing Database");
        }
        for(int i =0 ; i < count; i++)
        {
            String[] ar= arr[i].split(",");
                s2 = s2.replaceAll(ar[0],ar[1]);
        }
        s2 = s2.replaceAll("zzz", " ");
        s2 = s2.trim();
        s2 = s2.trim().replaceAll(" +", " ");
        return s2;
    }
    //find words inside bracket
  public String wordInsideBracket(String s) {
        String output = "";
        String xv = s;
        String df = "";
        Matcher m = Pattern.compile("\\((.*?)\\)").matcher(xv);
        while (m.find()) {
            df = m.group(1);
        }
        df = df.trim().replaceAll(" +", " ");
        String[] parts = df.split(" ");
        for (String px : parts) {
            int ibrack = 0;
            for (int t = 0; t < px.length(); t++) {
                if (px.charAt(t) == '0' || px.charAt(t) == '1' || px.charAt(t) == '2'
                        || px.charAt(t) == '3' || px.charAt(t) == '4' || px.charAt(t) == '5'
                        || px.charAt(t) == '6' || px.charAt(t) == '7' || px.charAt(t) == '8'
                        || px.charAt(t) == '9') {
                    ibrack++;
                }
            }
            if (ibrack <= 1) {
                output = output + px + " ";
            }
        }
        output = output.trim().replaceAll(" +", " ");
        return output;
    }
   //find capital words
   public static String capitalFinder(String s) {
        String words[] = s.split(" ");
        Pattern p = Pattern.compile("\\b[A-Z]{3,}\\b");
        String[] word = new String[5];
        String capital = "";
        for(int i = 0; i < 5;i++)
        {
            word[i] = "";
        }
        int count = 0;
        for (int i = 0; i < words.length; i++) {
            Matcher m = p.matcher(words[i]);
            while (m.find()) {
                word[count] = word[count] + m.group();
            }
            if (!word[count].equals(""))
            {
                count++;
            }
        }
        if(count > 1) {
            for (int i = 0; i < count; i++) {
                if (!word[i].equals("SIGARCH"))
                {
                    capital =  word[i];
                    break;
                }
            }
        }
        else
        {
            capital = word[0];
        }
        return capital;

    }
   //rules to make acronym by self
    public static String selfStringCreate(String s) {
        String s2 = s;
        s2 = s2.replaceAll("'", "");
        s2 = s2.replaceAll("\"", "");
        s2 = s2.replaceAll("\\-", "");
        s2 = s2.replaceAll("[0-9]", " ");
        s2 = s2.replaceAll(":", "");
        s2 = s2.replaceAll("\\p{P}", " ");
        s2 = s2.replaceAll("\\.", " ");
        int count = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader("./selfcreate.txt"));
            String str;
            while ((str = in.readLine()) != null)
            {
                count++;
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error while reading selfcreate.txt");
        }
        String[] arr = new String[count];
        try {
            BufferedReader in = new BufferedReader(new FileReader("./selfcreate.txt"));
            String str;
            int n = -1;
            while ((str = in.readLine()) != null)
            {
                n++;
                if(n >= count)break;
                arr[n] = str;
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error while reading selfcreate.txt");
        }
        for(int i =0 ; i < count; i++)
        {
            String[] ar= arr[i].split(",");
                
                s2 = s2.replaceAll(ar[0],ar[1]);
        }
        s2 = s2.replaceAll("zzz", " ");
        s2 = s2.trim();
        s2 = s2.trim();
        s2 = s2.trim().replaceAll(" +", " ");
        return s2;
    }
}
