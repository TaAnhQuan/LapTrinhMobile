package com.example.musiccuoiky.models;


public class Artist {
    private String id, albumArt, artist, numOfAlbums, numOfSongs;

    public Artist(String id, String albumArt, String artist, String numOfAlbums, String numOfSongs) {
        this.id = id;
        this.albumArt = albumArt;
        this.artist = artist;
        this.numOfAlbums = numOfAlbums;
        this.numOfSongs = numOfSongs;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNumOfAlbums() {
        return numOfAlbums;
    }

    public void setNumOfAlbums(String numOfAlbums) {
        this.numOfAlbums = numOfAlbums;
    }

    public String getNumOfSongs() {
        return numOfSongs;
    }

    public void setNumOfSongs(String numOfSongs) {
        this.numOfSongs = numOfSongs;
    }
}