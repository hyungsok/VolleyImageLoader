package com.onlyapps.volleyimageloader;

/**
 * ______________________________________________________________________Copyright 2014 wemakeprice
 * Description : 이미지 캐쉬 작업 클래스 
 * Date : 2015. 1. 12.
 * Author : Hyungsok Lee
 * History : [2015. 1. 12.] 최초 소스 작성(Hyungsok Lee)
 *_________________________________________________________________________________________________
 */

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.LinkedHashMap;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.net.http.AndroidHttpClient;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;

import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpClientStack;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;
import com.onlyapps.volleyimageloader.VolleyImageLoader.ImageContainer;
import com.onlyapps.volleyimageloader.VolleyImageLoader.ImageListener;
import com.onlyapps.volleyimageloader.utils.ScreenUtils;

/**
 * Volley Image Manager 
 * 참고링크 : http://bWLog.lemberg.co.uk/volley-part-3-image-loader
 */
public class VolleyImageCacheManager {
    // WLog TAG
    private static final String TAG = VolleyImageCacheManager.class.getSimpleName();
    // Debug
    private static final boolean DEBUG = false;
    // Default maximum disk usage in bytes
    private static final int DEFAULT_DISK_USAGE_BYTES = 50 * 1024 * 1024;
    // Default cache folder name ( com.wemakeprice/cache/image/volley ) - 다른 이미지 캐쉬파일은 내부적으로 삭제하므로 다르게 폴더를 구성한다.
    private static final String DEFAULT_CACHE_DIR = "volley"; 
    // MAX Request Image Url 
    private static final int MAX_REQUEST_IMAGE_URL_LIST_SIZE = 15;
    
    static {
        com.android.volley.VolleyLog.DEBUG = VolleyImageCacheManager.DEBUG;
    }

    /**
     * Volley image loader 
     * > How to use Volley ImageLoader instance before you must init(Context) call.
     * 
     * VolleyImageView image = (VolleyImageView) convertView;
     * image.setImageUrl(Utils.checkImageUrl(imageUrl), mImageLoader);
     * image.setOnImageLoadingListener(new ImageLoadingListener() {
     *      @Override
     *      public void onComplete(ImageView view, Bitmap bitmap) {
     *      }
     *  });
     * 
     */
    private RequestQueue mRequestQueue;
    private VolleyImageLoader mImageLoader;
    
    private LinkedHashMap<String, ImageContainer> mRequestImagesMap = new LinkedHashMap<String, ImageContainer>();
    
    private static final class VolleyManagerHolder {
        private static final VolleyImageCacheManager sInstance = new VolleyImageCacheManager();
    }

    public static VolleyImageCacheManager getInstance() {
        return VolleyManagerHolder.sInstance;
    }

    /**
     * ImageLoader 리스너 클래스 
     */
    public static class VolleyImageListener implements ImageListener {
        private WeakReference<ImageView> imageViewRef;
        private String imageUrl;
        private boolean isFitScreen;
        private boolean isFade;
        
        public VolleyImageListener(ImageView imageView) {
            this(imageView, false, false);
        }
        
        public VolleyImageListener(ImageView imageView, boolean isFitScreen, boolean isFade) {
            this.imageViewRef = new WeakReference<ImageView>(imageView);
            this.isFitScreen = isFitScreen;
            this.isFade = isFade;
        }
        
        public VolleyImageListener(String imageUrl) {
            this.imageUrl = imageUrl;
        }
        
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d(TAG, "VolleyImageListener onErrorResponse() error : " + error);
            VolleyImageCacheManager.getInstance().removeRequestImageUrl(imageUrl);
        }

        @Override
        public void onResponse(ImageContainer response, boolean isImmediate) {
            final Bitmap bitmap = response.getBitmap();
            Log.d(TAG, "VolleyImageListener onResponse() bitmap : " + bitmap + ", isImmediate : " + isImmediate);
            if (imageViewRef != null && imageViewRef.get() != null) {
                ImageView view = imageViewRef.get();
                if (view != null) {
                    if (bitmap != null) {
                        view.setImageBitmap(bitmap);
                        if (isFitScreen) {
                            ScreenUtils.fitToScreen(view, bitmap, isFade);
                        }
                    }
                }
            }
            VolleyImageCacheManager.getInstance().removeRequestImageUrl(imageUrl);
        }
    }

    /**
     * method and now you have fully working Image loader with configurable memory and disk cache
     * 
     * @param context
     * @return
     */
    private static RequestQueue newDiskCacheRequestQueue(Context context) {
        // define cache folder
        File rootCache = context.getExternalCacheDir();
        if (rootCache == null) {
            Log.d(TAG, "Can't find External Cache Dir, switching to application specific cache directory");
            rootCache = context.getCacheDir();
        }

        File cacheDir = new File(rootCache, DEFAULT_CACHE_DIR);
        Log.d(TAG, "- Volley cache dir path : " + cacheDir.getPath());
        
        String userAgent = "volley/0";
        try {
            String packageName = context.getPackageName();
            PackageInfo info = context.getPackageManager().getPackageInfo(packageName, 0);
            userAgent = packageName + "/" + info.versionCode;
        } catch (NameNotFoundException e) {
        }

        HttpStack stack = null;
        if (stack == null) {
            if (Build.VERSION.SDK_INT >= 9) {
                stack = new HurlStack();
            } else {
                // Prior to Gingerbread, HttpUrlConnection was unreliable.
                // See: http://android-developers.blogspot.com/2011/09/androids-http-clients.html
                stack = new HttpClientStack(AndroidHttpClient.newInstance(userAgent));
            }
        }
        
        Network network = new BasicNetwork(stack);
        DiskBasedCache diskBasedCache = new DiskBasedCache(cacheDir, DEFAULT_DISK_USAGE_BYTES);
        RequestQueue queue = new RequestQueue(diskBasedCache, network);
        queue.start();
        return queue;
    }

    /**
     * ImageLoader Validation check
     */
    private void checkValidation() {
        if (mImageLoader == null) {
            throw new IllegalStateException("Volley ImageLoader instance is NULL! You must call init method!");
        }
    }
    // [private_method]============================[END]===========================[private_method]
    // [life_cycle_method]========================[START]=======================[life_cycle_method]
    // [life_cycle_method]=========================[END]========================[life_cycle_method]
    // [public_method]============================[START]===========================[public_method]
    /**
     * 반드시 Application 클래스 onCreate() 함수안에서 초기화 해줘야 되는 메소드
     * 
     * @param context
     */
    public void init(Context context) {
        // init width, height
        Log.d(TAG, "VolleyManager init() method call");
        mRequestQueue = newDiskCacheRequestQueue(context);
        mImageLoader = new VolleyImageLoader(mRequestQueue, new VolleyLruCache(context));
    }

    /**
     * 
     * @param imageUrl
     */
    public void removeRequestImageUrl(String imageUrl) {
        if (mRequestImagesMap.containsKey(imageUrl)) {
            mRequestImagesMap.remove(imageUrl);
        }
    }
    
    /**
     * 미리 이미지 로딩 처리 ( 메모리, 디스크 캐쉬미리하기위해서 사용 )
     * 
     * @param url
     */
    public void load(String url) {
        load(url, Config.RGB_565);
    }

    /**
     * 미리 이미지 로딩 처리 ( 메모리, 디스크 캐쉬미리하기위해서 사용 ) 
     * - 상시 MAX_REQUEST_IMAGE_URL_LIST_SIZE 만큼의 최신 요청을 유지하도록 한다.
     * 
     * @param url
     * @param config
     */
    public void load(String url, Config config) {
        checkValidation();

        if (!mRequestImagesMap.containsKey(url)) {
            Log.d(TAG, "loadImage() url : " + url);

            if (mRequestImagesMap.size() > MAX_REQUEST_IMAGE_URL_LIST_SIZE) {
                String firstKey = mRequestImagesMap.keySet().iterator().next();
                ImageContainer container = mRequestImagesMap.get(firstKey);
                container.cancelRequest();
                Log.d(TAG, "-cancelRequest : " + firstKey);
                removeRequestImageUrl(firstKey);
            }

            mRequestImagesMap.put(url, mImageLoader.get(url, config, new VolleyImageListener(url)));
        }
    }
    
    /**
     * 이미지 로딩 처리
     * 
     * @param url
     * @param image
     * @param listener
     * @return cached Bitmap
     */
    public Bitmap displayImage(String url, Config config, boolean isCache, ImageListener listener) {
        checkValidation();
        ImageContainer container = mImageLoader.get(url, config, isCache, listener);
        Bitmap cachedBitmap = null;
        if (container != null) {
            cachedBitmap = container.getBitmap();
        }
        return cachedBitmap;
    }
    
    /**
     * ImageLoader 인스턴스 반환
     * 
     * ex> (VolleyImageView).setImageUrl(url, ImageLoader);
     * 
     * @return
     */
    public VolleyImageLoader getImageLoader() {
        return mImageLoader;
    }
    
    /**
     * RequestQueue 인스턴스 반환
     * 
     * @return
     */
    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }
    
}
