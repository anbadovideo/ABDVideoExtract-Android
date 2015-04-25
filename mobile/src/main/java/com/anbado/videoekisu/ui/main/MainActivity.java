package com.anbado.videoekisu.ui.main;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import com.anbado.videoekisu.R;
import com.anbado.videoekisu.app.BaseActivity;
import com.anbado.videoekisu.ui.ekisu.EkisuFragment;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public class MainActivity extends BaseActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = getTitle();

        //  Fragment managing the behaviors, interactions and presentation of the navigation drawer.
        NavigationDrawerFragment navigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.setStatusBarBackgroundColor(getResources().getColor(R.color.theme_color_primary_dark));

        navigationDrawerFragment.setUp(R.id.navigation_drawer, drawerLayout);
        navigationDrawerFragment.setItems(list());

        onNavigationDrawerItemSelected(list().get(0));
    }

    @Override
    public void onNavigationDrawerItemSelected(NavigationDrawerFragment.NavigationMenu menu) {
        restoreActionBar();
        getFragmentManager().beginTransaction()
                .replace(R.id.container, new EkisuFragment())
                .commit();
    }


    private List<NavigationDrawerFragment.NavigationMenu> list() {
        List<NavigationDrawerFragment.NavigationMenu> list = Lists.newArrayList();


        list.add(new NavigationDrawerFragment.NavigationMenu() {
            @Override
            public String getKey() {
                return null;
            }

            @Override
            public int getStringResId() {
                return R.string.abc_action_bar_home_description;
            }

            @Override
            public int getDrawableResId() {
                return R.drawable.ic_logo_48dp;
            }
        });


        return list;
    }


    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(mTitle);
        }
    }
}
