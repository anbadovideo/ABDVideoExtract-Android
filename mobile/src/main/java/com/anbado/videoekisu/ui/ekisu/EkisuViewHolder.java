package com.anbado.videoekisu.ui.ekisu;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.widget.ImageView;

import com.anbado.videoekisu.R;
import com.anbado.videoekisu.app.BaseViewHolder;
import com.anbado.videoekisu.model.Ekisu;
import com.anbado.videoekisu.util.ImageLoader;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public class EkisuViewHolder extends BaseViewHolder<Ekisu> {

    private final AppCompatTextView mSubTitleView;
    private final AppCompatTextView mTitleView;
    private final ImageView mThumbnailView;


    public EkisuViewHolder(Context context, View itemView) {
        super(context, itemView);

        mSubTitleView = (AppCompatTextView) itemView.findViewById(R.id.sub_title_view);
        mTitleView = (AppCompatTextView) itemView.findViewById(R.id.title_view);
        mThumbnailView = (ImageView) itemView.findViewById(R.id.thumbnail_image_view);
    }


    @Override
    public void onBind(Ekisu item) {
        if (item == null) {
            return;
        }

        mSubTitleView.setText(item.getIndex());
        mTitleView.setText(item.getTitle());

        ImageLoader imageLoader = new ImageLoader(getContext(), R.color.material_blue_grey_800);
        imageLoader.loadImage(item.getThumbnail(), mThumbnailView);
    }
}
