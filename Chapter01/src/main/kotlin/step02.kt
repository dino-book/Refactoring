import model.*
import kotlin.math.max
import kotlin.math.round

private fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    return renderPlainText(createStatement(invoice))
}

private fun htmlStatement(invoice: Invoice, plays: Map<String, Play>): String {
    return renderHtml(createStatement(invoice))
}

private fun createStatement(invoice: Invoice): Statement {
    return invoice.performanceSummaries.map { performanceSummaries ->
        enrichPerformance(performanceSummaries)
    }.let { performances ->
        Statement(
            invoice.customer,
            performances,
            totalAmount(performances),
            totalVolumeCredits(performances)
        )
    }
}

private fun renderPlainText(statement: Statement): String {
    var result = "청구 내역 (고객명 : ${statement.customer})\n"

    for (perf in statement.performances) {
        // 청구 내역을 출력한다.
        result += "${perf.play.name}: ${usd(perf.amount)} (${perf.audience}석)\n"
    }

    result += "총액: ${usd(statement.totalAmount)}\n"
    result += "적립 포인트: ${statement.totalVolumeCredits}점\n"
    return result
}

private fun renderHtml(statement: Statement): String {
    var result = "<h1>청구 내역 (고객명: ${statement.customer}</h1>\n"

    result += "<table>\n"
    result += "<tr><th>연극</th><th>좌석 수</th><th>금액</th>></tr>"

    for (perf in statement.performances) {
        result += " <tr><td>${perf.play.name}</td><td>(${perf.audience}석)</td>"
        result += "<td>${usd(perf.amount)}</td></tr>"
    }
    result += "</table>\n"

    result += "<p>총액: <em>${usd(statement.totalAmount)}</em></p>\n"
    result += "<p>적립 포인트: <em>${statement.totalVolumeCredits}</em>점</p>\n"
    return result
}

private fun enrichPerformance(performanceSummary: PerformanceSummary): Performance {
    val play = playFor(performanceSummary)!!

    return Performance(
        performanceSummary.playId,
        play,
        performanceSummary.audience,
        amountFor(performanceSummary, play.type),
        volumeCreditsFor(performanceSummary, play.type)
    )
}

private fun totalAmount(performances: List<Performance>): Int {
    return performances.sumOf { performance ->
        performance.amount
    }
}

private fun amountFor(performance: PerformanceSummary, playType: PlayType): Int {
    var result = 0

    when (playType) {
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

private fun volumeCreditsFor(performance: PerformanceSummary, playType: PlayType): Int {
    var volumeCredits = 0

    // 포인트를 적립한다.
    volumeCredits += max(performance.audience - 30, 0)

    // 희극 관객 5명마다 추가 포인트를 제공한다.
    if (playType == PlayType.COMEDY) {
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