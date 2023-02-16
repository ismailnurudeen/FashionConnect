package com.walletconnect.codingchallenge.ui.clothinglist

import com.walletconnect.codingchallenge.data.model.ClothingItem

data class ClothingListState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val items: List<ClothingItem> = emptyList()
)
