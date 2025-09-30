import javax.swing.*;
import java.awt.*;

public class Login
    {

        public static void loginAdmin() //Inicializa e exibe a tela de login para o Administrador.
            {
                JFrame frame = ConfUI.criarFrame("EcoColeta - Login",450, 320);

                // Painel com GridBagLayout para organizar campos de texto e botões.
                JPanel panel = new JPanel(new GridBagLayout());
                panel.setBackground(new Color(245, 255, 245));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(10, 10, 10, 10);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel titulo = ConfUI.criarTitulo("Login de Administrador",25);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                panel.add(titulo, gbc);

                // Campo Usuário.
                JLabel lblUser = new JLabel("Usuário:");
                lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                lblUser.setForeground(new Color(50, 50, 50));
                gbc.gridy = 1;
                gbc.gridwidth = 1;
                panel.add(lblUser, gbc);

                JTextField txtUser = new JTextField(15);
                gbc.gridx = 1;
                panel.add(txtUser, gbc);

                // Campo Senha (JPasswordField para ocultar a entrada).
                JLabel lblSenha = new JLabel("Senha:");
                lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                lblSenha.setForeground(new Color(50, 50, 50));
                gbc.gridx = 0;
                gbc.gridy = 2;
                panel.add(lblSenha, gbc);

                JPasswordField txtSenha = new JPasswordField(15);
                gbc.gridx = 1;
                panel.add(txtSenha, gbc);

                // Botão de Login.
                JButton btnLogin = new JButton("Entrar");

                // Estilo customizado (diferente do ConfUI.criarBotao) para destaque.
                btnLogin.setBackground(new Color(120, 200, 120));
                btnLogin.setForeground(Color.BLACK);
                btnLogin.setFocusPainted(false);
                btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnLogin.setPreferredSize(new Dimension(120, 35));

                // Botão de Cancelar.
                JButton btnCancelar = new JButton("Cancelar");
                btnCancelar.setBackground(new Color(200, 200, 200));
                btnCancelar.setForeground(Color.BLACK);
                btnCancelar.setFocusPainted(false);
                btnCancelar.setFont(new Font("Segoe UI", Font.BOLD, 14));
                btnCancelar.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnCancelar.setPreferredSize(new Dimension(120, 35));

                // Painel para agrupar os botões no rodapé.
                JPanel botoesPanel = new JPanel();
                botoesPanel.setBackground(new Color(245, 255, 245));
                botoesPanel.add(btnLogin);
                botoesPanel.add(btnCancelar);

                gbc.gridx = 0;
                gbc.gridy = 3;
                gbc.gridwidth = 2;
                panel.add(botoesPanel, gbc);

                // Listener para o botão 'Entrar'.
                btnLogin.addActionListener(event -> {
                    String usuario = txtUser.getText();
                    // Converte a senha de char[] para String
                    String senha = new String(txtSenha.getPassword());

                    // Lógica de Autenticação (usuário/senha fixos).
                    if  ("admin".equals(usuario) && "acesso741!".equals(senha))
                        {
                            // Login bem-sucedido: abre o Painel Admin e fecha o frame de login.
                            PainelAdmin.requisicoesAdmin();
                            frame.dispose();
                        }
                    else
                        {
                            // Login falhou: exibe mensagem de erro e limpa os campos.
                            JOptionPane.showMessageDialog(frame, "Usuário ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                            txtUser.setText("");
                            txtSenha.setText("");
                            txtUser.requestFocus(); // Volta o foco para o campo de usuário.
                        }
                });

                // Listener para o botão 'Cancelar'.
                btnCancelar.addActionListener(event -> {
                    // Fecha a tela de login e reexibe o menu principal.
                    frame.dispose();
                    Menu.menuInicial();
                });

                frame.add(panel);
                frame.setVisible(true);
            }
    }