package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Context;
import android.content.Intent;

public class MiniJuegoActivity extends Fragment{

    public MiniJuegoActivity () {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_mini_juego, container, false);
    }
	
	
	@Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        Intent mainIntent = new Intent().setClass(
                getActivity(), BuscarPalabraActivity.class);
        startActivity(mainIntent);
    }
}
