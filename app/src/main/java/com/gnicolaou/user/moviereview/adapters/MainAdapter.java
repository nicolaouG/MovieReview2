package com.gnicolaou.user.moviereview.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.gnicolaou.user.moviereview.R;
import com.gnicolaou.user.moviereview.model.Result;
import com.gnicolaou.user.moviereview.util.ItemClickListener;

import java.util.List;

/*
Adapter and ViewHolder classes below used for the RecyclerView
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    List<Result> mResults;
    Context mContext;

    public MainAdapter(List<Result> mResults) {
        this.mResults = mResults;
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_adapter_item_view, parent, false);
        return new MainAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final MainAdapter.ViewHolder holder, int position) {
        mContext = holder.itemView.getContext();
        String x = String.valueOf(position);
        holder.movieNumber.setText(x);
        holder.movieTitle.setText(mResults.get(position).getDisplayTitle());
        holder.movieReview.setText(mResults.get(position).getSummaryShort()
                .replaceAll("&quot;", "\""));
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, final int position, boolean isLongClick) {
                if (view.getId()== R.id.textview2) {
                    Toast.makeText(mContext, mResults.get(position).getMpaaRating(),Toast.LENGTH_SHORT).show();
                }

                if (view.getId()== R.id.fav_btn){
                    holder.favouriteBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                            if (isChecked) {
                                //saveMovie(position, mResults.get(position).getDisplayTitle());
                                holder.favouriteBtn.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_y));
                            } else {
                                //deleteMovie(mResults.get(position).getDisplayTitle());
                                holder.favouriteBtn.setBackgroundDrawable(ContextCompat.getDrawable(mContext, R.drawable.favorite_icon_w));
                            }
                        }
                    });
                }
            }
        });
        // mResults not ready yet or 0 results
        if (getItemCount()==0){
            Toast.makeText(mContext, "No movies found with this title!",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        if (mResults!=null)
            return mResults.size();
        else return 0;
    }





    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener, View.OnLongClickListener {

        TextView movieTitle, movieReview, movieNumber;
        ItemClickListener itemClickListener;
        ToggleButton favouriteBtn;

        public ViewHolder(View itemView) {
            super(itemView);
            movieNumber = itemView.findViewById(R.id.textview1);
            movieReview = itemView.findViewById(R.id.textview2);
            movieTitle = itemView.findViewById(R.id.textview3);
            favouriteBtn = itemView.findViewById(R.id.fav_btn);
            movieReview.setOnClickListener(this);
            favouriteBtn.setOnClickListener(this);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onClick(view, getAdapterPosition(), true);
            return true;
        }
    }
}
