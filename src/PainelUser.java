// Importação de classes necessárias
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PainelUser
    {
        public static void requisicoesUser()
            {
                try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    }
                catch (Exception e)
                    {
                        JOptionPane.showMessageDialog(null,"Erro: Não foi possível trocar o visual padrão do swing.");
                    }

                // Janela principal
                JFrame frame = new JFrame("EcoColeta - Painel do Usuário");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 400);
                frame.setLocationRelativeTo(null);
                frame.getContentPane().setBackground(new Color(245, 255, 245));

                // Painel principal vertical
                JPanel panelMain = new JPanel();
                panelMain.setBackground(new Color(245, 255, 245));
                panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

                // Título
                JLabel titulo = new JLabel("Painel do Usuário");
                titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
                titulo.setForeground(new Color(0, 100, 0));
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Subtítulo
                JLabel subtitulo = new JLabel("Escolha uma opção para continuar");
                subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                subtitulo.setForeground(new Color(50, 50, 50));
                subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

                panelMain.add(Box.createRigidArea(new Dimension(0, 20)));
                panelMain.add(titulo);
                panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
                panelMain.add(subtitulo);
                panelMain.add(Box.createRigidArea(new Dimension(0, 30)));

                // Botão Listar Pontos
                JButton listar = new JButton("Listar Pontos de Coleta");
                listar.setBackground(new Color(120, 200, 120));
                listar.setForeground(Color.BLACK);
                listar.setFocusPainted(false);
                listar.setFont(new Font("Segoe UI", Font.BOLD, 16));
                listar.setAlignmentX(Component.CENTER_ALIGNMENT);
                listar.setCursor(new Cursor(Cursor.HAND_CURSOR));
                listar.setMaximumSize(new Dimension(250, 40));
                panelMain.add(listar);
                panelMain.add(Box.createRigidArea(new Dimension(0, 15)));

                // Botão Encerrar Programa
                JButton encerrarProg = new JButton("Encerrar Programa");
                encerrarProg.setBackground(new Color(200, 120, 120));
                encerrarProg.setForeground(Color.BLACK);
                encerrarProg.setFocusPainted(false);
                encerrarProg.setFont(new Font("Segoe UI", Font.BOLD, 16));
                encerrarProg.setAlignmentX(Component.CENTER_ALIGNMENT);
                encerrarProg.setCursor(new Cursor(Cursor.HAND_CURSOR));
                encerrarProg.setMaximumSize(new Dimension(250, 40));
                panelMain.add(encerrarProg);

                // Ação do botão Encerrar
                encerrarProg.addActionListener(e -> System.exit(0));

                // Ação do botão Listar Pontos
                listar.addActionListener(e -> {
                    frame.dispose();
                    ArrayList<PontoDeColeta> pontos = PontoDeColeta.listarPontos(); //carrega pontos armazenados no servidor usando metodo "listarPontos"

                    // Janela da lista
                    JFrame listarPontos = new JFrame("Lista de Pontos");
                    listarPontos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    listarPontos.setSize(500, 450);
                    listarPontos.setLocationRelativeTo(null);

                    JPanel painelPrincipal = new JPanel(new BorderLayout());

                    // Painel de lista
                    JPanel panel = new JPanel();
                    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                    panel.setBackground(new Color(245, 255, 245));

                    // Título da lista
                    JLabel tituloLista = new JLabel("Pontos de Coleta");
                    tituloLista.setFont(new Font("Segoe UI", Font.BOLD, 24));
                    tituloLista.setForeground(new Color(0, 100, 0));
                    tituloLista.setAlignmentX(Component.CENTER_ALIGNMENT);

                    panel.add(Box.createRigidArea(new Dimension(0, 20)));
                    panel.add(tituloLista);
                    panel.add(Box.createRigidArea(new Dimension(0, 15)));

                    // Adiciona cada ponto com botão de detalhes
                    for (PontoDeColeta ponto : pontos) //percorre lista de pontos
                        {
                            JPanel pontoPanel = new JPanel();
                            pontoPanel.setLayout(new BoxLayout(pontoPanel, BoxLayout.Y_AXIS));
                            pontoPanel.setBackground(new Color(245, 255, 245));
                            pontoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                            pontoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //cria um texto para cada ponto com seu ponto de referência
                            JLabel lblPonto = new JLabel(ponto.pontoDeRef);
                            lblPonto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                            lblPonto.setAlignmentX(Component.CENTER_ALIGNMENT);

                            //cria um botão para cada ponto com a função de ver detalhes
                            JButton verPonto = new JButton("Ver Ponto");
                            verPonto.setPreferredSize(new Dimension(100, 30));
                            verPonto.setAlignmentX(Component.CENTER_ALIGNMENT);
                            verPonto.setBackground(new Color(120, 200, 120));
                            verPonto.setForeground(Color.BLACK);
                            verPonto.setFocusPainted(false);
                            verPonto.setFont(new Font("Segoe UI", Font.BOLD, 12));
                            verPonto.setCursor(new Cursor(Cursor.HAND_CURSOR));

                            verPonto.addActionListener(e1 -> {
                                String detalhes = "Rua: " + ponto.rua + "\n"
                                        + "Número: " + ponto.numero + "\n"
                                        + "Ponto de Referência: " + ponto.pontoDeRef + "\n"
                                        + "Bairro: " + ponto.bairro + "\n"
                                        + "Cidade: " + ponto.cidade + "\n"
                                        + "UF: " + ponto.uF + "\n"
                                        + "CEP: " + ponto.cEP;
                                JOptionPane.showMessageDialog(listarPontos, detalhes, "Detalhes do Ponto", JOptionPane.INFORMATION_MESSAGE);
                            });

                            pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                            pontoPanel.add(lblPonto);
                            pontoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                            pontoPanel.add(verPonto);
                            pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                            panel.add(pontoPanel);
                            panel.add(Box.createRigidArea(new Dimension(0, 10)));
                        }

                    JScrollPane scroll = new JScrollPane(panel);
                    scroll.setBorder(BorderFactory.createEmptyBorder());
                    painelPrincipal.add(scroll, BorderLayout.CENTER);

                    // Botão Fechar no rodapé
                    JButton fechar = new JButton("Fechar");
                    fechar.setBackground(new Color(200, 120, 120));
                    fechar.setForeground(Color.BLACK);
                    fechar.setFocusPainted(false);
                    fechar.setFont(new Font("Segoe UI", Font.BOLD, 14));
                    fechar.setPreferredSize(new Dimension(100, 35));
                    fechar.setCursor(new Cursor(Cursor.HAND_CURSOR));

                    fechar.addActionListener(e2 -> {
                        listarPontos.dispose();
                        frame.setVisible(true);
                    });

                    JPanel painelRodape = new JPanel();
                    painelRodape.setBackground(new Color(245, 255, 245));
                    painelRodape.add(fechar);
                    painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

                    listarPontos.setContentPane(painelPrincipal);
                    if(!pontos.isEmpty())
                        {
                            listarPontos.setVisible(true);
                        }
                    else
                        {
                            frame.setVisible(true);
                        }
                });

                frame.setContentPane(panelMain);
                frame.setVisible(true);
            }
    }