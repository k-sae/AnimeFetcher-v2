package AnimeFetcher.Grabber.Downloader;

import AnimeFetcher.Grabber.Downloader.DownloadProgressListener;
import AnimeFetcher.Model.FileManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by kemo on 29/05/2017.
 */
public class Downloader {
    private ArrayList<DownloadProgressListener> downloadProgressListeners;
    private String location;
    private String fileName;
    public Downloader() {
        downloadProgressListeners = new ArrayList<>();
        location = "downloads";
        fileName = "video.mp4";
    }
    public void startDownloading(String url)
    {
        FileManager.getInstance().createFolder(location);
        try {
            // list with all params ti start wget
            ArrayList<String> params = new ArrayList<>(4);
            params.add("Wget/wget.exe");
            params.add("-O");
            params.add(location + "/" + fileName);
            params.add("-c");
            params.add(url);
            triggerOnStart();
            ProcessBuilder processBuilder = new ProcessBuilder(params);
            processBuilder.redirectErrorStream(true); // for some reason it will not work without it :0
            Process process = processBuilder.start();
            BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            //start listening for wget
            while ((line = input.readLine()) != null) {
               Progress progress = parseProgress(line);
               if (progress != null)
                triggerOnProgress(progress);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
       triggerOnFinish();
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
    public void addProgressListener(DownloadProgressListener downloadProgressListener)
    {
        downloadProgressListeners.add(downloadProgressListener);
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

    private void triggerOnProgress(Progress progress)
    {
        for (DownloadProgressListener downloadProgressListener : downloadProgressListeners
                ) {
            downloadProgressListener.reportProgress(progress);
        }
    }
    private void triggerOnFinish() {
        for (DownloadProgressListener downloadProgressListener : downloadProgressListeners
                ) {
            downloadProgressListener.onFinish();
        }
    }
    private void triggerOnStart()
    {
        for ( DownloadProgressListener downloadProgressListener : downloadProgressListeners
                ) {
            downloadProgressListener.onStart();
        }
    }
}