package br.com.view;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JList;
import javax.swing.ListSelectionModel;

import br.com.application.Sessao;
import br.com.dominio.Monitor;
import br.com.dominio.Usuario;

public class JListContatos extends JList implements Monitor {

	public static void main(String[] args) {
		Principal p = new Principal();
		p.setVisible(true);
	}

	private static final long serialVersionUID = 1L;
	private JTabbedPaneContatos conversas;
	private Sessao sessao;

	public JListContatos(final JTabbedPaneContatos conversas) {
		this.conversas = conversas;
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setLayoutOrientation(JList.VERTICAL);
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					Usuario usuario = (Usuario) JListContatos.this
							.getSelectedValue();

					if (!JListContatos.this.conversas.contem(usuario)) {
						JListContatos.this.conversas.addNewTab(usuario, sessao);
					} else {
						JListContatos.this.conversas.setSelectedComponent(usuario);
					}
				}
			}
		});
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	@Override
	public void mensagemNova(String from, String body) {

		Usuario usuario = getValueByString(from);
		if (usuario != null) {
			if (!conversas.contem(usuario)) {
				conversas.addNewTab(usuario, sessao, body);
			} else {
				conversas.avisarChegadaDeConversa(usuario);
			}
		}
	}

	private Usuario getValueByString(String from) {
		for (int i = 0; i < this.getModel().getSize(); i++) {
			Usuario usuario = (Usuario) getModel().getElementAt(i);
			if (usuario.getUser().equals(from)) {
				return usuario;
			}
		}
		return null;
	}

}
