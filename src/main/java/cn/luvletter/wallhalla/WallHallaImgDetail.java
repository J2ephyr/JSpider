package cn.luvletter.wallhalla;

/**
 * @author Zephyr Ji
 * @ Description: TODO
 * @ Date 2017/12/23
 */
public class WallHallaImgDetail {
    private String imgId;
    private String imgDetailId;
    private String imgSize;

    public String getImgId() {
        return imgId;
    }

    public WallHallaImgDetail setImgId(String imgId) {
        this.imgId = imgId;
        return this;
    }

    public String getImgDetailId() {
        return imgDetailId;
    }

    public WallHallaImgDetail setImgDetailId(String imgDetailId) {
        this.imgDetailId = imgDetailId;
        return this;
    }

    public String getImgSize() {
        return imgSize;
    }

    public WallHallaImgDetail setImgSize(String imgSize) {
        this.imgSize = imgSize;
        return this;
    }

    @Override
    public String toString() {
        return "WallHallaImgDetail{" +
                "imgId='" + imgId + '\'' +
                ", imgDetailId='" + imgDetailId + '\'' +
                ", imgSize='" + imgSize + '\'' +
                '}';
    }
}
