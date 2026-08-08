package com.quran.app.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.quran.app.R
import com.quran.app.model.Ayah

class VerseAdapter(private val list: MutableList<Ayah>) : RecyclerView.Adapter<VerseAdapter.Holder>() {

    fun updateData(newList: List<Ayah>) {
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_verse, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val a = list[position]
        holder.ayahText.text = a.text
        holder.translation.text = "${a.number}. ${a.translation}"
    }

    override fun getItemCount() = list.size

    class Holder(v: View) : RecyclerView.ViewHolder(v) {
        val ayahText: TextView = v.findViewById(R.id.tvAyahText)
        val translation: TextView = v.findViewById(R.id.tvTranslation)
    }
}