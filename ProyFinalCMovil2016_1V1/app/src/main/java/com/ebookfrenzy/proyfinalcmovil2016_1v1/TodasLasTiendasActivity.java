package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//gps
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
import java.util.Arrays;
import java.util.List;


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


public class TodasLasTiendasActivity extends AppCompatActivity {

    BoundService myService;//gps
    boolean isBound = false;//gps
		
	ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
	ArrayList<Lista_entrada> datos2 = new ArrayList<Lista_entrada>();

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
                    // inicializaListView();
                    // pDialog.dismiss();

                    // placeHolder.tvNombre.setText(juegos.get(position).getNombre());
                    // placeHolder.tvID.setText(juegos.get(position).getID());
                    //placeHolder.tvDescripcion.setText(juegos.get(position).getDescripcion());		//placeHolder.ibtnJuego.setImageBitmap(juegos.get(position).getImgJuego());
                    // String nombre = ArrayTiendas.get(3).getDescripcion() + " SIZE DE ARRAY:" + ArrayTiendas.size();

					double currentTime[] = myService.getCurrentTime();//levantar servicio gps
					
                    // String nombre="";
                    for (int i =0;i<ArrayTiendas.size();i++)
                    {
                        // nombre = nombre + "\n" + ArrayTiendas.get(i).getNombre() + "," + ArrayTiendas.get(i).getLatitud() + ","+ ArrayTiendas.get(i).getLongitud();
						
						String nombre = ArrayTiendas.get(i).getNombre();
						String latitud = ArrayTiendas.get(i).getLatitud();
						String longitud = ArrayTiendas.get(i).getLongitud();
						
						if(ArrayTiendas.get(i).getNombre().equalsIgnoreCase("Walmart Copilco"))
							datos.add(new Lista_entrada(R.drawable.im_walmart, "WALMART", "COPILCO",nombre,latitud,longitud));
						if(ArrayTiendas.get(i).getNombre().equalsIgnoreCase("Mega Comercial Mexicana Quevedo"))
							datos.add(new Lista_entrada(R.drawable.im_mega, "MEGA", "QUEVEDO",nombre,latitud,longitud));
						if(ArrayTiendas.get(i).getNombre().equalsIgnoreCase("Chedraui Copilco"))
							datos.add(new Lista_entrada(R.drawable.im_chedraui, "CHEDRAUI", "COPILCO",nombre,latitud,longitud));
                    }

					// i.putExtra("Latitud", currentTime[0]);
					// i.putExtra("Longitud", currentTime[1]);
					// nombre = nombre + "\n\n\n" + "coordenadas GPS: " + currentTime[0] + "," + currentTime[1];


                    // // // // // TextView myTextView =
                            // // // // // (TextView)findViewById(R.id.textView);
                    // // // // // myTextView.setText(nombre);
                    // myTextView.setText(stackSites.get(0).toString()); //se trae toda una entrada

			
			// ArrayList<Lista_entrada> datos = new ArrayList<Lista_entrada>();
			// datos.add(new Lista_entrada(R.drawable.im_walmart, "WALMART", "COPILCO"));
			// datos.add(new Lista_entrada(R.drawable.im_mega, "MEGA", "QUEVEDO"));
			// datos.add(new Lista_entrada(R.drawable.im_chedraui, "CHEDRAUI", "COPILCO"));
					datos2 = obtenerTiendasOrdenadas(datos, currentTime);
			
				
					Intent intent = new Intent(TodasLasTiendasActivity.this,ListadoTiendasActivity.class);
					// intent.putExtra("DATO_2",elegido);
					intent.putExtra("DATO",datos2);
					startActivity(intent);			
					finish();
					
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                Toast.makeText(context,"Error de Lectura",Toast.LENGTH_SHORT).show();
            }
        }//fin de onPostExecute
		
		public ArrayList<Lista_entrada> obtenerTiendasOrdenadas(ArrayList<Lista_entrada> ArrayTiendas, double currentTime[])
		{
			ArrayList<Lista_entrada> ArrayTiendasOrdenadas = new ArrayList<Lista_entrada>();
			double distancia[] = new double[ArrayTiendas.size()];
			double distancia2[] = new double[ArrayTiendas.size()];
			
			String d_str[] = new String[ArrayTiendas.size()];
			String d2_str[] = new String[ArrayTiendas.size()];

			double xgps = currentTime[0];//Latitud
			double ygps = currentTime[1];//Longitud
			// double xgps = 19.333943;//Latitud
			// double ygps = -99.154865;//Longitud
			double xtienda=0.0;//Latitud
			double ytienda=0.0;//Longitud
			
			double dx=0.0;//distancia en x (latitud) entre la tienda y la posicion gps
			double dy=0.0;//distancia en y (longitud) entre la tienda y la posicion gps
			
			for(int i=0;i<ArrayTiendas.size();i++)
			{
				xtienda = Double.parseDouble(ArrayTiendas.get(i).get_latitud());
				ytienda = Double.parseDouble(ArrayTiendas.get(i).get_longitud());
				if(xtienda-xgps<0)//obtener distancia positiva en x (latitud)
				{
					dx = xgps-xtienda;
				}else{
					dx = xtienda-xgps;
				}
				
				if(ytienda-ygps<0)//obtener distancia positiva en y (longitud)
				{
					dy = ygps-ytienda;
				}else{
					dy = ytienda-ygps;
				}
				
				distancia[i]=Math.sqrt(Math.pow(dx,2)  +   Math.pow(dy,2));				
				distancia2[i] = distancia[i];
				d_str[i] = String.valueOf(distancia[i]);
				d2_str[i] = String.valueOf(distancia[i]);
                // Toast.makeText(context,String.valueOf(distancia[i]),Toast.LENGTH_SHORT).show();				
			}//fin de for
			
			Arrays.sort(distancia2);// ordena distancia2 en forma ascendente de menor a mayor distancia
			// Toast.makeText(context,String.valueOf(distancia2[0]),Toast.LENGTH_SHORT).show();				
			// Toast.makeText(context,String.valueOf(distancia2[1]),Toast.LENGTH_SHORT).show();				
			// Toast.makeText(context,String.valueOf(distancia2[2]),Toast.LENGTH_SHORT).show();				
			
			Arrays.sort(d2_str);
			
			if(d_str[0].equals(d2_str[0]))
            {
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(0));
                // Toast.makeText(context,"opcion1a",Toast.LENGTH_SHORT).show();				
            }else if(d_str[1].equals(d2_str[0]))
			{
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(1));
				// Toast.makeText(context,"opcion1b",Toast.LENGTH_SHORT).show();				
			}else if(d_str[2].equals(d2_str[0]))
			{
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(2));
				// Toast.makeText(context,"opcion1c",Toast.LENGTH_SHORT).show();				
			}
			//
			if(d_str[0].equals(d2_str[1]))
            {
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(0));
                // Toast.makeText(context,"opcion2a",Toast.LENGTH_SHORT).show();				
            }else if(d_str[1].equals(d2_str[1]))
			{
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(1));
				// Toast.makeText(context,"opcion2b",Toast.LENGTH_SHORT).show();				
			}else if(d_str[2].equals(d2_str[1]))
			{
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(2));
				// Toast.makeText(context,"opcion2c",Toast.LENGTH_SHORT).show();				
			}
			//
			if(d_str[0].equals(d2_str[2]))
            {
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(0));
                // Toast.makeText(context,"opcion3a",Toast.LENGTH_SHORT).show();				
            }else if(d_str[1].equals(d2_str[2]))
			{
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(1));
				// Toast.makeText(context,"opcion3b",Toast.LENGTH_SHORT).show();				
			}else if(d_str[2].equals(d2_str[2]))
			{
				ArrayTiendasOrdenadas.add(ArrayTiendas.get(2));
				// Toast.makeText(context,"opcion3c",Toast.LENGTH_SHORT).show();				
			}
			
			// if(d_str[0].equals(d2_str[1]))
            // {
                // Toast.makeText(context,"son diferentes",Toast.LENGTH_SHORT).show();				
            // }
						
			// int ubicacion=0;
			// ubicacion = Arrays.binarySearch( distancia, distancia2[0] );
			// ArrayTiendasOrdenadas.add(ArrayTiendas.get(ubicacion));
			
						
			// ubicacion = Arrays.binarySearch( distancia, distancia2[1] );
			// ArrayTiendasOrdenadas.add(ArrayTiendas.get(ubicacion));
	
			
			// Toast.makeText(context,"llego aqui segundo",Toast.LENGTH_SHORT).show();				
			
			
			
			// // ubicacion = Arrays.binarySearch( distancia, distancia2[2] );
			// // ArrayTiendasOrdenadas.add(ArrayTiendas.get(ubicacion));
			
			// ArrayTiendasOrdenadas.add(ArrayTiendas.get(posicionBuscada));
			
			// for(int i=0;i<ArrayTiendas.size();i++)
			// {
				// double distBuscada = distancia2[i];
				// int posicionBuscada = 0;
				// for(int j=0;j<ArrayTiendas.size();j++)
				// {
					// // if(distancia[j]==distBuscada)//busca la posicion donde esta la distBuscada
					// if(new Double(distancia[j]).compareTo(new Double(distBuscada))==0)//busca la posicion donde esta la distBuscada
					// {
						
						// posicionBuscada=j;
						// break;
					// }else{
						// j++;
					// }
				// }//fin de for de busqueda de posicionBuscada
				
				// ArrayTiendasOrdenadas.add(ArrayTiendas.get(posicionBuscada));
			// }//fin de for de cargar arreglo ordenado
			
			// if(ArrayTiendas.get(0).get_nombre().equalsIgnoreCase("Walmart Copilco"))
				// Toast.makeText(context,"Error de Lectura",Toast.LENGTH_SHORT).show();
			
			return ArrayTiendasOrdenadas;
		}//fin de obtenerTiendasOrdenadas
    }
}
