package br.com.application;

import java.util.List;

import javax.swing.DefaultListModel;

import br.com.dominio.Usuario;

public class UsuarioListModel extends DefaultListModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public UsuarioListModel(List<Usuario> usuarios) {
		adicionarTodos(usuarios);
	}
	
	public void adicionarTodos(List<Usuario> usuarios){
		for (int i = 0; i < usuarios.size(); i++) {
			super.add(i, usuarios.get(i));
		}
	}
	
	public void  atualizar(List<Usuario> usuarios){
		super.clear();
		adicionarTodos(usuarios);
	}

}
