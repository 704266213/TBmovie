package com.golove.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.golove.R;
import com.golove.adapter.FilmComingAdapter;
import com.golove.adapter.LoopViewPagerAdapter;
import com.golove.divider.FilmDivider;
import com.golove.ui.neterror.NetWorkErrorView;
import com.golove.ui.reflesh.PtrClassicFrameLayout;
import com.golove.ui.reflesh.PtrDefaultHandler;
import com.golove.ui.reflesh.PtrFrameLayout;
import com.golove.ui.reflesh.PtrHandler;
import com.golove.ui.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;
import com.golove.ui.stickyheadersrecyclerview.StickyRecyclerHeadersTouchListener;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FilmComingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FilmComingFragment extends TabFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FilmComingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FilmComingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FilmComingFragment newInstance(String param1, String param2) {
        FilmComingFragment fragment = new FilmComingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    private String mItemData = "iorem m m";

    private RecyclerView recyclerView;
    private FilmComingAdapter filmComingAdapter;
    private AppBarLayout appBarLayout;
    private PtrClassicFrameLayout ptrFrameLayout;
    private int verticalOffsetY;
    private NetWorkErrorView netWorkErrorView;

    private View headView;
    private ViewPager viewPager;
    private ViewGroup indicators;
    private LoopViewPagerAdapter mPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_film_coming, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        netWorkErrorView = (NetWorkErrorView) view.findViewById(R.id.netWorkErrorView);

//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addItemDecoration(new FilmDivider(
//                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line),(int)getResources().getDimension(R.dimen.film_line_paddingLeft)));
//
//        recyclerView.setHasFixedSize(true);



//        headView = LayoutInflater.from(view.getContext()).inflate(R.layout.film_hit_headerview, null, false);
//        filmHitRecyclerAdapter = new FilmHitRecyclerAdapter(list);
//        filmHitRecyclerAdapter.setHeadView(headView);
//
//        viewPager = (ViewPager) headView.findViewById(R.id.viewPager);
//        indicators = (ViewGroup) headView.findViewById(R.id.indicators);
//        mPagerAdapter = new LoopViewPagerAdapter(viewPager, indicators);
//        viewPager.setAdapter(mPagerAdapter);
//        viewPager.addOnPageChangeListener(mPagerAdapter);
//
//
//        recyclerView.setAdapter(filmHitRecyclerAdapter);


        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                Log.e("TAG", "====Height==========" + appBarLayout.getHeight());
                FilmComingFragment.this.verticalOffsetY = verticalOffset;
            }
        });
        ptrFrameLayout = (PtrClassicFrameLayout) view.findViewById(R.id.ptrFrameLayout);
        ptrFrameLayout.disableWhenHorizontalMove(true);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return verticalOffsetY == 0 && PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ptrFrameLayout.refreshComplete();
                    }
                }, 3 * 1000);
            }
        });


        // Set adapter populated with example dummy data
        filmComingAdapter = new FilmComingAdapter();
        recyclerView.setAdapter(filmComingAdapter);


        // Set layout manager
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(filmComingAdapter);
        recyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        recyclerView.addItemDecoration(new FilmDivider(
                getActivity(), LinearLayoutManager.VERTICAL, 1, getResources().getColor(R.color.top_line)));

        // Add touch listeners
        StickyRecyclerHeadersTouchListener touchListener =
                new StickyRecyclerHeadersTouchListener(recyclerView, headersDecor);
        touchListener.setOnHeaderClickListener(
                new StickyRecyclerHeadersTouchListener.OnHeaderClickListener() {
                    @Override
                    public void onHeaderClick(View header, int position, long headerId) {
                        Toast.makeText(getContext(), "Header position: " + position + ", id: " + headerId,
                                Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }


    @Override
    public void onTabChange(int position) {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ptrFrameLayout.setVisibility(View.VISIBLE);
                netWorkErrorView.setVisibility(View.GONE);
                filmComingAdapter.addAll(mItemData.split(" "));
            }
        },3*1000);

    }

    public void requestData() {
        Log.e("XLog", "=======即将上映===============");
    }

    @Override
    public void onRequestCallBackSuccess(Object bean) {

    }

    @Override
    public void onRequestCallBackError() {

    }
}
