package com.walletconnect.codingchallenge.ui.screens.clothinglist.parts

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun SortBar(
    sortFilters: List<SortItem>,
    currentSortFilter: SortItem,
    modifier: Modifier = Modifier,
    onSortSelected: (SortItem) -> Unit = {}
) {
    var showDropDown by remember {
        mutableStateOf(false)
    }
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "sort by: ", style = MaterialTheme.typography.labelMedium)
        TextButton(onClick = {
            showDropDown = !showDropDown
        }) {
            Text(text = currentSortFilter.name, style = MaterialTheme.typography.labelSmall)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier.size(16.dp),
                imageVector = Icons.Rounded.KeyboardArrowDown,
                contentDescription = "sort"
            )
        }
        DropdownMenu(expanded = showDropDown, onDismissRequest = {
            showDropDown = false
        }) {
            sortFilters.forEach {
                DropdownMenuItem(text = { Text(text = it.name) }, onClick = {
                    onSortSelected(it)
                    showDropDown = false
                })
            }
        }
    }
}
sealed class SortItem(val name: String) {
    object Title : SortItem("title")
    object Price : SortItem("price")
}
