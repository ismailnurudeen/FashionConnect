package com.walletconnect.codingchallenge.ui.screens.clothinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.walletconnect.codingchallenge.data.ClothingRepository
import com.walletconnect.codingchallenge.data.model.ClothingItem
import com.walletconnect.codingchallenge.ui.screens.clothinglist.parts.SortItem
import com.walletconnect.codingchallenge.util.assetManager
import com.walletconnect.codingchallenge.util.moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
class ClothingViewModel(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) : ViewModel() {
    // This will make the viewModel untestable, would be best if injected via constructor
    private val clothingRepository by lazy { ClothingRepository(assetManager, moshi) }

    private val _clothingListState = MutableStateFlow(ClothingListState())
    val clothingListState: StateFlow<ClothingListState> = _clothingListState

    init {
        loadClothes()
        initializeSearch()
        initializeSorting()
    }

    fun loadClothes(refreshing: Boolean = false) {
        viewModelScope.launch(dispatcher) {
            _clothingListState.update {
                it.copy(
                    isLoading = !refreshing,
                    isRefreshing = refreshing
                )
            }
            delay(1500) // Just to simulate loading

            val clothingItems = clothingRepository.fetchAllClothingItems()
                .sortWith(clothingListState.value.sortFilter)

            _clothingListState.update {
                it.copy(
                    isLoading = false,
                    isRefreshing = false,
                    loadedItems = clothingItems,
                    filteredItems = clothingItems,
                    query = ""
                )
            }
        }
    }
    private fun initializeSearch() {
        viewModelScope.launch {
            _clothingListState.debounce(300)
                .map { it.query }
                .distinctUntilChanged()
                .flatMapLatest { query ->
                    flow {
                        val filteredItems = if (query.isNotEmpty()) {
                            _clothingListState.value.loadedItems.filter {
                                it.title.contains(
                                    query,
                                    ignoreCase = true
                                )
                            }
                        } else { _clothingListState.value.loadedItems }
                        emit(filteredItems)
                    }
                }
                .flowOn(dispatcher)
                .collect { items ->
                    _clothingListState.update {
                        it.copy(filteredItems = items)
                    }
                }
        }
    }
    private fun initializeSorting() {
        viewModelScope.launch {
            _clothingListState
                .map { it.sortFilter }
                .distinctUntilChanged()
                .flowOn(dispatcher)
                .collect { sort ->
                    val sortedItems =
                        _clothingListState.update {
                            it.copy(
                                filteredItems = _clothingListState.value.filteredItems.sortWith(sort),
                                loadedItems = _clothingListState.value.loadedItems.sortWith(sort)
                            )
                        }
                }
        }
    }
    private fun List<ClothingItem>.sortWith(sort: SortItem): List<ClothingItem> {
        return sortedWith(
            compareBy {
                when (sort) {
                    SortItem.Title -> it.title
                    SortItem.Price -> it.price
                }
            }
        )
    }
    fun search(query: String) {
        _clothingListState.update { it.copy(query = query) }
    }

    fun setSortFilter(sort: SortItem) {
        _clothingListState.update { it.copy(sortFilter = sort) }
    }
}
