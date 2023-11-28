package com.ads.google;

import com.ads.google.config.AppProperties;
import com.ads.google.service.GoogleAdsService;
import com.ads.google.utils.GoogleAdsUtils;
import com.google.ads.googleads.v15.services.MutateGoogleAdsResponse;
import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.file.Paths;
import java.util.List;

@SpringBootTest
class GoogleAdsApplicationTests {

    @Autowired
    private AppProperties appProperties;
    @Autowired
    private GoogleAdsService service;

    @DisplayName("Test add ad")
    @Test
    public void testAddAdFromBudgetToAd() {
        Assertions.assertDoesNotThrow(() ->{
            String landscapeFileName = "landscape.png";
            String squareFileName = "square.jpg";
            String assetsDirectory = "assets";

            // Get the absolute path to the assets directory
            String absolutePath = Paths.get("").toAbsolutePath().toString();
            String landscapeFilePath = Paths.get(absolutePath, assetsDirectory, landscapeFileName).toString();
            String squareFilePath = Paths.get(absolutePath, assetsDirectory, squareFileName).toString();

            // Load the image as a resource
            Resource landscapeResource = new FileSystemResource(landscapeFilePath);
            Resource squareResource = new FileSystemResource(squareFilePath);

            String customerId = appProperties.getApiGoogleCustomerId();
            String keywords = "junitTest1,junitTest2";
            String negativeKeywords  = "negativeJunitTest1,negativeJunitTest2";
            List<String> shortHeaderLineList = ImmutableList.of("shor header");
            String longHeadLine = "long head line";
            List<String> descriptionList = ImmutableList.of("description");
            String urlToLandPage = "https://www.google.com";
            InputStream landscapeImageInputStream = landscapeResource.getInputStream();
            InputStream squareImageInputStream = squareResource.getInputStream();

            MutateGoogleAdsResponse mutateGoogleAdsResponse = GoogleAdsUtils.addAdFromBudgetToAd(customerId, keywords, negativeKeywords,
                    shortHeaderLineList, longHeadLine, descriptionList, urlToLandPage, landscapeImageInputStream, squareImageInputStream);
            System.out.println(mutateGoogleAdsResponse);
        });
    }

    @DisplayName("Test query all ads")
    @Test
    public void testQueryAllAds() {
        Assertions.assertDoesNotThrow(() ->{
            service.queryAllAds();
        });
    }

    @DisplayName("Test query all keywords")
    @Test
    public void testQueryAllKeywords() {
        Assertions.assertDoesNotThrow(() ->{
            service.queryAllKeywords();
        });
    }



}
