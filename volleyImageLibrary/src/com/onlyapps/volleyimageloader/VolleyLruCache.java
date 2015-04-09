package com.onlyapps.volleyimageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.support.v4.util.LruCache;
import android.util.Log;

/**
 * Bitmap LruCache Class 
 */
public class VolleyLruCache extends LruCache<String, Bitmap> implements VolleyImageLoader.ImageCache {
    private static final String TAG = VolleyLruCache.class.getSimpleName();
    private static final boolean SUPPORTED_SAMPLING_BITMAP = true;
    private static final int MAX_CACHE_SIZE_DIVISION = 8;
    /**
     * 안드로이드 GL_MAX_TEXTURE_SIZE 값
     * 
     * 안드로이드의 ImageView에 출력할 수 있는 이미지 크기에 제한이 있는데, 이 제한은 장치마다 다르다.
     * 예를 들어, 넥서스7의 경우는 2048*2048 이내의 이미지를 출력할 수 있고, 갤럭시노트10.1의 경우
     * 4096*4096 이내의 이미지를 출력할 수 있다.
     */
    private final int mMaxTextureSize = 2048 * 2048;

    public VolleyLruCache(Context context) {
        super(getDefaultLruCacheSize());
    }
    
    private static int getDefaultLruCacheSize() {
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        final int cacheSize = maxMemory / MAX_CACHE_SIZE_DIVISION;
        return cacheSize;
    }

    private int calculateInSampleSize(Bitmap bitmap) {
        final int width = bitmap.getWidth();
        final int height = bitmap.getHeight();
        int inSampleSize = 1;
        
        if (height > mMaxTextureSize || width > mMaxTextureSize) {
            final int halfHeight = height;
            final int halfWidth = width;
            while ((halfHeight / inSampleSize) > mMaxTextureSize || (halfWidth / inSampleSize) > mMaxTextureSize) {
                inSampleSize += 1;
            }
        }

        return inSampleSize;
    }

    @Override
    public int sizeOf(String key, Bitmap value) {
        return value.getRowBytes() * value.getHeight() / 1024;
    }

    @Override
    public Bitmap getBitmap(String url) {
        return get(url);
    }

    @Override
    public void putBitmap(String url, Bitmap bitmap) {
        if (SUPPORTED_SAMPLING_BITMAP) {
            int sampleSize = calculateInSampleSize(bitmap);
            Log.d(TAG, "putBitmap() sampleSize : " + sampleSize);
            if (sampleSize > 1) {
                bitmap = ThumbnailUtils.extractThumbnail(bitmap, bitmap.getWidth() / sampleSize, bitmap.getHeight() / sampleSize);
            }
        }
        put(url, bitmap);
    }
}
