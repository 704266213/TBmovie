package com.golove.fragment;

import android.support.v4.app.Fragment;


public abstract class MainFragment extends Fragment {


    public static MainFragment getFragmentInstance(int position){
        MainFragment baseFragment = null;
        switch (position)
        {
            case 0:
                baseFragment = new FilmFragment();
                break;
            case 1:
                baseFragment = new CinemaFragment();
                break;
            case 2:
                baseFragment = new DamaiFragment();
                break;
            case 3:
                baseFragment = new DiscoveryFragment();
                break;
            case 4:
                baseFragment = new MineFragment();
                break;
        }
        return baseFragment;
    }



}
