<%-- 
    Document   : persona
    Created on : 23-jun-2014, 20:58:41
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Personas</title>
    </head>
    <body>
        <table id="dgPersona" class="easyui-datagrid" style="min-width: 670px;height:450px"
               data-options="url:'${pageContext.request.contextPath}/view/persona/loadAll'"
               toolbar="#toolbarPersona" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <th data-options="field:'idPersona',align:'center'" width="50"><strong>Id</strong></th>
                    <th data-options="field:'tipoDocumento',align:'center'" width="50"><strong>Tipo Documento</strong></th>
                    <th data-options="field:'razonSocial',align:'center'" width="50"><strong>Razon Social</strong></th>
                    <th data-options="field:'email',align:'center'" width="50"><strong>E-mail</strong></th>
                    <th data-options="field:'activo',align:'center'" width="50"><strong>Activo</strong></th>
                    <th data-options="field:'sexo',align:'center'" width="50"><strong>Sexo</strong></th>
                    <th data-options="field:'imagen',align:'center'" width="50"><strong>Imagen</strong></th>
                    <th data-options="field:'nroDocumento',align:'center'" width="50"><strong>N&uacute;mero Documento</strong></th>
                    <th data-options="field:'direccion',align:'center'" width="50"><strong>Direcci&oacute;n</strong></th>
                    <th data-options="field:'telefono',align:'center'" width="50"><strong>Telefono</strong></th>
                </tr>
            </thead>
        </table>
        <div id="toolbarPersona">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newPersona()">Nuevo Persona</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editPersona()">Editar Persona</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyPersona()">Borrar Persona</a>
        </div>

        <div id="dlgPersona" class="easyui-dialog" style="width:400px;height:350px;padding:10px 20px"
             closed="true" buttons="#dlg-buttons">
            <form id="fmPersona" method="post" novalidate>
                <div class="fitem">
                    <label>Activo :</label>
                    <input id='ckMdlActivo' name="ckMdlActivo" type="checkbox" class="easyui-validatebox">
                </div>
                <div class="fitem">
                    <label>Sexo :</label>
                    <select id="state" class="easyui-combobox" name="state" style="width:100px;">
                        <option value="M">Masculino</option>
                        <option value="F">Femenino</option>
                    </select>
                    <!--<input id='ckOrden' name="ckOrden" type="checkbox" class="easyui-validatebox">-->
                </div>
                <div class="fitem">
                    <label>Tipo Documento :</label>
                    <input name="tipoDocumento">
                </div>
                <div class="fitem">
                    <label>Razon Social :</label>
                    <input name="razonSocial">
                </div>
                
                <div class="fitem">
                    <label>Correo :</label>
                    <input name="email" class="easyui-validatebox" >
                </div>
                
                <div class="fitem">
                    <label>N&uacute;mero Documento :</label>
                    <input name="nroDocumento" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <label>Icono :</label>
                    <input name="icono" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <label>Direcci&oacute;n :</label>
                    <input name="direccion" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <label>Telefono :</label>
                    <input name="telefono" class="easyui-validatebox" >
                </div>
                <div class="fitem">
                    <input name="idPersona" type="hidden" class="easyui-validatebox" >
                    <input name="activo" type="hidden" class="easyui-validatebox" >
                    <input name="sexo" type="hidden" class="easyui-validatebox" >
                </div>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="savePersona()">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgPersona').dialog('close')">Cancel</a>
        </div>
        <script type="text/javascript">
            var url;
            function newPersona() {
                var rows = $('#dgPersona').datagrid('getRows');
                $('#dgPersona').datagrid('selectRow', rows.length - 1);
                var last = $('#dgPersona').datagrid('getSelected');
                $('#dgPersona').datagrid('unselectRow', rows.length - 1);
                $('#dlgPersona').dialog('open').dialog('setTitle', 'Nuevo Persona');
                $('#fmPersona').form('clear');
                $('#fmPersona').form('load', {idModulo: last.idModulo + 1});
                url = '${pageContext.request.contextPath}/view/modulo/save?accion=add';
            }
            function editPersona() {
                var row = $('#dgPersona').datagrid('getSelected');
                if (row) {
                    $('#dlgPersona').dialog('open').dialog('setTitle', 'Editar Persona');

                    if (row.activo === '1') {
                        $("#ckMdlActivo").prop('checked', true);
                    }
                    $('#fmPersona').form('load', row);
                    url = '${pageContext.request.contextPath}/view/persona/save?accion=update';
                }
            }
            function savePersona() {
                var activo = $("#ckMdlActivo");
                if (activo.is(':checked')) {
                    $('#fmPersona').form('load', {activo: 1});
                } else {
                    $('#fmPersona').form('load', {activo: 0});
                }
                var orden = $("#ckOrden");
                if (orden.is(':checked')) {
                    $('#fmPersona').form('load', {orden: 1});
                } else {
                    $('#fmPersona').form('load', {orden: 0});
                }

                $('#fmPersona').form('submit', {
                    url: url,
                    onSubmit: function() {
                        return $(this).form('validate');
                    },
                    success: function(result) {
                        var result = eval('(' + result + ')');
                        if (result.errorMsg) {
                            $.messager.show({
                                title: 'Error',
                                msg: result.errorMsg
                            });
                        } else {
                            $('#dlgPersona').dialog('close');        // close the dialog
                            $('#dgPersona').datagrid('reload');    // reload the user data
                        }
                    }
                });
            }
            function destroyPersona() {
                var row = $('#dgPersona').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirmación', '¿Esta Seguro que quiere Eliminar este Modulo?', function(r) {
                        if (r) {
                            $.get('${pageContext.request.contextPath}/view/modulo/delete/' + row.idModulo, function(result) {
                                if (result.success) {
                                    $('#dgPersona').datagrid('reload');    // reload the user data
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
