package com.walletconnect.codingchallenge.ui.clothinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walletconnect.codingchallenge.data.ClothingRepository
import com.walletconnect.codingchallenge.util.assetManager
import com.walletconnect.codingchallenge.util.moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ClothingViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
    // This will make the repository untestable, would be best if injected via constructor
    private val clothingRepository by lazy { ClothingRepository(assetManager, moshi) }
    private val _clothingListState = MutableStateFlow(ClothingListState())
    val clothingListState: StateFlow<ClothingListState> = _clothingListState
    init {
        loadClothes()
    }
    fun loadClothes(refreshing: Boolean = false) {
        viewModelScope.launch(dispatcher) {
            _clothingListState.update {it.copy(isLoading = !refreshing, isRefreshing = refreshing) }
            delay(1500) // Just to simulate loading
            val clothingItems = clothingRepository.fetchAllClothingItems()

            _clothingListState.update {
                it.copy(isLoading = false, isRefreshing = false, items = clothingItems)
            }
        }
    }
}
