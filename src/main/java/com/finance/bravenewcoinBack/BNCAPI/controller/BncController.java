package com.finance.bravenewcoinBack.BNCAPI.controller;

import com.finance.bravenewcoinBack.BNCAPI.service.BncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/bnc")
public class BncController {

    @Autowired
    BncService bncService;

    @GetMapping("/token")
    public String token() throws IOException, InterruptedException {
        return bncService.getToken();
    }

    @GetMapping("/asset")
    public String asset() throws IOException, InterruptedException {
        return bncService.getAsset();
    }

    @GetMapping("/asset-ticker")
    public String assetTicker() throws IOException, InterruptedException {
        return bncService.getAssetTicker();
    }

    @GetMapping("/crypto")
    public String apiBrave() throws IOException, InterruptedException {
        return bncService.getCrypto();
    }
}
