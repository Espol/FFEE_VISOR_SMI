<%-- 
    Document   : clave
    Created on : 20-may-2014, 21:34:50
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Clave de Contingencia</title>
        <style>
            fieldset {
                font-family: sans-serif;
                border: 5px solid #1F497D;
                background: #ddd;
            }

            fieldset legend {
                background: #1F497D;
                color: #fff;
                padding: 5px 10px ;
                font-size: 32px;
                border-radius: 5px;
                box-shadow: 0 0 0 5px #ddd;
                margin-left: 20px;
            }
        </style>
    </head>
    <body>
        <h3><big>CLAVES DE CONTINGENCIAS</big></h3>
        <hr>
        <div style="margin: 0 auto; width: 450px;">
            <fieldset>
                <legend>&incare; Claves Usadas</legend>
                <div id="pp-clave" name="clave" class="easyui-progressbar" style="width:400px;background: white;"></div>
                <table style="margin: 0 auto">
                    <tr>
                        <td><div><input id="btnPedirClave" disabled type="button" onclick="pedirClaves()" value="Pedir Claves"/></div></td>
                        <td>
                            <fieldset>
                                <legend><strong>Claves usadas</strong></legend>
                                <div style="height: 20Px;width: 150px; background-color: #0fe0a4;text-align: center"><big>Utilizadas</big></div><br>
                                <div style="height: 20Px;width: 150px; background-color: #f0ad4e;text-align: center"><big>Necesita Atención</big></div><br>
                                <div style="height: 20Px;width: 150px; background-color: #d9534f;text-align: center"><big>Critico</big></div>
                            </fieldset>
                        </td>
                    </tr>
                </table>
            </fieldset>
        </div>
        <script type="text/javascript">
            $(document).ready(function() {
                $.ajax({type: "POST",
                    url: '${pageContext.request.contextPath}/view/config/clave/load',
                    dataType: 'json',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando", "Cargando Parametros Claves de Notificación");
                    },
                    success: function(response) {

                        $("#pp-clave").progressbar({
                            value: response.data.clave
                        });
                        var valor = response.data.clave;
                        if (valor > 75)
                            $("#btnPedirClave").attr('disabled', false);
                        $.fn.loading.close();
                    }
                });
            });

            function pedirClaves() {
                $.message.alert('Pedir Claves');
            }

        </script>
    </body>
</html>
