package pro.watchnews.watchnewspro.model;

public class player_recyclerView_model {

    String textView_continentName, textView_channelName;
    int imageView_channelImage;

    public player_recyclerView_model(String textView_continentName, String textView_channelName, int imageView_channelImage) {
        this.textView_continentName = textView_continentName;
        this.textView_channelName = textView_channelName;
        this.imageView_channelImage = imageView_channelImage;
    }

    public String getTextView_continentName() {
        return textView_continentName;
    }

    public void setTextView_continentName(String textView_continentName) {
        this.textView_continentName = textView_continentName;
    }

    public String getTextView_channelName() {
        return textView_channelName;
    }

    public void setTextView_channelName(String textView_channelName) {
        this.textView_channelName = textView_channelName;
    }

    public int getImageView_channelImage() {
        return imageView_channelImage;
    }

    public void setImageView_channelImage(int imageView_channelImage) {
        this.imageView_channelImage = imageView_channelImage;
    }
}
