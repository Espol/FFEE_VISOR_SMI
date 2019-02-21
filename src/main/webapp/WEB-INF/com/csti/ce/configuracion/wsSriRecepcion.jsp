<%-- 
    Document   : wsSriRecepcion
    Created on : 16-may-2014, 13:18:33
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Web Services Recepción - SRI</title>
    </head>
    <body>
        <h2>WEB SERVICES RECEPCI&Oacute;N - SRI</h2>
        <hr>
        <div class="container-frm" >
        <form id="ff-wsRecepcion" method="post" >
            <fieldset>
                <legend>Parametro de Recepci&oacute;n</legend>
                <div align="center">
                    <table cellpadding="5">
                        <tr>
                            <td><strong>URL:</strong></td>
                        <td><input id="id-url" name="id-url" type="hidden"></input></td>
                        <td><input id="url" size="70px" class="easyui-validatebox textbox  input-large input" type="text" name="url" data-options="required:true" ></input></td>
                        </tr>
                        <tr>
                            <td><strong>Nro. Intentos:</strong></td>
                        <td><input id="id-nroIntento" type="hidden" name="id-nroIntento"></input></td>
                        <td><input id="nroIntento"   name="nroIntento" class="easyui-numberspinner" data-options="min:0,max:10,required:true" ></input></td>
                        </tr>
                        <tr>
                            <td><strong>Tiempo Espera(seg.):</strong></td>
                        <td><input id="id-tiempoEspera" name="id-tiempoEspera" type="hidden"></input></td>
                        <td><input id="tiempoEspera"   name="tiempoEspera" class="easyui-numberspinner" data-options="min:0,max:10,required:true" ></input></td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <div style="text-align:center;padding:15px">
                <a href="javascript:void(0)" data-options="iconCls:'icon-save'" class="easyui-linkbutton" type="button" onclick="submitWsRecepcion()">Guardar</a>
            </div>
        </form>
        </div>
        <script type="text/javascript" >
            $("#document").ready(function() {
                /*$("#ff-wsRecepcion").form('load', '${pageContext.request.contextPath}/view/config/wsrecepcion/load');*/
                $.ajax({type: 'POST',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando","Cargando Parametros Web Service Recepción");
                    },
                    url: '${pageContext.request.contextPath}/view/config/wsrecepcion/load',
                    dataType: 'json',
                    success: function(response, textStatus, jqXHR) {
                        $("#ff-wsRecepcion").form('load', response.data);
                        $.fn.loading.close();
                    },
                    statusCode: {
                        404: function() {
                            $.fn.loading.close();
                            $.messager.alert("page not found");
                        }
                    },
                    error: function(response) {
                        $.fn.loading.close();
                            $.messager.alert("Error al Cargar Datos");
                    }});
            });
            function submitWsRecepcion() {
                $.fn.loading.open("Actualizando","Actualizando Parametros Web Service Recepción");
                $("#ff-wsRecepcion").form('submit', {
                    url: '${pageContext.request.contextPath}/view/config/wsrecepcion/edit',
                    success: function(data) {
                        var obj = $.parseJSON(data);
                        $.fn.loading.close();
                        $.messager.alert('Actualización', obj['message'], 'info');
                    }
                });
            }
        </script>
    </body>
</html>
