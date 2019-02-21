<%-- 
    Document   : emailPuntoEmision
    Created on : 02-jun-2014, 9:58:31
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>E-MAIL DE NOTIFICACION POR PUNTO DE EMISION</title>
    </head>
    <body>
        <h3>CORREOS DE NOTIFICACI&Oacute;N</h3>
        <hr>
        <div class="container-frm">
            <fieldset>
                <legend>Parametros del servidor:</legend>
                <div align="center">
                    <table>
                        <tr>
                            <td><strong>Email:</strong></td>
                            <td><input id="idCorreoNotificacion" name="idCorreoNotificacion" type="hidden"></input></td>
                            <td><input id="correoNotificacion" class="easyui-validatebox textbox input-medium input" size="50px" type="text"></input></td>
                        </tr>
                        <tr>
                            <td><strong>Firma:</strong></td>
                            <td><input id="idFirmaNotificacion" name="idFirmaNotificacion" type="hidden"></input></td>
                            <td><input id="firmaNotificacion" class="easyui-validatebox textbox input-medium input" size="50px" type="text"></input></td>
                            <td><strong>Caducidad:</strong></td>
                            <!--<td><input id="idDiaCaducidad" type="hidden"></input></td>-->
                            <td><input id="diaCaducidad" class="easyui-validatebox textbox input-small input" readonly="true" type="text"></input></td>
                        </tr>
                        <tr>
                            <td><strong>Clave:</strong></td>
                            <td><input id="idClaveNotificacion" name="idClaveNotificacion" type="hidden"></input></td>
                            <td><input id="claveNotificacion" class="easyui-validatebox textbox input-medium input" size="50px" type="text"></input></td>
                            <td><strong>&incare; Usadas</strong></td>
                            <td><input id="claveUsadas" class="easyui-validatebox textbox input-small input" readonly="true" type="text"></input></td>
                            <td><input id="idClaveUsadas" class="easyui-validatebox textbox   input" type="hidden"></input></td>

                        </tr>
                        <tr>
                            <td><strong>Punto de Emisi&oacute;n:</strong></td>
                            <td><input id="idPuntoEmisionNotificacion" name="idPuntoEmisionNotificacion" type="hidden"></input></td>
                            <td><input id="puntoEmisionNotificacion" class="easyui-validatebox textbox input-medium input" size="50px" type="text"></input></td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <div style="text-align:center;padding:15px">
                <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" type="button" onclick="submitPuntoEmision()">Guardar</a>
            </div>
        </div>
        <script>
            $(document).ready(function() {
                $.fn.loading.open("Cargando", "Cargando Parametros Correos de Notificación");
                $.ajax({type: 'POST',
                    url: '${pageContext.request.contextPath}/view/config/emailnotificacion/load',
                    success: function(data) {
                        $("#idCorreoNotificacion").val(data.data.idCorreoNotificacion);
                        $("#correoNotificacion").val(data.data.correoNotificacion);
                        $("#idFirmaNotificacion").val(data.data.idFirmaNotificacion);
                        $("#firmaNotificacion").val(data.data.firmaNotificacion);
                        $("#idClaveNotificacion").val(data.data.idClaveNotificacion);
                        $("#claveNotificacion").val(data.data.claveNotificacion);
                        $("#idPuntoEmisionNotificacion").val(data.data.idPuntoEmisionNotificacion);
                        $("#puntoEmisionNotificacion").val(data.data.puntoEmisionNotificacion);
                        $("#diaCaducidad").val(data.data.diaCaducidad);
                        $("#idClaveUsadas").val(data.data.idClaveUsadas);
                        $("#claveUsadas").val(data.data.claveUsadas);
                        $.fn.loading.close();
                    }
                });
            });

            function submitPuntoEmision() {
                $.fn.loading.open("Actualizando", "Actualizando Parametros Correos de Notificación");
                var objeto = {idCorreoNotificacion: $("#idCorreoNotificacion").val(), correoNotificacion: $("#correoNotificacion").val(),
                    idFirmaNotificacion: $("#idFirmaNotificacion").val(), firmaNotificacion: $("#firmaNotificacion").val(),
                    idClaveNotificacion: $("#idClaveNotificacion").val(), claveNotificacion: $("#claveNotificacion").val(),
                    idPuntoEmisionNotificacion: $("#idPuntoEmisionNotificacion").val(), puntoEmisionNotificacion: $("#puntoEmisionNotificacion").val()};

                $.ajax({type: 'POST', url: "${pageContext.request.contextPath}/view/config/emailnotificacion/edit",
                    dataType: 'json',
                    data: objeto,
                    success: function(data, textStatus, jqXHR) {
                        $.fn.loading.close();
                        $.messager.alert('Actualización', data.message, 'info');
                    }
                });
            }

        </script>
    </body>
</html>
