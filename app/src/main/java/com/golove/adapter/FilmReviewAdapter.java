package com.golove.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.GoloveApplication;
import com.golove.R;
import com.squareup.picasso.Picasso;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-12-12 23:44
 * 修改备注：
 */

public class FilmReviewAdapter extends RecyclerView.Adapter<FilmReviewAdapter.FilmViewHolder>{


    @Override
    public int getItemCount() {
        return 10;
    }

    public int getItemViewType(int position) {
        return 1;
    }


    @Override
    public FilmReviewAdapter.FilmViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_review_item, viewGroup, false);
        return new FilmReviewAdapter.FilmViewHolder(view);
    }


    public void onBindViewHolder(FilmReviewAdapter.FilmViewHolder recyclerViewHolder, int position) {
        String url = "https://raw.githubusercontent.com/704266213/data/master/WebContent/img/yourname.png";
        Picasso.with(GoloveApplication.goloveApplication).load(url).into(recyclerViewHolder.filmUrl);

    }


    public class FilmViewHolder extends RecyclerView.ViewHolder {
        private ImageView filmUrl;
        private TextView filmName;
        private TextView filmAmount;
        private TextView filmDate;

        public FilmViewHolder(View itemView) {
            super(itemView);
            filmUrl = (ImageView) itemView.findViewById(R.id.filmUrl);
            filmName = (TextView) itemView.findViewById(R.id.filmName);
            filmAmount = (TextView) itemView.findViewById(R.id.filmAmount);
            filmDate = (TextView) itemView.findViewById(R.id.filmDate);
        }
    }
}
