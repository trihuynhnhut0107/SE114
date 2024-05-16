package com.example.se114

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
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
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.se114.ui.theme.SE114Theme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val viewModel: ChatViewModel = ViewModelProvider(this).get(ChatViewModel::class.java)
        //val messages = MessageRepository.getMessages()

        setContent {
            SE114Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val messages = MessageRepository.getMessages()
                    val viewModel: ChatViewModel = remember { ChatViewModel(messages) }
                    ChatScreen(viewModel = viewModel)
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

object MessageRepository {
    fun getMessages(): List<ChatMessage> {
        // Truy vấn cơ sở dữ liệu để lấy danh sách tin nhắn
        // Sử dụng tạm thời để hiển th
        return listOf(
            ChatMessage("Alice", "Xin chào", System.currentTimeMillis()),
            ChatMessage("Bob", "Dậy sớm thế!", System.currentTimeMillis()),
            ChatMessage("Bob", "Có gì không", System.currentTimeMillis()),
            ChatMessage("Charlie", "Chào mọi người", System.currentTimeMillis()),
            ChatMessage("Trần Trung Thông", "Mai đi học không mọi người", System.currentTimeMillis())
        )
    }
}

data class ChatMessage(
    val sender: String,
    val message: String,
    val timestamp: Long
)

private const val FILE_PICKER_REQUEST_CODE = 1

class ChatViewModel(messages: List<ChatMessage>) : ViewModel() {
    private val _messages = MutableLiveData(messages)
    val messages: LiveData<List<ChatMessage>> = _messages

    fun sendMessage(message: String, currentUser: String) {
        val newMessage = ChatMessage(currentUser, message, System.currentTimeMillis())
        val currentMessages = _messages.value ?: emptyList()
        _messages.value = currentMessages + newMessage
    }
}

@Composable
fun ChatScreen(viewModel: ChatViewModel) {
    val currentChannel = "Tên nhóm" //Tên nhóm
    val currentUser = "Trần Trung Thông" //Tên chính chủ
    val sender = "Nguyễn Hữu Trường" //Tên người gửi nếu 1v1
    val fixedTime = parseTimeStringToTimestamp("20:38")
    val isGroupChat = true // true: nhóm, false: 1v1
    //val messages by viewModel.messages.observeAsState(initial = emptyList())

//    val messages = remember {
//        listOf(
//            ChatMessage("Trần Trung Thông", "Xin chào bạn nhá!", fixedTime),
//            ChatMessage("Alice", "Xin chào", fixedTime + 1000),
//            ChatMessage("Trần Trung Thông", "Ngày mai đi học môn java không", fixedTime+100000),
//            ChatMessage("Trần Trung Thông", "Chở mình đi học giúp với", fixedTime+300000),
//            ChatMessage("Alice", "Ok để tui chở cho", fixedTime+400000),
//            ChatMessage("Trần Trung Thông", "Phải vậy chứ", fixedTime+500000),
//            ChatMessage("Alice", "Haha không có gì", fixedTime+600000),
//            ChatMessage("Alice", "Nhớ dậy sớm", fixedTime+700000),
//            ChatMessage("Trần Trung Thông", "Ok. Có gì gọi nha, do có thể dậy trễ á", fixedTime+800000),
//            ChatMessage("Alice", "okok", fixedTime+900000),
//        )
//    }

    var messages by remember { mutableStateOf(viewModel.messages.value ?: emptyList()) }

    Column(modifier = Modifier.fillMaxSize()) {
        ChatTopBar(sender = sender, channelName = currentChannel, isGroupChat = isGroupChat)
        ChatMessages(messages = messages, currentUser = currentUser)
        ChatBottomBar(onSendMessage = { message ->
            viewModel.sendMessage(message, currentUser)
            messages = viewModel.messages.value ?: emptyList()
        })
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun ChatTopBar(sender: String, channelName: String, isGroupChat: Boolean) {
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
                        text = if (isGroupChat) channelName else sender,
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
                painter = painterResource(id = R.drawable.ic_launcher_call),
                contentDescription = "Call",
            )
        }

        IconButton(onClick = { /* Xử lý */ }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_videocam),
                contentDescription = "Video Call",
            )
        }
    }
}

@Composable
fun ChatMessages(messages: List<ChatMessage>, currentUser: String) {
    var previousSender: String? by remember { mutableStateOf(null) }
    var previousTimeStamp: Long? by remember { mutableStateOf(null) } // Thời gian tin nhắn trước đó

    LazyColumn(
        modifier = Modifier.height(600.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(messages.size) { index ->
            val chatMessage = messages[index]
            val isCurrentUser = chatMessage.sender == currentUser
            val showAvatar = previousSender != chatMessage.sender

            val showTimeStamp = if (previousTimeStamp != null) {
                val timeDifference = chatMessage.timestamp - previousTimeStamp!!
                timeDifference > 15 * 60 * 1000 // Kiểm tra xem có cách nhau quá 15 phút không
            } else {
                true // Hiển thị thời gian cho tin nhắn đầu
            }

            if (showTimeStamp) {
                val formattedTime = formatTime(chatMessage.timestamp)
                Text(
                    text = formattedTime,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            if (!isCurrentUser && showAvatar) {
                Text(
                    text = chatMessage.sender,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 56.dp, top = 20.dp),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // Hiển thị tin nhắn
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = if (showAvatar || showTimeStamp) 16.dp else 2.dp,
                    ),
                horizontalArrangement = if (isCurrentUser) Arrangement.End else Arrangement.Start,
                verticalAlignment = Alignment.Top
            ) {
                // Hiển thị avatar nếu cần
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
                if (!isCurrentUser && !showAvatar) {
                    Spacer(modifier = Modifier.width(48.dp))
                }
                Spacer(modifier = Modifier.width(6.dp))
                ChatMessage(chatMessage.message, isCurrentUser = isCurrentUser)
            }

            previousSender = chatMessage.sender
            previousTimeStamp = chatMessage.timestamp
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

// Hàm định dạng thời gian từ timestamp
fun formatTime(timestamp: Long): String {
    val time = Calendar.getInstance()
    time.timeInMillis = timestamp
    val now = Calendar.getInstance()

    val dateFormat = if (time[Calendar.YEAR] == now[Calendar.YEAR] &&
        time[Calendar.DAY_OF_YEAR] == now[Calendar.DAY_OF_YEAR]
    ) {
        SimpleDateFormat("HH:mm", Locale.getDefault()) // Nếu là hôm nay thì chỉ hiển thị giờ:phút
    } else {
        SimpleDateFormat("dd/MM HH:mm", Locale.getDefault()) // Nếu không phải hôm nay thì hiển thị ngày/tháng và giờ:phút
    }
    return dateFormat.format(time.time)
}

fun parseTimeStringToTimestamp(timeString: String): Long {
    val format = SimpleDateFormat("HH:mm", Locale.getDefault())
    val date = format.parse(timeString)
    return date?.time ?: 0 // Lấy thời gian từ date, nếu date null thì trả về 0
}


@Composable
fun ChatBottomBar(onSendMessage: (String) -> Unit) {
    var messageText by remember { mutableStateOf("") }
    var isMessageNotEmpty by remember { mutableStateOf(false) } // New state variable

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = {
//            val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
//            type = "*/*" // Kiểu tệp tin
//        }
//            startActivityForResult(1, intent, FILE_PICKER_REQUEST_CODE)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_launcher_attachment),
                contentDescription = "File Upload"
            )
        }

        // Input field for typing message
        OutlinedTextField(
            value = messageText,
            onValueChange = {
                messageText = it
                isMessageNotEmpty = it.isNotEmpty()
            },
            label = { Text(text = "Nhập tin nhắn") },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 6.dp)
        )

        if (isMessageNotEmpty) {
            IconButton(onClick = {
                onSendMessage(messageText)
                messageText = ""
                isMessageNotEmpty = false
            }) {
                Icon(
                    imageVector = Icons.Default.Send,
                    contentDescription = "Send Message",
                )
            }
        } else {
            IconButton(onClick = { /* Xử lý */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_camera),
                    contentDescription = "Camera"
                )
            }
            IconButton(onClick = { /* Xử lý */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_launcher_recording),
                    contentDescription = "Voice Recording"
                )
            }
        }
    }
}

//// Xử lý kết quả trả về từ hộp thoại chọn tập tin
//private fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//    onActivityResult(requestCode, resultCode, data)
//    if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
//        val selectedFileUri: Uri? = data?.data
//        selectedFileUri?.let { uri ->
//            val fileName: String = getFileName(uri)
//            val message = "Tôi đã gửi một tập tin: $fileName"
//            onSendMessage(message)
//        }
//    }
//}
//
//// Hàm lấy tên tập tin từ Uri
//private fun getFileName(uri: Uri): String {
//    val cursor: Cursor? = contentResolver.query(uri, null, null, null, null)
//    cursor?.use {
//        if (it.moveToFirst()) {
//            val displayName: String =
//                it.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
//            return displayName
//        }
//    }
//    return ""
//}

//hashmap

@Preview(showBackground = true)
@Composable
fun PreviewChatScreen() {
    val messages = MessageRepository.getMessages()
    val viewModel: ChatViewModel = remember { ChatViewModel(messages) }
    ChatScreen(viewModel = viewModel)
}