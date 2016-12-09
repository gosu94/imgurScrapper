package imgscrapper;

import java.io.*;
import java.net.URL;

/**
 * @author Tomasz Pilarczyk
 */
public class ImagesDownloader {

    public static String folderPath = "";

    public void getImage(String src) throws IOException {
        String imageName = extractImageName(src);
        downloadImage(src, imageName);
    }

    public String extractImageName(String src) {
        int indexOfLastSlash = src.lastIndexOf("/");
        return src.substring(indexOfLastSlash, src.length());
    }

    public void downloadImage(String src, String name) throws IOException {
        try {
            URL url = new URL(src);
            InputStream in = url.openStream();
            OutputStream out = new BufferedOutputStream(new FileOutputStream(folderPath + name));

            for (int b; (b = in.read()) != -1; ) {
                out.write(b);
            }
            out.close();
            in.close();
        } catch (IOException err) {
            FormControls controls = new FormControls();
            controls.appendMessageToLogger("Error occurred while saving images");
        }
    }
}
