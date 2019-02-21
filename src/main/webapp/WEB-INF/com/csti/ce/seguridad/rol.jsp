<%-- 
    Document   : rol
    Created on : 18-jun-2014, 15:52:37
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Rol</title>
    </head>
    <body>
        <table id="dg" class="easyui-datagrid" style="min-width: 670px;height:483px"
               data-options="url:'${pageContext.request.contextPath}/view/rol/loadAll'"
               toolbar="#toolbar" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <th data-options="field:'codigo',align:'center'" width="50"><strong>C&oacute;digo</strong></th>
                    <th data-options="field:'nombre',align:'left'" width="70"><strong>Nombre</strong></th>
                    <th data-options="field:'descripcion',align:'left'" width="170"><strong>Descripci&oacute;n</strong></th>
                    <th data-options="field:'activo',align:'center',formatter:$.fn.format.activo" width="50"><strong>Activo</strong></th>
                    <th data-options="field:'observacion',align:'left'" width="170"><strong>Observaci&oacute;n</strong></th>
                </tr>
            </thead>
        </table>
        <div id="toolbar">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newRol()">Nuevo Rol</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editRol()">Editar Rol</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyRol()">Eliminar Rol</a>
        </div>
        <div id="dlg" modal="true" class="easyui-dialog" style="width:400px;height:300px;padding:5px"
             closed="true" buttons="#dlg-buttons">
            <form id="fm" method="post" novalidate>
                <div class="fitem">
                    <label class="small">Activo :</label>
                    <input id="ckActivo" name="ckActivo" type="checkbox" class="easyui-validatebox"/>
                </div>
                <div class="fitem">
                    <label class="small">C&oacute;digo :</label>
                    <input name="codigo" class="easyui-validatebox textbox input-small" type="text" data-options="required:true"/>
                </div>
                <div class="fitem">
                    <label class="small">Nombre :</label>
                    <input name="nombre" class="easyui-validatebox textbox input-medium" type="text" data-options="required:true"/>
                </div>
                <div class="fitem">
                    <label class="small">Descripci&oacute;n :</label>
                    <input name="descripcion" class="easyui-validatebox textbox input-medium" type="text" data-options="required:true"/>
                </div>

                <div class="fitem">
                    <label class="small">Observaci&oacute;n :</label>
                    <textarea name="observacion" class="easyui-validatebox textbox input-medium" rows="5" cols="30"></textarea>
                </div>
                <div class="fitem">
                    <input name="activo" type="hidden" class="easyui-validatebox" >
                    <input name="idRol" type="hidden" class="easyui-validatebox" >
                </div>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveRol()">Guardar</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancelar</a>
        </div>
        <script type="text/javascript">
            var url;
            var message;
            function newRol() {
                $('#dlg').dialog('open').dialog('setTitle', 'Nuevo Rol');
                $('#fm').form('clear');
                $("#ckActivo").prop('checked', true);
                message = 'Guardando Rol';
                url = '${pageContext.request.contextPath}/view/rol/save?accion=add';
            }
            function editRol() {
                var row = $('#dg').datagrid('getSelected');
                if (row) {
                    $('#dlg').dialog('open').dialog('setTitle', 'Editar Rol');
                    if(row.activo === '1'){
                        $("#ckActivo").prop('checked',true);
                    } else {
                        $("#ckActivo").prop('checked',false);
                    }
                    $('#fm').form('load', row);
                    message = 'Editando Rol';
                    url = '${pageContext.request.contextPath}/view/rol/save?accion=update';
                } else {
                    $.messager.alert("Advertencia", "Debe Seleccionar un Item");
                }
            }
            function saveRol() {
                var activo = $("#ckActivo");
                if (activo.is(':checked')) {
                    $('#fm').form('load', {activo: 1});
                } else {
                    $('#fm').form('load', {activo: 0});
                }

                $('#fm').form('submit', {
                    url: url,
                    onSubmit: function() {
                        var enviar = $(this).form('validate');
                        if (enviar) {
                            $.fn.loading.open('Cargando', message);
                        }
                        return enviar;
                    },
                    success: function(result) { 
                        $.fn.loading.close();
                        var result = eval('(' + result + ')');
                        if (result.errorMsg) {
                            $.messager.show({
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        } else {
                            $('#dlg').dialog('close');        // close the dialog
                            $('#dg').datagrid('reload');    // reload the user data
                        }
                    }
                });
            }
            function destroyRol() {
                var row = $('#dg').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirmación', '¿Esta Seguro que quiere Eliminar este Rol?', function(r) {
                        if (r) {
                            $.get('${pageContext.request.contextPath}/view/rol/delete/' + row.idRol, function(result) {
                                if (result.success) {
                                    $('#dg').datagrid('reload');    // reload the user data
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
                $('#dg').datagrid({
                    onDblClickCell: function(index, field, value) {
                        editRol();
                    }
                });
            });
        </script>
    </body>
</html>
