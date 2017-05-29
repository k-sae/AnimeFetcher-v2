package AnimeFetcher.Grabber.AddAnime;

import AnimeFetcher.Grabber.CorruptedDataException;
import AnimeFetcher.Grabber.Grabber;
import AnimeFetcher.Grabber.JSParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.*;
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
    private String downloadLocation;
    private boolean isInProgress;
    public AddAnimeGrabber() {
        videoType = VideoType.HighQuality;
        downloadLocation = "downloads";
    }


    @Override
    protected void analyze(String websiteData)  throws CorruptedDataException {
        JSParser jsParser = new JSParser(websiteData);
        if (videoType == VideoType.HighQuality) {
          startDownloading(jsParser.pickVariable("hq_video_file"));
        }
        else if (videoType == VideoType.Normal)
        {
            startDownloading(jsParser.pickVariable("normal_video_file"));
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
    private void startDownloading(String url)
    {
        try {
            // list with all params ti start wget
            ArrayList<String> params = new ArrayList<>(4);
            params.add("Wget/wget.exe");
            params.add("-c");
            params.add(url);
            params.add(downloadLocation);
            ProcessBuilder processBuilder = new ProcessBuilder(params);
            processBuilder.redirectErrorStream(true); // for some reason it will not work without it :0
            Process process = processBuilder.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            //start listening for wget
            while ((line = input.readLine()) != null) {
                //TODO
                //      1- trigger listeners up here
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public boolean isInProgress() {
        return isInProgress;
    }

}
