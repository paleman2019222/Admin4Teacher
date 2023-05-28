package persistencia;

import java.util.Date;

public class Activities {

    int idActivity;
    int idCourse;
    String title;
    String description;
    //Date initDate;
    Date finishDate;

    public Activities() {
    }

    public Activities(int idActivity, int idCourse, String title, String description, Date finishDate) {
        this.idActivity = idActivity;
        this.idCourse = idCourse;
        this.title = title;
        this.description = description;
        this.finishDate = finishDate;
    }

    public int getIdActivity() {
        return idActivity;
    }

    public void setIdActivity(int idActivity) {
        this.idActivity = idActivity;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }
}