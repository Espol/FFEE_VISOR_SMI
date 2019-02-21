<%-- 
    Document   : permiso
    Created on : 24-jun-2014, 10:34:00
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table id="dgPermiso" class="easyui-datagrid" style="min-width: 670px;height:450px"
               data-options="url:'${pageContext.request.contextPath}/view/permiso/loadAll'"
               toolbar="#toolbarPermiso" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <!--<th data-options="field:'idPermiso',align:'center',hidden:true" ><strong>Id Permiso</strong></th>-->
                    <th data-options="field:'codigo',align:'center'" width="50"><strong>C&oacute;digo</strong></th>
                    <th data-options="field:'nombre',align:'center'" width="50"><strong>Nombre</strong></th>
                    <th data-options="field:'descripcion',align:'center'" width="50"><strong>Descripci&oacute;n</strong></th>
                    <th data-options="field:'activo',align:'center'" width="50"><strong>Activo</strong></th>
                    <th data-options="field:'actualizar',align:'center'" width="50"><strong>Actualizar</strong></th>
                    <th data-options="field:'eliminar',align:'center'" width="50"><strong>Eliminar</strong></th>
                    <th data-options="field:'agregar',align:'center'" width="50"><strong>Agregar</strong></th>
                    <th data-options="field:'lectura',align:'center'" width="50"><strong>Lectura</strong></th>
                </tr>
            </thead>
        </table>
        <div id="toolbarPermiso">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newPermiso()">Nuevo Permiso</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPermiso()">Editar Permiso</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyPermiso()">Borrar Permiso</a>
        </div>

        <div id="dlgPermiso" modal="true" class="easyui-dialog" style="width:300px;height:320px;padding:10px 20px"
             closed="true" buttons="#dlg-buttons">
            <form id="fmPermiso" method="post" novalidate>
                <div class="fitem">
                    <label>C&oacute;digo :</label>
                    <input name="codigo" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <label>Nombre :</label>
                    <input name="nombre" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <label>Descripci&oacute;n :</label>
                    <input name="descripcion" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <label>Activo :</label>
                    <input id='ckPerActivo' name="ckPerActivo" type="checkbox" class="easyui-validatebox">
                </div>
                <div class="fitem">
                    <label>Actualizar :</label>
                    <input id='ckActualizar' name="ckActualizar" type="checkbox" class="easyui-validatebox">
                </div>
                <div class="fitem">
                    <label>Eliminar :</label>
                    <input id='ckEliminar' name="ckEliminar" type="checkbox" class="easyui-validatebox">
                </div>
                <div class="fitem">
                    <label>Agregar :</label>
                    <input id='ckAgregar' name="ckAgregar" type="checkbox" class="easyui-validatebox">
                </div>
                <div class="fitem">
                    <label>Lectura :</label>
                    <input id='ckLectura' name="ckLectura" type="checkbox" class="easyui-validatebox">
                </div>
                <div class="fitem">
                    <input name="activo" type="hidden" class="easyui-validatebox" >
                    <input name="actualizar" type="hidden" class="easyui-validatebox" >
                    <input name="agregar" type="hidden" class="easyui-validatebox" >
                    <input name="eliminar" type="hidden" class="easyui-validatebox" >
                    <input name="lectura" type="hidden" class="easyui-validatebox" >
                    <input name="idPermiso" type="hidden" class="easyui-validatebox" >
                </div>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePermiso()">Guardar</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgPermiso').dialog('close')">Cancelar</a>
        </div>
        <script type="text/javascript">
            var url;
            var mensaje;
            function newPermiso() {
                $('#dlgPermiso').dialog('open').dialog('setTitle', 'Nuevo Permiso');
                $('#fmPermiso').form('clear');
                $("#ckPerActivo").prop('checked', true);
                mensaje = 'Guardando Permisos';
                url = '${pageContext.request.contextPath}/view/permiso/save?accion=save';
            }
            function editPermiso() {
                var row = $('#dgPermiso').datagrid('getSelected');
                if (row) {
                    $('#dlgPermiso').dialog('open').dialog('setTitle', 'Editar Permiso');

                    if (row.activo === '1') {
                        $("#ckPerActivo").prop('checked', true);
                    }
                    if (row.actualizar === '1') {
                        $("#ckActualizar").prop('checked', true);
                    }
                    if (row.agregar === '1') {
                        $("#ckAgregar").prop('checked', true);
                    }
                    if (row.eliminar === '1') {
                        $("#ckEliminar").prop('checked', true);
                    }
                    if (row.lectura === '1') {
                        $("#ckLectura").prop('checked', true);
                    }
                    $('#fmPermiso').form('load', row);
                    mensaje = 'Editando Permiso';
                    url = '${pageContext.request.contextPath}/view/permiso/save?accion=update';
                } else {
                    $.messager.alert("Advertencia", "Debe Seleccionar un Item");
                }
            }
            function savePermiso() {
                $.fn.loading.open('Cargando', mensaje);
                var activo = $("#ckPerActivo");
                if (activo.is(':checked')) {
                    $('#fmPermiso').form('load', {activo: 1});
                } else {
                    $('#fmPermiso').form('load', {activo: 0});
                }
                var actualizar = $("#ckActualizar");
                if (actualizar.is(':checked')) {
                    $('#fmPermiso').form('load', {actualizar: 1});
                } else {
                    $('#fmPermiso').form('load', {actualizar: 0});
                }
                var agregar = $("#ckAgregar");
                if (agregar.is(':checked')) {
                    $('#fmPermiso').form('load', {agregar: 1});
                } else {
                    $('#fmPermiso').form('load', {agregar: 0});
                }
                var eliminar = $("#ckEliminar");
                if (eliminar.is(':checked')) {
                    $('#fmPermiso').form('load', {eliminar: 1});
                } else {
                    $('#fmPermiso').form('load', {eliminar: 0});
                }
                var lectura = $("#ckLectura");
                if (lectura.is(':checked')) {
                    $('#fmPermiso').form('load', {lectura: 1});
                } else {
                    $('#fmPermiso').form('load', {lectura: 0});
                }
                $('#fmPermiso').form('submit', {
                    url: url,
                    onSubmit: function() {
                        return $(this).form('validate');
                    },
                    success: function(result) {
                        var obj = $.parseJSON(result);
                        $.fn.loading.close();
                        $.messager.alert('Confirmación', obj['message'],'info');
                        $('#dlgPermiso').dialog('close');        // close the dialog
                        $('#dgPermiso').datagrid('reload');
                    }
                });
            }
            function destroyPermiso() {
                var row = $('#dgPermiso').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirmación', '¿Esta Seguro que quiere Eliminar este Permiso?', function(r) {
                        if (r) {
                            $.get('${pageContext.request.contextPath}/view/permiso/delete/' + row.idPermiso, function(result) {
                                if (result.success) {
                                    $('#dgPermiso').datagrid('reload');    // reload the user data
                                } else {
                                    $.messager.show({// show error message
                                        title: 'Error',
                                        msg: result.errorMsg
                                    });
                                }
                            }, 'json');
                        }
                    });
                }
            }

            $(document).ready(function() {
                $('#dgPermiso').datagrid({
                    onDblClickCell: function(index, field, value) {
                        editPermiso();
                    }
                });
            });

        </script>
        <style type="text/css">
            #fm{
                margin:0;
                padding:10px 30px;
            }
            .ftitle{
                font-size:14px;
                font-weight:bold;
                padding:5px 0;
                margin-bottom:10px;
                border-bottom:1px solid #ccc;
            }
            .fitem{
                margin-bottom:5px;
            }
            .fitem label{
                display:inline-block;
                width:80px;
            }
        </style>
    </body>
</html>
