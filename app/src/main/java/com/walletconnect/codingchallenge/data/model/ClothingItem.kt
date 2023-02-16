package com.walletconnect.codingchallenge.data.model

import java.io.Serializable

data class ClothingItem(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val image: String
) : Serializable
