package com.finance.bravenewcoinBack.BNCAPI.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finance.bravenewcoinBack.BNCAPI.entity.Asset;
import com.finance.bravenewcoinBack.BNCAPI.entity.AssetTicker;
import com.finance.bravenewcoinBack.entity.ApiBrave;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BncService {

    public BncService() {
    }

    public String getToken() throws IOException, InterruptedException {

        Map<String, String> values = new HashMap<String, String>() {
            {
                put("audience", "https://api.bravenewcoin.com");
                put("client_id", "oCdQoZoI96ERE9HY3sQ7JmbACfBf55RY");
                put("grant_type", "client_credentials");
            }
        };

        ObjectMapper objectMapper = new ObjectMapper();
        String requestBody = objectMapper.writeValueAsString(values);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://bravenewcoin.p.rapidapi.com/oauth/token"))
                .header("content-type", "application/json")
                .header("x-rapidapi-key", "fd258a06b2msh1ea9ef28249d5a4p134bc2jsn9984647d833f")
                .header("x-rapidapi-host", "bravenewcoin.p.rapidapi.com")
                .method("POST", HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        String token = "";
        try {
            Map<String, String> map = new ObjectMapper().readValue(response.body(), Map.class);
            token = map.get("access_token");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return token;
    }

    public String getAsset() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://bravenewcoin.p.rapidapi.com/asset?status=ACTIVE"))
                .header("x-rapidapi-key", "fd258a06b2msh1ea9ef28249d5a4p134bc2jsn9984647d833f")
                .header("x-rapidapi-host", "bravenewcoin.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), JsonObject.class).get("content").toString();
    }

    public String getAssetTicker() throws IOException, InterruptedException {
        String token = this.getToken();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://bravenewcoin.p.rapidapi.com/market-cap"))
                .header("authorization", "Bearer "+ token)
                .header("x-rapidapi-key", "792b349dd1msh67e45d6056c75f3p1b5304jsn84633c03f8f9")
                .header("x-rapidapi-host", "bravenewcoin.p.rapidapi.com")
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), JsonObject.class).get("content").toString();
    }

    public String getCrypto() throws IOException, InterruptedException {
        ArrayList<Asset> assets = new Gson().fromJson(this.getAsset(),new TypeToken<ArrayList<Asset>>(){}.getType());
        ArrayList<AssetTicker> assetTickers = new Gson().fromJson(this.getAssetTicker(), new TypeToken<ArrayList<AssetTicker>>(){}.getType());
        List<ApiBrave> apiBraves = new ArrayList<>();
        for(AssetTicker assetTicker: assetTickers){
            for(Asset asset: assets){
                if(asset.getId().equals(assetTicker.getAssetId())){
                    ApiBrave apiBrave = new ApiBrave(asset.getName(), assetTicker.getPrice(), asset.getSymbol());
                    apiBraves.add(apiBrave);
                }
            }

        }
        return new Gson().toJson(apiBraves);
    }
}
