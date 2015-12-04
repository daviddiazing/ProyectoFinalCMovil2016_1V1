package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class AhorcadoActivity extends AppCompatActivity implements View.OnClickListener{

    TextView textView;
    TextView textViewOp;
    EditText editText;
    ImageView imageView;
    Button button;

    //Aquí declaro las variables globales importantes. Todos los métodos sólo van a modificar a estas
    ArrayList<Integer> visibles = new ArrayList<Integer>();
    String palabraSecreta = "Palabra Secreta";
    // char[] palabraArray = palabraSecreta.toCharArray();
	char[] palabraArray = null;
    int oportunidades=4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ahorcado);
		
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		
		String categoria = extras.getString("CATEGORIA");
		palabraSecreta = extras.getString("PALABRA");
		palabraArray = palabraSecreta.toCharArray();
		
		

        textView = (TextView) findViewById(R.id.textView);
        textViewOp= (TextView) findViewById(R.id.textViewOp);
        editText= (EditText)findViewById(R.id.editText);
        imageView=(ImageView)findViewById(R.id.imageView);

        //Inicialización de cómo se verá al principio la pantalla
        imageView.setImageResource(R.drawable.h1);
        button=(Button)findViewById(R.id.button);
        buscarLetra('_');
        buscarLetra(' ');
        textView.setText(muestraPalabra());
        textViewOp.setText("Tienes 4 oportunidades");
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                //Obtiene la respuesta del usuario
                String letra = editText.getText().toString();
                if(letra.length()==0){
                    Toast.makeText(this, "Ingrese una letra", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                }else
                if(letra.length()!=1){
                    Toast.makeText(this, "Ingrese sólo una letra", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                }else{
                    //Si ingresó correctamente sólo 1 letra verifica si es parte de la palabra.
                    if(buscarLetra(letra.charAt(0))){
                        //Si es parte de la palabra muestra la nueva palabra, y hay 2 opciones:
                        textView.setText(muestraPalabra());
                        editText.setText("");
                        //Que ya ganó, es decir ya no hay más letras que descubrir
                        if(revisaGanador()){
                            Toast.makeText(this, "GANASTE!!!!", Toast.LENGTH_SHORT).show();
                            textViewOp.setText("Ganaste ;)");
                            imageView.setImageResource(R.drawable.win);
                            editText.setEnabled(false);
                            button.setEnabled(false);
                        }else{
                            //O sino sigue jugando
                            Toast.makeText(this, "Bien!", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        //Pero si la letra ingresada no es parte de la palabra, entonce:
                        textView.setText(muestraPalabra());
                        //Si aun tiene oportunidades sigue jugando
                        if(revisarOportunidades()){
                            Toast.makeText(this, "Ups! Sigue jugando", Toast.LENGTH_SHORT).show();
                            editText.setText("");

                        }else{
                            //Si ya no le quedan oportundades pierde y ya no puede haacer más
                            Toast.makeText(this, "GAME OVER :c Loser", Toast.LENGTH_SHORT).show();

                        }

                    }

                }
                break;
        }
    }


    //Regresa TRUE si encontró al menos una coincidencia de la letra en la palabra secreta.
    //Al mismo tiempo de que guarda la coincidencia para mostrarla en pantalla
    boolean buscarLetra(char c){
        boolean res = false;
        Character character = new Character(c);
        for(int i=0; i<palabraArray.length;i++){
            if(palabraArray[i]==character.toLowerCase(c) || palabraArray[i]==character.toUpperCase(c)){//Para minusculas y mayusculas
                visibles.add(i);
                res=true;
            }
        }
        return res;
    }

    //Regresa la palabra que se va a mostrar mostrar a partir de la palabra secreta original y cuales letras se han adivinado
    String muestraPalabra(){
        String palabraMuestra="";
        for(int i=0; i<palabraArray.length;i++){
            if(visibles.contains(i)){
                palabraMuestra+=palabraArray[i];
            }else{
                palabraMuestra+="*";
            }
        }
        return palabraMuestra;
    }

    //TRUE si ya ganaste el juego. False si aún no has ganado
    boolean revisaGanador(){
        boolean ganaste=true;
        for(int i=0; i<palabraArray.length;i++){
            if(!visibles.contains(i)){
                ganaste=false;
            }
        }
        return ganaste;
    }

    //Revisa las oportunidades que le quedan, y en cada caso cambia la imagen y el texto a mostrar
    //Regresa TRUE si sigue jugando, FALSE para que ya no pueda seguir jugando
    boolean revisarOportunidades(){
        boolean sigue = true;
        switch (oportunidades){
            case 4:
                oportunidades=3;
                imageView.setImageResource(R.drawable.h2);
                textViewOp.setText("Te quedan 3 oportunidades extra");
                break;
            case 3:
                oportunidades=2;
                imageView.setImageResource(R.drawable.h3);
                textViewOp.setText("Te quedan 2 oportunidades extra");
                break;
            case 2:
                oportunidades=1;
                imageView.setImageResource(R.drawable.h4);
                textViewOp.setText("Te queda 1 oportunidad extra");
                break;
            case 1:
                oportunidades=0;
                imageView.setImageResource(R.drawable.h5);
                textViewOp.setText("Es tu última oportunidad");
                break;
            case 0:
                imageView.setImageResource(R.drawable.h6);
                textViewOp.setText("Ya no te quedan oportunidades");
                editText.setEnabled(false);
                button.setEnabled(false);
                sigue=false;
                break;
        }
        return sigue;
    }




}






