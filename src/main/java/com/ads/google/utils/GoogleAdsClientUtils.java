//package com.ads.google.utils;
//
//import com.ads.google.config.AppProperties;
//import com.google.ads.googleads.lib.GoogleAdsClient;
//import com.google.auth.oauth2.UserCredentials;
//
//public class GoogleAdsClientUtils {
//
//    private static GoogleAdsClient client = null;
//
//    public static GoogleAdsClient getClient() {
//        if (client == null) {
//            synchronized (client) {
//                if (client == null) {
//                    AppProperties appProperties = ApplicationContextUtils.getBean(AppProperties.class);
//                    UserCredentials credentials =
//                            UserCredentials.newBuilder()
//                                    .setClientId(appProperties.getApiGoogleClientId())
//                                    .setClientSecret(appProperties.getApiGoogleClientSecret())
//                                    .setRefreshToken(appProperties.getApiGoogleRefreshToken())
//                                    .build();
//                    client = GoogleAdsClient.newBuilder()
//                            // Sets the developer token which enables API access.
//                            .setDeveloperToken(appProperties.getApiGoogleDeveloperToken())
//                            // Sets the OAuth credentials which provide Google Ads account access.
//                            .setCredentials(credentials)
//                            // Optional: sets the login customer ID. This is required when the Google account
//                            // authenticated with the refresh token does not have direct access to
//                            // OPERATING_CUSTOMER_ID and the access is via a manager account. In this case, specify
//                            // the manager account ID as LOGIN_CUSTOMER_ID.
//                            .setLoginCustomerId(Long.valueOf(appProperties.getApiGoogleLoginCustomerId()))
//                            .build();
//                }
//            }
//        }
//        return client;
//    }
//}
