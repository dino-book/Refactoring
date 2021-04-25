class ProvinceTest {
    private val sampleDocument = Document(
        "Asia",
        listOf(
            Producer("Byzantium", 10, 9),
            Producer("Attalia", 12, 10),
            Producer("Sinope", 10, 6)
        ),
        30,
        20
    )
    val provinceData = Province(sampleDocument)
}