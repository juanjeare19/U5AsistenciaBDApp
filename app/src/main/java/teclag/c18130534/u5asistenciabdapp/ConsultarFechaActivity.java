/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                    Clase para consultar la fecha del activity
:*
:*  Archivo     : ConsultarFechaActivity.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase para consultar la fecha del activity
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades;
import teclag.c18130534.u5asistenciabdapp.utilidades.UtilidadesFecha;

//-------------------------------------------------------------------------------------------------
public class ConsultarFechaActivity extends AppCompatActivity {

    EditText campoId, campoNombre, campoStatus;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_fecha);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_alumnos1", null, 1);

        campoId = (EditText) findViewById(R.id.campoStatusId);
        campoNombre = (EditText) findViewById(R.id.nombreId);
        campoStatus = (EditText) findViewById(R.id.campoStatus);

    }

    //-------------------------------------------------------------------------------------------------
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnConsultar:
                consultarSql();
                break;
            case R.id.btnActualizar:
                actualizarAlumno();
                break;
            case R.id.btnEliminar:
                eliminarAlumno();
                break;
        }

    }

    //-------------------------------------------------------------------------------------------------
    private void eliminarAlumno() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {campoId.getText().toString()};

        db.delete(UtilidadesFecha.TABLA_MATERIA, UtilidadesFecha.CAMPO_ID + "=?", parametros);
        Toast.makeText(getApplicationContext(), "Ya se Eliminó el alumno", Toast.LENGTH_LONG).show();
        campoId.setText("");
        limpiar();
        db.close();
    }

    //-------------------------------------------------------------------------------------------------
    private void actualizarAlumno() {
        SQLiteDatabase db = conn.getWritableDatabase();
        String[] parametros = {campoId.getText().toString()};
        ContentValues values = new ContentValues();
        values.put(UtilidadesFecha.CAMPO_NOMBRE, campoNombre.getText().toString());
        values.put(UtilidadesFecha.CAMPO_STATUS, campoStatus.getText().toString());


        db.update(UtilidadesFecha.TABLA_MATERIA, values, UtilidadesFecha.CAMPO_ID + "=?", parametros);
        Toast.makeText(getApplicationContext(), "Ya se actualizó el alumno", Toast.LENGTH_LONG).show();
        db.close();

    }

    //-------------------------------------------------------------------------------------------------
    private void consultarSql() {
        SQLiteDatabase db = conn.getReadableDatabase();
        String[] parametros = {campoId.getText().toString()};

        try {
            //select nombre,telefono from usuario where codigo=?
            Cursor cursor = db.rawQuery("SELECT " + UtilidadesFecha.CAMPO_STATUS + ", " + UtilidadesFecha.CAMPO_NOMBRE + " FROM " + UtilidadesFecha.TABLA_MATERIA +
                    " WHERE " + UtilidadesFecha.CAMPO_ID + "=? ", parametros);

            cursor.moveToFirst();
            campoStatus.setText(cursor.getString(0));
            campoNombre.setText(cursor.getString(1));


        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "No se encontro la fecha ", Toast.LENGTH_LONG).show();
            limpiar();
        }

    }

    //-------------------------------------------------------------------------------------------------
    private void limpiar() {
        campoNombre.setText("");
        campoStatus.setText("");
    }
}

