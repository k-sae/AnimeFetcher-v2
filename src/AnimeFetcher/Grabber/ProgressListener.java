package AnimeFetcher.Grabber;

/**
 * Created by kemo on 29/05/2017.
 */
public interface ProgressListener {
    void reportProgress(Progress progress);
    void onFinish();
}
