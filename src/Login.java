// Importação de classes necessárias
import javax.swing.*;
import java.awt.*;

public class Login
    {
        public static void loginAdmin()
            {
                JFrame frame = new JFrame("EcoColeta - Login");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(400, 250);
                frame.setLocationRelativeTo(null);
                frame.getContentPane().setBackground(new Color(245, 255, 245));

                // Painel principal
                JPanel panel = new JPanel(new GridBagLayout());
                panel.setBackground(new Color(245, 255, 245));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(8, 8, 8, 8);

                // Título
                JLabel titulo = new JLabel("Login de Administrador");
                titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
                titulo.setForeground(new Color(0, 100, 0));

                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                panel.add(titulo, gbc);

                // Label Usuário
                JLabel lblUser = new JLabel("Usuário:");
                lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                lblUser.setForeground(new Color(50, 50, 50));

                gbc.gridx = 0;
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                panel.add(lblUser, gbc);

                // Campo Usuário
                JTextField txtUser = new JTextField(15);
                gbc.gridx = 1;
                panel.add(txtUser, gbc);

                // Label Senha
                JLabel lblSenha = new JLabel("Senha:");
                lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                lblSenha.setForeground(new Color(50, 50, 50));

                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(lblSenha, gbc);

                // Campo Senha
                JPasswordField txtSenha = new JPasswordField(15);
                gbc.gridx = 1;
                panel.add(txtSenha, gbc);

                // Botão Login
                JButton btnLogin = new JButton("Entrar");
                btnLogin.setBackground(new Color(120, 200, 120));
                btnLogin.setForeground(Color.BLACK);
                btnLogin.setFocusPainted(false);
                btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnLogin.setPreferredSize(new Dimension(120, 35));

                // Botão Cancelar
                JButton btnCancelar = new JButton("Cancelar");
                btnCancelar.setBackground(new Color(200, 200, 200));
                btnCancelar.setForeground(Color.BLACK);
                btnCancelar.setFocusPainted(false);
                btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnCancelar.setPreferredSize(new Dimension(120, 35));

                // Ação do botão Login
                btnLogin.addActionListener(event -> {
                    String usuario = txtUser.getText();
                    String senha = new String(txtSenha.getPassword());

                    if ("admin".equals(usuario) && "acesso741!".equals(senha))
                        {
                            PainelAdmin.requisicoesAdmin(); //chama painel do administrador
                            frame.dispose(); //fecha a tela de login
                        }
                    else
                        {
                            JOptionPane.showMessageDialog(frame,"Usuário ou senha incorretos.","Erro de Login",JOptionPane.ERROR_MESSAGE);
                            frame.dispose();
                            Menu.frame.setVisible(true); // volta ao menu principal
                        }
                });

                // Ação do botão Cancelar
                btnCancelar.addActionListener(event -> {
                    frame.dispose(); //fecha a tela de login
                    Menu.frame.setVisible(true); //volta ao menu principal
                });

                // Painel para os botões lado a lado
                JPanel botoesPanel = new JPanel();
                botoesPanel.setBackground(new Color(245, 255, 245));
                botoesPanel.add(btnLogin);
                botoesPanel.add(btnCancelar);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                panel.add(botoesPanel, gbc);

                frame.add(panel); //adiciona painel à janela
                frame.setVisible(true); //torna janela visível
            }
    }