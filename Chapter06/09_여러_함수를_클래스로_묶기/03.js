class Reading {
    constructor(data) {
        this._customer = data.customer
        this._quantity = data.quantity
        this._month = data.month
        this._year = data.year
    }

    get customer() {return this._customer}
    get quantity() {return this._quantity}
    get month() {return this._month}
    get year() {return this._year}

    get baseCharge() {
        return baseRate(this.month, this.year) * this.quantity
    }
}


// 클라이언트 3
// 데이터를 얻자마자 객체로 만든다.
const rawReading3 = acquireReading()
const aReading3 = new Reading(rawReading3)
const basicChargeAmount = aReading3.baseCharge

// 클라이언트1
const rawReading1 = acquireReading()
const aReading1 = new Reading(rawReading1)
const baseCharge = aReading1.baseCharge

// 클라이언트2
const rawReading2 = acquireReading()
const aReading2 = new Reading(rawReading2)
const taxableCharge = Math.max(0, aReading2.baseCharge - taxThreshold(aReading2.year))