const reading = {
    customer: "ivan",
    quantity: 10,
    month: 5,
    year: 2017
}

const aReading1 = acquireReading()
const baseCharge = (baseRate(aReading1.month, aReading1.year) * aReading1.quantity)

const aReading2 = acquireReading()
const base = (baseRate(aReading2.month, aReading2.year) * aReading2.quantity)
const taxableCharge = Math.max(0, base - taxThreshold(aReading2.year))

const aReading3 = acquireReading()
const basicChargeAmount = calculateBaseCharge(aReading3)

function calculateBaseCharge(aReading) {
    return baseRate(aReading.month, aReading.year) * aReading.quantity
}