package com.golove.adapter;

import android.graphics.Color;
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
import com.golove.model.FilmModel;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-12-12 21:35
 * 修改备注：
 */

public class CinemaAdapter extends BaseRecyclerAdapter<FilmModel, CinemaAdapter.FilmViewHolder>{


    public void onBodyBindViewHolder(CinemaAdapter.FilmViewHolder recyclerViewHolder, int position) {
        SpannableStringBuilder builder = new SpannableStringBuilder("17.8元起");
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#FFE24557"));
        builder.setSpan(redSpan, 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        recyclerViewHolder.cinemaMinPrice.setText(builder);

    }

    public CinemaAdapter.FilmViewHolder onCreateBodyViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cinema_list_item, viewGroup, false);
        return new CinemaAdapter.FilmViewHolder(view);
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        private ImageView lastedCinema;
        private TextView distance;
        private TextView location;
        private TextView cinemaName;
        private TextView cinemaMinPrice;
        private TextView cinemaInfo1;
        private TextView cinemaInfo2;
        private TextView cinemaDiscount;
        private TextView cinemaCard;



        public FilmViewHolder(View itemView) {
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
