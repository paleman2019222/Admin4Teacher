package persistencia;

public class Students {

    String idStudent;
    String carnet;
    String name;
    String lastname;
    String email;
    String idClass;

    public Students() {
    }

    public Students(String idStudent, String carnet, String name, String lastname, String email, String idClass) {
        this.idStudent = idStudent;
        this.carnet = carnet;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.idClass = idClass;
    }

    public String getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(String idStudent) {
        this.idStudent = idStudent;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdClass() {
        return idClass;
    }

    public void setIdClass(String idClass) {
        this.idClass = idClass;
    }
}
