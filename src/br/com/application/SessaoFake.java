package br.com.application;

import java.util.Arrays;
import java.util.List;

import org.jivesoftware.smack.packet.RosterPacket.ItemStatus;
import org.jivesoftware.smack.packet.RosterPacket.ItemType;

import br.com.dominio.Usuario;
import br.com.view.Principal;

public class SessaoFake extends Sessao {

	public static void main(String[] args) {
		Principal p = new Principal();
		p.setVisible(true);
	}

	public SessaoFake(String login, String senha) {
		this.login = login;
		this.senha = senha;
		this.nome = "Geraldo";

	}

	@Override
	protected List<Usuario> buscarUsuarios() {
		return Arrays.asList(new Usuario("Geraldo", "geraldox100@gmail.com",
				ItemStatus.SUBSCRIPTION_PENDING, ItemType.both, null),
				new Usuario("Nayara", "nnayaraa@gmail.com",
						ItemStatus.SUBSCRIPTION_PENDING, ItemType.both, null),
				new Usuario("Moreira", "guilherme.dealmeidamoreira@gmail.com",
						ItemStatus.SUBSCRIPTION_PENDING, ItemType.both, null));
	}

	@Override
	public void enviarConversa(String fala, Usuario usuario) {

	}

	@Override
	public boolean reconectar() {
		return true;
	}

	@Override
	public void registrarConversa(Usuario usuario) {
		conversas.put(usuario, null);
	}

	@Override
	public void removerConversa(Usuario usuario) {
		conversas.remove(usuario);
	}

	@Override
	protected void avisarMonitores() {
		
	}

}
