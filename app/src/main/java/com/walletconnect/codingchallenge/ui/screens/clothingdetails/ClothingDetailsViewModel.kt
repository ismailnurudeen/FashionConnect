package com.walletconnect.codingchallenge.ui.screens.clothingdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walletconnect.codingchallenge.data.ClothingRepository
import com.walletconnect.codingchallenge.util.assetManager
import com.walletconnect.codingchallenge.util.moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ClothingDetailsViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
    // This will make the viewModel untestable, would be best if injected via constructor
    private val clothingRepository by lazy { ClothingRepository(assetManager, moshi) }
    private val _clothingDetailsUiState = MutableStateFlow(ClothingDetailsUiState())
    val clothingDetailsUiState: StateFlow<ClothingDetailsUiState> = _clothingDetailsUiState

    fun loadClothingItem(id: Int) {
        viewModelScope.launch(dispatcher) {
            val clothingItem = clothingRepository.fetchClothingItemById(id)
            _clothingDetailsUiState.update {
                it.copy(item = clothingItem)
            }
        }
    }
}
