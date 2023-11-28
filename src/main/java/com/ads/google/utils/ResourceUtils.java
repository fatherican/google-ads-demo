package com.ads.google.utils;

import com.ads.google.exception.AppException;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

public class ResourceUtils {

    /**
     * get gaql file content;
     * @param fileName
     * @return
     */
    public static String getGAQLResource(String fileName) {
        Resource resource = new ClassPathResource("ql/" + fileName);
        try {
            return IOUtils.toString(resource.getInputStream(), Charset.forName("utf-8"));
        } catch (IOException e) {
            throw new AppException(e);
        }
    }
}
