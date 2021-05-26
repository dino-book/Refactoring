let _title = "untitled"

result += '<h1>${title()}</h1>'

setTitle(obj['articleTitle'])

// 변수를 먼저 캡슐화한다.
function title() {
    return _title
}

function setTitle(arg) {
    _title = arg
}