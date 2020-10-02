package chapter01.main

import chapter01.model.Invoice
import chapter01.model.Performance
import chapter01.model.Play
import chapter01.model.PlayType
import java.lang.Exception
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.max

fun statement2(invoice: Invoice, plays: Map<String, Play>): String {
    var totalAmount = 0
    var volumeCredits = 0
    var result = "청구 내역 (고객명 : ${invoice.customer})\n"
    val format = NumberFormat.getCurrencyInstance(Locale.US)

    for (perf in invoice.performances) {
        val play = playFor(perf)
        val thisAmount = amountFor(perf)

        // 포인트를 적립한다.
        volumeCredits += max(perf.audience - 30, 0)

        // 희극 관객 5명마다 추가 포인트를 제공한다.
        if (play.type == PlayType.COMEDY) {
            volumeCredits += floor(perf.audience.toDouble() / 5).toInt()
        }

        // 청구 내역을 출력한다.
        result += " ${play.name}: ${format.format(totalAmount/100)}\n"
        totalAmount += thisAmount
    }

    result += "총액: ${format.format(totalAmount / 100)}\n"
    result += "적립 포인트: ${volumeCredits}점\n"

    return result
}

fun playFor(performance: Performance): Play {
    return plays.getValue(performance.playID)
}

fun amountFor(performance: Performance): Int {
    var result = 0
    val play = playFor(performance)

    when (play.type) {
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
        else -> throw Exception("알 수 없는 장르: ${play.type}")
    }

    return result
}


