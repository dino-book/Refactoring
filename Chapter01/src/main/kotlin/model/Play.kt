package model

data class Play(
    val name: String,
    val type: PlayType
)

enum class PlayType {
    COMEDY,
    TRAGEDY
}