package persistencia;

public class Course {

    String idCourse;
    String courseName;
    String description;
    String idClass;

    public Course() {
    }

    public Course(String idCourse, String courseName, String description, String idClass) {
        this.idCourse = idCourse;
        this.courseName = courseName;
        this.description = description;
        this.idClass = idClass;
    }

    public String getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(String idCourse) {
        this.idCourse = idCourse;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }
}
