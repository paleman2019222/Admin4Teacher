package com.example.admin4teacher;

public class ListElement {
    public String color;
    public String grado;
    public String matertia;
    public String estudiantes;

    public ListElement(String color, String grado, String matertia, String estudiantes) {
        this.color = color;
        this.grado = grado;
        this.matertia = matertia;
        this.estudiantes = estudiantes;
    }

    public String getEstudiantes() {
        return estudiantes;
    }

    public void setEstudiantes(String estudiantes) {
        this.estudiantes = estudiantes;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public String getMatertia() {
        return matertia;
    }

    public void setMatertia(String matertia) {
        this.matertia = matertia;
    }
}
