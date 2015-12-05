package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class Reconocimiento extends AppCompatActivity {
    VideoView elVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconocimiento);

        //Ontiene el apuntador al VideoView
        elVideo = (VideoView) findViewById(R.id.myVideoView);

        // Obtiene el apuntador al Textview para el titulo del video
        TextView elTitulo = (TextView) findViewById(R.id.TituloVideo);

        //recupera el index del video (parametro obtenido en el reconocimiento de imagen)
        Bundle extras = getIntent().getExtras();
        String indice = extras.getString("videoIndex");

        //Obtiene el titulo del video asociado al index y lo establece en el TextView
        elTitulo.setText( getVideoTitle(indice) );

        // Propiedades del titulo
        elTitulo.setBackgroundColor(Color.argb(100, 0, 0, 0));
        elTitulo.setPadding(60, 25, 60, 25);
        elTitulo.setTextColor(Color.WHITE);
        elTitulo.setTextSize(16);

        // Determina la ubicación del archivo de video
        String ubicacion = "android.resource://" + getPackageName() + "/" + getVideoFile(indice);

        //Controles multimedia
        MediaController mediaController = new MediaController(this);
        mediaController.setAnchorView(elVideo);
        //mediaController.show(1000);

        elVideo.setMediaController(mediaController);

        // Muestra el video
        elVideo.setVideoURI(Uri.parse(ubicacion));
        elVideo.start();

    }

    ////////////// Permite que el video no se detenga al cambiar la orientación del dispositivo
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("Position", elVideo.getCurrentPosition());
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        int position= savedInstanceState.getInt("Position");
        elVideo.seekTo(position);
    }
    //////////////

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // stuff for set video to proper position..
    }

    // Obtiene el nombre del video
    protected String getVideoTitle( String index){
        String Titulo = "";

        switch( Integer.parseInt(index) ) {

            case 0: Titulo =  getString(R.string.titulo0);  break;
            case 1: Titulo =  getString(R.string.titulo1);  break;
            case 2: Titulo =  getString(R.string.titulo2);  break;
            case 3: Titulo =  getString(R.string.titulo3);  break;
            case 4: Titulo =  getString(R.string.titulo4);  break;
            case 5: Titulo =  getString(R.string.titulo5);  break;
            case 6: Titulo =  getString(R.string.titulo6);  break;
            case 7: Titulo =  getString(R.string.titulo7);  break;
            case 8: Titulo =  getString(R.string.titulo8);  break;
        }
        return Titulo;
    }

    // Obtiene el video
    protected int getVideoFile( String index){
        int videoID = 0;

        switch( Integer.parseInt(index) ) {

            case 0: videoID =  R.raw.v0;  break;
            case 1: videoID =  R.raw.v1;  break;
            case 2: videoID =  R.raw.v2;  break;
            case 3: videoID =  R.raw.v3;  break;
            case 4: videoID =  R.raw.v4;  break;
            case 5: videoID =  R.raw.v5;  break;
            case 6: videoID =  R.raw.v6;  break;
            case 7: videoID =  R.raw.v7;  break;
            case 8: videoID =  R.raw.v8;  break;
        }
        return videoID;
    }
}