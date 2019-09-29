package uitest.star.com.uiset.demo.view.emoji.entity;

public class EmojiBean {

    public String name;//中文u
    public String enname;
    public int id = -1;//默认为-1
    public int resource = -1;
    public String url;
    public int type;

    /**
     * 获取Id
     *
     * @return
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResource(int resourceId) {
        this.resource = resourceId;
    }

    public int getResouce() {
        return resource;

    }

    public String getUrl() {
        return this.url;
    }
}
