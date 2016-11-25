package com.golove.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.golove.R;
import com.golove.fragment.MainFragment;

public class MainActivity extends AppCompatActivity {

    private long mExitTime;
    private RadioGroup tab;
    private FragmentManager fragmentManager;
    private MainFragment[] baseFragments;
    private int selectPosition;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        initView();
    }

    private void initView() {
        fragmentManager = getSupportFragmentManager();
        tab = (RadioGroup) findViewById(R.id.tab);
        int count = tab.getChildCount();
        baseFragments = new MainFragment[count];

        for (int i = 0; i < count; i++) {
            tab.getChildAt(i).setOnClickListener(new OnItemClickListener(i));
            addFragment(i);
        }
    }


    public class OnItemClickListener implements View.OnClickListener {

        private int position;

        public OnItemClickListener(int position) {
            this.position = position;
        }

        public void onClick(View v) {
            if (selectPosition != position) {
                swichFragment(position);
                MainFragment mainFragment = baseFragments[position];
                if(!mainFragment.isRequest){
                    mainFragment.requestData();
                    mainFragment.isRequest = true;
                }
            }
        }
    }


    public void swichFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(baseFragments[selectPosition]);
        selectPosition = index;
        transaction.show(baseFragments[selectPosition]);
        transaction.commit();
    }

    private void addFragment(int index) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        MainFragment baseFragment = MainFragment.getFragmentInstance(index);
        if (baseFragment != null) {
            transaction.add(R.id.container, baseFragment);
            baseFragments[index] = baseFragment;
        }
        if (index == selectPosition) {
            transaction.show(baseFragment);
        } else {
            transaction.hide(baseFragment);
        }
        transaction.commit();
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
                //杀死自己的进程
//                android.os.Process.killProcess(Process.myPid());
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onSaveInstanceState(Bundle outState) {
        //将这一行注释掉，阻止activity保存fragment的状态
        //super.onSaveInstanceState(outState);
    }

}
