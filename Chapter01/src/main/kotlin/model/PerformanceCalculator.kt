package model

import kotlin.math.max

abstract class PerformanceCalculator(
    private val performanceSummary: PerformanceSummary,
    val play: Play
) {
    abstract fun amount(): Int

    fun volumeCredits(): Int {
        var volumeCredits = 0

        // 포인트를 적립한다.
        volumeCredits += max(performanceSummary.audience - 30, 0)

        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if (play.type == PlayType.COMEDY) {
            volumeCredits += performanceSummary.audience / 5
        }

        return volumeCredits
    }
}

class TragedyCalculator(
    private val performanceSummary: PerformanceSummary,
    play: Play
) : PerformanceCalculator(performanceSummary, play) {
    override fun amount(): Int {
        var result = 40_000
        if (performanceSummary.audience > 30) {
            result += 1_000 * (performanceSummary.audience - 30)
        }

        return result
    }
}

class ComedyCalculator(
    private val performanceSummary: PerformanceSummary,
    play: Play
) : PerformanceCalculator(performanceSummary, play) {
    override fun amount(): Int {
        var result = 30_000
        if (performanceSummary.audience > 20) {
            result += 10_000 + 500 * (performanceSummary.audience - 20)
        }
        result += 300 * performanceSummary.audience

        return result
    }
}