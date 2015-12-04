package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import com.google.android.gms.maps.model.Marker;//marcadores

import com.google.android.gms.maps.UiSettings;

import android.widget.Toast;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
	
	LatLng miPosicion;
	
	String nombreTienda=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
		
		
		
		Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }

        //En caso de que Sí haya enviado algo
        if(extras!=null){
            Lista_entrada dato = (Lista_entrada)extras.getSerializable("DATO_2");
            // texto.setText(dato.get_textoEncima());
            // texto2.setText(dato.get_textoDebajo());
            // imagen.setImageResource(((Lista_entrada) dato).get_idImagen());
			miPosicion = new LatLng(Double.parseDouble(dato.get_latitud()),Double.parseDouble(dato.get_longitud()));
			nombreTienda = dato.get_nombre();
			// Toast.makeText(this, dato.get_latitud()+",", Toast.LENGTH_SHORT).show();
        }
		
        // = extras.getDouble("Latitud");
        // miPosicion = new LatLng(extras.getDouble("Latitud"),extras.getDouble("Longitud"));

		
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
		
		
		//
        UiSettings mapSettings;
        mapSettings = mMap.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);//zoom
        //
		

        // // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        // mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
		
		// Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        // mMap.addMarker(new MarkerOptions().position(miPosicion).title("Mi posición"));
		mMap.addMarker(new MarkerOptions().position(miPosicion).title(nombreTienda));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(miPosicion));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(miPosicion, 15));
    }
}
