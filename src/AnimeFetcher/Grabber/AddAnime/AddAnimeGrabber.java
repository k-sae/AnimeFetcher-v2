package AnimeFetcher.Grabber.AddAnime;

import AnimeFetcher.Grabber.CorruptedDataException;
import AnimeFetcher.Grabber.Downloader.Downloader;
import AnimeFetcher.Grabber.Grabber;
import AnimeFetcher.Grabber.JSParser;

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

    public AddAnimeGrabber() {
        videoType = VideoType.HighQuality;
        downloader = new Downloader();
    }


    @Override
    protected void analyze(String websiteData)  throws CorruptedDataException {
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
