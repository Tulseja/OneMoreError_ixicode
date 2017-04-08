package ixigo.example.apple.ixigohack.serverApi;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import ixigo.example.apple.ixigohack.R;
import ixigo.example.apple.ixigohack.utils.AndroidUtils;
import ixigo.example.apple.ixigohack.utils.ImageUtils;
import ixigo.example.apple.ixigohack.utils.UIUtils;

/**
 * Created by Ashish on 17/02/17.
 */

public class ImageRequestManager {

    public static void requestImage(Context context, ImageView imageView, String imageUrl) {
        if (context != null && imageView != null) {
            if (AndroidUtils.isEmpty(imageUrl)) {
                UIUtils.loadImage(imageView, R.drawable.placeholder);
            } else {
                Glide.with(context)
                        .load(imageUrl)
                        .crossFade()
                        .thumbnail(1)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .error(R.drawable.placeholder)
                        .into(imageView);
            }
        }
    }
}
