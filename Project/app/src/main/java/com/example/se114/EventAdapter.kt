package com.example.se114

import android.content.Context
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.RequiresApi

class EventAdapter(context: Context, events: List<Event>) :
    ArrayAdapter<Event>(context, 0, events) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var convertView = convertView

        val event = getItem(position)

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.event_cell, parent, false)
        }

        val eventCellTV = convertView!!.findViewById<TextView>(R.id.eventCellTV)

        val eventTitle = "${event?.name} ${
            if (event?.time != null) {
                CalendarUtils.formattedTime(event.time)
            } else {
                "Unknown Time"
            }
        }"
        eventCellTV.text = eventTitle

        return convertView
    }
}