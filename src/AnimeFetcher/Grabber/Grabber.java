package AnimeFetcher.Grabber;

import java.util.ArrayList;

/**
 * Created by kareem on 30/03/17.
 */
public abstract class Grabber {
    private ArrayList<String> animeLink;

    public Grabber(ArrayList<String> animeLink) {
        this.animeLink = animeLink;
    }

    public ArrayList<String> getAnimeLink() {
        return animeLink == null ? new ArrayList<>() : animeLink;
    }
    /**
     @param  size  the expected size capacity of the list for better performance
     @throws IllegalArgumentException if the specified initial capacity
              is negative
     */
    public ArrayList<String> getAnimeLink(int size) {
        return animeLink == null ? new ArrayList<>(size) : animeLink;
    }
    /**
        abstract method bec different websites uses different patterns for url
        so have to override it for every website
        @param link the link to the desired website
     */
    protected abstract void enQueueAnimeLink(String link);
    protected String deQueueAnimeLink()
    {
       String link = animeLink.get(0);
       animeLink.remove(0);
       return link;
    }
    /** start Grabbing */
    public void startGrabbing()
    {
        new Thread(() -> {
            for (int i = 0; i <animeLink.size(); i++) {
                //deQueueAnimeLink()
                analyze(""); //TODO send website data
            }
        }).start();
    }
    //
    //Warning: this function will be called from a Thread other than the main thread
    protected abstract void analyze(String websiteData);

}
