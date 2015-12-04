package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

//xml
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

import java.util.ArrayList;


public class BuscarPalabraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_palabra);
		
		rellenarTiendas();
    }
	
	
	
	////xml
    public ArrayList<DatosAhorcado> ArrayAhorcado = new ArrayList<DatosAhorcado>();
    // private AdapterJuegos adapterTiendas;
    // ListView lstviListaTiendas;
    // private String URL = "http://www.serverbpw.com/cm/2016-1/list.php";
    private String URL = "http://www.serverbpw.com/cm/2016-1/hangman.php";


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
            pDialog.setMessage("Cargando Información para comenzar juego");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground( String... params) {
            // XMLParserAhorcado parser = new XMLParserAhorcado(strURL, getContext());
            XMLParserAhorcado parser = new XMLParserAhorcado(strURL, null);
            ArrayAhorcado = parser.parse();
            return true;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                try {
                    // inicializaListView();
                    // pDialog.dismiss();

                    // placeHolder.tvNombre.setText(juegos.get(position).getNombre());
                    // placeHolder.tvID.setText(juegos.get(position).getID());
                    //placeHolder.tvDescripcion.setText(juegos.get(position).getDescripcion());		//placeHolder.ibtnJuego.setImageBitmap(juegos.get(position).getImgJuego());
                    // String nombre = ArrayAhorcado.get(3).getDescripcion() + " SIZE DE ARRAY:" + ArrayAhorcado.size();

										
                    
                    
					String categoria = ArrayAhorcado.get(0).getCategoria();
					String palabra = ArrayAhorcado.get(0).getPalabra();
					Toast.makeText(context,"categoria: "+categoria+", palabra: "+palabra,Toast.LENGTH_SHORT).show();
                    // // // // // TextView myTextView =
                            // // // // // (TextView)findViewById(R.id.textView);
                    // // // // // myTextView.setText(nombre);
                    // myTextView.setText(stackSites.get(0).toString()); //se trae toda una entrada

			
			
			
				
					Intent intent = new Intent(BuscarPalabraActivity.this,AhorcadoActivity.class);
					// intent.putExtra("DATO_2",elegido);
					// intent.putExtra("DATO",datos2);
					intent.putExtra("CATEGORIA",categoria);
					intent.putExtra("PALABRA",palabra);
					startActivity(intent);			
					finish();
					
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(context,"Error de Lectura",Toast.LENGTH_SHORT).show();
            }
        }//fin de onPostExecute
		
		
    }
}
