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

    /**
     *  pickVariable Value from javascript code
     *  NOTE: this inline values not supported and will lead the function to crash: " '
     * @return variable value
     */
    public String pickVariable(String varName)
    {
        if (data.contains(varName))
        {
            StringBuilder value = new StringBuilder();
            int crawler =  data.indexOf(varName) + varName.length();
            data =  data.substring(crawler);
            boolean isStringHit = false;
            crawler = data.indexOf("=") + 1;
            data = data.substring(crawler);
            crawler = 0;
            char c = ' ';
            while (c != ';')
            {
                c = data.charAt(crawler++);
                if (isStringHit)
                {
                    value.append(c);
                }
                else if (c == '"' || c == '\'')
                {
                    isStringHit = true;
                }
                else if(c != ' ')
                {
                    value.append(c);
                }
            }
            String val = value.toString();
            val = val.replace("\"", "");
            val = val.replace("'", "");
            return val.substring(0, val.length() - 1);
        }
          return null;
    }
}
