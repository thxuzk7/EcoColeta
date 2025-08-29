import javax.swing.*;
import java.awt.*;

public class Requisicoes
    {
        public static void requisicoesUser()
            {
                //Tenta deixar a janela com o visual padrão do sistema (Windows, Linux, Mac)
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch(Exception e)
                {
                    e.printStackTrace(); //Se der erro, mostra no console
                }

                //criando janela
                JFrame frame = new JFrame("EcoColeta - User");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400,280);
                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

                //criando titulo
                JLabel titulo = new JLabel("EcoColeta",SwingConstants.CENTER);
                titulo.setFont(new Font("Arial", Font.BOLD, 28));
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

                //criando botões
                JButton listar = new JButton("Listar Pontos De Coleta");
                listar.setBackground(new Color(200,200,200));
                listar.setForeground(Color.BLACK);
                listar.setFocusPainted(false);
                listar.setFont(new Font("Arial", Font.BOLD, 16));
                listar.setAlignmentX(Component.CENTER_ALIGNMENT);

                listar.addActionListener(e -> {
                    JOptionPane.showMessageDialog(frame, "Aqui apareceriam os pontos de coleta."); //JOptionPane.showMessageDialog(...) abre uma janelinha mostrando uma mensagem.
                });

                JButton encerrarProg = new JButton("Encerrar o Programa");
                encerrarProg.setBackground(new Color(200, 200, 200));
                encerrarProg.setForeground(Color.BLACK);
                encerrarProg.setFocusPainted(false);
                encerrarProg.setFont(new Font("Arial", Font.BOLD, 16));
                encerrarProg.setAlignmentX(Component.CENTER_ALIGNMENT);

                encerrarProg.addActionListener(e ->
                {
                    frame.dispose(); //fecha a janela
                });

                //Adicionando espaçamentos e elementos na janela na ordem certa
                frame.add(Box.createRigidArea(new Dimension(0,20)));
                frame.add(titulo);
                frame.add(Box.createRigidArea(new Dimension(0,30)));
                frame.add(listar);
                frame.add(Box.createRigidArea(new Dimension(0,15)));
                frame.add(encerrarProg);

                //Centraliza a janela no meio da tela
                frame.setLocationRelativeTo(null);

                //Torna a janela visível
                frame.setVisible(true);
            }

        public static void requisicoesAdmin()
            {
                try
                {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                }
                catch(Exception e)
                {
                    e.printStackTrace(); //Se der erro, mostra no console
                }

                //criando a janela
                JFrame frame = new JFrame("EcoColeta - Admin");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400,280);
                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

                //criando titulo
                JLabel titulo = new JLabel("EcoColeta", SwingConstants.CENTER);
                titulo.setFont(new Font("Arial", Font.BOLD, 28));
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

                //criando botões
                JButton adicionarPonto = new JButton("Adicionar Ponto De Coleta");
                adicionarPonto.setBackground(new Color(200, 200, 200));
                adicionarPonto.setForeground(Color.BLACK);
                adicionarPonto.setFocusPainted(false);
                adicionarPonto.setFont(new Font("Arial", Font.BOLD, 16));
                adicionarPonto.setAlignmentX(Component.CENTER_ALIGNMENT);

                adicionarPonto.addActionListener(e ->
                    {

                    });

                JButton editarPonto = new JButton("Editar Ponto De Coleta");
                editarPonto.setBackground(new Color(200, 200, 200));
                editarPonto.setForeground(Color.BLACK);
                editarPonto.setFocusPainted(false);
                editarPonto.setFont(new Font("Arial", Font.BOLD, 16));
                editarPonto.setAlignmentX(Component.CENTER_ALIGNMENT);

                editarPonto.addActionListener(e ->
                    {

                    });

                JButton encerrarProg = new JButton("Encerrar o Programa");
                encerrarProg.setBackground(new Color(200, 200, 200));
                encerrarProg.setForeground(Color.BLACK);
                encerrarProg.setFocusPainted(false);
                encerrarProg.setFont(new Font("Arial", Font.BOLD, 16));
                encerrarProg.setAlignmentX(Component.CENTER_ALIGNMENT);

                encerrarProg.addActionListener(e ->
                    {
                        frame.dispose(); //fecha a janela
                    });

                //Adicionando espaçamentos e elementos na janela na ordem certa
                frame.add(Box.createRigidArea(new Dimension(0, 20)));
                frame.add(titulo);
                frame.add(Box.createRigidArea(new Dimension(0, 30)));
                frame.add(adicionarPonto);
                frame.add(Box.createRigidArea(new Dimension(0, 15)));
                frame.add(editarPonto);
                frame.add(Box.createRigidArea(new Dimension(0, 15)));
                frame.add(encerrarProg);

                //Centraliza a janela no meio da tela
                frame.setLocationRelativeTo(null);

                //Torna a janela visível
                frame.setVisible(true);
            }
    }