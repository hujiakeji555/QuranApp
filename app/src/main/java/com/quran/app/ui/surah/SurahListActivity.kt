package com.quran.app.ui.surah

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.quran.app.R
import com.quran.app.adapter.SurahAdapter
import com.quran.app.utils.QuranApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SurahListActivity : AppCompatActivity() {
    private lateinit var adapter: SurahAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_surah_list)

        val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.recyclerView)
        adapter = SurahAdapter(mutableListOf()) { surah ->
            val intent = android.content.Intent(this, com.quran.app.ui.verse.VerseActivity::class.java)
            intent.putExtra("surah_number", surah.number)
            intent.putExtra("surah_name", surah.englishName)
            startActivity(intent)
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val surahs = withContext(Dispatchers.IO) { QuranApi.getSurahs() }
                adapter.updateData(surahs)
            } catch (e: Exception) {
                Toast.makeText(this@SurahListActivity, "加载失败: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}