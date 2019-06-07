package com.boiko.taisa.salon.domain.mapper;

import com.google.firebase.database.DataSnapshot;

import java.util.List;
import java.util.Map;

public interface FirebaseEntityMapper<T> {
    List<T> mapCollection(DataSnapshot snapshot);
    T mapItem(DataSnapshot snapshot);

    Map<String, Object> mapToFirebaseObject(T item);
}
