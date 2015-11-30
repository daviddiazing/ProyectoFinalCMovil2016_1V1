package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import com.ebookfrenzy.proyfinalcmovil2016_1v1.BoundService.MyLocalBinder;


import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;



import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;


public class TodasLasTiendasActivity extends AppCompatActivity {

    BoundService myService;//gps
    boolean isBound = false;//gps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todas_las_tiendas);

        Intent intent = new Intent(this, BoundService.class);//levantar servicio gps
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);//levantar servicio gps

		rellenarTiendas();
		
        //double currentTime[] = myService.getCurrentTime();//levantar servicio gps
    }

    //conexión para el gps
    private ServiceConnection myConnection = new ServiceConnection()
    {

        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MyLocalBinder binder = (MyLocalBinder) service;
            myService = binder.getService();
            isBound = true;
        }

        public void onServiceDisconnected(ComponentName arg0) {
            isBound = false;
        }
    };



















    ////xml
    public ArrayList<DatosTienda> ArrayTiendas = new ArrayList<DatosTienda>();
    // private AdapterJuegos adapterTiendas;
    ListView lstviListaTiendas;
    // private String URL = "http://www.serverbpw.com/cm/2016-1/list.php";
    private String URL = "http://www.serverbpw.com/cm/2016-1/stores.php";


    private void inicializaListView(){
        // lstviListaJuegos = (ListView) getView().findViewById(R.id.lstviListaJuegos);
        // adapterJuegos = new AdapterJuegos(getContext(),ArrayJuegos);
        // lstviListaJuegos.setAdapter(adapterJuegos);
    }

    private void rellenarTiendas(){
        if(isOnline()){
            // new DescargarListaTiendas(getContext(),URL).execute();
            new DescargarListaTiendas(this,URL).execute();
        }else{
            // Toast.makeText(getContext(), "Desconectado", Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Desconectado", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isOnline() {//Para verificar que estamos conectados
        // ConnectivityManager connect = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        ConnectivityManager connect = (ConnectivityManager) this.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connect.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected()){
            return true;
        }
        return false;
    }




    private class DescargarListaTiendas extends AsyncTask<String, Void, Boolean> {
        private String strURL;
        private Context context;
        ProgressDialog pDialog;

        public DescargarListaTiendas (Context context, String strURL) {
            this.context = context;
            this.strURL = strURL;
        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            // // pDialog = new ProgressDialog(getContext());
            pDialog = new ProgressDialog(context);
            pDialog.setMessage("Cargando Información");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground( String... params) {
            // XMLParserTienda parser = new XMLParserTienda(strURL, getContext());
            XMLParserTienda parser = new XMLParserTienda(strURL, null);
            ArrayTiendas = parser.parse();
            return true;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                try {
                    inicializaListView();
                    // pDialog.dismiss();

                    // placeHolder.tvNombre.setText(juegos.get(position).getNombre());
                    // placeHolder.tvID.setText(juegos.get(position).getID());
                    //placeHolder.tvDescripcion.setText(juegos.get(position).getDescripcion());		//placeHolder.ibtnJuego.setImageBitmap(juegos.get(position).getImgJuego());
                    // String nombre = ArrayTiendas.get(3).getDescripcion() + " SIZE DE ARRAY:" + ArrayTiendas.size();

					double currentTime[] = myService.getCurrentTime();//levantar servicio gps
					
                    String nombre="";
                    for (int i =0;i<ArrayTiendas.size();i++)
                    {
                        nombre = nombre + "\n" + ArrayTiendas.get(i).getNombre() + "," + ArrayTiendas.get(i).getLatitud() + ","+ ArrayTiendas.get(i).getLongitud();
                    }

					// i.putExtra("Latitud", currentTime[0]);
					// i.putExtra("Longitud", currentTime[1]);
					nombre = nombre + "\n\n\n" + "coordenadas GPS: " + currentTime[0] + "," + currentTime[1];


                    TextView myTextView =
                            (TextView)findViewById(R.id.textView);
                    myTextView.setText(nombre);
                    // myTextView.setText(stackSites.get(0).toString()); //se trae toda una entrada

                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(context,"Error de Lectura",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
