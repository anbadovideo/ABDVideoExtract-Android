package com.anbado.videoekisu.ui.ekisu;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anbado.videoekisu.R;
import com.anbado.videoekisu.app.BaseFragment;
import com.anbado.videoekisu.app.SimpleOnInteractionListener;
import com.anbado.videoekisu.model.Ekisu;
import com.anbado.videoekisu.retrofit.RetrofitProvider;
import com.anbado.videoekisu.retrofit.service.EkisuServices;
import com.anbado.videoekisu.util.Pagination;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public class EkisuFragment extends BaseFragment {

    private SwipeRefreshLayout mSwipeRefreshLayout;

    private EkisuAdapter mAdapter;
    private GridLayoutManager mLayoutManager;

    private Pagination mPagination;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPagination = new Pagination();

        mAdapter = new EkisuAdapter(getActivity());
        mAdapter.setOnIntentInteractionListener(new SimpleOnInteractionListener(getActivity()));
        mLayoutManager = new GridLayoutManager(getActivity(), 1);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mPagination.setVisibleItemCount(mLayoutManager.getChildCount());
                mPagination.setTotalItemCount(mLayoutManager.getItemCount());
                mPagination.setPastVisibleItems(mLayoutManager.findFirstVisibleItemPosition());

                if (mPagination.isHasNext() && mPagination.isPagination()) {
                    if (!mPagination.isLoading()) {
                        loadData();
                    }
                }
            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        mSwipeRefreshLayout.setColorSchemeColors(getColorPrimary());
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPagination.setPage(1);
                mPagination.setReloading(true);

                loadData();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
    }

    public void loadData() {
        mPagination.setLoading(true);
        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
            }
        });
        requestList(mPagination.getPage());
    }

    private void requestList(Integer page) {
        EkisuServices services = RetrofitProvider.create(EkisuServices.class);
        services.getList(page, new Callback<JSONObject>() {
            @Override
            public void success(JSONObject object, Response response) {
                String next = object.optString("next");
                if (TextUtils.isEmpty(next) || "null".equalsIgnoreCase(next)) {
                    mPagination.setHasNext(false);
                } else {
                    Uri uri = Uri.parse(next);
                    String page = uri.getQueryParameter("page");
                    mPagination.setPage(Ints.tryParse(page));
                    mPagination.setHasNext(true);
                }

                JSONArray array = object.optJSONArray("results");
                if (array == null) {
                    array = new JSONArray();
                }

                List<Ekisu> list = Lists.newArrayList();
                Collections.addAll(list, new Gson().fromJson(array.toString(), Ekisu[].class));
                updateCollectionView(list);
            }

            @Override
            public void failure(RetrofitError error) {
                updateCollectionView(error);
            }
        });
    }


    private void updateCollectionView(List<Ekisu> list) {
        if (mPagination.isReloading()) {
            mAdapter.clear();
        }

        mAdapter.addAll(list);
        mAdapter.notifyDataSetChanged();
        mSwipeRefreshLayout.setRefreshing(false);
        mPagination.setLoading(false);
        mPagination.setReloading(false);
    }

    private void updateCollectionView(Throwable th) {
        mSwipeRefreshLayout.setRefreshing(false);
        mPagination.setLoading(false);
        mPagination.setReloading(false);



    }


}
