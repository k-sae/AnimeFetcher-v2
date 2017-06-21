package AnimeFetcher.Grabber;

import AnimeFetcher.Grabber.Downloader.Downloader;
import AnimeFetcher.Model.Anime;
import AnimeFetcher.Model.FileManager;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kareem on 30/03/17.
 */
public abstract class Grabber {
    private ArrayList<Anime> animes;
    private int retryAfter;
    private ArrayList<ProgressListener> progressListeners;
     private Downloader downloader;
    public Grabber(ArrayList<Anime> animes) {
        this();
        this.animes = animes;
    }
    public Grabber()
    {
        animes = new ArrayList<>();
       progressListeners  = new ArrayList<>();
       downloader = new Downloader();
       downloader.setFileName("AddAnime_temp");
       downloader.setLocation("cache");
    }
    @SuppressWarnings("WeakerAccess")
    public ArrayList<Anime> getAnimes() {
        return animes;
    }
   /**
        @param anime the link to the desired website
     */
    public void enQueueAnimeLink(Anime anime)
    {
        animes.add(anime);
    }
    private Anime deQueueAnimeLink()
    {
        Anime link = animes.get(0);
       animes.remove(0);
       return link;
    }
    /** start Grabbing */
    public void startGrabbing()
    {
        new Thread(() -> {
            Anime anime = null;
            while (animes.size() != 0) {
                try {
                    //do it in two steps so when an exception is thrown no data is lost
                    anime = deQueueAnimeLink();
                    triggerOnStart();
                    analyze(getWebsiteData(anime.getUrl()), anime);
                }catch (CorruptedDataException e) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    updateCookies(anime.getUrl());
                    triggerOnFail();
                    if (anime != null)
                        enQueueAnimeLink(anime);
                }
                catch (IOException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    triggerOnFail();
                    if (anime != null)
                        enQueueAnimeLink(anime);
                }
            }
            triggerOnFinish();
        }).start();
    }

    /**
     * this function is called after downloading website data
     * WARNING this function will be called from a Thread other than the main thread
     * @param websiteData data resulted after de
     * @throws CorruptedDataException to request redownloading the website
     */
    protected abstract void analyze(String websiteData, Anime anime) throws CorruptedDataException;

    /**
     @throws IOException if connection error or empty response or whatever
     is negative
     */
    private String getWebsiteData(String url) throws IOException {
        if (!FileManager.getInstance().exists(downloader.DEFAULT_CACHE_LOCATION)) updateCookies(url);
            downloader.downloadWithCookies(url);
        return FileManager.getInstance().read(downloader.getLocation() + "/" + downloader.getFileName());

    }
    private void updateCookies(String url)
    {
        downloader.cacheCookies(url);
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

    /**
     * set a progress listener
     * @param progressListener
     */
    public void setProgressListener(ProgressListener progressListener)
    {
        progressListeners.add(progressListener);
    }
    private void triggerOnStart()
    {
        for (ProgressListener progressListener: progressListeners
                ) {
            progressListener.onStart();
        }
    }
    private void triggerOnFail()
    {
        for (ProgressListener progressListener: progressListeners
                ) {
            progressListener.onFail();
        }
    }
    private void triggerOnFinish()
    {
        for (ProgressListener progressListener: progressListeners
                ) {
            progressListener.onFinish();
        }
    }
}
