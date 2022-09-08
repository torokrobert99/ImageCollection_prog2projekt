import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class Directories {
    //Class variables
    public static List<File> list;
    public static List<File> directories = new ArrayList<>();
    public static boolean checkbasedir = true;
    public static String basedir;

    //Constructor
    private Directories() {
        //Nem lehet példányosítani
    }

    public static void listDirectory(File fname) {
        //adding basedir to directories list, but only once
        if (checkbasedir) {
            basedir = fname.toString();
            directories.add(fname);
            checkbasedir = false;
        }
        //System.out.println(basedir);
        //Adding elements to directories list
        File[] firstLevelFiles = fname.listFiles();
        String Dname = "";
        if (firstLevelFiles != null && firstLevelFiles.length > 0) {
            for (File aFile : firstLevelFiles) {
                if (aFile.isDirectory()) {
                    directories.add(aFile);
                    Dname = aFile.getAbsolutePath();
                    System.out.println(Dname);
                    list = new ArrayList<>();
                    //System.out.println(list);
                    list.add(aFile);
                }
                //calling methods
                Html.listDirs(directories);
                Html.listFiles(list, basedir);
                Html.createHtmlPages();
                fname = new File(Dname);
                listDirectory(fname);
            }
        }
        //System.out.println(directories);
    }
}

