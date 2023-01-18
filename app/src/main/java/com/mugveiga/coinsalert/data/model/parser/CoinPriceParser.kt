package com.mugveiga.coinsalert.data.model.parser

import com.google.gson.*
import com.mugveiga.coinsalert.data.model.CoinPrice
import java.lang.reflect.Type

class CoinPriceParser : JsonDeserializer<CoinPrice> {

    override fun deserialize(json: JsonElement?, typeOfT: Type?, context: JsonDeserializationContext?): CoinPrice {
        json?.asJsonObject?.entrySet()?.iterator()?.next().apply {
            val id = this?.key
            val coinPriceObj: JsonObject = this?.value?.asJsonObject as JsonObject
            coinPriceObj.addProperty("id", id)
            return Gson().fromJson(coinPriceObj, CoinPrice::class.java)
        }
    }
}
