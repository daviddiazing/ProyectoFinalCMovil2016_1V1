package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.os.Bundle;

public class BoundService extends Service {


    private final IBinder myBinder = new MyLocalBinder();

    //int count=0;
    double Latitud[]={0.0,0.0};

    public BoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    public double[] getCurrentTime() {



        LocationManager locManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        final Location localizacion = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        //count++;

        LocationListener locListener = new LocationListener() {

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderEnabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onProviderDisabled(String provider) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onLocationChanged(Location localizacion) {
                //miTexto1.setText("Latitud: " + String.valueOf(localizacion.getLatitude()));
                //miTexto2.setText("Longitud: " + String.valueOf(localizacion.getLongitude()));

                //count++;
                //Latitud="Latitud: " + String.valueOf(localizacion.getLatitude()) + " Longitud: " + String.valueOf(localizacion.getLongitude()) + " count: " + String.valueOf(count);
                //Latitud[0]=String.valueOf(localizacion.getLatitude());
                //Latitud[1]=String.valueOf(localizacion.getLongitude());
                Latitud[0]=localizacion.getLatitude();
                Latitud[1]=localizacion.getLongitude();

            }
        };

        locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, locListener);




        if (localizacion != null){
            //  miTexto1.setText("Latitud: " + String.valueOf(localizacion.getLatitude()));
            //miTexto2.setText("Longitud: " + String.valueOf(localizacion.getLongitude()));
            //Latitud = "Latitud: " + String.valueOf(localizacion.getLatitude()) + " Longitud: " + String.valueOf(localizacion.getLongitude()) + " count: " + String.valueOf(count);

            //Latitud[0]=String.valueOf(localizacion.getLatitude());
            //Latitud[1]=String.valueOf(localizacion.getLongitude());
            Latitud[0]=localizacion.getLatitude();
            Latitud[1]=localizacion.getLongitude();
            return Latitud;
        } else {
            //miTexto1.setText("Sin datos de latitud.");
            //miTexto2.setText("Sin datos de longitud.");
            //Latitud = "SIN DATOS";
            Latitud[0]=10.0;
            Latitud[1]=-10.0;
            return Latitud;
        }



        //SimpleDateFormat dateformat =
        //      new SimpleDateFormat("HH:mm:ss MM/dd/yyyy",
        //            Locale.US);
        //return (dateformat.format(new Date()));
    }

    public class MyLocalBinder extends Binder {
        BoundService getService() {
            return BoundService.this;
        }
    }
}
