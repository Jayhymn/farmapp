package com.jayhymn.farmapp.ui.di

import android.content.Context
import androidx.room.Room
import com.jayhymn.farmapp.data.db.FarmerDao
import com.jayhymn.farmapp.data.db.FarmerDb
import com.jayhymn.farmapp.domain.FormatDateUseCase
import com.jayhymn.farmapp.domain.RegisterFarmerInteractor
import com.jayhymn.farmapp.domain.ValidateCropTypeUseCase
import com.jayhymn.farmapp.domain.ValidateFirstNameUseCase
import com.jayhymn.farmapp.domain.ValidateLastNameUseCase
import com.jayhymn.farmapp.domain.ValidatePhoneNumberUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFarmerDb(
        @ApplicationContext context: Context
    ): FarmerDb {
        return Room.databaseBuilder(
            context,
            FarmerDb::class.java,
            "farmer_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFarmerDao(farmerDb: FarmerDb): FarmerDao {
        return farmerDb.farmerDao()
    }

    @Provides
    @Singleton
    fun provideFormatDateUseCase(): FormatDateUseCase {
        return FormatDateUseCase()
    }

    @Provides
    @Singleton
    fun provideValidateFirstNameUseCase() = ValidateFirstNameUseCase()

    @Provides
    @Singleton
    fun provideValidateLastNameUseCase() = ValidateLastNameUseCase()

    @Provides
    @Singleton
    fun provideValidatePhoneNumberUseCase() = ValidatePhoneNumberUseCase()

    @Provides
    @Singleton
    fun provideValidateCropTypeUseCase() = ValidateCropTypeUseCase()

    @Provides
    @Singleton
    fun provideRegisterFarmerInteractor(firstNameUseCase: ValidateFirstNameUseCase,
                                        lastNameUseCase: ValidateLastNameUseCase,
                                        phoneUseCase: ValidatePhoneNumberUseCase,
                                        cropTypeUseCase: ValidateCropTypeUseCase
    ): RegisterFarmerInteractor {
        return RegisterFarmerInteractor(firstNameUseCase,lastNameUseCase, phoneUseCase, cropTypeUseCase)
    }
}