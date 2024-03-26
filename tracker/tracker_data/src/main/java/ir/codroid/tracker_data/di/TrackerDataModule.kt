package ir.codroid.tracker_data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ir.codroid.tracker_data.local.TrackerDataBase
import ir.codroid.tracker_data.remote.OpenFoodApi
import ir.codroid.tracker_data.repository.TrackerRepositoryImpl
import ir.codroid.tracker_domain.repository.TrackerRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object TrackerDataModule {

    @Provides
    @Singleton
    fun providesOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .build()

    @Provides
    @Singleton
    fun providesOpenFoodApi(client: OkHttpClient): OpenFoodApi =
        Retrofit.Builder()
            .baseUrl(OpenFoodApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create()

    @Provides
    @Singleton
    fun providesTrackerDatabase(app: Application): TrackerDataBase =
        Room.databaseBuilder(
            app,
            TrackerDataBase::class.java,
            "tracker_db"
        ).build()

    @Provides
    @Singleton
    fun providesTrackerRepository(
        api: OpenFoodApi,
        db: TrackerDataBase
    ): TrackerRepository {
        return TrackerRepositoryImpl(
            dao = db.trackerDao,
            api = api
        )
    }
}