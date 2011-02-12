package br.com.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import br.com.dominio.Ouvinte;
import br.com.dominio.Usuario;

public class JanelaDeChat extends JPanel implements Ouvinte {

	public static void main(String[] args) {
		Principal p = new Principal();
		p.setVisible(true);
	}

	private static final long serialVersionUID = 1L;
	final private JTextArea text = new JTextArea();
	final private JTextField field = new JTextField();
	final private Usuario usuario;
	private JTabbedPaneBotaoFechar botaoTab;
	private JScrollPane scrollTextArea;

	public JanelaDeChat(Usuario usuario, JTabbedPaneBotaoFechar botaoTab) {
		this.usuario = usuario;
		this.botaoTab = botaoTab;
		montaJanela();
		montarTextArea();
		montarTextField();
		montarBotoes();
	}

	private void montaJanela() {
		this.setBackground(Color.BLUE);
		this.setLayout(new GridLayout(3, 1));
	}

	private void montarTextArea() {
		text.setEditable(false);

		scrollTextArea = new JScrollPane(text);
		scrollTextArea.setAutoscrolls(true);
		scrollTextArea.setWheelScrollingEnabled(true);
		this.add(scrollTextArea);
	}

	private void montarTextField() {
		field.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				char keyChar = e.getKeyChar();
				if (keyChar == '\n') {
					enviarText();
				}
			}
		});
		JScrollPane scrollTextField = new JScrollPane(field);
		this.add(scrollTextField);
	}

	private void montarBotoes() {

		JPanel panelBotoes = new JPanel();
		panelBotoes.setLayout(new FlowLayout());

		JButton btn = new JButton("Enviar");
		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				enviarText();
			}

		});

		JButton btnLimparConversa = new JButton("Limpar Conversa");
		btnLimparConversa.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				text.setText("");
				field.requestFocus();
			}
		});

		JButton btnLimparTexto = new JButton("Limpar Texto");
		btnLimparTexto.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				field.setText("");
				field.requestFocus();
			}
		});

		panelBotoes.add(btn);
		panelBotoes.add(btnLimparConversa);
		panelBotoes.add(btnLimparTexto);
		this.add(panelBotoes);
	}

	private void enviarText() {
		if (!"".equals(field.getText())) {
			String conversa = text.getText();
			String fala = field.getText();

			usuario.conversar(fala);

			text.setText(conversa + "\n\n" + "EU : \n" + fala);
			field.setText("");
		}
	}

	public void receberTexto(String texto) {
		String conversa = text.getText();
		JScrollBar vscb = scrollTextArea.getVerticalScrollBar();
		text.setText(conversa + "\n\n" + usuario.getName() + ": \n" + texto);
		vscb.setValue(vscb.getMaximum());
	}

	public Usuario getUsuario() {
		return usuario;
	}

	@Override
	public void exibirMensagem(String body) {
		receberTexto(body);
	}

	public void mudarCorDoMotao(Color color) {
		botaoTab.chageLabelColor(color);
	}

}
