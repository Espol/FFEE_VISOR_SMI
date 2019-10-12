<%-- 
    Document   : inconsistente
    Created on : 27-abr-2014, 16:26:37
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
        <table id="dg-pendiente-off" class="easyui-datagrid" style="height:475px" pagination="true" 
               data-options="border:false,
               url:'${pageContext.request.contextPath}/view/pendiente/offline/load',
               striped:true,
               idField:'idDoc',
               rownumbers: true,
               selectOnCheck:true,
               checkOnSelect:true,
               singleSelect:false,
               pageSize:20" toolbar="#searchPendiente-off">
            <thead data-options="frozen:true">
                <tr>
                    <th rowspan="2" data-options="field:'ck',checkbox:true" ></th>
					<th rowspan="2" data-options="field:'fechaRegistro',width:100,align:'center'"><strong>Fe. Documento</strong></th>
                    <th rowspan="2" data-options="field:'nroSri',width:150,align:'center'"  ><strong>Nro. SRI</strong></th>
                    <th rowspan="2" data-options="field:'docReferencia',width:140,align:'center'"  ><strong>Doc. Referencia</strong></th>
                    <!--<th rowspan="2" data-options="field:'tipoEmision',width:130,align:'center'"><strong>Tipo Emisi&oacute;n</strong></th>-->
                    <th rowspan="2" data-options="field:'interlocutor',width:130,align:'center'"><strong>C&oacute;digo</strong></th>
                </tr>
            </thead>
            <thead>
                <tr>
                    <th rowspan="2" data-options="field:'razonSocial',width:300,align:'center',halign:'center'"><strong>Interlocutor</strong></th>
                    <th rowspan="2" data-options="field:'usuario',width:130,align:'center'"><strong>Usuario</strong></th>
                    <th rowspan="2" data-options="field:'terminal',width:130,align:'center'"><strong>T&eacute;rminal</strong></th>
                    <th colspan="3"><strong>Documentos</strong></th>
                </tr>
                <tr>
                    <th data-options="field:'xml',width:80,align:'center',formatter:$.fn.XML.format"><strong>XML</strong></th>
                    <th data-options="field:'pdf',width:80,align:'center',formatter:$.fn.PDF.format"><strong>PDF</strong></th>
                    <th data-options="field:'log',width:80,align:'center',formatter:$.fn.LOG.format"><strong>LOG</strong></th>
                </tr>
            </thead>
        </table>
        <div id="searchPendiente-off" style="padding:10px">
            <b>Busquedas por:</b>
            <select class="easyui-combobox fe-combobox-middle" id="tipoDocInconsistente" data-options="valueField:'id'" name="tipoDocInconsistente">
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
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="buscarPendienteOff()">Buscar</a> 
            <a href="#" id="pendiente-no-procesado" class="easyui-linkbutton" data-options="iconCls:'icon-cancel' " onclick="procesarPendientes()">Procesar Pendientes Estancados</a>
        </div>
        <!-- ----- PROCESAR DOCUMENTOS ESTANCADOS ----- -->
        <div id="dlg-autorizar-pendiente-off" class="easyui-dialog" title="Procesar Documentos estancados en Pendiente" style="width:600px;height:300px;padding:10px"
             data-options="
             iconCls: 'icon-save',
             toolbar: '#dlg-toolbar-pendiente',
             buttons: '#dlg-buttons-pendiente',
             closed: true,
             cache: false,
             modal: true
             ">
            <div id="dlg-content-text"></div>
        </div>
        <div id="dlg-toolbar-pendiente" style="padding:2px 0">
            <table cellpadding="0" cellspacing="0" style="width:100%">
                <tr>
                    <td style="padding-left:2px">
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onClick="$.fn.autorizar.sendComprobantes()">Enviar</a> 
                    </td>
                </tr>
            </table>
        </div>
        <div id="dlg-buttons-pendiente">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$.fn.autorizar.closeDialog('#dlg-autorizar-pendiente-off')">Cerrar</a>
        </div>
        <!-- ----- FIN PROCESAR DOCUMENTOS ESTANCADOS ----- -->
        <script tabindex="text/script">
            var f_1_i = $.fn.date.newDateFirstDay();
            var f_td_i = $.fn.date.newDate();
            var dgInconsistente = $("#dg-pendiente-off");
            dgInconsistente.datagrid({
                queryParams: {
                    tipoDoc: $("#tipoDocInconsistente").val(),
                    fechaIni: f_1_i,
                    fechaFinal: f_td_i,
                    nroSri: $("#searchPendiente-off #nroSri").val(),
                    nroReferencia: $("#searchPendiente-off #nroReferencia").val(),
                    codInterlocutor: $("#searchPendiente-off #codInterlocutor").val()
                }
            });
            $('#searchPendiente-off #fechaInicial').val(f_1_i);
            $('#searchPendiente-off #fechaFinal').val(f_td_i);
            var pagerInconsistente = dgInconsistente.datagrid('getPager');
            pagerInconsistente.pagination({
                // showPageList: false,
                buttons: [{
                        iconCls: 'icon-search',
                        handler: function() {
                            alert('search');
                        }
                    }, {
                        iconCls: 'icon-add',
                        handler: function() {
                            alert('add');
                        }
                    }, {
                        iconCls: 'icon-edit',
                        handler: function() {
                            alert('edit');
                        }
                    }],
                onBeforeRefresh: function() {
                    alert('before refresh');
                    return true;
                }
            });
            
             $('#searchPendiente-off #tipoDocInconsistente').combobox({
                  onSelect: function() {                 
                   buscarPendienteOff();
                  }
               });
               
            function buscarPendienteOff() {
                $("#dg-pendiente-off").datagrid('reload', {
                    tipoDoc: $("#searchPendiente-off input[name=tipoDocInconsistente]").val(),
                    fechaIni: $("#searchPendiente-off #fechaInicial").datebox('getValue'),
                    fechaFinal: $("#searchPendiente-off #fechaFinal").datebox('getValue'),
                    nroSri: $("#searchPendiente-off #nroSri").val(),
                    nroReferencia: $("#searchPendiente-off #nroReferencia").val(),
                    codInterlocutor: $("#searchPendiente-off #codInterlocutor").val()
                });
            }  
            function procesarPendientes() {
                $.fn.autorizar.setURL('/view/pendiente/offline/autorizar');
                $.fn.autorizar.setData($('#dg-pendiente-off').datagrid('getSelections'));
                $.fn.autorizar.gridPrincipal = $('#dg-pendiente-off');
                $.fn.autorizar.clearGrid = function() {
                    $('#dg-pendiente-off').datagrid('clearSelections');
                };
                $.fn.autorizar.openDialog('#dlg-autorizar-pendiente-off');
            }
        </script>
    </body>
</html>
