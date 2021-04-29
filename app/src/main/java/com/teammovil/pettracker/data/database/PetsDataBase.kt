package com.teammovil.pettracker.data.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase

abstract class PetsDataBase: RoomDatabase() {





    companion object{

        private var INSTANCE: PetsDataBase? = null

        fun getDatabase(context: Context): PetsDataBase{
            if (INSTANCE == null){
                INSTANCE = Room.databaseBuilder(context, PetsDataBase::class.java, "PetsDataBase")
                    .build()
            }
            return INSTANCE as PetsDataBase
        }

    }
}