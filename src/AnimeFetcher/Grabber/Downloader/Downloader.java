package AnimeFetcher.Grabber.Downloader;

import AnimeFetcher.Model.FileManager;

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
    private String fileName;
    public Downloader() {
        progressListeners = new ArrayList<>();
        location = "downloads";
        fileName = "video.mp4";

    }
    public void startDownloading(String url)
    {
        FileManager.getInstance().createFolder(location);
        try {
            // list with all params ti start wget
            ArrayList<String> params = new ArrayList<>(4);
            params.add(getBasicDownloader());
            params.add("-O");
            params.add(location + "/" + fileName);
            params.add("-c");
            params.add(url);
            ProcessBuilder processBuilder = new ProcessBuilder(params);
            processBuilder.redirectErrorStream(true); // for some reason it will not work without it :0
            Process process = processBuilder.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            //start listening for wget
            while ((line = input.readLine()) != null) {
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
        //TODO
        //      1- trigger the on finish listener
        for ( ProgressListener progressListener: progressListeners
             ) {
            progressListener.onFinish();
        }
    }

    private String getBasicDownloader()
    {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("lin")) return "wget";
        else return "Wget/wget.exe";
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


    /**
     * set Folder download Location
     * u can use folder absolute or relative location
     * if folder doesn't exist it will be created automatically
     * @param location ex: downloads/videos
     */
    public void setLocation(String location) {
        this.location = location;
    }

    public String getFileName() {
        return fileName;
    }

    /**
     * specify file name with extension
     * @param fileName ex: example.mp4
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
