package helloandroid.m2dl.coronattack.game_over;

import com.google.firebase.installations.FirebaseInstallations;

public class FirebaseInstallationService {

    private static String id;

    public static void init() {
        FirebaseInstallations.getInstance().getId()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        id = task.getResult();
                        HighscoreHandler.init();
                    }
                });
    }

    public static String getId() {
        return id;
    }
}
