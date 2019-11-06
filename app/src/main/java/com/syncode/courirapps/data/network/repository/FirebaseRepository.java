package com.syncode.courirapps.data.network.repository;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.syncode.courirapps.data.model.TrackingModel;

public class FirebaseRepository {

    private FirebaseDatabase database;
    private Context context;

    public FirebaseRepository() {
        database = FirebaseDatabase.getInstance();
    }


    public FirebaseRepository(Context context) {
        this.context = context;
        database = FirebaseDatabase.getInstance();
    }

    public void setTrackingCoordinate(TrackingModel trackingModel) {
        DatabaseReference databaseReference = database.getReference().child(trackingModel.getIdTransaction());
        databaseReference.setValue(trackingModel);
    }


    public void deleteTrackingCoordinate(String idTransaction) {
        DatabaseReference databaseReference = database.getReference().child(idTransaction);
        databaseReference.removeValue((databaseError, databaseReference1) -> {

        });
    }
}
