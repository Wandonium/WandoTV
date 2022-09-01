package com.wandonium.wandotv.details;

import androidx.leanback.widget.AbstractDetailsDescriptionPresenter;

import com.wandonium.wandotv.models.Movie;

public class DetailsDescriptionPresenter extends AbstractDetailsDescriptionPresenter {

    @Override
    protected void onBindDescription(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;

        if (movie != null) {
            viewHolder.getTitle().setText(movie.getTitle());
            viewHolder.getSubtitle().setText(movie.getStudio());
            viewHolder.getBody().setText(movie.getDescription());
        }
    }
}