<%-- 
    Document   : emailNotificacion
    Created on : 16-may-2014, 17:08:48
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Notificación de Email</title>
    </head>
    <body>
        <h2>CONFIGURACI&Oacute;N DE E-MAIL POR TIPO DE DOCUMENTO</h2>
        <hr>
        <div class="container-frm">
            <form id="ff-emailNotificacion" method="post" >
                <fieldset>
                    <legend>Email</legend>
                    <div align="center">
                        <table cellpadding="5">
                            <tr>
                                <td><strong>E-mail Factura:</strong></td>
                                <td><input id="id-url" name="id-hostCorreo" type="hidden"></input></td>
                                <td>
                                	<input id="mailFactura" name="mailFactura" size="70px" class="easyui-validatebox textbox input-large" type="text"></input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>E-mail Nota De Crédito:</strong></td>
                                <td><input id="id-url" name="id-hostCorreo" type="hidden"></input></td>
                                <td>
                                	<input id="mailCredito" name="mailCredito" size="70px" class="easyui-validatebox textbox input-large" type="text"></input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>E-mail Nota De Débito:</strong></td>
                                <td><input id="id-url" name="id-hostCorreo" type="hidden"></input></td>
                                <td>
                                	<input id="mailDebito" name="mailDebito" size="70px" class="easyui-validatebox textbox input-large" type="text"></input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>E-mail Guía De Remisión:</strong></td>
                                <td><input id="id-url" name="id-hostCorreo" type="hidden"></input></td>
                                <td>
                                	<input id="mailGuia" name="mailGuia" size="70px" class="easyui-validatebox textbox input-large" type="text"></input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>E-mail Comprobante De Retención:</strong></td>
                                <td><input id="id-url" name="id-hostCorreo" type="hidden"></input></td>
                                <td>
                                	<input id="mailRetencion" name="mailRetencion" size="70px" class="easyui-validatebox textbox input-large" type="text"></input>
                                </td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
                
                <br/>
                
                <fieldset>
                    <legend>Notificación Documentos Rechazados</legend>
                    <div align="center">
                        <table cellpadding="5">
                            <tr>
                                <td><strong>Tiempo Disponible De Correción (horas):</strong></td>
                                <td>
                                	<input id="iniTimeAvailableCorrecion" name="iniTimeAvailableCorrecion" size="70px" class="easyui-validatebox textbox input-small" type="text" data-options="required:true"></input>
                                </td>
                            </tr>
                            <tr>
                                <td><strong>Frecuencia Notificación (minutos):</strong></td>
                                <td>
                                	<input id="intervalNotifDocRechazado" name="intervalNotifDocRechazado" size="70px" class="easyui-validatebox textbox input-small" type="text" data-options="required:true"></input>
                                </td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
                
                
                <div style="text-align:center;padding:15px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" type="button" onclick="submitEmail()">Guardar</a>
                </div>
            </form>
        </div>
        <script type="text/javascript" >
            $("#document").ready(function() {
                /*$("#ff-emailNotificacion").form('load', '${pageContext.request.contextPath}/view/config/email/load');*/
                $.ajax({type: 'POST',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando","Cargando Parametros");
                    },
                    url: '${pageContext.request.contextPath}/view/config/emailxtipodoc/load',
                    dataType: 'json',
                    success: function(response, textStatus, jqXHR) {
                        //$("#ff-emailNotificacion").form('load', response.data);
                        $('#mailFactura').val(response.data.mailFactura);
                        $('#mailCredito').val(response.data.mailCredito);
                        $('#mailDebito').val(response.data.mailDebito);
                        $('#mailGuia').val(response.data.mailGuia);
                        $('#mailRetencion').val(response.data.mailRetencion);
                        
                        debugger;
                        
                        $('#iniTimeAvailableCorrecion').val(response.data.iniTimeAvailableCorrecion + 1);
                        $('#intervalNotifDocRechazado').val(response.data.intervalNotifDocRechazado);
                        
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
            function submitEmail() {
                $.fn.loading.open("Actualizando", "Actualizando Parametros");
                
                /*$("#ff-emailNotificacion").form('submit', {
                    url: '${pageContext.request.contextPath}/view/config/emailxtipodoc/edit',
                    dataType: 'json',
                    success: function(data) {
                        var obj = JSON.stringify(data);
                        $.fn.loading.close();
                        $.messager.alert('Actualización', obj['message'], 'info');

//                         $('#iniTimeAvailableCorrecion').val( $('#iniTimeAvailableCorrecion').val() + 1 );
                    }
                });*/
                
                
                var objeto = {  mailFactura: $('#mailFactura').val(), 
                				mailCredito : $('#mailCredito').val(), 
                			  	mailDebito: $('#mailDebito').val(), 
                			  	mailGuia: $('#mailGuia').val(), 
                			  	mailRetencion: $('#mailRetencion').val(),
                			  	iniTimeAvailableCorrecion: $('#iniTimeAvailableCorrecion').val(), 
                			  	intervalNotifDocRechazado: $('#intervalNotifDocRechazado').val()};
                
                
                $.ajax({type: 'POST',
                    beforeSend: function() {
                        $.fn.loading.open("Actualizando", "Actualizando Parametros");
                    },
                    url: '${pageContext.request.contextPath}/view/config/emailxtipodoc/edit',
                    dataType: 'json',
                    data: objeto,
                    success: function(response, textStatus, jqXHR) {
                        $.fn.loading.close();
                        $.messager.alert('Actualización', response.message, 'info');
                    }
                 });
                
            }
        </script>
    </body>
</html>
