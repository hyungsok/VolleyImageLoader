package com.onlyapps.volleyimageloader;

import android.content.Context;
import android.graphics.Bitmap.Config;
import android.widget.ImageView;

import com.onlyapps.volleyimageloader.VolleyImageLoader.ImageListener;
import com.onlyapps.volleyimageloader.utils.ScreenUtils;

/**
 * Volley 이미지 로더 API 클래스 
 */
public class ApiVolleyImage {
    protected final String mRequestUrl;
    protected Config mConfig = Config.RGB_565;
    protected boolean mIsCache = true;
    protected boolean mIsFitScreen = false;
    protected boolean mIsFade = false;
    protected ImageView mImageView;
    protected int mDefaultImage = -1;
    private ImageListener mListener;

    public ApiVolleyImage(String url) {
        this.mRequestUrl = url;
    }

    /**
     * Volley Image cache init
     * @param context
     */
    public static void init(Context context) {
        VolleyImageCacheManager.getInstance().init(context);
        ScreenUtils.init(context);
    }
    
    /**
     * Volley Image load
     * @param url
     */
    public static void load(String url) {
        VolleyImageCacheManager.getInstance().load(url);
    }
    
    /**
     * 
     * @param url
     * @param config
     */
    public static void load(String url, Config config) {
        VolleyImageCacheManager.getInstance().load(url, config);
    }
    
    /**
     * Volley display Image
     */
    public void displayImage() {
        if (mListener == null && mImageView != null) {
            mImageView.setImageResource(mDefaultImage > 0 ? mDefaultImage : android.R.color.transparent);
            mListener = new VolleyImageCacheManager.VolleyImageListener(mImageView, mIsFitScreen, mIsFade);
        }
        VolleyImageCacheManager.getInstance().displayImage(mRequestUrl, mConfig, mIsCache, mListener);
    }
    
    // [public_method]=============================[END]============================[public_method]
    // [get/set]==================================[START]=================================[get/set]
    /**
     * 
     * @param config
     * @return
     */
    public ApiVolleyImage config(Config config) {
        this.mConfig = config;
        return this;
    }
    
    /**
     * 
     * @param isCache
     * @return
     */
    public ApiVolleyImage isCache(boolean isCache) {
        this.mIsCache = isCache;
        return this;
    }
    
    /**
     * 
     * @param isCache
     * @return
     */
    public ApiVolleyImage isFitScreen(boolean isFitScreen) {
        this.mIsFitScreen = isFitScreen;
        return this;
    }
    
    /**
     * 
     * @param isCache
     * @return
     */
    public ApiVolleyImage isFade(boolean isFade) {
        this.mIsFade = isFade;
        return this;
    }
    
    /**
     * 
     * @param isCache
     * @return
     */
    public ApiVolleyImage view(ImageView view) {
        this.mImageView = view;
        return this;
    }
    
    /**
     * 
     * @param resourceId
     * @return
     */
    public ApiVolleyImage defaultImage(int defaultImage) {
        this.mDefaultImage = defaultImage;
        return this;
    }
    /**
     * 
     * @param isCache
     * @return
     */
    public ApiVolleyImage listener(ImageListener listener) {
        this.mListener = listener;
        return this;
    }
}
