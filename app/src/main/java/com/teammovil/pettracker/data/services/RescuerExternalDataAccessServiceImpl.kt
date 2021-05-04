package com.teammovil.pettracker.data.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.teammovil.data.rescuer.RescuerExternalDataAccess
import kotlinx.coroutines.tasks.await

class RescuerExternalDataAccessServiceImpl():
    RescuerExternalDataAccess {
    private val serviceFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val serviceFirebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun login(user: String, password: String): com.teammovil.domain.Rescuer? {
        try {
            val result = serviceFirebaseAuth.signInWithEmailAndPassword(user, password).await()
            if (result != null) {
                val resultGetUser = serviceFirebaseFirestore.collection(Constants.RESCUER_COLLECTION).document(user).get().await()
                if(resultGetUser!=null)
                    return Mapper.mapRescuer(resultGetUser)
            }
        }catch (e: Exception){
            return null
        }
        return null
    }

    override suspend fun registerRescuer(rescuer: com.teammovil.domain.Rescuer): Boolean {
        try {
            val result = serviceFirebaseAuth
                .createUserWithEmailAndPassword(rescuer.email, rescuer.password).await()
            return if(result != null) {
                FirebaseFirestore.getInstance().collection(Constants.RESCUER_COLLECTION)
                    .document(rescuer.email).set(
                        Mapper.map(rescuer)
                    )
                true
            } else false
        }
        catch (e: Exception){
            return false
        }
    }
}