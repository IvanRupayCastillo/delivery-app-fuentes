package com.yobel.lecturadeliveryapp.di

import android.content.Context
import android.content.SharedPreferences
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.endpoints.MethodsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    @Named("provideEnterprisesSharePreferences")
    fun provideSharePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("PREFERENCES_LIST_ENTERPRISE", 0)
    }

    @Provides
    @Singleton
    @Named("sharedPreferencesEnterpriseSelected")
    fun provideEnterpriseSelectedSharePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("PREFERENCES_ENTERPRISE", 0)
    }

    @Provides
    @Singleton
    @Named("provideDataSharePreferences")
    fun provideDataSharePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("PREFERENCES_DATA", 0)
    }


}