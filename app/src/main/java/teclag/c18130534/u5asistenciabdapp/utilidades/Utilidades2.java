/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                             Clase Utilidades2
:*
:*  Archivo     : Utilidades.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase que contiene los campos de nuestra tabla topicos y su sentencia para crear
                  la tabla
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp.utilidades;


public class Utilidades2 {

    //Constantes campos tabla topicos
    public static final String TABLA_MATERIA2="topicos";
    public static final String CAMPO_ID2="id";
    public static final String CAMPO_NOMBRE2="nombre";
    public static final String CAMPO_MATERIA2="materia";
    public static final String CAMPO_PRESENTE2="presente";
    public static final String CAMPO_JUSTIFICADO2="justificado";
    public static final String CAMPO_TOTAL2="total";
    public static final String CAMPO_PORCENTAJE2="porcentaje";

    public static final String CREAR_TABLA_MATERIA2 ="CREATE TABLE " +
            ""+TABLA_MATERIA2+" ("+CAMPO_ID2+" INTEGER PRIMARY KEY, "+CAMPO_NOMBRE2+" TEXT, " +CAMPO_MATERIA2+
            " TEXT, "+CAMPO_PRESENTE2+" INTEGER, "+CAMPO_JUSTIFICADO2+" INTEGER, "+CAMPO_TOTAL2+" INTEGER,"
            +CAMPO_PORCENTAJE2+" FLOAT)";
}
