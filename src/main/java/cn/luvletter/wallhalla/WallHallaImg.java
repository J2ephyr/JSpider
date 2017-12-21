package cn.luvletter.wallhalla;

/**
 * @author Zephyr Ji
 * @ Description: TODO
 * @ Date 2017/12/21
 */
public class WallHallaImg {
    private String imgId;
    private String imgSrc;
    private String imgHref;

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

    @Override
    public String toString() {
        return "WallHallaImg{" +
                "imgId='" + imgId + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", imgHref='" + imgHref + '\'' +
                '}';
    }
}
