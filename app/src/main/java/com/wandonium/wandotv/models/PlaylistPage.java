package com.wandonium.wandotv.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.youtube.model.Playlist;

import java.util.List;
import java.util.Objects;

public class PlaylistPage {

    private List<Playlist> playlists;
    private String nextPageToken;

    public PlaylistPage(List<Playlist> mPlaylists, String mNextPageToken) {
        playlists = mPlaylists;
        nextPageToken = mNextPageToken;
    }

    public List<Playlist> getPlaylists() {
        return playlists;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setPlaylists(List<Playlist> playlists) {
        this.playlists = playlists;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PlaylistPage)) return false;
        PlaylistPage that = (PlaylistPage) o;
        return playlists.equals(that.playlists) && nextPageToken.equals(that.nextPageToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playlists, nextPageToken);
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "PlaylistPage{" +
                "playlists=" + playlists +
                ", nextPageToken='" + nextPageToken + '\'' +
                '}';
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
