package com.example.se114

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.filled.Call
//import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.se114.ui.theme.SE114Theme


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
                    ChatScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SE114Theme {
        Greeting("Android")
    }
}

data class ChatMessage(
    val sender: String,
    val message: String
)

@Composable
fun ChatScreen() {
    val currentUser = "Trần Trung Thông"
    val messages = remember {
        listOf(
            ChatMessage("Trần Trung Thông", "Xin chào bạn nhá!"),
            ChatMessage("Alice", "Xin chào"),
            ChatMessage("Trần Trung Thông", "Ngày mai đi học môn java không"),
            ChatMessage("Trần Trung Thông", "Chở mình đi học giúp với"),
            ChatMessage("Alice", "Ok để tui chở cho"),
            ChatMessage("Trần Trung Thông", "Phải vậy chứ"),
            ChatMessage("Alice", "Haha không có gì"),
            ChatMessage("Alice", "Nhớ dậy sớm"),
            ChatMessage("Trần Trung Thông", "Ok. Có gì gọi nha, do có thể dậy trễ á"),
            ChatMessage("Alice", "okok"),
        )
    }
    Column(modifier = Modifier.fillMaxSize()) {
        ChatTopBar()
        ChatMessages(messages = messages, currentUser = currentUser)
        ChatBottomBar()
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ChatTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Xử lý */ }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
            )
        }

        Column(
            modifier = Modifier.weight(0.7f)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(color = Color.Gray, shape = CircleShape)
                ) {
                    // Avatar
//                    Image(
//                        painter = painter,
//                        contentDescription = "Avatar",
//                        modifier = Modifier.fillMaxSize()
//                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = "Trần Trung Thông",
                        style = TextStyle(fontSize = 17.sp, fontWeight = FontWeight.Bold)
                    )
                    Text(
                        text = "Đang hoạt động",
                        style = TextStyle(fontSize = 14.sp)
                    )
                }
            }
        }

        IconButton(onClick = { /* Xử lý */ }) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Call",
            )
        }

        IconButton(onClick = { /* Xử lý */ }) {
            Icon(
                imageVector = Icons.Default.Call,
                contentDescription = "Video Call",
            )
        }
    }
}

@Composable
fun ChatMessages(messages: List<ChatMessage>, currentUser: String) {
    var previousSender: String? by remember { mutableStateOf(null) }

    LazyColumn(
        modifier = Modifier.height(600.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(messages.size) { index ->
            val chatMessage = messages[index]
            val isCurrentUser = chatMessage.sender == currentUser
            val showAvatar = previousSender != chatMessage.sender

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (showAvatar) 16.dp else 2.dp,
                    ),
                horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                if (!isCurrentUser && showAvatar) {
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .background(color = Color.Gray, shape = CircleShape)
                            .padding(end = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        // Avatar
                    }
                }
                if(!isCurrentUser && !showAvatar)
                {
                    Spacer(modifier = Modifier.width(48.dp))
                }
                Spacer(modifier = Modifier.width(6.dp))
                // Hiển thị tin nhắn
                ChatMessage(chatMessage.message, isCurrentUser = isCurrentUser)
            }

            previousSender = chatMessage.sender
        }
    }
}


@Composable
fun ChatMessage(message: String, isCurrentUser: Boolean) {
    val backgroundColor = if (isCurrentUser) Color(android.graphics.Color.parseColor("#20A090")) else Color(android.graphics.Color.parseColor("#F2F7FB"))
    val contentColor = if (isCurrentUser) Color.White else Color.Black
    val alignment = if (isCurrentUser) Alignment.End else Alignment.Start
    val shape = if (isCurrentUser) RoundedCornerShape(15.dp).copy(topEnd = ZeroCornerSize) else RoundedCornerShape(15.dp).copy(topStart = ZeroCornerSize)

    Box(
        modifier = Modifier
            .background(color = backgroundColor, shape = shape)
            .padding(8.dp)
            .wrapContentWidth(Alignment.End)
            .widthIn(max = 280.dp)
    ) {
        Text(
            text = message,
            color = contentColor,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(8.dp),
            textAlign = TextAlign.Start,

        )
    }
}


@Composable
fun ChatBottomBar() {
    var messageText by remember { mutableStateOf("") }
    var isMessageNotEmpty by remember { mutableStateOf(false) } // New state variable

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { /* Xử lý */ }) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = "File Upload"
            )
        }

        // Input field for typing message
        OutlinedTextField(
            value = messageText,
            onValueChange = {
                messageText = it
                isMessageNotEmpty = it.isNotEmpty() // Update the state based on text input
            },
            label = { Text(text = "Nhập tin nhắn") },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 6.dp)
        )

        // Display either Send button or other buttons based on text input
        if (isMessageNotEmpty) {
            IconButton(onClick = { /* Xử lý */ }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Message"
                )
            }
        } else {
            IconButton(onClick = { /* Xử lý */ }) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Camera"
                )
            }
            IconButton(onClick = { /* Xử lý */ }) {
                Icon(
                    imageVector = Icons.Default.Build,
                    contentDescription = "Voice Recording"
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    ChatScreen()
}