package br.com.view;

import java.awt.Color;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.swing.JTabbedPane;

import br.com.application.Sessao;
import br.com.dominio.Usuario;

public class JTabbedPaneContatos extends JTabbedPane {
	public static void main(String[] args) {
		Principal p = new Principal();
		p.setVisible(true);
	}
	private static final long serialVersionUID = 1L;
	private Map<Usuario, JanelaDeChat> guardadorDeChats = new HashMap<Usuario, JanelaDeChat>();

	public JTabbedPaneContatos() {
		addContainerListener(new ContainerListener() {

			@Override
			public void componentRemoved(ContainerEvent e) {
				removeTab(((JanelaDeChat) e.getChild()).getUsuario());
			}

			@Override
			public void componentAdded(ContainerEvent e) {
			}
		});
	}

	public void addNewTab(Usuario usuario,Sessao sessao) {
		addNewTab(usuario, sessao,null);
	}
	public void addNewTab(Usuario usuario,Sessao sessao, String mensagem) {
		JTabbedPaneBotaoFechar botaoTab = new JTabbedPaneBotaoFechar(this);
		
		JanelaDeChat janelaDeChat = new JanelaDeChat(usuario,botaoTab);
		if(mensagem != null){
			janelaDeChat.receberTexto(mensagem);
		}

		int posicao = this.getTabCount();
		this.addTab(usuario.getName(), janelaDeChat);
		this.setTabComponentAt(posicao, botaoTab);
		this.setSelectedIndex(posicao);

		guardadorDeChats.put(usuario, janelaDeChat);
		usuario.registrarOuvinte(janelaDeChat);
		usuario.registrarConversa();
	}

	public boolean contem(Usuario usuario) {
		return guardadorDeChats.containsKey(usuario);
	}
	public boolean contem(String from) {
		Set<Usuario> keySet = guardadorDeChats.keySet();
		for (Usuario usuario : keySet) {
			if(usuario.getUser().equalsIgnoreCase(from)){
				return true;
			}
		}
		return false;
	}

	public void removeTab(Usuario usuario) {
		guardadorDeChats.remove(usuario);
		usuario.sairDaConversa();
	}

	public void setSelectedComponent(Usuario contato) {
		setSelectedComponent(guardadorDeChats.get(contato));
	}

	public void avisarChegadaDeConversa(Usuario usuario) {
		if(getSelectedComponent() != guardadorDeChats.get(usuario)){
			JanelaDeChat janelaDeChat = guardadorDeChats.get(usuario);
			janelaDeChat.mudarCorDoMotao(Color.RED);
		}
	}
}
