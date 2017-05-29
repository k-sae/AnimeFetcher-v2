package AnimeFetcher.Grabber.AddAnime;

import AnimeFetcher.Grabber.CorruptedDataException;
import AnimeFetcher.Grabber.Grabber;
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
    int page = 0;
    public AddAnimeGrabber() {
    }


    @Override
    protected void analyze(String websiteData)  throws CorruptedDataException {
        try {
            BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("file" + page++ + ".html"));
            bufferedWriter.write(websiteData);
            bufferedWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
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
}
