package chapter01.main

import chapter01.model.Invoice
import chapter01.model.Play
import chapter01.model.PlayType
import java.lang.Exception
import java.text.NumberFormat
import java.util.*
import kotlin.math.floor
import kotlin.math.max

val plays = mapOf(
        "hamlet" to Play("Hamlet", PlayType.TRAGEDY),
        "as-like" to Play("As You Like It", PlayType.COMEDY),
        "othello" to Play("Othello", PlayType.TRAGEDY)
)

fun statement(invoice: Invoice, plays: Map<String, Play>): String {
    var totalAmount = 0
    var volumeCredits = 0
    var result = "청구 내역 (고객명 : ${invoice.customer})\n"
    val format = NumberFormat.getCurrencyInstance(Locale.US)

    for (perf in invoice.performances) {
        val play = plays[perf.playID]
        var thisAmount = 0

        when (play?.type) {
            PlayType.TRAGEDY -> {
                thisAmount = 40_000

                if (perf.audience > 30) {
                    thisAmount += 1_000 * (perf.audience - 30)
                }
            }
            PlayType.COMEDY -> {
                thisAmount = 30_000

                if (perf.audience > 20) {
                    thisAmount += 10_000 + 500 * (perf.audience - 20)
                }

                thisAmount += 300 * perf.audience
            }
            else -> throw Exception("알 수 없는 장르: ${play?.type}")
        }

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


