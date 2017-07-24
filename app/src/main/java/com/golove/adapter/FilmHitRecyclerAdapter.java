package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.golove.listener.OnRecycleViewClickListener;
import com.golove.model.FilmModel;
import com.squareup.picasso.Picasso;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-08-08 15:28
 * 修改备注：
 */
public class FilmHitRecyclerAdapter extends BaseRecyclerAdapter<FilmModel, FilmHitRecyclerAdapter.FilmViewHolder> {

    private OnRecycleViewClickListener.OnRecycleViewItemClickListener onRecycleViewItemClickListener;

    public void setOnRecycleViewClickListener(OnRecycleViewClickListener.OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
        this.onRecycleViewItemClickListener = onRecycleViewItemClickListener;
    }

    public void onBodyBindViewHolder(FilmHitRecyclerAdapter.FilmViewHolder recyclerViewHolder, int position) {
        final FilmModel filmModel = listData.get(position);
        Picasso.with(GoloveApplication.goloveApplication).load(filmModel.getFilmUrl()).into(recyclerViewHolder.filmUrl);
        recyclerViewHolder.filmName.setText(filmModel.getFilmName());
        recyclerViewHolder.filmActor.setText(filmModel.getFilmActor());
        String filmScore = filmModel.getFilmScore();
        recyclerViewHolder.scorebar.setRating(Float.parseFloat(filmScore) / 2);
        recyclerViewHolder.filmScore.setText(filmScore);
        recyclerViewHolder.filmDesc.setText(filmModel.getFilmDesc());

        recyclerViewHolder.tickets.setOnClickListener(new OnRecycleViewClickListener(onRecycleViewItemClickListener, recyclerViewHolder.tickets, position));
        recyclerViewHolder.itemView.setOnClickListener(new OnRecycleViewClickListener(onRecycleViewItemClickListener, recyclerViewHolder.itemView, position));
    }

    public FilmHitRecyclerAdapter.FilmViewHolder onCreateBodyViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_hit_list_item, viewGroup, false);
        return new FilmViewHolder(view);
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView filmUrl;
        private TextView filmName;
        private TextView filmActor;
        private RatingBar scorebar;
        private TextView filmScore;
        private TextView filmDesc;
        private TextView tickets;

        public FilmViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            filmUrl = (ImageView) itemView.findViewById(R.id.filmUrl);
            filmName = (TextView) itemView.findViewById(R.id.filmName);
            filmActor = (TextView) itemView.findViewById(R.id.filmActor);
            scorebar = (RatingBar) itemView.findViewById(R.id.scorebar);
            filmScore = (TextView) itemView.findViewById(R.id.filmScore);
            filmDesc = (TextView) itemView.findViewById(R.id.filmDesc);
            tickets = (TextView) itemView.findViewById(R.id.tickets);
        }
    }


    public FilmModel getItemData(int posiiton) {
        if (listData != null && listData.size() > 0) {
            return listData.get(posiiton);
        }
        return null;
    }

}
