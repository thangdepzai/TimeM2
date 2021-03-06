package hust.edu.vn.timem.Model;


import android.os.Parcel;
import android.os.Parcelable;

public class CourseModel implements Parcelable {

    private String uid;
    private String cid;
    private String cname;
    private String schoolYear;
    private String term;
    private float credit;
    private int startSection;
    private int endSection;
    private int startWeek;
    private int endWeek;
    private int dayOfWeek;
    private String classroom;

    public CourseModel() {
    }

    private String teacher;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getSchoolYear() {
        return schoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        this.schoolYear = schoolYear;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public float getCredit() {
        return credit;
    }

    public void setCredit(float credit) {
        this.credit = credit;
    }

    public int getStartSection() {
        return startSection;
    }

    public void setStartSection(int startSection) {
        this.startSection = startSection;
    }

    public int getEndSection() {
        return endSection;
    }

    public void setEndSection(int endSection) {
        this.endSection = endSection;
    }

    public int getStartWeek() {
        return startWeek;
    }

    public void setStartWeek(int startWeek) {
        this.startWeek = startWeek;
    }

    public int getEndWeek() {
        return endWeek;
    }

    public void setEndWeek(int endWeek) {
        this.endWeek = endWeek;
    }

    public int getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(int dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
        dest.writeString(cid);
        dest.writeString(cname);
        dest.writeString(schoolYear);
        dest.writeString(term);
        dest.writeFloat(credit);
        dest.writeInt(startSection);
        dest.writeInt(endSection);
        dest.writeInt(startWeek);
        dest.writeInt(endWeek);
        dest.writeInt(dayOfWeek);
        dest.writeString(classroom);
        dest.writeString(teacher);

    }
    public CourseModel(Parcel in) {
        uid = in.readString();
        cid = in.readString();
        cname = in.readString();
        schoolYear = in.readString();
        term = in.readString();
        credit = in.readFloat();
        startSection = in.readInt();
        endSection = in.readInt();
        startWeek = in.readInt();
        endWeek = in.readInt();
        dayOfWeek = in.readInt();
        classroom = in.readString();
        teacher = in.readString();
    }
    public static final Parcelable.Creator<CourseModel> CREATOR = new Parcelable.Creator<CourseModel>() {
        @Override
        public CourseModel createFromParcel(Parcel in) {
            return new CourseModel(in);
        }

        @Override
        public CourseModel[] newArray(int size) {
            return new CourseModel[size];
        }
    };

}

