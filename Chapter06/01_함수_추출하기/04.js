function print0wing(invoice) {
    printBanner()

    const outstanding = calculateOutstanding(invoice) // 함수로 추출하고, 추출한 함수가 반환한 값을 원래 변수에 저장한다.

    recordDueDate(invoice)

    printDetails(invoice, outstanding) 
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

function calculateOutstanding(invoice) {
    let outstanding = 0
    for (const o of invoice.orders) {
        outstanding += o.amount
    }

    return outstanding
}