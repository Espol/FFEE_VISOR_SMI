/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


(function($) {
    $.fn.extend({
        setBase: function(txt) {
            $.fn.base = 'http://' + location.host + "" + txt;
        },
        base: 'http://' + location.host + 'monitor/',
	CLAVES:{
            claves: function (val, row, id){
                var strPassword = '*****';
                return strPassword;
            }
        },
        format: {
            activo: function(val) {
                var str = $("<strong/>");
                if (val == 1) {
                    str.text("SI");
                    str.css({"color": "green"});
                } else {
                    str.text("NO");
                    str.css({"color": "red"});
                }
                return $.fn.convertString(str);
            }
        },
        PDF: {
            format: function(val, row, id) {
                $.fn.PDF.addData(id, row);
                var id = row.id;
                var btn = "";
                if (row.pdf == 1) {
                    var attr = 'onClick="$.fn.PDF.show(\'' + id + '\')"';
                    btn = $.fn.linkButton.success('PDF', attr);
                } else {
                    var attr = 'onClick="$.fn.PDF.generar(\'' + id + '\')"';
                    btn = $.fn.linkButton.genPDF('GEN', attr);
                }
                var dialogXML = '<div>' + btn + '</div>';
                return dialogXML.toString();
            },
            show: function(id) {
                var dialog = $('<div id="pdf-' + id + '" data-options="iconCls:\'icon-save\',modal:true"></div>');
                dialog.addClass('easyui-dialog');
                dialog.attr("title", "Vizualización de PDF");
                dialog.attr("style", "width:875px;height:500px;padding:0px");
                dialog.append('<iframe src="' + $.fn.base + '/view/resources/' + id + '/pdf" frameborder="0" marginheight="0" scrolling ="auto" width="860" height="460"></iframe>');
                dialog.dialog();
                return dialog.dialog('open');
            },
            data: [],
            addData: function(id, row) {
                var object = {"id": id, value: row};
                this.data.push(object);
            },
            generar: function(id) {
                $.messager.confirm('Generar PDF', 'Desea generar el el archivo PDF?', function(r) {
                    if (r) {
                        $.ajax({
                            url: $.fn.base + '/view/resources/' + id + '/pdf/generar',
                            type: "POST",
                            data: {
                                email: 0
                            },
                            beforeSend: function(xhr) {
                                $.fn.loading.open("Generar PDF", "Generando PDF");
                            },
                            success: function(response) {
                                $.fn.loading.close();
                                var fun = function() {
                                };
                                if (response.success) {
                                    fun = function() {
                                        $.fn.loading.close();
                                        $.fn.PDF.show(response.data.identificador);
                                    };
                                }
                                $.messager.alert('Success', response.message, 'info', fun);
                            },
                            statusCode: {
                                404: function() {
                                    alert("page not found");
                                }
                            },
                            error: function(response) {
                                var div = $("#d45v78" + object.id + ' .panel-body');
                                div.empty();
                                div.show();
                                div.append(response);
                            }
                        });
                    }
                });
            }
        },
        EXCEL: {
            setData: function(data) {
                $.fn.EXCEL.path = data.replace(/\\/g, '/');
            },
            getData: function() {
                return $.fn.EXCEL.path;
            },
            show: function() {
                var dialog = $('<div id="xml-' + $.fn.EXCEL.path + '" data-options="iconCls:\'icon-save\',modal:true"></div>');
                dialog.addClass('easyui-dialog');
                dialog.attr("title", "Vizualización de EXCEL");
                dialog.attr("style", "width:875px;height:500px;padding:0px");
                dialog.append('<iframe src="' + $.fn.base + '/view/autorizado/comprobante/excel?path=' + $.fn.EXCEL.path + ' " frameborder="0" marginheight="0" scrolling ="auto" width="860" height="460"></iframe>');
                dialog.dialog();
            },
            path: ''
        },
        XML: {
            format: function(val, row, id) {
                $.fn.PDF.addData(id, row);
                var id = row.id;
                var btn = "";
//                if (row.xml == 1) {
                var attr = 'onClick="$.fn.XML.show(\'' + id + '\')"';
                btn = $.fn.linkButton.success('XML', attr);
//                } else {
//                    btn = $.fn.linkButton.error('XML');
//                }
                var dialogXML = '<div>' + btn + '</div>';
                return dialogXML.toString();
            },
            show: function(id) {
                var dialog = $('<div id="xml-' + id + '" data-options="iconCls:\'icon-save\',modal:true"></div>');
                dialog.addClass('easyui-dialog');
                dialog.attr("title", "Vizualización de XML");
                dialog.attr("style", "width:875px;height:500px;padding:0px");
                dialog.append('<iframe src="' + $.fn.base + '/view/resources/' + id + '/xml" frameborder="0" marginheight="0" scrolling ="auto" width="860" height="460"></iframe>');
                dialog.dialog();
                return dialog.dialog('open');
            },
            data: [],
            addData: function(id, row) {
                var object = {"id": id, value: row};
                this.data.push(object);
            }
        },
        LOG: {
            format: function(val, row, id) {
                $.fn.PDF.addData(id, row);
                var id = row.id;
                var btn = "";
//                if (row.log == 1) {
                var attr = 'onClick="$.fn.LOG.show(\'' + id + '\')"';
                btn = $.fn.linkButton.success('LOG', attr);
//                } else {
//                    btn = $.fn.linkButton.error('LOG');
//                }
                var dialogXML = '<div>' + btn + '</div>';
                return dialogXML.toString();
            },
            show: function(id) {
                var dialog = $('<div id="log-' + id + '" data-options="iconCls:\'icon-save\',closed: true,cache: false, modal: true"></div>');
                dialog.addClass('easyui-dialog');
                dialog.attr("title", "Vizualización de LOG");
                dialog.attr("style", "width:875px;height:500px;padding:0px");
                dialog.append('<iframe src="' + $.fn.base + '/view/resources/' + id + '/log" frameborder="0" marginheight="0" scrolling ="auto" width="860" height="460"></iframe>');
                dialog.dialog();
                return dialog.dialog('open');
            },
            data: [],
            addData: function(id, row) {
                var object = {"id": id, value: row};
                this.data.push(object);
            }
        },
        MAIL: {
            format: function(val, row, id) {
                $.fn.PDF.addData(id, row);
                var id = row.id;
                var btn = "";
//                if (row.log == 1) {
                var attr = 'onClick="$.fn.MAIL.show(\'' + id + '\')"';
                btn = $.fn.linkButton.sendEmail('E-Mail', attr);
//                } else {
//                    btn = $.fn.linkButton.error('LOG');
//                }
                var dialogXML = '<div>' + btn + '</div>';
                return dialogXML.toString();
            },
            show: function(id) {
                $.fn.loading.open("Cargando Vista", "Preparando");
                var dialog = $('<div id="dlg-mail" class="easyui-dialog" style="padding:5px">' +
                        '<div id="dlg-mail-content-text"><div></div></div>\n\
                        </div>');
                dialog.dialog({
                    width: 500,
                    height: 300,
                    closed: true,
                    cache: false,
                    title: 'Enviar e-mail: Proceso de Autorizaci&oacute;n.',
                    modal: true,
                    buttons: [{
                            text: 'Enviar e-mail',
                            iconCls: 'icon-sendEmail',
                            handler: function() {
                                $.fn.MAIL.sendEmail(id, '#dlg-mail #dlg-mail-content-text');
                            }
                        }, {
                            text: 'Cancelar',
                            iconCls: 'icon-no',
                            handler: function() {
                                dialog.dialog('close');
                            }
                        }]
                });
                var content = dialog.find('#dlg-mail-content-text');
                var div = $('<div><strong>Email de Destino: </strong><br>\n\
                <textarea id="e-mail-' + id + '"   name="e-mail" title="E-mail de destino - Cliente" style="margin: 5px; width: 455px; height: 50px;border-radius:5px;"/></div>');
                div.append('<strong>Mensaje Adicional</strong><br>\n\
                <textarea id="e-mail-msg' + id + '" name="e-mail-msg" title="E-mail de destino - Cliente" style="margin: 5px; width: 455px; height: 90px;border-radius:5px;"></textarea>');
                content.append(div);
//                var btn = $('<a id="btn" href="#"></a>');
//                btn.linkbutton({
//                    iconCls: 'icon-sendEmail'
//                });

//                var contBtn = $('<div style="text-align: center;"/>');
//                contBtn.append(btn);
//                content.append(contBtn);
                $.fn.MAIL.getEmail(id, '#dlg-mail #dlg-mail-content-text', dialog);
//                return dialog.dialog('open');
            },
            data: [],
            addData: function(id, row) {
                var object = {"id": id, value: row};
                this.data.push(object);
            },
            getEmail: function(id, idDom, dom) {
                $.ajax({
                    url: $.fn.base + '/view/resources/' + id + '/emails',
                    type: "POST",
                    beforeSend: function(xhr) {
                        $.fn.loading.open("Obteniendo Datos", "Cargando Email");
                    },
                    success: function(response) {
                        if (response.success) {
                            var input = $(idDom + ' #e-mail-' + id);
                            input.val(response.data.emails);
                            $.messager.alert('Success', response.message, 'info', function() {
                                $.fn.loading.close();
                                dom.dialog('open');
                            });
                        }
                    },
                    statusCode: {
                        404: function() {
                            alert("page not found");
                        }
                    },
                    error: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append(response);
                    }
                });
            },
            sendEmail: function(id, idDom) {
                $.messager.confirm('Enviar e-mail', 'Desea enviar el Email?', function(r) {
                    if (r) {
                        $.ajax({
                            url: $.fn.base + '/view/resources/' + id + '/emails/send',
                            type: "POST",
                            data: {
                                emails: $(idDom + ' #e-mail-' + id).val(),
                                mensaje: $(idDom + ' #e-mail-msg' + id).val()
                            },
                            beforeSend: function( ) {
                                $.fn.loading.open("Email", "Enviando Email");
                            },
                            success: function(response) {
                                $.fn.loading.close();
                                var fun = function() {
                                };
                                if (response.success) {
                                    fun = function() {
                                        $('#dlg-mail').dialog('close');
                                    };
                                }
                                $.messager.alert('Success', response.message, 'info', fun);
                            },
                            statusCode: {
                                404: function() {
                                    alert("page not found");
                                }
                            },
                            error: function(response) {
                                $.fn.loading.close();
                                $.messager.alert('Error', "Ocurrió un error, contáctese con el administrador", 'info', function() {
                                });
                            }
                        });
                    }
                });
            }
        },
        linkButton: {
            success: function(text, attr) {
                var btn = '<a ' + attr + ' href="#" class="easyui-linkbutton l-btn l-btn-small" data-options="iconCls:\'icon-save\'"><span class="l-btn-left l-btn-icon-left"><span class="l-btn-text">' + text + '</span><span class="l-btn-icon icon-ok">&nbsp;</span></span></a>';
                return btn;
            },
            error: function(text) {
                var btn = '<a href="#" class="easyui-linkbutton l-btn l-btn-small l-btn-disabled" \n\
                    data-options="iconCls:\'icon-save\'"><span class="l-btn-left l-btn-icon-left">\n\
                    <span class="l-btn-text">' + text + '</span>\n\
                    <span class="l-btn-icon icon-no">&nbsp;</span></span></a>';

                return btn;
            },
            sendEmail: function(text, attr) {
                var btn = '<a href="#" ' + attr + ' class="easyui-linkbutton l-btn l-btn-small" \n\
                    data-options="iconCls:\'icon-sendEmail\'"><span class="l-btn-left l-btn-icon-left">\n\
                    <span class="l-btn-text">' + text + '</span>\n\
                    <span class="l-btn-icon icon-sendEmail">&nbsp;</span></span></a>';

                return btn;
            },
            genPDF: function(text, attr) {
                var btn = '<a href="#" ' + attr + ' class="easyui-linkbutton l-btn l-btn-small" \n\
                    data-options="iconCls:\'icon-pdfFile\'"><span class="l-btn-left l-btn-icon-left">\n\
                    <span class="l-btn-text">' + text + '</span>\n\
                    <span class="l-btn-icon icon-pdfFile">&nbsp;</span></span></a>';

                return btn;
            }
        },
        date: {
            newDateFirstDay: function() {
                var fecha = new Date();
                var y = fecha.getFullYear();
                var m = fecha.getMonth();
                var d = 1;
                return this.format(new Date(y, m, d));
            },
            newDate: function(arg) {
                var fecha = new Date();
                if (arg != null) {
                    var y = fecha.getFullYear();
                    var m = fecha.getMonth();
                    var d = fecha.getDate() - arg;
                    return this.format(new Date(y, m, d));
                } else {
                    return this.format(fecha);
                }
            },
            format: function(date) {
                var y = date.getFullYear();
                var m = date.getMonth() + 1;
                var d = date.getDate();
                return (d < 10 ? ('0' + d) : d) + '/' + (m < 10 ? ('0' + m) : m) + '/' + y;
            },
            parser: function(s) {
                if (!s)
                    return new Date();
                var ss = (s.split('/'));
                var d = parseInt(ss[0], 10);
                var m = parseInt(ss[1], 10);
                var y = parseInt(ss[2], 10);
                if (!isNaN(y) && !isNaN(m) && !isNaN(d)) {
                    return new Date(y, m - 1, d);
                } else {
                    return new Date();
                }
            }
        },cancelar_noti_correo:{
        	clearGrid: function() {
            },
            openDialog: function(seletorDOM) {
                $.fn.cancelar_noti_correo.count = 0;
                var ss = [];
                for (var i = 0; i < $.fn.cancelar_noti_correo.data.length; i++) {
                    var row = $.fn.cancelar_noti_correo.data[i];
                    var texto = '' + (i + 1) + ')-';
                    texto += '' + row.nroSri + '***';
                    texto += '' + row.docReferencia + '****';
                    texto += '' + row.fechaEmision + '';
                    var elementDOM = '<div id="d45v78' + row.id + '">\n\
                    <div class="panel" style="display: block; width: 566px;margin-bottom: 5px;">\n\
                    <div class="panel-header" style="width: 554px;">\n\
                    <div class="panel-title">' + texto + '</div>\n\
                    <div class="panel-tool">\n\
                    </div>\n\
                    </div>\n\
                    <div style="display:none" title="" class="panel-body">\n\
                    </div>\n\
                    </div>\n\
                    </div>';
                    ss.push(elementDOM);
                }
                var div = $(seletorDOM + ' #dlg-content-text');
                div.empty();
                if ($.fn.cancelar_noti_correo.data.length > 0) {
                    div.append(ss.join(''));
                } else {
                    div.append("No se ha seleccionado documentos para Cancelar la Notificacion.");
                }

                $(seletorDOM).dialog('open');
            },
            closeDialog: function(seletorDOM) {
                $.fn.anular_offline.data = [];
                $(seletorDOM).dialog('close');
            },
            sendObject: function(object) {
                $.ajax({
                    url: $.fn.base + "/view/resources/correo/notificacion",
                    type: "POST",
                    data: {
                    	identificacion: object.id,
                        nroSri: object.nroSri,
                        tipoDocumento: object.tipoDoc
                    },
                    beforeSend: function(xhr) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append("Enviando documento...");
                        $.fn.loading.open("Documentos Rechazados", "Cancelando Notificacion de Documentos Rechazados:<br/> " + object.nroSri);
                    },
                    success: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        var html = '';
                        html += '<strong><span style="color:red">Estado:</strong> <strong><span style="color:blue">' + response.message + '</span></strong><br/>';
                        
                        div.html(html);
                        $.fn.loading.close();
                        $.fn.cancelar_noti_correo.sendComprobantes();
//                        $.fn.anular.gridPrincipal.datagrid('reload');
                        $('#dg-autorizado-off').datagrid('reload');
                    },
                    statusCode: {
                        404: function() {
                            alert("page not found");
                            $.fn.loading.close();
                            $.fn.cancelar_noti_correo.clearGrid();
                        }

                    },
                    error: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append(response);
                        $.fn.loading.close();
                        $.fn.cancelar_noti_correo.clearGrid();
                    }
                });
            },
            sendComprobantes: function() {
                if ($.fn.cancelar_noti_correo.data.length == 0 && $.fn.cancelar_noti_correo.count == 0) {
                    $.messager.alert('Información', "No har documantos rechazado para notificar", 'info', function() {
                    });
                } else {
                    $.fn.loading.open("Cancelar Norificacion", "Cancelando Notificacion de Comprobantes rechazados");
                }
                if ($.fn.cancelar_noti_correo.count < $.fn.cancelar_noti_correo.data.length) {
                    $.fn.cancelar_noti_correo.sendObject($.fn.cancelar_noti_correo.data[$.fn.cancelar_noti_correo.count]);
                    $.fn.cancelar_noti_correo.count++;
                } else {
                    $.fn.cancelar_noti_correo.data = [];
                    $.fn.loading.close();
                    $.fn.cancelar_noti_correo.count = 0;
                    $.fn.cancelar_noti_correo.clearGrid();
                }
            },
            setData: function(data) {
                $.fn.cancelar_noti_correo.data = [];
                $.fn.cancelar_noti_correo.data = data;
            },
            count: 0,
            data: []
        },
         anular_offline: {
            clearGrid: function() {
            },
            openDialog: function(seletorDOM) {
                $.fn.anular_offline.count = 0;
                var ss = [];
                for (var i = 0; i < $.fn.anular_offline.data.length; i++) {
                    var row = $.fn.anular_offline.data[i];
                    var texto = '' + (i + 1) + ')-';
                    texto += '' + row.nroSri + '***';
                    texto += '' + row.docReferencia + '****';
                    texto += '' + row.fechaEmision + '';
                    var elementDOM = '<div id="d45v78' + row.id + '">\n\
                    <div class="panel" style="display: block; width: 566px;margin-bottom: 5px;">\n\
                    <div class="panel-header" style="width: 554px;">\n\
                    <div class="panel-title">' + texto + '</div>\n\
                    <div class="panel-tool">\n\
                    </div>\n\
                    </div>\n\
                    <div style="display:none" title="" class="panel-body">\n\
                    </div>\n\
                    </div>\n\
                    </div>';
                    ss.push(elementDOM);
                }
                var div = $(seletorDOM + ' #dlg-content-text');
                div.empty();
                if ($.fn.anular_offline.data.length > 0) {
                    div.append(ss.join(''));
                } else {
                    div.append("No se ha seleccionado documentos para anular.");
                }

                $(seletorDOM).dialog('open');
            },
            closeDialog: function(seletorDOM) {
                $.fn.anular_offline.data = [];
                $(seletorDOM).dialog('close');
            },
            sendObject: function(object) {
                $.ajax({
                    url: $.fn.base + "/view/anulado/autorizar/comprobante",
                    type: "POST",
                    data: {
                        identificador: object.nroSri,
                        tipoDocumento: object.tipoDoc
                    },
                    beforeSend: function(xhr) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append("Enviando documento...");
                        $.fn.loading.open("Anulando", "Anulando Comprobante:<br/> " + object.nroSri);
                    },
                    success: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        var html = '';
                        html += '<strong><span style="color:red">Estado:</strong> <strong><span style="color:blue">' + response + '</span></strong><br/>';
                        
                        div.html(html);
                        $.fn.loading.close();
                        $.fn.anular_offline.sendComprobantes();
//                        $.fn.anular.gridPrincipal.datagrid('reload');
                        $('#dg-autorizado-off').datagrid('reload');
                    },
                    statusCode: {
                        404: function() {
                            alert("page not found");
                            $.fn.loading.close();
                            $.fn.anular_offline.clearGrid();
                        }

                    },
                    error: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append(response);
                        $.fn.loading.close();
                        $.fn.anular_offline.clearGrid();
                    }
                });
            },
            sendComprobantes: function() {
                if ($.fn.anular_offline.data.length == 0 && $.fn.anular_offline.count == 0) {
                    $.messager.alert('Información', "No hay documentos seleccionados para anular", 'info', function() {
                    });
                } else {
                    $.fn.loading.open("Anulando", "Anulando Comprobantes Offline");
                }
                if ($.fn.anular_offline.count < $.fn.anular_offline.data.length) {
                    $.fn.anular_offline.sendObject($.fn.anular_offline.data[$.fn.anular_offline.count]);
                    $.fn.anular_offline.count++;
                } else {
                    $.fn.anular_offline.data = [];
                    $.fn.loading.close();
                    $.fn.anular_offline.count = 0;
                    $.fn.anular_offline.clearGrid();
                }
            },
            setData: function(data) {
                $.fn.anular_offline.data = [];
                $.fn.anular_offline.data = data;
            },
            count: 0,
            data: []
        },
         anular: {
            clearGrid: function() {
            },
            openDialog: function(seletorDOM) {
                $.fn.anular.count = 0;
                var ss = [];
                for (var i = 0; i < $.fn.anular.data.length; i++) {
                    var row = $.fn.anular.data[i];
                    var texto = '' + (i + 1) + ')-';
                    texto += '' + row.nroSri + '***';
                    texto += '' + row.docReferencia + '****';
                    texto += '' + row.fechaEmision + '';
                    var elementDOM = '<div id="d45v78' + row.id + '">\n\
                    <div class="panel" style="display: block; width: 566px;margin-bottom: 5px;">\n\
                    <div class="panel-header" style="width: 554px;">\n\
                    <div class="panel-title">' + texto + '</div>\n\
                    <div class="panel-tool">\n\
                    </div>\n\
                    </div>\n\
                    <div style="display:none" title="" class="panel-body">\n\
                    </div>\n\
                    </div>\n\
                    </div>';
                    ss.push(elementDOM);
                }
                var div = $(seletorDOM + ' #dlg-content-text');
                div.empty();
                if ($.fn.anular.data.length > 0) {
                    div.append(ss.join(''));
                } else {
                    div.append("No se ha seleccionado documentos para anular.");
                }

                $(seletorDOM).dialog('open');
            },
            closeDialog: function(seletorDOM) {
                $.fn.anular.data = [];
                $(seletorDOM).dialog('close');
            },
            sendObject: function(object) {
                $.ajax({
                    url: $.fn.base + "/view/anulado/autorizar/comprobante",
                    type: "POST",
                    data: {
                        identificador: object.nroSri,
                        tipoDocumento: object.tipoDoc
                    },
                    beforeSend: function(xhr) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append("Enviando documento...");
                        $.fn.loading.open("Anulando", "Anulando Comprobante:<br/> " + object.nroSri);
                    },
                    success: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        var html = '';
                        html += '<strong><span style="color:red">Estado:</strong> <strong><span style="color:blue">' + response + '</span></strong><br/>';
                        
                        div.html(html);
                        $.fn.loading.close();
                        $.fn.anular.sendComprobantes();
                        $('#dg-autorizado').datagrid('reload');
                    },
                    statusCode: {
                        404: function() {
                            alert("page not found");
                            $.fn.loading.close();
                            $.fn.anular.clearGrid();
                        }

                    },
                    error: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append(response);
                        $.fn.loading.close();
                        $.fn.anular.clearGrid();
                    }
                });
            },
            sendComprobantes: function() {
                if ($.fn.anular.data.length == 0 && $.fn.anular.count == 0) {
                    $.messager.alert('Información', "No hay documentos seleccionados para anular", 'info', function() {
                    });
                } else {
                    $.fn.loading.open("Anulando", "Anulando Comprobantes");
                }
                if ($.fn.anular.count < $.fn.anular.data.length) {
                    $.fn.anular.sendObject($.fn.anular.data[$.fn.anular.count]);
                    $.fn.anular.count++;
                } else {
                    $.fn.anular.data = [];
                    $.fn.loading.close();
                    $.fn.anular.count = 0;
                    $.fn.anular.clearGrid();
                }
            },
            setData: function(data) {
                $.fn.anular.data = [];
                $.fn.anular.data = data;
            },
            count: 0,
            data: []
        },
        autorizar: {
            clearGrid: function() {
            },
            openDialog: function(seletorDOM) {
                $.fn.autorizar.count = 0;
                var ss = [];
                for (var i = 0; i < $.fn.autorizar.data.length; i++) {
                    var row = $.fn.autorizar.data[i];
                    var texto = '' + (i + 1) + ')-';
                    texto += '' + row.nroSri + '***';
                    texto += '' + row.docReferencia + '****';
                    texto += '' + row.fechaEmision + '';
                    var elementDOM = '<div id="d45v78' + row.id + '">\n\
                    <div class="panel" style="display: block; width: 566px;margin-bottom: 5px;">\n\
                    <div class="panel-header" style="width: 554px;">\n\
                    <div class="panel-title">' + texto + '</div>\n\
                    <div class="panel-tool">\n\
                    </div>\n\
                    </div>\n\
                    <div style="display:none" title="" class="panel-body">\n\
                    </div>\n\
                    </div>\n\
                    </div>';
                    ss.push(elementDOM);
                }
//                 ss.join('<br/>');
                var div = $(seletorDOM + ' #dlg-content-text');
                div.empty();
                if ($.fn.autorizar.data.length > 0) {
                    div.append(ss.join(''));
                } else {
                    div.append("No se ha seleccionado Documentos.");
                }

                $(seletorDOM).dialog('open');
            },
            closeDialog: function(seletorDOM) {
                $.fn.autorizar.data = [];
                $(seletorDOM).dialog('close');
            },
            sendObject: function(object) {
                $.ajax({
                    url: $.fn.base + $.fn.autorizar.url,
                    //url: $.fn.base + "/view/contingencia/autorizar/comprobante",
                    type: "POST",
                    data: {
                        nroSri: object.nroSri,
                        tipoDoc: object.tipoDoc
                    },
                    beforeSend: function(xhr) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append("Enviando documento...");
                        $.fn.loading.open("Enviando", "Enviando Comprobante:<br/> " + object.nroSri);
                    },
                    success: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        var html = '';
                        html += '<strong>Estado: </strong> <strong>' + response.message + '</strong><br/>';
                        div.html(html);
                        $.fn.loading.close();
                        $.fn.autorizar.sendComprobantes();
//                        $('#dg-porAutorizar').datagrid('reload'); 
//                        $('#dg-contingencia').datagrid('reload');   
                        $('#dg-rechazados-off').datagrid('reload');
                    },
                    statusCode: {
                        404: function() {
                            alert("page not found");
                            $.fn.loading.close();
                            $.fn.autorizar.clearGrid();
                        }

                    },
                    error: function(response) {
                        var div = $("#d45v78" + object.id + ' .panel-body');
                        div.empty();
                        div.show();
                        div.append(response);
                        $.fn.loading.close();
                        $.fn.autorizar.clearGrid();
                    }
                });
                $.fn.autorizar.data = [];
            },
            sendComprobantes: function() {

//                for (var i = 0; i < $.fn.autorizar.data.length; i++) {
                if ($.fn.autorizar.data.length === 0 && $.fn.autorizar.count === 0) {
                    $.messager.alert('Información', "No hay documentos seleccionados para autorizar", 'info', function() {
                    });
                }else{
                    $.fn.loading.open("Enviando", "Enviando Comprobantes");
                }
                if ($.fn.autorizar.count < $.fn.autorizar.data.length) {
                    $.fn.autorizar.sendObject($.fn.autorizar.data[$.fn.autorizar.count]);
                    $.fn.autorizar.count++;
                } else {
                    $.fn.autorizar.data = [];
                    $.fn.loading.close();
                    $.fn.autorizar.count = 0;
                    $.fn.autorizar.clearGrid();
                }
            },
            setData: function(data) {
                $.fn.autorizar.data = [];
                $.fn.autorizar.data = data;
            },
            setURL: function(data){
                $.fn.autorizar.url = '';
                $.fn.autorizar.url = data;
            },
            count: 0,
            url: '',
            data: []
        },
        htmlToString: function(element) {
            return $('<div />').append(element).remove().html();
        },
        nav: {
            children: [],
            setChildren: function(children) {
                var data = {'children': children};
                if (children.length > 0) {
                    $.fn.nav.obtenerChildren(data);
                }
            },
            obtenerChildren: function(nodo) {
                if (nodo.children.length > 0) {
                    for (var i = 0; i < nodo.children.length; i++) {
                        if (nodo.children[i].children.length > 0) {
                            $.fn.nav.obtenerChildren(nodo.children[i]);
                        } else {
                            $.fn.nav.children.push(nodo.children[i]);
                        }
                    }
                }

            },
            openNav: function(nodo) {
                if ($('#tt-app').tabs('exists', nodo.text)) {
                    $('#tt-app').tabs('select', nodo.text);
                } else {
                    if (nodo.principalView != null) {
                        $('#tt-app').tabs('add', {
                            title: nodo.text,
                            href: $.fn.base + nodo.principalView,
                            closable: true,
//                            fit:true,
//                            border:false,
//                            plain:true,
                            extractor: function(data) {
                                data = $.fn.panel.defaults.extractor(data);
                                var tmp = $('<div></div>').html(data);
//                            data = tmp.find('#content').html();
//                            tmp.remove();
                                return tmp;
                            }
                        });
                    }
                }
            },
             onClick: function(data) {
                $('#dg-porAutorizar').datagrid('reload'); 
                $('#dg-contingencia').datagrid('reload');
           }
        },
        loading: {
            open: function(title, msg) {
                $.fn.loading.close();
                $.messager.progress({
                    title: title,
                    msg: msg + '...'
                });
            },
            close: function() {
                $.messager.progress('close');
            }
        },
        convertString: function(obj) {
            var d = $("<div/>");
            d.append(obj);
            return d.html().toString();
        }
    });
})(jQuery);