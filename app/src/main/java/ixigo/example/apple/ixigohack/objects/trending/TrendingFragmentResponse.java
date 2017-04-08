package ixigo.example.apple.ixigohack.objects.trending;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by apple on 08/04/17.
 */

public class TrendingFragmentResponse {

    @SerializedName("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Flight {
        @SerializedName("image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("countryName")
        private String countryName;
        @SerializedName("url")
        private String url;
        @SerializedName("data")
        private String data;
        @SerializedName("text")
        private String text;
        @SerializedName("type")
        private String type;
        @SerializedName("cityName")
        private String cityName;
        @SerializedName("stateName")
        private String stateName;
        @SerializedName("price")
        private double price;
        @SerializedName("currency")
        private String currency;
        @SerializedName("cityId")
        private String cityId;
        @SerializedName("destinationCategories")
        private List<String> destinationCategories;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public List<String> getDestinationCategories() {
            return destinationCategories;
        }

        public void setDestinationCategories(List<String> destinationCategories) {
            this.destinationCategories = destinationCategories;
        }
    }

    public static class Budget_flight {
        @SerializedName("image")
        private String image;
        @SerializedName("name")
        private String name;
        @SerializedName("countryName")
        private String countryName;
        @SerializedName("url")
        private String url;
        @SerializedName("data")
        private String data;
        @SerializedName("text")
        private String text;
        @SerializedName("type")
        private String type;
        @SerializedName("cityName")
        private String cityName;
        @SerializedName("stateName")
        private String stateName;
        @SerializedName("price")
        private double price;
        @SerializedName("currency")
        private String currency;
        @SerializedName("cityId")
        private String cityId;
        @SerializedName("destinationCategories")
        private List<String> destinationCategories;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCountryName() {
            return countryName;
        }

        public void setCountryName(String countryName) {
            this.countryName = countryName;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public List<String> getDestinationCategories() {
            return destinationCategories;
        }

        public void setDestinationCategories(List<String> destinationCategories) {
            this.destinationCategories = destinationCategories;
        }
    }

    public static class Data {
        @SerializedName("flight")
        private List<Flight> flight;
        @SerializedName("budget_flight")
        private List<Budget_flight> budget_flight;

        public List<Flight> getFlight() {
            return flight;
        }

        public void setFlight(List<Flight> flight) {
            this.flight = flight;
        }

        public List<Budget_flight> getBudget_flight() {
            return budget_flight;
        }

        public void setBudget_flight(List<Budget_flight> budget_flight) {
            this.budget_flight = budget_flight;
        }
    }
}
