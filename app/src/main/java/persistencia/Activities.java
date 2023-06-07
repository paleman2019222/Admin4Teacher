package persistencia;

import java.util.Date;

public class Activities {

    String idActivity;
    String idCourse;
    String title;
    String description;
    //Date initDate;
    Date finishDate;

    public Activities() {
    }

    public String getIdActivity() {return idActivity;}

    public void setIdActivity(String idActivity) {this.idActivity = idActivity;}

    public String getIdCourse() {return idCourse;}

    public void setIdCourse(String idCourse) {this.idCourse = idCourse;}

    public String getTitle() {return title;}

    public void setTitle(String title) {this.title = title;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}

    public Date getFinishDate() {return finishDate;}

    public void setFinishDate(Date finishDate) {this.finishDate = finishDate;}
}