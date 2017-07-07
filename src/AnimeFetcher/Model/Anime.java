package AnimeFetcher.Model;

/**
 * Created by kemo on 08/06/2017.
 */
public class Anime {
    private String name;
    private String url;
    private String epNo;
    private String cat;
    public Anime(String url) {
        this.url = url;
    }

    public Anime(String name, String url, String epNo) {
        this.name = name;
        this.url = url;
        this.epNo = epNo;
    }
    //TODO
    //      remove cat and use addAnimeAnime
    public Anime(String name, String url, String epNo, String cat) {
        this.name = name;
        this.url = url;
        this.epNo = epNo;
        this.cat = cat;
    }

    public Anime() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getEpNo() {
        return epNo;
    }

    public void setEpNo(String epNo) {
        this.epNo = epNo;
    }

    public String getCat() {

        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }
}
