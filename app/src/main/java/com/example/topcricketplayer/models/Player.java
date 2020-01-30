package com.example.topcricketplayer.models;

public class Player implements Comparable<Player> {

    private String name;
    private String image;
    private Integer rating;
    private String content;

    public Player() {
    }

    public Player(String name, String image, int rating) {
        this.name = name;
        this.image = image;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public int compareTo(Player player) {
        if (getRating() == null || player.getRating() == null) {
            return 0;
        }
        return getRating().compareTo(player.getRating());
    }
}