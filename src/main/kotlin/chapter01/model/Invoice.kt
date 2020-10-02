package chapter01.model

data class Invoice(
        val customer: String,
        val performances: List<Performance>
)