<%-- 
    Document   : sapConexion
    Created on : 24/06/2014, 12:06:31 PM
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h3>SAP CONEXI&Oacute;N</h3>
        <hr>
        <div class="container-frm">
            <form id="ff-sapConfiguracion" method="post">
                <fieldset>
                    <legend>Parametros de Conexi&oacute;n</legend>
                    <table>
                        <tr>
                            <td><big><b>Mandante :</b></big></td>
                        <td><input id="mandante" name="mandante" type="text" class="easyui-validatebox textbox input-large input" /></td>
                        </tr>
                        <tr>
                            <td><big><b>Usuario :</b></big></td>
                        <td><input id="usuarioSap" name="usuarioSap" type="text" class="easyui-validatebox textbox input-large input" /></td>
                        </tr>
                        <tr>
                            <td><big><b>Clave de Acceso :</b></big></td>
                        <td><input id="passwordSap" name="passwordSap" type="text" class="easyui-validatebox textbox input-large input" /></td>
                        </tr>
                        <tr>
                            <td><big><b>Ip Configuracion :</b></big></td>
                        <td><input id="ipSap" name="ipSap" type="text" class="easyui-validatebox textbox input-large input" /></td>
                        </tr>
                        <tr>
                            <td><big><b>N&uacute;mero de Instancia :</b></big></td>
                        <td><input id="instanciaSap" name="instanciaSap" type="text" class="easyui-validatebox textbox input-large input" /></td>
                        </tr>
                        <tr>
                            <td><big><b>Idioma :</b></big></td>
                        <td><input id="idiomaSap" name="idiomaSap" type="text" class="easyui-validatebox textbox input-large input" /></td>
                        </tr>
                        <tr>
                            <td><big><b>Id Sistema :</b></big></td>
                        <td><input id="idSistema" name="idSistema" type="text" class="easyui-validatebox textbox input-large input" /></td>
                        </tr>
                        <tr>
	                        <td>
	                        	<br/>
	                        </td>
                        	<td colspan="2">
                        	</td>
                        </tr>
                    </table>
                </fieldset>
                
<!--                 <div style="color: #9F6000;background-color: #FEEFB3;width:680px"> -->
<!-- 				     <i class="fa fa-warning">(*) Recuerde que al realizar una modificación, será necesario reiniciar el servidor físico.</i>								      -->
<!-- 				</div> -->
				
                <div style="text-align:center;padding:15px">
                    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-save'" type="button" onclick="submitConexionSap()">Guardar</a>
                </div>
            </form>

        </div>
        <script>
            $(document).ready(function() {
                $.ajax({type: 'POST',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando", "Cargando Parametros Web Conexcion SAP");
                    },
                    url: '${pageContext.request.contextPath}/view/config/sap/load',
                    dataType: 'JSON',
                    success: function(response) {
                        $("#mandante").val(response.data.mandante);
                        $("#usuarioSap").val(response.data.usuario);
                        $("#passwordSap").val(response.data.contrasena);
                        $("#ipSap").val(response.data.ipServidor);
                        $("#idiomaSap").val(response.data.idioma);
                        $("#instanciaSap").val(response.data.numeroInstancia);
                        $("#idSistema").val(response.data.idSistema);
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
                    }
                });
            });
            function submitConexionSap() {
                var objeto = {
                    mandante: $("#mandante").val(),
                    usuarioSap: $("#usuarioSap").val(),
                    passwordSap: $("#passwordSap").val(),
                    ipSap: $("#ipSap").val(),
                    idiomaSap: $("#idiomaSap").val(),
                    instanciaSap: $("#instanciaSap").val(),
                    idSistema: $("#idSistema").val(),
                };
                $.ajax({type: 'POST',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando", "Guardando Web Conexcion SAP");
                    },
                    url: '${pageContext.request.contextPath}/view/config/sap/edit',
                    dataType: 'JSON',
                    data: objeto,
                    success: function(data) {
                        $.fn.loading.close();
                        $.messager.alert('Actualización', data.message, 'info');
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
                    }
                });
            }
        </script>
    </body>
</html>
