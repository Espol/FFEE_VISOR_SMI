<%-- 
    Document   : anulados
    Created on : 28/03/2015, 05:26:43 PM
    Author     : Ammiel
--%>


<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Documentos Anulados</title>
    </head>
    <body> 
        <table id="dg-anulado-off" class="easyui-datagrid" style="height:475px;min-width: 710px" pagination="true" 
               data-options="border:false,
               url:'${pageContext.request.contextPath}/view/anulado/offline/load',
               striped:true,
               idField:'idDoc',
               rownumbers: true,
               singleSelect:true,
               pageSize:20" toolbar="#searchAnulado-off">
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
                    <th rowspan="2" data-options="field:'razonSocial',width:300,align:'center',haling:'center'"><strong>Interlocutor</strong></th>
                    <th rowspan="2" data-options="field:'usuario',width:130,align:'center'"><strong>Usuario</strong></th>
                    <th rowspan="2" data-options="field:'terminal',width:130,align:'center'"><strong>T&eacute;rminal</strong></th>
                    <th colspan="2"><strong>Documentos</strong></th>
                </tr>
                <tr>
                   <th data-options="field:'xml',width:80,align:'center',formatter:$.fn.XML.format"><strong>XML</strong></th>
                   <th data-options="field:'pdf',width:80,align:'center',formatter:$.fn.PDF.format"><strong>PDF</strong></th>
                   <th data-options="field:'log',width:80,align:'center',formatter:$.fn.LOG.format"><strong>LOG</strong></th>
                </tr>
            </thead>
        </table>
        <div id="searchAnulado-off" style="padding:5px">           
                   <b>Busquedas por:</b>
                   <select class="easyui-combobox fe-combobox-middle" data-options="valueField:'id'" id="tipoDocAnulado" name="tipoDocAnulado">
                            <option value="01">Factura</option>
                            <option value="03">Liquidación de Compra</option>
                            <option value="04">Nota Crédito</option>
                            <option value="05">Nota Débito</option>
                            <option value="06">Guia Remisión</option>
                            <option value="07">Comp. Retención</option>
                   </select>
                   Desde : <input type="text" id="fechaInicialAnulado" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser"></td>
                   Hasta : <input type="text" id="fechaFinalAnulado" class="easyui-datebox" data-options="formatter:$.fn.date.format,parser:$.fn.date.parser"></td>
                   <span>Nro. SRI </span>
                   <input class="easyui-validatebox textbox" type="text" size="17" id="nroSri"/></td>
                   <span>Nro. SAP </span>
                   <input class="easyui-validatebox textbox" type="text" size="20" id="nroReferencia"/></td>
                   <span>Cod. Interlocutor </span>
            	<input class="easyui-validatebox textbox" type="text" size="20" id="codInterlocutor"/>
                   <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search' " onclick="buscarAnulados()">Buscar</a>  
        </div>               

        <script type="text/javascript">
            var f_1 = $.fn.date.newDateFirstDay();
            var f_td = $.fn.date.newDate();
            var dgAutorizar = $("#dg-anulado-off");
            dgAutorizar.datagrid({
                queryParams: {
                    tipoDoc: $("#tipoDocAnulado").val(),
                    fechaIni: f_1,
                    fechaFinal: f_td,
                    nroSri: $("#searchAnulado-off #nroSri").val(),
                    nroReferencia: $("#searchAnulado-off #nroReferencia").val(),
                    codInterlocutor: $("#searchAnulado-off #codInterlocutor").val()
                }
            });
            $('#searchAnulado-off #fechaInicialAnulado').val(f_1);
            $('#searchAnulado-off #fechaFinalAnulado').val(f_td);
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
            
            $('#tipoDocAnulado').combobox({
                onSelect: function() {                 
                   buscarAnulados();
                  }
              });
              
            function buscarAnulados() {
                $("#dg-anulado-off").datagrid('reload', {
                    tipoDoc: $("#searchAnulado-off input[name=tipoDocAnulado]").val(),
                    fechaIni: $("#searchAnulado-off #fechaInicialAnulado").datebox('getValue'),
                    fechaFinal: $("#searchAnulado-off #fechaFinalAnulado").datebox('getValue'),
                    nroSri: $("#searchAnulado-off #nroSri").val(),
                    nroReferencia: $("#searchAnulado-off #nroReferencia").val(),
                    codInterlocutor: $("#searchAnulado-off #codInterlocutor").val()
                });
            }

            $('#ckbFechasAnulados').click(
                    function() {
                        var check = $('#ckbFechasAnulados').is(':checked');
                        if (check) {
                            $('#fechaInicialAnulado').datebox({disabled: false});
                            $('#fechaFinalAnulado').datebox({disabled: false});
                        } else {
                            $('#fechaInicialAnulado').datebox({disabled: true});
                            $('#fechaFinalAnulado').datebox({disabled: true});
                        }
                    }
            );            
        </script>
    </body>
</html>
