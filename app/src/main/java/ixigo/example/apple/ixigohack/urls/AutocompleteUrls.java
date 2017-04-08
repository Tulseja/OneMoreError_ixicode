package ixigo.example.apple.ixigohack.urls;

/**
 * Created by apple on 08/04/17.
 */

public class AutocompleteUrls extends AppUrls {

    public static String getAutoCompleteUrl(String query) {
        query = query.replaceAll(" ", "%20");
        return BASE_URL + "action/content/zeus/autocomplete?searchFor=tpAutoComplete&neCategories=City&query=" + query;
    }
}
