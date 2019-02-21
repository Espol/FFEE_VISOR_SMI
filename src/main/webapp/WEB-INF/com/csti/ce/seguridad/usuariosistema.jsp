<%-- 
    Document   : usuariosistema
    Created on : 10-jul-2014, 17:09:35
    Author     : UsuarioSistema
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>USUARIO SISTEMA</title>
    </head>
    <body>
        <table id="dgUsuarioSistema" class="easyui-datagrid" style="min-width: 670px;height:450px"
               data-options="url:'${pageContext.request.contextPath}/view/usuasis/loadAll'"
               toolbar="#toolbar" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <th data-options="field:'descripcion',align:'center'" width="90"><strong>Descripci&oacute;n</strong></th>
                    <th data-options="field:'usuarioSistema',align:'center'" width="30"><strong>Usuario</strong></th>
                    <th data-options="field:'identificador',align:'center'" name="identificador" width="50"><strong>Identificador</strong></th>
                    <th data-options="field:'activo',align:'center',formatter:$.fn.FORMATO.format" width="30"><strong>Activo</strong></th>
                    <th data-options="field:'nombreSistema',align:'center'" width="50"><strong>Nombre Sistema</strong></th>
                    <th data-options="field:'licencia',align:'center'" width="50"><strong>Licencia</strong></th>
                </tr>
            </thead>
        </table>
    </body>
</html>
