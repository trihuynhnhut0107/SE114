package com.example.se114

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.se114.features.home_feature.presentation.screen.HomeScreen
import com.example.se114.features.messaging.presentation.screen.MessagingScreen
import com.example.se114.features.schedule.presentation.screen.BasicEvent
import com.example.se114.features.schedule.presentation.screen.Event
import com.example.se114.features.schedule.presentation.screen.Schedule
import com.example.se114.features.user_profile.presentation.screen.UserProfileScreen
import com.example.se114.ui.theme.SE114Theme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SE114Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Schedule(events = sampleEvents)
                }
            }
        }
    }
}
private val sampleEvents = listOf(
    Event(
        name = "Google I/O Keynote",
        color = Color(0xFFAFBBF2),
        start = LocalDateTime.parse("2021-05-18T13:00:00"),
        end = LocalDateTime.parse("2021-05-18T15:00:00"),
        description = "Tune in to find out about how we're furthering our mission to organize the world’s information and make it universally accessible and useful.",
    ),
    Event(
        name = "Developer Keynote",
        color = Color(0xFFAFBBF2),
        start = LocalDateTime.parse("2021-05-18T15:15:00"),
        end = LocalDateTime.parse("2021-05-18T16:00:00"),
        description = "Learn about the latest updates to our developer products and platforms from Google Developers.",
    ),
    Event(
        name = "What's new in Android",
        color = Color(0xFF1B998B),
        start = LocalDateTime.parse("2021-05-18T16:50:00"),
        end = LocalDateTime.parse("2021-05-18T17:00:00"),
        description = "In this Keynote, Chet Haase, Dan Sandler, and Romain Guy discuss the latest Android features and enhancements for developers.",
    ),
    Event(
        name = "What's new in Machine Learning",
        color = Color(0xFFF4BFDB),
        start = LocalDateTime.parse("2021-05-19T09:30:00"),
        end = LocalDateTime.parse("2021-05-19T11:00:00"),
        description = "Learn about the latest and greatest in ML from Google. We’ll cover what’s available to developers when it comes to creating, understanding, and deploying models for a variety of different applications.",
    ),
    Event(
        name = "What's new in Material Design",
        color = Color(0xFF6DD3CE),
        start = LocalDateTime.parse("2021-05-19T11:00:00"),
        end = LocalDateTime.parse("2021-05-19T12:15:00"),
        description = "Learn about the latest design improvements to help you build personal dynamic experiences with Material Design.",
    ),
    Event(
        name = "Jetpack Compose Basics",
        color = Color(0xFF1B998B),
        start = LocalDateTime.parse("2021-05-20T12:00:00"),
        end = LocalDateTime.parse("2021-05-20T13:00:00"),
        description = "This Workshop will take you through the basics of building your first app with Jetpack Compose, Android's new modern UI toolkit that simplifies and accelerates UI development on Android.",
    ),
)
