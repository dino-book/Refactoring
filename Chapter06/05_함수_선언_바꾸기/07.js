function inNewEngland(stateCode) {
    return ["NA", "CT", "ME", "VT", "NH", "RI"].includes(stateCode)
}

const newEnglanders = someCustomers.filter(c => inNewEngland(c.address.state))