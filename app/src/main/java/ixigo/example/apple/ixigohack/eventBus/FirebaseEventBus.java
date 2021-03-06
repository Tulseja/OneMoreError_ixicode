package ixigo.example.apple.ixigohack.eventBus;

import ixigo.example.apple.ixigohack.objects.FirebaseDataObject;

/**
 * Created by apple on 09/04/17.
 */

public class FirebaseEventBus {

    public static class OnFirebaseEventAdded {

        FirebaseDataObject data;

        public OnFirebaseEventAdded(FirebaseDataObject data) {
            this.data = data;
        }

        public FirebaseDataObject getData() {
            return data;
        }

        public void setData(FirebaseDataObject data) {
            this.data = data;
        }
    }

    public static class OnFirebaseEventChanged {

        FirebaseDataObject data;

        public OnFirebaseEventChanged(FirebaseDataObject data) {
            this.data = data;
        }

        public FirebaseDataObject getData() {
            return data;
        }

        public void setData(FirebaseDataObject data) {
            this.data = data;
        }
    }

    public static class OnFirebaseEventRemoved {

        FirebaseDataObject data;

        public OnFirebaseEventRemoved(FirebaseDataObject data) {
            this.data = data;
        }

        public FirebaseDataObject getData() {
            return data;
        }

        public void setData(FirebaseDataObject data) {
            this.data = data;
        }
    }
}
