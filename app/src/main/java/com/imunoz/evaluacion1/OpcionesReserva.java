package com.imunoz.evaluacion1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class OpcionesReserva extends AppCompatActivity {

    private TextView titulo;
    private TextView texto;
    private TextView coordenada;


    ArrayList<Noticia> lista;

    private final String[] permisos = { Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private final int ACTIVITY_CAMARA = 50;
    private final int ACTIVITY_GALERIA = 60;
    private ImageView fotito;
    private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opciones_reserva);
        titulo = findViewById(R.id.campoTitulo);
        texto = findViewById(R.id.campoTexto);
        coordenada = findViewById(R.id.campoCoordenada);
        fotito = findViewById(R.id.fotito);

        lista = new ArrayList<Noticia>();
        //aquí pedimos permisos
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.M){
            requestPermissions(permisos, 100);
        }

        bitmap = null;
        fotito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(OpcionesReserva.this, "Tocaste la foto", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            /*if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "Permiso de cámara concedido", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Se necesita acceso a la cámara. Por favor conceda el permiso", Toast.LENGTH_LONG).show();
            }*/

            if(!(grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de cámara", Toast.LENGTH_LONG).show();
            }

            if(!(grantResults[1] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de lectura de memoria", Toast.LENGTH_LONG).show();
            }

            if(!(grantResults[2] == PackageManager.PERMISSION_GRANTED)){
                Toast.makeText(this, "Se necesita permiso de escritura de memoria", Toast.LENGTH_LONG).show();
            }
        }

    }
    public void TomarFoto(View view){

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, ACTIVITY_CAMARA);
    }
    public void GuardarFoto(View view){
        if(bitmap != null){

            File archivoFoto = null;
            OutputStream streamSalida = null;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                ContentResolver resolver = getContentResolver();
                ContentValues values = new ContentValues();

                String nombreArchivo = System.currentTimeMillis()+"_fotoPrueba";

                values.put(MediaStore.Images.Media.DISPLAY_NAME, nombreArchivo);
                values.put(MediaStore.Images.Media.MIME_TYPE, "Image/jpg");
                values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp");
                values.put(MediaStore.Images.Media.IS_PENDING, 1);

                Uri coleccion = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                Uri fotoUri = resolver.insert(coleccion, values);

                try{
                    streamSalida = resolver.openOutputStream(fotoUri);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

                values.clear();
                values.put(MediaStore.Images.Media.IS_PENDING, 0);
                resolver.update(fotoUri, values, null, null);
            } else {

                String ruta = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
                String nombreArchivo = System.currentTimeMillis()+"_fotoPrueba.jpg";
                archivoFoto = new File(ruta, nombreArchivo);

                try{
                    streamSalida = new FileOutputStream(archivoFoto);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }

            }

            boolean fotoOk = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, streamSalida);

            if(fotoOk){
                Toast.makeText(this, "Foto Guardada!", Toast.LENGTH_SHORT).show();
            }

            if(streamSalida != null){
                try{
                    streamSalida.flush();
                    streamSalida.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            if(archivoFoto != null){
                MediaScannerConnection.scanFile(this, new String[]{archivoFoto.toString()}, null, null);
            }


        } else {
            Toast.makeText(this, "Primero debe tomar una foto antes de usar esta opción", Toast.LENGTH_SHORT).show();
        }
    }

    public void CargarFoto(View view){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(intent, ACTIVITY_GALERIA);
    }
    public void GuardaNoticia(View view) {

            //////////Alert de confirmacion
            StringBuilder alertConfirmacion = new StringBuilder();

        alertConfirmacion.append("Su Noticia es:");
        alertConfirmacion.append("\nTitulo: "+titulo);
        alertConfirmacion.append("\nHora: "+texto.getText().toString());


            new AlertDialog.Builder(this)

                    .setTitle("Confirmación de Noticia")
                    .setMessage(alertConfirmacion.toString())
                    .setCancelable(true)

                    .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Noticia r = new Noticia();
                            r.setTitulo(titulo.getText().toString());
                            r.setTexto(texto.getText().toString());
                            r.setTexto(coordenada.getText().toString());

                            lista.add(r);


                            Toast.makeText(OpcionesReserva.this, "Registro ingresado com éxito.", Toast.LENGTH_LONG).show();
                            //finish();
                        }
                    })

                    .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })

                    .create()
                    .show();

    }



    public void verReservas(View view) {
        StringBuilder str = new StringBuilder();
        if(lista.size()==0){
            Toast.makeText(OpcionesReserva.this, "El usuario no tiene reservas.", Toast.LENGTH_LONG).show();
        }else{

            Intent intent = new Intent(this, ListarReservas.class);
            intent.putExtra("noticia", lista);
            startActivity(intent);

        }

    }


    public  void tomarFoto (View view){

        Intent intent = new Intent(this,MainActivity.class);

        startActivityForResult(intent, 100);


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case ACTIVITY_CAMARA:
                if(resultCode == RESULT_OK){
                    bitmap = (Bitmap) data.getExtras().get("data");
                    fotito.setImageBitmap(bitmap);
                }
                break;

            case ACTIVITY_GALERIA:
                if(resultCode == RESULT_OK){
                    Uri ruta = data.getData();
                    fotito.setImageURI(ruta);
                }
                break;
        }


    }
}