package donkeybug.model;

public class WebcamDTO {
    private byte[] image;

    public WebcamDTO(byte[] content) {
        this.image = content;
    }

    public byte[] getImage() {
        return image;
    }
}
