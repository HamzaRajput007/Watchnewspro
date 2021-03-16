package pro.watchnews.watchnewspro.comment_section;

public class comment_item_model {

    int imageView_avatar;
    String textView_name,textView_comment;

    public comment_item_model(int imageView_avatar, String textView_name, String textView_comment) {
        this.imageView_avatar = imageView_avatar;
        this.textView_name = textView_name;
        this.textView_comment = textView_comment;
    }

    public int getImageView_avatar() {
        return imageView_avatar;
    }

    public void setImageView_avatar(int imageView_avatar) {
        this.imageView_avatar = imageView_avatar;
    }

    public String getTextView_name() {
        return textView_name;
    }

    public void setTextView_name(String textView_name) {
        this.textView_name = textView_name;
    }

    public String getTextView_comment() {
        return textView_comment;
    }

    public void setTextView_comment(String textView_comment) {
        this.textView_comment = textView_comment;
    }
}
