package model

import kotlin.math.max

abstract class PerformanceCalculator(
    protected val performanceSummary: PerformanceSummary,
    val play: Play
) {
    abstract fun amount(): Int

    open fun volumeCredits(): Int {
        return max(performanceSummary.audience - 30, 0)
    }
}

class TragedyCalculator(
    performanceSummary: PerformanceSummary,
    play: Play
) : PerformanceCalculator(performanceSummary, play) {
    override fun amount(): Int {
        var result = 40_000
        if (performanceSummary.audience > 30) {
            result += 1_000 * (performanceSummary.audience - 30)
        }

        return result
    }

    override fun volumeCredits(): Int {
        return super.volumeCredits() + performanceSummary.audience / 5
    }
}

class ComedyCalculator(
    performanceSummary: PerformanceSummary,
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