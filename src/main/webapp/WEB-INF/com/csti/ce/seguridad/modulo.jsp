<%-- 
    Document   : modulo
    Created on : 20-jun-2014, 9:19:22
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-15">
        <title>Modulo</title>
    </head>
    <body>
        <table id="dgModulo" class="easyui-datagrid" style="min-width: 670px;height:450px"
               data-options="url:'${pageContext.request.contextPath}/view/modulo/loadAll'"
               toolbar="#toolbarModulo" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <!--<th data-options="field:'idModulo',align:'center',hidden:true"><strong>Id</strong></th>-->
                    <th data-options="field:'codigo',align:'center'" width="70"><strong>C&oacute;digo</strong></th>
                    <th data-options="field:'nombre',align:'center'" width="100"><strong>Nombre</strong></th>
                    <th data-options="field:'descripcion',align:'center'" width="200"><strong>Descripci&oacute;n</strong></th>
                    <th data-options="field:'icono',align:'center',hidden:true" width="90"><strong>Icono</strong></th>
                    <th data-options="field:'activo',align:'center',formatter:$.fn.format.activo" width="50"><strong>Activo</strong></th>
                    <th data-options="field:'orden',align:'center'" width="50"><strong>Orden</strong></th>
                </tr>
            </thead>
        </table>
        <div id="toolbarModulo">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newModulo()">Nuevo Modulo</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editModulo()">Editar Modulo</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyModulo()">Borrar Modulo</a>
        </div>

        <div id="dlgModulo" modal="true" class="easyui-dialog" style="width:350px;height:280px;padding:10px 20px"
             closed="true" buttons="#dlg-buttons">
            <form id="fmModulo" method="post" novalidate>
                <div class="fitem">
                    <label>Activo :</label>
                    <input id='ckMdlActivo' name="ckMdlActivo" type="checkbox" class="easyui-validatebox">
                </div>
                <div class="fitem">
                    <label>Orden :</label>
                    <input id="nsOrden" name="orden" data-options="increment:1,min:1" class="easyui-numberspinner" style="width:80px;"></input>
                    <!--<input id='ckOrden' name="ckOrden" type="checkbox" class="easyui-validatebox">-->
                </div>
                <div class="fitem">
                    <label>C&oacute;digo :</label>
                    <input name="codigo" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <label>Nombre :</label>
                    <input name="nombre">
                </div>
                <div class="fitem">
                    <label>Descripci&oacute;n :</label>
                    <input name="descripcion" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <label>Icono :</label>
                    <input name="icono" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <input name="activo" type="hidden" class="easyui-validatebox" >
                    <input name="idModulo" type="hidden" class="easyui-validatebox" >
                </div>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveModulo()">Guardar</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgModulo').dialog('close')">Cancelar</a>
        </div>
        <script type="text/javascript">
            var url;
            var mensaje;
            function newModulo() {
                $('#dlgModulo').dialog('open').dialog('setTitle', 'Nuevo Modulo');
                $('#fmModulo').form('clear');
                $("#ckMdlActivo").prop('checked', true);
                $('#fmModulo').form('load', {orden: 1});
                mensaje = 'Guardando Modulo';
                url = '${pageContext.request.contextPath}/view/modulo/save?accion=add';
            }
            function editModulo() {
                var row = $('#dgModulo').datagrid('getSelected');
                if (row) {
                    $('#dlgModulo').dialog('open').dialog('setTitle', 'Editar Modulo');
                    if (row.activo === '1') {
                        $("#ckMdlActivo").prop('checked', true);
                    } else {
                        $("#ckMdlActivo").prop('checked', false);
                    }
                    $('#fmModulo').form('load', row);
                    mensaje = 'Editando Modulo';
                    url = '${pageContext.request.contextPath}/view/modulo/save?accion=update';
                } else {
                    $.messager.alert("Advertencia", "Debe Seleccionar un Item");
                }
            }
            function saveModulo() {
                var activo = $("#ckMdlActivo");
                if (activo.is(':checked')) {
                    $('#fmModulo').form('load', {activo: 1});
                } else {
                    $('#fmModulo').form('load', {activo: 0});
                }
                $.fn.loading.open('Guardar', mensaje);
                $('#fmModulo').form('submit', {
                    url: url,
                    onSubmit: function() {
                        return $(this).form('validate');
                    },
                    success: function(result) {
                        $.fn.loading.close();
                        var obj = $.parseJSON(result);
                        if (obj['success']) {
                            $.messager.alert('Confimación', obj['message']);
                            $('#dlgModulo').dialog('close');        // close the dialog
                            $('#dgModulo').datagrid('reload');
                        } else {
                            $.messager.alert('Error', obj['message']);
                            $('#dlgModulo').dialog('close');        // close the dialog
                            $('#dgModulo').datagrid('reload');
                        }
                    }
                });
            }
            function destroyModulo() {
                var row = $('#dgModulo').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirmación', '¿Esta Seguro que quiere Eliminar este Modulo?', function(r) {
                        if (r) {
                            $.get('${pageContext.request.contextPath}/view/modulo/delete/' + row.idModulo, function(result) {
                                if (result.success) {
                                    $('#dgModulo').datagrid('reload');    // reload the user data
                                } else {
                                    $.messager.show({// show error message
                                        title: 'Error',
                                        msg: result.errorMsg
                                    });
                                }
                            }, 'json');
                        }
                    });
                } else {
                    $.messager.alert("Advertencia", "Debe Seleccionar un Item");
                }
            }
            $(document).ready(function() {
                $('#dgModulo').datagrid({
                    onDblClickCell: function(index, field, value) {
                        editModulo();
                    }
                });
            });
        </script>
    </body>
</html>
