package com.gnicolaou.user.moviereview;


import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.gnicolaou.user.moviereview.adapters.MainAdapter;
import com.gnicolaou.user.moviereview.databases.FavoriteMoviesDB;
import com.gnicolaou.user.moviereview.model.MovieReviewResponse;
import com.gnicolaou.user.moviereview.model.SqlData;
import com.gnicolaou.user.moviereview.services.MovieReviewCalls;
import com.gnicolaou.user.moviereview.services.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment {

    View view;
    FavoriteMoviesDB favoriteMoviesDB;
    MainAdapter mainAdapter;

    private RecyclerView mRecyclerView;
    private MovieReviewResponse mMovieReviewResponse;
    private EditText searchInput;
    private Button searchBtn;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);

        favoriteMoviesDB = new FavoriteMoviesDB(getActivity());

        progressBar = view.findViewById(R.id.progress_bar);
        searchBtn = view.findViewById(R.id.search_btn);
        searchInput = view.findViewById(R.id.search_input);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasInternetConnection()) {
                    if (searchInput.getText().toString().trim().length() > 0) {
                        executeApi(searchInput.getText().toString());
//                        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
//                                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                        progressBar.setVisibility(View.VISIBLE);
                    } else
                        Toast.makeText(getActivity(), "Enter a valid movie title", Toast.LENGTH_SHORT).show();
                }
                else Toast.makeText(getActivity(), "No Internet Connection!", Toast.LENGTH_SHORT).show();
            }
        });

        mRecyclerView = view.findViewById(R.id.recyclerview);

        swipeRefreshLayout=view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    // query = user input / movie title
    private void executeApi(String query) {
        MovieReviewCalls movieReviewCalls = ServiceGenerator.createService(MovieReviewCalls.class);
        Call<MovieReviewResponse> call = movieReviewCalls.getMovieReview(
                "a7a879f6a9474c83b9fa964e3f60fdcd", query);
        call.enqueue(new Callback<MovieReviewResponse>() {
            @Override
            public void onResponse(Call<MovieReviewResponse> call, Response<MovieReviewResponse> response) {
                if (getActivity()==null)
                    return;
                if(!response.isSuccessful()){
                    Toast.makeText(getActivity(), "No response", Toast.LENGTH_SHORT).show();
                }else{
                    mMovieReviewResponse = response.body();
                    initRecyclerView();
                }

//                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressBar.setVisibility(View.GONE);
//                searchInput.setText("");
            }

            @Override
            public void onFailure(Call<MovieReviewResponse> call, Throwable t) {
                if (getActivity()==null)
                    return;
//                getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                progressBar.setVisibility(View.GONE);
//                searchInput.setText("");
                Toast.makeText(getActivity(), "Search failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initRecyclerView() {
        mainAdapter = new MainAdapter(mMovieReviewResponse.getResults());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(mainAdapter);

        if (mainAdapter.getItemCount()==0)
            Toast.makeText(getActivity(), "No movies found!",Toast.LENGTH_SHORT).show();
        else Toast.makeText(getActivity(), "Search Successful", Toast.LENGTH_SHORT).show();
    }


    private Boolean hasInternetConnection(){
        if (getActivity()==null)
            return false;
        ConnectivityManager cm = (ConnectivityManager)getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

}
