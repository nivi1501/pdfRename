package rename;
import org.apache.commons.lang.StringUtils;

public class ToolExecution
{
    Tools tool = new Tools();
   //modifies string inside brackets
    public String execBracketSearch(String finalConference)
    {
        String editForBrackets = finalConference;
        editForBrackets = editForBrackets.replaceAll("(?i)IEEE", " ");
        editForBrackets = editForBrackets.replaceAll("(?i)ACM", " ");
        editForBrackets = editForBrackets.replaceAll("IEEE/ACM", " ");
        editForBrackets = editForBrackets.replaceAll("[0-9]", " ");
        return tool.wordInsideBracket(editForBrackets);                
    }
    
    //modifies Capital words
    public String execCapitalFinder(String finalConference)
    {
        String editForBrackets = finalConference;
        editForBrackets = editForBrackets.replaceAll("(?i)IEEE", " ");
        editForBrackets = editForBrackets.replaceAll("(?i)ACM", " ");
        editForBrackets = editForBrackets.replaceAll("IEEE/ACM", " ");
        editForBrackets = editForBrackets.replaceAll("[0-9]", " ");
        editForBrackets = editForBrackets.replaceAll("(?i)VOL", " ");
        editForBrackets = editForBrackets.replaceAll(" \\. ", " ");
        editForBrackets = editForBrackets.replaceAll(" (?i)no ", " ");
        return tool.capitalFinder(editForBrackets);
    }
    
    //rules for making short form
    public String execSelfCreation(String finalConference)
    {
        String selfCreated = tool.selfStringCreate(finalConference);
        if (selfCreated.toLowerCase().contains(" and ")) {
            if (selfCreated.toLowerCase().contains(",")) {
                if (selfCreated.indexOf(',') == selfCreated.lastIndexOf(',')) {

                } else if (selfCreated.substring(selfCreated.indexOf(',') + 2, selfCreated.lastIndexOf(",")).contains(" ")) {

                } else {
                    String a1 = StringUtils.substringBefore(selfCreated, ",");
                    String a2 = StringUtils.substringAfter(selfCreated, ",");
                    selfCreated = a2 + " " + a1;
                    selfCreated = selfCreated.trim().replaceAll(" +", " ");
                }
            }
        } else {
            if (selfCreated.contains(",")) {
                String a1 = StringUtils.substringBefore(selfCreated, ",");
                String a2 = StringUtils.substringAfter(selfCreated, ",");
                selfCreated = a2 + " " + a1;
                selfCreated = selfCreated.trim().replaceAll(" +", " ");
            }
        }
        selfCreated = selfCreated.replaceAll(" (?i)and ", " ");
        selfCreated = selfCreated.replaceAll("\\.", " ");
        selfCreated = selfCreated.replaceAll(" (?i)no ", " ");
        selfCreated = selfCreated.replaceAll("(?i)Annual", " ");
        selfCreated = selfCreated.replaceAll("[^\\p{L}\\p{Z}]", "");
        selfCreated = selfCreated.replaceAll(",", " ");
        selfCreated = selfCreated.trim().replaceAll(" +", " ");
        selfCreated = selfCreated.replaceAll("\\B.|\\P{L}", "").toUpperCase();
        selfCreated = selfCreated.trim().replaceAll(" +", " ");
        selfCreated = selfCreated.replaceAll("\"", " ");
        selfCreated = selfCreated.replaceAll("\'.", " ");
        return selfCreated;
    }
    //modifies the final text 
    public String execRename(String s)
    {
        String renameFile = s; 
        renameFile = renameFile.replaceAll(":", " ");
        renameFile = renameFile.replaceAll(" ? ", " ");
        renameFile = renameFile.replaceAll("<", " ");
        renameFile = renameFile.replaceAll(">", " ");
        renameFile = renameFile.replaceAll("/", " ");
        renameFile = renameFile.replaceAll("\"", " ");
        renameFile = renameFile.replaceAll("\'", " ");
        renameFile = renameFile.replaceAll(",", " ");
        renameFile = renameFile.trim().replaceAll(" +", " ");
        return renameFile;
    }
    
    public static String capitalizeString(String string) {
   char[] chars = string.toLowerCase().toCharArray();
     boolean found = false;
  for (int i = 0; i < chars.length; i++) {
    if (!found && Character.isLetter(chars[i])) {
      chars[i] = Character.toUpperCase(chars[i]);
      found = true;
    } else if (Character.isWhitespace(chars[i]) || chars[i]=='.' || chars[i]=='\'') { // You can add other chars here
      found = false;
    }
  }
  return String.valueOf(chars);
}
    
}
