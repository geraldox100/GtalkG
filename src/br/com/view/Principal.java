package br.com.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import br.com.application.Sessao;
import br.com.application.SessaoGtalk;
import br.com.application.UsuarioListModel;

public class Principal extends JFrame {

	public static void main(String[] args) {
		Principal p = new Principal();
		p.setVisible(true);
	}

	private static final long serialVersionUID = 1L;

	private JPanel panelPrincipal = new JPanel();
	private JPanel panelSuperior = new JPanel();
	private JTabbedPaneContatos conversas;
	private JListContatos contatos;
	private JPanel panelPerguntaUsuarioESenha;
	private JPasswordField password;
	private JTextField login;
	private Sessao sessao;

	public Principal() {

		try {
			perguntaUsuarioESenha();
			montaJanelaPrincipal();
			montaPanelPrincipal();
			montaChat();
			montaListaDeContatos();
			montaPanelSuperior();
			montaMenu();
			montaStatus();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(this,
					"Ocorreu um erro, a tela pode não ser inicializada");
			JOptionPane.showMessageDialog(this, e);
		}
	}

	private void montaMenu() {
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("Configuracao");
		JMenuItem conectar = new JMenuItem("Conectar");
		conectar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				perguntarUsuarioESenha();
				System.out.println("Fazendo login para: " + login.getText()
						+ " senha: " + password.getPassword().length);
				
				sessao = new SessaoGtalk("geraldox100@gmail.com", new String(
						password.getPassword()));
				
				contatos.setSessao(sessao);
				populaContatos();
				sessao.registrarMonitor(Principal.this.contatos);
			}

		});

		menu.add(conectar);
		menuBar.add(menu);
		panelSuperior.add(menuBar);
	}

	private void perguntarUsuarioESenha() {
		JOptionPane.showMessageDialog(null, panelPerguntaUsuarioESenha,
				"Acesso restrito", JOptionPane.PLAIN_MESSAGE);
	}

	private void montaPanelSuperior() {
		this.panelSuperior = new JPanel();
		this.panelSuperior.setLayout(new GridLayout(2, 1));

		this.panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

	}

	private void populaContatos() {
		contatos.setModel(new UsuarioListModel(sessao.getUsuarios()));
	}

	private void perguntaUsuarioESenha() {
		login = new JTextField(10);
		password = new JPasswordField(10);
		password.setEchoChar('*');

		JLabel rotuloLogin = new JLabel("Entre o login:");
		JLabel rotuloSenha = new JLabel("Entre com a senha:");

		panelPerguntaUsuarioESenha = new JPanel();

		panelPerguntaUsuarioESenha.add(rotuloLogin);
		panelPerguntaUsuarioESenha.add(login);
		panelPerguntaUsuarioESenha.add(rotuloSenha);
		panelPerguntaUsuarioESenha.add(password);

	}

	private void montaJanelaPrincipal() {
		this.setSize(800, 600);
		this.setTitle("Meu GTalk Client");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void montaPanelPrincipal() {
		panelPrincipal.setLayout(new BorderLayout());
		this.setContentPane(panelPrincipal);
	}

	private void montaListaDeContatos() {
		contatos = new JListContatos(conversas);
		JScrollPane listScroller = new JScrollPane(contatos);
		listScroller.setPreferredSize(new Dimension(250, 80));
		panelPrincipal.add(listScroller, BorderLayout.WEST);
	}

	private void montaChat() {
		conversas = new JTabbedPaneContatos();
		panelPrincipal.add(conversas, BorderLayout.CENTER);
	}

	private void montaStatus() {
		ButtonGroup mode = new ButtonGroup();
		JPanel modePanel = new JPanel();

		List<JRadioButton> variosRadios = montaVariosRadioButton("Availabel",
				"Away", "Free to Chat", "Do not Disturbe", "Away for long time");

		for (JRadioButton jRadioButton : variosRadios) {
			mode.add(jRadioButton);
			modePanel.add(jRadioButton);
		}
		JPanel panel = new JPanel();
		panel.add(modePanel);
		panelSuperior.add(panel, BorderLayout.NORTH);

	}

	private List<JRadioButton> montaVariosRadioButton(String... labels) {
		List<JRadioButton> radios = new ArrayList<JRadioButton>();
		for (String label : labels) {
			radios.add(montaRadioButton(label));
		}
		return radios;
	}

	private JRadioButton montaRadioButton(String label) {
		JRadioButton jradio = new JRadioButton();
		jradio.setText(label);
		return jradio;
	}
}
