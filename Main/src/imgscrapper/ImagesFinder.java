package imgscrapper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * @author Tomasz Pilarczyk
 */
public class ImagesFinder implements Runnable {

    private static String URL = "http://imgur.com/search/score/";
    public static int albumCount = (Main.frame.albumCountComboBox.getSelectedIndex() + 1);
    private FormControls controls = new FormControls();
    private ImagesDownloader imagesDownloader = new ImagesDownloader();
    private String search;
    public static boolean threadStopper=false;

    ImagesFinder(String search) {
        this.search = search;
    }

    public static void stop() {
        threadStopper=true;
    }
    public void run() {
        threadStopper=false;
        findAndGetImages(search);
    }


    private void findAndGetImages(String searchPhrase) {
        String timePeriod = (String) Main.frame.timePeriodComboBox.getSelectedItem();
        String webSiteURL = URL + timePeriod + "?q_not=gif&q_type=album&q_all=" + searchPhrase;

        controls.appendMessageToLogger("Starting...\n");
        org.jsoup.nodes.Document htmlSource = getHtmlSource(webSiteURL);
        Elements albumsWithImgTag = htmlSource.select("[class=image-list-link]");
        enableStopButton();
        getAlbums(albumsWithImgTag);
        controls.appendMessageToLogger("Done");
        disableStopButton();

    }

    private org.jsoup.nodes.Document getHtmlSource(String url) {
        try {
            return Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:25.0) Gecko/20100101 Firefox/25.0")
                    .get();
        } catch (IOException err) {
            controls.appendMessageToLogger("Can't connect to imgur");
            return null;
        }
    }

    private void enableStopButton() {
        Main.frame.stopButton.setEnabled(true);
    }

    private void disableStopButton() {
        Main.frame.stopButton.setEnabled(false);
    }

    private void getAlbums(Elements albumsWithImgTag) {
        int albumCounter = 0;

        checkIfEmpty(albumsWithImgTag);

        for (Element album : albumsWithImgTag) {
            if (albumCounter++ == albumCount) break;
            if(threadStopper)break;

            String albumLink = album.attr("href");
            org.jsoup.nodes.Document albumHtmlSource = getHtmlSource("http://imgur.com" + albumLink);
            Elements imagesWithImgTags = albumHtmlSource.getElementsByTag("img");

            controls.appendMessageToLogger("Album found in http://imgur.com" + albumLink + "\n");
            controls.appendMessageToLogger("Contains " + countImagesInAlbum(imagesWithImgTags) + " images\n");

            downloadAllImages(imagesWithImgTags);
        }
    }

    private void checkIfEmpty(Elements albumsWithImgTag){
        if(albumsWithImgTag.isEmpty()){
            controls.appendMessageToLogger("Found 0 albums... Try diffrent keyword\n");
        }
    }

    private int countImagesInAlbum(Elements images) {
        int imgCount = 0;

        for (Element el : images) {
            if(threadStopper)break;
            String imageLink = el.attr("src");
            if (imageLink.contains("i.imgur.com")) imgCount++;
        }

        return imgCount;
    }

    private void downloadAllImages(Elements htmlImagesTags) {
        try {
            for (Element image : htmlImagesTags) {
                if(threadStopper)break;
                String imageLink = image.attr("src");
                if (imageLink.contains("i.imgur.com")) {
                    imageLink = "http:" + imageLink;
                    controls.appendMessageToLogger("--Downloading: " + imageLink + "\n");
                    imagesDownloader.getImage(imageLink);
                }
            }
        } catch (IOException err) {
            controls.appendMessageToLogger("Error occurred while downloading images");
        }
    }
}
