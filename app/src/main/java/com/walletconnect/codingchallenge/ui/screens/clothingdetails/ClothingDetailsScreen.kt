package com.walletconnect.codingchallenge.ui.screens.clothingdetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage

@Composable
fun ClothingDetailsScreen(itemId: Int, navigateBack: () -> Unit = {}, viewModel: ClothingDetailsViewModel = viewModel()) {
    LaunchedEffect(key1 = itemId){
        viewModel.loadClothingItem(itemId)
    }

    val uiState by viewModel.clothingDetailsUiState.collectAsState()
    val item = uiState.item

    Scaffold {
        Column(modifier = Modifier.padding(it)) {
            AsyncImage(
                modifier = Modifier.height(200.dp)
                    .clip(RoundedCornerShape(bottomEndPercent = 30)),
                model = item?.image,
                contentDescription = item?.title
            )
            Card {
                Column {
                    Row {
                        Text(modifier = Modifier.weight(1f), text = item?.title ?: "...")
                        Text(text = "$${item?.price}")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = item?.description ?: "")
                }
            }
        }
    }
}
