package views;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import model.Model;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;

public class Home extends JFrame {

	private JPanel contentPane;
	private Model model = new Model();
	private JComboBox cbPortas;
	JTextArea textArea;

	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 320, 375);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JButton btnAtualizar = new JButton("Atualizar");
		btnAtualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				AtualizarPortas();
			}
		});
		
		cbPortas = new JComboBox();
		
		JButton btnAbrir = new JButton("Conectar");
		btnAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirPorta();
			}
		});
		
		textArea = new JTextArea();
		
		JButton btnFechar = new JButton("Desconectar");
		btnFechar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				closePort();
			}
		});
		
		JButton btnNewButton = new JButton("Ler Buffer:");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				teste();
			}
		});
		
		JLabel lblPortasDisponiveis = new JLabel("Portas Disponiveis");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(cbPortas, GroupLayout.PREFERRED_SIZE, 121, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnAtualizar))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnAbrir)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnFechar))
						.addComponent(lblPortasDisponiveis)
						.addComponent(btnNewButton)
						.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(lblPortasDisponiveis)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbPortas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnAtualizar))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAbrir)
						.addComponent(btnFechar))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(textArea, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(35, Short.MAX_VALUE))
		);
		contentPane.setLayout(gl_contentPane);
	}

	protected void closePort() {
		model.closePort();
	}

	protected void teste() {
		textArea.append(model.LerPorta());
	}

	protected void abrirPorta() {
		model.AbrirPorta(cbPortas.getSelectedItem().toString());
	}

	protected void AtualizarPortas() {
		ArrayList<String> portas = model.ListarPortas();
		cbPortas.removeAll();
		for (String item : portas) {
			cbPortas.addItem(item);
		}
		
		
	}
}
