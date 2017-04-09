package ixigo.example.apple.ixigohack.eventBus;

/**
 * Created by apple on 09/04/17.
 */

public class HomeEventBus {

    public static class TrendingItemClick {
        String placeId;
        String placeName;

        public TrendingItemClick(String placeId, String placeName) {
            this.placeId = placeId;
            this.placeName = placeName;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }
    }
}
