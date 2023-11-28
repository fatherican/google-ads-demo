package com.ads.google.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdGroupAdItemDto {
    // ad preview url
    private String previewUrl;
    String landscapeImageBase64;
    String squareImageBase64;
    String longHeadLine;
    String shortHeadLine;
    String shortDescription;
    String landingPageUrl;
    String campaignName;
    String campaignResourceName;
    String adGroupName;
    String adResourceName;
    String status;
}
