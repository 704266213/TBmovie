package com.golove.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.golove.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView back = (TextView)toolbar.findViewById(R.id.back);
        back.setText(getTitle());

        toolbar.setNavigationIcon(R.mipmap.back);

        Log.e("TAG", "title ======== " + getTitle());

//        toolbar.setLogo(R.mipmap.back);
//        toolbar.setTitle("设置");
        toolbar.setTitleTextColor(Color.WHITE);
//        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                Log.e("TAG", "msg ======== " + menuItem.getItemId());
//                // Handle the menu item
//                switch (menuItem.getItemId()) {
//                    case android.R.id.home:
//                        Log.e("TAG", "msg ======== Back");
//                        break;
//                    case R.id.main:
//                        Log.e("TAG","msg ======== main");
//                        break;
//                    case R.id.action:
//                        Log.e("TAG","msg ======== action");
//                        break;
//                }
//
//                return true;
//            }
//        });
//        // Inflate a menu to be displayed in the toolbar
//        toolbar.inflateMenu(R.menu.menu_main);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Log.e("TAG", "msg ======== " + menuItem.getItemId());
        // Handle the menu item
        switch (menuItem.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.main:
                Log.e("TAG","msg ======== main");
                break;
            case R.id.action:
                Log.e("TAG","msg ======== action");
                break;
        }

        return super.onOptionsItemSelected(menuItem);
    }

}
