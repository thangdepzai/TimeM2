package hust.edu.vn.timem.Model;

import com.github.tlaabs.timetableview.Schedule;

import java.util.List;

public class TimeTableModel {
    int id;
    String subject;
    String classRoom;
    String professor;
    List<Schedule> listTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TimeTableModel(int id, String subject, String classRoom, String professor, List<Schedule> listTime) {
        this.id = id;
        this.subject = subject;

        this.classRoom = classRoom;
        this.professor = professor;
        this.listTime = listTime;
    }

    public TimeTableModel(String subject, String classRoom, String professor, List<Schedule> listTime) {
        this.subject = subject;
        this.classRoom = classRoom;
        this.professor = professor;
        this.listTime = listTime;
    }

    public TimeTableModel(int id, String subject, String classRoom, String professor) {
        this.id = id;
        this.subject = subject;
        this.classRoom = classRoom;
        this.professor = professor;
    }

    public TimeTableModel(String subject, String classRoom, String professor) {
        this.subject = subject;
        this.classRoom = classRoom;
        this.professor = professor;
    }

    public TimeTableModel() {
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setClassRoom(String classRoom) {
        this.classRoom = classRoom;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public void setListTime(List<Schedule> listTime) {
        this.listTime = listTime;
    }
    public void addTime(Schedule Time) {
       listTime.add(Time);
    }

    public String getSubject() {

        return subject;
    }

    public String getClassRoom() {
        return classRoom;
    }

    public String getProfessor() {
        return professor;
    }

    public List<Schedule> getListTime() {
        return listTime;
    }
}
