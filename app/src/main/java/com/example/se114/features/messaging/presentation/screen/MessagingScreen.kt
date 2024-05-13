package com.example.se114.features.messaging.presentation.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.LineBreak.Strategy.*
import androidx.compose.ui.text.style.LineBreak.Strategy.Companion.Simple
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.se114.R
import com.example.se114.ui.theme.PurpleGrey40
import com.example.se114.ui.theme.PurpleGrey80

data class Message(val author: String, val body: String, val isFromMe: Boolean)

@Composable
fun MessagingScreen() {
    // A list of sample messages
    val messages = listOf(
        Message("Alice", "Hi, how are you?", true),
        Message("Bob", "I'm good, thanks. How about you?", false),
        Message("Alice", "I'm fine too. What are you working on?", true),
        Message("Bob", "I'm learning Jetpack Compose, the new UI toolkit for Android.", false),
        Message("Alice", "Oh, that sounds interesting. How do you like it so far?", true),
        Message(
            "Bob",
            "It's awesome. It makes UI development faster and easier with less code and more features.",
            false
        ),
        Message("Alice", "Hi, how are you?", true),

        Message("Bob", "I'm good, thanks. How about you?", false),
        Message("Bob", "I'm good, thanks. How about you?", false),
        Message("Bob", "I'm good, thanks. How about you?", false)
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .background(Color.Gray)
                .fillMaxWidth()
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Icon(imageVector = Icons.Rounded.ArrowBack, contentDescription = "Back arrow", tint = Color.White)
            }
            Text(text = "Alice", fontWeight = FontWeight.Bold, color = Color.White)
        }
        Row(modifier = Modifier.weight(1f)) {
            LazyColumn(){
                val messageCount = messages.size
                items(messageCount) {
                    item -> MessageCard(message = messages[item])
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            var currentMessage by remember {
                mutableStateOf("")
            }
            OutlinedTextField(value = currentMessage, onValueChange = {currentMessage = it}, modifier = Modifier.fillMaxWidth())
        }
    }


}

@Composable
fun MessageCard(message: Message) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Card(
            modifier = Modifier
                .align(if (message.isFromMe) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (message.isFromMe) 48f else 0f,
                        bottomEnd = if (message.isFromMe) 0f else 48f
                    )
                )
                .padding(16.dp)
                .background(Color.Transparent)
                .width(200.dp)
        ) {
            Text(text = message.body,
                modifier = Modifier.padding(10.dp),
                style = TextStyle.Default.copy(
                    lineBreak = LineBreak.Paragraph
                ),)
        }
    }


}


