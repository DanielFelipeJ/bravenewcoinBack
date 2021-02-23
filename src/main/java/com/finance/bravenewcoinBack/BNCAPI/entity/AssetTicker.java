package com.finance.bravenewcoinBack.BNCAPI.entity;

public class AssetTicker {

    private String id;
    private String assetId;
    private String timestamp;
    private String marketCapRank;
    private String volumeRank;
    private Double price;
    private String volume;
    private String totalSupply;
    private String freeFloatSupply;
    private String marketCap;
    private String totalMarketCap;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getMarketCapRank() {
        return marketCapRank;
    }

    public void setMarketCapRank(String marketCapRank) {
        this.marketCapRank = marketCapRank;
    }

    public String getVolumeRank() {
        return volumeRank;
    }

    public void setVolumeRank(String volumeRank) {
        this.volumeRank = volumeRank;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getTotalSupply() {
        return totalSupply;
    }

    public void setTotalSupply(String totalSupply) {
        this.totalSupply = totalSupply;
    }

    public String getFreeFloatSupply() {
        return freeFloatSupply;
    }

    public void setFreeFloatSupply(String freeFloatSupply) {
        this.freeFloatSupply = freeFloatSupply;
    }

    public String getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(String marketCap) {
        this.marketCap = marketCap;
    }

    public String getTotalMarketCap() {
        return totalMarketCap;
    }

    public void setTotalMarketCap(String totalMarketCap) {
        this.totalMarketCap = totalMarketCap;
    }
}
