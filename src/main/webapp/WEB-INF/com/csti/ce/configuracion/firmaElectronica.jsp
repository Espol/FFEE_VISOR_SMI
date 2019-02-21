<%-- 
    Document   : firmaElectronica
    Created on : 20-may-2014, 9:57:17
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FIRMA ELECTRÓNICA</title>
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.10.2.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<link rel="stylesheet" href="/resources/demos/style.css">
<style type="text/css">
	.contenedor-tabla{
		display: table;
	}

	.contenedor-fila{
		display: table-row;
	}

	.contenedor-columna{
		display: table-cell;
	}
	.contenedor-celda{
		margin-left: 50px;
	}

.trFechaCaducidad td {
	background: red !important;
}

;
h3 {
	background: red;
}

;
body {
	background-color: olive;
}
;
</style>
</head>
<body>
	<h3>
		<big>CONFIGURACI&Oacute;N DE FIRMA ELECTR&Oacute;NICA</big>
	</h3>
	<HR>
	<div class="container-frm">
		<form id="ff-firma" name="ff-firma" method="POST">
			<fieldset>
				<legend>Parametro del Servidor</legend>
				<div style="margin-left: 17px;">
					
					<div class=”contenedor-tabla”>
					    <div class=”contenedor-fila”>
					        <div class=”contenedor-columna”>
					        	<strong>Firma Electrónica:</strong>
								<input id="firma" name="firma" size="120px" style="width: 700px; margin-left: 100px;" class="easyui-validatebox textbox input-large input" type="text"></input>
					         </div>
					     </div>
					     <br>
						 <div class=”contenedor-fila” style="width: 500px;">
					        <div class=”contenedor-columna” style="width: 205px;float: left;">
					             <strong>Fecha Caducidad:</strong>
					        </div>
					        <div class=”contenedor-columna” >
					             <input id="fechaCaducidad" name="fechaCaducidad" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser" type="text" style="padding:20px;"></input>
					        </div>
						</div>
						<br>
					    <div class=”contenedor-fila”>
					        <div class=”contenedor-columna”>
					            <strong>Clave Firma:</strong>
								<input id="clave" path="clave" name="clave"
								class="easyui-validatebox textbox input-small input"
								type="password" style="margin-left: 130px;" data-options="required:false"></input>
					        </div>
						</div>
					</div>
				</div>
			</fieldset>
			<br />
			<fieldset>
				<legend>Configuración Alertas</legend>
				<div style="margin-left: 17px;">
					<table cellpadding="5">
						<tr>
							<td><strong>Email Notificación:</strong></td>
							<td><input id="mailNotificacion" name="mailNotificacion"
								class="easyui-validatebox textbox input-large input" type="text"></input></td>
						</tr>
						<tr>
							<td><strong>Inicio Notificación (días):</strong></td>
							<td><input id="iniNotifFirma" name="iniNotifFirma"
								class="easyui-validatebox textbox input-small input" type="text"
								data-options="required:true"></input></td>
						</tr>
						<tr>
							<td><strong>Frecuencia Notificación (horas):</strong></td>
							<td><input id="intervalNotifFirma" name="intervalNotifFirma"
								class="easyui-validatebox textbox input-small input" type="text"
								data-options="required:true"></input></td>

						</tr>
					</table>
				</div>
			</fieldset>
			<div style="text-align: center; padding: 15px">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'" type="button"
					onclick="submitFirmaElectronica()">Guardar</a>

			</div>
		</form>
	</div>
	<script type="text/javascript">
		$(document)
				.ready(
						function() {

							$
									.ajax({
										type : 'POST',
										beforeSend : function() {
											$.fn.loading
													.open("Cargando",
															"Cargando Parametros Firma Electronica");
										},
										url : '${pageContext.request.contextPath}/view/config/firmaelectronica/load',
										dataType : 'json',
										success : function(response,
												textStatus, jqXHR) {
											debugger;

											var vecFirma = new Date(
													response.data.vencFirma
															.split('-').join(
																	'/'));
											var yyyy = vecFirma.getFullYear();
											var MM = vecFirma.getMonth() + 1;
											var dd = vecFirma.getDate();

											$('#fechaCaducidad').datebox('setValue', dd+"/"+MM+"/"+yyyy );
											$('#firma').val(
													response.data.pathFirma);
											$('#clave').val(
													response.data.password);

											$('#iniNotifFirma')
													.val(
															Math
																	.floor(+response.data.iniNotifFirma / 1440));
											$('#intervalNotifFirma')
													.val(
															Math
																	.floor(+response.data.intervalNotifFirma / 60));

											$('#mailNotificacion')
													.val(
															response.data.mailNotificacion);

											//$( $("#tdFechaCaducidad")[0]).children().children()[0].style.width="180px";
											//$( $("#tdFechaCaducidad")[0]).children().children().children()[0].style.height="25px";

											$.fn.loading.close();
										},
										statusCode : {
											404 : function() {
												$.fn.loading.close();
												alert("page not found");
											}
										},
										error : function(response) {
											$.fn.loading.close();
											alert("Error al Cargar Datos");
										}
									});
						});

		function submitFirmaElectronica() {

			var objeto = {

				fechaCaducidad : $('#fechaCaducidad').datebox('getValue'),

				firma : $("#firma").val(),
				clave : $("#clave").val(),

				iniNotifFirma : $("#iniNotifFirma").val() * 1444, // días a minutos
				intervalNotifFirma : $("#intervalNotifFirma").val() * 60 // minutos a horas
				,
				mailNotificacion : $("#mailNotificacion").val()
			};
			debugger;

			$
					.ajax({
						type : 'POST',
						beforeSend : function() {
							$.fn.loading
									.open("Actualizando",
											"Actualizando Parametros Firma Electronica");
						},
						url : '${pageContext.request.contextPath}/view/config/firmaelectronica/edit',
						dataType : 'JSON',
						data : objeto,
						success : function(data) {
							$.fn.loading.close();
							if (data.success) {
								$.messager.alert('Actualización', data.message,
										'info');
							} else {
								$.messager.alert('Advertencia', data.message,
										'error');
							}

						},
						statusCode : {
							404 : function() {
								$.fn.loading.close();
								$.messager.alert("page not found");
							}
						},
						error : function(response) {
							$.fn.loading.close();
							$.messager.alert("Error al Cargar Datos");
						}
					});
		}
	</script>
</body>
</html>
