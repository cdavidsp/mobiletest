package com.cesarsosa.mobiletest.di

import android.app.Application
import com.cesarsosa.mobiletest.api.MarvelService
import com.cesarsosa.mobiletest.data.AppDatabase
import com.cesarsosa.mobiletest.characters.data.CharacterRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideCharacterService(@MarvelAPI okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, MarvelService::class.java)


    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(marvelService: MarvelService)
            = CharacterRemoteDataSource(marvelService)

    @MarvelAPI
    @Provides
    fun providePrivateOkHttpClient(
            upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder()
                .build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.characterDao()

    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(MarvelService.ENDPOINT)
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
