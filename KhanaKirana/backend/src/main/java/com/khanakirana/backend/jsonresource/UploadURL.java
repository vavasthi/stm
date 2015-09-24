package com.khanakirana.backend.jsonresource;

/**
 * Created by vavasthi on 24/9/15.
 */
public class UploadURL {

    public UploadURL() {
    }

    public UploadURL(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UploadURL{" +
                "url='" + url + '\'' +
                '}';
    }

    private String url;
}
