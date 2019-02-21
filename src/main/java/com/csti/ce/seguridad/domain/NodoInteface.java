package com.csti.ce.seguridad.domain;

import java.util.ArrayList;
import java.util.List;
public class NodoInteface {
	
	private String text;
	private String name;
//	private String nivel;
	private String cls;
	private String iconCls;
//	private String classInit;
	private String principalView;
	private Boolean expanded;
//	private Boolean leaf;
	private Character actualizar;
	private Character eliminar;
	private Character agregar;
	private Character lectura;
	private Character todo;
	private List<NodoInteface> children;
	
	public NodoInteface(){
		children= new ArrayList<NodoInteface>();
	}


	public Character getActualizar() {
		return actualizar;
	}


	public void setActualizar(Character actualizar) {
		this.actualizar = actualizar;
	}


	public Character getEliminar() {
		return eliminar;
	}


	public void setEliminar(Character eliminar) {
		this.eliminar = eliminar;
	}


	public Character getAgregar() {
		return agregar;
	}


	public void setAgregar(Character agregar) {
		this.agregar = agregar;
	}


	public Character getLectura() {
		return lectura;
	}


	public void setLectura(Character lectura) {
		this.lectura = lectura;
	}


	public Character getTodo() {
		return todo;
	}


	public void setTodo(Character todo) {
		this.todo = todo;
	}


	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	public String getCls() {
		return cls;
	}

	public void setCls(String cls) {
		this.cls = cls;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	public String getNivel() {
//		return nivel;
//	}
//
//	public void setNivel(String nivel) {
//		this.nivel = nivel;
//	}
//
//	public String getClassInit() {
//		return classInit;
//	}
//
//	public void setClassInit(String classInit) {
//		this.classInit = classInit;
//	}

	public String getPrincipalView() {
		return principalView;
	}

	public void setPrincipalView(String principalView) {
		this.principalView = principalView;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

//	public Boolean getLeaf() {
//		return leaf;
//	}
//
//	public void setLeaf(Boolean leaf) {
//		this.leaf = leaf;
//	}

	public List<NodoInteface> getChildren() {
		return children;
	}

	public void setChildren(List<NodoInteface> children) {
		this.children = children;
	}
}
