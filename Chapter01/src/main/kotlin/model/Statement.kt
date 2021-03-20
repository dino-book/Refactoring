package model

data class Statement(
    val customer: String,
    val performances: List<Performance>,
    val totalAmount: Int,
    val totalVolumeCredits: Int
)
