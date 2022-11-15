package Practicing;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.Objects;

class onlyExt implements FilenameFilter{
    String ext ;
    public onlyExt(String ext){
        this.ext = ext;
    }

    @Override
    public boolean accept(File file, String s) {
        return s.endsWith(ext);
    }
}
public class Dirlist {
    public static void main(String [] args) {
        File file = new File("/src");
        file.mkdir();
        FilenameFilter filter = new onlyExt(".xml");
        String[] filelist = file.list();
        for(int i = 0; i < Objects.requireNonNull(filelist).length ; ++i)
            System.out.println(filelist[i]);
    }
}

