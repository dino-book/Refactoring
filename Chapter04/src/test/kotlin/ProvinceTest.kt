import org.junit.jupiter.api.Test

class ProvinceTest {
    private val sampleDocument = Document(
        "Asia",
        listOf(
            ProducerDTO("Byzantium", 10, 9),
            ProducerDTO("Attalia", 12, 10),
            ProducerDTO("Sinope", 10, 6)
        ),
        30,
        20
    )

    @Test
    fun shortfall_is_five() {
        val asia = Province(sampleDocument)
        assert(asia.shortFall() == 5)
    }
}