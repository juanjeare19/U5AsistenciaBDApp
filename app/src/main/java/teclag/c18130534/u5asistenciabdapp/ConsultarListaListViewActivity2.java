/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                    Clase para consultar la lista del view activity2
:*
:*  Archivo     : ConsultarListaViewActivity2.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase para consultar la lista del view activity2
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
import teclag.c18130534.u5asistenciabdapp.entidades.Android;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades2;

//--------------------------------------------------------------------------------------------------
public class ConsultarListaListViewActivity2 extends AppCompatActivity {

    ListView listViewPersonas;
    ArrayList<String> listaInformacion;
    ArrayList<Android> listaTopicos;

    ConexionSQLiteHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_lista_list_view);

        conn=new ConexionSQLiteHelper(getApplicationContext(),"bd_alumnos2",null,1);

        listViewPersonas= (ListView) findViewById(R.id.listViewPersonas);

        consultarListaPersonas();

        ArrayAdapter adaptador=new ArrayAdapter(this,android.R.layout.simple_list_item_1,listaInformacion);
        listViewPersonas.setAdapter(adaptador);

        listViewPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                String informacion="id: "+ listaTopicos.get(pos).getId()+"\n";
                informacion+="Nombre: "+ listaTopicos.get(pos).getNombre()+"\n";
                informacion+="Materia: "+ listaTopicos.get(pos).getMateria()+"\n";
                informacion+="Presente: "+ listaTopicos.get(pos).getPresente()+"\n";
                informacion+="Justificado: "+ listaTopicos.get(pos).getJustificado()+"\n";
                informacion+="Total: "+ listaTopicos.get(pos).getTotal()+"\n";
                informacion+="Porcentaje: "+ listaTopicos.get(pos).getPorcentaje()+"\n";

                Toast.makeText(getApplicationContext(),informacion,Toast.LENGTH_LONG).show();

                Android user= listaTopicos.get(pos);

                Intent intent=new Intent(ConsultarListaListViewActivity2.this, DetalleAlumnoActivity2.class);

                Bundle bundle=new Bundle();
                bundle.putSerializable("android",user);

                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }

    //--------------------------------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_fechas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //--------------------------------------------------------------------------------------------------
    public void btnAndroid(MenuItem item) {
        Intent intent = new Intent(this, ConsultarListViewActivityAndr.class);
        startActivity(intent);
    }
    //--------------------------------------------------------------------------------------------------
    public void btnTopicos(MenuItem item) {
        Intent intent = new Intent(this, ConsultarListViewActivityAndr2.class);
        startActivity(intent);
    }
    //--------------------------------------------------------------------------------------------------
    public void btnAutomatas2(MenuItem item) {
        Intent intent = new Intent(this, ConsultarListViewActivityAndr3.class);
        startActivity(intent);
    }

    //--------------------------------------------------------------------------------------------------
    private void consultarListaPersonas() {
        SQLiteDatabase db=conn.getReadableDatabase();

        Android android =null;
        listaTopicos =new ArrayList<Android>();
        //select * from tabla topicos
        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades2.TABLA_MATERIA2,null);

        while (cursor.moveToNext()){
            android =new Android();
            android.setId(cursor.getInt(0));
            android.setNombre(cursor.getString(1));
            android.setMateria(cursor.getString(2));
            android.setPresente(cursor.getInt(3));
            android.setJustificado(cursor.getInt(4));
            android.setTotal(cursor.getInt(5));
            android.setPorcentaje(cursor.getInt(6));


            listaTopicos.add(android);
        }
        obtenerLista();
    }

    //--------------------------------------------------------------------------------------------------
    private void obtenerLista() {
        listaInformacion=new ArrayList<String>();

        for (int i = 0; i< listaTopicos.size(); i++){
            listaInformacion.add(listaTopicos.get(i).getId()+" - "
                    + listaTopicos.get(i).getNombre());
        }

    }

}
