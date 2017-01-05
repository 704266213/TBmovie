package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.golove.R;
import com.golove.model.HallModel;

import java.util.List;

/**
 *
 */

public class RecentFilmAdapter extends RecyclerView.Adapter<RecentFilmAdapter.RecentFileViewHolder> {

    private List<HallModel> hallModels;

    public RecentFilmAdapter(List<HallModel> hallModels) {
        this.hallModels = hallModels;
    }

    @Override
    public int getItemCount() {
        return hallModels.size();
    }

    public int getItemViewType(int position) {
        return 1;
    }


    @Override
    public RecentFilmAdapter.RecentFileViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recent_film_item, viewGroup, false);
        return new RecentFilmAdapter.RecentFileViewHolder(view);
    }


    public void onBindViewHolder(RecentFilmAdapter.RecentFileViewHolder recyclerViewHolder, int position) {
        HallModel hallModel = hallModels.get(position);
        recyclerViewHolder.playTime.setText(hallModel.getPlayTime());
        recyclerViewHolder.hall.setText(hallModel.getHall());
        recyclerViewHolder.price.setText(hallModel.getPrice());
    }


    public class RecentFileViewHolder extends RecyclerView.ViewHolder {

        private TextView playTime;
        private TextView hall;
        private TextView price;

        public RecentFileViewHolder(View itemView) {
            super(itemView);
            playTime = (TextView) itemView.findViewById(R.id.playTime);
            hall = (TextView) itemView.findViewById(R.id.hall);
            price = (TextView) itemView.findViewById(R.id.price);
        }
    }
}
