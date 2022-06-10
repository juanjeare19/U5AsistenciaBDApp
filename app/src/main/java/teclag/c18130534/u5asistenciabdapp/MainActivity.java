/*--------------------------------------------------------------------------------------------------
:*                             TECNOLOGICO NACIONAL DE MEXICO
:*                           INSTITUTO TECNOLOGICO DE LA LAGUNA
:*                         INGENIERIA EN SISTEMAS COMPUTACIONALES
:*                                 DESARROLLO EN ANDROID "A"
:*
:*                       SEMESTRE: ENE-JUN/2021    HORA: 10-11 HRS
:*
:*                            Clase principal del main activity
:*
:*  Archivo     : MainActivity.java
:*  Autor       : Jesus Gerardo Gonzalez Ramirez   18130561
                  Alejandro Israel Medina Lujan 18130576
                  Juan Jesus Arellano Sanchez 18130534
                  Juan Carlos Romo Arroyo 17130836
:*  Fecha       : 28/Junio/2021
:*  Compilador  : Android Studio 4.1.2
:*  Descripción : Clase principal del main activity
:*  Ultima modif:
:*  Fecha       Modificó             Motivo
:*==================================================================================================
:*------------------------------------------------------------------------------------------------*/
package teclag.c18130534.u5asistenciabdapp;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import teclag.c18130534.androlib.util.permisos.ChecadorDePermisos;
import teclag.c18130534.androlib.util.permisos.PermisoApp;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades2;
import teclag.c18130534.u5asistenciabdapp.utilidades.Utilidades3;
import teclag.c18130534.u5asistenciabdapp.utilidades.UtilidadesFecha;

//---------------------------------------------------------------------------------------------------
public class MainActivity extends AppCompatActivity {

    private PermisoApp[] permisosReq = new PermisoApp[]{
            new PermisoApp(Manifest.permission.READ_EXTERNAL_STORAGE, "Almacenamiento", false)
    };

    //---------------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChecadorDePermisos.checarPermisos(this, permisosReq);
    }

    //---------------------------------------------------------------------------------------------------
    ConexionSQLiteHelper conn; //= new ConexionSQLiteHelper(this, "bd_alumnos1", null, 1);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_acercade, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //---------------------------------------------------------------------------------------------------
    public void mniAcercaDe(MenuItem item) {
        Intent intent = new Intent(this, Acerca.class);
        startActivity(intent);
    }

    //---------------------------------------------------------------------------------------------------
    public void onClick(View view) {
        Intent miIntent = null;
        switch (view.getId()) {
            case R.id.btnConsultaIndividual:
                miIntent = new Intent(MainActivity.this, ConsultarAlumnoActivity.class);
                break;
            case R.id.btnConsultaLista:
                miIntent = new Intent(MainActivity.this, ConsultarListaListViewActivity.class);
                break;
            case R.id.btnOpcionConsulta2:
                miIntent = new Intent(MainActivity.this, ConsultarAlumnoActivity2.class);
                break;
            case R.id.btnOpcionLista2:
                miIntent = new Intent(MainActivity.this, ConsultarListaListViewActivity2.class);
                break;
            case R.id.btnListaAlumnos3:
                miIntent = new Intent(MainActivity.this, ConsultarListaListViewActivity3.class);
                break;
            case R.id.bntConsultaAlumno3:
                miIntent = new Intent(MainActivity.this, ConsultarAlumnoActivity3.class);
                break;
            case R.id.btnAsisAndr:inicializarFecha();countPresandr();countJustandr();sumPresJust();break;
            case R.id.btnAsisTop:inicializarFechaTop();countPresandr2();countJustandr2();sumPresJust2();break;
            case R.id.btnAsisLA2: inicializarFechaAut();countPresandr3();countJustandr3();sumPresJust3();break;
            case R.id.btnVacAndr: deleteAndr();Toast.makeText(this, "Tablas Vaciadas", Toast.LENGTH_SHORT).show();break;
            case R.id.btnVacTop: deleteTop();Toast.makeText(this, "Tablas Vaciadas", Toast.LENGTH_SHORT).show();break;
            case R.id.btnVacAut: deleteAut();Toast.makeText(this, "Tablas Vaciadas", Toast.LENGTH_SHORT).show();break;
            case R.id.btnCarAndr: inicializar();break;
            case R.id.btnCarTop: inicializar2();break;
            case R.id.btnCarAuto: inicializar3();break;
            //case R.id.btnFileExplo:getExplorer(view);
        }
        if (miIntent != null) {
            startActivity(miIntent);
        }
    }

    //---------------------------------------------------------------------------------------------------
    private void deleteAndr() {
        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_alumnos1", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("delete from " + Utilidades.TABLA_MATERIA);
        db.close();
    }

    //---------------------------------------------------------------------------------------------------
    private void deleteTop() {
        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_alumnos2", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("delete from " + Utilidades2.TABLA_MATERIA2);
        db.close();
    }

    //---------------------------------------------------------------------------------------------------
    private void deleteAut() {
        conn = new ConexionSQLiteHelper(getApplicationContext(), "bd_alumnos3", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        db.execSQL("delete from " + Utilidades3.TABLA_MATERIA);
        db.close();
    }

//-----------------------------------------------------------------------------------------------------------------
    private long cantidadRegistrosFecha() {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos1",
                null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db, "androidfecha");
        db.close();
        return cn;
    }

    //---------------------------------------------------------------------------------------------------
    private String[] leerArchivoFecha() {
        InputStream inputStream = getResources().openRawResource(R.raw.fechasandroids);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");
    }

    //---------------------------------------------------------------------------------------------------
    public void inicializarFecha() {
        if (cantidadRegistrosFecha() == 0) {
            String[] texto = leerArchivoFecha();
            ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos1",
                    null, 1);
            SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
            db.beginTransaction();

            for (int i = 0; i < texto.length; i++) {
                String[] linea = texto[i].split(":");
                ContentValues contentValues = new ContentValues();
                contentValues.put("statusid", linea[0]);
                contentValues.put("nombre", linea[1]);
                contentValues.put("status", linea[2]);
                db.insert("androidfecha", null, contentValues);
            }
            Toast.makeText(this, "Asistencias Insertadas = " + texto.length, Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful();
            db.endTransaction();
        //} else {
          //  Toast.makeText(this, "La tabla ya esta llena!", Toast.LENGTH_LONG).show();
        }
    }
    //----------------------------------------------------------------------------------------------
    private long cantidadRegistrosFechaTop() {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos2",
                null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db, "androidfechatopicos");
        db.close();
        return cn;
    }

    //---------------------------------------------------------------------------------------------------
    private String[] leerArchivoFechaTop() {
        InputStream inputStream = getResources().openRawResource(R.raw.fechastopicos);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");
    }

    //---------------------------------------------------------------------------------------------------
    public void inicializarFechaTop() {
        if (cantidadRegistrosFechaTop() == 0) {
            String[] texto = leerArchivoFechaTop();
            ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos2",
                    null, 1);
            SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
            db.beginTransaction();

            for (int i = 0; i < texto.length; i++) {
                String[] linea = texto[i].split(":");
                ContentValues contentValues = new ContentValues();
                contentValues.put("statusid", linea[0]);
                contentValues.put("nombre", linea[1]);
                contentValues.put("status", linea[2]);
                db.insert("androidfechatopicos", null, contentValues);
            }
            Toast.makeText(this, "Asistencias insertadas = " + texto.length, Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful();
            db.endTransaction();
        //} else {
          //  Toast.makeText(this, "La tabla ya esta llena!", Toast.LENGTH_LONG).show();
        }
    }
    //------------------------------------------------------------------------------------------------------------
    private long cantidadRegistrosFechaAut() {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos3",
                null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db, "automatasfecha");
        db.close();
        return cn;
    }

    //---------------------------------------------------------------------------------------------------
    private String[] leerArchivoFechaAut() {
        InputStream inputStream = getResources().openRawResource(R.raw.fechasautomatas);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");
    }

    //---------------------------------------------------------------------------------------------------
    public void inicializarFechaAut() {
        if (cantidadRegistrosFechaAut() == 0) {
            String[] texto = leerArchivoFechaAut();
            ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos3",
                    null, 1);
            SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
            db.beginTransaction();

            for (int i = 0; i < texto.length; i++) {
                String[] linea = texto[i].split(":");
                ContentValues contentValues = new ContentValues();
                contentValues.put("statusid", linea[0]);
                contentValues.put("nombre", linea[1]);
                contentValues.put("status", linea[2]);
                db.insert("automatasfecha", null, contentValues);
            }
            Toast.makeText(this, "Asistencias insertadas = " + texto.length, Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful();
            db.endTransaction();
       // } else {
         //   Toast.makeText(this, "La tabla ya esta llena!", Toast.LENGTH_LONG).show();
        }
    }
//-----------------------------------------------------------------------------------------------------------------------
    private long cantidadRegistros() {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos1",
                null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db, "android");
        db.close();
        return cn;
    }

    //---------------------------------------------------------------------------------------------------
    private String[] leerArchivo() {
        InputStream inputStream = getResources().openRawResource(R.raw.listaandroid);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");
    }

    //---------------------------------------------------------------------------------------------------
    private void inicializar() {
        if (cantidadRegistros() == 0) {
            String[] texto = leerArchivo();
            ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos1",
                    null, 1);
            SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
            db.beginTransaction();

            for (int i = 0; i < texto.length; i++) {
                String[] linea = texto[i].split(",");
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", linea[0]);
                contentValues.put("nombre", linea[1]);
                contentValues.put("materia", linea[2]);
                db.insert("android", null, contentValues);
            }
            Toast.makeText(this, "Registros insertados = " + texto.length, Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful();
            db.endTransaction();
        } else {
            Toast.makeText(this, "Las tablas de los alumnos ya esta llena!", Toast.LENGTH_LONG).show();
        }
    }

    //----------------------------------------------------------------------------------------------------------------
    private long cantidadRegistros2() {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos2",
                null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db, "topicos");
        db.close();
        return cn;
    }

    //---------------------------------------------------------------------------------------------------
    private String[] leerArchivo2() {
        InputStream inputStream = getResources().openRawResource(R.raw.listatopicos);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");
    }

    //---------------------------------------------------------------------------------------------------
    public void inicializar2() {
        if (cantidadRegistros2() == 0) {
            String[] texto = leerArchivo2();
            ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos2",
                    null, 1);
            SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
            db.beginTransaction();

            for (int i = 0; i < texto.length; i++) {
                String[] linea = texto[i].split(",");
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", linea[0]);
                contentValues.put("nombre", linea[1]);
                contentValues.put("materia", linea[2]);
                db.insert("topicos", null, contentValues);
            }
            Toast.makeText(this, "Registros insertados = " + texto.length, Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful();
            db.endTransaction();
        } else {
            Toast.makeText(this, "Las tablas de los alumnos ya esta llena!", Toast.LENGTH_LONG).show();
        }
    }

    //-----------------------------------------------------------------------------------------------------------------
    private long cantidadRegistros3() {
        ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos3",
                null, 1);
        SQLiteDatabase db = conexionSQLiteHelper.getReadableDatabase();
        long cn = DatabaseUtils.queryNumEntries(db, "la2");
        db.close();
        return cn;
    }

    //---------------------------------------------------------------------------------------------------
    private String[] leerArchivo3() {
        InputStream inputStream = getResources().openRawResource(R.raw.listaautomatas);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            int i = inputStream.read();
            while (i != -1) {
                byteArrayOutputStream.write(i);
                i = inputStream.read();
            }
            inputStream.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return byteArrayOutputStream.toString().split("\n");
    }

    //---------------------------------------------------------------------------------------------------
    public void inicializar3() {
        if (cantidadRegistros3() == 0) {
            String[] texto = leerArchivo3();
            ConexionSQLiteHelper conexionSQLiteHelper = new ConexionSQLiteHelper(this, "bd_alumnos3",
                    null, 1);
            SQLiteDatabase db = conexionSQLiteHelper.getWritableDatabase();
            db.beginTransaction();

            for (int i = 0; i < texto.length; i++) {
                String[] linea = texto[i].split(",");
                ContentValues contentValues = new ContentValues();
                contentValues.put("id", linea[0]);
                contentValues.put("nombre", linea[1]);
                contentValues.put("materia", linea[2]);
                db.insert("la2", null, contentValues);
            }
            Toast.makeText(this, "Registros insertados = " + texto.length, Toast.LENGTH_LONG).show();
            db.setTransactionSuccessful();
            db.endTransaction();
        } else {
            Toast.makeText(this, "La tabla de los alumnos ya esta llena", Toast.LENGTH_LONG).show();
        }
    }
    //----------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void countPresandr() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos1", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();

        //Num control condicion update
        String[] parametros = {"18131209"};String[] parametros2 = {"16130783"};String[] parametros3 = {"17130576"};String[] parametros4 = {"18130534"};
        String[] parametros5 = {"17130004"};String[] parametros6 = {"17130764"};String[] parametros7 = {"15131296"};String[] parametros8 = {"16131450"};
        String[] parametros9 = {"15450676"};String[] parametros10 = {"17130026"};String[] parametros11 = {"17130028"};String[] parametros12 = {"17130787"};
        String[] parametros13 = {"18130561"};String[] parametros14 = {"16130812"};String[] parametros15 = {"17130043"};String[] parametros16 = {"17131621"};
        String[] parametros17 = {"18130575"};String[] parametros18 = {"17130801"};String[] parametros19 = {"18130576"};String[] parametros20 = {"18130583"};
        String[] parametros21 = {"18130586"};String[] parametros22 = {"18130588"};String[] parametros23 = {"18130592"};String[] parametros24 = {"171000063"};
        String[] parametros25 = {"17130059"};String[] parametros26 = {"18130597"};String[] parametros27 = {"17131623"};String[] parametros28 = {"17130836"};
        String[] parametros29 = {"17130839"};String[] parametros30 = {"17130844"};String[] parametros31 = {"17130848"};String[] parametros32 = {"17130850"};
        String[] parametros33 = {"17130073"};String[] parametros34 = {"17130852"};String[] parametros35 = {"171000302"};String[] parametros36 = {"17130854"};

        //Variables values para el update del campo presente
        ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
        ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
        ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
        ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
        ContentValues values22 = new ContentValues();ContentValues values23 = new ContentValues();ContentValues values24 = new ContentValues();
        ContentValues values25 = new ContentValues();ContentValues values26 = new ContentValues();ContentValues values27 = new ContentValues();
        ContentValues values28 = new ContentValues();ContentValues values29 = new ContentValues();ContentValues values30 = new ContentValues();
        ContentValues values31 = new ContentValues();ContentValues values32 = new ContentValues();ContentValues values33 = new ContentValues();
        ContentValues values34 = new ContentValues();ContentValues values35 = new ContentValues();ContentValues values36 = new ContentValues();

        //Consultas total columnas presente
        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131209%'", null);
        long count2 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%16130783%'", null);
        long count3 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130576%'", null);
        long count4 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130534%'", null);
        long count5 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130004%'", null);
        long count6 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130764%'", null);
        long count7 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%15131296%'", null);
        long count8 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%16131450%'", null);
        long count9 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%15450676%'", null);
        long count10 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130026%'", null);
        long count11 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130028%'", null);
        long count12 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130787%'", null);
        long count13 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130561%'", null);
        long count14 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%16130812%'", null);
        long count15 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130043%'", null);
        long count16 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17131621%'", null);
        long count17 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130575%'", null);
        long count18 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130801%'", null);
        long count19 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130576%'", null);
        long count20 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130583%'", null);
        long count21 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130586%'", null);
        long count22 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130588%'", null);
        long count23 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130592%'", null);
        long count24 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%171000063%'", null);
        long count25 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130059%'", null);
        long count26 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130597%'", null);
        long count27 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17131623%'", null);
        long count28 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130836%'", null);
        long count29 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130839%'", null);
        long count30 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130844%'", null);
        long count31 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130848%'", null);
        long count32 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130850%'", null);
        long count33 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130073%'", null);
        long count34 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130852%'", null);
        long count35 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%171000302%'", null);
        long count36 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130854%'", null);

        //insercion de los valores del count
        values.put(Utilidades.CAMPO_PRESENTE, count);values2.put(Utilidades.CAMPO_PRESENTE, count2);values3.put(Utilidades.CAMPO_PRESENTE, count3);
        values4.put(Utilidades.CAMPO_PRESENTE, count4);values5.put(Utilidades.CAMPO_PRESENTE, count5);values6.put(Utilidades.CAMPO_PRESENTE, count6);
        values7.put(Utilidades.CAMPO_PRESENTE, count7);values8.put(Utilidades.CAMPO_PRESENTE, count8);values9.put(Utilidades.CAMPO_PRESENTE, count9);
        values10.put(Utilidades.CAMPO_PRESENTE, count10);values11.put(Utilidades.CAMPO_PRESENTE, count11);values12.put(Utilidades.CAMPO_PRESENTE, count12);
        values13.put(Utilidades.CAMPO_PRESENTE, count13);values14.put(Utilidades.CAMPO_PRESENTE, count14);values15.put(Utilidades.CAMPO_PRESENTE, count15);
        values16.put(Utilidades.CAMPO_PRESENTE, count16);values17.put(Utilidades.CAMPO_PRESENTE, count17);values18.put(Utilidades.CAMPO_PRESENTE, count18);
        values19.put(Utilidades.CAMPO_PRESENTE, count19);values20.put(Utilidades.CAMPO_PRESENTE, count20);values21.put(Utilidades.CAMPO_PRESENTE, count21);
        values22.put(Utilidades.CAMPO_PRESENTE, count22);values23.put(Utilidades.CAMPO_PRESENTE, count23);values24.put(Utilidades.CAMPO_PRESENTE, count24);
        values25.put(Utilidades.CAMPO_PRESENTE, count25);values26.put(Utilidades.CAMPO_PRESENTE, count26);values27.put(Utilidades.CAMPO_PRESENTE, count27);
        values28.put(Utilidades.CAMPO_PRESENTE, count28);values29.put(Utilidades.CAMPO_PRESENTE, count29);values30.put(Utilidades.CAMPO_PRESENTE, count30);
        values31.put(Utilidades.CAMPO_PRESENTE, count31);values32.put(Utilidades.CAMPO_PRESENTE, count32);values33.put(Utilidades.CAMPO_PRESENTE, count33);
        values34.put(Utilidades.CAMPO_PRESENTE, count34);values35.put(Utilidades.CAMPO_PRESENTE, count35);values36.put(Utilidades.CAMPO_PRESENTE, count36);

        //update campo presente
        db.update(Utilidades.TABLA_MATERIA, values, Utilidades.CAMPO_ID + "=?", parametros);db.update(Utilidades.TABLA_MATERIA, values2, Utilidades.CAMPO_ID + "=?", parametros2);
        db.update(Utilidades.TABLA_MATERIA, values3, Utilidades.CAMPO_ID + "=?", parametros3);db.update(Utilidades.TABLA_MATERIA, values4, Utilidades.CAMPO_ID + "=?", parametros4);
        db.update(Utilidades.TABLA_MATERIA, values5, Utilidades.CAMPO_ID + "=?", parametros5);db.update(Utilidades.TABLA_MATERIA, values6, Utilidades.CAMPO_ID + "=?", parametros6);
        db.update(Utilidades.TABLA_MATERIA, values7, Utilidades.CAMPO_ID + "=?", parametros7);db.update(Utilidades.TABLA_MATERIA, values8, Utilidades.CAMPO_ID + "=?", parametros8);
        db.update(Utilidades.TABLA_MATERIA, values9, Utilidades.CAMPO_ID + "=?", parametros9);db.update(Utilidades.TABLA_MATERIA, values10, Utilidades.CAMPO_ID + "=?", parametros10);
        db.update(Utilidades.TABLA_MATERIA, values11, Utilidades.CAMPO_ID + "=?", parametros11);db.update(Utilidades.TABLA_MATERIA, values12, Utilidades.CAMPO_ID + "=?", parametros12);
        db.update(Utilidades.TABLA_MATERIA, values13, Utilidades.CAMPO_ID + "=?", parametros13);db.update(Utilidades.TABLA_MATERIA, values14, Utilidades.CAMPO_ID + "=?", parametros14);
        db.update(Utilidades.TABLA_MATERIA, values15, Utilidades.CAMPO_ID + "=?", parametros15);db.update(Utilidades.TABLA_MATERIA, values16, Utilidades.CAMPO_ID + "=?", parametros16);
        db.update(Utilidades.TABLA_MATERIA, values17, Utilidades.CAMPO_ID + "=?", parametros17);db.update(Utilidades.TABLA_MATERIA, values18, Utilidades.CAMPO_ID + "=?", parametros18);
        db.update(Utilidades.TABLA_MATERIA, values19, Utilidades.CAMPO_ID + "=?", parametros19);db.update(Utilidades.TABLA_MATERIA, values20, Utilidades.CAMPO_ID + "=?", parametros20);
        db.update(Utilidades.TABLA_MATERIA, values21, Utilidades.CAMPO_ID + "=?", parametros21);db.update(Utilidades.TABLA_MATERIA, values22, Utilidades.CAMPO_ID + "=?", parametros22);
        db.update(Utilidades.TABLA_MATERIA, values23, Utilidades.CAMPO_ID + "=?", parametros23);db.update(Utilidades.TABLA_MATERIA, values24, Utilidades.CAMPO_ID + "=?", parametros24);
        db.update(Utilidades.TABLA_MATERIA, values25, Utilidades.CAMPO_ID + "=?", parametros25);db.update(Utilidades.TABLA_MATERIA, values26, Utilidades.CAMPO_ID + "=?", parametros26);
        db.update(Utilidades.TABLA_MATERIA, values27, Utilidades.CAMPO_ID + "=?", parametros27);db.update(Utilidades.TABLA_MATERIA, values28, Utilidades.CAMPO_ID + "=?", parametros28);
        db.update(Utilidades.TABLA_MATERIA, values29, Utilidades.CAMPO_ID + "=?", parametros29);db.update(Utilidades.TABLA_MATERIA, values30, Utilidades.CAMPO_ID + "=?", parametros30);
        db.update(Utilidades.TABLA_MATERIA, values31, Utilidades.CAMPO_ID + "=?", parametros31);db.update(Utilidades.TABLA_MATERIA, values32, Utilidades.CAMPO_ID + "=?", parametros32);
        db.update(Utilidades.TABLA_MATERIA, values33, Utilidades.CAMPO_ID + "=?", parametros33);db.update(Utilidades.TABLA_MATERIA, values34, Utilidades.CAMPO_ID + "=?", parametros34);
        db.update(Utilidades.TABLA_MATERIA, values35, Utilidades.CAMPO_ID + "=?", parametros35);db.update(Utilidades.TABLA_MATERIA, values36, Utilidades.CAMPO_ID + "=?", parametros36);

        db.close();

    }
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void countJustandr() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos1", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Num control condicion update
        String[] parametros = {"18131209"};String[] parametros2 = {"16130783"};String[] parametros3 = {"17130576"};String[] parametros4 = {"18130534"};
        String[] parametros5 = {"17130004"};String[] parametros6 = {"17130764"};String[] parametros7 = {"15131296"};String[] parametros8 = {"16131450"};
        String[] parametros9 = {"15450676"};String[] parametros10 = {"17130026"};String[] parametros11 = {"17130028"};String[] parametros12 = {"17130787"};
        String[] parametros13 = {"18130561"};String[] parametros14 = {"16130812"};String[] parametros15 = {"17130043"};String[] parametros16 = {"17131621"};
        String[] parametros17 = {"18130575"};String[] parametros18 = {"17130801"};String[] parametros19 = {"18130576"};String[] parametros20 = {"18130583"};
        String[] parametros21 = {"18130586"};String[] parametros22 = {"18130588"};String[] parametros23 = {"18130592"};String[] parametros24 = {"171000063"};
        String[] parametros25 = {"17130059"};String[] parametros26 = {"18130597"};String[] parametros27 = {"17131623"};String[] parametros28 = {"17130836"};
        String[] parametros29 = {"17130839"};String[] parametros30 = {"17130844"};String[] parametros31 = {"17130848"};String[] parametros32 = {"17130850"};
        String[] parametros33 = {"17130073"};String[] parametros34 = {"17130852"};String[] parametros35 = {"171000302"};String[] parametros36 = {"17130854"};

        //Variables values para el update del campo justificado
        ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
        ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
        ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
        ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
        ContentValues values22 = new ContentValues();ContentValues values23 = new ContentValues();ContentValues values24 = new ContentValues();
        ContentValues values25 = new ContentValues();ContentValues values26 = new ContentValues();ContentValues values27 = new ContentValues();
        ContentValues values28 = new ContentValues();ContentValues values29 = new ContentValues();ContentValues values30 = new ContentValues();
        ContentValues values31 = new ContentValues();ContentValues values32 = new ContentValues();ContentValues values33 = new ContentValues();
        ContentValues values34 = new ContentValues();ContentValues values35 = new ContentValues();ContentValues values36 = new ContentValues();

        //Consultas total columnas justificado
        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131209%'", null);
        long count2 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%16130783%'", null);
        long count3 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130576%'", null);
        long count4 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130534%'", null);
        long count5 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130004%'", null);
        long count6 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130764%'", null);
        long count7 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%15131296%'", null);
        long count8 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%16131450%'", null);
        long count9 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%15450676%'", null);
        long count10 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130026%'", null);
        long count11 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130028%'", null);
        long count12 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130787%'", null);
        long count13 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130561%'", null);
        long count14 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%16130812%'", null);
        long count15 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130043%'", null);
        long count16 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17131621%'", null);
        long count17 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130575%'", null);
        long count18 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130801%'", null);
        long count19 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130576%'", null);
        long count20 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130583%'", null);
        long count21 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130586%'", null);
        long count22 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130588%'", null);
        long count23 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130592%'", null);
        long count24 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%171000063%'", null);
        long count25 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130059%'", null);
        long count26 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130597%'", null);
        long count27 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17131623%'", null);
        long count28 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130836%'", null);
        long count29 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130839%'", null);
        long count30 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130844%'", null);
        long count31 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130848%'", null);
        long count32 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130850%'", null);
        long count33 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130073%'", null);
        long count34 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130852%'", null);
        long count35 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%171000302%'", null);
        long count36 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130854%'", null);

        //insercion de los valores del count
        values.put(Utilidades.CAMPO_JUSTIFICADO, count);values2.put(Utilidades.CAMPO_JUSTIFICADO, count2);values3.put(Utilidades.CAMPO_JUSTIFICADO, count3);
        values4.put(Utilidades.CAMPO_JUSTIFICADO, count4);values5.put(Utilidades.CAMPO_JUSTIFICADO, count5);values6.put(Utilidades.CAMPO_JUSTIFICADO, count6);
        values7.put(Utilidades.CAMPO_JUSTIFICADO, count7);values8.put(Utilidades.CAMPO_JUSTIFICADO, count8);values9.put(Utilidades.CAMPO_JUSTIFICADO, count9);
        values10.put(Utilidades.CAMPO_JUSTIFICADO, count10);values11.put(Utilidades.CAMPO_JUSTIFICADO, count11);values12.put(Utilidades.CAMPO_JUSTIFICADO, count12);
        values13.put(Utilidades.CAMPO_JUSTIFICADO, count13);values14.put(Utilidades.CAMPO_JUSTIFICADO, count14);values15.put(Utilidades.CAMPO_JUSTIFICADO, count15);
        values16.put(Utilidades.CAMPO_JUSTIFICADO, count16);values17.put(Utilidades.CAMPO_JUSTIFICADO, count17);values18.put(Utilidades.CAMPO_JUSTIFICADO, count18);
        values19.put(Utilidades.CAMPO_JUSTIFICADO, count19);values20.put(Utilidades.CAMPO_JUSTIFICADO, count20);values21.put(Utilidades.CAMPO_JUSTIFICADO, count21);
        values22.put(Utilidades.CAMPO_JUSTIFICADO, count22);values23.put(Utilidades.CAMPO_JUSTIFICADO, count23);values24.put(Utilidades.CAMPO_JUSTIFICADO, count24);
        values25.put(Utilidades.CAMPO_JUSTIFICADO, count25);values26.put(Utilidades.CAMPO_JUSTIFICADO, count26);values27.put(Utilidades.CAMPO_JUSTIFICADO, count27);
        values28.put(Utilidades.CAMPO_JUSTIFICADO, count28);values29.put(Utilidades.CAMPO_JUSTIFICADO, count29);values30.put(Utilidades.CAMPO_JUSTIFICADO, count30);
        values31.put(Utilidades.CAMPO_JUSTIFICADO, count31);values32.put(Utilidades.CAMPO_JUSTIFICADO, count32);values33.put(Utilidades.CAMPO_JUSTIFICADO, count33);
        values34.put(Utilidades.CAMPO_JUSTIFICADO, count34);values35.put(Utilidades.CAMPO_JUSTIFICADO, count35);values36.put(Utilidades.CAMPO_JUSTIFICADO, count36);

        //update campo justificado
        db.update(Utilidades.TABLA_MATERIA, values, Utilidades.CAMPO_ID + "=?", parametros);db.update(Utilidades.TABLA_MATERIA, values2, Utilidades.CAMPO_ID + "=?", parametros2);
        db.update(Utilidades.TABLA_MATERIA, values3, Utilidades.CAMPO_ID + "=?", parametros3);db.update(Utilidades.TABLA_MATERIA, values4, Utilidades.CAMPO_ID + "=?", parametros4);
        db.update(Utilidades.TABLA_MATERIA, values5, Utilidades.CAMPO_ID + "=?", parametros5);db.update(Utilidades.TABLA_MATERIA, values6, Utilidades.CAMPO_ID + "=?", parametros6);
        db.update(Utilidades.TABLA_MATERIA, values7, Utilidades.CAMPO_ID + "=?", parametros7);db.update(Utilidades.TABLA_MATERIA, values8, Utilidades.CAMPO_ID + "=?", parametros8);
        db.update(Utilidades.TABLA_MATERIA, values9, Utilidades.CAMPO_ID + "=?", parametros9);
        db.update(Utilidades.TABLA_MATERIA, values10, Utilidades.CAMPO_ID + "=?", parametros10);db.update(Utilidades.TABLA_MATERIA, values11, Utilidades.CAMPO_ID + "=?", parametros11);
        db.update(Utilidades.TABLA_MATERIA, values12, Utilidades.CAMPO_ID + "=?", parametros12);db.update(Utilidades.TABLA_MATERIA, values13, Utilidades.CAMPO_ID + "=?", parametros13);
        db.update(Utilidades.TABLA_MATERIA, values14, Utilidades.CAMPO_ID + "=?", parametros14);db.update(Utilidades.TABLA_MATERIA, values15, Utilidades.CAMPO_ID + "=?", parametros15);
        db.update(Utilidades.TABLA_MATERIA, values16, Utilidades.CAMPO_ID + "=?", parametros16);db.update(Utilidades.TABLA_MATERIA, values17, Utilidades.CAMPO_ID + "=?", parametros17);
        db.update(Utilidades.TABLA_MATERIA, values18, Utilidades.CAMPO_ID + "=?", parametros18);db.update(Utilidades.TABLA_MATERIA, values19, Utilidades.CAMPO_ID + "=?", parametros19);
        db.update(Utilidades.TABLA_MATERIA, values20, Utilidades.CAMPO_ID + "=?", parametros20);db.update(Utilidades.TABLA_MATERIA, values21, Utilidades.CAMPO_ID + "=?", parametros21);
        db.update(Utilidades.TABLA_MATERIA, values22, Utilidades.CAMPO_ID + "=?", parametros22);db.update(Utilidades.TABLA_MATERIA, values23, Utilidades.CAMPO_ID + "=?", parametros23);
        db.update(Utilidades.TABLA_MATERIA, values24, Utilidades.CAMPO_ID + "=?", parametros24);db.update(Utilidades.TABLA_MATERIA, values25, Utilidades.CAMPO_ID + "=?", parametros25);
        db.update(Utilidades.TABLA_MATERIA, values26, Utilidades.CAMPO_ID + "=?", parametros26);db.update(Utilidades.TABLA_MATERIA, values27, Utilidades.CAMPO_ID + "=?", parametros27);
        db.update(Utilidades.TABLA_MATERIA, values28, Utilidades.CAMPO_ID + "=?", parametros28);db.update(Utilidades.TABLA_MATERIA, values29, Utilidades.CAMPO_ID + "=?", parametros29);
        db.update(Utilidades.TABLA_MATERIA, values30, Utilidades.CAMPO_ID + "=?", parametros30);db.update(Utilidades.TABLA_MATERIA, values31, Utilidades.CAMPO_ID + "=?", parametros31);
        db.update(Utilidades.TABLA_MATERIA, values32, Utilidades.CAMPO_ID + "=?", parametros32);
        db.update(Utilidades.TABLA_MATERIA, values33, Utilidades.CAMPO_ID + "=?", parametros33);db.update(Utilidades.TABLA_MATERIA, values34, Utilidades.CAMPO_ID + "=?", parametros34);
        db.update(Utilidades.TABLA_MATERIA, values35, Utilidades.CAMPO_ID + "=?", parametros35);db.update(Utilidades.TABLA_MATERIA, values36, Utilidades.CAMPO_ID + "=?", parametros36);

        db.close();
    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void sumPresJust() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos1", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Num control condicion update
        String[] parametros = {"18131209"};String[] parametros2 = {"16130783"};String[] parametros3 = {"17130576"};String[] parametros4 = {"18130534"};
        String[] parametros5 = {"17130004"};String[] parametros6 = {"17130764"};String[] parametros7 = {"15131296"};String[] parametros8 = {"16131450"};
        String[] parametros9 = {"15450676"};String[] parametros10 = {"17130026"};String[] parametros11 = {"17130028"};String[] parametros12 = {"17130787"};
        String[] parametros13 = {"18130561"};String[] parametros14 = {"16130812"};String[] parametros15 = {"17130043"};String[] parametros16 = {"17131621"};
        String[] parametros17 = {"18130575"};String[] parametros18 = {"17130801"};String[] parametros19 = {"18130576"};String[] parametros20 = {"18130583"};
        String[] parametros21 = {"18130586"};String[] parametros22 = {"18130588"};String[] parametros23 = {"18130592"};String[] parametros24 = {"171000063"};
        String[] parametros25 = {"17130059"};String[] parametros26 = {"18130597"};String[] parametros27 = {"17131623"};String[] parametros28 = {"17130836"};
        String[] parametros29 = {"17130839"};String[] parametros30 = {"17130844"};String[] parametros31 = {"17130848"};String[] parametros32 = {"17130850"};
        String[] parametros33 = {"17130073"};String[] parametros34 = {"17130852"};String[] parametros35 = {"171000302"};String[] parametros36 = {"17130854"};

        //Variables values para el update del campo total
        ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
        ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
        ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
        ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
        ContentValues values22 = new ContentValues();ContentValues values23 = new ContentValues();ContentValues values24 = new ContentValues();
        ContentValues values25 = new ContentValues();ContentValues values26 = new ContentValues();ContentValues values27 = new ContentValues();
        ContentValues values28 = new ContentValues();ContentValues values29 = new ContentValues();ContentValues values30 = new ContentValues();
        ContentValues values31 = new ContentValues();ContentValues values32 = new ContentValues();ContentValues values33 = new ContentValues();
        ContentValues values34 = new ContentValues();ContentValues values35 = new ContentValues();ContentValues values36 = new ContentValues();

        //Consultas total columnas total
        long count = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18131209", null);
        long count2 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=16130783", null);
        long count3 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130576", null);
        long count4 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130534", null);
        long count5 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130004", null);
        long count6 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130764", null);
        long count7 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=15131296", null);
        long count8 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=16131450", null);
        long count9 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=15450676", null);
        long count10 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130026", null);
        long count11 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130028", null);
        long count12 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130787", null);
        long count13 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130561", null);
        long count14 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=16130812", null);
        long count15 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130043", null);
        long count16 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17131621", null);
        long count17 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130575", null);
        long count18 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130801", null);
        long count19 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130576", null);
        long count20 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130583", null);
        long count21 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130586", null);
        long count22 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130588", null);
        long count23 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130592", null);
        long count24 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=171000063", null);
        long count25 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130059", null);
        long count26 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=18130597", null);
        long count27 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17131623", null);
        long count28 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130836", null);
        long count29 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130839", null);
        long count30 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130844", null);
        long count31 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130848", null);
        long count32 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130850", null);
        long count33 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130073", null);
        long count34 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130852", null);
        long count35 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=171000302", null);
        long count36 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM android WHERE id=17130854", null);

        //insercion de los valores del count
        values.put(Utilidades.CAMPO_TOTAL, count);values2.put(Utilidades.CAMPO_TOTAL, count2);values3.put(Utilidades.CAMPO_TOTAL, count3);
        values4.put(Utilidades.CAMPO_TOTAL, count4);values5.put(Utilidades.CAMPO_TOTAL, count5);values6.put(Utilidades.CAMPO_TOTAL, count6);
        values7.put(Utilidades.CAMPO_TOTAL, count7);values8.put(Utilidades.CAMPO_TOTAL, count8);values9.put(Utilidades.CAMPO_TOTAL, count9);
        values10.put(Utilidades.CAMPO_TOTAL, count10);values11.put(Utilidades.CAMPO_TOTAL, count11);values12.put(Utilidades.CAMPO_TOTAL, count12);
        values13.put(Utilidades.CAMPO_TOTAL, count13);values14.put(Utilidades.CAMPO_TOTAL, count14);values15.put(Utilidades.CAMPO_TOTAL, count15);
        values16.put(Utilidades.CAMPO_TOTAL, count16);values17.put(Utilidades.CAMPO_TOTAL, count17);values18.put(Utilidades.CAMPO_TOTAL, count18);
        values19.put(Utilidades.CAMPO_TOTAL, count19);values20.put(Utilidades.CAMPO_TOTAL, count20);values21.put(Utilidades.CAMPO_TOTAL, count21);
        values22.put(Utilidades.CAMPO_TOTAL, count22);values23.put(Utilidades.CAMPO_TOTAL, count23);values24.put(Utilidades.CAMPO_TOTAL, count24);
        values25.put(Utilidades.CAMPO_TOTAL, count25);values26.put(Utilidades.CAMPO_TOTAL, count26);values27.put(Utilidades.CAMPO_TOTAL, count27);
        values28.put(Utilidades.CAMPO_TOTAL, count28);values29.put(Utilidades.CAMPO_TOTAL, count29);values30.put(Utilidades.CAMPO_TOTAL, count30);
        values31.put(Utilidades.CAMPO_TOTAL, count31);values32.put(Utilidades.CAMPO_TOTAL, count32);values33.put(Utilidades.CAMPO_TOTAL, count33);
        values34.put(Utilidades.CAMPO_TOTAL, count34);values35.put(Utilidades.CAMPO_TOTAL, count35);values36.put(Utilidades.CAMPO_TOTAL, count36);

        //update campo total
        db.update(Utilidades.TABLA_MATERIA, values, Utilidades.CAMPO_ID + "=?", parametros);db.update(Utilidades.TABLA_MATERIA, values2, Utilidades.CAMPO_ID + "=?", parametros2);
        db.update(Utilidades.TABLA_MATERIA, values3, Utilidades.CAMPO_ID + "=?", parametros3);db.update(Utilidades.TABLA_MATERIA, values4, Utilidades.CAMPO_ID + "=?", parametros4);
        db.update(Utilidades.TABLA_MATERIA, values5, Utilidades.CAMPO_ID + "=?", parametros5);db.update(Utilidades.TABLA_MATERIA, values6, Utilidades.CAMPO_ID + "=?", parametros6);
        db.update(Utilidades.TABLA_MATERIA, values7, Utilidades.CAMPO_ID + "=?", parametros7);db.update(Utilidades.TABLA_MATERIA, values8, Utilidades.CAMPO_ID + "=?", parametros8);
        db.update(Utilidades.TABLA_MATERIA, values9, Utilidades.CAMPO_ID + "=?", parametros9);db.update(Utilidades.TABLA_MATERIA, values10, Utilidades.CAMPO_ID + "=?", parametros10);
        db.update(Utilidades.TABLA_MATERIA, values11, Utilidades.CAMPO_ID + "=?", parametros11);db.update(Utilidades.TABLA_MATERIA, values12, Utilidades.CAMPO_ID + "=?", parametros12);
        db.update(Utilidades.TABLA_MATERIA, values13, Utilidades.CAMPO_ID + "=?", parametros13);db.update(Utilidades.TABLA_MATERIA, values14, Utilidades.CAMPO_ID + "=?", parametros14);
        db.update(Utilidades.TABLA_MATERIA, values15, Utilidades.CAMPO_ID + "=?", parametros15);db.update(Utilidades.TABLA_MATERIA, values16, Utilidades.CAMPO_ID + "=?", parametros16);
        db.update(Utilidades.TABLA_MATERIA, values17, Utilidades.CAMPO_ID + "=?", parametros17);db.update(Utilidades.TABLA_MATERIA, values18, Utilidades.CAMPO_ID + "=?", parametros18);
        db.update(Utilidades.TABLA_MATERIA, values19, Utilidades.CAMPO_ID + "=?", parametros19);db.update(Utilidades.TABLA_MATERIA, values20, Utilidades.CAMPO_ID + "=?", parametros20);
        db.update(Utilidades.TABLA_MATERIA, values21, Utilidades.CAMPO_ID + "=?", parametros21);db.update(Utilidades.TABLA_MATERIA, values22, Utilidades.CAMPO_ID + "=?", parametros22);
        db.update(Utilidades.TABLA_MATERIA, values23, Utilidades.CAMPO_ID + "=?", parametros23);db.update(Utilidades.TABLA_MATERIA, values24, Utilidades.CAMPO_ID + "=?", parametros24);
        db.update(Utilidades.TABLA_MATERIA, values25, Utilidades.CAMPO_ID + "=?", parametros25);db.update(Utilidades.TABLA_MATERIA, values26, Utilidades.CAMPO_ID + "=?", parametros26);
        db.update(Utilidades.TABLA_MATERIA, values27, Utilidades.CAMPO_ID + "=?", parametros27);db.update(Utilidades.TABLA_MATERIA, values28, Utilidades.CAMPO_ID + "=?", parametros28);
        db.update(Utilidades.TABLA_MATERIA, values29, Utilidades.CAMPO_ID + "=?", parametros29);db.update(Utilidades.TABLA_MATERIA, values30, Utilidades.CAMPO_ID + "=?", parametros30);
        db.update(Utilidades.TABLA_MATERIA, values31, Utilidades.CAMPO_ID + "=?", parametros31);db.update(Utilidades.TABLA_MATERIA, values32, Utilidades.CAMPO_ID + "=?", parametros32);
        db.update(Utilidades.TABLA_MATERIA, values33, Utilidades.CAMPO_ID + "=?", parametros33);db.update(Utilidades.TABLA_MATERIA, values34, Utilidades.CAMPO_ID + "=?", parametros34);
        db.update(Utilidades.TABLA_MATERIA, values35, Utilidades.CAMPO_ID + "=?", parametros35);db.update(Utilidades.TABLA_MATERIA, values36, Utilidades.CAMPO_ID + "=?", parametros36);


        //Variables values para el update del campo porcentaje
        ContentValues valpor = new ContentValues();ContentValues valpor2 = new ContentValues();ContentValues valpor3 = new ContentValues();
        ContentValues valpor4 = new ContentValues();ContentValues valpor5 = new ContentValues();ContentValues valpor6 = new ContentValues();
        ContentValues valpor7 = new ContentValues();ContentValues valpor8 = new ContentValues();ContentValues valpor9 = new ContentValues();
        ContentValues valpor10 = new ContentValues();ContentValues valpor11 = new ContentValues();ContentValues valpor12 = new ContentValues();
        ContentValues valpor13 = new ContentValues();ContentValues valpor14 = new ContentValues();ContentValues valpor15 = new ContentValues();
        ContentValues valpor16 = new ContentValues();ContentValues valpor17 = new ContentValues();ContentValues valpor18 = new ContentValues();
        ContentValues valpor19 = new ContentValues();ContentValues valpor20 = new ContentValues();ContentValues valpor21 = new ContentValues();
        ContentValues valpor22 = new ContentValues();ContentValues valpor23 = new ContentValues();ContentValues valpor24 = new ContentValues();
        ContentValues valpor25 = new ContentValues();ContentValues valpor26 = new ContentValues();ContentValues valpor27 = new ContentValues();
        ContentValues valpor28 = new ContentValues();ContentValues valpor29 = new ContentValues();ContentValues valpor30 = new ContentValues();
        ContentValues valpor31 = new ContentValues();ContentValues valpor32 = new ContentValues();ContentValues valpor33 = new ContentValues();
        ContentValues valpor34 = new ContentValues();ContentValues valpor35 = new ContentValues();ContentValues valpor36 = new ContentValues();

        //operaciones porcentajes

        float por = (float) (100.00 / 51.00 * count);float por2 = (float) (100.00 / 51.00 * count2);float por3 = (float) (100.00 / 51.00 * count3);
        float por4 = (float) (100.00 / 51.00 * count4);float por5 = (float) (100.00 / 51.00 * count5);float por6 = (float) (100.00 / 51.00 * count6);
        float por7 = (float) (100.00 / 51.00 * count7);float por8 = (float) (100.00 / 51.00 * count8);float por9 = (float) (100.00 / 51.00 * count9);
        float por10 = (float) (100.00 / 51.00 * count10);float por11 = (float) (100.00 / 51.00 * count11);float por12 = (float) (100.00 / 51.00 * count12);
        float por13 = (float) (100.00 / 51.00 * count13);float por14 = (float) (100.00 / 51.00 * count14);float por15 = (float) (100.00 / 51.00 * count15);
        float por16 = (float) (100.00 / 51.00 * count16);float por17 = (float) (100.00 / 51.00 * count17);float por18 = (float) (100.00 / 51.00 * count18);
        float por19 = (float) (100.00 / 51.00 * count19);float por20 = (float) (100.00 / 51.00 * count20);float por21 = (float) (100.00 / 51.00 * count21);
        float por22 = (float) (100.00 / 51.00 * count22);float por23 = (float) (100.00 / 51.00 * count23);float por24 = (float) (100.00 / 51.00 * count24);
        float por25 = (float) (100.00 / 51.00 * count25);float por26 = (float) (100.00 / 51.00 * count26);float por27 = (float) (100.00 / 51.00 * count27);
        float por28 = (float) (100.00 / 51.00 * count28);float por29 = (float) (100.00 / 51.00 * count29);float por30 = (float) (100.00 / 51.00 * count30);
        float por31 = (float) (100.00 / 51.00 * count31);float por32 = (float) (100.00 / 51.00 * count32);float por33 = (float) (100.00 / 51.00 * count33);
        float por34 = (float) (100.00 / 51.00 * count34);float por35 = (float) (100.00 / 51.00 * count35);float por36 = (float) (100.00 / 51.00 * count36);

        //insercion de los valores
        valpor.put(Utilidades.CAMPO_PORCENTAJE, por);valpor2.put(Utilidades.CAMPO_PORCENTAJE, por2);valpor3.put(Utilidades.CAMPO_PORCENTAJE, por3);
        valpor4.put(Utilidades.CAMPO_PORCENTAJE, por4);valpor5.put(Utilidades.CAMPO_PORCENTAJE, por5);valpor6.put(Utilidades.CAMPO_PORCENTAJE, por6);
        valpor7.put(Utilidades.CAMPO_PORCENTAJE, por7);valpor8.put(Utilidades.CAMPO_PORCENTAJE, por8);valpor9.put(Utilidades.CAMPO_PORCENTAJE, por9);
        valpor10.put(Utilidades.CAMPO_PORCENTAJE, por10);valpor11.put(Utilidades.CAMPO_PORCENTAJE, por11);valpor12.put(Utilidades.CAMPO_PORCENTAJE, por12);
        valpor13.put(Utilidades.CAMPO_PORCENTAJE, por13);valpor14.put(Utilidades.CAMPO_PORCENTAJE, por14);valpor15.put(Utilidades.CAMPO_PORCENTAJE, por15);
        valpor16.put(Utilidades.CAMPO_PORCENTAJE, por16);valpor17.put(Utilidades.CAMPO_PORCENTAJE, por17);valpor18.put(Utilidades.CAMPO_PORCENTAJE, por18);
        valpor19.put(Utilidades.CAMPO_PORCENTAJE, por19);valpor20.put(Utilidades.CAMPO_PORCENTAJE, por20);valpor21.put(Utilidades.CAMPO_PORCENTAJE, por21);
        valpor22.put(Utilidades.CAMPO_PORCENTAJE, por22);valpor23.put(Utilidades.CAMPO_PORCENTAJE, por23);valpor24.put(Utilidades.CAMPO_PORCENTAJE, por24);
        valpor25.put(Utilidades.CAMPO_PORCENTAJE, por25);valpor26.put(Utilidades.CAMPO_PORCENTAJE, por26);valpor27.put(Utilidades.CAMPO_PORCENTAJE, por27);
        valpor28.put(Utilidades.CAMPO_PORCENTAJE, por28);valpor29.put(Utilidades.CAMPO_PORCENTAJE, por29);valpor30.put(Utilidades.CAMPO_PORCENTAJE, por30);
        valpor31.put(Utilidades.CAMPO_PORCENTAJE, por31);valpor32.put(Utilidades.CAMPO_PORCENTAJE, por32);valpor33.put(Utilidades.CAMPO_PORCENTAJE, por33);
        valpor34.put(Utilidades.CAMPO_PORCENTAJE, por34);valpor35.put(Utilidades.CAMPO_PORCENTAJE, por35);valpor36.put(Utilidades.CAMPO_PORCENTAJE, por36);

        //update
        db.update(Utilidades.TABLA_MATERIA, valpor, Utilidades.CAMPO_ID + "=?", parametros);db.update(Utilidades.TABLA_MATERIA, valpor2, Utilidades.CAMPO_ID + "=?", parametros2);
        db.update(Utilidades.TABLA_MATERIA, valpor3, Utilidades.CAMPO_ID + "=?", parametros3);db.update(Utilidades.TABLA_MATERIA, valpor4, Utilidades.CAMPO_ID + "=?", parametros4);
        db.update(Utilidades.TABLA_MATERIA, valpor5, Utilidades.CAMPO_ID + "=?", parametros5);db.update(Utilidades.TABLA_MATERIA, valpor6, Utilidades.CAMPO_ID + "=?", parametros6);
        db.update(Utilidades.TABLA_MATERIA, valpor7, Utilidades.CAMPO_ID + "=?", parametros7);db.update(Utilidades.TABLA_MATERIA, valpor8, Utilidades.CAMPO_ID + "=?", parametros8);
        db.update(Utilidades.TABLA_MATERIA, valpor9, Utilidades.CAMPO_ID + "=?", parametros9);db.update(Utilidades.TABLA_MATERIA, valpor10, Utilidades.CAMPO_ID + "=?", parametros10);
        db.update(Utilidades.TABLA_MATERIA, valpor11, Utilidades.CAMPO_ID + "=?", parametros11);db.update(Utilidades.TABLA_MATERIA, valpor12, Utilidades.CAMPO_ID + "=?", parametros12);
        db.update(Utilidades.TABLA_MATERIA, valpor13, Utilidades.CAMPO_ID + "=?", parametros13);db.update(Utilidades.TABLA_MATERIA, valpor14, Utilidades.CAMPO_ID + "=?", parametros14);
        db.update(Utilidades.TABLA_MATERIA, valpor15, Utilidades.CAMPO_ID + "=?", parametros15);db.update(Utilidades.TABLA_MATERIA, valpor16, Utilidades.CAMPO_ID + "=?", parametros16);
        db.update(Utilidades.TABLA_MATERIA, valpor17, Utilidades.CAMPO_ID + "=?", parametros17);db.update(Utilidades.TABLA_MATERIA, valpor18, Utilidades.CAMPO_ID + "=?", parametros18);
        db.update(Utilidades.TABLA_MATERIA, valpor19, Utilidades.CAMPO_ID + "=?", parametros19);db.update(Utilidades.TABLA_MATERIA, valpor20, Utilidades.CAMPO_ID + "=?", parametros20);
        db.update(Utilidades.TABLA_MATERIA, valpor21, Utilidades.CAMPO_ID + "=?", parametros21);db.update(Utilidades.TABLA_MATERIA, valpor22, Utilidades.CAMPO_ID + "=?", parametros22);
        db.update(Utilidades.TABLA_MATERIA, valpor23, Utilidades.CAMPO_ID + "=?", parametros23);db.update(Utilidades.TABLA_MATERIA, valpor24, Utilidades.CAMPO_ID + "=?", parametros24);
        db.update(Utilidades.TABLA_MATERIA, valpor25, Utilidades.CAMPO_ID + "=?", parametros25);db.update(Utilidades.TABLA_MATERIA, valpor26, Utilidades.CAMPO_ID + "=?", parametros26);
        db.update(Utilidades.TABLA_MATERIA, valpor27, Utilidades.CAMPO_ID + "=?", parametros27);db.update(Utilidades.TABLA_MATERIA, valpor28, Utilidades.CAMPO_ID + "=?", parametros28);
        db.update(Utilidades.TABLA_MATERIA, valpor29, Utilidades.CAMPO_ID + "=?", parametros29);db.update(Utilidades.TABLA_MATERIA, valpor30, Utilidades.CAMPO_ID + "=?", parametros30);
        db.update(Utilidades.TABLA_MATERIA, valpor31, Utilidades.CAMPO_ID + "=?", parametros31);db.update(Utilidades.TABLA_MATERIA, valpor32, Utilidades.CAMPO_ID + "=?", parametros32);
        db.update(Utilidades.TABLA_MATERIA, valpor33, Utilidades.CAMPO_ID + "=?", parametros33);db.update(Utilidades.TABLA_MATERIA, valpor34, Utilidades.CAMPO_ID + "=?", parametros34);
        db.update(Utilidades.TABLA_MATERIA, valpor35, Utilidades.CAMPO_ID + "=?", parametros35);db.update(Utilidades.TABLA_MATERIA, valpor36, Utilidades.CAMPO_ID + "=?", parametros36);

        db.close();
    }

    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    private void countPresandr2() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos2", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Num control condicion update
        String[] parametros = {"19130882"};String[] parametros2 = {"19130509"};String[] parametros3 = {"18131226"};String[] parametros4 = {"18130543"};
        String[] parametros5 = {"19130515"};String[] parametros6 = {"18131232"};String[] parametros7 = {"19131498"};String[] parametros8 = {"19130517"};
        String[] parametros9 = {"19130906"};String[] parametros10 = {"18131241"};String[] parametros11 = {"19130914"};String[] parametros12 = {"19130916"};
        String[] parametros13 = {"19130923"};String[] parametros14 = {"18131251"};String[] parametros15 = {"18131257"};String[] parametros16 = {"18131266"};
        String[] parametros17 = {"16130762"};String[] parametros18 = {"19130959"};String[] parametros19 = {"19130962"};String[] parametros20 = {"19130559"};
        String[] parametros21 = {"19130963"};String[] parametros22 = {"18131272"};String[] parametros23 = {"16130832"};String[] parametros24 = {"19130967"};
        String[] parametros25 = {"18130598"};String[] parametros26 = {"17130670"};String[] parametros27 = {"18131275"};String[] parametros28 = {"19130566"};
        String[] parametros29 = {"19130576"};String[] parametros30 = {"18130612"};String[] parametros31 = {"17130853"};

        //Variables values para el update del campo presente
        ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
        ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
        ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
        ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
        ContentValues values22 = new ContentValues();ContentValues values23 = new ContentValues();ContentValues values24 = new ContentValues();
        ContentValues values25 = new ContentValues();ContentValues values26 = new ContentValues();ContentValues values27 = new ContentValues();
        ContentValues values28 = new ContentValues();ContentValues values29 = new ContentValues();ContentValues values30 = new ContentValues();
        ContentValues values31 = new ContentValues();

        //Consultas total columnas presente
        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130882%'", null);
        long count2 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130509%'", null);
        long count3 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131226%'", null);
        long count4 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130543%'", null);
        long count5 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130515%'", null);
        long count6 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131232%'", null);
        long count7 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19131498%'", null);
        long count8 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130517%'", null);
        long count9 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130906%'", null);
        long count10 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131241%'", null);
        long count11 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130914%'", null);
        long count12 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130916%'", null);
        long count13 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130923%'", null);
        long count14 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131251%'", null);
        long count15 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131257%'", null);
        long count16 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131266%'", null);
        long count17 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%16130762%'", null);
        long count18 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130959%'", null);
        long count19 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130962%'", null);
        long count20 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130559%'", null);
        long count21 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130963%'", null);
        long count22 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131272%'", null);
        long count23 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%16130832%'", null);
        long count24 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130967%'", null);
        long count25 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130598%'", null);
        long count26 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130670%'", null);
        long count27 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131275%'", null);
        long count28 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130566%'", null);
        long count29 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%19130576%'", null);
        long count30 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130612%'", null);
        long count31 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130853%'", null);

        //insercion de los valores del count
        values.put(Utilidades2.CAMPO_PRESENTE2, count);values2.put(Utilidades2.CAMPO_PRESENTE2, count2);values3.put(Utilidades2.CAMPO_PRESENTE2, count3);
        values4.put(Utilidades2.CAMPO_PRESENTE2, count4);values5.put(Utilidades2.CAMPO_PRESENTE2, count5);values6.put(Utilidades2.CAMPO_PRESENTE2, count6);
        values7.put(Utilidades2.CAMPO_PRESENTE2, count7);values8.put(Utilidades2.CAMPO_PRESENTE2, count8);values9.put(Utilidades2.CAMPO_PRESENTE2, count9);
        values10.put(Utilidades2.CAMPO_PRESENTE2, count10);values11.put(Utilidades2.CAMPO_PRESENTE2, count11);values12.put(Utilidades2.CAMPO_PRESENTE2, count12);
        values13.put(Utilidades2.CAMPO_PRESENTE2, count13);values14.put(Utilidades2.CAMPO_PRESENTE2, count14);values15.put(Utilidades2.CAMPO_PRESENTE2, count15);
        values16.put(Utilidades2.CAMPO_PRESENTE2, count16);values17.put(Utilidades2.CAMPO_PRESENTE2, count17);values18.put(Utilidades2.CAMPO_PRESENTE2, count18);
        values19.put(Utilidades2.CAMPO_PRESENTE2, count19);values20.put(Utilidades2.CAMPO_PRESENTE2, count20);values21.put(Utilidades2.CAMPO_PRESENTE2, count21);
        values22.put(Utilidades2.CAMPO_PRESENTE2, count22);values23.put(Utilidades2.CAMPO_PRESENTE2, count23);values24.put(Utilidades2.CAMPO_PRESENTE2, count24);
        values25.put(Utilidades2.CAMPO_PRESENTE2, count25);values26.put(Utilidades2.CAMPO_PRESENTE2, count26);values27.put(Utilidades2.CAMPO_PRESENTE2, count27);values28.put(Utilidades2.CAMPO_PRESENTE2, count28);
        values29.put(Utilidades2.CAMPO_PRESENTE2, count29);values30.put(Utilidades2.CAMPO_PRESENTE2, count30);values31.put(Utilidades2.CAMPO_PRESENTE2, count31);

        //update campo presente
        db.update(Utilidades2.TABLA_MATERIA2, values, Utilidades2.CAMPO_ID2 + "=?", parametros);db.update(Utilidades2.TABLA_MATERIA2, values2, Utilidades2.CAMPO_ID2 + "=?", parametros2);
        db.update(Utilidades2.TABLA_MATERIA2, values3, Utilidades2.CAMPO_ID2 + "=?", parametros3);db.update(Utilidades2.TABLA_MATERIA2, values4, Utilidades2.CAMPO_ID2 + "=?", parametros4);
        db.update(Utilidades2.TABLA_MATERIA2, values5, Utilidades2.CAMPO_ID2 + "=?", parametros5);db.update(Utilidades2.TABLA_MATERIA2, values6, Utilidades2.CAMPO_ID2 + "=?", parametros6);
        db.update(Utilidades2.TABLA_MATERIA2, values7, Utilidades2.CAMPO_ID2 + "=?", parametros7);db.update(Utilidades2.TABLA_MATERIA2, values8, Utilidades2.CAMPO_ID2 + "=?", parametros8);
        db.update(Utilidades2.TABLA_MATERIA2, values9, Utilidades2.CAMPO_ID2 + "=?", parametros9);db.update(Utilidades2.TABLA_MATERIA2, values10, Utilidades2.CAMPO_ID2 + "=?", parametros10);
        db.update(Utilidades2.TABLA_MATERIA2, values11, Utilidades2.CAMPO_ID2 + "=?", parametros11);db.update(Utilidades2.TABLA_MATERIA2, values12, Utilidades2.CAMPO_ID2 + "=?", parametros12);
        db.update(Utilidades2.TABLA_MATERIA2, values13, Utilidades2.CAMPO_ID2 + "=?", parametros13);db.update(Utilidades2.TABLA_MATERIA2, values14, Utilidades2.CAMPO_ID2 + "=?", parametros14);
        db.update(Utilidades2.TABLA_MATERIA2, values15, Utilidades2.CAMPO_ID2 + "=?", parametros15);db.update(Utilidades2.TABLA_MATERIA2, values16, Utilidades2.CAMPO_ID2 + "=?", parametros16);
        db.update(Utilidades2.TABLA_MATERIA2, values17, Utilidades2.CAMPO_ID2 + "=?", parametros17);db.update(Utilidades2.TABLA_MATERIA2, values18, Utilidades2.CAMPO_ID2 + "=?", parametros18);
        db.update(Utilidades2.TABLA_MATERIA2, values19, Utilidades2.CAMPO_ID2 + "=?", parametros19);db.update(Utilidades2.TABLA_MATERIA2, values20, Utilidades2.CAMPO_ID2 + "=?", parametros20);
        db.update(Utilidades2.TABLA_MATERIA2, values21, Utilidades2.CAMPO_ID2 + "=?", parametros21);db.update(Utilidades2.TABLA_MATERIA2, values22, Utilidades2.CAMPO_ID2 + "=?", parametros22);
        db.update(Utilidades2.TABLA_MATERIA2, values23, Utilidades2.CAMPO_ID2 + "=?", parametros23);db.update(Utilidades2.TABLA_MATERIA2, values24, Utilidades2.CAMPO_ID2 + "=?", parametros24);
        db.update(Utilidades2.TABLA_MATERIA2, values25, Utilidades2.CAMPO_ID2 + "=?", parametros25);db.update(Utilidades2.TABLA_MATERIA2, values26, Utilidades2.CAMPO_ID2 + "=?", parametros26);
        db.update(Utilidades2.TABLA_MATERIA2, values27, Utilidades2.CAMPO_ID2 + "=?", parametros27);db.update(Utilidades2.TABLA_MATERIA2, values28, Utilidades2.CAMPO_ID2 + "=?", parametros28);
        db.update(Utilidades2.TABLA_MATERIA2, values29, Utilidades2.CAMPO_ID2 + "=?", parametros29);db.update(Utilidades2.TABLA_MATERIA2, values30, Utilidades2.CAMPO_ID2 + "=?", parametros30);
        db.update(Utilidades2.TABLA_MATERIA2, values31, Utilidades2.CAMPO_ID2 + "=?", parametros31);

        db.close();

    }

    //---------------------------------------------------------------------------------------------------
    private void countJustandr2() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos2", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Num control condicion update
        String[] parametros = {"19130882"};String[] parametros2 = {"19130509"};String[] parametros3 = {"18131226"};String[] parametros4 = {"18130543"};
        String[] parametros5 = {"19130515"};String[] parametros6 = {"18131232"};String[] parametros7 = {"19131498"};String[] parametros8 = {"19130517"};
        String[] parametros9 = {"19130906"};String[] parametros10 = {"18131241"};String[] parametros11 = {"19130914"};String[] parametros12 = {"19130916"};
        String[] parametros13 = {"19130923"};String[] parametros14 = {"18131251"};String[] parametros15 = {"18131257"};String[] parametros16 = {"18131266"};
        String[] parametros17 = {"16130762"};String[] parametros18 = {"19130959"};String[] parametros19 = {"19130962"};String[] parametros20 = {"19130559"};
        String[] parametros21 = {"19130963"};String[] parametros22 = {"18131272"};String[] parametros23 = {"16130832"};String[] parametros24 = {"19130967"};
        String[] parametros25 = {"18130598"};String[] parametros26 = {"17130670"};String[] parametros27 = {"18131275"};String[] parametros28 = {"19130566"};
        String[] parametros29 = {"19130576"};String[] parametros30 = {"18130612"};String[] parametros31 = {"17130853"};

        //Variables values para el update del campo justificado
        ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
        ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
        ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
        ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
        ContentValues values22 = new ContentValues();ContentValues values23 = new ContentValues();ContentValues values24 = new ContentValues();
        ContentValues values25 = new ContentValues();ContentValues values26 = new ContentValues();ContentValues values27 = new ContentValues();
        ContentValues values28 = new ContentValues();ContentValues values29 = new ContentValues();ContentValues values30 = new ContentValues();
        ContentValues values31 = new ContentValues();

        //Consultas total columnas justificado
        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130882%'", null);
        long count2 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130509%'", null);
        long count3 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131226%'", null);
        long count4 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130543%'", null);
        long count5 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130515%'", null);
        long count6 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131232%'", null);
        long count7 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19131498%'", null);
        long count8 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130517%'", null);
        long count9 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130906%'", null);
        long count10 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131241%'", null);
        long count11 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130914%'", null);
        long count12 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130916%'", null);
        long count13 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130923%'", null);
        long count14 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131251%'", null);
        long count15 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131257%'", null);
        long count16 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131266%'", null);
        long count17 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%16130762%'", null);
        long count18 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130959%'", null);
        long count19 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130962%'", null);
        long count20 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130559%'", null);
        long count21 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130963%'", null);
        long count22 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131272%'", null);
        long count23 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%16130832%'", null);
        long count24 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130967%'", null);
        long count25 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130598%'", null);
        long count26 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130670%'", null);
        long count27 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131275%'", null);
        long count28 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130566%'", null);
        long count29 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%19130576%'", null);
        long count30 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130612%'", null);
        long count31 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM androidfechatopicos WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130853%'", null);

        //insercion de los valores del count
        values.put(Utilidades2.CAMPO_JUSTIFICADO2, count);
        values2.put(Utilidades2.CAMPO_JUSTIFICADO2, count2);
        values3.put(Utilidades2.CAMPO_JUSTIFICADO2, count3);
        values4.put(Utilidades2.CAMPO_JUSTIFICADO2, count4);
        values5.put(Utilidades2.CAMPO_JUSTIFICADO2, count5);
        values6.put(Utilidades2.CAMPO_JUSTIFICADO2, count6);
        values7.put(Utilidades2.CAMPO_JUSTIFICADO2, count7);
        values8.put(Utilidades2.CAMPO_JUSTIFICADO2, count8);
        values9.put(Utilidades2.CAMPO_JUSTIFICADO2, count9);
        values10.put(Utilidades2.CAMPO_JUSTIFICADO2, count10);
        values11.put(Utilidades2.CAMPO_JUSTIFICADO2, count11);
        values12.put(Utilidades2.CAMPO_JUSTIFICADO2, count12);
        values13.put(Utilidades2.CAMPO_JUSTIFICADO2, count13);
        values14.put(Utilidades2.CAMPO_JUSTIFICADO2, count14);
        values15.put(Utilidades2.CAMPO_JUSTIFICADO2, count15);
        values16.put(Utilidades2.CAMPO_JUSTIFICADO2, count16);
        values17.put(Utilidades2.CAMPO_JUSTIFICADO2, count17);
        values18.put(Utilidades2.CAMPO_JUSTIFICADO2, count18);
        values19.put(Utilidades2.CAMPO_JUSTIFICADO2, count19);
        values20.put(Utilidades2.CAMPO_JUSTIFICADO2, count20);
        values21.put(Utilidades2.CAMPO_JUSTIFICADO2, count21);
        values22.put(Utilidades2.CAMPO_JUSTIFICADO2, count22);
        values23.put(Utilidades2.CAMPO_JUSTIFICADO2, count23);
        values24.put(Utilidades2.CAMPO_JUSTIFICADO2, count24);
        values25.put(Utilidades2.CAMPO_JUSTIFICADO2, count25);
        values26.put(Utilidades2.CAMPO_JUSTIFICADO2, count26);
        values27.put(Utilidades2.CAMPO_JUSTIFICADO2, count27);
        values28.put(Utilidades2.CAMPO_JUSTIFICADO2, count28);
        values29.put(Utilidades2.CAMPO_JUSTIFICADO2, count29);
        values30.put(Utilidades2.CAMPO_JUSTIFICADO2, count30);
        values31.put(Utilidades2.CAMPO_JUSTIFICADO2, count31);

        //update
        db.update(Utilidades2.TABLA_MATERIA2, values, Utilidades2.CAMPO_ID2 + "=?", parametros);db.update(Utilidades2.TABLA_MATERIA2, values2, Utilidades2.CAMPO_ID2 + "=?", parametros2);
        db.update(Utilidades2.TABLA_MATERIA2, values3, Utilidades2.CAMPO_ID2 + "=?", parametros3);db.update(Utilidades2.TABLA_MATERIA2, values4, Utilidades2.CAMPO_ID2 + "=?", parametros4);
        db.update(Utilidades2.TABLA_MATERIA2, values5, Utilidades2.CAMPO_ID2 + "=?", parametros5);db.update(Utilidades2.TABLA_MATERIA2, values6, Utilidades2.CAMPO_ID2 + "=?", parametros6);
        db.update(Utilidades2.TABLA_MATERIA2, values7, Utilidades2.CAMPO_ID2 + "=?", parametros7);db.update(Utilidades2.TABLA_MATERIA2, values8, Utilidades2.CAMPO_ID2 + "=?", parametros8);
        db.update(Utilidades2.TABLA_MATERIA2, values9, Utilidades2.CAMPO_ID2 + "=?", parametros9);db.update(Utilidades2.TABLA_MATERIA2, values10, Utilidades2.CAMPO_ID2 + "=?", parametros10);
        db.update(Utilidades2.TABLA_MATERIA2, values11, Utilidades2.CAMPO_ID2 + "=?", parametros11);db.update(Utilidades2.TABLA_MATERIA2, values12, Utilidades2.CAMPO_ID2 + "=?", parametros12);
        db.update(Utilidades2.TABLA_MATERIA2, values13, Utilidades2.CAMPO_ID2 + "=?", parametros13);db.update(Utilidades2.TABLA_MATERIA2, values14, Utilidades2.CAMPO_ID2 + "=?", parametros14);
        db.update(Utilidades2.TABLA_MATERIA2, values15, Utilidades2.CAMPO_ID2 + "=?", parametros15);db.update(Utilidades2.TABLA_MATERIA2, values16, Utilidades2.CAMPO_ID2 + "=?", parametros16);
        db.update(Utilidades2.TABLA_MATERIA2, values17, Utilidades2.CAMPO_ID2 + "=?", parametros17);db.update(Utilidades2.TABLA_MATERIA2, values18, Utilidades2.CAMPO_ID2 + "=?", parametros18);
        db.update(Utilidades2.TABLA_MATERIA2, values19, Utilidades2.CAMPO_ID2 + "=?", parametros19);db.update(Utilidades2.TABLA_MATERIA2, values20, Utilidades2.CAMPO_ID2 + "=?", parametros20);
        db.update(Utilidades2.TABLA_MATERIA2, values21, Utilidades2.CAMPO_ID2 + "=?", parametros21);db.update(Utilidades2.TABLA_MATERIA2, values22, Utilidades2.CAMPO_ID2 + "=?", parametros22);
        db.update(Utilidades2.TABLA_MATERIA2, values23, Utilidades2.CAMPO_ID2 + "=?", parametros23);db.update(Utilidades2.TABLA_MATERIA2, values24, Utilidades2.CAMPO_ID2 + "=?", parametros24);
        db.update(Utilidades2.TABLA_MATERIA2, values25, Utilidades2.CAMPO_ID2 + "=?", parametros25);db.update(Utilidades2.TABLA_MATERIA2, values26, Utilidades2.CAMPO_ID2 + "=?", parametros26);
        db.update(Utilidades2.TABLA_MATERIA2, values27, Utilidades2.CAMPO_ID2 + "=?", parametros27);db.update(Utilidades2.TABLA_MATERIA2, values28, Utilidades2.CAMPO_ID2 + "=?", parametros28);
        db.update(Utilidades2.TABLA_MATERIA2, values29, Utilidades2.CAMPO_ID2 + "=?", parametros29);db.update(Utilidades2.TABLA_MATERIA2, values30, Utilidades2.CAMPO_ID2 + "=?", parametros30);
        db.update(Utilidades2.TABLA_MATERIA2, values31, Utilidades2.CAMPO_ID2 + "=?", parametros31);

        db.close();
    }

    //---------------------------------------------------------------------------------------------------
    private void sumPresJust2() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos2", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Num control condicion update
        String[] parametros = {"19130882"};String[] parametros2 = {"19130509"};String[] parametros3 = {"18131226"};String[] parametros4 = {"18130543"};
        String[] parametros5 = {"19130515"};String[] parametros6 = {"18131232"};String[] parametros7 = {"19131498"};String[] parametros8 = {"19130517"};
        String[] parametros9 = {"19130906"};String[] parametros10 = {"18131241"};String[] parametros11 = {"19130914"};String[] parametros12 = {"19130916"};
        String[] parametros13 = {"19130923"};String[] parametros14 = {"18131251"};String[] parametros15 = {"18131257"};String[] parametros16 = {"18131266"};
        String[] parametros17 = {"16130762"};String[] parametros18 = {"19130959"};String[] parametros19 = {"19130962"};String[] parametros20 = {"19130559"};
        String[] parametros21 = {"19130963"};String[] parametros22 = {"18131272"};String[] parametros23 = {"16130832"};String[] parametros24 = {"19130967"};
        String[] parametros25 = {"18130598"};String[] parametros26 = {"17130670"};String[] parametros27 = {"18131275"};String[] parametros28 = {"19130566"};
        String[] parametros29 = {"19130576"};String[] parametros30 = {"18130612"};String[] parametros31 = {"17130853"};

        //Variables values para el update del campo total
        ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
        ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
        ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
        ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
        ContentValues values22 = new ContentValues();ContentValues values23 = new ContentValues();ContentValues values24 = new ContentValues();
        ContentValues values25 = new ContentValues();ContentValues values26 = new ContentValues();ContentValues values27 = new ContentValues();
        ContentValues values28 = new ContentValues();ContentValues values29 = new ContentValues();ContentValues values30 = new ContentValues();
        ContentValues values31 = new ContentValues();

        //Consultas total columnas total
        long count = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130882", null);
        long count2 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130509", null);
        long count3 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18131226", null);
        long count4 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18130543", null);
        long count5 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130515", null);
        long count6 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18131232", null);
        long count7 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19131498", null);
        long count8 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130517", null);
        long count9 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130906", null);
        long count10 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18131241", null);
        long count11 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130914", null);
        long count12 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130916", null);
        long count13 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130923", null);
        long count14 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18131251", null);
        long count15 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18131257", null);
        long count16 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18131266", null);
        long count17 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=16130762", null);
        long count18 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130959", null);
        long count19 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130962", null);
        long count20 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130559", null);
        long count21 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130963", null);
        long count22 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18131272", null);
        long count23 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=16130832", null);
        long count24 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130967", null);
        long count25 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18130598", null);
        long count26 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=17130670", null);
        long count27 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18131275", null);
        long count28 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130566", null);
        long count29 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=19130576", null);
        long count30 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=18130612", null);
        long count31 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM topicos WHERE id=17130853", null);

        //insercion de los valores del count
        values.put(Utilidades2.CAMPO_TOTAL2, count);values2.put(Utilidades2.CAMPO_TOTAL2, count2);values3.put(Utilidades2.CAMPO_TOTAL2, count3);
        values4.put(Utilidades2.CAMPO_TOTAL2, count4);values5.put(Utilidades2.CAMPO_TOTAL2, count5);values6.put(Utilidades2.CAMPO_TOTAL2, count6);
        values7.put(Utilidades2.CAMPO_TOTAL2, count7);values8.put(Utilidades2.CAMPO_TOTAL2, count8);values9.put(Utilidades2.CAMPO_TOTAL2, count9);
        values10.put(Utilidades2.CAMPO_TOTAL2, count10);values11.put(Utilidades2.CAMPO_TOTAL2, count11);values12.put(Utilidades2.CAMPO_TOTAL2, count12);
        values13.put(Utilidades2.CAMPO_TOTAL2, count13);values14.put(Utilidades2.CAMPO_TOTAL2, count14);values15.put(Utilidades2.CAMPO_TOTAL2, count15);
        values16.put(Utilidades2.CAMPO_TOTAL2, count16);values17.put(Utilidades2.CAMPO_TOTAL2, count17);values18.put(Utilidades2.CAMPO_TOTAL2, count18);
        values19.put(Utilidades2.CAMPO_TOTAL2, count19);values20.put(Utilidades2.CAMPO_TOTAL2, count20);values21.put(Utilidades2.CAMPO_TOTAL2, count21);
        values22.put(Utilidades2.CAMPO_TOTAL2, count22);values23.put(Utilidades2.CAMPO_TOTAL2, count23);values24.put(Utilidades2.CAMPO_TOTAL2, count24);
        values25.put(Utilidades2.CAMPO_TOTAL2, count25);values26.put(Utilidades2.CAMPO_TOTAL2, count26);values27.put(Utilidades2.CAMPO_TOTAL2, count27);
        values28.put(Utilidades2.CAMPO_TOTAL2, count28);values29.put(Utilidades2.CAMPO_TOTAL2, count29);values30.put(Utilidades2.CAMPO_TOTAL2, count30);
        values31.put(Utilidades2.CAMPO_TOTAL2, count31);

        //update campo total
        db.update(Utilidades2.TABLA_MATERIA2, values, Utilidades2.CAMPO_ID2 + "=?", parametros);db.update(Utilidades2.TABLA_MATERIA2, values2, Utilidades2.CAMPO_ID2 + "=?", parametros2);
        db.update(Utilidades2.TABLA_MATERIA2, values3, Utilidades2.CAMPO_ID2 + "=?", parametros3);db.update(Utilidades2.TABLA_MATERIA2, values4, Utilidades2.CAMPO_ID2 + "=?", parametros4);
        db.update(Utilidades2.TABLA_MATERIA2, values5, Utilidades2.CAMPO_ID2 + "=?", parametros5);db.update(Utilidades2.TABLA_MATERIA2, values6, Utilidades2.CAMPO_ID2 + "=?", parametros6);
        db.update(Utilidades2.TABLA_MATERIA2, values7, Utilidades2.CAMPO_ID2 + "=?", parametros7);db.update(Utilidades2.TABLA_MATERIA2, values8, Utilidades2.CAMPO_ID2 + "=?", parametros8);
        db.update(Utilidades2.TABLA_MATERIA2, values9, Utilidades2.CAMPO_ID2 + "=?", parametros9);db.update(Utilidades2.TABLA_MATERIA2, values10, Utilidades2.CAMPO_ID2 + "=?", parametros10);
        db.update(Utilidades2.TABLA_MATERIA2, values11, Utilidades2.CAMPO_ID2 + "=?", parametros11);db.update(Utilidades2.TABLA_MATERIA2, values12, Utilidades2.CAMPO_ID2 + "=?", parametros12);
        db.update(Utilidades2.TABLA_MATERIA2, values13, Utilidades2.CAMPO_ID2 + "=?", parametros13);db.update(Utilidades2.TABLA_MATERIA2, values14, Utilidades2.CAMPO_ID2 + "=?", parametros14);
        db.update(Utilidades2.TABLA_MATERIA2, values15, Utilidades2.CAMPO_ID2 + "=?", parametros15);db.update(Utilidades2.TABLA_MATERIA2, values16, Utilidades2.CAMPO_ID2 + "=?", parametros16);
        db.update(Utilidades2.TABLA_MATERIA2, values17, Utilidades2.CAMPO_ID2 + "=?", parametros17);db.update(Utilidades2.TABLA_MATERIA2, values18, Utilidades2.CAMPO_ID2 + "=?", parametros18);
        db.update(Utilidades2.TABLA_MATERIA2, values19, Utilidades2.CAMPO_ID2 + "=?", parametros19);db.update(Utilidades2.TABLA_MATERIA2, values20, Utilidades2.CAMPO_ID2 + "=?", parametros20);
        db.update(Utilidades2.TABLA_MATERIA2, values21, Utilidades2.CAMPO_ID2 + "=?", parametros21);db.update(Utilidades2.TABLA_MATERIA2, values22, Utilidades2.CAMPO_ID2 + "=?", parametros22);
        db.update(Utilidades2.TABLA_MATERIA2, values23, Utilidades2.CAMPO_ID2 + "=?", parametros23);db.update(Utilidades2.TABLA_MATERIA2, values24, Utilidades2.CAMPO_ID2 + "=?", parametros24);
        db.update(Utilidades2.TABLA_MATERIA2, values25, Utilidades2.CAMPO_ID2 + "=?", parametros25);db.update(Utilidades2.TABLA_MATERIA2, values26, Utilidades2.CAMPO_ID2 + "=?", parametros26);
        db.update(Utilidades2.TABLA_MATERIA2, values27, Utilidades2.CAMPO_ID2 + "=?", parametros27);db.update(Utilidades2.TABLA_MATERIA2, values28, Utilidades2.CAMPO_ID2 + "=?", parametros28);
        db.update(Utilidades2.TABLA_MATERIA2, values29, Utilidades2.CAMPO_ID2 + "=?", parametros29);db.update(Utilidades2.TABLA_MATERIA2, values30, Utilidades2.CAMPO_ID2 + "=?", parametros30);
        db.update(Utilidades2.TABLA_MATERIA2, values31, Utilidades2.CAMPO_ID2 + "=?", parametros31);

        //Variables values para el update del campo porcentaje
        ContentValues valpor = new ContentValues();ContentValues valpor2 = new ContentValues();ContentValues valpor3 = new ContentValues();
        ContentValues valpor4 = new ContentValues();ContentValues valpor5 = new ContentValues();ContentValues valpor6 = new ContentValues();
        ContentValues valpor7 = new ContentValues();ContentValues valpor8 = new ContentValues();ContentValues valpor9 = new ContentValues();
        ContentValues valpor10 = new ContentValues();ContentValues valpor11 = new ContentValues();ContentValues valpor12 = new ContentValues();
        ContentValues valpor13 = new ContentValues();ContentValues valpor14 = new ContentValues();ContentValues valpor15 = new ContentValues();
        ContentValues valpor16 = new ContentValues();ContentValues valpor17 = new ContentValues();ContentValues valpor18 = new ContentValues();
        ContentValues valpor19 = new ContentValues();ContentValues valpor20 = new ContentValues();ContentValues valpor21 = new ContentValues();
        ContentValues valpor22 = new ContentValues();ContentValues valpor23 = new ContentValues();ContentValues valpor24 = new ContentValues();
        ContentValues valpor25 = new ContentValues();ContentValues valpor26 = new ContentValues();ContentValues valpor27 = new ContentValues();
        ContentValues valpor28 = new ContentValues();ContentValues valpor29 = new ContentValues();ContentValues valpor30 = new ContentValues();
        ContentValues valpor31 = new ContentValues();

        //operaciones porcentaje
        float por = (float) (100.00 / 52.00 * count);float por2 = (float) (100.00 / 52.00 * count2);float por3 = (float) (100.00 / 52.00 * count3);
        float por4 = (float) (100.00 / 52.00 * count4);float por5 = (float) (100.00 / 52.00 * count5);float por6 = (float) (100.00 / 52.00 * count6);
        float por7 = (float) (100.00 / 52.00 * count7);float por8 = (float) (100.00 / 52.00 * count8);float por9 = (float) (100.00 / 52.00 * count9);
        float por10 = (float) (100.00 / 52.00 * count10);float por11 = (float) (100.00 / 52.00 * count11);float por12 = (float) (100.00 / 52.00 * count12);
        float por13 = (float) (100.00 / 52.00 * count13);float por14 = (float) (100.00 / 52.00 * count14);float por15 = (float) (100.00 / 52.00 * count15);
        float por16 = (float) (100.00 / 52.00 * count16);float por17 = (float) (100.00 / 52.00 * count17);float por18 = (float) (100.00 / 52.00 * count18);
        float por19 = (float) (100.00 / 52.00 * count19);float por20 = (float) (100.00 / 52.00 * count20);float por21 = (float) (100.00 / 52.00 * count21);
        float por22 = (float) (100.00 / 52.00 * count22);float por23 = (float) (100.00 / 52.00 * count23);float por24 = (float) (100.00 / 52.00 * count24);
        float por25 = (float) (100.00 / 52.00 * count25);float por26 = (float) (100.00 / 52.00 * count26);float por27 = (float) (100.00 / 52.00 * count27);
        float por28 = (float) (100.00 / 52.00 * count28);float por29 = (float) (100.00 / 52.00 * count29);float por30 = (float) (100.00 / 52.00 * count30);
        float por31 = (float) (100.00 / 52.00 * count31);

        //insercion de los valores
        valpor.put(Utilidades2.CAMPO_PORCENTAJE2, por);valpor2.put(Utilidades2.CAMPO_PORCENTAJE2, por2);valpor3.put(Utilidades2.CAMPO_PORCENTAJE2, por3);
        valpor4.put(Utilidades2.CAMPO_PORCENTAJE2, por4);valpor5.put(Utilidades2.CAMPO_PORCENTAJE2, por5);valpor6.put(Utilidades2.CAMPO_PORCENTAJE2, por6);valpor7.put(Utilidades2.CAMPO_PORCENTAJE2, por7);
        valpor8.put(Utilidades2.CAMPO_PORCENTAJE2, por8);valpor9.put(Utilidades2.CAMPO_PORCENTAJE2, por9);valpor10.put(Utilidades2.CAMPO_PORCENTAJE2, por10);valpor11.put(Utilidades2.CAMPO_PORCENTAJE2, por11);
        valpor12.put(Utilidades2.CAMPO_PORCENTAJE2, por12);valpor13.put(Utilidades2.CAMPO_PORCENTAJE2, por13);valpor14.put(Utilidades2.CAMPO_PORCENTAJE2, por14);valpor15.put(Utilidades2.CAMPO_PORCENTAJE2, por15);
        valpor16.put(Utilidades2.CAMPO_PORCENTAJE2, por16);valpor17.put(Utilidades2.CAMPO_PORCENTAJE2, por17);valpor18.put(Utilidades2.CAMPO_PORCENTAJE2, por18);valpor19.put(Utilidades2.CAMPO_PORCENTAJE2, por19);
        valpor20.put(Utilidades2.CAMPO_PORCENTAJE2, por20);valpor21.put(Utilidades2.CAMPO_PORCENTAJE2, por21);valpor22.put(Utilidades2.CAMPO_PORCENTAJE2, por22);valpor23.put(Utilidades2.CAMPO_PORCENTAJE2, por23);
        valpor24.put(Utilidades2.CAMPO_PORCENTAJE2, por24);valpor25.put(Utilidades2.CAMPO_PORCENTAJE2, por25);valpor26.put(Utilidades2.CAMPO_PORCENTAJE2, por26);valpor27.put(Utilidades2.CAMPO_PORCENTAJE2, por27);
        valpor28.put(Utilidades2.CAMPO_PORCENTAJE2, por28);valpor29.put(Utilidades2.CAMPO_PORCENTAJE2, por29);valpor30.put(Utilidades2.CAMPO_PORCENTAJE2, por30);valpor31.put(Utilidades2.CAMPO_PORCENTAJE2, por31);

        //update
        db.update(Utilidades2.TABLA_MATERIA2, valpor, Utilidades2.CAMPO_ID2 + "=?", parametros);db.update(Utilidades2.TABLA_MATERIA2, valpor2, Utilidades2.CAMPO_ID2 + "=?", parametros2);
        db.update(Utilidades2.TABLA_MATERIA2, valpor3, Utilidades2.CAMPO_ID2 + "=?", parametros3);db.update(Utilidades2.TABLA_MATERIA2, valpor4, Utilidades2.CAMPO_ID2 + "=?", parametros4);
        db.update(Utilidades2.TABLA_MATERIA2, valpor5, Utilidades2.CAMPO_ID2 + "=?", parametros5);db.update(Utilidades2.TABLA_MATERIA2, valpor6, Utilidades2.CAMPO_ID2 + "=?", parametros6);
        db.update(Utilidades2.TABLA_MATERIA2, valpor7, Utilidades2.CAMPO_ID2 + "=?", parametros7);db.update(Utilidades2.TABLA_MATERIA2, valpor8, Utilidades2.CAMPO_ID2 + "=?", parametros8);
        db.update(Utilidades2.TABLA_MATERIA2, valpor9, Utilidades2.CAMPO_ID2 + "=?", parametros9);db.update(Utilidades2.TABLA_MATERIA2, valpor10, Utilidades2.CAMPO_ID2 + "=?", parametros10);
        db.update(Utilidades2.TABLA_MATERIA2, valpor11, Utilidades2.CAMPO_ID2 + "=?", parametros11);db.update(Utilidades2.TABLA_MATERIA2, valpor12, Utilidades2.CAMPO_ID2 + "=?", parametros12);
        db.update(Utilidades2.TABLA_MATERIA2, valpor13, Utilidades2.CAMPO_ID2 + "=?", parametros13);db.update(Utilidades2.TABLA_MATERIA2, valpor14, Utilidades2.CAMPO_ID2 + "=?", parametros14);
        db.update(Utilidades2.TABLA_MATERIA2, valpor15, Utilidades2.CAMPO_ID2 + "=?", parametros15);db.update(Utilidades2.TABLA_MATERIA2, valpor16, Utilidades2.CAMPO_ID2 + "=?", parametros16);
        db.update(Utilidades2.TABLA_MATERIA2, valpor17, Utilidades2.CAMPO_ID2 + "=?", parametros17);db.update(Utilidades2.TABLA_MATERIA2, valpor18, Utilidades2.CAMPO_ID2 + "=?", parametros18);
        db.update(Utilidades2.TABLA_MATERIA2, valpor19, Utilidades2.CAMPO_ID2 + "=?", parametros19);db.update(Utilidades2.TABLA_MATERIA2, valpor20, Utilidades2.CAMPO_ID2 + "=?", parametros20);
        db.update(Utilidades2.TABLA_MATERIA2, valpor21, Utilidades2.CAMPO_ID2 + "=?", parametros21);db.update(Utilidades2.TABLA_MATERIA2, valpor22, Utilidades2.CAMPO_ID2 + "=?", parametros22);
        db.update(Utilidades2.TABLA_MATERIA2, valpor23, Utilidades2.CAMPO_ID2 + "=?", parametros23);db.update(Utilidades2.TABLA_MATERIA2, valpor24, Utilidades2.CAMPO_ID2 + "=?", parametros24);
        db.update(Utilidades2.TABLA_MATERIA2, valpor25, Utilidades2.CAMPO_ID2 + "=?", parametros25);db.update(Utilidades2.TABLA_MATERIA2, valpor26, Utilidades2.CAMPO_ID2 + "=?", parametros26);
        db.update(Utilidades2.TABLA_MATERIA2, valpor27, Utilidades2.CAMPO_ID2 + "=?", parametros27);db.update(Utilidades2.TABLA_MATERIA2, valpor28, Utilidades2.CAMPO_ID2 + "=?", parametros28);
        db.update(Utilidades2.TABLA_MATERIA2, valpor29, Utilidades2.CAMPO_ID2 + "=?", parametros29);db.update(Utilidades2.TABLA_MATERIA2, valpor30, Utilidades2.CAMPO_ID2 + "=?", parametros30);
        db.update(Utilidades2.TABLA_MATERIA2, valpor31, Utilidades2.CAMPO_ID2 + "=?", parametros31);

        db.close();
    }

     //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
     private void countPresandr3() {
         ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos3", null, 1);
         SQLiteDatabase db = conn.getWritableDatabase();
         //Num control condicion update
         String[] parametros = {"18131209"};String[] parametros2 = {"18130533"};String[] parametros3 = {"17131613"};String[] parametros4 = {"17130764"};
         String[] parametros5 = {"18131221"};String[] parametros6 = {"17130014"};String[] parametros7 = {"18131227"};String[] parametros8 = {"17130772"};
         String[] parametros9 = {"17130787"};String[] parametros10 = {"17130029"};String[] parametros11 = {"17130033"};String[] parametros12 = {"17131621"};
         String[] parametros13 = {"17130806"};String[] parametros14 = {"17130807"};String[] parametros15 = {"18130578"};String[] parametros16 = {"18131263"};
         String[] parametros17 = {"18130588"};String[] parametros18 = {"18130594"};String[] parametros19 = {"17130059"};String[] parametros20 = {"17130845"};
         String[] parametros21 = {"17130855"};String[] parametros22 = {"18130615"};

         //Variables values para el update del campo presente
         ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
         ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
         ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
         ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
         ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
         ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
         ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
         ContentValues values22 = new ContentValues();

         //Consultas total columnas presente
         long count = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131209%'", null);
         long count2 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130533%'", null);
         long count3 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17131613%'", null);
         long count4 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130764%'", null);
         long count5 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131221%'", null);
         long count6 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130014%'", null);
         long count7 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131227%'", null);
         long count8 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130772%'", null);
         long count9 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130787%'", null);
         long count10 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130029%'", null);
         long count11 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130033%'", null);
         long count12 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17131621%'", null);
         long count13 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130806%'", null);
         long count14 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130807%'", null);
         long count15 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130578%'", null);
         long count16 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18131263%'", null);
         long count17 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130588%'", null);
         long count18 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130594%'", null);
         long count19 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130059%'", null);
         long count20 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130845%'", null);
         long count21 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%17130855%'", null);
         long count22 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%PRES%' AND status LIKE '%Pres%' AND status LIKE '%pres%' AND statusid LIKE '%18130615%'", null);

         //insercion de los valores del count
         values.put(Utilidades3.CAMPO_PRESENTE, count);values2.put(Utilidades3.CAMPO_PRESENTE, count2);values3.put(Utilidades3.CAMPO_PRESENTE, count3);
         values4.put(Utilidades3.CAMPO_PRESENTE, count4);values5.put(Utilidades3.CAMPO_PRESENTE, count5);values6.put(Utilidades3.CAMPO_PRESENTE, count6);
         values7.put(Utilidades3.CAMPO_PRESENTE, count7);values8.put(Utilidades3.CAMPO_PRESENTE, count8);values9.put(Utilidades3.CAMPO_PRESENTE, count9);
         values10.put(Utilidades3.CAMPO_PRESENTE, count10);values11.put(Utilidades3.CAMPO_PRESENTE, count11);values12.put(Utilidades3.CAMPO_PRESENTE, count12);
         values13.put(Utilidades3.CAMPO_PRESENTE, count13);values14.put(Utilidades3.CAMPO_PRESENTE, count14);values15.put(Utilidades3.CAMPO_PRESENTE, count15);
         values16.put(Utilidades3.CAMPO_PRESENTE, count16);values17.put(Utilidades3.CAMPO_PRESENTE, count17);values18.put(Utilidades3.CAMPO_PRESENTE, count18);
         values19.put(Utilidades3.CAMPO_PRESENTE, count19);values20.put(Utilidades3.CAMPO_PRESENTE, count20);values21.put(Utilidades3.CAMPO_PRESENTE, count21);
         values22.put(Utilidades3.CAMPO_PRESENTE, count22);

         //update campo presente
         db.update(Utilidades3.TABLA_MATERIA, values, Utilidades3.CAMPO_ID + "=?", parametros);db.update(Utilidades3.TABLA_MATERIA, values2, Utilidades3.CAMPO_ID + "=?", parametros2);
         db.update(Utilidades3.TABLA_MATERIA, values3, Utilidades3.CAMPO_ID + "=?", parametros3);db.update(Utilidades3.TABLA_MATERIA, values4, Utilidades3.CAMPO_ID + "=?", parametros4);
         db.update(Utilidades3.TABLA_MATERIA, values5, Utilidades3.CAMPO_ID + "=?", parametros5);db.update(Utilidades3.TABLA_MATERIA, values6, Utilidades3.CAMPO_ID + "=?", parametros6);
         db.update(Utilidades3.TABLA_MATERIA, values7, Utilidades3.CAMPO_ID + "=?", parametros7);db.update(Utilidades3.TABLA_MATERIA, values8, Utilidades3.CAMPO_ID + "=?", parametros8);
         db.update(Utilidades3.TABLA_MATERIA, values9, Utilidades3.CAMPO_ID + "=?", parametros9);db.update(Utilidades3.TABLA_MATERIA, values10, Utilidades3.CAMPO_ID + "=?", parametros10);
         db.update(Utilidades3.TABLA_MATERIA, values11, Utilidades3.CAMPO_ID + "=?", parametros11);db.update(Utilidades3.TABLA_MATERIA, values12, Utilidades3.CAMPO_ID + "=?", parametros12);
         db.update(Utilidades3.TABLA_MATERIA, values13, Utilidades3.CAMPO_ID + "=?", parametros13);db.update(Utilidades3.TABLA_MATERIA, values14, Utilidades3.CAMPO_ID + "=?", parametros14);
         db.update(Utilidades3.TABLA_MATERIA, values15, Utilidades3.CAMPO_ID + "=?", parametros15);db.update(Utilidades3.TABLA_MATERIA, values16, Utilidades3.CAMPO_ID + "=?", parametros16);
         db.update(Utilidades3.TABLA_MATERIA, values17, Utilidades3.CAMPO_ID + "=?", parametros17);db.update(Utilidades3.TABLA_MATERIA, values18, Utilidades3.CAMPO_ID + "=?", parametros18);
         db.update(Utilidades3.TABLA_MATERIA, values19, Utilidades3.CAMPO_ID + "=?", parametros19);db.update(Utilidades3.TABLA_MATERIA, values20, Utilidades3.CAMPO_ID + "=?", parametros20);
         db.update(Utilidades3.TABLA_MATERIA, values21, Utilidades3.CAMPO_ID + "=?", parametros21);db.update(Utilidades3.TABLA_MATERIA, values22, Utilidades3.CAMPO_ID + "=?", parametros22);

         db.close();

     }

    //---------------------------------------------------------------------------------------------------
    private void countJustandr3() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos3", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Num control condicion update
        String[] parametros = {"18131209"};String[] parametros2 = {"18130533"};String[] parametros3 = {"17131613"};String[] parametros4 = {"17130764"};
        String[] parametros5 = {"18131221"};String[] parametros6 = {"17130014"};String[] parametros7 = {"18131227"};String[] parametros8 = {"17130772"};
        String[] parametros9 = {"17130787"};String[] parametros10 = {"17130029"};String[] parametros11 = {"17130033"};String[] parametros12 = {"17131621"};
        String[] parametros13 = {"17130806"};String[] parametros14 = {"17130807"};String[] parametros15 = {"18130578"};String[] parametros16 = {"18131263"};
        String[] parametros17 = {"18130588"};String[] parametros18 = {"18130594"};String[] parametros19 = {"17130059"};String[] parametros20 = {"17130845"};
        String[] parametros21 = {"17130855"};String[] parametros22 = {"18130615"};

        //Variables values para el update del campo justificado
        ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
        ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
        ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
        ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
        ContentValues values22 = new ContentValues();

        //Consultas total columnas justificado
        long count = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131209%'", null);
        long count2 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130533%'", null);
        long count3 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17131613%'", null);
        long count4 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130764%'", null);
        long count5 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131221%'", null);
        long count6 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130014%'", null);
        long count7 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131227%'", null);
        long count8 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130772%'", null);
        long count9 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130787%'", null);
        long count10 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130029%'", null);
        long count11 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130033%'", null);
        long count12 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17131621%'", null);
        long count13 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130806%'", null);
        long count14 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130807%'", null);
        long count15 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130578%'", null);
        long count16 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18131263%'", null);
        long count17 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130588%'", null);
        long count18 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130594%'", null);
        long count19 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130059%'", null);
        long count20 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130845%'", null);
        long count21 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%17130855%'", null);
        long count22 = DatabaseUtils.longForQuery(db, "SELECT COUNT(status) FROM automatasfecha WHERE status LIKE '%JUST%' AND status LIKE '%Just%' AND status LIKE '%just%' AND statusid LIKE '%18130615%'", null);

        //insercion de los valores del count
        values.put(Utilidades3.CAMPO_JUSTIFICADO, count);values2.put(Utilidades3.CAMPO_JUSTIFICADO, count2);values3.put(Utilidades3.CAMPO_JUSTIFICADO, count3);
        values4.put(Utilidades3.CAMPO_JUSTIFICADO, count4);values5.put(Utilidades3.CAMPO_JUSTIFICADO, count5);values6.put(Utilidades3.CAMPO_JUSTIFICADO, count6);
        values7.put(Utilidades3.CAMPO_JUSTIFICADO, count7);values8.put(Utilidades3.CAMPO_JUSTIFICADO, count8);values9.put(Utilidades3.CAMPO_JUSTIFICADO, count9);
        values10.put(Utilidades3.CAMPO_JUSTIFICADO, count10);values11.put(Utilidades3.CAMPO_JUSTIFICADO, count11);values12.put(Utilidades3.CAMPO_JUSTIFICADO, count12);
        values13.put(Utilidades3.CAMPO_JUSTIFICADO, count13);values14.put(Utilidades3.CAMPO_JUSTIFICADO, count14);values15.put(Utilidades3.CAMPO_JUSTIFICADO, count15);
        values16.put(Utilidades3.CAMPO_JUSTIFICADO, count16);values17.put(Utilidades3.CAMPO_JUSTIFICADO, count17);values18.put(Utilidades3.CAMPO_JUSTIFICADO, count18);
        values19.put(Utilidades3.CAMPO_JUSTIFICADO, count19);values20.put(Utilidades3.CAMPO_JUSTIFICADO, count20);values21.put(Utilidades3.CAMPO_JUSTIFICADO, count21);
        values22.put(Utilidades3.CAMPO_JUSTIFICADO, count22);

        //update campo justificado
        db.update(Utilidades3.TABLA_MATERIA, values, Utilidades3.CAMPO_ID + "=?", parametros);db.update(Utilidades3.TABLA_MATERIA, values2, Utilidades3.CAMPO_ID + "=?", parametros2);
        db.update(Utilidades3.TABLA_MATERIA, values3, Utilidades3.CAMPO_ID + "=?", parametros3);db.update(Utilidades3.TABLA_MATERIA, values4, Utilidades3.CAMPO_ID + "=?", parametros4);
        db.update(Utilidades3.TABLA_MATERIA, values5, Utilidades3.CAMPO_ID + "=?", parametros5);db.update(Utilidades3.TABLA_MATERIA, values6, Utilidades3.CAMPO_ID + "=?", parametros6);
        db.update(Utilidades3.TABLA_MATERIA, values7, Utilidades3.CAMPO_ID + "=?", parametros7);db.update(Utilidades3.TABLA_MATERIA, values8, Utilidades3.CAMPO_ID + "=?", parametros8);
        db.update(Utilidades3.TABLA_MATERIA, values9, Utilidades3.CAMPO_ID + "=?", parametros9);db.update(Utilidades3.TABLA_MATERIA, values10, Utilidades3.CAMPO_ID + "=?", parametros10);
        db.update(Utilidades3.TABLA_MATERIA, values11, Utilidades3.CAMPO_ID + "=?", parametros11);db.update(Utilidades3.TABLA_MATERIA, values12, Utilidades3.CAMPO_ID + "=?", parametros12);
        db.update(Utilidades3.TABLA_MATERIA, values13, Utilidades3.CAMPO_ID + "=?", parametros13);db.update(Utilidades3.TABLA_MATERIA, values14, Utilidades3.CAMPO_ID + "=?", parametros14);
        db.update(Utilidades3.TABLA_MATERIA, values15, Utilidades3.CAMPO_ID + "=?", parametros15);db.update(Utilidades3.TABLA_MATERIA, values16, Utilidades3.CAMPO_ID + "=?", parametros16);
        db.update(Utilidades3.TABLA_MATERIA, values17, Utilidades3.CAMPO_ID + "=?", parametros17);db.update(Utilidades3.TABLA_MATERIA, values18, Utilidades3.CAMPO_ID + "=?", parametros18);
        db.update(Utilidades3.TABLA_MATERIA, values19, Utilidades3.CAMPO_ID + "=?", parametros19);db.update(Utilidades3.TABLA_MATERIA, values20, Utilidades3.CAMPO_ID + "=?", parametros20);
        db.update(Utilidades3.TABLA_MATERIA, values21, Utilidades3.CAMPO_ID + "=?", parametros21);db.update(Utilidades3.TABLA_MATERIA, values22, Utilidades3.CAMPO_ID + "=?", parametros22);

        db.close();
    }

    //---------------------------------------------------------------------------------------------------
    private void sumPresJust3() {
        ConexionSQLiteHelper conn = new ConexionSQLiteHelper(this, "bd_alumnos3", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();
        //Num control condicion update
        String[] parametros = {"18131209"};String[] parametros2 = {"18130533"};String[] parametros3 = {"17131613"};String[] parametros4 = {"17130764"};
        String[] parametros5 = {"18131221"};String[] parametros6 = {"17130014"};String[] parametros7 = {"18131227"};String[] parametros8 = {"17130772"};
        String[] parametros9 = {"17130787"};String[] parametros10 = {"17130029"};String[] parametros11 = {"17130033"};String[] parametros12 = {"17131621"};
        String[] parametros13 = {"17130806"};String[] parametros14 = {"17130807"};String[] parametros15 = {"18130578"};String[] parametros16 = {"18131263"};
        String[] parametros17 = {"18130588"};String[] parametros18 = {"18130594"};String[] parametros19 = {"17130059"};String[] parametros20 = {"17130845"};
        String[] parametros21 = {"17130855"};String[] parametros22 = {"18130615"};

        //Variables values para el update del campo total
        ContentValues values = new ContentValues();ContentValues values2 = new ContentValues();ContentValues values3 = new ContentValues();
        ContentValues values4 = new ContentValues();ContentValues values5 = new ContentValues();ContentValues values6 = new ContentValues();
        ContentValues values7 = new ContentValues();ContentValues values8 = new ContentValues();ContentValues values9 = new ContentValues();
        ContentValues values10 = new ContentValues();ContentValues values11 = new ContentValues();ContentValues values12 = new ContentValues();
        ContentValues values13 = new ContentValues();ContentValues values14 = new ContentValues();ContentValues values15 = new ContentValues();
        ContentValues values16 = new ContentValues();ContentValues values17 = new ContentValues();ContentValues values18 = new ContentValues();
        ContentValues values19 = new ContentValues();ContentValues values20 = new ContentValues();ContentValues values21 = new ContentValues();
        ContentValues values22 = new ContentValues();

        //Consultas total columnas total
        long count = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18131209", null);
        long count2 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18130533", null);
        long count3 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17131613", null);
        long count4 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130764", null);
        long count5 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18131221", null);
        long count6 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130014", null);
        long count7 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18131227", null);
        long count8 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130772", null);
        long count9 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130787", null);
        long count10 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130029", null);
        long count11 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130033", null);
        long count12 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17131621", null);
        long count13 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130806", null);
        long count14 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130807", null);
        long count15 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18130578", null);
        long count16 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18131263", null);
        long count17 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18130588", null);
        long count18 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18130594", null);
        long count19 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130059", null);
        long count20 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130845", null);
        long count21 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=17130855", null);
        long count22 = DatabaseUtils.longForQuery(db, "SELECT SUM(presente + justificado) FROM la2 WHERE id=18130615", null);


        //insercion de los valores del count
        values.put(Utilidades3.CAMPO_TOTAL, count);values2.put(Utilidades3.CAMPO_TOTAL, count2);values3.put(Utilidades3.CAMPO_TOTAL, count3);
        values4.put(Utilidades3.CAMPO_TOTAL, count4);values5.put(Utilidades3.CAMPO_TOTAL, count5);values6.put(Utilidades3.CAMPO_TOTAL, count6);
        values7.put(Utilidades3.CAMPO_TOTAL, count7);values8.put(Utilidades3.CAMPO_TOTAL, count8);values9.put(Utilidades3.CAMPO_TOTAL, count9);
        values10.put(Utilidades3.CAMPO_TOTAL, count10);values11.put(Utilidades3.CAMPO_TOTAL, count11);values12.put(Utilidades3.CAMPO_TOTAL, count12);
        values13.put(Utilidades3.CAMPO_TOTAL, count13);values14.put(Utilidades3.CAMPO_TOTAL, count14);values15.put(Utilidades3.CAMPO_TOTAL, count15);
        values16.put(Utilidades3.CAMPO_TOTAL, count16);values17.put(Utilidades3.CAMPO_TOTAL, count17);values18.put(Utilidades3.CAMPO_TOTAL, count18);
        values19.put(Utilidades3.CAMPO_TOTAL, count19);values20.put(Utilidades3.CAMPO_TOTAL, count20);values21.put(Utilidades3.CAMPO_TOTAL, count21);
        values22.put(Utilidades3.CAMPO_TOTAL, count22);

        //update campo total
        db.update(Utilidades3.TABLA_MATERIA, values, Utilidades3.CAMPO_ID + "=?", parametros);db.update(Utilidades3.TABLA_MATERIA, values2, Utilidades3.CAMPO_ID + "=?", parametros2);
        db.update(Utilidades3.TABLA_MATERIA, values3, Utilidades3.CAMPO_ID + "=?", parametros3);db.update(Utilidades3.TABLA_MATERIA, values4, Utilidades3.CAMPO_ID + "=?", parametros4);
        db.update(Utilidades3.TABLA_MATERIA, values5, Utilidades3.CAMPO_ID + "=?", parametros5);db.update(Utilidades3.TABLA_MATERIA, values6, Utilidades3.CAMPO_ID + "=?", parametros6);
        db.update(Utilidades3.TABLA_MATERIA, values7, Utilidades3.CAMPO_ID + "=?", parametros7);db.update(Utilidades3.TABLA_MATERIA, values8, Utilidades3.CAMPO_ID + "=?", parametros8);
        db.update(Utilidades3.TABLA_MATERIA, values9, Utilidades3.CAMPO_ID + "=?", parametros9);db.update(Utilidades3.TABLA_MATERIA, values10, Utilidades3.CAMPO_ID + "=?", parametros10);
        db.update(Utilidades3.TABLA_MATERIA, values11, Utilidades3.CAMPO_ID + "=?", parametros11);db.update(Utilidades3.TABLA_MATERIA, values12, Utilidades3.CAMPO_ID + "=?", parametros12);
        db.update(Utilidades3.TABLA_MATERIA, values13, Utilidades3.CAMPO_ID + "=?", parametros13);db.update(Utilidades3.TABLA_MATERIA, values14, Utilidades3.CAMPO_ID + "=?", parametros14);
        db.update(Utilidades3.TABLA_MATERIA, values15, Utilidades3.CAMPO_ID + "=?", parametros15);db.update(Utilidades3.TABLA_MATERIA, values16, Utilidades3.CAMPO_ID + "=?", parametros16);
        db.update(Utilidades3.TABLA_MATERIA, values17, Utilidades3.CAMPO_ID + "=?", parametros17);db.update(Utilidades3.TABLA_MATERIA, values18, Utilidades3.CAMPO_ID + "=?", parametros18);
        db.update(Utilidades3.TABLA_MATERIA, values19, Utilidades3.CAMPO_ID + "=?", parametros19);db.update(Utilidades3.TABLA_MATERIA, values20, Utilidades3.CAMPO_ID + "=?", parametros20);
        db.update(Utilidades3.TABLA_MATERIA, values21, Utilidades3.CAMPO_ID + "=?", parametros21);db.update(Utilidades3.TABLA_MATERIA, values22, Utilidades3.CAMPO_ID + "=?", parametros22);


        //Variables values para el update del campo total
        ContentValues valpor = new ContentValues();ContentValues valpor2 = new ContentValues();ContentValues valpor3 = new ContentValues();
        ContentValues valpor4 = new ContentValues();ContentValues valpor5 = new ContentValues();ContentValues valpor6 = new ContentValues();
        ContentValues valpor7 = new ContentValues();ContentValues valpor8 = new ContentValues();ContentValues valpor9 = new ContentValues();
        ContentValues valpor10 = new ContentValues();ContentValues valpor11 = new ContentValues();ContentValues valpor12 = new ContentValues();
        ContentValues valpor13 = new ContentValues();ContentValues valpor14 = new ContentValues();ContentValues valpor15 = new ContentValues();
        ContentValues valpor16 = new ContentValues();ContentValues valpor17 = new ContentValues();ContentValues valpor18 = new ContentValues();
        ContentValues valpor19 = new ContentValues();ContentValues valpor20 = new ContentValues();ContentValues valpor21 = new ContentValues();
        ContentValues valpor22 = new ContentValues();

        //operaciones porcentaje

        float por = (float) (100.00 / 60.00 * count);float por2 = (float) (100.00 / 60.00 * count2);float por3 = (float) (100.00 / 60.00 * count3);
        float por4 = (float) (100.00 / 60.00 * count4);float por5 = (float) (100.00 / 60.00 * count5);float por6 = (float) (100.00 / 60.00 * count6);
        float por7 = (float) (100.00 / 60.00 * count7);float por8 = (float) (100.00 / 60.00 * count8);float por9 = (float) (100.00 / 60.00 * count9);
        float por10 = (float) (100.00 / 60.00 * count10);float por11 = (float) (100.00 / 60.00 * count11);float por12 = (float) (100.00 / 60.00 * count12);
        float por13 = (float) (100.00 / 60.00 * count13);float por14 = (float) (100.00 / 60.00 * count14);float por15 = (float) (100.00 / 60.00 * count15);
        float por16 = (float) (100.00 / 60.00 * count16);float por17 = (float) (100.00 / 60.00 * count17);float por18 = (float) (100.00 / 60.00 * count18);
        float por19 = (float) (100.00 / 60.00 * count19);float por20 = (float) (100.00 / 60.00 * count20);float por21 = (float) (100.00 / 60.00 * count21);
        float por22 = (float) (100.00 / 60.00 * count22);

        //insercion de los valores
        valpor.put(Utilidades3.CAMPO_PORCENTAJE, por);valpor2.put(Utilidades3.CAMPO_PORCENTAJE, por2);valpor3.put(Utilidades3.CAMPO_PORCENTAJE, por3);
        valpor4.put(Utilidades3.CAMPO_PORCENTAJE, por4);valpor5.put(Utilidades3.CAMPO_PORCENTAJE, por5);valpor6.put(Utilidades3.CAMPO_PORCENTAJE, por6);
        valpor7.put(Utilidades3.CAMPO_PORCENTAJE, por7);valpor8.put(Utilidades3.CAMPO_PORCENTAJE, por8);valpor9.put(Utilidades3.CAMPO_PORCENTAJE, por9);
        valpor10.put(Utilidades3.CAMPO_PORCENTAJE, por10);valpor11.put(Utilidades3.CAMPO_PORCENTAJE, por11);valpor12.put(Utilidades3.CAMPO_PORCENTAJE, por12);
        valpor13.put(Utilidades3.CAMPO_PORCENTAJE, por13);valpor14.put(Utilidades3.CAMPO_PORCENTAJE, por14);valpor15.put(Utilidades3.CAMPO_PORCENTAJE, por15);
        valpor16.put(Utilidades3.CAMPO_PORCENTAJE, por16);valpor17.put(Utilidades3.CAMPO_PORCENTAJE, por17);valpor18.put(Utilidades3.CAMPO_PORCENTAJE, por18);
        valpor19.put(Utilidades3.CAMPO_PORCENTAJE, por19);valpor20.put(Utilidades3.CAMPO_PORCENTAJE, por20);valpor21.put(Utilidades3.CAMPO_PORCENTAJE, por21);
        valpor22.put(Utilidades3.CAMPO_PORCENTAJE, por22);

        //update
        db.update(Utilidades3.TABLA_MATERIA, valpor, Utilidades3.CAMPO_ID + "=?", parametros);db.update(Utilidades3.TABLA_MATERIA, valpor2, Utilidades3.CAMPO_ID + "=?", parametros2);
        db.update(Utilidades3.TABLA_MATERIA, valpor3, Utilidades3.CAMPO_ID + "=?", parametros3);db.update(Utilidades3.TABLA_MATERIA, valpor4, Utilidades3.CAMPO_ID + "=?", parametros4);
        db.update(Utilidades3.TABLA_MATERIA, valpor5, Utilidades3.CAMPO_ID + "=?", parametros5);db.update(Utilidades3.TABLA_MATERIA, valpor6, Utilidades3.CAMPO_ID + "=?", parametros6);
        db.update(Utilidades3.TABLA_MATERIA, valpor7, Utilidades3.CAMPO_ID + "=?", parametros7);db.update(Utilidades3.TABLA_MATERIA, valpor8, Utilidades3.CAMPO_ID + "=?", parametros8);
        db.update(Utilidades3.TABLA_MATERIA, valpor9, Utilidades3.CAMPO_ID + "=?", parametros9);db.update(Utilidades3.TABLA_MATERIA, valpor10, Utilidades3.CAMPO_ID + "=?", parametros10);
        db.update(Utilidades3.TABLA_MATERIA, valpor11, Utilidades3.CAMPO_ID + "=?", parametros11);db.update(Utilidades3.TABLA_MATERIA, valpor12, Utilidades3.CAMPO_ID + "=?", parametros12);
        db.update(Utilidades3.TABLA_MATERIA, valpor13, Utilidades3.CAMPO_ID + "=?", parametros13);db.update(Utilidades3.TABLA_MATERIA, valpor14, Utilidades3.CAMPO_ID + "=?", parametros14);
        db.update(Utilidades3.TABLA_MATERIA, valpor15, Utilidades3.CAMPO_ID + "=?", parametros15);db.update(Utilidades3.TABLA_MATERIA, valpor16, Utilidades3.CAMPO_ID + "=?", parametros16);
        db.update(Utilidades3.TABLA_MATERIA, valpor17, Utilidades3.CAMPO_ID + "=?", parametros17);db.update(Utilidades3.TABLA_MATERIA, valpor18, Utilidades3.CAMPO_ID + "=?", parametros18);
        db.update(Utilidades3.TABLA_MATERIA, valpor19, Utilidades3.CAMPO_ID + "=?", parametros19);db.update(Utilidades3.TABLA_MATERIA, valpor20, Utilidades3.CAMPO_ID + "=?", parametros20);
        db.update(Utilidades3.TABLA_MATERIA, valpor21, Utilidades3.CAMPO_ID + "=?", parametros21);db.update(Utilidades3.TABLA_MATERIA, valpor22, Utilidades3.CAMPO_ID + "=?", parametros22);

        db.close();
    }
}




