import model.*
import kotlin.math.max
import kotlin.math.round

private fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    val statement = invoice.performanceSummaries.map { performanceSummaries ->
        enrichPerformance(performanceSummaries)
    }.let { performances ->
        Statement(
            invoice.customer,
            performances,
            totalAmount(performances),
            totalVolumeCredits(performances)
            )
    }

    return renderPlainText(statement, plays)
}

private fun renderPlainText(statement: Statement, plays: Map<String, Play>): String {
    var result = "청구 내역 (고객명 : ${statement.customer})\n"

    for (perf in statement.performances) {
        // 청구 내역을 출력한다.
        result += "${perf.play.name}: ${usd(perf.amount)} (${perf.audience}석)\n"
    }

    result += "총액: ${usd(statement.totalAmount)}\n"
    result += "적립 포인트: ${statement.totalVolumeCredits}점\n"
    return result
}

private fun enrichPerformance(performanceSummary: PerformanceSummary): Performance {
    return Performance(
        performanceSummary.playId,
        playFor(performanceSummary)!!,
        performanceSummary.audience,
        amountFor(performanceSummary),
        volumeCreditsFor(performanceSummary)
    )
}

private fun totalAmount(performances: List<Performance>): Int {
    return performances.sumOf { performances ->
        performances.amount
    }
}

private fun amountFor(performance: PerformanceSummary): Int {
    var result = 0

    when (playFor(performance)!!.type) {
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

private fun totalVolumeCredits(performances: List<Performance>): Int {
    return performances.sumOf { performance ->
        performance.volumeCredits
    }
}