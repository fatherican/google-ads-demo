package com.ads.google.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author xiaokai
 * @date 2023-11-26
 */
@Component
@Data
public class AppProperties {

    /**
     * google ADS api clientId
     */
    @Value("${api.google.clientId}")
    private String apiGoogleClientId;

    /**
     * google ADS api clientSecret
     */
    @Value("${api.google.clientSecret}")
    private String apiGoogleClientSecret;
    /**
     * google ADS api refreshToken
     */
    @Value("${api.google.refreshToken}")
    private String apiGoogleRefreshToken;
    /**
     * google ADS api developerToken
     */
    @Value("${api.google.developerToken}")
    private String apiGoogleDeveloperToken;
    /**
     * google ADS api loginCustomerId
     */
    @Value("${api.google.loginCustomerId}")
    private String apiGoogleLoginCustomerId;


    /**
     * google ADS api loginCustomerId
     */
    @Value("${api.google.customerId}")
    private String apiGoogleCustomerId;

}
