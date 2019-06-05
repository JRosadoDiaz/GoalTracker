package com.example.joserosado.goaltrackerassignment.GoalTracker.db;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Event;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.Sprint;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.User;
import com.example.joserosado.goaltrackerassignment.GoalTracker.Models.UserBuilder;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    private Task<Void> addUser()
    {
        FirebaseUser user = getSignedInUser();
        HashMap<String, Object> map = new HashMap<>();
        map.put("UserId", user.getUid());
        return db.collection("users").document(user.getUid()).set(map)
        .addOnSuccessListener(task
                -> Log.d(DB_TAG, "User added with ID: " + getSignedInUser().getUid()))
        .addOnFailureListener(exception
                -> Log.w(DB_TAG, "Error adding document", exception));

    }

    public Task<Void> updateUser(User user)
    {
        FirebaseUser authUser = getSignedInUser();
        if(authUser == null) return null;
        return getUserDocument(authUser).set(user, SetOptions.merge())
                .addOnSuccessListener(task
                        -> Log.d(DB_TAG, "User with ID: " + getSignedInUser().getUid() + " updated"))
                .addOnFailureListener(exception
                        -> Log.w(DB_TAG, "Error updating document", exception));
    }

    public Task<Sprint> getCurrentSprint(){
        FirebaseUser user = getSignedInUser();
        DocumentReference ref = getCurrentSprintDocument(user);
        return (ref == null ? createNewSprint() : getSprint(ref));
    }

    private Task<Sprint>getSprint(DocumentReference sprintRef)
    {
        Task<List<Event>> sprintEventTask = getEventsFromSprint(sprintRef);
        return sprintEventTask.onSuccessTask(result -> Tasks.call(() ->
        {
            Sprint sprint = new Sprint();
            sprint.setSprintId(sprintRef.getId());
            sprint.Events.clear();
            sprint.Events.addAll(result);
            return sprint;
        }));
    }

    public Task<Sprint> createNewSprint()
    {
        Task<Sprint> createSprint = Tasks.call(this::createNewSprintMethod)
                .continueWith(task -> {
                    setCurrentSprint(task.getResult());
                    return task.getResult();
                });
        return createSprint;
    }

    public Task<Void> addEventToSprint(Event event, Sprint sprint)
    {
        Task<Void> task = null;
        if(event.getId() == null || event.getId().isEmpty())
        {
            task = createEvent(event);
        }
        if(task != null)
        {
            task.continueWithTask(nothing -> Tasks.call(() ->
            {
                return tagEventInSprint(event, sprint);
            }));
        }
        else
        {
            task = tagEventInSprint(event, sprint);
        }
        return task;
    }

    private Task<Void> tagEventInSprint(Event event, Sprint sprint)
    {
        DocumentReference sprintRef = getSprintDocuments(getSignedInUser()).document(sprint.getSprintId());
        DocumentReference eventRef = getEventDocumentById(event);
        return sprintRef.get().onSuccessTask(result -> {
            List<DocumentReference> events = (List<DocumentReference>) result.get("events");
            events.add(eventRef);
            return sprintRef.update("events", events);
        });
    }

    public Task<List<Event>> getEventsFromSprint(Sprint sprint)
    {
        return getEventsFromSprint(getSprintDocuments(getSignedInUser()).document(sprint.getSprintId()));
    }

    public Task<List<Event>>  getEventsFromSprint(DocumentReference sprintDocRef)
    {
        return sprintDocRef.get().onSuccessTask(document -> {
            return Tasks.call(() -> {
                List<DocumentReference> eventRefs;
                eventRefs = document.get("events", new ArrayList<DocumentReference>().getClass());
                List<Task<Event>> tasks = new ArrayList<>();
                Task<List<Event>> taskPipeline = null;
                for (int i = 0; i < eventRefs.size(); i++) {
                    Task<Event> eventTask = eventRefs.get(i).get().onSuccessTask(eventDoc -> Tasks.call(() -> {
                        Map<String, Object> eventData = eventDoc.getData();
                        return Event.pullData(eventData, eventDoc.getId());
                    }));
                    if (taskPipeline == null) {
                        taskPipeline = eventTask.continueWithTask(pipe -> Tasks.call(() -> {
                            List<Event> events = new ArrayList<Event>();
                            events.add(pipe.getResult());
                            return events;
                        }));
                    } else {
                        taskPipeline.continueWithTask(events -> eventTask.continueWithTask(event -> Tasks.call(() ->
                        {
                            return events.getResult().add(event.getResult());
                        })));
                    }
                }
                return taskPipeline.getResult();
            });
        });
    }

    public Task<Void> createEvent(Event event)
    {
        HashMap<String, Object> eventData = buildEventData(event);
        DocumentReference reference = db.collection("events").document();
        return reference.set(eventData)
                .onSuccessTask(result -> Tasks.call(() -> {
                event.setId(reference.getId());
                return null;
        }));
    }

    public Task<Void> updateEvent(Event event)
    {
        HashMap<String, Object> eventData = buildEventData(event);
        DocumentReference ref = db.collection("events").document(event.getId());
        return ref.update(eventData).addOnCompleteListener( result ->
        {
            StringBuilder resultBuilder = new StringBuilder("UpdateEvent:").append(event.getId()).append(':');
            resultBuilder.append( result.isSuccessful() ? "Success" :
                    result.isCanceled() ? "Canceled" : "Fail");
            Log.d(DB_TAG, resultBuilder.toString());
        });
    }

    private DocumentReference getEventDocumentById(Event event)
    {
        return db.collection("events").document(event.getId());
    }

    public Task<Void> deleteEvent(Event event)
    {
        DocumentReference ref = db.collection("events").document(event.getId());
        return ref.delete().addOnSuccessListener(nothing -> event.setId(""));
    }

    private HashMap<String, Object> buildEventData(Event event)
    {
        HashMap<String, Object> eventData = new HashMap<>();
        eventData.put("title", event.getTitle());
        eventData.put("isDone", event.getIsDone());
        //TODO:find out/establish the date format used in front-end
        //eventData.put("startDate", new Timestamp(new Date()))
        eventData.put("description", event.getDescription());
        eventData.put("duration", event.getDuration());
        eventData.put("userId", getSignedInUser().getUid());
        return eventData;
    }

    private Sprint createNewSprintMethod() {
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

    //TODO: Possible problem
    public Task<List<Sprint>> getPastSprints()
    {
        FirebaseUser user = getSignedInUser();
        Task<QuerySnapshot> query = getSprintDocuments(user).get();
        Task<List<Sprint>> sprintsTask = query.continueWith(task -> {
            QuerySnapshot snapshot = task.getResult();
            if(snapshot.isEmpty()) return null;
            List<Task<Sprint>> sprintTasks = snapshot.getDocuments().stream()
                .filter(DocumentSnapshot::exists)
                .map(documentSnapshot -> getSprint(documentSnapshot.getReference()))
                .collect(Collectors.toList());
            return Tasks.whenAllComplete(sprintTasks).getResult().stream()
            .filter(Task::isSuccessful)
            .map(sprintTask ->{ return (Sprint)sprintTask.getResult();})
            .collect(Collectors.toList());
        });
        return sprintsTask;
    }

    public Task<Void> setCurrentSprint(Sprint sprint)
    {
        FirebaseUser user = getSignedInUser();
        return getUserDocument(user).update("currentSprint",
                getSprintDocuments(user).document(sprint.getSprintId()));
    }

    public Task<Void> archiveCurrentSprint()
    {
        DocumentReference sprintRef = getUserDocument(getSignedInUser());
        return sprintRef.update("currentSprint", null);
    }

    public Task<Void> updateSprint(Sprint sprint)
    {
        DocumentReference sprintRef = getSprintDocuments(getSignedInUser()).document(sprint.getSprintId());
        return sprintRef.set(sprint, SetOptions.merge()).addOnCompleteListener(task ->
        {
           if(task.isSuccessful()) Log.d(DB_TAG, String.format("Sprint %s was updated successfully", sprint.getSprintId()));
               else Log.d(DB_TAG, "Sprint update failed", task.getException());
        });
    }

    public Task<Void> deleteSprint(Sprint sprint)
    {
        DocumentReference sprintRef = getSprintDocuments(getSignedInUser()).document(sprint.getSprintId());
        return sprintRef.delete().addOnCompleteListener(task ->
        {
            if(task.isSuccessful()) {
                Log.d(DB_TAG, String.format("Sprint %s was deleted successfully", sprint.getSprintId()));
                sprint.setSprintId("");
            }
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

    public Task<User> getUserData()
    {
        FirebaseUser user = getSignedInUser();
        getUserDocument(user);
        Task<List<Sprint>> sprints = getPastSprints();
        Task<Sprint> currentSprint = getCurrentSprint();
        return Tasks.whenAllComplete(sprints, currentSprint).continueWithTask(
                finishedTasks -> Tasks.call( () ->{
                    UserBuilder builder = new UserBuilder();
                    builder.pastSprints(sprints.getResult())
                            .currentSprint(currentSprint.getResult())
                            .id(user.getUid());
                    return builder.returnUser();
                }));
    }

}
