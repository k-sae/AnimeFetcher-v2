package AnimeFetcher.Grabber.Downloader;

/**
 * Created by kemo on 29/05/2017.
 */
public class Progress {
    private String percentage;
    private String timeRemaining;
    private String speed;
    public String getPercentage() {
        return percentage;
    }

    public void setPercentage(String percentage) {
        this.percentage = percentage;
    }

    public String getTimeRemaining() {
        return timeRemaining;
    }

    public void setTimeRemaining(String timeRemaining) {
        this.timeRemaining = timeRemaining;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }
}
