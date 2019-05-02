package hust.edu.vn.timem.Model;

public class UserModel {
    int id;
    String userName;
    String sdt;
    String mail;
    String passW;

    public UserModel(String userName, String sdt, String passW) {
        this.userName = userName;
        this.sdt = sdt;
        this.passW = passW;
    }

    public UserModel() {
    }

    public UserModel(int id, String userName, String sdt, String mail, String passW) {
        this.id = id;
        this.userName = userName;
        this.sdt = sdt;
        this.mail = mail;
        this.passW = passW;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public void setPassW(String passW) {
        this.passW = passW;
    }

    public UserModel(String userName, String sdt, String mail, String passW) {

        this.userName = userName;
        this.sdt = sdt;
        this.mail = mail;
        this.passW = passW;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getSdt() {
        return sdt;
    }

    public String getMail() {
        return mail;
    }

    public String getPassW() {
        return passW;
    }
}
