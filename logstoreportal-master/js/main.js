const endpoint = "http://localhost:8080/logstore"; //END POINT

/* IMPLEMENTAÇÃO DAS CHAMADAS AJAX */
function clientRequest(action, url, data, callback, type) {
  if ($.isFunction(data)) {
    (type = type || callback), (callback = data), (data = {});
  }
  var setting = {
    url: url,
    method: action,
    timeout: 0,
    success: callback,
    data: data,
    headers: {
      "Content-Type": type,
    },
  };
  return $.ajax(setting);
}

//POST - ADICIONAR NOVO LOG
function postLog(data) {
  data.preventDefault(); //Cancela o evento se for cancelável, sem parar a propagação do mesmo.
  jsonData = JSON.stringify(Object.fromEntries(new FormData(data.target)));
  clientRequest(
    "POST",
    endpoint + "/log",
    jsonData,
    function () {
      $("#btnCancelar").click(); //Forçando o fechamento do modal
      clearDataForm(); // Linpando o form do modal
      getLogs(); //Listando Logs
    },
    "application/json"
  );
  return false;
}

function clearDataForm() {
  $("#content, #occurrences").val("");
}

/**  POST - in xhr
$("#newLog").click(function() {
  var formData = new FormData();
  var content = document.getElementById("content");
  var occurrences = document.getElementById("occurrences");

  if (content.value != "" && occurrences.value != "") {
    formData.append("content", content.value);
    formData.append("occurrences", occurrences.value);
    var jsonData = JSON.stringify(Object.fromEntries(formData));
    console.log(jsonData)
    var xmlhtpp = new XMLHttpRequest();
    xmlhtpp.withCredentials = true;

    if (xmlhtpp.onreadystatechange === 4 && xmlhtpp.status === 200) {
      alert(xmlhtpp.responseText);
    }
    
    xmlhtpp.open("POST", "http://localhost:8080/logstore/log");
    xmlhtpp.setRequestHeader("Content-Type", "application/json");
    xmlhtpp.setRequestHeader("Accept", "*");
    xmlhtpp.send(jsonData);
  } else {
    alert("Preencha os campos!");
  }
});
*/

//ALTERAR LOG / Com problemas de duplicidade
function putLog(data) {
  data.preventDefault();
  jsonData = JSON.stringify(Object.fromEntries(new FormData(data.target)));
  clientRequest(
    "PUT",
    endpoint + "/log",
    jsonData,
    function () {
      alert("Log alterado com sucesso!");
      $("#btnCancelarEdicao").click(); //Forçando o fechamento do modal
      clearDataForm(); // Linpando o form do modal
      getLogs();
    },
    "application/json"
  );
}

//DELETAR LOG
function deletarLog(id) {
  if (!window.confirm("Você realmente quer deletar?")) {
    return;
  }
  clientRequest(
    "DELETE",
    endpoint + "/log/" + id,
    null,
    function () {
      alert("Log deletado com sucesso! [" + id + "]");
      getLogs();
    },
    "application/json"
  );
}

//Função inicial (Somente para carregar os logs no início da aplicação)
$(document).ready(function () {
  getLogs();
});

function getLogs() {
  $.get(endpoint + "/log", function (data) {
    //Resolvendo duplicidade
    $("#logTable > thead > tr").not(":first").remove(); //

    console.log("dados v1", data); //Dados
    if (data != null) {
      data.forEach((log) => {
        var content;
        if (log.content.length > 150) {
          content = log.content.slice(0, 150) + "...";
        } else {
          content = log.content;
        }
        var table = document.getElementById("logTable");//Populando a tabela
        var row = table.insertRow();
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        cell1.textContent = content;
        cell2.innerHTML = log.occurrences;
        
        //BOTÕES EDITAR E DELETAR
        cell3.innerHTML =
          "<a class='btn btn-info ' data-toggle='modal' data-target='#updateLogModal'" +
          log.id +
          "'>Editar </a> <button class='btn btn-danger' onClick='deletarLog(" +
          log.id +
          ")'> Deletar </button>";
      });
    }
  });
}
