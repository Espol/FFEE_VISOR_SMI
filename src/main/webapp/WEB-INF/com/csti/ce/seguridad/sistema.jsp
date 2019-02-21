<%-- 
    Document   : sistema
    Created on : 25-jun-2014, 16:40:48
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>SISTEMA</title>
    </head>
    <body>
        <table id="dgSistema" class="easyui-datagrid" style="min-width: 670px;height:450px"
               data-options="url:'${pageContext.request.contextPath}/view/sistema/loadAll'"
               toolbar="#toolbarSistema" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <th data-options="field:'identificador',align:'center'" width="50"><strong>Identificador</strong></th>
                    <th data-options="field:'nombre',align:'center'" width="120"><strong>Nombre</strong></th>
                    <th data-options="field:'descripcion',align:'center'" width="170"><strong>Descripci&oacute;n</strong></th>
                    <th data-options="field:'licencia',align:'center'" width="150"><strong>Licencia</strong></th>
                    <th data-options="field:'ambiente',align:'center'" width="30"><strong>Ambiente</strong></th>
                </tr>
            </thead>
        </table>
        <div id="toolbarSistema">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newSistema()">Nuevo Sistema</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editSistema()">Editar Sistema</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroySistema()">Eliminar Sistema</a>
        </div>
        <div id="dlgSistema" modal="true" class="easyui-dialog" style="width:400px;height:250px;padding:5px"
             closed="true" buttons="#dlg-buttons">
            <form id="fmSistema" method="post" novalidate>
                <div class="fitem">
                    <label class="small">Identificador :</label>
                    <input name="identificador" class="easyui-validatebox textbox input-small" type="text" data-options="required:true" >
                </div>
                <div class="fitem">
                    <label class="small">Descripci&oacute;n :</label>
                    <input name="descripcion" class="easyui-validatebox textbox input-medium" type="text" data-options="required:true" >
                </div>
                <div class="fitem">
                    <label class="small">Nombre :</label>
                    <input name="nombre" class="easyui-validatebox textbox input-small" type="text" data-options="required:true">
                </div>
                <div class="fitem">
                    <label class="small">Licencia :</label>
                    <input name="licencia" class="easyui-validatebox textbox input-medium" type="text" data-options="required:true">
                </div>
                <div class="fitem">
                    <label class="small">Ambiente :</label>
                    <input name="ambiente">
                </div>
                <div class="fitem">
                    <input name="idSistema" type="hidden">
                </div>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSistema()">Guardar</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgSistema').dialog('close')">Cancelar</a>
        </div>
        <script type="text/javascript">
            var url;
            var message;
            function newSistema() {
                $('#dlgSistema').dialog('open').dialog('setTitle', 'Nuevo Sistema');
                $('#fmSistema').form('clear');
                message = 'Guardando Sistema';
                url = '${pageContext.request.contextPath}/view/sistema/save?accion=add';
            }
            function editSistema() {
                var row = $('#dgSistema').datagrid('getSelected');
                if (row) {
                    $('#dlgSistema').dialog('open').dialog('setTitle', 'Editar Sistema');
                    $('#fmSistema').form('load', row);
                    message = 'Editando Sistema';
                    url = '${pageContext.request.contextPath}/view/sistema/save?accion=update';
                } else {
                    $.messager.alert("Advertencia", "Debe Seleccionar un Item");
                }
            }
            function saveSistema() {
                
                $('#fmSistema').form('submit', {
                    url: url,
                    onSubmit: function() {
                        var submit=$(this).form('validate');
                        if(submit){
                            $.fn.loading.open('Cargando', message);
                        }
                        return submit;
                    },
                    success: function(result) {
                        $.fn.loading.close();
                        var obj = $.parseJSON(result);
                        if (obj['success']) {
                            $.messager.alert('Confimación', obj['message']);
                            $('#dlgSistema').dialog('close');        // close the dialog
                            $('#dgSistema').datagrid('reload');
                        } else {
                            $.messager.alert('Error', obj['message']);
                            $('#dlgSistema').dialog('close');        // close the dialog
                            $('#dgSistema').datagrid('reload');
                        }
                    }
                });
            }
            function destroySistema() {
                var row = $('#dgSistema').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirmación', '¿Esta Seguro que quiere Eliminar este Sistema?', function(r) {
                        if (r) {
                            $.get('${pageContext.request.contextPath}/view/sistema/delete/' + row.idSistema, function(result) {
                                if (result.success) {
                                    $('#dgSistema').datagrid('reload');    // reload the user data
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
                $('#dgSistema').datagrid({
                    onDblClickCell: function(index, field, value) {
                        editSistema();
                    }
                });
            });
        </script>
        <style type="text/css">
           /* #fm{
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
            } */
        </style>
    </body>
</html>
