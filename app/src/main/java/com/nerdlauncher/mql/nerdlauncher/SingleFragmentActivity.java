package com.nerdlauncher.mql.nerdlauncher;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by MQL on 2017/3/30.
 */
public abstract class SingleFragmentActivity extends AppCompatActivity{
    protected abstract Fragment CreatFragment();
    private DrawerLayout mDrawerLayout;
    private ImageView mImageView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        FragmentManager fragmentManager=getSupportFragmentManager();
        Fragment fragment=fragmentManager.findFragmentById(R.id.fragment_activity);
        if(fragment==null){
            fragment=CreatFragment();
            fragmentManager.beginTransaction().add(R.id.fragment_activity,fragment).commit();
        }
        initview();
        initclick();
    }
    private void initview(){
        mDrawerLayout=(DrawerLayout)findViewById(R.id.Main_DL_dl);
        mImageView=(ImageView)findViewById(R.id.Main_IV_menu);
    }
    private void initclick(){
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }
}
