package com.ads.google.utils;

import com.google.ads.googleads.v15.services.MutateOperation;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TempOperation {
    private MutateOperation operation;
    private String resourceName;
}
