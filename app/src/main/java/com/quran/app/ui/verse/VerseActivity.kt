package com.quran.app.ui.verse

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.quran.app.R
import com.quran.app.adapter.VerseAdapter
import com.quran.app.utils.QuranApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VerseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verse)

        val surahNumber = intent.getIntExtra("surah_number", 1)
        val surahName = intent.getStringExtra("surah_name") ?: "Surah"

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.toolbar)
        toolbar.title = "$surahNumber. $surahName"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        val adapter = VerseAdapter(mutableListOf())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val ayahs = withContext(Dispatchers.IO) { QuranApi.getAyahsWithChinese(surahNumber) }
                adapter.updateData(ayahs)
            } catch (e: Exception) {
                Toast.makeText(this@VerseActivity, "加载失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}