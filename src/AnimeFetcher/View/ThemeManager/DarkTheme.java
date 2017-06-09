package AnimeFetcher.View.ThemeManager;

/**
 * Created by kemo on 02/06/2017.
 */
public class DarkTheme extends ThemeManager {
    public DarkTheme()
    {
        //Title bar
        setTitleBarBackground("#000");
        setTitleBarForeground("#000"); //change this to Golded
        setAnimePaneBackground("#000");

        // Anime bar
        setAnimeBarBackground("#222");
        setAnimeActiveBarBackground("#fff"); // this to Orange or blue
        setAnimeBarForeground("#fff");
        setAnimeActiveBarForeground("#000");

        //AnimeContent
        setAnimeWebsiteContent("#222");

        //input prompts
        setInputPromptBackground("#666");
        setInputPromptForeground("#fff");

        //Button
        setButtonBackground("#666");
        setButtonForeground("#fff");

        //global
        setForeground("fff");
    }
}
