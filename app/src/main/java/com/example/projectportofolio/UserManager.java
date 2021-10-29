package com.example.projectportofolio;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class UserManager {
    private static DocumentSnapshot instance;

    public static DocumentSnapshot getInstance() {
        return instance;
    }

    public static void setInstance(DocumentSnapshot instance) {
        UserManager.instance = instance;
    }
}
