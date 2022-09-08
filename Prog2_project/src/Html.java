import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;


public class Html {
    //Class variables
    private static List<File> listOfHtmls;
    private static List<Path> html_navigation;
    private static String startPageforHTML="";
    private static String startPageforIndexHtml="";
    private static List<File> directories;
    private static List<Path> images;
    private static String basedirectory;
    //Constructor
    private Html() {
        //Nem lehet példányosítani
    }

    public static void listDirs(List<File> dir) {
        directories = new ArrayList<>();
        for (File dirs : dir) {
            directories.add(dirs);
        }

    }
    public static void listFiles(List<File> list, String basedir) {
        listOfHtmls = new ArrayList<>();
        images = new ArrayList<>();
        basedirectory = basedir;
        html_navigation = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            File[] imgs = list.get(i).listFiles();
            for (File img : imgs) {
                if (img.isFile()) {
                    String absolute_name = img.getAbsolutePath();
                    String[] absolute_parts = absolute_name.split("\\.");
                    String raw_name = absolute_parts[0];
                    String extension = absolute_parts[1];
                    if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif")) {
                        Path pict_name = Paths.get(img.toString());
                        Path pictures = pict_name.getFileName();
                        images.add(pictures);
                        File cpy = new File(raw_name +".html");
                        Path html = Paths.get(cpy.toString());
                        Path html_final = html.getFileName();
                        html_navigation.add(html_final);
                        listOfHtmls.add(cpy);
                    }
                }
            }

        }
        //System.out.println(images);
    }
    public static void createHtmlPages() {
        for (int i = 0; i < listOfHtmls.size(); i++) {
            String absolute_path = listOfHtmls.get(i).getAbsolutePath();
            Path startPage = Paths.get(absolute_path);
            Path basedir = Paths.get(basedirectory);
            int basedircounter = basedir.getNameCount();
            int currentdir = startPage.getNameCount();
            startPageforHTML ="";
            for(int j = 0; j < (currentdir-basedircounter)-1; j++)
            {
                startPageforHTML += "../";
            }
            int hop = 1;
            int hop2 = 1;
            if (listOfHtmls.size() == 1) {
                hop = 0;
                hop2 = 0;
            } else {
                if (i == listOfHtmls.size() - 1) {
                    hop = 1;
                    hop2 = 0;

                }
                if (i == 0) {
                    hop = 0;
                    hop2 = 1;
                }
            }
            Path path= Paths.get(listOfHtmls.get(i).toString());
            Path html_name = path.getFileName();
            String jpg = html_name.toString();
            String[] parts = jpg.split("\\.");
            parts[1] = "jpg";
            String img = "<img src=" + "\"./" +images.get(i) +"\""+ "style = \" width: 500px; height: 600px;\"" + ">";
            String title = "<html>\n" +
                    "\t<head>\n" +
                    "\t\t<h1><a href = " + "\"" + startPageforHTML+"index.html" + "\" " + ">Start Page</h1> </a>\n" +
                    "\t</head>" + "<hr>\n" +
                    "\t<body>\n" +
                    "\t\t<h1>"+"<a href =\"./index.html\""+"> ^^ </a></h1>\n"+
                    "\t\t<h1>" + "<a href=" + "\"" + html_navigation.get(i - hop) + "\"" + ">" + " << " + "</a>"
                    +images.get(i) +
                    "<a href=" + "\"" + html_navigation.get(i + hop2) + "\">" + " >>" + " </a>" + "</h1>\n"
                    +"\t\t<a href=" + "\"" + html_navigation.get(i + hop2) + "\">" + img + "</a>\n\t</body>\n</html>";
            File copyOfFile = new File(absolute_path);
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(copyOfFile));
                bw.write(title);
                bw.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void createIndexHtml_page() {
        for (int i = 0; i < directories.size(); i++) {
            File[] listdir = directories.get(i).listFiles();
            String list_dict = "";
            String list_img = "";
            startPageforIndexHtml ="";
            if(i!=0) {
                Path startPage = Paths.get(directories.get(i).toString());
                int currentdir = startPage.getNameCount();
                Path basedir = Paths.get(basedirectory);
                int basedircounter = basedir.getNameCount();
                for (int j = 0; j < (currentdir-basedircounter); j++)
                {
                    startPageforIndexHtml += "../";
                }
            }
            if(listdir!=null && listdir.length >0) {
                for (File aDir : listdir) {
                    if (aDir.isDirectory()) {
                        Path dir_path = Paths.get(aDir.toString());
                        int last_dir = dir_path.getNameCount();
                        Path dir_final = dir_path.getName(last_dir - 1);
                        Path root = dir_path.getRoot();
                        String root2 = root.toString().replace("\\", "/");
                        list_dict += "\t\t\t<li><h2><a href=" + "\"" + dir_final + "/index.html" + "\">" + dir_final + "</a></h2>\n";
                    }
                    if (aDir.isFile()) {
                        String filename_str = aDir.getAbsolutePath();
                        Path check = Paths.get(filename_str);
                        Path isMakefile = check.getFileName();
                        String makefile_str = isMakefile.toString();
                        if (!(makefile_str.equals("Makefile"))) {
                            //System.out.println(filename_str);
                            String[] parts = filename_str.split("\\.");
                            String extension = parts[1];
                            Path path = Paths.get(filename_str);
                            Path fileName = path.getFileName();
                            String str_fn = fileName.toString();
                            String[] parts2 = str_fn.split("\\.");
                            //System.out.println(extension);
                            if (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif")) {
                                list_img += "\t\t\t<li> <h2> <a href=" + "\"" + "./" + parts2[0] + ".html" + "\">" + fileName + "</a></h2>\n";
                            }
                        }
                    }

                }
            }
            String result = "\t\t<ul>\n" + list_dict + "\t\t</ul><hr>\n";
            String result2 = "\t\t<ul>\n" + list_img + "\t\t</ul>\n\t</body>\n</html>\n";
            File cpy_File = directories.get(i);
            File index_html = new File(cpy_File + "\\index.html");
            String html = "<html>\n\t<head>\n\t\t<h1><a href=" + "\""+startPageforIndexHtml+"index.html"+"\"" + ">" + "Start Page </a></h1>\n\t</head> <hr>\n" + "\t<body>\n\t\t<h1>Directories:</h1>\n";
            String str_img = "\t\t<h1>Images:</h1>\n";
            String backward="";
            if(i !=0)
            {
                backward = "\t\t<ul><li><a href=\"../index.html\"> << </a></li></ul>\n";
            }
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(index_html));
                bw.write(html);
                bw.write(backward);
                bw.write(result);
                bw.write(str_img);
                bw.write(result2);
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

