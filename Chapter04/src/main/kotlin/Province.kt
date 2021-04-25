import kotlin.math.min

data class Province(
    val name: String,
    val producers: MutableList<Producer>,
    val totalProduction: Int,
    val demand: Int,
    val price: Int
) {
    constructor(document: Document) : this(
        document.name,
        mutableListOf(),
        document.producerDTOs.sumOf { it.production },
        document.demand,
        document.price
    ) {
        document.producerDTOs.forEach {
            producers.add(Producer(this, it.name, it.cost, it.production))
        }
    }

    fun shortFall(): Int {
        return demand - totalProduction
    }

    fun profit(): Int {
        return demandValue() - demandCost()
    }

    fun demandValue(): Int {
        return satisfiedDemand() * price
    }

    fun satisfiedDemand(): Int {
        return min(demand, totalProduction)
    }

    fun demandCost(): Int {
        var remainingDemand = demand
        var result = 0
        producers.sortedWith { producer1, producer2 ->
            producer1.cost - producer2.cost
        }.forEach {
            val contribution = min(remainingDemand, it.production)
            remainingDemand -= contribution
            result += contribution * it.cost
        }

        return result
    }
}
