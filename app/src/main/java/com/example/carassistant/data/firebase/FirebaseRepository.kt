package com.example.carassistant.data.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseRepository {

    private val firebaseFirestore = FirebaseFirestore.getInstance()

    suspend fun getAllCarCompaniesSnapshot(): QuerySnapshot =
        firebaseFirestore.collection("auti").get().await()

    suspend fun getCarModelDocumentSnapshot(Id: String) =
        firebaseFirestore.collection("auti").document(Id)
            .collection("modeli").get().await()
}