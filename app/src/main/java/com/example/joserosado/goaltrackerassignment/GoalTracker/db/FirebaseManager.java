package com.example.joserosado.goaltrackerassignment.GoalTracker.db;

import android.util.Log;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Sprint;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.User;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.UserBuilder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
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
        if(getSignedInUser() != null) {
            auth.signOut();
        }
    }

    private void addUser()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("UserId", getSignedInUser().getUid());
        db.collection("users").document().set(map)
        .addOnSuccessListener(task
                -> Log.d(DB_TAG, "User added with ID: " + getSignedInUser().getUid()))
        .addOnFailureListener(exception
                -> Log.w(DB_TAG, "Error adding document", exception));
    }

    public void updateUser(User user)
    {
        FirebaseUser authUser = getSignedInUser();
        if(authUser == null) return;
        getUserDocument(authUser).set(user, SetOptions.merge())
                .addOnSuccessListener(task
                        -> Log.d(DB_TAG, "User with ID: " + getSignedInUser().getUid() + " updated"))
                .addOnFailureListener(exception
                        -> Log.w(DB_TAG, "Error updating document", exception));
    }

    public Sprint getCurrentSprint(){
        DocumentReference sprintRef = getCurrentSprintDocument(getSignedInUser());
        Sprint taskedSprint = null;
        Task<Sprint> getSprint = sprintRef.get().onSuccessTask(result -> Tasks.call(()->
        {
            Sprint sprint = new Sprint();
            sprint.setSprintId(result.getId());
            //Retrieve Events
            //sprint.Events.add();
            return sprint;
        }));
        taskedSprint = getSprint.isSuccessful() ? getSprint.getResult() : createNewSprint();
        return taskedSprint;
    }

    public Sprint createNewSprint() {
        Sprint sprint = new Sprint();
        sprint.setSprintId("StartingValue");
        CollectionReference sprintsRef = getSprintDocuments(getSignedInUser());
        sprintsRef.add(sprint).addOnSuccessListener(document ->{
           sprint.setSprintId(document.getId());
           document.update("sprintId", sprint.getSprintId())
                   .addOnSuccessListener(updateSuccessTask
                           -> Log.d(DB_TAG, "Sprint Creation Successful"))
                   .addOnFailureListener(updateFailedTask
                           ->  Log.d(DB_TAG, "Sprint Creation Update Failed", updateFailedTask));
        }).addOnFailureListener(failedCreationTask
                -> Log.d(DB_TAG, "Sprint Creation Failed", failedCreationTask));
        return sprint;
    }

    public void archiveCurrentSprint()
    {
        DocumentReference sprintRef = getUserDocument(getSignedInUser());
        sprintRef.update("currentSprint", null);
    }

    public void updateSprint(Sprint sprint)
    {
        DocumentReference sprintRef = getSprintDocuments(getSignedInUser()).document(sprint.getSprintId());
        sprintRef.set(sprint, SetOptions.merge()).addOnCompleteListener(task ->
        {
           if(task.isSuccessful()) Log.d(DB_TAG, String.format("Sprint %s was updated successfully", sprint.getSprintId()));
               else Log.d(DB_TAG, "Sprint update failed", task.getException());
        });
    }

    public void deleteSprint(Sprint sprint)
    {
        DocumentReference sprintRef = getSprintDocuments(getSignedInUser()).document(sprint.getSprintId());
        sprintRef.delete().addOnCompleteListener(task ->
        {
            if(task.isSuccessful()) Log.d(DB_TAG, String.format("Sprint %s was deleted successfully", sprint.getSprintId()));
            else Log.d(DB_TAG, "Sprint delete failed", task.getException());
        });
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
        DocumentReference reference = null;
        Task<DocumentReference> referenceTask = getUserDocument(user)
                .get().onSuccessTask(snapshot ->
                        Tasks.call(() -> snapshot.getDocumentReference("currentSprint")));
        if(referenceTask.isSuccessful()) reference = referenceTask.getResult();
        return reference;
    }

    public User getUserData()
    {
        FirebaseUser user = getSignedInUser();
        getUserDocument(user).get();
        /*builder.id(user.getUid())
                .email(user.getEmail())
                .currentSprint(getCurrentSprint())
                .pastSprints(null);*/
        return null;//builder.returnUser();
    }

}
