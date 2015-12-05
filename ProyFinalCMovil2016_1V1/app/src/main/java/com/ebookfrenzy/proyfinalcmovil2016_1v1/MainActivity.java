package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.Fragment;
import android.widget.Toast;


import android.os.IBinder;
import android.content.Context;
import android.content.Intent;
import android.content.ComponentName;
import android.content.ServiceConnection;
import com.ebookfrenzy.proyfinalcmovil2016_1v1.BoundService.MyLocalBinder;


/**
 * Created by NORUEGO on 14/11/2015.
 */
public class MainActivity extends AppCompatActivity{

    private DrawerLayout drawerLayout;
    private NavigationView navView;

    BoundService myService;//gps
    boolean isBound = false;//gps

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        navView = (NavigationView)findViewById(R.id.navview);

        Intent intent = new Intent(this, BoundService.class);//levantar servicio gps
        bindService(intent, myConnection, Context.BIND_AUTO_CREATE);//levantar servicio gps

        navView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        double currentTime[] = myService.getCurrentTime();//levantar servicio gps

                        boolean fragmentTransaction = false;
                        Fragment fragment = null;

                        boolean miTienda = false;

                        switch (menuItem.getItemId()) {
                            case R.id.menu_escanear_caja:
                                fragment = new EscanearCajaActivity();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_listar_juegos:
                                fragment = new ListarJuegosActivity();
                                fragmentTransaction = true;
                                break;
                            case R.id.menu_ubicar_tienda:
                                fragment = new UbicarTiendaActivity();
                                fragmentTransaction = true;
                                miTienda = true;
                                break;
                            case R.id.menu_mini_juego:
                                fragment = new MiniJuegoActivity();
                                fragmentTransaction = true;
                                break;
                        }

                        if (fragmentTransaction) {
                            getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.content_frame, fragment)
                                    .commit();

                            menuItem.setChecked(true);
                            // getSupportActionBar().setTitle(menuItem.getTitle());
                            // if(miTienda)
                                // getSupportActionBar().setTitle("ProyFinalCMovil2016_1V1");
                        }

                        drawerLayout.closeDrawers();

                        return true;
                    }
                });
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

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/

}
