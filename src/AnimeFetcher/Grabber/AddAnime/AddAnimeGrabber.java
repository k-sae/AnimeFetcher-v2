package AnimeFetcher.Grabber.AddAnime;

import AnimeFetcher.Grabber.CorruptedDataException;
import AnimeFetcher.Grabber.Downloader.Downloader;
import AnimeFetcher.Grabber.Grabber;
import AnimeFetcher.Grabber.JSParser;
import AnimeFetcher.Model.AddAnimeAnime;
import javafx.beans.value.ChangeListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kareem on 30/03/17.
 */


/* demo on how To use Jsoup

        int index = 0;
        Document document = Jsoup.parse(websiteData);

        // now u the full document loaded
        Document document = Jsoup.parse(websiteData);
        // select an first <a> element
        Element element = document.select("a").first();

        // get element at specific index
        Element element = document.select("a").get(index);
        // a trick :)
        while (!element.hasClass("someClass"))
        {
            index++;
            element = document.select("a").get(index);
        }
*/

// i think this should be singleton
    // decide later :)
public class AddAnimeGrabber extends Grabber {
    public AddAnimeGrabber(ArrayList<String> animeLink) {
        super(animeLink);
    }
    private VideoType videoType;
    private boolean isInProgress;
    private Downloader downloader;
    private ArrayList<AddAnimeAnime> addAnimeAnimes;
    private ArrayList<ChangeListener<ArrayList<AddAnimeAnime>>> changeListeners;
    public AddAnimeGrabber() {
        videoType = VideoType.HighQuality;
        downloader = new Downloader();
        changeListeners = new ArrayList<>();
        // i have used this initial value to prevent the recreation of array many times
        //as iam sure the anime list will exceeds this value
        //TODO
        //      1- Modify it later or may add the initialization in the update list function
        addAnimeAnimes = new ArrayList<>(2400);
    }
    public void updateAnimeList()
    {
        Grabber grabber = new Grabber() {
            @Override
            protected void analyze(String websiteData) throws CorruptedDataException {
                addAnimeAnimes.clear();
                Document document = Jsoup.parse(websiteData);
                Elements s = document.select("form").last().select("select").first().select("option");
                for (int i = 0; i < s.size(); i++) {
                    Element element = s.get(i);
                    addAnimeAnimes.add(new AddAnimeAnime(element.attr("label"),element.attr("value" )));
                }
                for (ChangeListener<ArrayList<AddAnimeAnime>> arrayListChangeListener: changeListeners
                     ) {
                    arrayListChangeListener.changed(null,null, addAnimeAnimes);
                }
            }

            @Override
            protected URL fetchUrl(String animeLink) {
                try {
                    return new URL(animeLink);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
        grabber.enQueueAnimeLink("http://add-anime.net/");
        grabber.startGrabbing();
    }
    @Override
    protected void analyze(String websiteData)  throws CorruptedDataException {
        System.out.println(websiteData);
        JSParser jsParser = new JSParser(websiteData);
        if (videoType == VideoType.HighQuality) {
            downloader.startDownloading(jsParser.pickVariable("hq_video_file"));
        }
        else if (videoType == VideoType.Normal)
        {
            downloader.startDownloading(jsParser.pickVariable("normal_video_file"));
        }
    }

    @Override
    protected URL fetchUrl(String animeLink) {
        try {
            return new URL(animeLink);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void setOnListChangeListener(ChangeListener<ArrayList<AddAnimeAnime>> changeListener)
    {
        changeListeners.add(changeListener);
    }

    public VideoType getVideoType() {
        return videoType;
    }

    public void setVideoType(VideoType videoType) {
        this.videoType = videoType;
    }
    public boolean isInProgress() {
        return isInProgress;
    }

    public Downloader getDownloader() {
        return downloader;
    }
}
