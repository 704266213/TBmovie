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
import com.golove.model.CinemaInfo;
import com.golove.model.CinemaModel;

import java.util.ArrayList;
import java.util.List;



/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-12-12 21:35
 * 修改备注：
 */

public class CinemaAdapter extends BaseRecyclerAdapter<CinemaModel, CinemaAdapter.FilmViewHolder> {


    public void onBodyBindViewHolder(CinemaAdapter.FilmViewHolder recyclerViewHolder, int position) {

        CinemaModel cinemaModel = listData.get(position);
        recyclerViewHolder.cinemaName.setText(cinemaModel.getCinemaName());
        recyclerViewHolder.cinemaLocation.setText(cinemaModel.getCinemaLocal());
        recyclerViewHolder.cinemaDistance.setText(cinemaModel.getCinemaDistance());

        if (cinemaModel.getLastedCinema() == 1) {
            recyclerViewHolder.lastedCinema.setVisibility(View.VISIBLE);
        } else {
            recyclerViewHolder.lastedCinema.setVisibility(View.GONE);
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(cinemaModel.getCinemaMinPrice() + "元起");
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.parseColor("#FFE24557"));
        builder.setSpan(redSpan, 0, builder.length() - 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        recyclerViewHolder.cinemaMinPrice.setText(builder);

        List<CinemaInfo> cinemaInfos = cinemaModel.getCinemaInfos();
        int length = cinemaInfos.size();
        int size = recyclerViewHolder.textViews.size();
        for(int i = 0 ; i < size ; i++){
            TextView textView = recyclerViewHolder.textViews.get(i);
            if( i< length){
                CinemaInfo cinemaInfo = cinemaInfos.get(i);
                textView.setVisibility(View.VISIBLE);
                textView.setText(cinemaInfo.getInfo());
                int type = cinemaInfo.getType();
                if(type == 0){
                    textView.setTextColor(Color.parseColor("#FF67A3CE"));
                    textView.setBackgroundResource(R.drawable.cinema_info);
                } else if(type == 1){
                    textView.setTextColor(Color.parseColor("#FFEA9A58"));
                    textView.setBackgroundResource(R.drawable.cinema_discount);
                } else if(type == 2){
                    textView.setTextColor(Color.parseColor("#FF71B670"));
                    textView.setBackgroundResource(R.drawable.cinema_card);
                }

            } else {
                textView.setVisibility(View.GONE);
            }

        }

    }

    public CinemaAdapter.FilmViewHolder onCreateBodyViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cinema_list_item, viewGroup, false);
        return new CinemaAdapter.FilmViewHolder(view);
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        private ImageView lastedCinema;
        private TextView cinemaDistance;
        private TextView cinemaLocation;
        private TextView cinemaName;
        private TextView cinemaMinPrice;
        private TextView cinemaInfo1;
        private TextView cinemaInfo2;
        private TextView cinemaDiscount;
        private TextView cinemaCard;
        private List<TextView> textViews;


        public FilmViewHolder(View itemView) {
            super(itemView);
            textViews = new ArrayList<>();
            lastedCinema = (ImageView) itemView.findViewById(R.id.lastedCinema);
            cinemaDistance = (TextView) itemView.findViewById(R.id.cinemaDistance);
            cinemaLocation = (TextView) itemView.findViewById(R.id.cinemaLocation);
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
        }
    }

}
