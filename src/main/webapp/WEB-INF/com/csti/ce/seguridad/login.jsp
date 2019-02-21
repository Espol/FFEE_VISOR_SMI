 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd"><html xmlns="http://www.w3.org/1999/xhtml">
     <head>
         <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="Cache-control" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1" />
         
        <title>Login::SISTEMA DE MONITOREO DE C.E.</title>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/bootstrap/easyui.css"/>
        <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/theme/icon.css"/>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
        
    <script type="text/javascript">
    
    	function getParameter(theParameter) {
	   		var params = window.location.search.substr(1).split('&');
    	
			for (var i = 0; i < params.length; i++) {
			   	var p=params[i].split('=');
				if (p[0] == theParameter) {
				  return decodeURIComponent(p[1]);
				}
			}
	    	  return false;
		}
		
        function submitForm() {
        	debugger;
        	$.ajax({
        	    url: 'view/login/verify',
        	    dataType: 'json',
        	    contentType: "application/json; charset=utf-8",
        	    data: {
        	    		'userName':$('#userName').val(),
        	    		'password':$('#password').val(),
        	    		'sociedad':$('#sociedad').combobox('getValue'),
        	    		'ambiente':$('#ambiente').val()        	    		
        	    },
	      		  success: function(result) {
	      			  
	                     if (result.success) {
	                         $('#dgUsuario').datagrid('reload');    // reload the user data
	                         $.messager.alert('Confirmación', result['message']);
	                  		location.reload();
	                     } else {
	                         $.messager.alert('Error', result['message'] );
// 	                         $.messager.show({// show error message
// 	                             title: 'Error',
// 	                             msg: result.errorMsg
// 	                         });
	                     }
	      		  }
      		});
        }
        
        function clearForm() {
            $('#ff').form('clear');
        }
    </script>
    
     </head>
    <body>
        <center>
       <div class="easyui-panel" title="ACCESO AL SISTEMA" style="width:400px">
            <div style="padding:10px 60px 20px 60px">
                <form id="ff" method="post" action="view/login/verify">
                    <table cellpadding="5">
                        <tr>
                            <td>Usuario:</td>
                            <td><input class="easyui-validatebox textbox" type="text" id="userName" name="userName" data-options="required:true"></input></td>
                        </tr>
                        <tr>
                            <td>Contraseña:</td>
                            <td><input class="easyui-validatebox textbox" type="password" id="password" name="password" data-options="required:true"></input></td>
                        </tr> 
                        <tr>
                            <td>Sociedad:</td>
                            <td>
<!--                              -->
                                <select id="sociedad"  name="sociedad" style="width:200px;" class="easyui-combobox" data-options="required:true"> 
                                    <option value="0992865768001">SMI</option>
                                </select>
                            </td>
                        </tr>
<!--                         <tr> -->
<!--                             <td>Ambiente:</td> -->
<!--                             <td> -->
								<div style="visibility:hidden;">
                                <select id="ambiente" name="ambiente" style="width:200px;" >
                                    <option value="1" selected="selected">PRUEBA</option>
                                </select>
                                </div>
<!--                             </td> -->
<!--                         </tr> -->
                    </table>
                   <div style="text-align:center;padding:15px">
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="submitForm()">Entrar</a>
                        <a href="javascript:void(0)" class="easyui-linkbutton" onclick="clearForm()">Limpiar</a>
                    </div>
                </form>

            </div>
       </div>
    <style scoped="scoped">
        .textbox{
            height:30px;
            margin:0;
            padding:0 5px;
            box-sizing:content-box;
        }
    </style>
    </center>
    
    <script>

	var sociedadParam= getParameter('sociedad')!=false?getParameter('sociedad'):'', userNameParam=getParameter('userName')!=false?getParameter('userName'):'';
    
    $("#document").ready(function() {
		
    	$('#sociedad').combobox('select',sociedadParam);
    	$('#userName').val(userNameParam);
    	
    });
    </script>
</body>
</html>