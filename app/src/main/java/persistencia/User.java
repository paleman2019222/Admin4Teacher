package persistencia;

public class User {

    int idUser;
    String name;
    String lastname;
    String email;
    String phone;
    String picture;

    public User(int id, String name, String lastname, String email, String phone, String picture){
        idUser = id;
        this.name = name;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
    }

    public User(){

    }

    public void setIdUser(int id){
        this.idUser = id;
    }

    public int getIdUser(){
        return idUser;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setLastname(String lastname){
        this.lastname = lastname;
    }

    public String getLastname(){
        return lastname;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public String getPhone(){
        return phone;
    }

    public void setPicture(String picture){
        this.picture = picture;
    }

    public String getPicture(){
        return picture;
    }

}