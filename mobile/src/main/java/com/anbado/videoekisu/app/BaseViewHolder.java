package com.anbado.videoekisu.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    private Context mContext;


    public BaseViewHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
    }


    public final Context getContext() {
        return mContext;
    }


    public abstract void onBind(T item);
}
