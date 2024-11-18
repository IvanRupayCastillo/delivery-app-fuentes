package com.yobel.lecturadeliveryapp.di

import android.content.Context
import androidx.room.Room
import com.yobel.lecturadeliveryapp.data.database.AppDatabase
import com.yobel.lecturadeliveryapp.data.database.dao.LabelDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "DB_DELIVERY"
    ).build()

    @Provides
    @Singleton
    fun provideLabelDao(appDatabase: AppDatabase): LabelDao =
        appDatabase.labelDao()

}