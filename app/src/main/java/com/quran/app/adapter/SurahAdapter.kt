package com.quran.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quran.app.R
import com.quran.app.model.Surah

class SurahAdapter(private val list: MutableList<Surah>, private val onClick: (Surah) -> Unit)
    : RecyclerView.Adapter<SurahAdapter.Holder>() {

    fun updateData(newList: List<Surah>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_surah, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val s = list[position]
        holder.number.text = s.number.toString()
        holder.nameArabic.text = s.name
        holder.nameEnglish.text = s.englishName
        holder.ayahCount.text = "${s.numberOfAyahs} 节"
        holder.itemView.setOnClickListener { onClick(s) }
    }

    override fun getItemCount() = list.size

    class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val number: TextView = v.findViewById(R.id.tvNumber)
        val nameArabic: TextView = v.findViewById(R.id.tvNameArabic)
        val nameEnglish: TextView = v.findViewById(R.id.tvNameEnglish)
        val ayahCount: TextView = v.findViewById(R.id.tvAyahCount)
    }
}