package com.gnicolaou.user.moviereview.services;

import com.gnicolaou.user.moviereview.model.MovieReviewResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieReviewCalls {

    @GET("reviews/search.json")
    Call<MovieReviewResponse> getMovieReview(
            @Query("apikey") String apikey,
            @Query("query") String query);
}
