package com.boiko.taisa.salon.mvp.visit;

import com.boiko.taisa.salon.dal.mapper.FirebaseVisitMapper;
import com.boiko.taisa.salon.domain.entity.Product;
import com.boiko.taisa.salon.domain.entity.SalonService;
import com.boiko.taisa.salon.domain.entity.Visit;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class NewVisitModel implements NewVisit.Model {
    private static NewVisitModel INSTANCE;
    private BehaviorSubject<State> stateObservable = BehaviorSubject.create();
    private State state = new State(new ArrayList<>(), new ArrayList<>(), new Visit());

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference servicesNode = database.getReference("services");
    private DatabaseReference productsNode = database.getReference("products");
    private DatabaseReference visitNode = database.getReference("visits");

    public static NewVisitModel getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NewVisitModel();
        }
        return INSTANCE;
    }

    @Override
    public void loadBaseData() {
        servicesNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<SalonService> servicesList = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String description = (String) snapshot.child("description").getValue();
                    String name = (String) snapshot.child("name").getValue();
                    int price = new BigDecimal((long)snapshot.child("price").getValue()).intValueExact();
                    servicesList.add(new SalonService(snapshot.getKey(), name, description, price));
                }
                state.services = servicesList;
                stateObservable.onNext(state);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
        productsNode.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<Product> productsList = new ArrayList<>();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String name = (String) snapshot.child("name").getValue();
                    String description = (String) snapshot.child("description").getValue();
                    String unit = (String) snapshot.child("unit").getValue();
                    String categoryId = (String) snapshot.child("categoryId").getValue();
                    int price = new BigDecimal((long)snapshot.child("price").getValue()).intValueExact();
                    productsList.add(new Product(snapshot.getKey(), name, description, categoryId, unit, price));
                }
                state.products = productsList;
                stateObservable.onNext(state);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
            }
        });
    }

    @Override
    public void submitVisit() {
        FirebaseVisitMapper mapper = new FirebaseVisitMapper();
        state.visit.setMasterName(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        String key = visitNode.push().getKey();
        Map<String, Object> visitUpdate = new HashMap<>();
        visitUpdate.put("visit" + key, mapper.mapToFirebaseObject(state.visit));
        visitNode.updateChildren(visitUpdate);
    }

    @Override
    public State getState() {
        return state;
    }

    @Override
    public Observable<State> getStateObservable() {
        return stateObservable;
    }
}
