package com.yobel.lecturadeliveryapp.di

import android.content.SharedPreferences
import com.jotadev.jetcompose_2024_ii_ecoeats.data.networking.endpoints.MethodsApi
import com.yobel.lecturadeliveryapp.data.repository.AuthRepositoryImp
import com.yobel.lecturadeliveryapp.data.repository.LabelRepositoryImp
import com.yobel.lecturadeliveryapp.domain.repository.AuthRepository
import com.yobel.lecturadeliveryapp.domain.repository.LabelRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        @Named("provideEnterprisesSharePreferences") sharedPreferences: SharedPreferences,
        @Named("sharedPreferencesEnterpriseSelected") sharedPreferencesEntepriseSelected: SharedPreferences,
        api: MethodsApi,
    ): AuthRepository {
        return AuthRepositoryImp(sharedPreferences,sharedPreferencesEntepriseSelected,api)
    }

    @Provides
    @Singleton
    fun provideLabelRepository(
        api: MethodsApi
    ): LabelRepository {
        return LabelRepositoryImp(api)
    }

}