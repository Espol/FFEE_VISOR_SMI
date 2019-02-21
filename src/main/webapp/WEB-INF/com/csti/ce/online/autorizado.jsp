<%-- 
    Document   : autorizado
    Created on : 27-abr-2014, 13:40:05
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documentos Autorizados</title>
    </head>
    <body> 
        <table id="dg-autorizado" class="easyui-datagrid" style="height:475px;min-width: 710px" pagination="true" 
               data-options="border:false,
               url:'${pageContext.request.contextPath}/view/autorizado/comprobante/load',
               striped:true,
               idField:'id',
               rownumbers: true,
               selectOnCheck:true,
               checkOnSelect:true,
               singleSelect:false,
               pageSize:20" toolbar="#searchAutorizado">
            <thead data-options="frozen:true">
                <tr>
                    <th rowspan="2" data-options="field:'ck',checkbox:true"></th>
					<th rowspan="2" data-options="field:'fechaRegistro',width:100,align:'center'"><strong>Fe. Documento</strong></th>
                    <th rowspan="2" data-options="field:'nroSri',width:130,align:'center'"  ><strong>Nro. SRI</strong></th>
                    <th rowspan="2" data-options="field:'docReferencia',width:140,align:'center'"  ><strong>Doc. Referencia</strong></th>
                    <!--<th rowspan="2" data-options="field:'tipoEmision',width:100,align:'center'"><strong>Tipo Emisi&oacute;n</strong></th>-->
                    <th rowspan="2" data-options="field:'interlocutor',width:100,align:'center'"><strong>C&oacute;digo</strong></th>
                </tr>
            </thead>
            <thead>
                <tr>
                    <th rowspan="2" data-options="field:'razonSocial',width:300,align:'center',haling:'center'"><strong>Interlocutor</strong></th>
                    <th colspan="2"><strong>Autorizaci&oacute;n</strong></th>
                    <th rowspan="2" data-options="field:'usuario',width:120,align:'center'"><strong>Usuario</strong></th>
                    <th rowspan="2" data-options="field:'terminal',width:120,align:'center'"><strong>T&eacute;rminal</strong></th>
                    <th colspan="3"><strong>Documentos</strong></th>
                </tr>
                <tr>
                    <th data-options="field:'nroAutorizacion',width:300,align:'center'"><strong>N&uacute;mero</strong></th>
                    <th data-options="field:'fechaAutorizacion',width:150,align:'center'"><strong>Fecha</strong></th>
                    <th data-options="field:'xml',width:80,align:'center',formatter:$.fn.XML.format"><strong>XML</strong></th>
                    <th data-options="field:'pdf',width:80,align:'center',formatter:$.fn.PDF.format"><strong>PDF</strong></th>
                    <th data-options="field:'log',width:80,align:'center',formatter:$.fn.LOG.format"><strong>LOG</strong></th>
                    <th data-options="field:'email',width:80,align:'center',formatter:$.fn.MAIL.format"><strong>EMAIL</strong></th>
                </tr>
            </thead>
        </table>
        <div id="searchAutorizado" style="padding:10px">
            <b>Busquedas por:</b>
            <select class="easyui-combobox fe-combobox-middle" data-options="valueField:'id'" id="tipoDocAutorizado" name="tipoDocAutorizado">
                <option value="01">Factura</option>
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
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search' " onclick="buscarAutorizados()">Buscar</a>
            <a href="#" id="anula" class="easyui-linkbutton" data-options="iconCls:'icon-cancel' " onclick="anularComprobantes()">Anular</a>
        </div>
        <div id="dlg-autorizados-anular" class="easyui-dialog" title="Anulacion de comprobantes" style="width:610px;height:300px;padding:10px"
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
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onClick="$.fn.anular.sendComprobantes()">Anular</a> 
                    </td>
                </tr>
            </table>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$.fn.anular.closeDialog('#dlg-autorizados-anular')">Cerrar</a>
        </div>     
               
        <script type="text/javascript"> 
                var f_1 = $.fn.date.newDateFirstDay();
                var f_td = $.fn.date.newDate();
                var dgAutorizar = $("#dg-autorizado");
                dgAutorizar.datagrid({
                    queryParams: {
                        tipoDoc: $("#tipoDocAutorizado").val(),
                        fechaIni: f_1,
                        fechaFinal: f_td,
                        nroSri: $("#searchAutorizado #nroSri").val(),
                        nroReferencia: $("#searchAutorizado #nroReferencia").val(),
                        codInterlocutor: $("#searchAutorizado #codInterlocutor").val(),
                    }
                }); 
                $('#searchAutorizado #fechaInicial').val(f_1);
                $('#searchAutorizado #fechaFinal').val(f_td);
                var pager = dgAutorizar.datagrid('getPager');
                pager.pagination({
                    showPageList: false,
                    button: [{
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
                
              function anularComprobantes() {
                $.fn.anular.setData($('#dg-autorizado').datagrid('getSelections'));
                $.fn.anular.clearGrid = function() {
                    $('#dg-autorizado').datagrid('clearSelections');
                };
                $.fn.anular.openDialog('#dlg-autorizados-anular'); 
               }
               
              $('#searchAutorizado #tipoDocAutorizado').combobox({
                onSelect: function() {
                    $("#dg-autorizado").datagrid('clearSelections');
                    buscarAutorizados();
                  }
               });
               
              function buscarAutorizados() {
                    $("#dg-autorizado").datagrid('reload', {
                        tipoDoc: $("#searchAutorizado input[name=tipoDocAutorizado]").val(),
                        fechaIni: $("#searchAutorizado #fechaInicial").datebox('getValue'),
                        fechaFinal: $("#searchAutorizado #fechaFinal").datebox('getValue'),
                        nroSri: $("#searchAutorizado #nroSri").val(),
                        nroReferencia: $("#searchAutorizado #nroReferencia").val(),
                        codInterlocutor: $("#searchAutorizado #codInterlocutor").val()
                    });
                } 
        </script>
    </body>
</html>
