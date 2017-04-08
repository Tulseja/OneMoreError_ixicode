package ixigo.example.apple.ixigohack.binding;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ixigo.example.apple.ixigohack.serverApi.ImageRequestManager;

/**
 * Created by Ashish on 19/02/17.
 */

public class ImageBindingUtils {

    @BindingAdapter({"bind:loadSmallImage"})
    public static void loadSmallImage(ImageView imageView, String imageUrl) {
        ImageRequestManager.requestImage(imageView.getContext(), imageView, imageUrl);
    }
}
