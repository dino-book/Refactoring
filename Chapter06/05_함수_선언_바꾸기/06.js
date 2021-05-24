function inNewEngland(aCustomer) {
    return xxNewinNewEngland(aCustomer.address.state)
}

function xxNewinNewEngland(stateCode) {
    return ["NA", "CT", "ME", "VT", "NH", "RI"].includes(stateCode)
}

const newEnglanders = someCustomers.filter(c => xxNewinNewEngland(c.address.state))