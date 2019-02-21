<%-- 
    Document   : porautorizar
    Created on : 27-abr-2014, 16:23:45
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documentos por Autorizar</title>
    </head>
    <body>
        <table id="dg-porAutorizar" class="easyui-datagrid" style="height:475px" pagination="true" 
               data-options=" border:false, 
               url:'${pageContext.request.contextPath}/view/porautorizar/comprobante/load',
               striped:true,
               idField:'idDoc',
               rownumbers: true,
               selectOnCheck:true,
               checkOnSelect:true,
               singleSelect:false,
               pageSize:20" toolbar="#searchPorAutorizar">
            <thead data-options="frozen:true">
                <tr>
                    <th rowspan="2" data-options="field:'ck',checkbox:true" ></th>
					<th rowspan="2" data-options="field:'fechaRegistro',width:100,align:'center'"><strong>Fe. Documento</strong></th>
                    <th rowspan="2" data-options="field:'nroSri',width:130,align:'center'"  ><strong>Nro. SRI</strong></th>
                    <th rowspan="2" data-options="field:'docReferencia',width:130,align:'center'"  ><strong>Doc. Referencia</strong></th>
                    <!--<th rowspan="2" data-options="field:'tipoEmision',width:100,align:'center'"><strong>Tipo Emisi&oacute;n</strong></th>-->
                    <th rowspan="2" data-options="field:'interlocutor',width:100,align:'center'"><strong>C&oacute;digo</strong></th>
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

                    <th data-options="field:'xml',width:90,align:'center',formatter:$.fn.XML.format"><strong>XML</strong></th>
                    <th data-options="field:'log',width:90,align:'center',formatter:$.fn.LOG.format"><strong>LOG</strong></th>
                </tr>
            </thead>
        </table>
        <div id="searchPorAutorizar" style="padding:10px 10px 10px 10px"> 
            <b>Busquedas por:</b>
            <select class="easyui-combobox fe-combobox-middle" data-options="valueField:'id'" id="tipoDocPorAutorizar" name="tipoDocPorAutorizar">
                <option value="01">Factura</option>
                <option value="04">Nota Crédito</option>
                <option value="05">Nota Débito</option>
                <option value="06">Guia Remisión</option>
                <option value="07">Comp. Retención</option>
            </select>  
            <b>Desde:</b> <input type="text" id="fechaInicial" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser" value="$.fn.date.newDateFirstDay()">
            <b>Hasta:</b> <input type="text" id="fechaFinal" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser" value="$.fn.date.newDate()"> 
            <b>Nro. SRI </b>
            <input class="easyui-validatebox textbox" type="text" size="17" id="nroSri"/>
            <b>Nro. SAP </b>
            <input class="easyui-validatebox textbox" type="text" size="20" id="nroReferencia"/>
            <b>Cod. Interlocutor </b>
            <input class="easyui-validatebox textbox" type="text" size="20" id="codInterlocutor"/>
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px" onclick="buscarPorAutorizar()">Search</a> 
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-ok'" onclick="autorizarPorAutorizar()">Autorizar</a>

        </div>

        <div id="dlg-autorizar-porautorizar" class="easyui-dialog" title="Autorizaci&oacute;n de documentos en Contigencia" style="width:600px;height:300px;padding:10px"
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
            <a href="javascript:void(0)" class="easyui-linkbutton" onclick="javascript:$.fn.autorizar.closeDialog('#dlg-autorizar-porautorizar')">Cerrar</a>
        </div>
        <script type="text/javascript" >
            var f_1_pa = $.fn.date.newDateFirstDay();
            var f_td_pa = $.fn.date.newDate();
            var dgPorAutorizar = $("#dg-porAutorizar");
            dgPorAutorizar.datagrid({
                queryParams: {
                    tipoDoc: $("#tipoDocPorAutorizar").val(),
                    fechaIni: f_1_pa,
                    fechaFinal: f_td_pa,
                    nroSri: $("#searchPorAutorizar #nroSri").val(),
                    nroReferencia: $("#searchPorAutorizar #nroReferencia").val(),
                    codInterlocutor: $("#searchPorAutorizar #codInterlocutor").val()
                }
            });
            $('#searchPorAutorizar #fechaInicial').val(f_1_pa);
            $('#searchPorAutorizar #fechaFinal').val(f_td_pa);
            var pagerPorAutorizar = dgPorAutorizar.datagrid('getPager');
            pagerPorAutorizar.pagination({
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
            
            $('#tipoDocPorAutorizar').combobox({
                  onSelect: function() {                 
                   buscarPorAutorizar();
                  }
             });
             
            function buscarPorAutorizar() {
                $("#dg-porAutorizar").datagrid('reload', {
                    tipoDoc: $("#searchPorAutorizar input[name=tipoDocPorAutorizar]").val(),
                    fechaIni: $("#searchPorAutorizar #fechaInicial").datebox('getValue'),
                    fechaFinal: $("#searchPorAutorizar #fechaFinal").datebox('getValue'),
                    nroSri: $("#searchPorAutorizar #nroSri").val(),
                    nroReferencia: $("#searchPorAutorizar #nroReferencia").val(),
                    codInterlocutor: $("#searchPorAutorizar #codInterlocutor").val()
                });
            }
            function showFile(val, row) {
                console.info(val);
                console.info(row);
            }
            function autorizarPorAutorizar() {
                $.fn.autorizar.setData($('#dg-porAutorizar').datagrid('getSelections'));
                $.fn.autorizar.clearGrid = function() {
                    $('#dg-porAutorizar').datagrid('clearSelections');
                };
                $.fn.autorizar.openDialog('#dlg-autorizar-porautorizar');
            }
        </script>
    </body>
</html>
