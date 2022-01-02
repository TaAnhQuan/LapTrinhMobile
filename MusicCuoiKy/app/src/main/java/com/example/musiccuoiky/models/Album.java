package com.example.musiccuoiky.models;

public class Album {
    private String id;
    private String album;
    private String albumArt;
    private String artist;
    private String numberOfSongs;

    public Album(String id, String album, String albumArt, String artist, String numberOfSongs) {
        this.id = id;
        this.album = album;
        this.albumArt = albumArt;
        this.artist = artist;
        this.numberOfSongs = numberOfSongs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getAlbumArt() {
        return albumArt;
    }

    public void setAlbumArt(String albumArt) {
        this.albumArt = albumArt;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getNumberOfSongs() {
        return numberOfSongs;
    }

    public void setNumberOfSongs(String numberOfSongs) {
        this.numberOfSongs = numberOfSongs;
    }
}
