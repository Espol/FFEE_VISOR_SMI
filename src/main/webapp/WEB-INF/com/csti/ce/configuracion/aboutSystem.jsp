<%-- 
    Document   : factura
    Created on : 13-may-2014, 17:13:31
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Acerca del Sistema</title>
    </head>
    <body>
        <h3><big>CONFIGURACION COMPROBANTES ELECTRÓNICOS</big></h3>
        <hr>
        <div class="container-frm">
            <form id="ff-fcParametro" method="post">
                <fieldset>
                    <legend>Detalles del Sistema</legend>
                    <div align="center">
                        <table cellpadding="5">
                        	<tr>
                                <td><strong>Url Servicio Web del SRI OffLine - Recepción:</strong> </td>
                                <td><input id="url-ws-recepcion-off" size="70px" readonly="true" class="easyui-validatebox textbox input-large input" type="text" name="autorizacion" data-options="required:true" >
                                	</input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Url Servicio Web del SRI OffLine - Autorización:</strong> </td>
                                <td><input id="url-ws-autorizacion-off" size="70px" readonly="true" class="easyui-validatebox textbox input-large input" type="text" name="autorizacion" data-options="required:true" >
                                	</input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Url Servicio Web del SRI OnLine - Recepción:</strong> </td>
                                <td><input id="url-ws-recepcion-on" size="70px" readonly="true" class="easyui-validatebox textbox input-large input" type="text" name="autorizacion" data-options="required:true" >
                                	</input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Url Servicio Web del SRI OnLine - Autorización:</strong> </td>
                                <td><input id="url-ws-autorizacion-on" size="70px" readonly="true" class="easyui-validatebox textbox input-large input" type="text" name="autorizacion" data-options="required:true" >
                                	</input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Ruta Sociedad:</strong> </td>
                                <td><input id="path-sociedad" size="70px" readonly="true" class="easyui-validatebox textbox input-large input" type="text" name="autorizacion" >
                                	</input>
                                </td>
                            </tr>
                            <tr><td><strong>Esquema de Validación XSD utilizado:</strong> </td>
                                <td><strong>Version 1.1.0</strong></td>
                            </tr> 
                        </table>
                    </div>
                </fieldset>
                <!-- 
                <div style="text-align:center;padding:15px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" type="button" onclick="submitFactura()">Guardar</a>
                </div>
                 -->
            </form>
        </div>
        
        <script>
            $(document).ready(function() {
            	// off-line
            	$('#url-ws-recepcion-off').val("https:\/\/cel.sri.gob.ec\/comprobantes-electronicos-ws\/RecepcionComprobantesOffline?wsdl");
            	$('#url-ws-autorizacion-off').val("https:\/\/cel.sri.gob.ec\/comprobantes-electronicos-ws\/AutorizacionComprobantesOffline?wsdl");
            	//on-line
            	$('#url-ws-recepcion-on').val("https:\/\/cel.sri.gob.ec\/comprobantes-electronicos-ws\/RecepcionComprobantes?wsdl");
            	$('#url-ws-autorizacion-on').val("https:\/\/cel.sri.gob.ec\/comprobantes-electronicos-ws\/AutorizacionComprobantes?wsdl");
            	
            	$.ajax({type: 'POST',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando","Cargando Ruta de Sociedad");
                    },
                    url: '${pageContext.request.contextPath}/view/config/about/load',
                    dataType: 'json',
                    success: function(response, textStatus, jqXHR) {
                        $("#path-sociedad").val(response.data);
                        $.fn.loading.close();
                    },
                    statusCode: {
                        404: function() {
                            $.fn.loading.close();
                            $.message.alert("page not found");
                        }
                    },
                    error: function(response) {
                        $.fn.loading.close();
                            $.message.alert("Error al Cargar Datos");
                    }});
                
            });
        </script>
    </body>
</html>

