function print0wing(invoice) {
    let outstanding = 0

    printBanner()

    // 미해결 채무(outstanding)을 계산한다.
    for (const o of invoice.orders) {
        outstanding += o.amount
    }

    recordDueDate(invoice) // 마감일 설정 로직을 함수로 추출한다.

    printDetails(invoice, outstanding) // 내부 함수에서 매개변수를 받는 외부 함수로 옮긴다. 
}

function printBanner() {
    console.log("****************")
    console.log("****고객채무****")
    console.log("****************")
}

function printDetails(invoice, outstanding) {
    console.log('고객명: ${invoice.customer}')
    console.log('채무액: ${outstanding}')
    console.log('마감일: ${invoice.dueDate.toLocaleDateString()}')
}

function recordDueDate(invoice) {
    // 마감일(dueDate)을 기록한다.
    const today = Clock.today
    invoice.dueDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 30)
}