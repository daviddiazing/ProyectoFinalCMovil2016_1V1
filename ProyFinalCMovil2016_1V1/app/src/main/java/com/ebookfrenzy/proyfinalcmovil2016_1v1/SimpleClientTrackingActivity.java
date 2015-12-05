package com.ebookfrenzy.proyfinalcmovil2016_1v1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.ebookfrenzy.proyfinalcmovil2016_1v1.rendering.external.CustomSurfaceView;
import com.ebookfrenzy.proyfinalcmovil2016_1v1.rendering.external.Driver;
import com.ebookfrenzy.proyfinalcmovil2016_1v1.rendering.external.GLRenderer;
import com.wikitude.WikitudeSDK;
import com.wikitude.WikitudeSDKStartupConfiguration;
import com.wikitude.common.camera.CameraSettings;
import com.wikitude.common.rendering.RenderExtension;
import com.wikitude.common.tracking.RecognizedTarget;
import com.wikitude.rendering.ExternalRendering;
import com.wikitude.tracker.ClientTracker;
import com.wikitude.tracker.ClientTrackerEventListener;
import com.wikitude.tracker.Tracker;

public class SimpleClientTrackingActivity extends Activity implements ClientTrackerEventListener, ExternalRendering {

    private static final String TAG = "SimpleClientTracking";

    private WikitudeSDK _wikitudeSDK;
    private CustomSurfaceView _view;
    private Driver _driver;
    private GLRenderer _glRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _wikitudeSDK = new WikitudeSDK(this);
        WikitudeSDKStartupConfiguration startupConfiguration = new WikitudeSDKStartupConfiguration(WikitudeSDKConstants.WIKITUDE_SDK_KEY, CameraSettings.CameraPosition.BACK, CameraSettings.CameraFocusMode.CONTINUOUS);
        _wikitudeSDK.onCreate(getApplicationContext(), startupConfiguration);
        ClientTracker tracker = _wikitudeSDK.getTrackerManager().create2dClientTracker("file:///android_asset/tracker.wtc");
        tracker.registerTrackerEventListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        _wikitudeSDK.onResume();
        _view.onResume();
        _driver.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        _wikitudeSDK.onPause();
        _view.onPause();
        _driver.stop();
    }

    @Override
    protected void onDestroy() {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
        _wikitudeSDK.onDestroy();
    }

    @Override
    public void onRenderExtensionCreated(final RenderExtension renderExtension_) {
        Log.v(TAG, "onRenderExtensionCreated");
        _glRenderer = new GLRenderer(renderExtension_);
        _view = new CustomSurfaceView(getApplicationContext(), _glRenderer);
        _driver = new Driver(_view, 30);
        setContentView(_view);
    }

    @Override
    public void onErrorLoading(final ClientTracker clientTracker_, final String errorMessage_) {
        Log.v(TAG, "onErrorLoading: " + errorMessage_);
    }

    @Override
    public void onTrackerFinishedLoading(final ClientTracker clientTracker_, final String trackerFilePath_) {
        Log.v(TAG, "onTrackerFinishedLoading");
    }

    @Override
    public void onTargetRecognized(final Tracker tracker_, final String targetName_) {
        Log.v(TAG, "TargetRecognized: " + targetName_);

        // Muestra el video en otro activity
        Intent i = new Intent(this, Reconocimiento.class);
        i.putExtra("videoIndex", targetName_);
        startActivity(i);
    }

    @Override
    public void onTracking(final Tracker tracker_, final RecognizedTarget recognizedTarget_) {
        //Log.v(TAG, "onTracking");
        _glRenderer.setCurrentlyRecognizedTarget(recognizedTarget_);
    }

    @Override
    public void onTargetLost(final Tracker tracker_, final String targetName_) {
        Log.v(TAG, "onTargetLost");
        _glRenderer.setCurrentlyRecognizedTarget(null);
    }

    @Override
    public void onExtendedTrackingQualityUpdate(final Tracker tracker_, final String targetName_, final int oldTrackingQuality_, final int newTrackingQuality_) {

    }
}