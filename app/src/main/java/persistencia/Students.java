package persistencia;

public class Students {

    int idStudent;
    String carnet;
    String name;
    String lastname;
    String email;
    int idClass;

    public Students() {
    }

    public Students(int idStudent, String carnet, String name, String lastname, String email, int idClass) {
        this.idStudent = idStudent;
        this.carnet = carnet;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.idClass = idClass;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
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

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }
}
