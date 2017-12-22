package cn.luvletter.wallhalla;

/**
 * @author Zephyr Ji
 * @ Description: TODO
 * @ Date 2017/12/21
 */
public class WallHallaImg {
    private String id;
    private String imgId;
    private String imgSrc;
    private String imgHref;
    private String imgTags;
    private String imgSources;
    private String imgColor;
    private String imgWH;

    public String getImgId() {
        return imgId;
    }

    public WallHallaImg setImgId(String imgId) {
        this.imgId = imgId;
        return this;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public WallHallaImg setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
        return this;
    }

    public String getImgHref() {
        return imgHref;
    }

    public WallHallaImg setImgHref(String imgHref) {
        this.imgHref = imgHref;
        return this;
    }

    public String getId() {
        return id;
    }

    public WallHallaImg setId(String id) {
        this.id = id;
        return this;
    }

    public String getImgTags() {
        return imgTags;
    }

    public WallHallaImg setImgTags(String imgTags) {
        this.imgTags = imgTags;
        return this;
    }

    public String getImgSources() {
        return imgSources;
    }

    public WallHallaImg setImgSources(String imgSources) {
        this.imgSources = imgSources;
        return this;
    }

    public String getImgColor() {
        return imgColor;
    }

    public WallHallaImg setImgColor(String imgColor) {
        this.imgColor = imgColor;
        return this;
    }

    public String getImgWH() {
        return imgWH;
    }

    public WallHallaImg setImgWH(String imgWH) {
        this.imgWH = imgWH;
        return this;
    }

    @Override
    public String toString() {
        return "WallHallaImg{" +
                "id='" + id + '\'' +
                ", imgId='" + imgId + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", imgHref='" + imgHref + '\'' +
                ", imgTags='" + imgTags + '\'' +
                ", imgSources='" + imgSources + '\'' +
                ", imgColor='" + imgColor + '\'' +
                ", imgWH='" + imgWH + '\'' +
                '}';
    }
}
