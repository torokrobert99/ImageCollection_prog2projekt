import java.io.*;
public class Main {

    public static void main(String[] args) {
        //Check commandline argument
       if(args.length != 1)
       {
           System.out.println("Error: Only one arguments allowed!");
           System.exit(2);
       }
       File fname = new File(args[0]);
       if(!fname.isDirectory())
       {
           System.out.println("Error: provide a path of a directory!");
           System.exit(1);
       }
       // System.out.println("Base dir: " + fname);
        Directories.listDirectory(fname);
        Html.createIndexHtml_page();


    }
}
