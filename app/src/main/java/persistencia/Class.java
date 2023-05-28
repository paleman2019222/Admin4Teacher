package persistencia;

public class Class {

    int idClass;
    String className;
    int idUser;

    public Class() {
    }

    public Class(int idClass, String className, int idUser) {
        this.idClass = idClass;
        this.className = className;
        this.idUser = idUser;
    }

    public int getIdClass() {
        return idClass;
    }

    public void setIdClass(int idClass) {
        this.idClass = idClass;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }
}
