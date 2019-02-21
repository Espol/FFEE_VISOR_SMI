<%-- 
    Document   : version
    Created on : 26/10/2015, 02:18:10 PM
    Author     : CSTICORP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h2>COMPROBANTES ELECTR&Oacute;NICOS</h2>
        <hr>
        <div class="container-frm" >
            <fieldset>
                <legend>Esquema Comprobantes Electr&oacute;nicos</legend>
                <div align="center">
                    <table cellpadding="5" border="1">
                        <tr>
                            <td><strong>ON-LINE:</strong></td>
                            <td><input type="radio" name="fe" value="online" ></td>
                        </tr>
                        <tr>
                            <td><strong>OFF-LINE:</strong></td>
                            <td><input type="radio" name="fe" value="offline"></td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            
            <br/>
            
            <!-- <fieldset>
                <legend>Portal</legend>
                <div align="center">
                    <table cellpadding="5" >
                        <tr>
	                        <td><strong>URL:</strong></td>
	                        <td>
	                        	<input id="url" name="url" size="70px" class="easyui-validatebox textbox input-large" type="text"></input>
	                        </td>
                        </tr>
                    </table>
                </div>
            </fieldset> -->
            
                <div style="text-align:center;padding:15px">
                    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" onclick="submitVersion()">Guardar</a>
                </div>
        </div>
        <script>
            
            $(document).ready(function (){
                $.ajax({type: "POST",
                    url: '${pageContext.request.contextPath}/view/config/version/load',
                    dataType: 'json',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando", "Cargando Parametros");
                    },
                    success: function(response) {
                        data = response.data;
                        
                        //$("#url").val( data.url );
                        
                        if(data.offline === 1){
                            $('input:radio[name=fe]').val(['offline']);
                        } else {
                            $('input:radio[name=fe]').val(['online']);
                        }
                        $.fn.loading.close();
                    }
                });
            });
            
            function submitVersion(){
                var objeto ;
                if($('input:radio[name=fe]:checked').val() === 'offline'){
                    objeto = { version: 1, url : '' };
                } else {
                    objeto = {version: 0, url : '' };
                }
                
                $.ajax({type: 'POST',
                    url: '${pageContext.request.contextPath}/view/config/version/edit',
                    dataType: 'json',
                    data: objeto,
                    beforeSend: function() {
                        $.fn.loading.open("Cargando", "Guardando Configuración");
                    },
                    success: function(data) {
                        $.fn.loading.close();
                        $.messager.alert('Actualización', data.message, 'info');
                    }
                });
            }
        </script>
    </body>
</html>
