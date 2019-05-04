package hust.edu.vn.timem.Model;

public class NoteModel {
    public int id;
    public String time;
    public String title;
    public String mota;
    public int color;

    public NoteModel(int id, String time, String title, String mota, String urlImage) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.mota = mota;
        this.urlImage = urlImage;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String urlImage = null;

    public NoteModel(int id, String time, String title, String mota) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.mota = mota;
    }

    public NoteModel(int id, String time, String title, String mota, int color) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.mota = mota;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public NoteModel() {
    }
}
