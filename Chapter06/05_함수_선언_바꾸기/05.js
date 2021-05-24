function inNewEngland(aCustomer) {
    return ["NA", "CT", "ME", "VT", "NH", "RI"].includes(aCustomer.address.state)
}

const newEnglanders = someCustomers.filter(c => inNewEngland(c))