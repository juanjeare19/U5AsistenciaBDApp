/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                    Clase para consultar al alumno3 desde el activity principal
:*
:*  Archivo     : ConsultaralumnoActivity3.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase para consultar al alumno3 desde el activity principal
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades3;

//--------------------------------------------------------------------------------------------------
public class ConsultarAlumnoActivity3 extends AppCompatActivity {

    EditText campoId, campoNombre, campoMateria, campoPresente, campoJustificado, campoTotal, campoPorcentaje;

    ConexionSQLiteHelper conn;

    //--------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_alumnos3);

        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_alumnos3", null, 1);

        campoId = (EditText) findViewById(R.id.campoId);
        campoNombre = (EditText) findViewById(R.id.campoNombreConsulta);
        campoMateria = (EditText) findViewById(R.id.campoMateriaConsulta);
        campoPresente = (EditText) findViewById(R.id.campoPresenteConsulta);
        campoJustificado = (EditText) findViewById(R.id.campoJustificadoConsulta);
        campoTotal = (EditText) findViewById(R.id.campoTotalConsulta);
        campoPorcentaje = (EditText) findViewById(R.id.campoPorcentajeConsulta);
    }

    //--------------------------------------------------------------------------------------------------
    public void btnFechasIdClick3(View v){
        Intent intent = new Intent(this, ConsultarFecha3Activity.class);
        startActivity(intent);
    }

    //--------------------------------------------------------------------------------------------------
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.btnConsultar2:
                consultarSql();
                break;
            case R.id.btnActualizar2:
                actualizarAlumno();
                break;
            case R.id.btnEliminar2:
                eliminarAlumno();
                break;
        }

    }

    //--------------------------------------------------------------------------------------------------
    private void eliminarAlumno() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={campoId.getText().toString()};

        db.delete(Utilidades3.TABLA_MATERIA,Utilidades3.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se Eliminó el alumno",Toast.LENGTH_LONG).show();
        campoId.setText("");
        limpiar();
        db.close();
    }

    //--------------------------------------------------------------------------------------------------
    private void actualizarAlumno() {
        SQLiteDatabase db=conn.getWritableDatabase();
        String[] parametros={campoId.getText().toString()};
        ContentValues values=new ContentValues();
        values.put(Utilidades3.CAMPO_NOMBRE,campoNombre.getText().toString());
        values.put(Utilidades3.CAMPO_MATERIA,campoMateria.getText().toString());
        values.put(Utilidades3.CAMPO_PRESENTE,campoPresente.getText().toString());
        values.put(Utilidades3.CAMPO_JUSTIFICADO,campoJustificado.getText().toString());
        values.put(Utilidades3.CAMPO_TOTAL,campoTotal.getText().toString());
        values.put(Utilidades3.CAMPO_PORCENTAJE,campoPorcentaje.getText().toString());

        db.update(Utilidades3.TABLA_MATERIA,values,Utilidades3.CAMPO_ID+"=?",parametros);
        Toast.makeText(getApplicationContext(),"Ya se actualizó el alumno",Toast.LENGTH_LONG).show();
        db.close();

    }

    //--------------------------------------------------------------------------------------------------
    private void consultarSql() {
        SQLiteDatabase db=conn.getReadableDatabase();
        String[] parametros={campoId.getText().toString()};

        try {
            Cursor cursor=db.rawQuery("SELECT "+Utilidades3.CAMPO_NOMBRE+", "+Utilidades3.CAMPO_MATERIA+","+
                            Utilidades3.CAMPO_PRESENTE+", "+Utilidades3.CAMPO_JUSTIFICADO+", "+Utilidades3.CAMPO_TOTAL+
                            ", "+Utilidades3.CAMPO_PORCENTAJE+" FROM "+Utilidades3.TABLA_MATERIA+" WHERE "+
                            Utilidades3.CAMPO_ID+"=? ",parametros);

            cursor.moveToFirst();
            campoNombre.setText(cursor.getString(0));
            campoMateria.setText(cursor.getString(1));
            campoPresente.setText(cursor.getString(2));
            campoJustificado.setText(cursor.getString(3));
            campoTotal.setText(cursor.getString(4));
            campoPorcentaje.setText(cursor.getString(5));

        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"El Numero de control no existe",Toast.LENGTH_LONG).show();
            limpiar();
        }

    }

    //--------------------------------------------------------------------------------------------------
    private void limpiar() {
        campoNombre.setText("");
        campoMateria.setText("");
        campoPresente.setText("");
        campoJustificado.setText("");
        campoTotal.setText("");
        campoPorcentaje.setText("");
    }

}
