/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                    Clase que hace la conexion con SQLite
:*
:*  Archivo     : ConexionSQLiteHelper.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase que hace la conexion con SQLite
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades2;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades3;
import teclag.c18130534.u5asistenciabdapp.utilidades.UtilidadesFecha;

//--------------------------------------------------------------------------------------------------
public class ConexionSQLiteHelper extends SQLiteOpenHelper {

    //--------------------------------------------------------------------------------------------------
    public static final int DATABASE_VERSION = 2;

    public ConexionSQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, 2);
    }

    //--------------------------------------------------------------------------------------------------
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_MATERIA);
        db.execSQL(Utilidades2.CREAR_TABLA_MATERIA2);
        db.execSQL(Utilidades3.CREAR_TABLA_MATERIA);
        db.execSQL(UtilidadesFecha.CREAR_TABLA_MATERIA);
        db.execSQL(UtilidadesFecha.CREAR_TABLA_MATERIA2);
        db.execSQL(UtilidadesFecha.CREAR_TABLA_MATERIA3);
    }

    //--------------------------------------------------------------------------------------------------
    @Override
    public void onUpgrade(SQLiteDatabase db, int versionAntigua, int versionNueva) {
        db.execSQL("DROP TABLE IF EXISTS " +Utilidades.TABLA_MATERIA);
        db.execSQL("DROP TABLE IF EXISTS " +Utilidades2.TABLA_MATERIA2);
        db.execSQL("DROP TABLE IF EXISTS " +Utilidades3.TABLA_MATERIA);
        db.execSQL("DROP TABLE IF EXISTS " +UtilidadesFecha.TABLA_MATERIA);
        db.execSQL("DROP TABLE IF EXISTS " +UtilidadesFecha.TABLA_MATERIA2);
        db.execSQL("DROP TABLE IF EXISTS " +UtilidadesFecha.TABLA_MATERIA3);
        onCreate(db);
    }
}
