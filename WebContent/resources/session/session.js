SESSIONCRUD.session = new Object();

$(document).ready(function() {
    SESSIONCRUD.session.create = function() {

        var presentCheck = $('#present').find(":selected").text();
        var present = true;
        var register = $('textarea#register').val();
        
        if (presentCheck == "Sim") {
            present = true
        } else {
            present = false
        }

        var session = new Object();
        session.id_session = $("#id_session").val();
        session.date = $("#date").val();
        session.present = present;
        session.session_register = register;

        var cfg = {
            url: "rest/sessionRest/newSession",
            data: session,
            success: function(msg) {
                var cfg = {
                    title: "Mensagem",
                    height: 250,
                    width: 400,
                    modal: true,
                    buttons: {
                        "Ok": function() {
                            $(this).dialog("close");
                        }
                    }
                };

                $("#msg").html(msg);
                $("#msg").dialog(cfg);

                SESSIONCRUD.session.search();

            },
            error: function(err) {
                alert("Erro ao cadastrar uma nova sessão " + err.responseText)
            }
        };
        SESSIONCRUD.ajax.post(cfg);
    }


	SESSIONCRUD.session.search = function() {
		var dateValue = $("#consultsession").val();
		var cfg={
				type: "POST",
				url: "rest/sessionRest/searchByDate/" + dateValue,
				success: function(listDates){
					alert("CHEGOU");
					SESSIONCRUD.session.showSessions(listDates);			
				}	
		}
		SESSIONCRUD.ajax.post(cfg);
	}
    
    SESSIONCRUD.session.showSessions = function(listSession, dateValue, listDates) {
    	 
        var html = "<table class='table'>";
        html +=
            "<tr><th>Data</th><th>Presente</th><th>Registro</th><th>Editar</th><th>Delete</th></tr>";
        if (listSession != undefined && listSession.length > 0 &&
            listSession[0].id_session != undefined) {
            for (var i = 0; i < listSession.length; i++) {
            	 
                html += "<tr>" +
                    "<td>" + listSession[i].date + "</td>" +
                    "<td>" + listSession[i].present + "</td>" +
                    "<td>" + listSession[i].session_register + "</td>" +
                    "<td>" + "<a class='link' onclick='SESSIONCRUD.session.editSession(" + listSession[i].id_session +")'>Editar</a>" + "</td>" +
                    "<td>" + "<a class='link' onclick='SESSIONCRUD.session.confirmDelete(" + listSession[i].id_session + ")'>Deletar</a>" + "</td>" +
                    "</td>" +
                    "</tr>";
            }
        } else {
            if (listSession == undefined || (listSession !=
                    undefined && listSession.length > 0)) {
            	
                var cfg = {
                    type: "GET",
                    url: "rest/sessionRest/listSessions/",
                    success: function(listSession) {
                        SESSIONCRUD.session.showSessions(listSession);
                    },
                    error: function(err) {
                        var modalbug = {
                            title: "Erro!",
                            height: 250,
                            width: 400,
                            modal: true,
                            buttons: {
                                "Ok": function() {
                                    $(this).dialog("close");
                                }
                            }
                        };

                        $("#msg").html("Erro ao consultar uma sessão" +
                            err.responseText);
                        $("#msg").dialog(modalbug);

                    }
                };
                SESSIONCRUD.ajax.post(cfg);
            }else {
                html += "<tr><td colspan='3'>Nenhum registro encontrado</td></tr>";
            }
        }
        html += "</table>";
        $("#resultSession").html(html);
    };
    SESSIONCRUD.session.showSessions(undefined, "");
    
    SESSIONCRUD.session.confirmDelete = function(id_session){
		
		var confirmDel= {
				title : "Mensagem",
				height : 250,
				width : 400,
				modal : true,
				buttons : {
					"Sim" : function() {
						 SESSIONCRUD.session.deleteSession(id_session);
					},
					"Não" : function(){
						$(this).dialog("close");
					}
					
				}
		};
		
		$("#msg").html("Deseja excluir a sessão?");
		$("#msg").dialog(confirmDel);
				
		}
    SESSIONCRUD.session.deleteSession = function(id_session){
		var cfg = {
				type: "POST",
				url: "rest/sessionRest/deleteSession/"+ id_session,
				success: function (msg) {
					var cfg = {
							title : "Mensagem",
							height : 250,
							width : 400,
							modal : true,
							buttons : {
								"Ok" : function() {
									$(this).dialog("close");
								}
							}
					};
					$("#msg").html(msg);
					$("#msg").dialog(cfg);


					SESSIONCRUD.session.search();
				},
				error: function (err){
					alert("Erro ao deletar a sessão:" + err.responseText);
				}
		};
		SESSIONCRUD.ajax.post(cfg);
	};
    
	SESSIONCRUD.session.editSession = function(id_session){
		var cfg = { 
				type:"POST",
				url: "rest/sessionRest/searchBySessionId/" + id_session,
				success: function(sessionInfo){			
					$("#idUserEdit").val(sessionInfo.id_session);
					$("#dateEdit").val(sessionInfo.date);
					$("#presentEdit").val(sessionInfo.present)
					$("#registerEdit").val(sessionInfo.session_register)
					SESSIONCRUD.session.showEdit(sessionInfo);
				},
				error: function (err){
					console.log("CHEGOU no erro")
					alert ("Erro ao editar o produto:" + err.responseText);
				}

		};
        SESSIONCRUD.ajax.post(cfg);
		
	}
    
    SESSIONCRUD.session.showEdit = function(sessionInfo){
		var cfg = {
				title: "Editar",
				height: 400,
				width: 550,
				modal: true,
				buttons: {
					"Salvar": function() {
						 
						var erro = "";
						validaerro = false;
						var dialog = this;
						var newSession = new Object();
						var checkPresent = true;
						
						if ($("#presentEdit").val() == "sim" ){
							checkPresent = true;
						}else{
							checkPresent = false;
						}
						
						newSession.id_session = $("#idUserEdit").val();
						newSession.date = $("#dateEdit").val();
						newSession.present = checkPresent;
						newSession.session_register = $("#registerEdit").val();

						var cfg = {
								type: "POST",
								url: "rest/sessionRest/updateSession",
								data: newSession,
								success: function (data) {
									$(dialog).dialog("close");
	
									SESSIONCRUD.session.search();
								},
								error: function (err) {
									alert("Erro ao editar o produto:  " + err.responseText);
								}
						};
						SESSIONCRUD.ajax.post(cfg);
					},"Cancelar": function() {
						$(this).dialog( "close" );
					}
				},
				close: function() {
				}
		};
		$("#editSession").dialog(cfg);
	};
	
});