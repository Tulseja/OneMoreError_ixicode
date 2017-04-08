package ixigo.example.apple.ixigohack.urls;

/**
 * Created by apple on 08/04/17.
 */

public class PlannerAppUrls extends AppUrls {

    public static String getInterestsUrl(String placeId, int skip, int limit) {
        return BASE_URL + "api/v3/namedentities/city/"
                + placeId + "/categories?apiKey=ixicode!2$&type=Places To Visit,Hotel,Things To Do&skip="
                + skip + "&limit=" + limit;
    }
}
