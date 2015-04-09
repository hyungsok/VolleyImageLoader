# Introduction to VolleyImageLoader Library
- How to use Volley API before you must ApiVolleyImage.init(Context) call.

> Add More API than NetworkImageView 
 - setBitmapConfig, setFitToScreen
```java
VolleyImageView image = new VolleyImageView(Context)
image.setBitmapConfig(Config.ARGB_8888);
image.setFitToScreen(true);
image.setImageUrl(Utils.checkImageUrl(imageUrl), VolleyImageLoader);
image.setOnImageLoadingListener(new ImageLoadingListener() {
    @Override
      public void onComplete(ImageView view, Bitmap bitmap) {
      }
});
```

> Simple Volley Image API (Java Builder Patten)
 - set : Bitmap.Config, ImageListener, isCache, isFitScreen, isFade, 
```java
new ApiVolleyImage((String)url).view((ImageView)view)
   .isCache(true).isFitScreen(true).isFade(false).displayImage();
```
