package com.teammovil.pettracker.di

import android.app.Application
import com.teammovil.data.adopter.AdopterExternalDataAccess
import com.teammovil.data.adopter.AdopterRepository
import com.teammovil.data.adopter.AdopterStorageDataAccess
import com.teammovil.data.pet.PetExternalDataAccess
import com.teammovil.data.pet.PetRepository
import com.teammovil.data.rescuer.RescuerExternalDataAccess
import com.teammovil.data.rescuer.RescuerRepository
import com.teammovil.data.rescuer.RescuerStorageDataAccess
import com.teammovil.pettracker.data.database.dataaccess.AdopterStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.data.database.dataaccess.RescuerStorageDataAccessDataBaseImpl
import com.teammovil.pettracker.data.services.AdopterExternalDataAccessServiceImpl
import com.teammovil.pettracker.data.services.PetExternalDataAccessServiceImpl
import com.teammovil.pettracker.data.services.RescuerExternalDataAccessServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAdopterRepository (
        externalDataAccess: AdopterExternalDataAccess,
        storageDataAccess: AdopterStorageDataAccess
    ) = AdopterRepository(externalDataAccess, storageDataAccess)

    @Provides
    @Singleton
    fun provideRescuerRepository (
        externalDataAccess: RescuerExternalDataAccess,
        storageDataAccess: RescuerStorageDataAccess
    ) = RescuerRepository (externalDataAccess, storageDataAccess)

    @Provides
    @Singleton
    fun providePetRepository (
        externalDataAccess: PetExternalDataAccess
    ) = PetRepository (externalDataAccess)


    @Provides
    fun provideAdopterExternalDataAccess (): AdopterExternalDataAccess = AdopterExternalDataAccessServiceImpl()

    @Provides
    fun provideAdopterStorageDataAccess (
        app: Application
    ): AdopterStorageDataAccess = AdopterStorageDataAccessDataBaseImpl(app)

    @Provides
    fun provideRescuerExternalDataAccess (): RescuerExternalDataAccess = RescuerExternalDataAccessServiceImpl()

    @Provides
    fun provideRescuerStorageDataAccess (
        app: Application
    ): RescuerStorageDataAccess = RescuerStorageDataAccessDataBaseImpl(app)

    @Provides
    fun providePetExternalDataAccess (): PetExternalDataAccess = PetExternalDataAccessServiceImpl()

    @Provides
    fun provideCoroutineDispatcher (): CoroutineDispatcher = Dispatchers.Main
}