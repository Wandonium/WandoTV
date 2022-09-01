package com.wandonium.wandotv.models;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.services.youtube.model.Video;

import java.util.ArrayList;
import java.util.Objects;

public class VideoItem {

    private Video video;
    private ArrayList<Video> playlist;

    public VideoItem(Video video, ArrayList<Video> playlist) {
        this.video = video;
        this.playlist = playlist;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public ArrayList<Video> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(ArrayList<Video> playlist) {
        this.playlist = playlist;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VideoItem)) return false;
        VideoItem videoItem = (VideoItem) o;
        return getVideo().equals(videoItem.getVideo())
                && getPlaylist().equals(videoItem.getPlaylist());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVideo(), getPlaylist());
    }

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "VideoItem{" +
                "video=" + video +
                ", playlist=" + playlist +
                '}';
        try {
            jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return jsonString;
    }
}
