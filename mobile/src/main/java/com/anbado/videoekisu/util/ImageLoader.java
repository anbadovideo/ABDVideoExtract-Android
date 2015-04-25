package com.anbado.videoekisu.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;
import com.bumptech.glide.request.RequestListener;

import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author by seun on 2014. 10. 3..
 */
public final class ImageLoader {
    private static final ModelCache<String, GlideUrl> URL_CACHE = new ModelCache<>(500);

    private final RequestManager.ImageModelRequest<String> mGlideModelRequest;

    private int mPlaceHolderResId = -1;

    public ImageLoader(Context context) {
        VariableWidthImageLoader imageLoader = new VariableWidthImageLoader(context);
        mGlideModelRequest = Glide.with(context).using(imageLoader);
    }

    public ImageLoader(Context context, int placeHolderResId) {
        this(context);
        mPlaceHolderResId = placeHolderResId;
    }

    /**
     * Load an image from a url into an ImageView using the default placeholder
     * drawable if available.
     *
     * @param url             The web URL of an image.
     * @param imageView       The target ImageView to load the image into.
     * @param requestListener A listener to monitor the request result.
     */
    public void loadImage(String url, ImageView imageView,
                          RequestListener<String, Bitmap> requestListener) {
        loadImage(url, imageView, requestListener, null, false);
    }

    /**
     * Load an image from a url into an ImageView using the given placeholder drawable.
     *
     * @param url                 The web URL of an image.
     * @param imageView           The target ImageView to load the image into.
     * @param requestListener     A listener to monitor the request result.
     * @param placeholderOverride A placeholder to use in place of the default placeholder.
     */
    public void loadImage(String url, ImageView imageView,
                          RequestListener<String, Bitmap> requestListener,
                          Drawable placeholderOverride) {
        loadImage(url, imageView, requestListener, placeholderOverride, false /*crop*/);
    }


    /**
     * Load an image from a url into an ImageView using the default placeholder
     * drawable if available.
     *
     * @param url                 The web URL of an image.
     * @param imageView           The target ImageView to load the image into.
     * @param requestListener     A listener to monitor the request result.
     * @param placeholderOverride A drawable to use as a placeholder for this specific image.
     *                            If this parameter is present, {@link #mPlaceHolderResId}
     *                            if ignored for this request.
     */
    public void loadImage(String url, ImageView imageView,
                          RequestListener<String, Bitmap> requestListener,
                          Drawable placeholderOverride, boolean crop) {
        BitmapRequestBuilder request = beginImageLoad(url, requestListener)
                .animate(android.R.anim.fade_in);

        if (crop) {
            request.centerCrop();
        }

        if (placeholderOverride != null) {
            request.placeholder(placeholderOverride);
        } else if (mPlaceHolderResId != -1) {
            request.placeholder(mPlaceHolderResId);
        }
        request.into(imageView);
    }

    public BitmapRequestBuilder beginImageLoad(String url,
                                               final RequestListener<String, Bitmap>
                                                       requestListener) {
        return mGlideModelRequest
                .load(url)

                .asBitmap() // don't allow animated GIFs
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(requestListener)
                ;
    }


    /**
     * Load an image from a url into the given image view using the default placeholder if
     * available.
     *
     * @param url       The web URL of an image.
     * @param imageView The target ImageView to load the image into.
     */
    public void loadImage(String url, ImageView imageView) {
        loadImage(url, imageView, false /*crop*/);
    }

    /**
     * Load an image from a url into an ImageView using the default placeholder
     * drawable if available.
     *
     * @param url       The web URL of an image.
     * @param imageView The target ImageView to load the image into.
     * @param crop      True to apply a center crop to the image.
     */
    public void loadImage(String url, ImageView imageView, boolean crop) {
        loadImage(url, imageView, null, null, crop);
    }


    private static class VariableWidthImageLoader extends BaseGlideUrlLoader<String> {
        private static final Pattern PATTERN = Pattern.compile("__w-((?:-?\\d+)+)__");

        public VariableWidthImageLoader(Context context) {
            super(context, URL_CACHE);
        }


        /**
         * If the URL contains a special variable width indicator (eg "__w-200-400-800__")
         * we get the buckets from the URL (200, 400 and 800 in the example) and replace
         * the URL with the best bucket for the requested width (the bucket immediately
         * larger than the requested width).
         */
        @Override
        protected String getUrl(String model, int width, int height) {
            Matcher m = PATTERN.matcher(model);
            int bestBucket = 0;
            if (m.find()) {
                String[] found = m.group(1).split("-");
                for (String bucketStr : found) {
                    bestBucket = Integer.parseInt(bucketStr);
                    if (bestBucket >= width) {
                        // the best bucket is the first immediately bigger than the requested width
                        break;
                    }
                }
                if (bestBucket > 0) {
                    model = m.replaceFirst("w" + bestBucket);
                }
            }
            return model;
        }
    }

}
