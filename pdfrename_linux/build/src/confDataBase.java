package rename;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;


public class confDataBase {
    // enters text in database
    public void insertIntoTable(String s, String s1) {
        Tools tls = new Tools();
        String searchString = this.searchFromTable(tls.editForDataBase(s));
        if(searchString.equals(""))
        {
        try (FileWriter fw = new FileWriter("./database.txt", true);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.print(tls.editForDataBase(s)+","+s1);
            out.println();
        } catch (Exception e) {
            System.out.println("Unable to add new rule to database");
            System.exit(0);
        }
        }
        else
        {
            this.replaceFile("./database.txt",s, s1);
        }

    }
    //search the database
     public String searchFromTable(String s1)
     { 
        String externalInput = s1.toUpperCase();
        String SHORTFORM = "";
        int count = 0;
        try {
            BufferedReader in = new BufferedReader(new FileReader("./database.txt"));
            String str;
            while ((str = in.readLine()) != null)
            {
                count++;
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error while reading database.txt");
        }
        String[] arr = new String[count];
        try {
            BufferedReader in = new BufferedReader(new FileReader("./database.txt"));
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
            System.out.println("Error while reading database.");
        }
        int maxlength = 0;
        for(int i =0 ; i < count; i++)
        {
            String[] ar= arr[i].split(",");
            String internalData[] = ar[0].trim().split(" ");
            int matchCount = 0;
            for (int j = 0; j < internalData.length; j++) {

                  if(externalInput.contains(internalData[j].toUpperCase()))
                  {
                      matchCount++;

                  }
                  else
                      break;
            }
                if (matchCount >= internalData.length) {
                String possibleAns = ar[0].trim().replaceAll(" +", " ");
                int lengthPossibleans = possibleAns.split(" ").length;
                if(maxlength < lengthPossibleans)
                {
                    SHORTFORM = ar[1].trim().replaceAll(" +", " ");
                    maxlength = lengthPossibleans;
                }  
            }
        }
        return SHORTFORM;   
        }
     //prints the database
     public void printAll(){
         try {
            BufferedReader in = new BufferedReader(new FileReader("./database.txt"));
            String str;
            while ((str = in.readLine()) != null)
            {
                System.out.println(str);
            }
            in.close();
        } catch (Exception e) {
            System.out.println("Error while reading database.txt");
        }
     }
     //delets the text from database
     public void delete(String file, String lineToRemove) {
          Tools tls = new Tools();  
    try {
      File inFile = new File(file);
      if (!inFile.isFile()) {
        System.out.println("Parameter is not an existing file");
        return;
      }
      //Construct the new file that will later be renamed to the original filename.
      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
      BufferedReader br = new BufferedReader(new FileReader(file));
      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
      String line = null;
      //Read from the original file and write to the new
      //unless content matches data to be removed.
      int firstTime =0;
      while ((line = br.readLine()) != null) {
           String[] ar= line.split(",");
           String internalData = ar[0].trim();
       
        if (!internalData.replaceAll(" +"," ").trim().toUpperCase().equals(tls.editForDataBase(lineToRemove).toUpperCase())) {
            if(firstTime ==0)
            {
                 pw.print(line);
                 pw.flush();
                 firstTime++;
            }
            else
            {
            pw.println();
            pw.print(line);
            pw.flush();
            }
        }
      }
      pw.close();
      br.close();
      //Delete the original file
      if (!inFile.delete()) {
        System.out.println("Could not delete file");
        return;
      }
      //Rename the new file to the filename the original file had.
      if (!tempFile.renameTo(inFile))
        System.out.println("Could not rename file");
    }
    catch (Exception ex) {
      System.out.println("Unable to delete rules from database ");
      System.exit(0);
    }
  } 
     //replaces certain text
     public void replaceFile(String file, String lineToRepalce, String shortForm) {
          Tools tls = new Tools();
    try {
      File inFile = new File(file);
      if (!inFile.isFile()) {
        System.out.println("Parameter is not an existing file");
        return;
      }
      //Construct the new file that will later be renamed to the original filename.
      File tempFile = new File(inFile.getAbsolutePath() + ".tmp");
      BufferedReader br = new BufferedReader(new FileReader(file));
      PrintWriter pw = new PrintWriter(new FileWriter(tempFile));
      String line = null;
      //Read from the original file and write to the new
      //unless content matches data to be removed.
      int firstTime =0;
      while ((line = br.readLine()) != null) {
           String[] ar= line.split(",");
           String internalData = ar[0].trim();
       
        if (!internalData.replaceAll(" +"," ").trim().toUpperCase().equals(tls.editForDataBase(lineToRepalce).toUpperCase())) {
            if(firstTime ==0)
            {
                 pw.print(line);
                 pw.flush();
                 firstTime++;
            }
            else
            {
            pw.println();
            pw.print(line);
            pw.flush();
            }
        }
        else
        {
            if(firstTime ==0)
            {
                 pw.print(tls.editForDataBase(lineToRepalce) +","+shortForm.trim());
                 pw.flush();
                 firstTime++;
            }
            else
            {
            pw.println();
            pw.print(tls.editForDataBase(lineToRepalce) +","+shortForm.trim());
            pw.flush();
            }
        }
      }
      pw.close();
      br.close();
      //Delete the original file
      if (!inFile.delete()) {
        System.out.println("Could not delete file");
        return;
      }
      //Rename the new file to the filename the original file had.
      if (!tempFile.renameTo(inFile))
        System.out.println("Could not rename file");
    }
    catch (Exception ex) {
        System.out.println("Unable to update rule in database ");
        System.exit(0);
    }
  }      
}
