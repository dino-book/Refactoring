class Producer(
    val province: Province,
    val name: String,
    val cost: Int,
    production: Int
) {
    var production: Int = -1
        set(value) {
            if (field >= 0) {
                province.totalProduction += (value - field)
            }
            field = value
        }

    init {
        this.production = production
    }
}