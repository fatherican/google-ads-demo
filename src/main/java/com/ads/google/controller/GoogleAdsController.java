package com.ads.google.controller;

import com.ads.google.dto.AdGroupAdItemDto;
import com.ads.google.dto.AdKeywordItemDto;
import com.ads.google.service.GoogleAdsService;
import com.google.ads.googleads.v15.errors.GoogleAdsError;
import com.google.ads.googleads.v15.errors.GoogleAdsException;
import com.google.ads.googleads.v15.services.MutateGoogleAdsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GoogleAdsController {

    @Autowired
    private GoogleAdsService googleAdsService;

    /**
     * To index page
     * @return
     */
    @GetMapping("/")
    public String showForm() {
        return "index";
    }

    /**
     * To add campaign page
     * @return
     */
    @GetMapping("/addAdsPage")
    public String addAdsPage() {
        return "addForm";
    }


    /**
     * Add Ad
     * @param landScapeImageFile
     * @param squareImageFile
     * @param longHeadLine
     * @param shortHeadLine
     * @param shortDescription
     * @param landingPageUrl
     * @param keywords
     * @param negativeKeywords
     * @param otherFilters
     * @param model
     * @return
     * @throws IOException
     */
    @PostMapping("/addAds")
    public String processForm(
            @RequestParam("landScapeImageFile") MultipartFile landScapeImageFile,
            @RequestParam("squareImageFile") MultipartFile squareImageFile,
            @RequestParam String longHeadLine,
            @RequestParam List<String> shortHeadLine,
            @RequestParam List<String> shortDescription,
            @RequestParam String landingPageUrl,
            @RequestParam String keywords,
            @RequestParam String negativeKeywords,
            @RequestParam String otherFilters,
            Model model) throws IOException {

        try {
            MutateGoogleAdsResponse mutateGoogleAdsResponse = googleAdsService.addAdFromBudgetToAd(keywords, negativeKeywords, shortHeadLine,
                    longHeadLine, shortDescription, landingPageUrl,
                    landScapeImageFile.getInputStream(), squareImageFile.getInputStream());
        }catch (GoogleAdsException gaException) {
            List<String> errorList = new ArrayList<>();
            int i = 0;
            for (GoogleAdsError googleAdsError : gaException.getGoogleAdsFailure().getErrorsList()) {
                errorList.add(String.format("  Error %d: %s%n", i++, googleAdsError));
            }
            model.addAttribute("errorList", errorList);
            return "addError";
        }
        return "redirect:/";
    }

    /**
     * query all ads;
     * @return
     */
    @PostMapping("/queryAllAds")
    @ResponseBody
    public List<AdGroupAdItemDto> queryAllAds() {
        List<AdGroupAdItemDto> adGroupAdItemDtoList = googleAdsService.queryAllAds();
        return adGroupAdItemDtoList;
    }

    /**
     * query all keywords
     * @return
     */
    @PostMapping("/queryAllKeywords")
    @ResponseBody
    public List<AdKeywordItemDto> queryAllSearchKeywords() {
        List<AdKeywordItemDto> adKeywordItemDtos = googleAdsService.queryAllKeywords();
        return adKeywordItemDtos;
    }


}
