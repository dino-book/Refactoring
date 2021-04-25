import org.junit.jupiter.api.BeforeEach
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

    lateinit var asia: Province

    @BeforeEach
    fun setAsiaProvince() {
        asia = Province(sampleDocument)
    }

    @Test
    fun shortfall() {
        assert(asia.shortFall() == 5)
    }

    @Test
    fun profit() {
        assert(asia.profit() == 230)
    }

    @Test
    fun setProduction() {
        asia.producers[0].production = 20
        assert(asia.shortFall() == -6)
        assert(asia.profit() == 292)
    }
}