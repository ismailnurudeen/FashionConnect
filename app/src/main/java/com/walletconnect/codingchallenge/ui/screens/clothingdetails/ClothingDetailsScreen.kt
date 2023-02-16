package com.walletconnect.codingchallenge.ui.screens.clothingdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClothingDetailsScreen(itemId: Int, navigateBack: () -> Unit = {}, viewModel: ClothingDetailsViewModel = viewModel()) {
    LaunchedEffect(key1 = itemId) {
        viewModel.loadClothingItem(itemId)
    }

    val uiState by viewModel.clothingDetailsUiState.collectAsState()
    val item = uiState.item
    Scaffold {
        Column(
            modifier = Modifier.padding(it).background(color = Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.fillMaxWidth().padding(top = 24.dp)) {
                AsyncImage(
                    modifier = Modifier.height(280.dp)
                        .align(Alignment.Center)
                        .padding(top = 10.dp),
                    model = item?.image,
                    contentScale = ContentScale.Inside,
                    contentDescription = item?.title
                )
                Icon(
                    modifier = Modifier.align(Alignment.TopStart).size(64.dp)
                        .padding(start = 20.dp)
                        .clip(RoundedCornerShape(50))
                        .clickable {
                            navigateBack()
                        },
                    imageVector = Icons.Rounded.Close,
                    contentDescription = "close"
                )
            }
            Spacer(modifier = Modifier.height(24.dp))
            Card(
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(topStartPercent = 20, topEndPercent = 20)
            ) {
                Column(modifier = Modifier.verticalScroll(state = rememberScrollState()).padding(34.dp)) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Row {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = item?.title ?: "...",
                            style = MaterialTheme.typography.headlineMedium
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = item?.description ?: "", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(20.dp)
                        )
                            .padding(16.dp),
                        text = "USD ${item?.price}",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        maxLines = 1
                    )
                }
            }
        }
    }
}
