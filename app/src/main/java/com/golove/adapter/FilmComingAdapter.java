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
import com.golove.ui.stickyheadersrecyclerview.StickyRecyclerHeadersAdapter;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 类描述：
 * 创建人：alan
 * 创建时间：2016-08-08 17:41
 * 修改备注：
 */
public class FilmComingAdapter extends BaseRecyclerAdapter<FilmModel, FilmComingAdapter.FilmViewHolder> implements StickyRecyclerHeadersAdapter<RecyclerView.ViewHolder> {

    private OnRecycleViewClickListener.OnRecycleViewItemClickListener onRecycleViewItemClickListener;

    public void setOnRecycleViewClickListener(OnRecycleViewClickListener.OnRecycleViewItemClickListener onRecycleViewItemClickListener) {
        this.onRecycleViewItemClickListener = onRecycleViewItemClickListener;
    }


    public FilmComingAdapter() {
        setHasStableIds(true);
    }

    public void onBodyBindViewHolder(FilmComingAdapter.FilmViewHolder recyclerViewHolder, int position) {
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

    public FilmComingAdapter.FilmViewHolder onCreateBodyViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.film_hit_list_item, viewGroup, false);
        return new FilmComingAdapter.FilmViewHolder(view);
    }

    public class FilmViewHolder extends RecyclerView.ViewHolder {
        private ImageView filmUrl;
        private TextView filmName;
        private TextView filmActor;
        private RatingBar scorebar;
        private TextView filmScore;
        private TextView filmDesc;
        private TextView tickets;

        public FilmViewHolder(View itemView) {
            super(itemView);
            filmUrl = (ImageView) itemView.findViewById(R.id.filmUrl);
            filmName = (TextView) itemView.findViewById(R.id.filmName);
            filmActor = (TextView) itemView.findViewById(R.id.filmActor);
            scorebar = (RatingBar) itemView.findViewById(R.id.scorebar);
            filmScore = (TextView) itemView.findViewById(R.id.filmScore);
            filmDesc = (TextView) itemView.findViewById(R.id.filmDesc);
            tickets = (TextView) itemView.findViewById(R.id.tickets);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public long getHeaderId(int position) {
        if (position > 0 && position < listData.size()) {
            FilmModel filmModel = listData.get(position - 1);
            String str = filmModel.getReleaseDate();
            SimpleDateFormat simpleDateFormat;
            Date date;
            try {
                simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                date = simpleDateFormat.parse(str.split(" ")[0]);
                //继续转换得到秒数的long型
                return date.getTime() / 1000;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.file_coming_header, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position > 0 && position < listData.size()) {
            FilmModel filmModel = listData.get(position - 1);
            TextView textView = (TextView) holder.itemView;
            textView.setText(filmModel.getReleaseDate());
        }
    }


    public FilmModel getItemData(int posiiton) {
        if (listData != null && listData.size() > 0) {
            return listData.get(posiiton);
        }
        return null;
    }


}