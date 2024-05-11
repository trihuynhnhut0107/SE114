package com.example.se114.features.home_feature.presentation.screen

import android.graphics.drawable.Icon
import android.media.Image
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Call
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.background(color = Color.Transparent)
            ) {
                Image(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "searchIcon",
                    modifier = Modifier.size(20.dp)
                )
            }
            Text(text = "Home")

            TextButton(onClick = { /*TODO*/ }) {
                Image(imageVector = Icons.Rounded.AccountCircle, contentDescription = "avatarIcon")
            }
        }
        val imageVectorArray = mutableListOf<ImageVector>().apply {
            repeat(20) { this.add(element = Icons.Rounded.AccountCircle) }
        }
        val names = mutableListOf<String>().apply {
            repeat(20) { this.add(element = "Names") }
        }

        val message = mutableListOf<String>().apply {
            repeat(20) { this.add(element = "Preview messages") }
        }


        LazyRow(contentPadding = PaddingValues(16.dp)) {
            val itemCount = names.size
            items(itemCount) { item ->
                ConnectionList(
                    itemIndex = item,
                    testImageVector = imageVectorArray,
                    title = names, modifier = Modifier
                )
            }
        }


        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            LazyColumn(contentPadding = PaddingValues(5.dp),) {
                val itemCount = names.size
                items(itemCount) { item ->
                    MessageList(
                        itemIndex = item,
                        testImageVector = imageVectorArray,
                        connectionName = names,
                        previewMessage = message
                    )

                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.background(color = Color.Transparent)
            ) {
                Image(
                    imageVector = Icons.Rounded.AccountBox,
                    contentDescription = "searchIcon",
                    modifier = Modifier.size(36.dp)
                )
            }
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.background(color = Color.Transparent)
            ) {
                Image(
                    imageVector = Icons.Rounded.Call,
                    contentDescription = "searchIcon",
                    modifier = Modifier.size(36.dp)
                )
            }
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.background(color = Color.Transparent)
            ) {
                Image(
                    imageVector = Icons.Rounded.AccountCircle,
                    contentDescription = "searchIcon",
                    modifier = Modifier.size(36.dp)
                )
            }
            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.background(color = Color.Transparent)
            ) {
                Image(
                    imageVector = Icons.Rounded.Settings,
                    contentDescription = "searchIcon",
                    modifier = Modifier.size(36.dp)
                )
            }


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConnectionList(
    itemIndex: Int,
    testImageVector: MutableList<ImageVector>,
    title: MutableList<String>,
    modifier: Modifier
) {

        ElevatedCard(
            onClick = { },
            modifier
                .padding(10.dp)
                .wrapContentSize()
                .background(Color.Transparent),

        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp)
            ) {
                Image(
                    imageVector = testImageVector[itemIndex],
                    contentDescription = title[itemIndex]
                )
                Text(text = title[itemIndex])
            }
        }
    }


@Composable
fun MessageList(
    itemIndex: Int,
    testImageVector: MutableList<ImageVector>,
    connectionName: MutableList<String>,
    previewMessage: MutableList<String>,
) {
    Card(modifier = Modifier.padding(10.dp)) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Image(
                imageVector = testImageVector[itemIndex],
                contentDescription = connectionName[itemIndex]
            )
            Spacer(modifier = Modifier.width(24.dp))
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = connectionName[itemIndex], fontWeight = FontWeight.Bold)
                Text(text = previewMessage[itemIndex])
            }
        }
    }
}
