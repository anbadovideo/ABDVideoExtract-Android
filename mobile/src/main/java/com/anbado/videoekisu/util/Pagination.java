package com.anbado.videoekisu.util;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author by SeunOh on 15. 4. 1..
 */
@Data
@Accessors(prefix = "m")
@NoArgsConstructor
public class Pagination {

    private int mPage = 1;
    private boolean mHasNext = false;
    private boolean mLoading = false;
    private boolean mReloading = false;

    private int mPastVisibleItems;
    private int mVisibleItemCount;
    private int mTotalItemCount;


    public boolean isPagination() {
        return (mVisibleItemCount + mPastVisibleItems) == mTotalItemCount;
    }
}
