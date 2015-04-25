package com.anbado.videoekisu.ui.ekisu;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anbado.videoekisu.R;
import com.anbado.videoekisu.app.BaseAdapter;
import com.anbado.videoekisu.model.Ekisu;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public class EkisuAdapter extends BaseAdapter<Ekisu, EkisuViewHolder> {

    public EkisuAdapter(Context context) {
        super(context);
    }

    @Override
    public EkisuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(getContext())
                .inflate(R.layout.list_item_ekisu, parent, false);
        return new EkisuViewHolder(getContext(), view);
    }

    @Override
    public void onBindViewHolder(EkisuViewHolder holder, int position) {
        final Ekisu item = getItem(position);
        holder.onBind(item);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                startActivity(intent, null);
            }
        });

    }
}
