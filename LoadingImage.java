import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Looper;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;

import android.os.Handler;

public class LoadingImage {

    public static void loadingWithFadeIn(Context context, String gifUrl, String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .asGif()
                .load(gifUrl)  // Load loading.gif file from assets
                .apply(requestOptions)
                .into(new CustomTarget<GifDrawable>() {
                    @Override
                    public void onResourceReady(@NonNull GifDrawable resource, @Nullable Transition<? super GifDrawable> transition) {
                        // When the gif is loaded it goes here
                        imageView.setImageDrawable(resource);

                        // Fade-in effect
                        resource.start();
                        resource.setLoopCount(GifDrawable.LOOP_FOREVER);

                        // Set alpha value using ViewPropertyAnimator
                        imageView.animate().alpha(1f).setDuration(1000).start();

                        // Load image from URL after 2 sec

                        Handler handler = new Handler(Looper.getMainLooper());
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadUrlImage(context, imageUrl, imageView);
                            }
                        }, 2000);

                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                        // Gif goes here when upload is cancelled.
                    }
                });
    }

    private static void loadUrlImage(Context context, String imageUrl, ImageView imageView) {
        RequestOptions requestOptions = new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(context)
                .load(imageUrl)
                .apply(requestOptions)
                .into(imageView);
    }
}
