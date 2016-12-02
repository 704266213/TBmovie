package com.golove.ui.footer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.golove.R;
import com.golove.listener.OnLoadMoreListener;
import com.golove.ui.MaterialProgressBar;
import com.golove.ui.OnLoadDataListener;


public class FooterView extends RelativeLayout implements OnLoadDataListener {

    private MaterialProgressBar progressBar;
    private TextView reLoadMore;
    private OnLoadMoreListener onLoadMoreListener;

    public FooterView(Context context) {
        super(context);
        init(context);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void setOnLoadMoreListener(OnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    public void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.footer_loading, this);

        progressBar = (MaterialProgressBar) findViewById(R.id.progressBar);
        reLoadMore = (TextView) findViewById(R.id.reLoadMore);
        reLoadMore.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingDataView();
                if(onLoadMoreListener != null){
                    onLoadMoreListener.onLoadMore(true);
                }
            }
        });
    }

    @Override
    public void loadDataErrorView() {
        progressBar.setVisibility(GONE);
        reLoadMore.setVisibility(VISIBLE);
    }

    @Override
    public void loadingDataView() {
        progressBar.setVisibility(VISIBLE);
        reLoadMore.setVisibility(GONE);
    }

    public void loadNoDataOrNoMoreDataView() {
        progressBar.setVisibility(GONE);
        reLoadMore.setVisibility(VISIBLE);
        reLoadMore.setClickable(false);
        reLoadMore.setText(getResources().getString(R.string.no_more_load));
    }
}
