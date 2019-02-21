<%-- 
    Document   : administracionConfiguracion
    Created on : 21-may-2014, 14:31:26
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Administración de Configuraciones</title>
    </head>
    <body>
        <h3>ADMINISTRACI&Oacute;N DE CONFIGURACI&Oacute;N</h3>
        <hr>
        <div class="container-frm">
            <fieldset>
                <legend>Parametros del Servidor</legend>
                <table style="margin: 0 auto; width:500px" >

                    <tr>
                        <td></td>
                        <td><div style="text-align: center"><strong>CONTABILIZAR</strong></div></td>
                        <td><div style="text-align: center"><strong>PDF</strong></div></td>
                        <td><div style="text-align: center"><strong>EMAIL</strong></div></td>
                        <td><div style="text-align: center"><strong>NOTIFICAR</strong></div></td>
                    </tr>
                    <tr>
                        <td><div style="text-align: left"><strong>AUTORIZADO</strong></div></td>
                        <td><div style="text-align: center"><strong style="color:rgb(8, 185, 8);">SI</strong></div></td>
                        <td><div style="text-align: center">
                                <intput id="idpdfAutorizado" name="idpdfAutorizado" type="hidden" ></intput>
                                <input id="ck-pdfAutorizado" name="ck-pdfAutorizado" type="checkbox"></intput>
                            </div>
                        </td>
                        <td><div style="text-align: center">
                                <intput id="idemailAutorizado" name="idemailAutorizado" type="hidden" ></intput>
                                <input id="ck-emailAutorizado" name="ck-emailAutorizado" type="checkbox"></intput>
                            </div>
                        </td>
                        <td><div style="text-align: center">
                                <intput id="idnotificarAutorizado" name="idnotificarAutorizado" type="hidden" ></intput>
                                <input id="ck-notificarAutorizado" name="ck-notificarAutorizado" type="checkbox"></intput>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><div style="text-align: left"><strong>INCONSISTENTE</strong></div></td>
                        <td><div style="text-align: center"><strong style="color:rgb(240, 0, 0);">NO</strong></div></td>
                        <td><div style="text-align: center"><strong style="color:rgb(240, 0, 0);">NO</strong></div></td>
                        <td><div style="text-align: center"><strong style="color:rgb(240, 0, 0);">NO</strong></div></td>
                        <td><div style="text-align: center">
                                <intput id="idnotificarInconsistencia" name="idnotificarInconsistencia" type="hidden" ></intput>
                                <input id="ck-notificarInconsistencia" name="ck-notificarInconsistencia" type="checkbox"></intput>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><div style="text-align: left"><strong>CONTINGENCIA</strong></div></td>
                        <td><div style="text-align: center">
                                <intput id="idcontabilizarContingencia" name="idcontabilizarContingencia" type="hidden" ></intput>
                                <input id="ck-contabilizarContingencia" name="ck-contabilizarContingencia" type="checkbox"></intput>
                            </div>
                        </td>
                        <td><div style="text-align: center">
                                <intput id="idpdfContingencia" name="idpdfContingencia" type="hidden" ></intput>
                                <input id="ck-pdfContingencia" name="ck-pdfContingencia" type="checkbox"></intput>
                            </div></td>
                        <td><div style="text-align: center">
                                <intput id="idemailContingencia" name="idemailContingencia" type="hidden" ></intput>
                                <input id="ck-emailContingencia" name="ck-emailContingencia" type="checkbox"></intput>
                            </div>
                        </td>
                        <td><div style="text-align: center">
                                <intput id="idnotificarContingencia" name="idnotificarContingencia" type="hidden" ></intput>
                                <input id="ck-notificarContingencia" name="ck-notificarContingencia" type="checkbox"></intput>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><div style="text-align: left"><strong>RECHAZADO</strong></div></td>
                        <td><div style="text-align: center"><strong style="color:rgb(240, 0, 0);">NO</strong></div></td>
                        <td><div style="text-align: center"><strong style="color:rgb(240, 0, 0);">NO</strong></div></td>
                        <td><div style="text-align: center"><strong style="color:rgb(240, 0, 0);">NO</strong></div></td>
                        <td><div style="text-align: center">
                                <intput id="idnotificarRechazado" name="idnotificarRechazado" type="hidden" ></intput>
                                <input id="ck-notificarRechazado" name="ck-notificarRechazado" type="checkbox"></intput>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td><div style="text-align: left"><strong>POR AUTORIZAR</strong></div></td>
                        <td><div style="text-align: center">
                                <intput id="idcontabilizarPorAutorizar" name="idcontabilizarPorAutorizar" type="hidden" ></intput>
                                <input id="ck-contabilizarPorAutorizar" name="ck-contabilizarPorAutorizar" type="checkbox"></intput>
                            </div>
                        </td>
                        <td><div style="text-align: center"><strong style="color:rgb(240, 0, 0);">NO</strong></div></td>
                        <td><div style="text-align: center"><strong style="color:rgb(240, 0, 0);">NO</strong></div></td>
                        <td><div style="text-align: center">
                                <intput id="idnotificarPorAutorizar" name="idnotificarPorAutorizar" type="hidden" ></intput>
                                <input id="ck-notificarPorAutorizar" name="ck-notificarPorAutorizar" type="checkbox"></intput>
                            </div>
                        </td>
                    </tr>
                </table>
                <!--</div>-->
            </fieldset>
        </div>
        <div style="text-align:center;padding:15px">
            <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'" type="button" onclick="submitGuardarCambios()">Guardar</a>
        </div>
        <script>
            $(document).ready(function() {
                $.ajax({type: "POST",
                    url: '${pageContext.request.contextPath}/view/config/adminconfig/load',
                    dataType: 'json',
                    beforeSend: function() {
                        $.fn.loading.open("Cargando", "Cargando Parametros Administración de Configuración");
                    },
                    success: function(response) {
                        data = response.data
                        if (data.contabilizarContingencia === '1') {
                            $("#ck-contabilizarContingencia").prop("checked", true);
                        }
                        if (data.contabilizarPorAutorizar === '1') {
                            $("#ck-contabilizarPorAutorizar").prop("checked", true);
                        }
                        if (data.pdfAutorizado === '1') {
                            $("#ck-pdfAutorizado").prop("checked", true);
                        }
                        if (data.pdfContingencia === '1') {
                            $("#ck-pdfContingencia").prop("checked", true);
                        }
                        if (data.emailAutorizado === '1') {
                            $("#ck-emailAutorizado").prop("checked", true);
                        }
                        if (data.emailContingencia === '1') {
                            $("#ck-emailContingencia").prop("checked", true);
                        }
                        if (data.notificarAutorizado === '1') {
                            $("#ck-notificarAutorizado").prop("checked", true);
                        }
                        if (data.notificarInconsistencia === '1') {
                            $("#ck-notificarInconsistencia").prop("checked", true);
                        }
                        if (data.notificarContingencia === '1') {
                            $("#ck-notificarContingencia").prop("checked", true);
                        }
                        if (data.notificarRechazado === '1') {
                            $("#ck-notificarRechazado").prop("checked", true);
                        }
                        if (data.notificarPorAutorizar === '1') {
                            $("#ck-notificarPorAutorizar").prop("checked", true);
                        }
                        $("#idcontabilizarContingencia").val(data.idcontabilizarContingencia);
                        $("#idcontabilizarPorAutorizar").val(data.idcontabilizarPorAutorizar);
                        $("#idemailAutorizado").val(data.idemailAutorizado);
                        $("#idemailContingencia").val(data.idemailContingencia);
                        $("#idnotificarAutorizado").val(data.idnotificarAutorizado);
                        $("#idnotificarContingencia").val(data.idnotificarContingencia);
                        $("#idnotificarInconsistencia").val(data.idnotificarInconsistencia);
                        $("#idnotificarPorAutorizar").val(data.idnotificarPorAutorizar);
                        $("#idnotificarRechazado").val(data.idnotificarRechazado);
                        $("#idpdfAutorizado").val(data.idpdfAutorizado);
                        $("#idpdfContingencia").val(data.idpdfContingencia);
                        $.fn.loading.close();
                    }
                });
            });

            function submitGuardarCambios() {
                $.fn.loading.open("Actualizando", "Actualizando Parametros Administración Configuración");
                var contabilizarContingencia = $("#ck-contabilizarContingencia").is(':checked');
                var contabilizarPorAutorizar = $("#ck-contabilizarPorAutorizar").is(':checked');
                var pdfAutorizado = $("#ck-pdfAutorizado").is(':checked');
                var pdfContingencia = $("#ck-pdfContingencia").is(':checked');
                var emailAutorizado = $("#ck-emailAutorizado").is(':checked');
                var emailContingencia = $("#ck-emailContingencia").is(':checked');
                var notificarAutorizado = $("#ck-notificarAutorizado").is(':checked');
                var notificarInconsistencia = $("#ck-notificarInconsistencia").is(':checked');
                var notificarContingencia = $("#ck-notificarContingencia").is(':checked');
                var notificarRechazado = $("#ck-notificarRechazado").is(':checked');
                var notificarPorAutorizar = $("#ck-notificarPorAutorizar").is(':checked');

                var idcontabilizarContingencia = $("#idcontabilizarContingencia").val();
                var idcontabilizarPorAutorizar = $("#idcontabilizarPorAutorizar").val();
                var idpdfAutorizado = $("#idpdfAutorizado").val();
                var idpdfContingencia = $("#idpdfContingencia").val();
                var idemailAutorizado = $("#idemailAutorizado").val();
                var idemailContingencia = $("#idemailContingencia").val();
                var idnotificarAutorizado = $("#idnotificarAutorizado").val();
                var idnotificarInconsistencia = $("#idnotificarInconsistencia").val();
                var idnotificarContingencia = $("#idnotificarContingencia").val();
                var idnotificarRechazado = $("#idnotificarRechazado").val();
                var idnotificarPorAutorizar = $("#idnotificarPorAutorizar").val();

                var objeto = {contabilizarPorAutorizar: contabilizarPorAutorizar, contabilizarContingencia: contabilizarContingencia,
                    pdfAutorizado: pdfAutorizado, pdfContingencia: pdfContingencia, emailAutorizado: emailAutorizado,
                    emailContingencia: emailContingencia, notificarAutorizado: notificarAutorizado,
                    notificarInconsistencia: notificarInconsistencia, notificarContingencia: notificarContingencia,
                    notificarRechazado: notificarRechazado, notificarPorAutorizar: notificarPorAutorizar,
                    idcontabilizarPorAutorizar: idcontabilizarPorAutorizar, idcontabilizarContingencia: idcontabilizarContingencia,
                    idpdfAutorizado: idpdfAutorizado, idpdfContingencia: idpdfContingencia, idemailAutorizado: idemailAutorizado,
                    idemailContingencia: idemailContingencia, idnotificarAutorizado: idnotificarAutorizado,
                    idnotificarInconsistencia: idnotificarInconsistencia, idnotificarContingencia: idnotificarContingencia,
                    idnotificarRechazado: idnotificarRechazado, idnotificarPorAutorizar: idnotificarPorAutorizar};

                $.ajax({type: 'POST',
                    url: '${pageContext.request.contextPath}/view/config/adminconfig/edit',
                    dataType: 'json',
                    data: objeto,
                    success: function(data) {
                        $.fn.loading.close();
                        $.messager.alert('Actualización', data.message, 'info');
                    }
                });
            }
        </script>
    </body>
</html>
