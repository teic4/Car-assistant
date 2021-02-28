package com.example.carassistant.di
import android.app.Application
import androidx.room.Room

import com.example.carassistant.data.room.CarAssistantDatabase
import com.example.carassistant.data.room.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ) = Room.databaseBuilder(app, CarAssistantDatabase::class.java, "CarAssistantDB.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideCarDao(db: CarAssistantDatabase) = db.getDao()

    @Singleton
    @Provides
    fun provideRepository(db: CarAssistantDatabase) = Repository(db)

}