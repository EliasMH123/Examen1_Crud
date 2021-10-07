package com.example.examen1moviles_elias.model;

public class Estudiante {
    public String uid;
    public String codigo;
    public String nombre_apellido;
    public String correo;
    public String dni;
    public String direccion;
    public String carrera_profes;

    public Estudiante() {
    }

    public Estudiante(String uid, String codigo, String nombre_apellido, String correo, String dni, String direccion, String carrera_profes) {
        this.uid = uid;
        this.codigo = codigo;
        this.nombre_apellido = nombre_apellido;
        this.correo = correo;
        this.dni = dni;
        this.direccion = direccion;
        this.carrera_profes = carrera_profes;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre_apellido() {
        return nombre_apellido;
    }

    public void setNombre_apellido(String nombre_apellido) {
        this.nombre_apellido = nombre_apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCarrera_profes() {
        return carrera_profes;
    }

    public void setCarrera_profes(String carrera_profes) {
        this.carrera_profes = carrera_profes;
    }
}
