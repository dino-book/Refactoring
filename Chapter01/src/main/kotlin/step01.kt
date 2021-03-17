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

    for (perf in invoice.performances) {
        volumeCredits += volumeCreditsFor(perf)

        // 청구 내역을 출력한다.
        result += "${playFor(perf)?.name}: ${usd(amountFor(perf))} (${perf.audience}석)\n"
        totalAmount += amountFor(perf)
    }

    result += "총액: ${usd(totalAmount)}\n"
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

private fun volumeCreditsFor(performance: Performance): Int {
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