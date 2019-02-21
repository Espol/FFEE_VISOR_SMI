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
        <table id="dg-rechazados" class="easyui-datagrid" style="height:475px" pagination="true" 
               data-options="border:false, 
               url:'${pageContext.request.contextPath}/view/rechazado/comprobante/load',
               striped:true,
               idField:'idDoc',
               rownumbers: true,
              
               pageSize:20" toolbar="#searchRechazados">
            <thead data-options="frozen:true">
                <tr>
					<th rowspan="2" data-options="field:'fechaRegistro',width:100,align:'center'"><strong>Fe. Documento</strong></th>
                    <th rowspan="2" data-options="field:'nroSri',width:140,align:'center'"  ><strong>Nro. SRI</strong></th>
                    <th rowspan="2" data-options="field:'docReferencia',width:140,align:'center'"  ><strong>Doc. Referencia</strong></th>
                    <!--<th rowspan="2" data-options="field:'tipoEmision',width:110,align:'center'"><strong>Tipo Emisi&oacute;n</strong></th>-->
                    <th rowspan="2" data-options="field:'interlocutor',width:100,align:'center'"><strong>C&oacute;digo</strong></th>
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
        <div id="searchRechazados" style="padding:10px">
            <b>Busquedas por:</b>
            <select class="easyui-combobox fe-combobox-middle" id="tipoDocRechazado" data-options="valueField:'id'" name="tipoDocRechazado">
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
            <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search' " onclick="buscarRechazados()">Buscar</a>
        </div>
        <script tabindex="text/script">
            var f_1_r = $.fn.date.newDateFirstDay();
            var f_td_r = $.fn.date.newDate();
            var dgAutorizar = $("#dg-rechazados");
            dgAutorizar.datagrid({
                queryParams: {
                    tipoDoc: $("#tipoDocRechazado").val(),
                    fechaIni: f_1_r,
                    fechaFinal: f_td_r,
                    nroSri: $("#searchRechazados #nroSri").val(),
                    nroReferencia: $("#searchRechazados #nroReferencia").val(),
                    codInterlocutor: $("#searchRechazados #codInterlocutor").val()
                }
            });
            
            $('#tipoDocRechazado').combobox({
                  onSelect: function() {                 
                   buscarRechazados();
                  }
             });
             
            $('#searchRechazados #fechaInicial').val(f_1_r);
            $('#searchRechazados #fechaFinal').val(f_td_r);
            function buscarRechazados() {
                $("#dg-rechazados").datagrid('reload', {
                    tipoDoc: $("#searchRechazados input[name=tipoDocRechazado]").val(),
                    fechaIni: $("#searchRechazados #fechaInicial").datebox('getValue'),
                    fechaFinal: $("#searchRechazados #fechaFinal").datebox('getValue'),
                    nroSri: $("#searchRechazados #nroSri").val(),
                    nroReferencia: $("#searchRechazados #nroReferencia").val(),
                    codInterlocutor: $("#searchRechazados #codInterlocutor").val()
                });
            }
        </script>
    </body>
</html>
