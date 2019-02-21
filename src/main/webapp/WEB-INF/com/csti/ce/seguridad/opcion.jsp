<%-- 
    Document   : opcion
    Created on : 20-jun-2014, 12:55:25
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Opci&oacute;n</title>
    </head>
    <body>
        <table id="dgOpcion" class="easyui-datagrid" style="min-width: 670px;height:450px"
               data-options="url:'${pageContext.request.contextPath}/view/gui/loadAll?query='"
               toolbar="#toolbarOpcion" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <!--<th data-options="field:'idOpcion',align:'center',order:'desc'"><strong>Id</strong></th>-->
                    <!--<th data-options="field:'idModulo',align:'center'"><strong>Modulo</strong></th>-->
                    <th data-options="field:'codigo',align:'center'" width="70"><strong>C&oacute;digo</strong></th>
                    <th data-options="field:'nombre',align:'center'" width="100"><strong>Nombre</strong></th>
                    <th data-options="field:'descripcion',align:'left'" width="150"><strong>Descripci&oacute;n</strong></th>
                    <th data-options="field:'orden',align:'center'" width="50"><strong>Orden</strong></th>
                    <th data-options="field:'icono',align:'center',hidden:true" width="50"><strong>Icono</strong></th>
                    <th data-options="field:'controlador',align:'left'" width="150"><strong>Controlador</strong></th>
                    <th data-options="field:'vista',align:'left'" width="150"><strong>Vista</strong></th>
                    <th data-options="field:'esDirectorio',align:'center',formatter:$.fn.format.activo" width="70"><strong>Es Directorio</strong></th> 
                    <th data-options="field:'activo',align:'center',formatter:$.fn.format.activo" width="50"><strong>Activo</strong></th>
                </tr>
            </thead>
        </table>
        <div id="toolbarOpcion">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newOpcion()">Nueva Opcion</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editOpcion()">Editar Opcion</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyOpcion()">Eliminar Opcion</a>
        </div>

        <div id="dlgOpcion" modal='true' class="easyui-dialog" style="width:350px;height:390px;padding:10px 20px"
             closed="true" buttons="#dlg-buttons">
            <form id="fmOpcion" method="post" novalidate>
                <div class="fitem">
                    <label>Modulo :</label>
                    <input class="easyui-combobox" id="cmbModulo" name="idModulo" />
                    <!--<input name="modulo" class="easyui-validatebox" >-->
                </div>
                <div class="fitem">
                    <label>C&oacute;digo :</label>
                    <input name="codigo" class="easyui-validatebox" data-options="required:true" />
                </div>
                <div class="fitem">
                    <label>Nombre :</label>
                    <input name="nombre" data-options="required:true" />
                </div>
                <div class="fitem">
                    <label>Descripci&oacute;n :</label>
                    <input name="descripcion" class="easyui-validatebox" data-options="required:true" />
                </div>
                <div class="fitem">
                    <label>Icono :</label>
                    <input name="icono" class="easyui-validatebox"  />
                </div>
                <div class="fitem">
                    <label>Controlador :</label>
                    <input name="controlador" class="easyui-validatebox" data-options="required:true" />
                </div>
                <div class="fitem">
                    <label>Vista :</label>
                    <input name="vista" class="easyui-validatebox" />
                </div>
                <div class="fitem">
                    <label>Orden :</label>
                    <input name="orden" class="easyui-numberspinner" data-options="increment:1,min:1" style="width:80px;" />
                    <!--<input id='ckOrden' name="ckOrden" type="checkbox" class="easyui-validatebox">-->
                </div>
                <div class="fitem">
                    <label>Activo :</label>
                    <input id='ckOpcActivo' name="ckOpcActivo" type="checkbox" class="easyui-validatebox" />
                </div>
                <div class="fitem">
                    <label>Es Directorio :</label>
                    <input id='ckEsDirectorio' name="ckEsDirectorio" type="checkbox" class="easyui-validatebox" />
                </div>
                <div class="fitem">
                    <input name="activo" type="hidden" class="easyui-validatebox" />
                    <input name="esDirectorio" type="hidden" class="easyui-validatebox" />
                    <input name="idOpcion" type="hidden" class="easyui-validatebox" />
                </div>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveOpcion()">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgOpcion').dialog('close')">Cancel</a>
        </div>
        <script type="text/javascript">
            var url;
            var mensaje;
            function newOpcion() {
                cargarModulo(1);
                $('#dlgOpcion').dialog('open').dialog('setTitle', 'Nueva Opción');
                $('#fmOpcion').form('clear');
                mensaje = 'Guardando Opción';
                $("#ckOpcActivo").prop('checked', true);
                $("#ckEsDirectorio").prop('checked', true);
                $('#fmOpcion').form('load', {orden: 1});
                url = '${pageContext.request.contextPath}/view/gui/save?accion=add';
            }
            function editOpcion() {
                var row = $('#dgOpcion').datagrid('getSelected');
                if (row) {
                    cargarModulo(row.modulo.idModulo);
                    if (row.activo === '1') {
                        $("#ckOpcActivo").prop('checked', true);
                    } else {
                        $("#ckOpcActivo").prop('checked', false);
                    }
                    if (row.esDirectorio === '1') {
                        $("#ckEsDirectorio").prop('checked', true);
                    } else {
                        $("#ckEsDirectorio").prop('checked', false);
                    }
                    $('#dlgOpcion').dialog('open').dialog('setTitle', 'Editar Opción');
                    $('#fmOpcion').form('load', row);
                    mensaje = 'Editando Opción';
                    url = '${pageContext.request.contextPath}/view/gui/save?accion=update';
                } else {
                    $.messager.alert("Advertencia", "Debe Seleccionar un Item");
                }
            }
            function saveOpcion() {
                var activo = $("#ckOpcActivo");
                if (activo.is(':checked')) {
                    $('#fmOpcion').form('load', {activo: 1});
                } else {
                    $('#fmOpcion').form('load', {activo: 0});
                }
                var esDirectorio = $("#ckEsDirectorio");
                if (esDirectorio.is(':checked')) {
                    $('#fmOpcion').form('load', {esDirectorio: 1});
                } else {
                    $('#fmOpcion').form('load', {esDirectorio: 0});
                }
                $('#fmOpcion').form('submit', {
                    url: url,
                    onSubmit: function() {
                        return $(this).form('validate');
                    },
                    success: function(result) {
                        $.fn.loading.close();
                        var obj = $.parseJSON(result);
                        $.messager.alert('Confirmación', obj['message']);
                        $('#dlgOpcion').dialog('close');
                        $('#dgOpcion').datagrid('reload');
                    }
                });
            }
            function destroyOpcion() {
                var row = $('#dgOpcion').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirmación', '¿Esta segura que quiere Eliminar esta Opción?', function(r) {
                        if (r) {
                            $.get('${pageContext.request.contextPath}/view/gui/delete/' + row.idOpcion, function(result) {
                                if (result.success) {
                                    $('#dgOpcion').datagrid('reload'); // reload the user data
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
                $('#dgOpcion').datagrid({
                    onDblClickCell: function(index, field, value) {
                        editOpcion();
                    }
                });
            });
            function cargarModulo(valor) {
                $.ajax({type: 'POST',
                    url: '${pageContext.request.contextPath}/view/modulo/loadAll',
                    dataType: 'json',
                    success: function(data) {
                        $('#cmbModulo').combobox({
                            valueField: 'idModulo',
                            textField: 'nombre',
                            data: data.rows
                        });
                        $('#cmbModulo').combobox('setValue', valor);
                    }
                });
            }

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
