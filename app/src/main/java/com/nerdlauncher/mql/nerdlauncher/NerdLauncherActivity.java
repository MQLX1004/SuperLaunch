package com.nerdlauncher.mql.nerdlauncher;

import android.support.design.internal.BottomNavigationItemView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class NerdLauncherActivity extends SingleFragmentActivity {
    private DrawerLayout mDrawerLayout;
    private ImageView mImageView;

    @Override
    protected Fragment CreatFragment() {
        return NerdLauncherFragment.newIntence();
    }
}
