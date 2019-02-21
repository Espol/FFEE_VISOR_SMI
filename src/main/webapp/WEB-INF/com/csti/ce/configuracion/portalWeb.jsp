<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Configuración Portal Web</title>
    </head>
    <body>
        <h2>CONFIGURACI&Oacute;N PORTAL WEB</h2>
        <hr>
        <div class="container-frm" >
            <fieldset>
                <legend>Portal</legend>
                <div align="center">
                    <table cellpadding="5">
                        <tr>
                            <td style="width:180px"><strong>Implementa Portal:</strong></td>
                            <td><input id="portal" name="portal" type="checkbox"></td>
                            <!--<td><input type="radio" name="portal" ></td>-->
                        </tr>
                        <tr>
                            <td><strong>URL:</strong></td>
                            <td><input id="url" name="url" size="70px"
                                       class="easyui-validatebox textbox input-large" type="text"></input>
                            </td>
                        </tr>
                    </table>
                </div>
            </fieldset>
            <div style="text-align:center;padding:15px">
                <a href="javascript:void(0)" class="easyui-linkbutton" 
                   data-options="iconCls:'icon-save'" onclick="submitPortal()">Guardar</a>
            </div>
        </div>
        <script type="text/javascript">
            $(document).ready(function() {
                $.ajax({type: "POST",
                    url: '${pageContext.request.contextPath}/view/config/portal/load',
                    dataType: 'json',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando", "Cargando Parametros");
                    },
                    success: function(response) {
                        data = response.data;

                        $("#url").val(data.url);
                        if (data.portalImpl == 1) {
                            $('#portal').prop('checked', true);
                        }

                        $.fn.loading.close();
                    }
                });
            });

            function submitPortal() {
                var impl_portal = 0;
                if ($('#portal').is(':checked')) {
                    impl_portal = 1;
                }

                var objeto = {url: $("#url").val(), portalImp: impl_portal};

                $.ajax({type: 'POST',
                    url: '${pageContext.request.contextPath}/view/config/portal/edit',
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