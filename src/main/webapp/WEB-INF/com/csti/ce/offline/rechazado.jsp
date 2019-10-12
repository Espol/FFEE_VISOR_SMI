<%-- 
    Document   : rechazado
    Created on : 27-abr-2014, 15:50:55
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documentos Rechazador</title>
    </head>
    <body>
        <table id="dg-rechazados-off" class="easyui-datagrid" style="height:475px" pagination="true" 
               data-options="border:false, 
               url:'${pageContext.request.contextPath}/view/rechazado/offline/load',
               striped:true,
               idField:'id',
               rownumbers: true,
               selectOnCheck:true,
               checkOnSelect:true,
               singleSelect:false,
               pageSize:20" toolbar="#searchRechazados-off">
            <thead data-options="frozen:true">
                <tr>
                	<th rowspan="2" data-options="field:'ck',checkbox:true"></th>
					<th rowspan="2" data-options="field:'fechaRegistro',width:100,align:'center'"><strong>Fe. Documento</strong></th>
                    <th rowspan="2" data-options="field:'nroSri',width:140,align:'center'"  ><strong>Nro. SRI</strong></th>
                    <th rowspan="2" data-options="field:'docReferencia',width:140,align:'center'"  ><strong>Doc. Referencia</strong></th>
                    <!--<th rowspan="2" data-options="field:'tipoEmision',width:110,align:'center'"><strong>Tipo Emisi&oacute;n</strong></th>-->
                    <th rowspan="2" data-options="field:'interlocutor',width:130,align:'center'"><strong>C&oacute;digo</strong></th>
                </tr>
            </thead>
            <thead>
                <tr>
                    <th rowspan="2" data-options="field:'razonSocial',width:300,align:'center',halign:'center'"><strong>Interlocutor</strong></th>
                    <th rowspan="2" data-options="field:'usuario',width:120,align:'center'"><strong>Usuario</strong></th>
                    <th rowspan="2" data-options="field:'terminal',width:120,align:'center'"><strong>T&eacute;rminal</strong></th>
                    <th colspan="2"><strong>Documentos</strong></th>
                </tr>
                <tr>  
                    <th data-options="field:'xml',width:150,align:'center',formatter:$.fn.XML.format"><strong>XML</strong></th>
                    <th data-options="field:'log',width:150,align:'center',formatter:$.fn.LOG.format"><strong>LOG</strong></th>
                </tr>
            </thead>
        </table>
        <div id="searchRechazados-off" style="padding:10px">
            <b>Busquedas por:</b>
            <select class="easyui-combobox fe-combobox-middle" id="tipoDocRechazado" data-options="valueField:'id'" name="tipoDocRechazado">
                <option value="01">Factura</option>
                <option value="03">Liquidación de Compra</option>
                <option value="04">Nota Crédito</option>
                <option value="05">Nota Débito</option>
                <option value="06">Guia Remisión</option>
                <option value="07">Comp. Retención</option>
            </select>  
            Desde : <input type="text" id="fechaInicial" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser">
            Hasta : <input type="text" id="fechaFinal" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser"> 
            <span>Nro. SRI </span>
            <input class="easyui-validatebox textbox" type="text" size="17" id="nroSri"/>
            <span>Nro. SAP </span>
            <input class="easyui-validatebox textbox" type="text" size="20" id="nroReferencia"/>
            <span>Cod. Interlocutor </span>
            <input class="easyui-validatebox textbox" type="text" size="20" id="codInterlocutor"/>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search' " onclick="buscarRechazadosOff()">Buscar</a>
            <a href="#" id="cancelar-noti" class="easyui-linkbutton" data-options="iconCls:'icon-cancel' " onclick="cancelarNotificacionCorreo()">Cancelar Notificación</a>
            <a href="#" id="autorizar-rechazado" class="easyui-linkbutton" data-options="iconCls:'icon-cancel' " onclick="autorizarClaveRegistrada()">Autorizar Clave Registrado</a>
        </div>
        <div id="dlg-noAutorizado-cancelar-notif" class="easyui-dialog" title="Cancelar Notificación Correo" style="width:610px;height:300px;padding:10px"
             data-options="
             iconCls: 'icon-save',
             toolbar: '#dlg-toolbar',
             buttons: '#dlg-buttons',
             closed: true,
             cache: false,
             modal: true
             ">
            <div id="dlg-content-text"></div>
        </div>
        <div id="dlg-toolbar" style="padding:2px 0">
            <table cellpadding="0" cellspacing="0" style="width:100%">
                <tr>
                    <td style="padding-left:2px">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onClick="$.fn.cancelar_noti_correo.sendComprobantes()">Cancelar Notificacion</a> 
                    </td>
                </tr>
            </table>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$.fn.cancelar_noti_correo.closeDialog('#dlg-noAutorizado-cancelar-notif')">Cerrar</a>
        </div>
        <!-- ----- AUTORIZAR DOCUMENTOS RECHAZADO POR CLAVE ACCESSO REGISTRADO ----- -->
        <div id="dlg-autorizar-rechazado" class="easyui-dialog" title="Autorizaci&oacute;n de documentos Rechazados por clave acceso registrado" style="width:600px;height:300px;padding:10px"
             data-options="
             iconCls: 'icon-save',
             toolbar: '#dlg-toolbar-rechazado',
             buttons: '#dlg-buttons-rechazado',
             closed: true,
             cache: false,
             modal: true
             ">
            <div id="dlg-content-text"></div>
        </div>
        <div id="dlg-toolbar-rechazado" style="padding:2px 0">
            <table cellpadding="0" cellspacing="0" style="width:100%">
                <tr>
                    <td style="padding-left:2px">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onClick="$.fn.autorizar.sendComprobantes()">Enviar</a> 
                    </td>
                </tr>
            </table>
        </div>
        <div id="dlg-buttons-rechazado">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$.fn.autorizar.closeDialog('#dlg-autorizar-rechazado')">Cerrar</a>
        </div>
        <!-- ----- AUTORIZAR RECHAZADOS ----- -->
        <script tabindex="text/script">
            var f_1_r = $.fn.date.newDateFirstDay();
            var f_td_r = $.fn.date.newDate();
            var dgAutorizar = $("#dg-rechazados-off");
            dgAutorizar.datagrid({
                queryParams: {
                    tipoDoc: $("#tipoDocRechazado").val(),
                    fechaIni: f_1_r,
                    fechaFinal: f_td_r,
                    nroSri: $("#searchRechazados-off #nroSri").val(),
                    nroReferencia: $("#searchRechazados-off #nroReferencia").val(),
                    codInterlocutor: $("#searchRechazados-off #codInterlocutor").val()
                }
            });
            
            $('#searchRechazados-off #tipoDocRechazado').combobox({
                  onSelect: function() {                 
                   buscarRechazadosOff();
                  }
             });
             
            $('#searchRechazados-off #fechaInicial').val(f_1_r);
            $('#searchRechazados-off #fechaFinal').val(f_td_r);
            function buscarRechazadosOff() {
                $("#dg-rechazados-off").datagrid('reload', {
                    tipoDoc: $("#searchRechazados-off input[name=tipoDocRechazado]").val(),
                    fechaIni: $("#searchRechazados-off #fechaInicial").datebox('getValue'),
                    fechaFinal: $("#searchRechazados-off #fechaFinal").datebox('getValue'),
                    nroSri: $("#searchRechazados-off #nroSri").val(),
                    nroReferencia: $("#searchRechazados-off #nroReferencia").val(),
                    codInterlocutor: $("#searchRechazados-off #codInterlocutor").val()
                });
            }
            
            function cancelarNotificacionCorreo() {
                $.fn.cancelar_noti_correo.setData($('#dg-rechazados-off').datagrid('getSelections'));
                $.fn.cancelar_noti_correo.gridPrincipal = $('#dg-rechazados-off');
                $.fn.cancelar_noti_correo.clearGrid = function() {
                    $('#dg-rechazados-off').datagrid('clearSelections');
                };
                $.fn.cancelar_noti_correo.openDialog('#dlg-noAutorizado-cancelar-notif'); 
               }
               
            function autorizarClaveRegistrada() {
                $.fn.autorizar.setURL('/view/rechazado/offline/autorizar');
                $.fn.autorizar.setData($('#dg-rechazados-off').datagrid('getSelections'));
                $.fn.autorizar.gridPrincipal = $('#dg-rechazados-off');
                $.fn.autorizar.clearGrid = function() {
                    $('#dg-rechazados-off').datagrid('clearSelections');
                };
                $.fn.autorizar.openDialog('#dlg-autorizar-rechazado');
            }
        </script>
    </body>
</html>
