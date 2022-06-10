/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                             Clase Utilidades3
:*
:*  Archivo     : Utilidades.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase que contiene los campos de nuestra tabla la2 y su sentencia para crear
                  la tabla
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp.utilidades;


public class Utilidades3 {

    //Constantes campos tabla la2
    public static final String TABLA_MATERIA="la2";
    public static final String CAMPO_ID="id";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_MATERIA="materia";
    public static final String CAMPO_PRESENTE="presente";
    public static final String CAMPO_JUSTIFICADO="justificado";
    public static final String CAMPO_TOTAL="total";
    public static final String CAMPO_PORCENTAJE="porcentaje";

    public static final String CREAR_TABLA_MATERIA ="CREATE TABLE " +
            ""+TABLA_MATERIA+" ("+CAMPO_ID+" INTEGER PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT, " +CAMPO_MATERIA+
            " TEXT, "+CAMPO_PRESENTE+" INTEGER, "+CAMPO_JUSTIFICADO+" INTEGER, "+CAMPO_TOTAL+" INTEGER,"
            +CAMPO_PORCENTAJE+" FLOAT)";
}
