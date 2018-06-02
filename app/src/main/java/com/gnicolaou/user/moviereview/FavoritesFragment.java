package com.gnicolaou.user.moviereview;


import android.app.FragmentTransaction;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gnicolaou.user.moviereview.adapters.MainAdapter;
import com.gnicolaou.user.moviereview.adapters.SecondAdapter;
import com.gnicolaou.user.moviereview.databases.FavoriteMoviesDB;
import com.gnicolaou.user.moviereview.model.SqlData;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment {

    View view;
    FavoriteMoviesDB favoriteMoviesDB;

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    SecondAdapter secondAdapter;

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_favorites, container, false);

        mRecyclerView = view.findViewById(R.id.recyclerview2);
        swipeRefreshLayout=view.findViewById(R.id.swiperefresh);
        favoriteMoviesDB = new FavoriteMoviesDB(getActivity());

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                secondAdapter.setmSqlDataList(getSqlDataList());
                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRecyclerView();
    }


    private void initRecyclerView() {
        secondAdapter = new SecondAdapter(getSqlDataList(),favoriteMoviesDB);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setAdapter(secondAdapter);

        if (secondAdapter.getItemCount() == 0)
            Toast.makeText(getActivity(), "No favorite movies!", Toast.LENGTH_SHORT).show();
    }


    public List<SqlData> getSqlDataList() {
        List<SqlData> sqlDataList = new ArrayList<>();
        Cursor x = favoriteMoviesDB.getData();
        for (x.moveToFirst(); !x.isAfterLast(); x.moveToNext()) {
            int id=x.getInt(0); // COL1
            String title=x.getString(1); //COL2
            sqlDataList.add(new SqlData(id,title,""));
        }
        x.close();
        return sqlDataList;
    }
}
