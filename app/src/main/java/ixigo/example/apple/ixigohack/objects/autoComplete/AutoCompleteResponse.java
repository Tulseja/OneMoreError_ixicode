package ixigo.example.apple.ixigohack.objects.autoComplete;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by apple on 08/04/17.
 */

public class AutoCompleteResponse  implements Parcelable{

    @SerializedName("text")
    private String text;
    @SerializedName("url")
    private String url;
    @SerializedName("ct")
    private String ct;
    @SerializedName("address")
    private String address;
    @SerializedName("_id")
    private String _id;
    @SerializedName("cn")
    private String cn;
    @SerializedName("en")
    private boolean en;
    @SerializedName("rt")
    private String rt;
    @SerializedName("st")
    private String st;
    @SerializedName("co")
    private String co;
    @SerializedName("_oid")
    private int _oid;
    @SerializedName("eid")
    private String eid;
    @SerializedName("cid")
    private String cid;
    @SerializedName("useNLP")
    private boolean useNLP;
    @SerializedName("lat")
    private double lat;
    @SerializedName("lon")
    private double lon;
    @SerializedName("xid")
    private int xid;

    protected AutoCompleteResponse(Parcel in) {
        text = in.readString();
        url = in.readString();
        ct = in.readString();
        address = in.readString();
        _id = in.readString();
        cn = in.readString();
        en = in.readByte() != 0;
        rt = in.readString();
        st = in.readString();
        co = in.readString();
        _oid = in.readInt();
        eid = in.readString();
        cid = in.readString();
        useNLP = in.readByte() != 0;
        lat = in.readDouble();
        lon = in.readDouble();
        xid = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeString(url);
        dest.writeString(ct);
        dest.writeString(address);
        dest.writeString(_id);
        dest.writeString(cn);
        dest.writeByte((byte) (en ? 1 : 0));
        dest.writeString(rt);
        dest.writeString(st);
        dest.writeString(co);
        dest.writeInt(_oid);
        dest.writeString(eid);
        dest.writeString(cid);
        dest.writeByte((byte) (useNLP ? 1 : 0));
        dest.writeDouble(lat);
        dest.writeDouble(lon);
        dest.writeInt(xid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AutoCompleteResponse> CREATOR = new Creator<AutoCompleteResponse>() {
        @Override
        public AutoCompleteResponse createFromParcel(Parcel in) {
            return new AutoCompleteResponse(in);
        }

        @Override
        public AutoCompleteResponse[] newArray(int size) {
            return new AutoCompleteResponse[size];
        }
    };

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCt() {
        return ct;
    }

    public void setCt(String ct) {
        this.ct = ct;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public boolean getEn() {
        return en;
    }

    public void setEn(boolean en) {
        this.en = en;
    }

    public String getRt() {
        return rt;
    }

    public void setRt(String rt) {
        this.rt = rt;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public int get_oid() {
        return _oid;
    }

    public void set_oid(int _oid) {
        this._oid = _oid;
    }

    public String getEid() {
        return eid;
    }

    public void setEid(String eid) {
        this.eid = eid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public boolean getUseNLP() {
        return useNLP;
    }

    public void setUseNLP(boolean useNLP) {
        this.useNLP = useNLP;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public int getXid() {
        return xid;
    }

    public void setXid(int xid) {
        this.xid = xid;
    }
}
