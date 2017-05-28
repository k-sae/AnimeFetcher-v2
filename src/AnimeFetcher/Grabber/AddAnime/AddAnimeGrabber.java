package AnimeFetcher.Grabber.AddAnime;

import AnimeFetcher.Grabber.CorruptedDataException;
import AnimeFetcher.Grabber.Grabber;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by kareem on 30/03/17.
 */
public class AddAnimeGrabber extends Grabber {
    public AddAnimeGrabber(ArrayList<String> animeLink) {
        super(animeLink);
    }

    public AddAnimeGrabber() {
    }


    @Override
    protected void analyze(String websiteData)  throws CorruptedDataException {
//        try {
//            BufferedWriter bufferedWriter = new BufferedWriter();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
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
