package com.quran.app.utils

import com.quran.app.model.*
import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

object QuranApi {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()
    private val gson = Gson()

    suspend fun getSurahs(): List<Surah> = withContext(Dispatchers.IO) {
        val req = Request.Builder().url("https://api.alquran.cloud/v1/surah").build()
        val res = client.newCall(req).execute()
        val body = res.body?.string() ?: return@withContext emptyList()
        val response = gson.fromJson(body, SurahResponse::class.java)
        response.data.surahs
    }

    suspend fun getAyahsWithChinese(surahNumber: Int): List<Ayah> = withContext(Dispatchers.IO) {
        val arabicReq = Request.Builder().url("https://api.alquran.cloud/v1/surah/$surahNumber").build()
        val arabicRes = client.newCall(arabicReq).execute()
        val arabicBody = arabicRes.body?.string() ?: return@withContext emptyList()
        val arabicResponse = gson.fromJson(arabicBody, AyahResponse::class.java)

        val transReq = Request.Builder().url("https://api.alquran.cloud/v1/surah/$surahNumber/zh.simplified").build()
        val transRes = client.newCall(transReq).execute()
        val transBody = transRes.body?.string() ?: return@withContext emptyList()
        val transResponse = gson.fromJson(transBody, AyahResponse::class.java)

        arabicResponse.data.ayahs.mapIndexed { i, info ->
            val trans = transResponse.data.ayahs.getOrNull(i)
            Ayah(number = info.numberInSurah, text = info.text, translation = trans?.text ?: "")
        }
    }
}