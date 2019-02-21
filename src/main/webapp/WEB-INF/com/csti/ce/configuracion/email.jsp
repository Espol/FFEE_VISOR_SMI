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
        <h2>CONFIGURACI&Oacute;N DE NOTIFICACI&Oacute;N DE E-MAIL</h2>
        <hr>
        <div class="container-frm">
            <form id="ff-emailNotificacion" method="post" >
                <fieldset>
                    <legend>Parametros del Servidor</legend>
                    <div align="center">
                        <table cellpadding="5">
                            <tr>
                                <td><strong>Host de Correo:</strong></td>
                                <td><input id="id-url" name="id-hostCorreo" type="hidden"></input></td>
                                <td><input id="hostCorreo" size="70px" class="easyui-validatebox textbox input-small" type="text" name="hostCorreo" ></input></td>
                            </tr>
                            <tr>
                                <td><strong>Usuario de Correo:</strong></td>
                                <td><input id="id-usuarioCorreo" type="hidden" name="id-usuarioCorreo"></input></td>
                                <td><input id="usuarioCorreo" class="easyui-validatebox textbox input-medium" type="text" name="usuarioCorreo" ></input></td>
                            </tr>
                            <tr>
                                <td><strong>Password de Email:</strong></td>
                                <td><input id="id-password" name="id-password" type="hidden"></input></td>
                                <td><input id="password" class="easyui-validatebox textbox input-small input" type="password" name="password" ></input></td>
                            </tr>
                            <tr>
                                <td><strong>Puerto:</strong></td>
                                <td><input id="id-emailNotificacion" name="id-emailNotificacion" type="hidden"></input></td>
                                <td><input id="puerto" class="easyui-validatebox textbox input-small input" type="text" name="puerto" ></input></td>
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
                        $.fn.loading.open("Cargando","Cargando Parametros Email");
                    },
                    url: '${pageContext.request.contextPath}/view/config/email/load',
                    dataType: 'json',
                    success: function(response, textStatus, jqXHR) {
                        //$("#ff-emailNotificacion").form('load', response.data);
                        $('#hostCorreo').val(response.data.host);
                        $('#usuarioCorreo').val(response.data.user);
                        $('#password').val(response.data.password);
                        $('#puerto').val(response.data.port);
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
                
                var objeto = {	hostCorreo: $('#hostCorreo').val(), 
                				usuarioCorreo: $('#usuarioCorreo').val(),
                				password: $('#password').val(),
                				puerto:	$('#puerto').val() };
                
                $.ajax({type: 'POST',
                    beforeSend: function() {
                        $.fn.loading.open("Actualizando", "Actualizando Parametros Email");
                    },
                    url: '${pageContext.request.contextPath}/view/config/email/edit',
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
