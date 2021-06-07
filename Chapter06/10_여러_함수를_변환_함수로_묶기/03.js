// 클라이언트3
const rawReading3 = acquireReading()
const aReading3 = enrichReading(rawReading3)
const basicChargeAmount = aReading3.baseCharge

// 클라이언트1
const rawReading1 = acquireReading()
const aReading1 = enrichReading(rawReading1)
const baseCharge = aReading1.baseCharge

function calculateBaseCharge(aReading) {
    return baseRate(aReading.month, aReading.year) * aReading.quantity
}

function enrichReading(original) {
    const result = _.cloneDeep(original)
    result.baseCharge = calculateBaseCharge(result)
    return result
}

it('check reading unchanged', function() {
    const baseReading = {
        customer: "ivan",
        quantity: 10,
        month: 5,
        year: 2017
    }
    const oracle = _.cloneDeep(baseReading)
    enrichReading(baseReading)
    assert.deepEqual(baseReading, oracle)
})