<%-- 
    Document   : contingencia
    Created on : 27-abr-2014, 16:12:15
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documentos En Contingencia</title>
    </head>
    <body>
        <table id="dg-contingencia-off" class="easyui-datagrid" style="height:475px" pagination="true" 
               data-options="border:false,
               url:'${pageContext.request.contextPath}/view/contingencia/offline/load',
               striped:true,
               idField:'id',
               rownumbers: true,
               selectOnCheck:true,
               checkOnSelect:true,
               singleSelect:false,
               pageSize:20" toolbar="#searchContingencia-off">
            <thead data-options="frozen:true">
                <tr>
                    <th rowspan="2" data-options="field:'ck',checkbox:true" ></th>
					<th rowspan="2" data-options="field:'fechaRegistro',width:100,align:'center'"><strong>Fe. Documento</strong></th>
                    <th rowspan="2" data-options="field:'nroSri',width:130,align:'center'"  ><strong>Nro. SRI</strong></th>
                    <th rowspan="2" data-options="field:'docReferencia',width:140,align:'center'"  ><strong>Doc. Referencia</strong></th>
                    <!--<th rowspan="2" data-options="field:'tipoEmision',width:100,align:'center'"><strong>Tipo Emisi&oacute;n</strong></th>-->
                    <th rowspan="2" data-options="field:'interlocutor',width:130,align:'center'"><strong>C&oacute;digo</strong></th>
                </tr>
            </thead>
            <thead>
                <tr>
                    <th rowspan="2" data-options="field:'razonSocial',width:300,align:'center',halign:'center'"><strong>Interlocutor</strong></th>
                    <th rowspan="2" data-options="field:'usuario',width:120,align:'center'"><strong>Usuario</strong></th>
                    <th rowspan="2" data-options="field:'terminal',width:120,align:'center'"><strong>T&eacute;rminal</strong></th>
                    <th colspan="3"><strong>Documentos</strong></th>
                </tr>
                <tr>
                    <th data-options="field:'xml',width:80,align:'center',formatter:$.fn.XML.format"><strong>XML</strong></th>
                    <th data-options="field:'pdf',width:80,align:'center',formatter:$.fn.PDF.format"><strong>PDF</strong></th>
                    <th data-options="field:'log',width:80,align:'center',formatter:$.fn.LOG.format"><strong>LOG</strong></th>
                </tr>
            </thead>
        </table>
        <div id="searchContingencia-off" style="padding:10px">
            <b>Busquedas por:</b>
            <select class="easyui-combobox fe-combobox-middle" id="tipoDocContingencia" data-options="valueField:'id'" name="tipoDocContingencia">
                <option value="01">Factura</option>
                <option value="04">Nota Crédito</option>
                <option value="05">Nota Débito</option>
                <option value="06">Guia Remisión</option>
                <option value="07">Comp. Retención</option>
            </select>  
            Desde : <input type="text" id="fechaInicial" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser"   onload="this.value = $.fn.date.newDateFirstDay()">
            Hasta : <input type="text" id="fechaFinal" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser" onload="this.value = $.fn.date.newDateFirstDay()"> 
            <span>Nro. SRI </span>
            <input class="easyui-validatebox textbox" data-options="prompt:'Please input somthing'" type="text" size="17" id="nroSri"/>
            <span>Nro. SAP </span>
            <input class="easyui-validatebox textbox" type="text" size="20" id="nroReferencia"/>
            <span>Cod. Interlocutor </span>
            <input class="easyui-validatebox textbox" type="text" size="20" id="codInterlocutor"/>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="buscarContingencia()">Search</a> 
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="autorizarContingencia()">Autorizar</a>
        </div>

        <div id="dlg-autorizar-contingencia" class="easyui-dialog" title="Autorizaci&oacute;n de documentos en Contigencia" style="width:600px;height:300px;padding:10px"
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
                        <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-ok',plain:true" onClick="$.fn.autorizar.sendComprobantes()">Enviar</a> 
                    </td>
                </tr>
            </table>
        </div>
        <div id="dlg-buttons">
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$.fn.autorizar.closeDialog('#dlg-autorizar-contingencia')">Cerrar</a>
        </div>
        <script tabindex="text/script">
            var f_1_c = $.fn.date.newDateFirstDay();
            var f_td_c = $.fn.date.newDate();
            var dgContingencia = $("#dg-contingencia-off");
            dgContingencia.datagrid({
                queryParams: {
                    tipoDoc: $("#tipoDocContingencia").val(),
                    fechaIni: f_1_c,
                    fechaFinal: f_td_c,
                    nroSri: $("#searchContingencia-off #nroSri").val(),
                    nroReferencia: $("#searchContingencia-off #nroReferencia").val(),
                    codInterlocutor: $("#searchContingencia-off #codInterlocutor").val()
                }
            });
            $('#searchContingencia-off #fechaInicial').val(f_1_c);
            $('#searchContingencia-off #fechaFinal').val(f_td_c);
            var pagerContingencia = dgContingencia.datagrid('getPager');
            pagerContingencia.pagination({
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
            
            $('#tipoDocContingencia').combobox({
                onSelect: function() {                 
                    buscarContingencia();
                  }
             });
              
            function buscarContingencia() {
                $("#dg-contingencia-off").datagrid('reload', {
                    tipoDoc: $("#searchContingencia-off input[name=tipoDocContingencia]").val(),
                    fechaIni: $("#searchContingencia-off #fechaInicial").datebox('getValue'),
                    fechaFinal: $("#searchContingencia-off #fechaFinal").datebox('getValue'),
                    nroSri: $("#searchContingencia-off #nroSri").val(),
                    nroReferencia: $("#searchContingencia-off #nroReferencia").val(),
                    codInterlocutor: $("#searchContingencia-off #codInterlocutor").val()
                });
            }
            function autorizarContingencia() {
                $.fn.autorizar.setData($('#dg-contingencia-off').datagrid('getSelections'));
                $.fn.autorizar.clearGrid=function(){
                    $('#dg-contingencia-off').datagrid('clearSelections');
                };
                $.fn.autorizar.openDialog('#dlg-autorizar-contingencia');
            }
        </script>
    </body>
</html>
