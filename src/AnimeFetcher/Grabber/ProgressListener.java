package AnimeFetcher.Grabber;

import AnimeFetcher.Grabber.Downloader.Progress;

/**
 * Created by kemo on 29/05/2017.
 */
public interface ProgressListener {
    void onStart();
    void onFinish();
    void onFail();
}
