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
        <title>Parametro de Factura</title>
    </head>
    <body>
        <h3><big>CONFIGURACI&Oacute;N DE FACTURA</big></h3>
        <hr>
        <div class="container-frm">
            <form id="ff-fcParametro" method="post">
                <fieldset>
                    <legend>Path XML</legend>
                    <div align="center">
                        <table cellpadding="5">
                            <tr>
                                <td><strong>Autorización:</strong> </td>
                                <td><input id="id-autorizacion" name="id-autorizacion" type="hidden"></input></td>
                                <td><input id="autorizacion" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="autorizacion" data-options="required:true" ></input></td>
                            </tr>
                            <tr>
                                <td><strong>Rechazado:</strong></td>
                                <td><input id="id-rechazado" name="id-rechazado" type="hidden"></input></td>
                                <td><input id="rechazado" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="rechazado" data-options="required:true" ></input></td>
                            </tr>
                            <tr>
                                <td> <strong>Contingencia:</strong></td>
                                <td><input id="id-contingencia" name="id-contingencia" type="hidden"></input></td>
                                <td><input id="contingencia" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="contingencia" data-options="required:true" ></input></td>
                            </tr>
                            <tr>
                                <td><strong>Inconsistente:</strong></td>
                                <td><input id="id-inconsistente" name="id-inconsistente" type="hidden"></input></td>
                                <td><input id="inconsistente" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="inconsistente" data-options="required:true" ></input></td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Path Reporte</legend>
                    <div align="center">
                        <table cellpadding="5">
                            <tr>
                                <td><strong>Recurso:</strong></td>
                                <td><input id="id-reporte" name="id-reporte" type="hidden"></input></td>
                                <td><input id="reporte" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="reporte" data-options="required:true" ></input></td>
                            </tr>
                            <tr>
                                <td><strong>XML:</strong></td>
                                <td><input id="id-xml" name="id-xml" type="hidden"></input></td>
                                <td><input id="xml" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="xml" data-options="required:true" ></input></td>
                            </tr>
                            <tr>
                                <td><strong>PDF:</strong></td>
                                <td><input id="id-pdf" name="id-pdf" type="hidden"></input></td>
                                <td><input id="pdf" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="pdf" data-options="required:true" ></input></td>
                            </tr>
                        </table>
                    </div>
                </fieldset>
                <fieldset>
                    <legend>Esquema de Validación: XSD</legend>
                    <div align="center">
                        <table cellpadding="5">
                            <tr>
                                <td><strong>Version 1.0.0:</strong></td>
                                <td><input id="id-xsd100" name="id-xsd100" type="hidden"></input></td>
                                <td><input id="xsd100" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="xsd100" data-options="required:true" ></input></td>
                            </tr>
                            <tr>
                                <td><strong>Version 1.1.0:</strong></td>
                                <td><input id="id-xsd110" name="id-xsd110" type="hidden"></input></td>
                                <td><input id="xsd110" size="70px" class="easyui-validatebox textbox input-large input" type="text" name="xsd110" data-options="required:true" ></input></td>
                            </tr> 
                        </table>
                    </div>
                </fieldset>
                <div style="text-align:center;padding:15px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" type="button" onclick="submitFactura()">Guardar</a>
                </div>
            </form>
        </div>
        <script>
            $(document).ready(function() {
                /*$("#ff-fcParametro").form('load', '${pageContext.request.contextPath}/view/config/factura/load');*/
                $.ajax({
                    type: 'POST',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando", "Cargando Parametros Factura");
                    },
                    url: '${pageContext.request.contextPath}/view/config/factura/load',
                    dataType: 'json',
                    success: function(response, textStatus, jqXHR) {
                        $("#ff-fcParametro").form('load', response.data);
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

            function submitFactura() {
                $.fn.loading.open("Actualizando", "Actualizando Parametros Factura");
                $("#ff-fcParametro").form('submit', {
                    url: '${pageContext.request.contextPath}/view/config/factura/edit',
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
