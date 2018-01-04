package com.example.datatypes;

public class SearchResult {

    String url;
    String title;
    String description;

    public SearchResult(String url, String title, String description) {
        this.url = url;
        this.title = title;
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "SearchResult{" + "\n" +
                "url='" + url + "\'\n" +
                ", title='" + title + "\'\n" +
                ", description='" + description + "\'\n" +
                '}';
    }
}
