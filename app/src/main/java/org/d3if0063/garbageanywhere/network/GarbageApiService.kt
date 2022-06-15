package org.d3if0063.garbageanywhere.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.d3if0063.garbageanywhere.database.GarbageDb
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface GarbageApiService {
    @GET("static-api.json")
    suspend fun getGarbage(): List<GarbageDb>
}

object GarbageApi{
    val service: GarbageApiService by lazy {
        retrofit.create(GarbageApiService::class.java)
    }

    fun getGarbageUrl(nama: String): String{
        return  "$BASE_URL$nama.jpg"
    }
}

enum class GarbageStatus{ LOADING, SUCCESS, FAILED}