let defaultOwner = {
    firstName: '마틴',
    lastName: '파울러'
}
spaceship.owner = getDefaultOwner()

defaultOwner = setDefaultOwner({
    firstName: '레베카',
    lastName: '파슨스'
})

function getDefaultOwner() {
    return defaultOwner
}

function setDefaultOwner(arg) {
    defaultOwner = arg
}