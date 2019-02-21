/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.csti.ce.generic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.poi.util.IOUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.view.AbstractView;

import com.csti.ce.seguridad.domain.Usuario;
import com.csti.ce.seguridad.domain.UsuarioApp;
import com.csti.ce.util.Constants;

/**
 *
 * @author Usuario
 */
public abstract class GenericController extends AbstractView{

    protected final Logger log = Logger.getLogger(GenericController.class);

    public String getFileContentAsString(BufferedReader bufferedReader) throws Exception{
            BufferedReader buf = new BufferedReader(bufferedReader);

            StringBuffer sBuf = new StringBuffer();
            String s = null;

            while ((s = buf.readLine()) != null)
            {
                    sBuf.append(s);
            }
            return sBuf.toString();
    }
    
    protected HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    protected Usuario getUsuarioSesion() {
        Usuario usuario = null;
        UsuarioApp usuarioApp = (UsuarioApp) this.getRequest().getSession().getAttribute(Constants.USER_SESSION);
        if (usuarioApp == null) {
            System.out.println("el usuario es nullo");
        } else {
            usuario = usuarioApp.getUsuario();
            System.err.println("si hay usuario" + usuarioApp.getUserNameApp());
        }
        return usuario;
    }

    public String getSociedadRUC() {
        return (String) this.getRequest().getSession().getAttribute(Constants.SOCIEDAD);
    }
    
    public String getAmbiente() {
        return (String) this.getRequest().getSession().getAttribute(Constants.AMBIENTE);
    }

    protected void viewPdf(String resourse,
            String filename,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
        InputStream inputStream = new FileInputStream(resourse);
        response.setContentType("application/pdf");
        response.setHeader("Content-disposition", "inline; filename=" + filename.toLowerCase() + ".pdf");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    protected void viewXML(String resourse,
            String filename,
            HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        InputStream inputStream = new FileInputStream(resourse);
//        response.setContentType("application/xml");
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-disposition", "inline; filename=" + filename.toLowerCase() + ".xml");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        
//    	Map map = new HashMap<>();
//    	map.put("resource", resourse);
//    	
//    	renderMergedOutputModel(map, request, response);
    }
    
    @Override
    protected void renderMergedOutputModel(Map arg0, HttpServletRequest request, HttpServletResponse response)
                    throws Exception
    {
            try
            {
                    response.setContentType("text/xml; charset=UTF-8");
                    response.setCharacterEncoding("UTF-8");
                    String locReport = (String) arg0.get("resource");

                    response.setContentType(getContentType());

                    response.getWriter().write(getFileContentAsString(new BufferedReader(new FileReader(locReport))));
            }
            catch (Exception e)
            {
            }
    }

    protected void viewLOG(String resourse,
            String filename,
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {
//        InputStream inputStream = new FileInputStream(resourse);
        response.setContentType("text/plain");
        response.setHeader("Content-disposition", "inline; filename=" + filename.toLowerCase() + ".log");
        response.setHeader("Cache-Control", "max-age=30");
        response.setHeader("Pragma", "No-cache");
        response.setDateHeader("Expires", 0);
        response.getWriter().write(resourse);
//        bs = new BufferedOutputStream(res.getOutputStream());
//            bs.write(simpleResponse.getBytes());
//        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
    }

    protected boolean evaluarSesion() {
        boolean resultado = false;
        if (this.getAmbiente() != null && !this.getAmbiente().isEmpty()) {
            resultado = true;
        }
        if (this.getSociedadRUC() != null && !this.getSociedadRUC().isEmpty()) {
            resultado = true;
        }
        if (this.getUsuarioSesion() != null) {
            resultado = true;
        }
        return resultado;
    } 
}
