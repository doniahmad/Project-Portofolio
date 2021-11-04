package com.example.projectportofolio.auth;

import com.google.firebase.firestore.DocumentSnapshot;

public class UserManager {
    private static DocumentSnapshot instance;

    public static DocumentSnapshot getInstance() {
        return instance;
    }

    public static void setInstance(DocumentSnapshot instance) {
        UserManager.instance = instance;
    }
}
