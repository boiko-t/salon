package com.boiko.taisa.salon.mvp.home;

import com.boiko.taisa.salon.domain.entity.Category;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class HomeModel implements Home.Model {
    private static HomeModel INSTANCE;
    private BehaviorSubject<State> state = BehaviorSubject.create();
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference categoryNode = database.getReference("categories");

    public static HomeModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HomeModel();
        }
        return INSTANCE;
    }

    @Override
    public void loadCategoryCollection() {
        categoryNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Category> categoryList = new ArrayList<>();
                for (DataSnapshot categorySnapshot: dataSnapshot.getChildren()) {
                    String url = (String) categorySnapshot.child("icon").getValue();
                    String title = (String) categorySnapshot.child("name").getValue();
                    String description = (String) categorySnapshot.child("description").getValue();
                    categoryList.add(new Category(url, title, description));
                }
                state.onNext(new State(categoryList));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    public Observable<State> getStateObservable() {
        return state;
    }
}
