package AnimeFetcher.Grabber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kareem on 30/03/17.
 */
public abstract class Grabber {
    private ArrayList<String> animeLinks;

    public Grabber(ArrayList<String> animeLinks) {
        this.animeLinks = animeLinks;
    }
    public Grabber()
    {

    }
    @SuppressWarnings("WeakerAccess")
    public ArrayList<String> getAnimeLinks() {
        return animeLinks == null ? new ArrayList<>() : animeLinks;
    }
   /**
        abstract method bec different websites uses different patterns for url
        so have to override it for every website
        @param link the link to the desired website
     */
    protected abstract void enQueueAnimeLink(String link);
    private String deQueueAnimeLink()
    {
       String link = animeLinks.get(0);
       animeLinks.remove(0);
       return link;
    }
    /** start Grabbing */
    public void startGrabbing()
    {
        new Thread(() -> {
            for (int i = 0; i < animeLinks.size(); i++) {
                try {
                    //do it in two steps so when an exception is thrown no data is lost
                    analyze(getWebsiteData(getAnimeLinks().get(0)));
                    deQueueAnimeLink();
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();
    }
    //
    //Warning: this function will be called from a Thread other than the main thread
    protected abstract void analyze(String websiteData);
    /**
     @throws IOException if connection error or empty response or whatever
     is negative
     */
    private String getWebsiteData(String url) throws IOException {
        HttpURLConnection httpURLConnection;
        BufferedReader reader;
        URL mUrl = fetchUrl(url);
            httpURLConnection = (HttpURLConnection) mUrl.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            StringBuilder buffer = new StringBuilder();
            if (inputStream == null) {
                throw  new IOException();
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line).append("\n");
            }
            if (buffer.length() == 0) {
                // empty response may be internet or else
                //TODO #kareem
                //              after many tests throw an appropriate exception message with the
                //              appropriate exception
                throw new IOException();
            }
            //lastly after finish make sure no to waste any bytes
            httpURLConnection.disconnect();
            reader.close();
            return buffer.toString();

    }
    //this should return a the full url with all the uri param
    //although u should specify the Request Method ie: (GET or POST)
    protected abstract URL fetchUrl(String animeLink);
}
