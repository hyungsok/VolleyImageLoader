# Introduction to VolleyImageLoader Library
- How to use Volley ImageLoader instance before you must ApiVolleyImage.init(Context) call.

> Add More API than NetworkImageView 
```java
VolleyImageView image = new VolleyImageView(Context)
image.setBitmapConfig(Config.ARGB_8888);
image.setFitToScreen(true);
image.setImageUrl(Utils.checkImageUrl(imageUrl), mImageLoader);
image.setOnImageLoadingListener(new ImageLoadingListener() {
    @Override
      public void onComplete(ImageView view, Bitmap bitmap) {
      }
});
```

> Simple API (Builder Patten)
```java
new ApiVolleyImage(url).view(ImageView).isFitScreen(true).isFade(false).displayImage();
```
