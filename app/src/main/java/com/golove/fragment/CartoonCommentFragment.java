package com.golove.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.golove.R;
import com.golove.adapter.CartoonChapterAdapter;
import com.golove.adapter.CartoonCommentAdapter;
import com.golove.callback.RequestCallBack;
import com.golove.listener.OnLoadMoreListener;
import com.golove.loadmore.OnLinearLoadMoreListener;
import com.golove.model.ResultStateModel;
import com.golove.model.ReviewAllModel;
import com.golove.model.ReviewModel;
import com.golove.request.DeaultSendRequest;
import com.golove.request.DefaultBuildRequest;
import com.golove.ui.footer.FooterView;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CartoonCommentFragment extends TabFragment<ResultStateModel<ReviewAllModel>> implements OnLoadMoreListener {

    private TextView description;
    private ImageView authorImage;
    private TextView author;
    private TextView heat;
    private TextView follow;
    private ImageView submission;
    private FooterView footerView;
    private OnLinearLoadMoreListener onLinearLoadMoreListener;
    private RecyclerView recyclerView;
    private CartoonCommentAdapter cartoonCommentAdapter;

    public CartoonCommentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cartoon_comment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    private void initView(View view) {
       /*
         * 加载更多时，失败的回调设置
         */
        footerView = new FooterView(view.getContext());
        footerView.setOnLoadMoreListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        LinearLayoutManager filmReviewLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(filmReviewLayoutManager);
        recyclerView.setHasFixedSize(true);

        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View headView = layoutInflater.inflate(R.layout.cartoon_comment_head, recyclerView, false);
        description = (TextView) headView.findViewById(R.id.description);
        authorImage = (ImageView) headView.findViewById(R.id.authorImage);
        author = (TextView) headView.findViewById(R.id.author);
        heat = (TextView) headView.findViewById(R.id.heat);
        follow = (TextView) headView.findViewById(R.id.follow);
        submission = (ImageView) headView.findViewById(R.id.submission);

        onLinearLoadMoreListener = new OnLinearLoadMoreListener(footerView, this);
        recyclerView.addOnScrollListener(onLinearLoadMoreListener);

        cartoonCommentAdapter = new CartoonCommentAdapter(layoutInflater);
        cartoonCommentAdapter.setHeadView(headView);
        cartoonCommentAdapter.setFooterView(footerView);
        recyclerView.setAdapter(cartoonCommentAdapter);

        requestData();
    }

    @Override
    public void onRequestCallBackSuccess(ResultStateModel<ReviewAllModel> reviewAllModelResultStateModel) {
        ReviewAllModel reviewAllModel = reviewAllModelResultStateModel.getResult();
        if (reviewAllModel.getTotal() < 15) {
            footerView.loadNoDataOrNoMoreDataView();
            onLinearLoadMoreListener.setHasMore(false);
        } else {
            onLinearLoadMoreListener.setHasMore(true);
        }
        List<ReviewModel> reviewModels = reviewAllModel.getReviews();
        cartoonCommentAdapter.addDataToList(reviewModels);
    }

    @Override
    public void onRequestCallBackError() {

    }

    @Override
    public void onTabChange(int position) {

    }

    @Override
    public void requestData() {
        DefaultBuildRequest defaultBuildRequest = new DefaultBuildRequest("reviews.txt", "GET", null);
        DeaultSendRequest deaultSendRequest = new DeaultSendRequest(defaultBuildRequest);
        RequestCallBack requestCallBack = new RequestCallBack(this);
        deaultSendRequest.sendRequest(requestCallBack);
    }

    @Override
    public void onLoadMore() {
        requestData();
    }




}
