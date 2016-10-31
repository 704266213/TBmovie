package com.golove.fragment;


import android.support.v4.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class TabFragment extends Fragment {


    protected boolean isSwitch = false;

    public abstract void onTabChange(int position);


}
