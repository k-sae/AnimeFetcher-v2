package AnimeFetcher.Grabber.AddAnime;

import AnimeFetcher.Grabber.CorruptedDataException;
import AnimeFetcher.Grabber.Downloader.Downloader;
import AnimeFetcher.Grabber.Grabber;
import AnimeFetcher.Grabber.JSParser;
import AnimeFetcher.Model.AddAnimeAnime;
import AnimeFetcher.Model.Anime;
import AnimeFetcher.Model.Config;
import javafx.beans.value.ChangeListener;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.Serializable;
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
public abstract class AddAnimeGrabber extends Grabber {
    public AddAnimeGrabber(ArrayList<Anime> animes) {
        super(animes);
    }
    private VideoType videoType;
    private boolean isInProgress;
    private Downloader downloader;
    private ArrayList<AddAnimeAnime> addAnimeAnimes;
    private ArrayList<ChangeListener<ArrayList<AddAnimeAnime>>> changeListeners;

    private String animeName;
    private int startEp;
    private int numberingSchema;
    public AddAnimeGrabber() {
        videoType = VideoType.HighQuality;
        downloader = new Downloader();
        downloader.setAutoResume(true);
        changeListeners = new ArrayList<>();
        isInProgress = false;
        // i have used this initial value to prevent the recreation of array many times
        //as iam sure the anime list will exceeds this value
        //TODO
        //      1- Modify it later or may add the initialization in the update list function
        addAnimeAnimes = new ArrayList<>(2400);
        numberingSchema = 3;
    }
    public void updateAnimeList()
    {
        Grabber grabber = new Grabber() {
            @Override
            protected void analyze(String websiteData, Anime anime) throws CorruptedDataException {
                addAnimeAnimes.clear();
                try {
                    Document document = Jsoup.parse(websiteData);
                    Elements s = document.select("form").last().select("select").first().select("option");
                    for (int i = 0; i < s.size(); i++) {
                        Element element = s.get(i);
                        addAnimeAnimes.add(new AddAnimeAnime(element.attr("label"), element.attr("value")));
                    }
                    for (ChangeListener<ArrayList<AddAnimeAnime>> arrayListChangeListener : changeListeners
                            ) {
                        arrayListChangeListener.changed(null, null, addAnimeAnimes);
                    }
                }catch (NullPointerException ignored)
                {
                    onAnimeListUpdateFailure();
                    throw  new CorruptedDataException();
                }
            }

            @Override
            protected String fetchUrl(Anime animeLink) {
                return animeLink.getUrl();
            }
        };
        grabber.enQueueAnimeLink(new Anime("http://add-anime.net/"));
        grabber.startGrabbing();
    }
    @Override
    protected void analyze(String websiteData, Anime anime)  throws CorruptedDataException {
       if (!validateTheWebsite(websiteData)) throw  new CorruptedDataException() ;
        JSParser jsParser = new JSParser(websiteData);
        downloader.setLocation(Config.getInstance().getDownloadLocation() + "/" + anime.getName());
        downloader.setFileName(anime.getName() + " " + anime.getEpNo() + ".mp4");
        if (videoType == VideoType.HighQuality) {
            String s = jsParser.pickVariable("hq_video_file");
            if (s == null) throw new CorruptedDataException();
            downloader.startDownloading(s);
        }
        else if (videoType == VideoType.Normal)
        {
            downloader.startDownloading(jsParser.pickVariable("normal_video_file"));
        }
    }

    @Override
    protected String fetchUrl(Anime animeLink) {
            return animeLink.getUrl()
                    + "last=" + getEpisodeNo(Integer.valueOf(animeLink.getEpNo()))
                    + "&cat=" + animeLink.getCat()
                    + ",";
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

    public int getStartEp() {
        return startEp;
    }

    public void setStartEp(int startEp) {
        this.startEp = startEp;
    }

    public String getAnimeName() {
        return animeName;
    }

    public void setAnimeName(String animeName) {
        this.animeName = animeName;
    }
    public abstract void onAnimeListUpdateFailure();

    //TODO
    public boolean validateTheWebsite(String website)
    {
        Document document = Jsoup.parse(website);
         Elements elements =  document.select("a");
        for (Element element: elements
             ) {
            if (element.hasClass("btn btn-small btn-danger"))
            {
                String val =  element.attr("href");
                val = val.substring(val.indexOf("last=") + 5);
               val =  val.substring(0, val.indexOf("&"));
                if (val.length() != numberingSchema)
                {
                    numberingSchema = val.length();
                    return false;
                }
                else return true;
            }

        }
        return true;
    }

    private String getEpisodeNo(int num)
    {
        if (num < 10 && numberingSchema == 3)
        {
            return "00" + num;
        }
        else if(num < 10 && numberingSchema == 2)
        {
            return  "0" + num;
        }
        else if (num < 100 && numberingSchema == 3)
        {
            return  "0" + num;
        }
        else return num + "";
    }

}
