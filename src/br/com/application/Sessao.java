package br.com.application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.XMPPConnection;

import br.com.dominio.Monitor;
import br.com.dominio.Usuario;

public abstract class Sessao {
	protected XMPPConnection conexao;
	protected String nome;
	protected String login;
	protected String senha;
	protected List<Usuario> usuarios = new ArrayList<Usuario>();
	protected Map<Usuario, PacketListener> conversas = new HashMap<Usuario, PacketListener>();
	protected List<Monitor> monitores = new ArrayList<Monitor>();

	public String getNome() {
		return nome;
	}

	public String getLogin() {
		return login;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}
	
	protected List<Monitor> buscarMonitores(){
		return this.monitores;
	}
	
	public void registrarMonitor(Monitor monitor){
		this.monitores.add(monitor);
	}
	
	

	protected abstract List<Usuario> buscarUsuarios();
	
	protected abstract void avisarMonitores();

	public abstract void registrarConversa(final Usuario usuario);

	public abstract void removerConversa(Usuario usuario);

	public abstract boolean reconectar();

	public abstract void enviarConversa(String fala, Usuario usuario);
}
