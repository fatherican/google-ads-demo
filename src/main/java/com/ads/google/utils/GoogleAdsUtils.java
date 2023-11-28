package com.ads.google.utils;

import com.ads.google.config.AppProperties;
import com.ads.google.exception.AppException;
import com.google.ads.googleads.lib.GoogleAdsClient;
import com.google.ads.googleads.v13.utils.ResourceNames;
import com.google.ads.googleads.v15.common.*;
import com.google.ads.googleads.v15.enums.*;
import com.google.ads.googleads.v15.errors.GoogleAdsException;
import com.google.ads.googleads.v15.resources.*;
import com.google.ads.googleads.v15.services.*;
import com.google.auth.oauth2.UserCredentials;
import com.google.common.collect.ImmutableList;
import com.google.protobuf.ByteString;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 请注意：该类中虽有有较多的方法，实际使用的很少；
 * 例如 大部分addXXX打头的方法都是在熟悉google Ads sdk过程中书写； 后面因为参考了'最佳实践' 所以项目中用到的是
 * GoogleAdsUtils#addAdFromBudgetToAd
 */
public class GoogleAdsUtils {
    private static GoogleAdsClient client = null;

    /**
     *  add campaign Budget
     * @param customerId
     * @param campaignBudget
     * @return resource name
     */
    public static String addCampaignBudget(String customerId, CampaignBudget campaignBudget) {
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (campaignBudget == null) {
            throw new AppException("campaignBudget is null");
        }
        CampaignBudgetOperation campaignBudgetOperation = CampaignBudgetOperation.newBuilder().setCreate(campaignBudget).build();

        GoogleAdsClient adsClient = GoogleAdsUtils.getClient();
        try(CampaignBudgetServiceClient campaignBudgetServiceClient = adsClient.getLatestVersion().createCampaignBudgetServiceClient();) {
            MutateCampaignBudgetsResponse mutateCampaignBudgetsResponse =
                    campaignBudgetServiceClient.mutateCampaignBudgets(
                            customerId, ImmutableList.of(campaignBudgetOperation));

            return mutateCampaignBudgetsResponse.getResultsList().get(0).getResourceName();
        }
    }

    /**
     * create campaign Budget Operation
     *
     * @param customerId
     * @param campaignBudget
     * @return resource name
     */
    public static MutateOperation createCampaignBudgetOperation(String customerId, CampaignBudget campaignBudget) {
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (campaignBudget == null) {
            throw new AppException("campaignBudget is null");
        }
        CampaignBudgetOperation campaignBudgetOperation = CampaignBudgetOperation.newBuilder().setCreate(campaignBudget).build();

       return MutateOperation.newBuilder().setCampaignBudgetOperation(campaignBudgetOperation).build();
    }


    /**
     * add campaign
     * @param customerId
     * @return resource name
     */
    public static String addCampaign(String customerId, Campaign campaign) {
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (campaign == null) {
            throw new AppException("campaign is null");
        }
        CampaignOperation campaignOperation = CampaignOperation.newBuilder().setCreate(campaign).build();
        List<CampaignOperation> operationList = new ArrayList<>();
        operationList.add(campaignOperation);

        GoogleAdsClient adsClient = GoogleAdsUtils.getClient();
        try(CampaignServiceClient campaignServiceClient = adsClient.getLatestVersion().createCampaignServiceClient()) {
            MutateCampaignsResponse mutateCampaignsResponse = campaignServiceClient.mutateCampaigns(customerId, operationList);
            return mutateCampaignsResponse.getResults(0).getResourceName();
        }
    }

    /**
     * create campaign Operation
     *
     * @param customerId
     * @return resource name
     */
    public static MutateOperation createCampaignOperation(String customerId, Campaign campaign) {
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (campaign == null) {
            throw new AppException("campaign is null");
        }
        CampaignOperation campaignOperation = CampaignOperation.newBuilder().setCreate(campaign).build();
        return MutateOperation.newBuilder().setCampaignOperation(campaignOperation).build();
    }


    /**
     * add campaign
     * @param customerId
     * @param adGroup
     * @return campaignResourceName if empty the system will create;
     */
    public static String addAdGroup(String customerId, AdGroup adGroup) {
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (adGroup == null) {
            throw new AppException("adGroup is null");
        }
        AdGroupOperation adGroupOperation = AdGroupOperation.newBuilder().setCreate(adGroup).build();
        List<AdGroupOperation> operationList = new ArrayList<>();
        operationList.add(adGroupOperation);

        GoogleAdsClient adsClient = GoogleAdsUtils.getClient();
        try(AdGroupServiceClient adGroupServiceClient = adsClient.getLatestVersion().createAdGroupServiceClient()){
            MutateAdGroupsResponse mutateAdGroupsResponse = adGroupServiceClient.mutateAdGroups(customerId, operationList);
            return mutateAdGroupsResponse.getResults(0).getResourceName();
        }
    }

    /**
     * create campaign Operation
     *
     * @param customerId
     * @param adGroup
     * @return campaignResourceName if empty the system will create;
     */
    public static MutateOperation createAdGroupOperation(String customerId, AdGroup adGroup) {
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (adGroup == null) {
            throw new AppException("adGroup is null");
        }
        AdGroupOperation adGroupOperation = AdGroupOperation.newBuilder().setCreate(adGroup).build();
        return MutateOperation.newBuilder().setAdGroupOperation(adGroupOperation).build();
    }



    /**
     * add campaign
     * @param customerId
     * @return campaignResourceName if empty the system will create;
     */
    public static String addAd(String customerId, String adGroupResourceName, List<String> headLinesList,
                               String longHeadLine,
                               List<String> descriptionList,
                               String finalUrl,
                               File landscapeImageFile, File squareImageFile) {
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (StringUtils.isBlank(adGroupResourceName)) {
            throw new AppException("adGroupResourceName is empty");
        }
        // TODO 条件判断，判断各个参数的长度个数是否齐全;


        ResponsiveDisplayAdInfo.Builder builder = ResponsiveDisplayAdInfo.newBuilder();
        // 自适应展示广告
        ResponsiveDisplayAdInfo responsiveDisplayAdInfo = builder
                                                                .setBusinessName("RespAdTest " + System.currentTimeMillis())
                                                                // 短标题可以是多个，所以是add
                                                                .addHeadlines(AdTextAsset.newBuilder().setText("这是短标题1").build())
                                                                .addHeadlines(AdTextAsset.newBuilder().setText("这是短标题2").build())
                                                                .addMarketingImages(AdImageAsset.newBuilder().setAsset(addImageAsset(customerId, "li", landscapeImageFile)).build())
                                                                .addSquareMarketingImages(AdImageAsset.newBuilder().setAsset(addImageAsset(customerId, "si", squareImageFile)).build())
//                                                                .addMarketingImages(AdImageAsset.newBuilder().setAsset(addImageAsset(customerId, "in2", imageFile)).build())
                                                                // 长标题 只能有一个
                                                                .setLongHeadline(AdTextAsset.newBuilder().setText("这里是长标题").build())
                                                                .addDescriptions(AdTextAsset.newBuilder().setText("这里是 广告内容描述").build())
                                                                .build();
        // 设置广告
        Ad ad = Ad.newBuilder().setType(AdTypeEnum.AdType.RESPONSIVE_DISPLAY_AD)
                            .setName("ad test " + System.currentTimeMillis())
                            .setResponsiveDisplayAd(responsiveDisplayAdInfo)
                            .addFinalUrls(finalUrl).build();


        AdGroupAd adGroupAd = AdGroupAd.newBuilder()
                                    .setAdGroup(adGroupResourceName)
                                    .setStatus(AdGroupAdStatusEnum.AdGroupAdStatus.ENABLED)
                                    .setAd(ad).build();

        AdGroupAdOperation adGroupAdOperation = AdGroupAdOperation.newBuilder().setCreate(adGroupAd).build();

        List<AdGroupAdOperation> adGroupAdOperationList = new ArrayList<>();
        adGroupAdOperationList.add(adGroupAdOperation);

        GoogleAdsClient adsClient = GoogleAdsUtils.getClient();
        try(AdGroupAdServiceClient adGroupAdServiceClient = adsClient.getLatestVersion().createAdGroupAdServiceClient()) {
            MutateAdGroupAdsResponse mutateAdGroupAdsResponse = adGroupAdServiceClient.mutateAdGroupAds(customerId, adGroupAdOperationList);
            return mutateAdGroupAdsResponse.getResults(0).getResourceName();
        }
    }


    /**
     * add campaign
     *
     * @param customerId
     * @return campaignResourceName if empty the system will create;
     */
    public static MutateOperation createAdOperation(String customerId, String adGroupResourceName, String finalUrl,
                                                    List<String> headLinesList,
                                                    String longHeadLine,
                                                    List<String> descriptionList,
                                                    String landscapeImageFileResourceName, String squareImageFileResourceName) {
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (StringUtils.isBlank(adGroupResourceName)) {
            throw new AppException("adGroupResourceName is empty");
        }
        // TODO 条件判断，判断各个参数的长度个数是否齐全;


        ResponsiveDisplayAdInfo.Builder builder = ResponsiveDisplayAdInfo.newBuilder();
        builder.setBusinessName("RespAdTest " + System.currentTimeMillis());
        for (String headLine : headLinesList) {
            builder.addHeadlines(AdTextAsset.newBuilder().setText(headLine).build());
        }
        if (StringUtils.isNotBlank(longHeadLine)) {
            // 长标题 只能有一个
            builder.setLongHeadline(AdTextAsset.newBuilder().setText(longHeadLine).build());
        }
        for (String description : descriptionList) {
            builder.addDescriptions(AdTextAsset.newBuilder().setText(description).build());
        }
        builder.addMarketingImages(AdImageAsset.newBuilder().setAsset(landscapeImageFileResourceName).build());
        builder.addSquareMarketingImages(AdImageAsset.newBuilder().setAsset(squareImageFileResourceName).build());
        ResponsiveDisplayAdInfo responsiveDisplayAdInfo = builder.build();
        // 自适应展示广告
        // 设置广告
        Ad ad = Ad.newBuilder().setType(AdTypeEnum.AdType.RESPONSIVE_DISPLAY_AD)
                .setName("ad test " + System.currentTimeMillis())
                .setResponsiveDisplayAd(responsiveDisplayAdInfo)
                .addFinalUrls(finalUrl).build();


        AdGroupAd adGroupAd = AdGroupAd.newBuilder()
                .setAdGroup(adGroupResourceName)
                .setStatus(AdGroupAdStatusEnum.AdGroupAdStatus.ENABLED)
                .setAd(ad).build();

        AdGroupAdOperation adGroupAdOperation = AdGroupAdOperation.newBuilder().setCreate(adGroupAd).build();
        return MutateOperation.newBuilder().setAdGroupAdOperation(adGroupAdOperation).build();
    }

    public static GoogleAdsServiceClient.SearchPagedResponse executeGAQL(SearchGoogleAdsRequest request) {
        try (GoogleAdsServiceClient googleAdsServiceClient =
                     getClient().getLatestVersion().createGoogleAdsServiceClient()) {
            return googleAdsServiceClient.search(request);
        }
    }

    public static String addImageAsset(String customerId, String assetName, File imageFile){
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (StringUtils.isBlank(assetName)) {
            throw new AppException("assetName is empty");
        }
        if (imageFile == null) {
            throw new AppException("imageFile is null");
        }

        byte[] imageBts;
        try {
            imageBts = FileUtils.readFileToByteArray(imageFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Asset asset =
                Asset.newBuilder()
                        .setName(assetName + " " + System.currentTimeMillis())
                        .setType(AssetTypeEnum.AssetType.IMAGE)
                        .setImageAsset(ImageAsset.newBuilder().setData(ByteString.copyFrom(imageBts)).build())
                        .build();
        AssetOperation assetOperation = AssetOperation.newBuilder().setCreate(asset).build();
        List<AssetOperation> assetOperationList = new ArrayList<>();
        assetOperationList.add(assetOperation);

        try(AssetServiceClient assetServiceClient = getClient().getLatestVersion().createAssetServiceClient()) {
            MutateAssetsResponse mutateAssetsResponse = assetServiceClient.mutateAssets(customerId, assetOperationList);
            return mutateAssetsResponse.getResultsList().get(0).getResourceName();
        }
    }

    public static TempOperation createImageAssetOperation(String customerId, String assetName, InputStream fileInputStream){
        if (StringUtils.isBlank(customerId)) {
            throw new AppException("customerId is empty");
        }
        if (StringUtils.isBlank(assetName)) {
            throw new AppException("assetName is empty");
        }
        if (fileInputStream == null) {
            throw new AppException("fileInputStream is null");
        }

        byte[] imageBts;
        try {
            imageBts = IOUtils.toByteArray(fileInputStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String resourceName = ResourceNames.asset(Long.parseLong(customerId), -getRandomLongVal());
        Asset asset =
                Asset.newBuilder()
                        .setResourceName(resourceName)
                        .setName(assetName + " " + System.currentTimeMillis())
                        .setType(AssetTypeEnum.AssetType.IMAGE)
                        .setImageAsset(ImageAsset.newBuilder().setData(ByteString.copyFrom(imageBts)).build())
                        .build();

        AssetOperation assetOperation = AssetOperation.newBuilder().setCreate(asset).build();
        MutateOperation operation = MutateOperation.newBuilder().setAssetOperation(assetOperation).build();
        return TempOperation.builder().operation(operation).resourceName(resourceName).build();
    }

    public static Long getRandomLongVal() {
        return Long.parseLong(String.valueOf(System.currentTimeMillis()) + RandomUtils.nextLong(1000,9999));
    }

    public static GoogleAdsClient getClient() {
        if (client == null) {
            synchronized (GoogleAdsUtils.class) {
                if (client == null) {
                    AppProperties appProperties = ApplicationContextUtils.getBean(AppProperties.class);
                    UserCredentials credentials =
                            UserCredentials.newBuilder()
                                    .setClientId(appProperties.getApiGoogleClientId())
                                    .setClientSecret(appProperties.getApiGoogleClientSecret())
                                    .setRefreshToken(appProperties.getApiGoogleRefreshToken())
                                    .build();
                    client = GoogleAdsClient.newBuilder()
                            .setDeveloperToken(appProperties.getApiGoogleDeveloperToken())
                            .setCredentials(credentials)
                            .setLoginCustomerId(Long.valueOf(appProperties.getApiGoogleLoginCustomerId()))
                            .build();
                }
            }
        }
        return client;
    }

    public static MutateOperation createAdGroupCrierionOperation(AdGroupCriterion adGroupCriterion) {
        AdGroupCriterionOperation adGroupCriterionOperation = AdGroupCriterionOperation.newBuilder().setCreate(adGroupCriterion).build();
        return MutateOperation.newBuilder().setAdGroupCriterionOperation(adGroupCriterionOperation).build();
    }


    /**
     * create ad from budget to add, keep the operation in atomic;
     * create budget -> create campaign -> create AD group -> create adgroup Criterion(optional) -> create ad group ad
     * @param customerId
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
    public static MutateGoogleAdsResponse addAdFromBudgetToAd(String customerId, String keywords, String negativeKeywords,
                                                              List<String> shortHeaderLineList, String longHeadLine, List<String> descriptionList,
                                                              String urlToLandPage,
                                                              InputStream landscapeImageInputStream,
                                                              InputStream squareImageInputStream) {
        try {
            List<MutateOperation> allMutateOperationList = new ArrayList<>(100);
            // 1 add campaign budget
            String campaignBudgetResourceName = ResourceNames.campaignBudget(Long.parseLong(customerId), -getRandomLongVal());
            CampaignBudget campaignBudget = CampaignBudget.newBuilder()
                    .setName("my budget " + System.currentTimeMillis())
                    .setResourceName(campaignBudgetResourceName)
                    .setDeliveryMethod(BudgetDeliveryMethodEnum.BudgetDeliveryMethod.STANDARD)
                    .setAmountMicros(1000_000)
                    .build();
            MutateOperation campaignBudgetOperation = createCampaignBudgetOperation(customerId, campaignBudget);
            allMutateOperationList.add(campaignBudgetOperation);
            // 2 add campaign operation
            String campaignResourceName = ResourceNames.campaign(Long.parseLong(customerId), -getRandomLongVal());
            Campaign campaign = Campaign.newBuilder().setName("campaign test " + System.currentTimeMillis())
                    .setResourceName(campaignResourceName)
                    .setAdvertisingChannelType(AdvertisingChannelTypeEnum.AdvertisingChannelType.SEARCH)
                    .setCampaignBudget(campaignBudgetResourceName)
                    .setManualCpc(ManualCpc.newBuilder().build())
                    .build();
            MutateOperation campaignOperation = createCampaignOperation(customerId, campaign);
            allMutateOperationList.add(campaignOperation);
            // 3 add AD group campaignOperation
            String adGroupResourceName = ResourceNames.adGroup(Long.parseLong(customerId), -getRandomLongVal());
            AdGroup adGroup = AdGroup.newBuilder().setName("adGroup test " + System.currentTimeMillis())
                    .setResourceName(adGroupResourceName)
                    .setType(AdGroupTypeEnum.AdGroupType.SEARCH_STANDARD)
                    .setCampaign(campaignResourceName)
                    .setCpcBidMicros(1000_000)
                    .build();
            MutateOperation adGroupOperation = createAdGroupOperation(customerId, adGroup);
            allMutateOperationList.add(adGroupOperation);
            // 4 add AD group criterion operation
            if (StringUtils.isNotBlank(keywords)) {
                for (String keyword : keywords.split(",")) {
                    AdGroupCriterion adGroupCriterion = AdGroupCriterion.newBuilder()
                            .setAdGroup(adGroupResourceName)
                            .setKeyword(KeywordInfo.newBuilder().setText(keyword).setMatchType(KeywordMatchTypeEnum.KeywordMatchType.BROAD).build())
                            .build();
                    MutateOperation adGroupCrierionOperation = createAdGroupCrierionOperation(adGroupCriterion);
                    allMutateOperationList.add(adGroupCrierionOperation);
                }

            }
            if (StringUtils.isNotBlank(negativeKeywords)) {
                for (String negativeKeyword : negativeKeywords.split(",")) {
                    AdGroupCriterion adGroupCriterion = AdGroupCriterion.newBuilder()
                            .setAdGroup(adGroupResourceName)
                            .setNegative(true)
                            .setKeyword(KeywordInfo.newBuilder().setText(negativeKeyword).setMatchType(KeywordMatchTypeEnum.KeywordMatchType.BROAD).build())
                            .build();
                    MutateOperation adGroupCrierionOperation = createAdGroupCrierionOperation(adGroupCriterion);
                    allMutateOperationList.add(adGroupCrierionOperation);
                }
            }
            // 5 add AD operation
            // 5.1 add image(landscape) asset operation
            TempOperation assetLandScapeTempOperation = createImageAssetOperation(customerId, "li", landscapeImageInputStream);
            allMutateOperationList.add(assetLandScapeTempOperation.getOperation());
            // 5.2 add image(square) asset operation
            TempOperation assetSquareTempOperation = createImageAssetOperation(customerId, "li", squareImageInputStream);
            allMutateOperationList.add(assetSquareTempOperation.getOperation());
            MutateOperation adOperation = createAdOperation(customerId, adGroupResourceName, urlToLandPage, shortHeaderLineList, longHeadLine, descriptionList, assetLandScapeTempOperation.getResourceName(), assetSquareTempOperation.getResourceName());
            allMutateOperationList.add(adOperation);
            // 6 mutate all operations
            try(GoogleAdsServiceClient googleAdsServiceClient = getClient().getLatestVersion().createGoogleAdsServiceClient()) {
                MutateGoogleAdsResponse response = googleAdsServiceClient.mutate(customerId, allMutateOperationList);
                return response;
            }
        } catch (GoogleAdsException ex) {
            throw ex;
        }
    }
}
