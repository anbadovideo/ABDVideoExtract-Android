package com.anbado.videoekisu.ui.main;


import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anbado.videoekisu.R;
import com.anbado.videoekisu.app.BaseFragment;

import java.util.Collections;
import java.util.List;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public class NavigationDrawerFragment extends BaseFragment {


    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private View mFragmentContainerView;

    private List<NavigationMenu> mItems;

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mItems = Collections.emptyList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);


        createNavDrawerItems(rootView);
        return rootView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        mDrawerToggle = new ActionBarDrawerToggle(
                getActivity(),                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close/* "close drawer" description for accessibility */
        ) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }
                getActivity().invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.setDrawerListener(mDrawerToggle);

        // If the user hasn't 'learned' about the drawer, open it to introduce them to the drawer,
        // per the navigation drawer design guidelines.

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    public void setItems(List<NavigationMenu> items) {
        mItems = items;
    }

    private void createNavDrawerItems(View rootView) {
        ViewGroup drawerItemsListContainer = (ViewGroup) rootView.findViewById(R.id.drawer_items_list);
        if (drawerItemsListContainer == null) {
            return;
        }

        drawerItemsListContainer.removeAllViews();

        for (NavigationMenu menu : mItems) {
            View view = makeDrawerItem(drawerItemsListContainer, menu);
            drawerItemsListContainer.addView(view);
        }
    }


    private View makeDrawerItem(ViewGroup container, final NavigationMenu menu) {
        int layoutToInflate = R.layout.navigation_drawer_item;
        View view = LayoutInflater.from(getActivity())
                .inflate(layoutToInflate, container, false);


        ImageView iconView = (ImageView) view.findViewById(R.id.icon);
        TextView titleView = (TextView) view.findViewById(R.id.title);
        iconView.setImageResource(menu.getDrawableResId());

        titleView.setText(menu.getStringResId());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectItem(menu);
            }
        });

        return view;
    }


    private void selectItem(NavigationMenu menu) {
        if (mDrawerLayout != null) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        }
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(menu);
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Callbacks interface that all activities using this fragment must implement.
     */
    public interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        void onNavigationDrawerItemSelected(NavigationMenu menu);
    }


    public interface NavigationMenu {
        String getKey();

        int getStringResId();

        int getDrawableResId();
    }

}
