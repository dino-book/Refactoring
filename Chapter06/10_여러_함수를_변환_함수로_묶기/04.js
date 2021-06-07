// 클라이언트2
const rawReading2 = acquireReading()
const aReading2 = enrichReading(rawReading2)
const taxableCharge = aReading2.taxableCharge

function calculateBaseCharge(aReading) {
    return baseRate(aReading.month, aReading.year) * aReading.quantity
}

function enrichReading(original) {
    const result = _.cloneDeep(original)
    result.baseCharge = calculateBaseCharge(result)
    result.taxableCharge = Math.max(0, result.baseCharge - taxThreshold(result.year))
    return result
}

