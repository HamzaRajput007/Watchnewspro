package pro.watchnews.watchnewspro.model;

public class releventModel {
    private String ch_name;
    private String ch_image;
    private String stream_url;
    private String country_name;
    private String decoded_stream;
    private String category_name;
    private String channel_desc;
    private  String id;

    public String getChannel_name() {
        return ch_name;
    }

    public void setChannel_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public String getCh_image() {
        return ch_image;
    }

    public void setCh_image(String ch_image) {
        this.ch_image = ch_image;
    }

    public String getStream_url() {
        return stream_url;
    }

    public void setStream_url(String stream_url) {
        this.stream_url = stream_url;
    }

    public String getDecoded_stream() {
        return decoded_stream;
    }

    public void setDecoded_stream(String decoded_stream) {
        this.decoded_stream = decoded_stream;
    }

    public String getChannel_id() {
        return id;
    }

    public void setChannel_id(String id) {
        this.id = id;
    }

    public String getChannel_desc() {
        return channel_desc;
    }

    public void setChannel_desc(String id) {
        this.id = channel_desc;
    }


    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }


    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }


}
