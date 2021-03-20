package model

data class Invoice(
    val customer: String,
    val performanceSummaries: List<PerformanceSummary>
)