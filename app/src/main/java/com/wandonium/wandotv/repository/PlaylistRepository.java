package com.wandonium.wandotv.repository;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTubeRequestInitializer;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemListResponse;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.wandonium.wandotv.BuildConfig;
import com.wandonium.wandotv.models.PlaylistPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class PlaylistRepository {

    private YouTube youTubeService;
    private static PlaylistRepository self;

    public static final String YOUTUBE_CHANNEL_ID = "UCtmYk7H-NNYLEe_LgBRYomA";
    public static final String APP_NAME = "WandoTV";

    private class SetTimeout implements HttpRequestInitializer {
        public void initialize(HttpRequest request) {
            request.setConnectTimeout(0);
            request.setReadTimeout(0);
        }
    }

    private PlaylistRepository() {
        HttpTransport transport = AndroidHttp.newCompatibleTransport();
        youTubeService = new YouTube.Builder(
                transport,
                JacksonFactory.getDefaultInstance(),
                new SetTimeout()
        ).setApplicationName(APP_NAME)
                .setYouTubeRequestInitializer(
                        new YouTubeRequestInitializer(BuildConfig.YOUTUBE_API_KEY)
                ).build();
    }

    public static PlaylistRepository getInstance() {
        if(self == null) {
            synchronized (PlaylistRepository.class) {
                if(self == null) {
                    self = new PlaylistRepository();
                }
            }
        }
        return self;
    }

    public PlaylistPage getPlaylists(String nextPageToken) throws IOException {
        PlaylistListResponse playlists = youTubeService
                .playlists()
                .list("contentDetails,id,localizations,player,snippet,status")
                .setChannelId(YOUTUBE_CHANNEL_ID)
                .setPageToken(nextPageToken)
                .execute();
        return new PlaylistPage(playlists.getItems(), playlists.getNextPageToken());
    }

    public List<Video> getVideos(String playlistId) throws IOException {
        List<Video> videoList = new ArrayList<>();
        PlaylistItemListResponse playlistItem = youTubeService
                .playlistItems()
                .list("contentDetails,id,snippet,status")
                .setPlaylistId(playlistId)
                .execute();
        for(PlaylistItem p: playlistItem.getItems()) {
            VideoListResponse videoResponse = youTubeService
                    .videos()
                    .list("contentDetails,id,liveStreamingDetails,localizations,player," +
                            "recordingDetails,snippet,statistics,status,topicDetails")
                    .setId(p.getContentDetails().getVideoId())
                    .execute();
            for(Video v: videoResponse.getItems()) {
                videoList.add(v);
            }
        }
        return videoList;
    }
}
