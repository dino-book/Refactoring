package model

data class PerformanceSummary(
    val playId: String,
    val audience: Int,
)

data class Performance(
    val playId: String,
    val play: Play,
    val audience: Int,
    val amount: Int,
    val volumeCredits: Int
)