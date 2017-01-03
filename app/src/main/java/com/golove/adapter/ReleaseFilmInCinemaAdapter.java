package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.R;

/**
 *
 */

public class ReleaseFilmInCinemaAdapter extends RecyclerView.Adapter<ReleaseFilmInCinemaAdapter.ReleaseFilmInCinemaAdapterHolder> {

    private int itemCount = 8;

    public void updateAdapterData(int itemCount){
        this.itemCount = itemCount;
        notifyDataSetChanged();
    }

    @Override
    public ReleaseFilmInCinemaAdapter.ReleaseFilmInCinemaAdapterHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.release_film_in_cinema_item, parent, false);
        return new ReleaseFilmInCinemaAdapter.ReleaseFilmInCinemaAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(ReleaseFilmInCinemaAdapter.ReleaseFilmInCinemaAdapterHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return itemCount;
    }

    public final class ReleaseFilmInCinemaAdapterHolder extends RecyclerView.ViewHolder {
        private ImageView lastedCinema;
        private TextView distance;
        private TextView location;
        private TextView cinemaName;
        private TextView cinemaMinPrice;
        private TextView cinemaInfo1;
        private TextView cinemaInfo2;
        private TextView cinemaDiscount;
        private TextView cinemaCard;

        public ReleaseFilmInCinemaAdapterHolder(View itemView) {
            super(itemView);
            lastedCinema = (ImageView) itemView.findViewById(R.id.lastedCinema);
            distance = (TextView) itemView.findViewById(R.id.distance);
            location = (TextView) itemView.findViewById(R.id.location);
            cinemaName = (TextView) itemView.findViewById(R.id.cinemaName);
            cinemaMinPrice = (TextView) itemView.findViewById(R.id.cinemaMinPrice);
            cinemaInfo1 = (TextView) itemView.findViewById(R.id.cinemaInfo1);
            cinemaInfo2 = (TextView) itemView.findViewById(R.id.cinemaInfo2);
            cinemaDiscount = (TextView) itemView.findViewById(R.id.cinemaDiscount);
            cinemaCard = (TextView) itemView.findViewById(R.id.cinemaCard);
        }
    }
}
