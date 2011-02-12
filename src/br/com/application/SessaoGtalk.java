package br.com.application;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.SASLAuthentication;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.AndFilter;
import org.jivesoftware.smack.filter.FromContainsFilter;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.filter.PacketTypeFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;

import br.com.dominio.Monitor;
import br.com.dominio.Ouvinte;
import br.com.dominio.Usuario;
import br.com.view.Principal;

public class SessaoGtalk extends Sessao {

	public static void main(String[] args) {
		Principal p = new Principal();
		p.setVisible(true);
	}

	private static final String HOST = "talk.google.com";
	private static final int PORT = 5222;
	private static final String SERVICE = "gmail.com";

	public SessaoGtalk(String login, String senha) {
		this.login = login;
		this.senha = senha;
		conectar();
		efetuarLogin(login, senha);
		buscarUsuarios();
		avisarMonitores();
	}

	protected void avisarMonitores() {
		PacketFilter filter = new AndFilter(new PacketTypeFilter(Message.class));
		PacketListener pl = new PacketListener() {
			@Override
			public void processPacket(Packet p) {
				Message msg = (Message) p;
				System.out.println(msg);
				if (msg.getBody() != null && !msg.getBody().equals("")) {
					for (Monitor monitor : monitores) {
						monitor.mensagemNova(somenteNome(msg.getFrom()), msg
								.getBody());
					}
				}
			}

			private String somenteNome(String from) {
				return from.substring(0, from.indexOf("/"));
			}
		};
		conexao.addPacketListener(pl, filter);

	}

	private void conectar() {
		ConnectionConfiguration connConfig = new ConnectionConfiguration(HOST,
				PORT, SERVICE);

		conexao = new XMPPConnection(connConfig);

		try {
			conexao.connect();
			SASLAuthentication.supportSASLMechanism("PLAIN", 0);
		} catch (XMPPException e) {
			throw new RuntimeException(e);
		}
	}

	private void efetuarLogin(String login, String senha) {
		try {
			conexao.login(login, senha);
		} catch (XMPPException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Usuario> buscarUsuarios() {
		Roster roster = conexao.getRoster();
		Iterator<RosterEntry> iter = roster.getEntries().iterator();
		while (iter.hasNext()) {
			RosterEntry entry = iter.next();
			usuarios.add(new Usuario(entry.getName(), entry.getUser(), entry
					.getStatus(), entry.getType(), this));
		}
		organizar();
		return usuarios;
	}

	private void organizar() {
		Collections.sort(usuarios, new Comparator<Usuario>() {

			@Override
			public int compare(Usuario usuario1, Usuario usuario2) {
				if (usuario1.getName() != null && usuario2.getName() != null)
					return usuario1.getName().compareTo(usuario2.getName());
				else
					return usuario1.getUser().compareTo(usuario2.getUser());
			}
		});
	}

	public void registrarConversa(final Usuario usuario) {
		PacketFilter filter = new AndFilter(
				new PacketTypeFilter(Message.class), new FromContainsFilter(
						usuario.getUser()));

		PacketListener myListener = new PacketListener() {
			public void processPacket(Packet packet) {
				if (packet instanceof Message) {
					Message msg = (Message) packet;
					if (msg.getBody() != null && !msg.getBody().equals("")) {
						for (Ouvinte ouvinte : usuario.getOuvintes()) {
							ouvinte.exibirMensagem(msg.getBody());
						}
					}
				}
			}
		};

		conversas.put(usuario, myListener);
		conexao.addPacketListener(myListener, filter);
	}

	public void removerConversa(Usuario usuario) {
		conexao.removePacketListener(conversas.get(usuario));
		conversas.remove(usuario);
	}

	public boolean reconectar() {
		efetuarLogin(login, senha);
		return conexao.isConnected();
	}

	public void enviarConversa(String fala, Usuario usuario) {
		Message msg = new Message(usuario.getUser(), Message.Type.chat);
		msg.setBody(fala);
		conexao.sendPacket(msg);
	}

}
