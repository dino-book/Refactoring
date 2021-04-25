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
    private val noProducersDocument = Document(
        "No Producers",
        listOf(),
        30,
        20
    )

    lateinit var asia: Province
    lateinit var noProducers: Province

    @BeforeEach
    fun setAsiaProvince() {
        asia = Province(sampleDocument)
        noProducers = Province(noProducersDocument)
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

    @Test
    fun no_producers() {
        assert(noProducers.shortFall() == 30)
        assert(noProducers.profit() == 0)
    }

    @Test
    fun zero_demand() {
        asia.demand = 0
        assert(asia.shortFall() == -25)
        assert(asia.profit() == 0)
    }

    @Test
    fun negative_demand() {
        asia.demand = -1
        assert(asia.shortFall() == -26)
        assert(asia.profit() == -10)
    }
}