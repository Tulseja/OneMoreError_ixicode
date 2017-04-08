package ixigo.example.apple.ixigohack.urls;

/**
 * Created by apple on 08/04/17.
 */

public class TrendingAppUrls extends AppUrls {

    public static String getTrendingPlacesUrl() {
        return BASE_URL + "api/v2/widgets/brand/inspire?product=1&apiKey=ixicode!2$";
    }
}
