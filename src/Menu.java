//Importação de classes necessárias
import javax.swing.*;
import java.awt.*;

public class Menu
    {
        //JFrame do menu principal é declarado como variável de classe, permitindo acesso de qualquer parte do código e facilitando o loop do menu
        public static JFrame frame = new JFrame("EcoColeta");

        public static void menuInicial()
            {
                try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); //troca o visual padrão do swing pelo visual do sistema operacional
                    }
                catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null,"Erro: Não foi possível trocar o visual padrão do swing.");
                    }

                // Configurações básicas do JFrame
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(450, 320);
                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                frame.setLocationRelativeTo(null);
                frame.getContentPane().setBackground(new Color(245, 255, 245));

                // Título
                JLabel titulo = new JLabel("EcoColeta", SwingConstants.CENTER);
                titulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
                titulo.setForeground(new Color(0, 100, 0));
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Subtítulo
                JLabel subtitulo = new JLabel("Como deseja acessar o sistema?", SwingConstants.CENTER);
                subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                subtitulo.setForeground(new Color(80, 80, 80));
                subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Botão Administrador
                JButton btnAdmin = new JButton("Administrador");
                btnAdmin.setBackground(new Color(200, 255, 200));
                btnAdmin.setForeground(new Color(0, 100, 0));
                btnAdmin.setFocusPainted(false);
                btnAdmin.setFont(new Font("Segoe UI", Font.BOLD, 16));
                btnAdmin.setPreferredSize(new Dimension(220, 40));
                btnAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnAdmin.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnAdmin.addActionListener(e -> {
                    frame.dispose();
                    Login.loginAdmin(); //chama a função para fazer a verificação das credenciais do admin
                });

                // Botão Usuário
                JButton btnUsuario = new JButton("Usuário Comum");
                btnUsuario.setBackground(new Color(200, 255, 200));
                btnUsuario.setForeground(new Color(0, 100, 0));
                btnUsuario.setFocusPainted(false);
                btnUsuario.setFont(new Font("Segoe UI", Font.BOLD, 16));
                btnUsuario.setPreferredSize(new Dimension(220, 40));
                btnUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
                btnUsuario.setCursor(new Cursor(Cursor.HAND_CURSOR));
                btnUsuario.addActionListener(e -> {
                    frame.dispose();
                    PainelUser.requisicoesUser(); //chama o painel com as requisições de usuário
                });

                //adiciona os componentes ao frame com espaçamento
                frame.add(Box.createRigidArea(new Dimension(0, 20)));
                frame.add(titulo);
                frame.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.add(subtitulo);
                frame.add(Box.createRigidArea(new Dimension(0, 40)));
                frame.add(btnAdmin);
                frame.add(Box.createRigidArea(new Dimension(0, 20)));
                frame.add(btnUsuario);

                frame.setVisible(true); //torna a janela visível
            }
    }