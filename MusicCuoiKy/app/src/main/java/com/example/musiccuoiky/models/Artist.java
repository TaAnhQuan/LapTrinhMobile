package com.example.musiccuoiky.models;


public class Artist {
    private String id, album_art, artist, numOfAlbums, numOfSongs;

    public Artist(String id, String album_art, String artist, String numOfAlbums, String numOfSongs) {
        this.id = id;
        this.album_art = album_art;
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

    public String getAlbum_art() {
        return album_art;
    }

    public void setAlbum_art(String album_art) {
        this.album_art = album_art;
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