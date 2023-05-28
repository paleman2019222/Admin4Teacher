package persistencia;

public class Course {

    int idCourse;
    String courseName;
    String description;
    int idClass;

    public Course() {
    }

    public Course(int idCourse, String courseName, String description, int idClass) {
        this.idCourse = idCourse;
        this.courseName = courseName;
        this.description = description;
        this.idClass = idClass;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
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

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }
}
