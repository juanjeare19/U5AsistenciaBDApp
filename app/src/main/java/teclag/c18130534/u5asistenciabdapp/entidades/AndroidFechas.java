/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                             Clase AndroidFechas
:*
:*  Archivo     : AndroidFechas.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase que mapea los campos de nuestra tabla de fechas
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp.entidades;

public class AndroidFechas {

    private String statusid;
    private String nombre;
    private String status;

    public AndroidFechas(String statusid, String nombre, String status) {
        this.statusid = statusid;
        this.nombre = nombre;
        this.status = statusid;

    }

    public AndroidFechas(){

    }

    public String getStatusid() {
        return statusid;
    }

    public void setStatusid(String statusid) {
        this.statusid = statusid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
