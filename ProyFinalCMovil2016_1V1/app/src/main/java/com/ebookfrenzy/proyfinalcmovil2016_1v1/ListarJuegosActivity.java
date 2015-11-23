package com.ebookfrenzy.proyfinalcmovil2016_1v1;

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

public class ListarJuegosActivity extends Fragment{

    public ArrayList<DatosJuego> ArrayJuegos = new ArrayList<DatosJuego>();
    private AdapterJuegos adapterJuegos;
    ListView lstviListaJuegos;
    private String URL = "http://www.serverbpw.com/cm/2016-1/list.php";

    public ListarJuegosActivity () {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_listar_juegos, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);
        rellenarJuegos();
    }

    private void inicializaListView(){
        lstviListaJuegos = (ListView) getView().findViewById(R.id.lstviListaJuegos);
        adapterJuegos = new AdapterJuegos(getContext(),ArrayJuegos);
        lstviListaJuegos.setAdapter(adapterJuegos);
    }

    private void rellenarJuegos(){
        if(isOnline()){
            new DescargarListaJuegos(getContext(),URL).execute();
        }else{
            Toast.makeText(getContext(), "Desconectado", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isOnline() {//Para verificar que estamos conectados
        ConnectivityManager connect = (ConnectivityManager) getContext().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = connect.getActiveNetworkInfo();
        if(netInfo != null && netInfo.isConnected()){
            return true;
        }
        return false;
    }

    private class DescargarListaJuegos extends AsyncTask<String, Void, Boolean> {
        private String strURL;
        private Context context;
        ProgressDialog pDialog;

        public DescargarListaJuegos (Context context, String strURL) {
            this.context = context;
            this.strURL = strURL;
        }


        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();

            pDialog = new ProgressDialog(getContext());
            pDialog.setMessage("Cargando Información");
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.show();

        }

        @Override
        protected Boolean doInBackground( String... params) {
            XMLParser parser = new XMLParser(strURL, getContext());
            ArrayJuegos = parser.parse();
            return true;
        }


        @Override
        protected void onPostExecute(Boolean success) {
            if(success){
                try {
                    inicializaListView();
                    pDialog.dismiss();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(context,"Error de Lectura",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
