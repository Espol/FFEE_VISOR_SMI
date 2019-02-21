<%-- 
    Document   : usuario
    Created on : 23-jun-2014, 22:16:15
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Usuario</title>
        
        <script type="text/javascript">
        
        </script>
        
        
    </head>
    <body>
        <table id="dgUsuario" class="easyui-datagrid" style="min-width: 670px;height:450px"
        		pagination="true"
               data-options="border:false,
               url:'${pageContext.request.contextPath}/view/usuario/getUsersBySociedad',               
           		rownumbers:true,
               fitColumns:true,
               singleSelect:true,
               pageSize:10"
               toolbar="#toolbarUsuario" 
           	>
            <thead>
                <tr>
                    <!--<th data-options="field:'idUsuario',align:'center'" width="50"><strong>Id Usuario</strong></th>-->
                    <!--<th data-options="field:'idPersona',align:'center'" width="50"><strong>Id Persona</strong></th>-->
                    <th data-options="field:'usuario.userName',align:'center', formatter:function(value,row){return row.usuario.userName}" width="50"><strong>Usuario</strong></th>
                    <th data-options="field:'rol.nombre',align:'center', formatter:function(value,row){return row.rol.nombre}" width="50" ><strong>Rol</strong></th>
                    <th data-options="field:'usuario.activo',align:'center', formatter:function(value,row){return row.usuario.activo==1?'SI':'NO'} " width="50"><strong>Activo</strong></th>
                </tr>
            </thead>
        </table>
        <div id="toolbarUsuario">
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUsuario()">Nuevo Usuario</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUsuario()">Editar Usuario</a>
            <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUsuario()">Borrar Usuario</a>
        </div>
        <div id="dlgUsuario" class="easyui-dialog" modal="true" style="width:350px;height:340px;padding:10px 20px"
             closed="true" buttons="#dlg-buttons">
            <form id="fmUsuario" method="post" novalidate>
                <div id="dlg-input">
                	<input type="hidden" name="idUsuarioRol" />
                	
                	<input type="hidden" name="usuario.idUsuario" />
                	<input type="hidden" id="idPersona" name="usuario.persona.idPersona" />
                	<input type="hidden" name="usuario.sociedad" />
                	<input type="hidden" id="idActivo" name="usuario.activo" />
                	
                	<input type="hidden" name="rol.codigo" />
                	<input type="hidden" name="rol.nombre" />
                	<input type="hidden" name="rol.descripcion" />
                	<input type="hidden" name="rol.activo" />
                	<input type="hidden" name="rol.observacion" />
                	
                	<input type="hidden" id="idSistema" name="rol.sistema.idSistema" />
                	
                    <div class="fitem">
                        <label>User :</label>
                        <input id="userName" name="usuario.userName" class="easyui-validatebox" data-options="required:true" />
                    </div>
                    <div class="fitem">
                        <label>Password :</label>
                        <input id="usuario.userPassword" name="usuario.userPassword" type="password" class="easyui-validatebox" data-options="required:true" />
                    </div>
                    <div class="fitem">
                        <label>Activo :</label>
                        <input id="ckUserActivo" type="checkbox" class="easyui-validatebox" />
                    </div>
                    
<!--                     <div class="fitem"> -->
<!--                         <label>Persona :</label> -->
<!--                         <input class="easyui-combobox" id="cmbPersona" name="idPersona" /> -->
<!--                     </div> -->
                    
                    <div class="fitem">
                        <label>Rol :</label>
                        <input class="easyui-combobox" id="cmbRol" name="rol.idRol" />
                    </div>
                </div>
            </form>
        </div>
        <div class="fitem">
            <input name="usuario.activo" type="hidden" />
            <input name="usuario.idUsuario" type="hidden" />
        </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" onclick="saveUsuario()">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlgUsuario').dialog('close')">Cancel</a>
    </div>
    <script type="text/javascript">
	    
	    $.extend($.fn.form.methods, {
	    	load3:	function (frm, data) {
		    	  		$.each(data, function(key, value){
			    	  		if(value instanceof Object){
			    	  			$.each(value, function(key1, value1){
			    	  				$("[name='"+key+"."+key1+"']", frm).val(value1);		    	  				
			    	  			})
			    	  		}else{
					    	    $('[name='+key+']', frm).val(value);
			    	  		}		    	  		
			    	  	});
			    	}
	    });
    
        var url;
        var mensaje;
        var row;
        var flagAccion=0;//1 nuevo usuario, 2 editar usuario.
        
        function newUsuario() {
        	flagAccion=1;
            cargarPersona(1);
            cargarRol(1);
            
            $('#dlgUsuario').dialog('open').dialog('setTitle', 'Nuevo Usuario');
            $('#fmUsuario').form('clear');
            $("#ckUserActivo").prop('checked', true);
            mensaje = 'Guardando Usuario';
            url = '${pageContext.request.contextPath}/view/usuario/save?accion=add';
        }
        function editUsuario() {
        	flagAccion = 2;
            row = $('#dgUsuario').datagrid('getSelected');
            if (row) {
//                 cargarPersona(row.persona.idPersona);
                //cargarSistema(1);
                cargarRol(row.rol.idRol);
                $('#fmUsuario').form('clear');
                $('#dlgUsuario').dialog('open').dialog('setTitle', 'Editar Usuario');
                if (row.usuario.activo === '1') {
                    $('#ckUserActivo').prop('checked', true);
                }
				
                $('#fmUsuario').form('load3', row);
                mensaje = 'Editando Usuario';
                url = '${pageContext.request.contextPath}/view/usuario/save?accion=update';
            } else {
                $.messager.alert("Advertencia", "Debe Seleccionar un Item");
            }
        }
        function saveUsuario() {
            var activo = $("#ckUserActivo");
            $('#idPersona').val(4);//Id Persona Default
            $('#idSistema').val(1);//Id Sistema Default
            if (activo.is(':checked')) {
                $('#idActivo').val('1');
            } else {
                $('#idActivo').val('0');
            }
            debugger;
            $.fn.loading.open('Cargando', mensaje);
            
            var paramObj = {};
            $.each($('#fmUsuario').serializeArray(), function(_, kv) {
              if (paramObj.hasOwnProperty(kv.name)) {
                paramObj[kv.name] = $.makeArray(paramObj[kv.name]);
                paramObj[kv.name].push(kv.value);
              }
              else {
                paramObj[kv.name] = kv.value;
              }
            });
            
        	$.ajax({
        		method: "POST",
        	    url: url,
        	    data: paramObj,
	      		  success: function(result) {
	      			  debugger;
	      			  console.log(result);
	                   $.fn.loading.close();
// 	                   var obj = $.parseJSON(result);
	                   $.messager.alert('Confirmación', result['message']);
	                   $('#dlgUsuario').dialog('close');
	                   $('#dgUsuario').datagrid('reload');
	      		  }
      		});
            
            
//             $('#fmUsuario').form('submit', {
//                 url: url,
//                 onSubmit: function() {
//                     $.fn.loading.close();
//                     return $(this).form('validate');
//                 },
//                 success: function(result) {
//                 	debugger;
//                 	console.log('result: '+result);
//                     $.fn.loading.close();
// //                     var obj = $.parseJSON(result);
//                     $.messager.alert('Confirmación', result['message']);
//                     $('#dlgUsuario').dialog('close');
//                     $('#dgUsuario').datagrid('reload');
//                 }
//             });
            
            
            
            
        }
        function destroyUsuario() {
            var row = $('#dgUsuario').datagrid('getSelected');
            if (row) {
            	debugger;
            	var usuarioRol = {'idUsuarioRol':row.idUsuarioRol, 'usuario.idUsuario': row.usuario.idUsuario, 'usuario.sociedad':row.usuario.sociedad};
            	
                $.messager.confirm('Confirmación', '¿Esta Seguro que quiere Eliminar este Usuario?', function(r) {
                    if (r) {
                    	$.ajax({
                    		type: "POST",
                    		  url: '${pageContext.request.contextPath}/view/usuario/deleteUser',
                    		  data: usuarioRol,
                    		  success: function(result) {
	                               if (result.success) {
		                               $('#dgUsuario').datagrid('reload');    // reload the user data
										
		                               $.messager.alert('Confirmación', result['message']);
		                           } else {
		                               $.messager.show({// show error message
		                                   title: 'Error',
		                                   msg: result.errorMsg
		                               });
		                           }
                    		  }
                    		});
                    }
                });
            } else {
                $.messager.alert("Advertencia", "Debe Seleccionar un Item");
            }
        }

        $(document).ready(function() {
            $('#dgUsuario').datagrid({
                onDblClickCell: function(index, field, value) {
                    editUsuario();
                }
            });
        });

        function cargarPersona(valor) {
            $.ajax({type: 'POST',
                url: '${pageContext.request.contextPath}/view/persona/loadAll',
                dataType: 'json',
                success: function(data) {
                    $('#cmbPersona').combobox({
//                         valueField: 'idPersona',
                        textField: 'razonSocial',
                        data: data.rows
                    });
                    $('#cmbPersona').combobox('setValue', valor);
                }
            });
        }

        function cargarRol(valor) {
            $.ajax({type: 'POST',
                url: '${pageContext.request.contextPath}/view/rol/loadAll',
                dataType: 'json',
                success: function(data) {
                    $('#cmbRol').combobox({
                        valueField: 'idRol',
                        textField: 'nombre',
                        data: data.rows
                    });
                    $('#cmbRol').combobox('setValue', valor);
                }
            });
        }

    </script>
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
    </style>
</body>
</html>
