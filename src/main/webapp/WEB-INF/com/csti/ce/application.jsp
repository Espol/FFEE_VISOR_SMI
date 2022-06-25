<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta http-equiv="Cache-control" content="no-cache">
        <meta http-equiv="Expires" content="-1">
    	
        <title>Comprobantes Electronicos - Monitor :: ${message}</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/bootstrap/easyui.css">
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/icon.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
        <!--<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.map"></script>-->
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/monitor.js"></script>

    </head>
    <body style="margin: 0px" onload="$.fn.setBase('${pageContext.request.contextPath}')">
        <div  class="easyui-layout" style="height: 563px">
            <div data-options="region:'north'" style="height: 50px;display:none;">
                <div>
                    <image id="imgUsuario" src='${pageContext.request.contextPath}/theme/icons/usuario.png' width="50px" height="40" />
                    <table align="right">
                        <tr>
                            <td><div id="id-nombre"></div></td>
                            <td><a id="linkSalir" href="javascript:void(0);" onclick="javascript:salir();">Salir</a></td>
                        </tr>
                    </table>
                </div>
                <script>
                
                    function salir() {
                        $.ajax({type: "POST",
                            url: '${pageContext.request.contextPath}/view/login/salir',
                            dataType: 'json',
                            success: function(data) {
                            	
//     	                  		location.reload();
// 								location.reload(true);
//     	                  		window.location.href = '${pageContext.request.contextPath}/';
    	                  		
                                try {
                                    if (typeof data.success != 'undefined') {
                                        if (data.success) {
                                            $.messager.alert('Acceso al Sistema', data.message, 'info', function() {
                                                //if(r){reload()
                                                	document.execCommand("ClearAuthenticationCache");
                                                location.href = $.fn.base;
                                                // }
                                            });
                                        }
                                    } else {
                                        throw "Ocurrió un error en el servidor.";
                                    }
                                }
                                catch (err) {
                                    $.messager.alert('Acceso al Sistema', 'Ocurrió un error inesperado.', 'info');
                                }
                            }
                        });
                    }
                </script>                 
            </div>
<!--            <div data-options="region:'south',split:true" style="height: 50px;" >
                <div style="text-align: center">&COPY; Derechos Reservados 2014 - <a target="_blank" href="http://csticorp.biz/">Chain Services TI - CSTI CORP</a></div>
                <div style="text-align: center">Est&aacute; prohibida la reproducci&oacute;n parcial y total, sin autorizaci&oacute;n escrita de su titular, de todos los contenidos de Facturaci&oacute;n Electr&oacute;nica.</div>
            </div>-->
            <!--<div data-options="region:'east',split:true" title="East" style="width:100px;">east</div>-->
            <div data-options="region:'west',split:true" title="Menú" style="width:250px;">
            	<div >	           		
                   <a id="linkSalir" style="margin-left:75%;" title="Salir" href="javascript:void(0);" onclick="javascript:salir();">
                   	<img id="imgUsuario" src='${pageContext.request.contextPath}/theme/icons/usuario.png' width="15px" height="15" /> Salir
                   </a>
               	</div>
                <ul id="nav-app"></ul>
            </div>
            <div data-options="region:'center'" >
                <div id="tt-app" class="easyui-tabs" data-options="fit:true,border:false,plain:true">
                    <div title="Bienvenido" data-options=""  style="padding:0px;border:none;"></div>
                    <!--<div title="Autorizado" data-options="href:'${pageContext.request.contextPath}/view/autorizado/view'" style="padding:0px;border:none;"></div>
                    <div title="Rechazado" data-options="href:'${pageContext.request.contextPath}/view/rechazado/view'" style="padding:0px;border:none;"></div>
                    <div title="Contingencia" data-options="href:'${pageContext.request.contextPath}/view/contingencia/view'" style="padding:0px;border:none;"></div>
                    <div title="PorAutorizar" data-options="href:'${pageContext.request.contextPath}/view/porautorizar/view'" style="padding:0px;border:none;"></div>
                    <div title="Inconsistente" data-options="href:'${pageContext.request.contextPath}/view/inconsistente/view'" style="padding:0px;border:none;"></div>
                    <div title="Factura" data-options="href:'${pageContext.request.contextPath}/view/config/factura/view'" style="padding:0px;border:none;"></div>
                    <div title="Nota Credito" data-options="href:'${pageContext.request.contextPath}/view/config/notacredito/view'" style="padding:0px;border:none;"></div>
                    <div title="Nota Debito" data-options="href:'${pageContext.request.contextPath}/view/config/notadebito/view'" style="padding:0px;border:none;"></div>
                    <div title="Guia Remision" data-options="href:'${pageContext.request.contextPath}/view/config/guiaremision/view'" style="padding:0px;border:none;"></div>
                    <div title="Retencion" data-options="href:'${pageContext.request.contextPath}/view/config/retencion/view'" style="padding:0px;border:none;"></div>
                    <div title="WS-Recepci?n" data-options="href:'${pageContext.request.contextPath}/view/config/wsrecepcion/view'" style="padding:10px "></div>
                    <div title="WS-Autorizaci?n" data-options="href:'${pageContext.request.contextPath}/view/config/wsautorizacion/view'" style="padding:10px "></div>
                    <div title="E-mail" data-options="href:'${pageContext.request.contextPath}/view/config/emailnotificacion/view'" style="padding:10px "></div>
                    <div title="Firma" data-options="href:'${pageContext.request.contextPath}/view/config/firmaelectronica/view'" style="padding:10px "></div>
                    <div title="Clave" data-options="href:'${pageContext.request.contextPath}/view/config/clave/view'" style="padding:10px "></div>
                    <div title="Configuraci?n" data-options="href:'${pageContext.request.contextPath}/view/config/adminconfig/view'" style="padding:10px "></div>-->
                    <!--<div title="Opcion" data-options="href:'${pageContext.request.contextPath}/view/gui/view'" style="padding:10px "></div>
                    <div title="Usuario" data-options="href:'${pageContext.request.contextPath}/view/usuario/view'" style="padding:10px "></div>
                    <div title="Sistema" data-options="href:'${pageContext.request.contextPath}/view/sistema/view'" style="padding:10px "></div>
                    <div title="Rol" data-options="href:'${pageContext.request.contextPath}/view/rol/view'" style="padding:10px "></div>
                    <div title="Modulo" data-options="href:'${pageContext.request.contextPath}/view/modulo/view'" style="padding:10px "></div>-->
                </div>
            </div>
        </div>
        <script type="text/javascript">
            $(document).ready(function() {
                $.fn.loading.open('Facturaci&oacute;n El&eacute;ctronica', 'Cargando p&aacute;gina');
                $('#nav-app').tree({
                    beforeSend: function() {

                    },
                    url: '${pageContext.request.contextPath}/view/menu/loadMenu',
                    success: function() {
                        $.fn.loading.close();
                    },
                    loadFilter: function(data) {
                        $.fn.loading.close();
                        if (data.children) {
                            $.fn.nav.setChildren(data.children);
                            return data.children;
                        } else {
                            $.fn.nav.setChildren(data);
                            return data;
                        }

                    },
                    onClick: function(node) {
                        $.fn.nav.openNav(node);
                    }
                });
            });

        </script>
        <style type="text/css">
            .container-frm{width: 95%;margin: auto;}
            .container-frm fieldset {
                background-color: #F7F7F7;
                border-style: solid;
                border-width: 1px;
                border-radius: 4px;
                color: #4A4C50;border-color: #DBDBDB;}
            .container-frm table td strong{ color: #585858;text-shadow:2px 2px 0px #fff;}
            .container-frm table td input[type=text],
            .container-frm table td input[type=password],
            form input[type=text],form input[type=password]
            {
                padding: 5px; border-radius: 4px;border-color: #95B8E7;
            }
            fieldset legend{
                font-weight: bold;
                color: #0088CC;
                text-transform: uppercase;
                text-shadow: 1px 1px 0px #fff;
            }
            .container-frm input{display: inline-block;}
            .container-frm .input-large,.input-large{width: 500px}
            .container-frm .input-medium,.input-medium{width: 250px}
            .container-frm .input-small,.input-small{width: 120px}
            label{font-weight: bold;display: inline-block;text-align: right;padding:0px 5px 0px 0px }
            label.medium{
                width: 150px;
            }
            label.small{
                width: 90px;
            }
        </style>
    </body> 
</html>
