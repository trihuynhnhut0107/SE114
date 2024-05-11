package com.example.se114.features.user_profile.presentation.screen

import android.widget.Space
import androidx.compose.animation.expandVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun UserProfileScreen() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "avatarIcon",
                    modifier = Modifier.size(96.dp)
                )
                Text(text = "Full Name Here", fontWeight = FontWeight.Bold, fontSize = 30.sp)
                Text(text = "Nick name here", fontWeight = FontWeight.Light)
            }

        }
        Spacer(modifier = Modifier.size(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            TextButton(onClick = { /*TODO*/ }) {
                Image(imageVector = Icons.Outlined.Send, contentDescription = "messageButton")
            }
            TextButton(onClick = { /*TODO*/ }) {
                Image(imageVector = Icons.Outlined.Call, contentDescription = "callButton")

            }
            TextButton(onClick = { /*TODO*/ }) {
                Image(imageVector = Icons.Outlined.DateRange, contentDescription = "calendarButton")

            }
            TextButton(onClick = { /*TODO*/ }) {
                Image(imageVector = Icons.Outlined.Menu, contentDescription = "menuButton")
            }

        }
        Row(modifier = Modifier.padding(24.dp).fillMaxSize().align(Alignment.Start)) {
            Column {

                Text(text = "Display name", fontWeight = FontWeight.Light)
                Text(text = "Full Name Here", fontWeight = FontWeight.Bold, fontSize = 24.sp)

                Text(text = "Email address", fontWeight = FontWeight.Light)
                Text(text = "Full Name Here", fontWeight = FontWeight.Bold, fontSize = 24.sp)

                Text(text = "Address", fontWeight = FontWeight.Light)
                Text(text = "Full Name Here", fontWeight = FontWeight.Bold, fontSize = 24.sp)

                Text(text = "Phone number", fontWeight = FontWeight.Light)
                Text(text = "Full Name Here", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            }
        }

    }
}