package com.example.joserosado.goaltrackerassignment.GoalTracker.db;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.User;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.UserBuilder;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
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

    public int CreateAccount(String email, String password) {
        //TODO: Find a way to adjust result based on outcome
        int result = 0;
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(resultTask -> {
                    if(resultTask.isSuccessful())
                    {
                        Log.d(AUTH_TAG, "createUserWithEmail:success");
                        AddUser();
                        signOut();
                    }else
                    {
                        Log.w(AUTH_TAG, "createUserWithEmail:failure", resultTask.getException());
                    }
                });
        return result;
    }

    public int signIn(String email, String password)
    {
        int result = 0;
        auth.signInWithEmailAndPassword(email, password)
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
        return result;
    }

    public int signOut()
    {
        int result = 0;
        if(getSignedInUser() != null) {
            result = 1;
            auth.signOut();
        }
        return result;
    }

    private void AddUser()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("UserId", getSignedInUser().getUid());
        db.collection("users").document().set(map)
        .addOnSuccessListener(task -> Log.d(DB_TAG, "User added with ID: " + getSignedInUser().getUid()))
        .addOnFailureListener(exception -> Log.w(DB_TAG, "Error adding document", exception));
    }

    public void UpdateUser(HashMap<String, Object> map)
    {
        FirebaseUser authUser = getSignedInUser();
        if(authUser == null) return;
        getUserDocument(authUser).set(map, SetOptions.merge())
                .addOnSuccessListener(task -> Log.d(DB_TAG, "User added with ID: " + getSignedInUser().getUid()))
                .addOnFailureListener(exception -> Log.w(DB_TAG, "Error adding document", exception));
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
        DocumentReference userSprint = getUserDocument(user);
        getSprintDocuments(user).document();
        return null;
    }

    public User getUserData()
    {
        UserBuilder builder = new UserBuilder();
        getUserDocument(getSignedInUser()).get();
        //.addOn;
        return null;
    }

}
