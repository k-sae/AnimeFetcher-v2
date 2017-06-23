package AnimeFetcher.Model;

import com.google.gson.Gson;

import java.io.File;

/**
 * Created by kareem on 6/23/17.
 */
public class Config {
    private String downloadLocation;
    private static Config instance;
    public Config()
    {
        downloadLocation = "downloads";
    }

    public String getDownloadLocation() {
        return downloadLocation;
    }

    public void setDownloadLocation(String downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

    public static Config getInstance() {
        instance =  instance == null ? new Config() : instance;
        return instance;
    }

    public synchronized void importFromFile(String file){
        Config config =  new Gson().fromJson(new FileManager().read(file), Config.class);
        //implement the rest of values up here
        if (config == null) return;
        this.downloadLocation= config.getDownloadLocation();
    }
    public synchronized void exportToFile(String file)
    {
        new FileManager().write(file, new Gson().toJson(this));
    }
}
