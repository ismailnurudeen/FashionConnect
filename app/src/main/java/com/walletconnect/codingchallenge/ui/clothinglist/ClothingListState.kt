package com.walletconnect.codingchallenge.ui.clothinglist

import com.walletconnect.codingchallenge.data.model.ClothingItem

data class ClothingListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val loadedItems: List<ClothingItem> = emptyList(),
    val filteredItems: List<ClothingItem> = emptyList(),
    val query: String = ""
)
