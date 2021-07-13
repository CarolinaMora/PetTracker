package com.teammovil.pettracker.data.services

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.teammovil.data.adopter.AdopterExternalDataAccess
import com.teammovil.domain.Adopter
import kotlinx.coroutines.tasks.await

class AdopterExternalDataAccessServiceImpl():
    com.teammovil.data.adopter.AdopterExternalDataAccess {
    private val serviceFirebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val serviceFirebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun login(user: String, password: String): com.teammovil.domain.Adopter? {
        try {
            val result = serviceFirebaseAuth.signInWithEmailAndPassword(user, password).await()
            if (result != null) {
                val resultGetUser = serviceFirebaseFirestore.collection(Constants.ADOPTER_COLLECTION).document(user).get().await()
                if(resultGetUser!=null)
                    return Mapper.mapAdopter(resultGetUser)
            }
        }catch (e: Exception){
            return null
        }
        return null
    }

    override suspend fun registerAdopter(adopter: com.teammovil.domain.Adopter): Boolean {
        try {
            val result = serviceFirebaseAuth
                .createUserWithEmailAndPassword(adopter.email, adopter.password).await()
            return if(result != null) {
                serviceFirebaseFirestore.collection(Constants.ADOPTER_COLLECTION)
                    .document(adopter.email).set(
                        Mapper.map(adopter)
                    )
                true
            } else false
        }
        catch (e: Exception){
            return false
        }
    }

    override suspend fun getAllAdopters(): List<com.teammovil.domain.Adopter> {
        try{
            val result = serviceFirebaseFirestore.collection(Constants.ADOPTER_COLLECTION).get().await()
            return result.documents.map { Mapper.mapAdopter(it) }
        }
        catch (e: Exception){
            return listOf()
        }
    }
}