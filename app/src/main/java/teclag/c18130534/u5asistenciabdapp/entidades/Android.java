/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                             Clase Android
:*
:*  Archivo     : Android.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase que mapea los campos de nuestra tabla de alumnos
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp.entidades;

import java.io.Serializable;


public class Android implements  Serializable{

    private Integer id;
    private String nombre;
    private String materia;
    private Integer presente;
    private Integer justificado;
    private Integer total;
    private Integer porcentaje;

    public Android(Integer id, String nombre, String materia, Integer presente, Integer justificado
                   , Integer total, Integer porcentaje) {
        this.id = id;
        this.nombre = nombre;
        this.materia = materia;
        this.presente = presente;
        this.justificado = justificado;
        this.total = total;
        this.porcentaje = porcentaje;
    }

    public Android(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public Integer getPresente() {
        return presente;
    }

    public void setPresente(Integer presente) {
        this.presente = presente;
    }

    public Integer getJustificado() {
        return justificado;
    }

    public void setJustificado(Integer justificado) {
        this.justificado = justificado;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(Integer porcentaje) {
        this.porcentaje = porcentaje;
    }
}
