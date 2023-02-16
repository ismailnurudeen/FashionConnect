package com.walletconnect.codingchallenge.ui.clothinglist

import ClothingListItem
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.walletconnect.codingchallenge.ui.clothinglist.parts.AppBar
import com.walletconnect.codingchallenge.ui.clothinglist.parts.SearchInputField
import com.walletconnect.codingchallenge.ui.clothinglist.parts.SortBar
import com.walletconnect.codingchallenge.ui.theme.CodingChallengeTheme
import com.walletconnect.codingchallenge.util.assetManager

class ClothingActivity : ComponentActivity() {
    private val viewModel: ClothingViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupDI()

        setContent {
            CodingChallengeTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    val uiState by viewModel.clothingListState.collectAsState()

                    val refreshState = rememberPullRefreshState(uiState.isRefreshing, {
                        viewModel.loadClothes(refreshing = true)
                    })

                    Scaffold(
                        topBar = {
                            AppBar(
                                "Clothing Co.",
                                searchBar = {
                                    SearchInputField(
                                        value = uiState.query,
                                        hint = "Search Items",
                                        onValueChange = {
                                            viewModel.search(it)
                                        },
                                        onClearSearch = {
                                            viewModel.search("")
                                        }
                                    )
                                }
                            )
                        }
                    ) {
                        Column(modifier = Modifier.padding(it)) {
                            if (uiState.isLoading) {
                                Box(
                                    modifier = Modifier.fillMaxSize(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            } else {
                                SortBar(
                                    modifier = Modifier.padding(end = 16.dp),
                                    currentSortFilter = "title"
                                )
                                if (uiState.filteredItems.isNotEmpty()) {
                                    Box(modifier = Modifier.pullRefresh(refreshState)) {
                                        LazyVerticalGrid(
                                            columns = GridCells.Fixed(2),
                                            contentPadding = PaddingValues(16.dp),
                                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                                            verticalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                            items(items = uiState.filteredItems, key = { item ->
                                                item.id
                                            }) { item ->
                                                ClothingListItem(item = item) {
                                                    // Navigate to details screen
                                                }
                                            }
                                        }
                                        PullRefreshIndicator(
                                            refreshing = uiState.isRefreshing,
                                            state = refreshState,
                                            Modifier.align(Alignment.TopCenter)
                                        )
                                    }
                                } else {
                                    NoItems(modifier = Modifier.padding(top = 58.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun NoItems(modifier: Modifier = Modifier) {
        Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                modifier = Modifier.size(34.dp),
                imageVector = Icons.Rounded.Face,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text("No Items Found", color = MaterialTheme.colorScheme.onBackground, fontSize = 16.sp)
        }
    }
    private fun setupDI() {
        assetManager = assets!!
    }
}
