package edu.uc.bitcoinparkpay.dto;

/**
 * Created by Greg on 7/28/2015.
 * Stores API Keys
 */

public class Key {
    private int id;
    private String apiKey;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
