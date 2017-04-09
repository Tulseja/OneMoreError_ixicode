package ixigo.example.apple.ixigohack.utils;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import io.branch.indexing.BranchUniversalObject;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import io.branch.referral.util.LinkProperties;
import ixigo.example.apple.ixigohack.extras.AppConstants;

/**
 * Created by apple on 09/04/17.
 */

public class AppUtils {

    static AppUtils instance;

    public static AppUtils getInstance() {
        if (instance == null) {
            instance = new AppUtils();
        }
        return instance;
    }

    ProgressDialog progressDialog;

    public void doBranchShare(final Context context, String deviceId, String placeName, int days, String placeId) {
        BranchUniversalObject branchUniversalObject = new BranchUniversalObject()
                .setTitle("Ixigo travel plan share")
                .setContentDescription("Collabrate with me on this travel planning.")
//                .setContentImageUrl("https://example.com/mycontent-12345.png")
                .setContentIndexingMode(BranchUniversalObject.CONTENT_INDEX_MODE.PUBLIC)
                .addContentMetadata(AppConstants.BRANCH.BRANCH_ANDROID_ID, deviceId)
                .addContentMetadata(AppConstants.BRANCH.BRANCH_DAYS, Integer.toString(days))
                .addContentMetadata(AppConstants.BRANCH.BRANCH_PLACE_ID, placeId)
                .addContentMetadata(AppConstants.BRANCH.BRANCH_PLACE_NAME, placeName);

        progressDialog = UIUtils.showProgressDialog(context,
                null,
                "Generating link. Please wait.",
                true);

        LinkProperties linkProperties = new LinkProperties()
                .setChannel("branch")
                .setFeature("sharing");

        branchUniversalObject.generateShortUrl(context, linkProperties, new Branch.BranchLinkCreateListener() {
            @Override
            public void onLinkCreate(String url, BranchError error) {
                if (context != null) {
                    UIUtils.hideDialog(context, progressDialog);

                    if (error == null) {
                        DebugUtils.log("Branch link : " + url);

                        StringBuilder finalUrl = new StringBuilder("Click here to collaborate on our travel planning.");
                        finalUrl.append(url);

                        DebugUtils.log(finalUrl.toString());

                        Intent sendIntent = new Intent();
                        sendIntent.setAction(Intent.ACTION_SEND);
                        sendIntent.putExtra(Intent.EXTRA_TEXT, finalUrl.toString());
                        sendIntent.setType("text/plain");
                        context.startActivity(Intent.createChooser(sendIntent, "Share"));
                    } else {
                        Toast.makeText(context, "Unable to create share URL. Please check internet connection and try again.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
