import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class PainelAdmin
    {
        public static void requisicoesAdmin()
            {
                JFrame frame = ConfUI.criarFrame("Painel Administrativo", 450, 380);

                // Painel principal com BorderLayout
                JPanel panelMain = new JPanel(new BorderLayout());
                panelMain.setBackground(new Color(245, 255, 245));

                // Painel central (botões principais)
                JPanel panelCentro = new JPanel();
                panelCentro.setBackground(new Color(245, 255, 245));
                panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

                // Cria Titulo
                JLabel titulo = ConfUI.criarTitulo("Painel Administrativo", 25);

                // Cria Botões principais
                JButton listar = ConfUI.criarBotao("Listar Pontos de Coleta", 250, 40);
                JButton adicionar = ConfUI.criarBotao("Adicionar Ponto de Coleta", 250, 40);
                JButton editar = ConfUI.criarBotao("Editar Ponto de Coleta", 250, 40);
                JButton excluir = ConfUI.criarBotao("Excluir Ponto de Coleta", 250, 40);

                // Cria Botões Deslogar e Encerrar (menor e fixo no rodapé)
                JButton deslogar = ConfUI.criarBotao("Deslogar", 100, 30);
                JButton encerrar = ConfUI.criarBotao("Encerrar Programa", 100, 30);

                // Listeners principais
                listar.addActionListener(e -> PontoDeColeta.exibirlista(frame));

                adicionar.addActionListener(e -> {
                    frame.dispose();
                    PontoDeColeta ponto = PontoDeColeta.pedirDadosDePonto();

                    if  (ponto != null)
                        {
                            try (Socket socket = new Socket("localhost", 8080);
                                 BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                 PrintWriter saida = new PrintWriter(socket.getOutputStream(), true)
                                )
                                    {

                                        saida.println("adicionar " + ponto.toString());
                                        String resposta = entrada.readLine();

                                        if ("success".equals(resposta))
                                            {
                                                JOptionPane.showMessageDialog(frame, "Ponto adicionado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                                PainelAdmin.requisicoesAdmin();
                                            }
                                        else
                                            {
                                                JOptionPane.showMessageDialog(frame, "Erro ao adicionar ponto. Resposta do servidor: " + resposta, "Erro de Comunicação", JOptionPane.ERROR_MESSAGE);
                                                PainelAdmin.requisicoesAdmin();
                                            }
                                    }
                            catch (IOException ex)
                                    {
                                        JOptionPane.showMessageDialog(null, "Erro: não foi possível se conectar ao servidor.\nVerifique se o Servidor.java está em execução.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                                    }
                        }
                });

                editar.addActionListener(e -> {
                    ArrayList<PontoDeColeta> pontos = PontoDeColeta.listarPontos();
                    if  (pontos != null)
                        {
                            frame.dispose();
                            abrirListaParaEdicao(frame, pontos);
                        }
                });

                excluir.addActionListener(e -> {
                    ArrayList<PontoDeColeta> pontos = PontoDeColeta.listarPontos();
                    if  (pontos != null)
                        {
                            frame.dispose();
                            abrirListaParaExclusao(frame, pontos);
                        }
                });

                encerrar.addActionListener(e -> System.exit(0));

                deslogar.addActionListener(e -> {
                    frame.dispose();
                    Menu.menuInicial();
                });

                // Monta painel central
                panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
                panelCentro.add(titulo);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
                panelCentro.add(listar);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
                panelCentro.add(adicionar);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
                panelCentro.add(editar);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
                panelCentro.add(excluir);

                // Painel de rodapé (para o botão deslogar)
                JPanel panelRodape = new JPanel();
                panelRodape.setBackground(new Color(230, 240, 230));
                panelRodape.add(deslogar);
                panelRodape.add(encerrar);

                // Adiciona nos lugares certos do BorderLayout
                panelMain.add(panelCentro, BorderLayout.CENTER);
                panelMain.add(panelRodape, BorderLayout.SOUTH);

                frame.setContentPane(panelMain);
                frame.setVisible(true);
            }

        private static void abrirListaParaEdicao(JFrame parentFrame, ArrayList<PontoDeColeta> pontos)
            {
                JFrame listaFrame = new JFrame("Editar Ponto");
                listaFrame.setSize(400, 400);
                listaFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listaFrame.setLocationRelativeTo(parentFrame);
                listaFrame.setLayout(new BorderLayout());

                JPanel listaPanel = new JPanel();
                // Usa BoxLayout.Y_AXIS para empilhar os painéis de cada ponto verticalmente.
                listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));

                for (PontoDeColeta ponto : pontos)
                    {
                        // Cria um painel individual para cada ponto de coleta.
                        JPanel pontoPanel = new JPanel();
                        pontoPanel.setLayout(new BoxLayout(pontoPanel, BoxLayout.Y_AXIS));
                        pontoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                        pontoPanel.setBackground(new Color(245, 245, 245));

                        // Exibe Ponto de Referência e Materiais.
                        JLabel pontoDeRef = new JLabel("Ref: " + ponto.pontoDeRef);
                        pontoDeRef.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        pontoDeRef.setForeground(new Color(0, 100, 0));
                        pontoDeRef.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel materiaisLabel = new JLabel("Materiais: " + String.join(", ", ponto.materiaisAceitos));
                        materiaisLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                        materiaisLabel.setForeground(new Color(0, 0, 0));
                        materiaisLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JButton editarBtn = new JButton("Editar");
                        editarBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        editarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

                        // Listener para o botão 'Editar'.
                        editarBtn.addActionListener(ev -> {
                            listaFrame.dispose(); // Fecha a lista.
                            // Abre a tela de edição.
                            PontoDeColeta.editarDadosDePonto(ponto);

                            PainelAdmin.requisicoesAdmin();
                        });

                        // Adiciona os componentes ao painel do ponto.
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                        pontoPanel.add(pontoDeRef);
                        pontoPanel.add(materiaisLabel);
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                        pontoPanel.add(editarBtn);
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                        listaPanel.add(pontoPanel);
                        listaPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Espaçamento entre os pontos
                    }

                // Cria e adiciona o JScrollPane e o posiciona no centro.
                JScrollPane scrollPane = new JScrollPane(listaPanel);
                listaFrame.add(scrollPane, BorderLayout.CENTER);

                // Botão de voltar para a tela principal do administrador.
                JButton btnVoltar = ConfUI.criarBotao("Voltar", 250, 40);

                btnVoltar.addActionListener(e -> {
                    listaFrame.dispose();
                    parentFrame.setVisible(true);
                });

                // Painel para conter o botão 'Voltar' no rodapé.
                JPanel botaoPanel = new JPanel();
                botaoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
                botaoPanel.add(btnVoltar);

                // Adiciona o painel de botões no rodapé.
                listaFrame.add(botaoPanel, BorderLayout.SOUTH);

                // Exibe o frame.
                listaFrame.setVisible(true);
            }

        private static void abrirListaParaExclusao(JFrame frame, ArrayList<PontoDeColeta> pontos) //Cria uma nova janela que lista os pontos de coleta, com um botão de 'Excluir' para cada um.
            {
                JFrame listaFrame = new JFrame("Excluir Ponto");
                listaFrame.setSize(400, 400);
                listaFrame.setLocationRelativeTo(frame);
                listaFrame.setLayout(new BorderLayout()); // Usa BorderLayout para posicionar o botão 'Voltar' no rodapé.

                JPanel listaPanel = new JPanel();
                listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));
                listaPanel.setBackground(new Color(245, 245, 245));

                for (PontoDeColeta ponto : pontos)
                    {
                        // Cria o painel individual do ponto (estrutura similar à edição).
                        JPanel pontoPanel = new JPanel();
                        pontoPanel.setLayout(new BoxLayout(pontoPanel, BoxLayout.Y_AXIS));
                        pontoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                        pontoPanel.setBackground(new Color(245, 245, 245));

                        JLabel pontoDeRef = new JLabel("Ref: " + ponto.pontoDeRef);
                        pontoDeRef.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        pontoDeRef.setForeground(new Color(0, 100, 0));
                        pontoDeRef.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel materiaisLabel = new JLabel("Materiais: " + String.join(", ", ponto.materiaisAceitos));
                        materiaisLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                        materiaisLabel.setForeground(new Color(0, 0, 0));
                        materiaisLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JButton excluirBtn = new JButton("Excluir");
                        excluirBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                        excluirBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

                        // Listener para o botão 'Excluir'.
                        excluirBtn.addActionListener(ev -> {
                            // Comunicação com o servidor para exclusão.
                            try (Socket socket = new Socket("localhost", 8080))
                                {
                                    PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
                                    BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                                    // Envia a requisição: "excluir " + ponto serializado (toString()).
                                    saida.println("excluir " + ponto);
                                    String resposta = entrada.readLine();

                                    if ("success".equals(resposta))
                                        {
                                            JOptionPane.showMessageDialog(frame, "Ponto excluído com sucesso!");
                                            // Remove o painel do ponto da interface gráfica imediatamente.
                                            listaPanel.remove(pontoPanel);
                                            // Força a atualização da interface.
                                            listaPanel.revalidate();
                                            listaPanel.repaint();
                                        }
                                    else
                                        {
                                            JOptionPane.showMessageDialog(frame, "Erro: Não foi possível excluir o ponto.");
                                        }
                                }
                            catch (IOException ex)
                                {
                                    JOptionPane.showMessageDialog(frame, "Erro: não foi possível excluir o ponto.");
                                    ex.printStackTrace();
                                }
                        });

                        // Adiciona os componentes ao painel do ponto.
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                        pontoPanel.add(pontoDeRef);
                        pontoPanel.add(materiaisLabel);
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                        pontoPanel.add(excluirBtn);
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                        listaPanel.add(pontoPanel);
                    }

            // Área de rolagem para a lista de pontos.
            JScrollPane scrollPane = new JScrollPane(listaPanel);
            listaFrame.add(scrollPane, BorderLayout.CENTER);

            // Botão de voltar para a tela principal do administrador.
            JButton btnVoltar = ConfUI.criarBotao("Voltar", 250, 40);

            btnVoltar.addActionListener(e -> {
                listaFrame.dispose();
                frame.setVisible(true); // Reabre o painel administrativo.
            });

            // Painel para conter o botão 'Voltar' no rodapé.
            JPanel botaoPanel = new JPanel();
            botaoPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            botaoPanel.add(btnVoltar);
            listaFrame.add(botaoPanel, BorderLayout.SOUTH);

            listaFrame.setVisible(true);
        }
    }