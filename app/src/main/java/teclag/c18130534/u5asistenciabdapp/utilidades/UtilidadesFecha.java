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
:*  Archivo     : UtilidadesFecha.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase que contiene los campos de nuestras tablas de las fechas y sus sentencias
                  para crear las tablas
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp.utilidades;

public class UtilidadesFecha {

    //Constantes campos tablas fechas
    public static final String TABLA_MATERIA="androidfecha";
    public static final String TABLA_MATERIA2="androidfechatopicos";
    public static final String TABLA_MATERIA3="automatasfecha";

    public static final String CAMPO_ID="statusid";
    public static final String CAMPO_NOMBRE="nombre";
    public static final String CAMPO_STATUS="status";

    public static final String CAMPO_ID2="statusid";
    public static final String CAMPO_NOMBRE2="nombre";
    public static final String CAMPO_STATUS2="status";

    public static final String CAMPO_ID3="statusid";
    public static final String CAMPO_NOMBRE3="nombre";
    public static final String CAMPO_STATUS3="status";

    public static final String CREAR_TABLA_MATERIA ="CREATE TABLE " +
            ""+TABLA_MATERIA+" ("+CAMPO_ID+" TEXT PRIMARY KEY, "+CAMPO_NOMBRE+" TEXT, "+CAMPO_STATUS+" TEXT)";

    public static final String CREAR_TABLA_MATERIA2 ="CREATE TABLE " +
            ""+TABLA_MATERIA2+" ("+CAMPO_ID2+" TEXT PRIMARY KEY, "+CAMPO_NOMBRE2+" TEXT, "+CAMPO_STATUS2+" TEXT)";

    public static final String CREAR_TABLA_MATERIA3 ="CREATE TABLE " +
            ""+TABLA_MATERIA3+" ("+CAMPO_ID3+" TEXT PRIMARY KEY, "+CAMPO_NOMBRE3+" TEXT, "+CAMPO_STATUS3+" TEXT)";

    public static final String COUNT_CONSULTA = "SELECT COUNT"+"("+UtilidadesFecha.CAMPO_STATUS+") "+"FROM "
            +UtilidadesFecha.TABLA_MATERIA+" WHERE " +UtilidadesFecha.CAMPO_ID+" LIKE "+" '%18130534%'";

    public static final String UPDATE_CAMPO = "UPDATE "+Utilidades.TABLA_MATERIA+" SET "+Utilidades.CAMPO_PRESENTE+"="
            +COUNT_CONSULTA+" WHERE "+Utilidades.CAMPO_ID+"="+"18130534";
}
