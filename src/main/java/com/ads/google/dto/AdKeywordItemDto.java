package com.ads.google.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AdKeywordItemDto {
    String keyword;
    String matchType;
    String campaignName;
    String campaignResourceName;
    String adGroupName;
    String adGroupResourceName;
    String status;
    boolean negative;
}
