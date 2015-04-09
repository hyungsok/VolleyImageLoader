package com.onlyapps.volleyimageloader.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;

/**
 * 스크린 관련 유틸 클래스 
 */
public class ScreenUtils {
    
    private static int sDisplayWidth;
    private static int sDisplayHeight;
    
    /**
     * init 
     * @param context
     */
    public static void init(Context context) {
        sDisplayWidth = context.getResources().getDisplayMetrics().widthPixels;
        sDisplayHeight = context.getResources().getDisplayMetrics().heightPixels;
    }
    
    /**
     * 단말 스크린 크기에 맞게 이미지 세팅 
     * @param imageView
     * @param bitmap
     */
    public static void fitToScreen(View view, Bitmap bitmap) {
        fitToScreen(view, bitmap, false);
    }
    
    /**
     * 단말 스크린 크기에 맞게 이미지 세팅 
     * @param imageView
     * @param bitmap
     * @param isAnimFadeIn 
     */
    public static void fitToScreen(View view, Bitmap bitmap, boolean isAnimFadeIn) {
        android.view.ViewGroup.LayoutParams param = view.getLayoutParams();
        if (null != param) {
            param.height = getScaleHeight(bitmap) > 0 ? getScaleHeight(bitmap) : getScreenHeight();
            view.setLayoutParams(param);
        }
        if (isAnimFadeIn) {
            view.setAnimation(AnimationUtils.loadAnimation(view.getContext(), android.R.anim.fade_in));
        }
    }

    /**
     * 실제 비트맵 크기를 단말 스크린 크기에 맞게 높이 크기 리턴함수 
     * @param bitmap
     * @return
     */
    public static int getScaleHeight(Bitmap bitmap) {
        if (bitmap == null) {
            return 0;
        }
        return getScaleHeight(bitmap.getWidth(), bitmap.getHeight());
    }
    
    /**
     * 실제 width, height 단말 스크린 크기에 맞게 높이 크기 리턴함수 
     * @param width
     * @param height
     * @return
     */
    public static int getScaleHeight(int width, int height) {
        return ((height * getScreenWidth()) / width);
    }
    
    /**
     * 현재 디스플레이 화면에 비례한 DP단위를 픽셀 크기로 반환합니다.
     *
     * @param DP 픽셀
     * @return 변환된 값 (pixel)
     */
    public static int getPixelFromDip(Context context, float dip) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, dm);
    }
    
    /**
     * 현재 디스플레이 화면에 비례한 픽셀단위를 DIP단위로 반환합니다.
     * 
     * @param px
     * @param context
     * @return 변환된 값 (dip)
     */
    public static int getDipFromPixel(Context context, int px) {
        float dns = context.getResources().getDisplayMetrics().density;
        int dip = (int) (px / dns);
        return dip;
    }
    
    /**
     * 인디게이터바 높이
     * 
     * @return
     */
    public static int getStatusbarHeight(Context context) {
        Rect CheckRect = new Rect();
        Window window = ((Activity) context).getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(CheckRect);
        int height = CheckRect.top;
        return height;
    }
    
    /**
     * 단말 가로 사이즈 
     * @return
     */
    public static int getScreenWidth() {
        return sDisplayWidth;
    }
    
    /**
     * 단말 세로 사이즈 
     * @return
     */
    public static int getScreenHeight() {
        return sDisplayHeight;
    }
}
