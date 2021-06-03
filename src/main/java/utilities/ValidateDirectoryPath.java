package utilities;

import java.io.File;

public class ValidateDirectoryPath {
    public boolean isValidDirectoryPath(String path) {
        return (new File(path).isDirectory()) ? true : false;
    }
}
