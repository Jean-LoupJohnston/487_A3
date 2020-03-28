
var listBooksConfig = "http://localhost:8080/ServiceSystem/resources/library/listbooks/html";
var getBookConfig = "http://localhost:8080/ServiceSystem/resources/library/displaybook?id=";
var addBookConfig = "http://localhost:8080/ServiceSystem/resources/library/addbook";
var updateBookConfig = "http://localhost:8080/ServiceSystem/resources/library/updatebook";
var deleteBookConfig = "http://localhost:8080/ServiceSystem/resources/library/deletebook/";

function getBooks()
{
	var request = new XMLHttpRequest();
	request.open('GET', listBooksConfig, true);
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	request.send();
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}
function getBookById()
{
	var request = new XMLHttpRequest();
	request.open('GET', getBookConfig+document.getElementById(2).value, true);
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	request.send();
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}

function addBook()
{
	var request = new XMLHttpRequest();
	request.open('POST', addBookConfig, true);
	request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	var data = "";
	data+='title='+ document.getElementById("t").value;
	data+='&description='+ document.getElementById("d").value;
	data+='&isbn='+ document.getElementById("i").value;
	data+='&author='+ document.getElementById("a").value;
	data+='&publisher='+ document.getElementById("p").value;
	request.send(data);
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
		
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}

function updateBook()
{
	var request = new XMLHttpRequest();
	request.open('PUT', updateBookConfig, true);
	request.setRequestHeader('Content-type', 'application/x-www-form-urlencoded');
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	var data = "";
	data+='id='+ document.getElementById("iupdate").value;
	data+='&Attribute='+ document.getElementById("attribute").value;
	data+='&NewValue='+ document.getElementById("newval").value;
	request.send(data);
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}

function deleteBook()
{
	var request = new XMLHttpRequest();
	request.open('DELETE', deleteBookConfig+document.getElementById(3).value, true);
	request.addEventListener("readystatechange", processRequest, false);
	request.onreadystatechange = processRequest;
	request.send();
	
	function processRequest(e) {
    if (request.readyState == 4 && request.status == 200) {
        document.getElementById(1).innerHTML  = request.responseText;
    }
	else{
		document.getElementById(1).innerHTML  = "Service unavailable";
	}
}
}
