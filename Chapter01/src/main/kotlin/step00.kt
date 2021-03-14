import model.Invoice
import model.Performance
import model.Play
import model.PlayType
import kotlin.math.max
import kotlin.math.round

val plays = mapOf(
    "hamlet" to Play("Hamlet", PlayType.TRAGEDY),
    "as-like" to Play("As You Like It", PlayType.COMEDY),
    "othello" to Play("Othello", PlayType.TRAGEDY)
)

val invoices = listOf(
    Invoice(
        "BigCo",
        listOf(
            Performance(
                "hamlet",
                55
            ),
            Performance(
                "as-like",
                35
            ),
            Performance(
                "othello",
                40
            )
        )
    )
)

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    var totalAmount = 0
    var volumeCredits = 0

    var result = "청구 내역 (고객명 : ${invoice.customer})\n"
    val format = { amount: Double ->
        val formattedAmount = round(amount * 100) / 100
        "$$formattedAmount"
    }

    for (perf in invoice.performances) {
        val play = plays[perf.playId] ?: continue
        var thisAmount = 0

        when (play.type) {
            PlayType.COMEDY -> {
                thisAmount = 40_000
                if (perf.audience > 30) {
                    thisAmount += 1_000 * (perf.audience - 30)
                }
            }
            PlayType.TRAGEDY -> {
                thisAmount = 30_000
                if (perf.audience > 20) {
                    thisAmount += 10_000 + 500 * (perf.audience - 20)
                }
                thisAmount += 300 * perf.audience
            }
        }

        // 포인트를 적립한다.
        volumeCredits += max(perf.audience - 30, 0)

        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if (play.type == PlayType.COMEDY) {
            volumeCredits += perf.audience / 5
        }

        // 청구 내역을 출력한다.
        result += "${play.name}: ${format(thisAmount.toDouble()/100)} (${perf.audience}석)\n"
        totalAmount += thisAmount
    }

    result += "총액: ${format(totalAmount.toDouble()/100)}\n"
    result += "적립 포인트: ${volumeCredits}점\n"
    return result
}

fun main() {
    println(statement(invoices[0], plays))
}