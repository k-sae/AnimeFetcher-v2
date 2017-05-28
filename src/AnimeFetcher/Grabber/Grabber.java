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
    private int retryAfter;
    public Grabber(ArrayList<String> animeLinks) {
        this.animeLinks = animeLinks;
    }
    public Grabber()
    {
        animeLinks = new ArrayList<>();
    }
    @SuppressWarnings("WeakerAccess")
    public ArrayList<String> getAnimeLinks() {
        return animeLinks;
    }
   /**
        @param link the link to the desired website
     */
    public void enQueueAnimeLink(String link)
    {
        animeLinks.add(link);
    }
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
                    analyze(getWebsiteData( deQueueAnimeLink()));

                } catch (IOException e) {
                    e.printStackTrace();
                    i--;
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /**
     * this function is called after downloading website data
     * WARNING this function will be called from a Thread other than the main thread
     * @param websiteData data resulted after de
     * @throws CorruptedDataException to request redownloading the website
     */
    protected abstract void analyze(String websiteData) throws CorruptedDataException;

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

    /** pass the url before Dequeue for last changes
     *
     * @param animeLink the next link in the link queue
     * @return URL after adding last changes
     */
    protected abstract URL fetchUrl(String animeLink);

    /**
     * @return  time needed in milli seconds for next retry when connection fails
     */
    public int getRetryAfter() {
        return retryAfter;
    }

    /**
     * @param retryAfter time needed in milli seconds for next retry when connection fails
     */
    public void setRetryAfter(int retryAfter) {
        this.retryAfter = retryAfter;
    }
}
