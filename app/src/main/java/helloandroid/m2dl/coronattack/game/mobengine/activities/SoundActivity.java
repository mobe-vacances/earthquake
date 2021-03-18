package helloandroid.m2dl.coronattack.game.mobengine.activities;

import android.app.Activity;

import helloandroid.m2dl.coronattack.game.mobengine.utils.ActiveActivitiesTracker;

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
