/*--------------------------------------------------------------------------------------------------
:*                         TECNOLOGICO NACIONAL DE MEXICO
:*                       INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                     INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                             DESARROLLO EN ANDROID "A"
:*
:*                   SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                        Clase que invoca al layout activity_portada
:*
:*  Archivo     : Acerca.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripci�n : En esta clase se invoca al layout activity_portada mediante el metodo
:*                 setContentView.
:*  Ultima modif:
:*  Fecha       Modific�             Motivo
:*==================================================================================================
:*
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

//--------------------------------------------------------------------------------------------------
public class Acerca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);
    }
}