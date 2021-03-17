import model.Invoice
import model.Performance
import model.Play
import model.PlayType
import kotlin.math.max
import kotlin.math.round

private fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    var totalAmount = 0
    var volumeCredits = 0

    var result = "청구 내역 (고객명 : ${invoice.customer})\n"
    val format = { amount: Double ->
        val formattedAmount = round(amount * 100) / 100
        "$$formattedAmount"
    }

    for (perf in invoice.performances) {
        val play = plays[perf.playId] ?: continue

        // 포인트를 적립한다.
        volumeCredits += max(perf.audience - 30, 0)

        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if (play.type == PlayType.COMEDY) {
            volumeCredits += perf.audience / 5
        }

        // 청구 내역을 출력한다.
        result += "${play.name}: ${format(amountFor(perf).toDouble()/100)} (${perf.audience}석)\n"
        totalAmount += amountFor(perf)
    }

    result += "총액: ${format(totalAmount.toDouble()/100)}\n"
    result += "적립 포인트: ${volumeCredits}점\n"
    return result
}

private fun amountFor(performance: Performance): Int {
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

private fun playFor(performance: Performance): Play? {
    return plays[performance.playId]
}