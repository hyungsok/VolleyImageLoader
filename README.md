# VolleyImageLoader
Introduction to Volley image Loader

Volley Image loader 

How to use Volley ImageLoader instance before you must init(Context) call.
 - ApiVolleyImage.init(Context);

ex>

VolleyImageView image = (VolleyImageView) convertView;
image.setImageUrl(Utils.checkImageUrl(imageUrl), mImageLoader);
image.setOnImageLoadingListener(new ImageLoadingListener() {
    @Override
      public void onComplete(ImageView view, Bitmap bitmap) {
      }
});

new ApiVolleyImage(url).view(ImageView).isFitScreen(true).isFade(false).displayImage();
