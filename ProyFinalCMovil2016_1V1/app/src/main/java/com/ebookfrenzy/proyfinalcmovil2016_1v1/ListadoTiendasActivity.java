package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListadoTiendasActivity extends AppCompatActivity {

	private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listado_tiendas);

        //recibe el "DATO"
        Bundle extras = getIntent().getExtras();

        //En caso de que Sí haya enviado algo
        if(extras!=null){
            ArrayList<Lista_entrada> datos = (ArrayList<Lista_entrada>)extras.getSerializable("DATO");
            lista = (ListView) findViewById(R.id.listView);
            lista.setAdapter(new Lista_adaptador(this, R.layout.entrada, datos){
                @Override
                public void onEntrada(Object entrada, View view) {
                    if (entrada != null) {
                        TextView texto_superior_entrada = (TextView) view.findViewById(R.id.textView_superior);
                        if (texto_superior_entrada != null)
                            texto_superior_entrada.setText(((Lista_entrada) entrada).get_textoEncima());

                        TextView texto_inferior_entrada = (TextView) view.findViewById(R.id.textView_inferior);
                        if (texto_inferior_entrada != null)
                            texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo());
							// texto_inferior_entrada.setText(((Lista_entrada) entrada).get_textoDebajo() + ((Lista_entrada) entrada).get_latitud());

                        ImageView imagen_entrada = (ImageView) view.findViewById(R.id.imageView_imagen);
                        if (imagen_entrada != null)
                            imagen_entrada.setImageResource(((Lista_entrada) entrada).get_idImagen());
                    }
                }
            });

            lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
                    Lista_entrada elegido = (Lista_entrada) pariente.getItemAtPosition(posicion);
/*
                    CharSequence texto = "Seleccionado: " + elegido.get_textoDebajo();
                    Toast toast = Toast.makeText(Main2Activity.this, texto, Toast.LENGTH_LONG);
                    toast.show();
                    */

                    Intent intent = new Intent(ListadoTiendasActivity.this,MapsActivity.class);
                    intent.putExtra("DATO_2",elegido);
                    startActivity(intent);
                }
            });

        }//fin de if

    }
}
