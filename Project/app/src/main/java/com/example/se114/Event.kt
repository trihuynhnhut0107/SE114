package com.example.se114

import java.time.LocalDate
import java.time.LocalTime

class Event(val name: String, val date: LocalDate, val time: LocalTime) {
    companion object {
        val eventsList = ArrayList<Event>()

        fun eventsForDate(date: LocalDate): ArrayList<Event> {
            val events = ArrayList<Event>()

            for (event in eventsList) {
                if (event.date == date)
                    events.add(event)
            }

            return events
        }
    }
}