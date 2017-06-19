package AnimeFetcher.Grabber.Downloader;

import AnimeFetcher.Grabber.ProgressListener;

/**
 * Created by kemo on 29/05/2017.
 */
public interface DownloadProgressListener extends ProgressListener {
    void reportProgress(Progress progress);
}
