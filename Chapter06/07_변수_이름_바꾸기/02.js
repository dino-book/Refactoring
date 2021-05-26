let tpHd = "untitled"

result += '<h1>${title()}</h1>'

setTitle(obj['articleTitle'])

// 변수를 먼저 캡슐화한다.
function title() {
    return tpHd
}

function setTitle(arg) {
    tpHd = arg
}