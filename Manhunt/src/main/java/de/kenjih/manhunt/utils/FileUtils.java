package de.kenjih.manhunt.utils;

import java.io.File;
import java.io.IOException;

public final class FileUtils {


    public File folder = new File("plugins/Manhunt");
    public File file = new File("plugins/Manhunt/locations.yml");

    public void createFiles(){

        if(!folder.exists())
            return;
        folder.mkdir();

            if(!file.exists()){
                try {
                    file.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
    }
}