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
    val calculator = createPerformanceCalculator(performanceSummary, playFor(performanceSummary)!!)

    return Performance(
        performanceSummary.playId,
        calculator.play,
        performanceSummary.audience,
        calculator.amount(),
        calculator.volumeCredits()
    )
}

private fun createPerformanceCalculator(performance: PerformanceSummary, play: Play): PerformanceCalculator {
    return when (play.type) {
        PlayType.TRAGEDY -> TragedyCalculator(performance, play)
        PlayType.COMEDY -> ComedyCalculator(performance, play)
    }
}

private fun totalAmount(performances: List<Performance>): Int {
    return performances.sumOf { performance ->
        performance.amount
    }
}

private fun playFor(performance: PerformanceSummary): Play? {
    return plays[performance.playId]
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