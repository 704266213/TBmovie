package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.model.BannerModel;
import com.golove.model.FilmModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-08-08 15:28
 * 修改备注：
 */
public class FilmHitRecyclerAdapter extends RecyclerView.Adapter<FilmHitRecyclerAdapter.RecyclerViewHolder> {

    private static final int IS_HEADER = 2;
    private static final int IS_FOOTER = 3;
    private static final int IS_NORMAL = 1;

    private View headView;
    private View footerView;

    private OnBuyTicketListener onBuyTicketListener;

    public void setOnBuyTicketListener(OnBuyTicketListener onBuyTicketListener) {
        this.onBuyTicketListener = onBuyTicketListener;
    }

    public void setHeadView(View headView) {
        this.headView = headView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    private List<FilmModel> filmModels = new ArrayList<>();

    public void addData(List<FilmModel> filmModels){
        this.filmModels.addAll(filmModels);
        notifyDataSetChanged();
    }

    public void addFreshData(List<FilmModel> dataList) {
        this.filmModels.clear();
        addData(dataList);
    }

    @Override
    public int getItemCount() {
        if(filmModels.size() == 0){
            return 0;
        } else {
            if(headView != null && footerView != null){
                return filmModels.size() + 1;
            } else if(headView == null && footerView == null){
                return filmModels.size() - 1;
            } else {
                return filmModels.size();
            }
        }
    }

    public int getItemViewType(int position) {
        if (position == 0) {
            return IS_HEADER;
        } else if (position > 0 && position < filmModels.size()) {
            return IS_NORMAL;
        } else {
            return IS_FOOTER;
        }
    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        if (viewType == IS_HEADER) {
            if(headView != null){
                view = headView;
            }
        } else if (viewType == IS_FOOTER) {
            view = footerView;
        } else if (viewType == IS_NORMAL) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_hit_list_item, viewGroup, false);
        }

        return new RecyclerViewHolder(view, viewType);
    }


    public void onBindViewHolder(RecyclerViewHolder recyclerViewHolder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == IS_NORMAL) {
            final FilmModel filmModel = filmModels.get(position);
            Picasso.with(GoloveApplication.goloveApplication).load(filmModel.getFilmUrl()).into(recyclerViewHolder.filmUrl);
            recyclerViewHolder.filmName.setText(filmModel.getFilmName());
            recyclerViewHolder.filmActor.setText(filmModel.getFilmActor());
            String filmScore = filmModel.getFilmScore();
            recyclerViewHolder.scorebar.setRating(Float.parseFloat(filmScore)/2);
            recyclerViewHolder.filmScore.setText(filmScore);
            recyclerViewHolder.filmDesc.setText(filmModel.getFilmDesc());
            recyclerViewHolder.tickets.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   if(onBuyTicketListener != null){
                       onBuyTicketListener.buyTickey(filmModel);
                   }
                }
            });
        } else if (viewType == IS_HEADER) {

        } else {

        }

    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {
        private ImageView filmUrl;
        private TextView filmName;
        private TextView filmActor;
        private RatingBar scorebar;
        private TextView filmScore;
        private TextView filmDesc;
        private TextView tickets;

        public RecyclerViewHolder(View itemView, int viewType) {
            super(itemView);
            if (viewType == IS_NORMAL) {
                filmUrl = (ImageView) itemView.findViewById(R.id.filmUrl);
                filmName = (TextView) itemView.findViewById(R.id.filmName);
                filmActor = (TextView) itemView.findViewById(R.id.filmActor);
                scorebar = (RatingBar) itemView.findViewById(R.id.scorebar);
                filmScore = (TextView) itemView.findViewById(R.id.filmScore);
                filmDesc = (TextView) itemView.findViewById(R.id.filmDesc);
                tickets = (TextView) itemView.findViewById(R.id.tickets);
            } else if (viewType == IS_HEADER) {

            } else {

            }
        }
    }


    public interface OnBuyTicketListener{
        void buyTickey(FilmModel filmModel);
    }
}
