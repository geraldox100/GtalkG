package br.com.dominio;

import java.util.ArrayList;
import java.util.List;

import org.jivesoftware.smack.packet.RosterPacket.ItemStatus;
import org.jivesoftware.smack.packet.RosterPacket.ItemType;

import br.com.application.Sessao;

public class Usuario {

	private String name;
	private String user;
	private ItemStatus status;
	private ItemType type;
	private Sessao sessao;
	private List<Ouvinte> ouvintes = new ArrayList<Ouvinte>();

	public Usuario(String name, String user, ItemStatus status, ItemType type,
			Sessao sessao) {
		super();
		this.name = name;
		this.user = user;
		this.status = status;
		this.type = type;
		this.sessao = sessao;

	}

	public String getName() {
		return name;
	}

	public String getUser() {
		return user;
	}

	public ItemStatus getStatus() {
		return status;
	}

	public ItemType getType() {
		return type;
	}

	public List<Ouvinte> getOuvintes() {
		return ouvintes;
	}

	public void registrarOuvinte(Ouvinte ouvinte) {
		ouvintes.add(ouvinte);
	}

	public void conversar(String mensagem) {
		sessao.enviarConversa(mensagem, this);
	}

	@Override
	public String toString() {
		return name + "(" + user + ")";
	}

	public void registrarConversa() {
		sessao.registrarConversa(this);
	}

	public void sairDaConversa() {
		sessao.removerConversa(this);
	}

}
