package com.golove.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.R;
import com.golove.divider.FilmDivider;
import com.golove.model.CinemaInfo;
import com.golove.model.CinemaModel;
import com.golove.model.HallModel;
import com.golove.model.ReleaseFilmInCinemaModel;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */

public class ReleaseFilmInCinemaAdapter extends RecyclerView.Adapter<ReleaseFilmInCinemaAdapter.ReleaseFilmInCinemaAdapterHolder> {

    private List<CinemaModel> cinemaModels = new ArrayList<>();
    private Context context;

    public void updateAdapterData(List<CinemaModel> cinemaModels) {
        this.cinemaModels = cinemaModels;
        notifyDataSetChanged();
    }

    @Override
    public ReleaseFilmInCinemaAdapter.ReleaseFilmInCinemaAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.release_film_in_cinema_item, parent, false);
        return new ReleaseFilmInCinemaAdapter.ReleaseFilmInCinemaAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(ReleaseFilmInCinemaAdapter.ReleaseFilmInCinemaAdapterHolder recyclerViewHolder, int position) {
        CinemaModel cinemaModel = cinemaModels.get(position);
        recyclerViewHolder.cinemaName.setText(cinemaModel.getCinemaName());
        recyclerViewHolder.cinemaLocation.setText(cinemaModel.getCinemaLocal());
        recyclerViewHolder.cinemaDistance.setText(cinemaModel.getCinemaDistance());

        List<HallModel> hallModels = cinemaModel.getHallInfos();
        if (hallModels != null) {
            if (cinemaModel.getLastedCinema() == 1) {
                recyclerViewHolder.lastedCinema.setVisibility(View.VISIBLE);
                recyclerViewHolder.recentFilm.setVisibility(View.GONE);
                recyclerViewHolder.recyclerView.setVisibility(View.VISIBLE);

                final LinearLayoutManager filmReviewLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

                recyclerViewHolder.recyclerView.setLayoutManager(filmReviewLayoutManager);
                recyclerViewHolder.recyclerView.setHasFixedSize(true);

                RecentFilmAdapter recentFilmAdapter = new RecentFilmAdapter(hallModels);
                recyclerViewHolder.recyclerView.setAdapter(recentFilmAdapter);

            } else {
                recyclerViewHolder.lastedCinema.setVisibility(View.GONE);
                recyclerViewHolder.recentFilm.setVisibility(View.VISIBLE);
                recyclerViewHolder.recyclerView.setVisibility(View.GONE);
                String recentFilmStr = "近期场次：";
                int size = hallModels.size();
                for (int i = 0; i < size; i++) {
                    if (i < 3) {
                        recentFilmStr = recentFilmStr + hallModels.get(i).getPlayTime() + " |";
                    }
                }
                recentFilmStr = recentFilmStr.substring(0, recentFilmStr.length() - 1) + "...";
                recyclerViewHolder.recentFilm.setText(recentFilmStr);
            }
        } else {
            recyclerViewHolder.recentFilm.setVisibility(View.GONE);
            recyclerViewHolder.recyclerView.setVisibility(View.GONE);
        }


        SpannableStringBuilder builder = new SpannableStringBuilder(cinemaModel.getCinemaMinPrice() + "元起");
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#FFE24557"));
        builder.setSpan(redSpan, 0, builder.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        recyclerViewHolder.cinemaMinPrice.setText(builder);

        List<CinemaInfo> cinemaInfos = cinemaModel.getCinemaInfos();
        int length = cinemaInfos.size();
        int size = recyclerViewHolder.textViews.size();
        for (int i = 0; i < size; i++) {
            TextView textView = recyclerViewHolder.textViews.get(i);
            if (i < length) {
                CinemaInfo cinemaInfo = cinemaInfos.get(i);
                textView.setVisibility(View.VISIBLE);
                textView.setText(cinemaInfo.getInfo());
                int type = cinemaInfo.getType();
                if (type == 0) {
                    textView.setTextColor(Color.parseColor("#FF67A3CE"));
                    textView.setBackgroundResource(R.drawable.cinema_info);
                } else if (type == 1) {
                    textView.setTextColor(Color.parseColor("#FFEA9A58"));
                    textView.setBackgroundResource(R.drawable.cinema_discount);
                } else if (type == 2) {
                    textView.setTextColor(Color.parseColor("#FF71B670"));
                    textView.setBackgroundResource(R.drawable.cinema_card);
                }

            } else {
                textView.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cinemaModels.size();
    }

    public final class ReleaseFilmInCinemaAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView lastedCinema;
        private TextView cinemaLocation;
        private TextView cinemaDistance;
        private TextView cinemaName;
        private TextView cinemaMinPrice;
        private TextView cinemaInfo1;
        private TextView cinemaInfo2;
        private TextView cinemaDiscount;
        private TextView cinemaCard;
        private List<TextView> textViews;

        private TextView recentFilm;
        private RecyclerView recyclerView;

        public ReleaseFilmInCinemaAdapterHolder(View itemView) {
            super(itemView);
            textViews = new ArrayList<>();
            lastedCinema = (ImageView) itemView.findViewById(R.id.lastedCinema);
            cinemaDistance = (TextView) itemView.findViewById(R.id.distance);
            cinemaLocation = (TextView) itemView.findViewById(R.id.location);
            cinemaName = (TextView) itemView.findViewById(R.id.cinemaName);
            cinemaMinPrice = (TextView) itemView.findViewById(R.id.cinemaMinPrice);
            cinemaInfo1 = (TextView) itemView.findViewById(R.id.cinemaInfo1);
            cinemaInfo2 = (TextView) itemView.findViewById(R.id.cinemaInfo2);
            cinemaDiscount = (TextView) itemView.findViewById(R.id.cinemaDiscount);
            cinemaCard = (TextView) itemView.findViewById(R.id.cinemaCard);
            textViews.add(cinemaInfo1);
            textViews.add(cinemaInfo2);
            textViews.add(cinemaDiscount);
            textViews.add(cinemaCard);
            recentFilm = (TextView) itemView.findViewById(R.id.recentFilm);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        }
    }
}
