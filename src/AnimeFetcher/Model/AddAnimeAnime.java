package AnimeFetcher.Model;

import javax.naming.Name;

/**
 * Created by kemo on 01/06/2017.
 */
public class AddAnimeAnime {
    private String name;
    private String id;

    public AddAnimeAnime(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return name;
    }
}
