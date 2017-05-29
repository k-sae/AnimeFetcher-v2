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


public class AddAnimeGrabber extends Grabber {
    public AddAnimeGrabber(ArrayList<String> animeLink) {
        super(animeLink);
    }
    private VideoType videoType;
    private String downloadLocation;
    public AddAnimeGrabber() {
        videoType = VideoType.HighQuality;
        downloadLocation = "/downloads";
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
    private boolean startDownloading(String url)
    {
        Runtime runtime = Runtime.getRuntime();
        try {
//            Process process = runtime.exec("Wget/wget.exe -c " + url + " " + downloadLocation);
            Process process = runtime.exec("dir");
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = input.readLine()) != null) {
                result.append("\n").append(line);
                System.out.println(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
