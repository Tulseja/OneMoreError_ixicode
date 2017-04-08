package ixigo.example.apple.ixigohack.serverApi;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.makeramen.roundedimageview.RoundedImageView;

import de.hdodenhof.circleimageview.CircleImageView;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;

/**
 * Created by Ashish on 17/02/17.
 */

public class ImageRequestManager {

    public static void requestImage(Context context, ImageView imageView, String imageUrl) {
        if (context != null && imageView != null) {
            if (AndroidUtils.isEmpty(imageUrl)) {
                // empty image
            } else {
                if (imageView instanceof RoundedImageView || imageView instanceof CircleImageView) {
                    Glide.with(context)
                            .load(imageUrl)
                            .thumbnail(0.1f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .dontAnimate()
                            .into(imageView);
                } else {
                    Glide.with(context)
                            .load(imageUrl)
                            .thumbnail(0.1f)
                            .crossFade()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(imageView);
                }
            }
        }
    }
}
