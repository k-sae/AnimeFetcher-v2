package AnimeFetcher.Model;

import javax.naming.Name;

/**
 * Created by kemo on 01/06/2017.
 */
public class AddAnimeAnime extends Anime {
    private String id;

    public AddAnimeAnime(String name, String id) {
        setName(name);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getName();
    }
}
