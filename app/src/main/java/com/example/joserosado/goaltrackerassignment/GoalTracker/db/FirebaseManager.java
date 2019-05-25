package com.example.joserosado.goaltrackerassignment.GoalTracker.db;

import android.util.Log;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Sprint;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.User;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.UserBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;

public final class FirebaseManager {
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private static final String AUTH_TAG = "Authorization";
    private static final String DB_TAG = "FirestoreDatabase";

    public FirebaseManager()
    {
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    public Task<AuthResult> createAccount(String email, String password) {
        return auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(resultTask -> {
                    if(resultTask.isSuccessful())
                    {
                        Log.d(AUTH_TAG, "createUserWithEmail:success");
                        addUser();
                        signOut();
                    }else
                    {
                        Log.w(AUTH_TAG, "createUserWithEmail:failure", resultTask.getException());
                    }
                });
    }

    public Task<AuthResult> signIn(String email, String password)
    {
       return auth.signInWithEmailAndPassword(email, password)
        .addOnCompleteListener(task ->
                {
                    if(task.isSuccessful())
                    {
                        Log.d(AUTH_TAG, "signInWithEmail:success");
                    }else
                    {
                        Log.w(AUTH_TAG, "signInWithEmail:failure", task.getException());
                    }
                });
    }

    public void signOut()
    {
        int result = 0;
        if(getSignedInUser() != null) {
            result = 1;
            auth.signOut();
        }
    }

    private void addUser()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("UserId", getSignedInUser().getUid());
        db.collection("users").document().set(map)
        .addOnSuccessListener(task -> Log.d(DB_TAG, "User added with ID: " + getSignedInUser().getUid()))
        .addOnFailureListener(exception -> Log.w(DB_TAG, "Error adding document", exception));
    }

    public Task<Void> updateUser(HashMap<String, Object> map)
    {
        FirebaseUser authUser = getSignedInUser();
        if(authUser == null) return null;
        return getUserDocument(authUser).set(map, SetOptions.merge())
                .addOnSuccessListener(task -> Log.d(DB_TAG, "User added with ID: " + getSignedInUser().getUid()))
                .addOnFailureListener(exception -> Log.w(DB_TAG, "Error adding document", exception));
    }

    public Sprint getCurrentSprint()
    {
        /*DocumentReference sprintRef = getCurrentSprintDocument(getSignedInUser());
        sprintRef.get().onCompleteListener(task ->
        {
        });*/
        return new Sprint();
    }

    public void updateSprint(Sprint sprint)
    {

    }

    public void deleteSprint(Sprint sprint)
    {

    }

    public FirebaseUser getSignedInUser()
    {
        return auth.getCurrentUser();
    }

    private DocumentReference getUserDocument(FirebaseUser user)
    {
        return db.collection("users").document(user.getUid());
    }

    private CollectionReference getSprintDocuments(FirebaseUser user)
    {
        return getUserDocument(getSignedInUser()).collection("sprints");
    }

    private DocumentReference getCurrentSprintDocument(FirebaseUser user)
    {
        /*DocumentReference sprintDoc =
        if(user != null) {
            getUserDocument(user).get().addOnCompleteListener(task ->
            {
                if (task.isSuccessful()) {
                    possibleSprint = task.getResult().getDocumentReference("currentSprint");
                }
            });
        }
        return possibleSprint;*/
        //TODO: find data
        return null;
    }

    public User getUserData()
    {
        FirebaseUser user = getSignedInUser();
        UserBuilder builder = new UserBuilder();
        getUserDocument(user).get();
        builder.id(user.getUid())
                .email(user.getEmail())
                .currentSprint(getCurrentSprint())
                .pastSprints(null);
        return builder.returnUser();
    }

}
