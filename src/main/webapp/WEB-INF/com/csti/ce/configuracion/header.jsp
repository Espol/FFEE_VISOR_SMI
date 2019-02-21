<%-- 
    Document   : header
    Created on : 27-may-2014, 17:16:13
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cabecera</title>
    </head>
    <body>
        <div>
            <image id="imgUsuario" src='${pageContext.request.contextPath}/theme/icons/usuario.png' width="50px" height="40" />
            <table align="right">
                <tr>
                    <td><div id="id-nombre"></div></td>
                    <td><a id="linkSalir" href="javascript:void(0);" onclick="javascript:salir();">Salir</a></td>
                </tr>
            </table>
        </div>
        <script>
            function salir() {
                $.ajax({type: "POST",
                    url: '${pageContext.request.contextPath}/view/login/salir',
                    dataType: 'json',
                    success: function(data) {
                        try {
                            if (typeof data.success != 'undefined') {
                                if (data.success) {
                                    $.messager.alert('Acceso al Sistema', data.message, 'info', function() {
                                        //if(r){
                                            location.reload();
                                       // }
                                    });
                                }
                            } else {
                                throw "Ocurrió un error en el servidor.";
                            }
                        }
                        catch (err) {
                            $.messager.alert('Acceso al Sistema', 'Ocurrió un error inesperado.', 'info');
                        }
                    }
                });
            }
            /*$(document).ready(function () {
             
             });*/
        </script>
    </body>
</html>
