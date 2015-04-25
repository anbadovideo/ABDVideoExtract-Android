package com.anbado.videoekisu.app;

import android.app.Fragment;
import android.content.res.TypedArray;

import com.anbado.videoekisu.R;

/**
 * @author by SeunOh on 15. 4. 25..
 */
public class BaseFragment extends Fragment {


    public final int getColorPrimary() {
        TypedArray typedArray = getActivity().getTheme()
                .obtainStyledAttributes(new int[]{R.attr.colorPrimary});

        int colorPrimary = typedArray.getColor(0, 0);

        typedArray.recycle();

        return colorPrimary;
    }
}
