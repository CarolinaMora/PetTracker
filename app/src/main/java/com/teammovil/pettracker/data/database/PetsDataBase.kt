package com.teammovil.pettracker.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.teammovil.pettracker.data.database.dao.AdopterDao
import com.teammovil.pettracker.data.database.entities.AdopterEntity

@Database(entities = [AdopterEntity::class], version = 1, exportSchema = false)
@TypeConverters(ConvertersForDate::class)
abstract class PetsDataBase: RoomDatabase() {

    abstract fun adopterDao(): AdopterDao




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