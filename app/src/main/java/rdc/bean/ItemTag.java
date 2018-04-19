package rdc.bean;

/**
 * Created by Lin Yaotian on 2018/4/19.
 */

public class ItemTag {
    private String tag;
    private boolean isChecked;

    public ItemTag(){

    }

    public ItemTag(String tag,boolean isChecked){
        this.tag = tag;
        this.isChecked = isChecked;
    }

    @Override
    public String toString() {
        return "ItemTag{" +
                "tag='" + tag + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
