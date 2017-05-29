package AnimeFetcher.Grabber;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by kemo on 29/05/2017.
 */
public class Downloader {
    private ArrayList<ProgressListener> progressListeners;
    private String location;
    public Downloader() {
        progressListeners = new ArrayList<>();
        location = "";
    }
    public void startDownloading(String url)
    {
        try {
            // list with all params ti start wget
            ArrayList<String> params = new ArrayList<>(4);
            params.add("Wget/wget.exe");
            params.add("-c");
            params.add(url);
            params.add(location);
            ProcessBuilder processBuilder = new ProcessBuilder(params);
            processBuilder.redirectErrorStream(true); // for some reason it will not work without it :0
            Process process = processBuilder.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            //start listening for wget
            while ((line = input.readLine()) != null) {
                //TODO
                //      1- trigger listeners up here
               Progress progress = parseProgress(line);
               if (progress != null)
                for (ProgressListener progressListener: progressListeners
                     ) {
                    progressListener.reportProgress(progress);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private Progress parseProgress(String s)
    {
        final String PROGRESS_SCHEMA = ".......... .......... .......... .......... ..........";
        if (s.contains(PROGRESS_SCHEMA))
        {
           s = s.substring(s.indexOf(PROGRESS_SCHEMA) + PROGRESS_SCHEMA.length());
           s = s.replace("  ", " ");
           String[] strings = s.split(" ");
            Progress progress = new Progress();
            progress.setPercentage(strings[1]);
            progress.setSpeed(strings[2]);
            progress.setTimeRemaining(strings[3]);
            return progress ;
        }
        return null;
    }
    public void addProgressListener(ProgressListener progressListener)
    {
        progressListeners.add(progressListener);
    }
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
