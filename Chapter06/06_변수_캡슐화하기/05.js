// 값 캡슐화하기
let defaultOwnerData = {
    firstName: '마틴',
    lastName: '파울러'
}

// 데이터의 복제본을 반환하게 만든다.
export function defaultOwner() {
    return Object.assign({}, defaultOwnerData)
}

export function setDefaultOwner(arg) {
    defaultOwnerData = arg
}

const owner1 = defaultOwner()
assert.equal("파울러", owner1.lastName, "처음 값 확인")

const owner2 = defaultOwner()
owner2.lastName = "파슨스"
assert.equal("파슨스", owner1.lastName, "owner2를 변경한 후") // 필드 값을 변경하는 일은 제어할 수 없다.