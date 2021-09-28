package com.alex.springmusicproject.service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Service
public class ImageService {
    private static final Logger logger = LoggerFactory.getLogger(ImageService.class);

    public BufferedImage getImage(String band) {
        BufferedImage image = null;
        try {
            URIBuilder builder = new URIBuilder("https://bing-image-search1.p.rapidapi.com/images/search");
            builder.addParameter("q", band);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(builder.build())
                    .header("x-rapidapi-host", "bing-image-search1.p.rapidapi.com")
                    .header("x-rapidapi-key", "6da07acd0fmsh1140767c8902cbdp10083ejsn501e7544739c")
                    .method("GET", HttpRequest.BodyPublishers.noBody())
                    .build();
            HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
            String url = parseResponse(response.body());
            image = ImageIO.read(new URL(url));
            logger.info("Image " + band + " was loaded");
        } catch (IOException | InterruptedException | URISyntaxException e) {
            logger.warn("Error getting response from api");
        }

        return image;
    }

    private String parseResponse(String response) {
        JsonObject object = new Gson().fromJson(response, JsonObject.class);
        String url = object.get("value")
                .getAsJsonArray()
                .get(0)
                .getAsJsonObject()
                .get("thumbnailUrl")
                .getAsString();

        return url;
    }
}
