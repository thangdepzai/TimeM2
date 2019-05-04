package hust.edu.vn.timem.Model;

public class NoteModel {
    public int id;
    public String time;
    public String title;
    public String mota;

    public NoteModel(int id, String time, String title, String mota) {
        this.id = id;
        this.time = time;
        this.title = title;
        this.mota = mota;
    }

    public NoteModel() {
    }
}
