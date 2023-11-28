package com.ads.google.service;

import com.ads.google.config.AppProperties;
import com.ads.google.dto.AdGroupAdItemDto;
import com.ads.google.dto.AdKeywordItemDto;
import com.ads.google.utils.GoogleAdsUtils;
import com.ads.google.utils.ResourceUtils;
import com.google.ads.googleads.v15.common.KeywordInfo;
import com.google.ads.googleads.v15.common.ResponsiveDisplayAdInfo;
import com.google.ads.googleads.v15.resources.*;
import com.google.ads.googleads.v15.services.GoogleAdsRow;
import com.google.ads.googleads.v15.services.GoogleAdsServiceClient;
import com.google.ads.googleads.v15.services.MutateGoogleAdsResponse;
import com.google.ads.googleads.v15.services.SearchGoogleAdsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class GoogleAdsService {
    @Autowired
    private AppProperties appProperties;

    /**
     * create ad from budget to add, keep the operation in atomic;
     * @param keywords
     * @param negativeKeywords
     * @param shortHeaderLineList
     * @param longHeadLine
     * @param descriptionList
     * @param urlToLandPage
     * @param landscapeImageInputStream
     * @param squareImageInputStream
     * @return
     */
    public MutateGoogleAdsResponse addAdFromBudgetToAd(String keywords, String negativeKeywords,
                                    List<String> shortHeaderLineList, String longHeadLine, List<String> descriptionList,
                                    String urlToLandPage,
                                    InputStream landscapeImageInputStream,
                                    InputStream squareImageInputStream) {
        MutateGoogleAdsResponse mutateGoogleAdsResponse = GoogleAdsUtils.addAdFromBudgetToAd(appProperties.getApiGoogleCustomerId(),keywords, negativeKeywords,
                shortHeaderLineList, longHeadLine, descriptionList,
                urlToLandPage, landscapeImageInputStream, squareImageInputStream);
        return mutateGoogleAdsResponse;
    }


    public List<AdGroupAdItemDto> queryAllAds() {
        String searchQL = ResourceUtils.getGAQLResource("selectAdGroupAd.gaql");
        SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(appProperties.getApiGoogleCustomerId())
                .setQuery(searchQL)
                .build();
        List<AdGroupAdItemDto> adGroupAdItemDtoList = new ArrayList<>(100);
        GoogleAdsServiceClient.SearchPagedResponse searchPagedResponse = GoogleAdsUtils.executeGAQL(request);
        for (GoogleAdsRow googleAdsRow : searchPagedResponse.iterateAll()) {
            Campaign campaign = googleAdsRow.getCampaign();
            AdGroup adGroup = googleAdsRow.getAdGroup();
            AdGroupAd adGroupAd = googleAdsRow.getAdGroupAd();
            Ad ad = adGroupAd.getAd();
            ResponsiveDisplayAdInfo responsiveDisplayAd = ad.getResponsiveDisplayAd();
            AdGroupAdItemDto adGroupAdItemDto = AdGroupAdItemDto.builder().campaignResourceName(campaign.getResourceName())
                    .adResourceName(adGroupAd.getResourceName())
                    .campaignName(campaign.getName())
                    .adGroupName(adGroup.getName())
                    .status(adGroupAd.getStatus().name())
                    .longHeadLine(responsiveDisplayAd.getLongHeadline().getText())
                    .shortHeadLine(responsiveDisplayAd.getHeadlines(0).getText())
                    .shortDescription(responsiveDisplayAd.getDescriptions(0).getText())
                    .landingPageUrl(ad.getFinalUrls(0))
                    .build();
            adGroupAdItemDtoList.add(adGroupAdItemDto);
        }
        return adGroupAdItemDtoList;
    }


    public List<AdKeywordItemDto> queryAllKeywords() {
        String searchQL = ResourceUtils.getGAQLResource("selectKeywords.gaql");
        SearchGoogleAdsRequest request = SearchGoogleAdsRequest.newBuilder()
                .setCustomerId(appProperties.getApiGoogleCustomerId())
                .setQuery(searchQL)
                .build();
        List<AdKeywordItemDto> keywordItemDtoList = new ArrayList<>(100);
        GoogleAdsServiceClient.SearchPagedResponse searchPagedResponse = GoogleAdsUtils.executeGAQL(request);
        for (GoogleAdsRow googleAdsRow : searchPagedResponse.iterateAll()) {
            Campaign campaign = googleAdsRow.getCampaign();
            AdGroup adGroup = googleAdsRow.getAdGroup();
            AdGroupCriterion adGroupCriterion = googleAdsRow.getAdGroupCriterion();
            KeywordInfo keyword = adGroupCriterion.getKeyword();
            AdKeywordItemDto adKeywordItemDto = AdKeywordItemDto.builder()
                    .keyword(keyword.getText()).matchType(keyword.getMatchType().name())
                    .negative(adGroupCriterion.getNegative())
                    .status(adGroupCriterion.getStatus().name())
                    .campaignName(campaign.getName())
                    .campaignResourceName(campaign.getResourceName())
                    .adGroupName(adGroup.getName())
                    .adGroupResourceName(adGroup.getResourceName()).build();

            keywordItemDtoList.add(adKeywordItemDto);
        }
        return keywordItemDtoList;
    }



}
