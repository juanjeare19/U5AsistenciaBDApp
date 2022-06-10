/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                    Clase para consultar los detalles del alumno desde el activity3
:*
:*  Archivo     : DetalleAlumnoActivity3.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase para consultar los detalles del alumno desde el activity3
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import teclag.c18130534.u5asistenciabdapp.entidades.Android;

//--------------------------------------------------------------------------------------------------
public class DetalleAlumnoActivity3 extends AppCompatActivity {

    TextView campoId, campoNombre, campoMateria, campoPresente, campoJustificado, campoTotal, campoPorcentaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_alumnos);


        campoId = (TextView) findViewById(R.id.campoId);
        campoNombre = (TextView) findViewById(R.id.campoNombre);
        campoMateria = (TextView) findViewById(R.id.campoMateria);
        campoPresente = (TextView) findViewById(R.id.campoPresente);
        campoJustificado = (TextView) findViewById(R.id.campoJustificado);
        campoTotal = (TextView) findViewById(R.id.campoTotal);
        campoPorcentaje = (TextView) findViewById(R.id.campoPorcentaje);

        Bundle objetoEnviado=getIntent().getExtras();
        Android user=null;

        if(objetoEnviado!=null){
            user= (Android) objetoEnviado.getSerializable("android");
            campoId.setText(user.getId().toString());
            campoNombre.setText(user.getNombre().toString());
            campoMateria.setText(user.getMateria().toString());
            campoPresente.setText(user.getPresente().toString());
            campoJustificado.setText(user.getJustificado().toString());
            campoTotal.setText(user.getTotal().toString());
            campoPorcentaje.setText(user.getPorcentaje().toString());

        }

    }
}

