package model

import kotlin.math.max

class PerformanceCalculator(
    private val performanceSummary: PerformanceSummary,
    val play: Play
) {
    fun amount(): Int {
        var result = 0

        when (play.type) {
            PlayType.TRAGEDY -> {
                result = 40_000
                if (performanceSummary.audience > 30) {
                    result += 1_000 * (performanceSummary.audience - 30)
                }
            }
            PlayType.COMEDY -> {
                result = 30_000
                if (performanceSummary.audience > 20) {
                    result += 10_000 + 500 * (performanceSummary.audience - 20)
                }
                result += 300 * performanceSummary.audience
            }
        }

        return result
    }

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