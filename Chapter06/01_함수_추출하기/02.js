function print0wing(invoice) {
    let outstanding = 0

    printBanner() // 배너 출력 로직을 함수로 추출한다.

    // 미해결 채무(outstanding)을 계산한다.
    for (const o of invoice.orders) {
        outstanding += o.amount
    }

    // 마감일(dueDate)을 기록한다.
    const today = Clock.today
    invoice.dueDate = new Date(today.getFullYear(), today.getMonth(), today.getDate() + 30)

    printDetails() // 세부사항 출력 로직을 함수로 추출한다.

    function printDetails() {
        console.log('고객명: ${invoice.customer}')
        console.log('채무액: ${outstanding}')
        console.log('마감일: ${invoice.dueDate.toLocaleDateString()}')
    }
}

function printBanner() {
    console.log("****************")
    console.log("****고객채무****")
    console.log("****************")
}