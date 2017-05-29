package AnimeFetcher.Grabber;

/**
 * Created by kemo on 29/05/2017.
 * replace it by one
 */
public class JSParser {
    private String data;

    public JSParser(String data) {
        this.data = data;
    }
    public String pickVariable(String varName)
    {
        if (data.contains(varName))
        {
            String value = "";
            int crawler = data.indexOf(varName) + varName.length();
            boolean isEqualHit= false;
            boolean isStringHit = false;
            while (data.charAt(crawler) != ';')
            {
                if (data.charAt(crawler++) == '"')
                {

                }
               else if (data.charAt(crawler++) == '=')
                {

                }
            }
            return value;
        }
        else  return null;
    }
}
