package helloandroid.m2dl.earthquake.game.mobengine.activities;

import android.app.Activity;
import android.view.View;
import android.view.WindowManager;

import helloandroid.m2dl.earthquake.game.mobengine.core.GameEngine;
import helloandroid.m2dl.earthquake.game.mobengine.resource_stores.SoundStore;
import helloandroid.m2dl.earthquake.game.mobengine.utils.ActiveActivitiesTracker;

public class SoundActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();
        ActiveActivitiesTracker.activityStarted();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActiveActivitiesTracker.activityStopped();
    }
}
