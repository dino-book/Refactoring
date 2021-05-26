// 값 캡슐화하기
let defaultOwnerData = {
    firstName: '마틴',
    lastName: '파울러'
}

export function defaultOwner() {
    return new Person(defaultOwnerData)
}

export function setDefaultOwner(arg) {
    defaultOwnerData = arg
}

class Person {
    constructor(data) {
        this._lastName = data.lastName
        this._firstName = data.firstName
    }

    get lastName() {
        return this._lastName
    }

    get firstName() {
        return this._firstName
    }
}

const owner1 = defaultOwner()
assert.equal("파울러", owner1.lastName, "처음 값 확인")

const owner2 = defaultOwner()
owner2.lastName = "파슨스"
assert.equal("파슨스", owner1.lastName, "owner2를 변경한 후") // 필드 값을 변경하는 일은 제어할 수 없다.