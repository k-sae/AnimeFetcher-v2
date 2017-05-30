package AnimeFetcher.Model;

import java.io.File;

/**
 * Created by kemo on 31/05/2017.
 */
public class FileManager {
    private static FileManager instance;
    private void FileManager(){}
    public static FileManager getInstance()
    {
       if (instance == null) instance = new FileManager();
        return instance;
    }
    public void createFolder(String folderName) {
        if (new File(folderName).exists()) return;
        String[] nestedFolders = folderName.split("/");
        String nextFolderLocation = "";
        for (int i = 0; i < nestedFolders.length; i++) {
            File Folder = new File(nextFolderLocation + nestedFolders[i]);
            //noinspection ResultOfMethodCallIgnored
            Folder.mkdir();
            nextFolderLocation += nestedFolders[i] + "/";
        }
    }
}
