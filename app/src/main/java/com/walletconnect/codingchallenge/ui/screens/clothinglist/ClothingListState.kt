package com.walletconnect.codingchallenge.ui.screens.clothinglist

import com.walletconnect.codingchallenge.data.model.ClothingItem
import com.walletconnect.codingchallenge.ui.screens.clothinglist.parts.SortItem

data class ClothingListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val loadedItems: List<ClothingItem> = emptyList(),
    val filteredItems: List<ClothingItem> = emptyList(),
    val sortFilter: SortItem = SortItem.Title,
    val query: String = ""
)
