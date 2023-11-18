package pgdp.pingunetwork;

public class Picture {
    private String location;
    private int width;
    private int height;
    private int[][] data;

    private Picture[] thumbnails;

    public Picture(String location, int[][] data) {
        this.location = location;
        this.data = data;

        this.height = data.length;

        if (data.length != 0) {
            this.width = data[0].length;
        }

        thumbnails = new Picture[0];
    }

    public int getHeight() {
        return this.height;
    }

    public String getLocation() {
        return this.location;
    }

    public int getWidth() {
        return this.width;
    }

    public int[][] getData() {
        return this.data;
    }

    public Picture[] getThumbnails() {
        return this.thumbnails;
    }

    public void setThumbnails(Picture[] thumbnails) {
        this.thumbnails = thumbnails;
    }
}
