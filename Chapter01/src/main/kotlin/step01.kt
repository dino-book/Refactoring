import model.*
import kotlin.math.max
import kotlin.math.round

private fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    var result = "청구 내역 (고객명 : ${invoice.customer})\n"

    for (perf in invoice.performanceSummaries) {
        // 청구 내역을 출력한다.
        result += "${playFor(perf)?.name}: ${usd(amountFor(perf))} (${perf.audience}석)\n"
    }

    result += "총액: ${usd(totalAmount(invoice))}\n"
    result += "적립 포인트: ${totalVolumeCredits(invoice)}점\n"
    return result
}

private fun totalAmount(invoice: Invoice): Int {
    var totalAmount = 0

    for (perf in invoice.performanceSummaries) {
        totalAmount += amountFor(perf)
    }

    return totalAmount
}

private fun amountFor(performance: PerformanceSummary): Int {
    var result = 0

    when (playFor(performance)?.type) {
        PlayType.TRAGEDY -> {
            result = 40_000
            if (performance.audience > 30) {
                result += 1_000 * (performance.audience - 30)
            }
        }
        PlayType.COMEDY -> {
            result = 30_000
            if (performance.audience > 20) {
                result += 10_000 + 500 * (performance.audience - 20)
            }
            result += 300 * performance.audience
        }
        null -> {
            // do nothing
        }
    }

    return result
}

private fun playFor(performance: PerformanceSummary): Play? {
    return plays[performance.playId]
}

private fun volumeCreditsFor(performance: PerformanceSummary): Int {
    var volumeCredits = 0

    // 포인트를 적립한다.
    volumeCredits += max(performance.audience - 30, 0)

    // 희극 관객 5명마다 추가 포인트를 제공한다.
    if (playFor(performance)?.type == PlayType.COMEDY) {
        volumeCredits += performance.audience / 5
    }

    return volumeCredits
}

private fun usd(amount: Int): String {
    val number = amount.toDouble() / 100

    val formattedAmount = round(number * 100) / 100
    return "$$formattedAmount"

}

private fun totalVolumeCredits(invoice: Invoice): Int {
    var volumeCredits = 0

    for (perf in invoice.performanceSummaries) {
        volumeCredits += volumeCreditsFor(perf)
    }

    return volumeCredits
}