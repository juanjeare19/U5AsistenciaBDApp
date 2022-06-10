/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                    Clase para consultar la lista de la materia de android2
:*
:*  Archivo     : ConsultarListaViewAndr2.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase para consultar la lista de la materia de android2
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import teclag.c18130534.u5asistenciabdapp.entidades.AndroidFechas;
import teclag.c18130534.u5asistenciabdapp.utilidades.UtilidadesFecha;

//--------------------------------------------------------------------------------------------------
public class ConsultarListViewActivityAndr2 extends AppCompatActivity {

    ListView listViewPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<AndroidFechas> listaAndroids;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista_list_view);



        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_alumnos2", null, 1);

        listViewPersonas = (ListView) findViewById(R.id.listViewPersonas);

        consultarListaPersonas();

        ArrayAdapter adaptador = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInformacion);
        listViewPersonas.setAdapter(adaptador);


        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion = "Fecha: " + listaAndroids.get(pos).getStatusid() + "\n";
                informacion += "Nombre: " + listaAndroids.get(pos).getStatus() + "\n";
                informacion += "Status: " + listaAndroids.get(pos).getNombre() + "\n";


                Toast.makeText(getApplicationContext(), informacion, Toast.LENGTH_LONG).show();

            }
        });

    }

    //--------------------------------------------------------------------------------------------------
    private void consultarListaPersonas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        AndroidFechas android = null;
        listaAndroids = new ArrayList<AndroidFechas>();
        //select * from tabla fechas topicos
        Cursor cursor = db.rawQuery("SELECT * FROM " + UtilidadesFecha.TABLA_MATERIA2, null);

        while (cursor.moveToNext()) {
            android = new AndroidFechas();
            android.setStatusid(cursor.getString(0));
            android.setStatus(cursor.getString(1));
            android.setNombre(cursor.getString(2));

            listaAndroids.add(android);
        }
        obtenerLista();
    }

    //--------------------------------------------------------------------------------------------------
    private void obtenerLista() {
        listaInformacion = new ArrayList<String>();

        for (int i = 0; i < listaAndroids.size(); i++) {
            listaInformacion.add(listaAndroids.get(i).getStatusid() + " - "
                    + listaAndroids.get(i).getNombre());
        }

    }
}


