<%-- 
    Document   : acceso
    Created on : 23/07/2014, 12:20:25 PM
    Author     : Usuario
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Acceso</title>
    </head>
    <body>
        <table id="dgAcceso" class="easyui-datagrid" style="min-width: 670px;height:450px"               
               pagination="true"
               data-options="border:false,
               url:'${pageContext.request.contextPath}/view/gui/acceso/loadAll',               
               rownumbers:true,
               fitColumns:true,
               singleSelect:true,
               pageSize:10" toolbar="#toolbarAcceso"
            >
            <thead>
                <tr>
                    <!--<th data-options="field:'idModulo',align:'center',hidden:true"><strong>Id</strong></th>-->
                    <th data-options="field:'opcion',align:'center'" width="300"><strong>Opcion</strong></th>
<!--                     <th data-options="field:'modulo',align:'center'" width="200"><strong>modulo</strong></th> -->
                    <th data-options="field:'rol',align:'center'" width="100"><strong>Rol</strong></th>
<!--                     <th data-options="field:'permiso',align:'center'" width="100"><strong>permiso</strong></th> -->

                </tr>
            </thead>
        </table>
        <div id="toolbarAcceso">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newModulo()">Nuevo Acceso</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editModulo()">Editar Acceso</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyModulo()">Borrar Acceso</a>
        </div>
    </body>
</html>
