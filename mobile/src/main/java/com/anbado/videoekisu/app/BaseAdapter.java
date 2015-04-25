package com.anbado.videoekisu.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public abstract class BaseAdapter<T, VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<VH> {

    private Context mContext;

    private Set<T> mSet;
    private LinkedList<T> mItems;

    private SimpleOnInteractionListener mOnIntentInteractionListener;

    public BaseAdapter(Context context) {
        mContext = context;
        mSet = Sets.newHashSet();
        mItems = Lists.newLinkedList();
    }


    public final Context getContext() {
        return mContext;
    }


    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public final T getItem(int position) {
        return mItems.get(position);
    }

    public void add(T item) {
        if (mSet.add(item)) {
            mItems.add(item);
        }
    }

    public void addAll(Collection<T> collection) {
        for (T item : collection) {
            add(item);
            notifyItemChanged(mItems.indexOf(item));
        }
    }


    public synchronized void setItems(List<T> items) {
        mItems = Lists.newLinkedList(items);
        mSet = Sets.newHashSet(items);
    }

    public void remove(int position) {
        T t = mItems.remove(position);
        mSet.remove(t);
    }

    public void clear() {
        mItems.clear();
        mSet.clear();
    }


    public final void setOnIntentInteractionListener(SimpleOnInteractionListener l) {
        mOnIntentInteractionListener = l;
    }


    public final void startActivity(Intent intent, @Nullable Bundle options) {
        if (mOnIntentInteractionListener != null)
            mOnIntentInteractionListener.startActivity(intent, options);
    }
}
