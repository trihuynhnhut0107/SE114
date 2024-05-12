package com.example.se114

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarViewHolder(itemView: View, private val onItemListener: CalendarAdapter.OnItemListener, private val days: ArrayList<LocalDate?>) :
    RecyclerView.ViewHolder(itemView), View.OnClickListener {

    val parentView: View = itemView.findViewById(R.id.parentView)
    val dayOfMonth: TextView = itemView.findViewById(R.id.celldaytext)

    init {
        itemView.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        onItemListener.onItemClick(adapterPosition, days[adapterPosition])
    }
}