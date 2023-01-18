package com.mugveiga.coinsalert.data.api

import com.google.gson.GsonBuilder
import com.mugveiga.coinsalert.data.model.CoinPrice
import com.mugveiga.coinsalert.data.model.parser.CoinPriceParser
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


const val BASE_URL = "https://api.coingecko.com"
const val DEFAULT_TIMEOUT_SECONDS = 60L

object CoinGeckoClient {

    fun getClient(): CoinGeckoInterface {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

        val gsonBuilder = GsonBuilder()
        gsonBuilder.registerTypeAdapter(CoinPrice::class.java, CoinPriceParser())

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
            .build()
            .create(CoinGeckoInterface::class.java)
    }
}
