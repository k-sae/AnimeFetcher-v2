
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
    private ArrayList<DownloadProgressListener> downloadProgressListeners;
    private String location;
    private String fileName;
    private ArrayList<String> params;
    private String downloader;

    public final String DEFAULT_CACHE_LOCATION = "cache/cookies";
    public Downloader() {
        downloadProgressListeners = new ArrayList<>();
        location = "downloads";
        fileName = "video.mp4";
        params = new ArrayList<>();
        downloader = getBasicDownloader();
        params.add(downloader);
    }
    public void startDownloading(String url)
    {
        FileManager.getInstance().createFolder(location);
        try {
            // list with all params ti start wget
            params.add("-O");
            params.add(location + "/" + fileName);
            params.add("-c");
            params.add(filterUrl(url));
            triggerOnStart();
            Process process = startDownloader();
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
        clearParams();
       triggerOnFinish();
    }
    private Progress parseProgress(String s)
    {
        final String PROGRESS_SCHEMA = ".......... .......... .......... .......... ..........";
        try {
            System.out.println(s);
            if (s.contains(PROGRESS_SCHEMA)) {
                s = s.substring(s.indexOf(PROGRESS_SCHEMA) + PROGRESS_SCHEMA.length());
                s = s.replace("  ", " ");
                String[] strings = s.split(" ");
                Progress progress = new Progress();
                progress.setPercentage(strings[1]);
                progress.setSpeed(strings[2]);
                progress.setTimeRemaining(strings[3]);
                return progress;
            }
        }catch (Exception e)
        {
//            e.printStackTrace();
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
    private String getBasicDownloader()
    {
        String s = System.getProperty("os.name").toLowerCase();
        if (s.contains("lin")) return "wget";
        else return "Wget/wget.exe";
    }
    public void cacheCookies(String url)
    {
        cacheCookies(url, DEFAULT_CACHE_LOCATION);
    }
    public void cacheCookies(String url, String location)
    {
        params.add("--save-cookies");
        params.add(location);
        params.add("--keep-session-cookies");
        params.add("--delete-after");
        params.add(url);
        try {
            startDownloader().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        clearParams();
    }
    private Process startDownloader() throws IOException {
        ProcessBuilder processBuilder = new ProcessBuilder(params);
        processBuilder.redirectErrorStream(true); // for some reason it will not work without it :0
        return processBuilder.start();
    }
    private void clearParams()
    {
        params.clear();
        params.add(downloader);
    }
    public void downloadWithCookies(String url)
    {
        downloadWithCookies(url,DEFAULT_CACHE_LOCATION);
    }
    public void downloadWithCookies(String url,String cookiesLocation)
    {
        params.add("--load-cookies");
        params.add(cookiesLocation);
        startDownloading(url);
    }
    //TODO
    //      test it on windows
    private String filterUrl(String url)
    {
        if (url.contains("&"))
        {
           url = url.replace("&", "\\&");
        }
        return url;
    }
}
