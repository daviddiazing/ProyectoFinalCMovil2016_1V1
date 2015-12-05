package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EscanearCajaActivity extends Fragment{
    public EscanearCajaActivity () {
        // Required empty public constructor
    }


    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        Intent mainIntent = new Intent().setClass(
                getActivity(), SimpleClientTrackingActivity.class);
        startActivity(mainIntent);
    }

}
