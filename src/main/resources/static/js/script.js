$(document).ready(function() {
    $('.tooltipped').tooltip();
});

$(document).ready(function(){
    $('select').formSelect();
});

let copiarTexto = () => {
    const inputTest = document.querySelector("#inputTest");
    inputTest.select();
    document.execCommand('copy');
};

$(document).ready(function() {
    var link = document.getElementById("inputTest");
    link.value = window.location.host + "/" + link.value
});

function myFunction() {
    var link = document.getElementById("inputTest");
    var btn = document.getElementById("btn-go-to-link");
    btn.setAttribute("href", link.value);
}

$(document).ready(function () {
    $('input#input_text, textarea#textarea2').characterCounter();
});