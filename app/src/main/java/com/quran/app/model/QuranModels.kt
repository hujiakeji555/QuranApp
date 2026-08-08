package com.quran.app.model

data class Surah(val number: Int, val name: String, val englishName: String, val numberOfAyahs: Int)
data class Ayah(val number: Int, val text: String, val translation: String = "")
data class SurahResponse(val data: SurahData)
data class SurahData(val surahs: List<Surah>)
data class AyahResponse(val data: AyahData)
data class AyahData(val ayahs: List<AyahInfo>)
data class AyahInfo(val numberInSurah: Int, val text: String)