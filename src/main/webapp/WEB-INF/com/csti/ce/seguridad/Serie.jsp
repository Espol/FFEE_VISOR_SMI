<%-- 
    Document   : Serie
    Created on : 10-jul-2014, 15:57:28
    Author     : Serie
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <table id="dgSerie" title="My Series" class="easyui-datagrid" style="width:700px;height:250px"
               url="get_users.php"
               toolbar="#toolbar" pagination="true"
               rownumbers="true" fitColumns="true" singleSelect="true">
            <thead>
                <tr>
                    <th field="firstname" width="50">First Name</th>
                    <th field="lastname" width="50">Last Name</th>
                    <th field="phone" width="50">Phone</th>
                    <th field="email" width="50">Email</th>
                </tr>
            </thead>
        </table>
        <div id="toolbar">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newSerie()">Nueva Serie</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editSerie()">Editar Serie</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroySerie()">Eliminar Serie</a>
        </div>

        <div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
             closed="true" buttons="#dlg-buttons">
            <form id="fmSerie" method="post" novalidate>
                <div class="fitem">
                    <label>Establecimiento:</label>
                    <input name="establecimiento" class="easyui-validatebox" required="true" />
                </div>
                <div class="fitem">
                    <label>Punto Emision:</label>
                    <input name="puntoEmision" class="easyui-validatebox" required="true" />
                </div>
                <div class="fitem">
                    <label>Activo:</label>
                    <input id="ckUserActivo" type="checkbox" class="easyui-validatebox" />
                </div>
                <div class="fitem">
                    <label>Email:</label>
                    <input name="email" class="easyui-validatebox" validType="email">
                </div>
            </form>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveSerie()">Save</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">Cancel</a>
        </div>
        <script type="text/javascript">
            var url;
            function newSerie() {
                $('#dlgSerie').dialog('open').dialog('setTitle', 'Nueva Serie');
                $('#fmSerie').form('clear');
                url = 'save_user.php';
            }
            function editSerie() {
                var row = $('#dgSerie').datagrid('getSelected');
                if (row) {
                    $('#dlgSerie').dialog('open').dialog('setTitle', 'Editar Serie');
                    $('#fmSerie').form('load', row);
                    url = 'update_user.php?id=' + row.id;
                }
            }
            function saveSerie() {
                $('#fmSerie').form('submit', {
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
                            $('#dlgSerie').dialog('close');        // close the dialog
                            $('#dgSerie').datagrid('reload');    // reload the user data
                        }
                    }
                });
            }
            function destroySerie() {
                var row = $('#dgSerie').datagrid('getSelected');
                if (row) {
                    $.messager.confirm('Confirm', 'Are you sure you want to destroy this user?', function(r) {
                        if (r) {
                            $.post('destroy_user.php', {id: row.id}, function(result) {
                                if (result.success) {
                                    $('#dgSerie').datagrid('reload');    // reload the user data
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
            #fmSerie{
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
