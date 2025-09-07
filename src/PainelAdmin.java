// Importação de classes necessárias
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class PainelAdmin
    {
        public static void requisicoesAdmin()
            {
                try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    }
                catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null,"Erro: Não foi possível trocar o visual padrão do swing.");
                    }

                // Criando a janela
                JFrame frame = new JFrame("EcoColeta - Painel do Administrador");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(500, 400);
                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                frame.getContentPane().setBackground(new Color(245, 255, 245));
                frame.setLocationRelativeTo(null);

                // Título
                JLabel titulo = new JLabel("Painel Administrador");
                titulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
                titulo.setForeground(new Color(0, 100, 0));
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Painel principal vertical
                JPanel panel = new JPanel();
                panel.setBackground(new Color(245, 255, 245));
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

                panel.add(Box.createRigidArea(new Dimension(0, 20)));
                panel.add(titulo);
                panel.add(Box.createRigidArea(new Dimension(0, 30)));

                // Botão Adicionar Ponto
                JButton adicionarPonto = new JButton("Adicionar Ponto de Coleta");
                adicionarPonto.setBackground(new Color(120, 200, 120));
                adicionarPonto.setForeground(Color.BLACK);
                adicionarPonto.setFocusPainted(false);
                adicionarPonto.setFont(new Font("Segoe UI", Font.BOLD, 16));
                adicionarPonto.setAlignmentX(Component.CENTER_ALIGNMENT);
                adicionarPonto.setCursor(new Cursor(Cursor.HAND_CURSOR));
                adicionarPonto.setMaximumSize(new Dimension(250, 40));
                panel.add(adicionarPonto);
                panel.add(Box.createRigidArea(new Dimension(0, 15)));

                adicionarPonto.addActionListener(e -> {
                    frame.dispose();
                    PontoDeColeta ponto = PontoDeColeta.pedirDadosDePonto(); //chama o metodo que solicita os dados do ponto ao usuário e retorna um objeto PontoDeColeta com essas informações

                    try (Socket socket = new Socket("localhost", 8080); //cria uma conexão com o servidor local na porta 8080
                         BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream())); //para ler respostas do servidor
                         PrintWriter saida = new PrintWriter(socket.getOutputStream(), true) //para enviar dados ao servidor, auto-flush ativado
                        )
                            {
                                saida.println("adicionar" + ponto); //envia a requisição ao servidor para adicionar o ponto (formato: "adicionarponto")
                                String resposta = entrada.readLine();
                                JOptionPane.showMessageDialog(frame, resposta, "Servidor", JOptionPane.INFORMATION_MESSAGE); //mostra resposta do servidor
                                frame.setVisible(true); //abre novamente painel de usuário
                            }
                    catch(IOException ex)
                            {
                                JOptionPane.showMessageDialog(frame, "Erro ao conectar com o servidor!", "Erro", JOptionPane.ERROR_MESSAGE);
                            }
                });

                // Botão Editar Ponto
                JButton editarPonto = new JButton("Editar Ponto de Coleta");
                editarPonto.setBackground(new Color(120, 200, 120));
                editarPonto.setForeground(Color.BLACK);
                editarPonto.setFocusPainted(false);
                editarPonto.setFont(new Font("Segoe UI", Font.BOLD, 16));
                editarPonto.setAlignmentX(Component.CENTER_ALIGNMENT);
                editarPonto.setCursor(new Cursor(Cursor.HAND_CURSOR));
                editarPonto.setMaximumSize(new Dimension(250, 40));
                panel.add(editarPonto);
                panel.add(Box.createRigidArea(new Dimension(0, 15)));

                editarPonto.addActionListener(e -> {
                    frame.dispose();
                    ArrayList<PontoDeColeta> pontos = PontoDeColeta.listarPontos(); //carrega pontos armazenados no servidor usando metodo "listarPontos"

                    if (!pontos.isEmpty()) //verifíca se a lista de pontos está vazia
                        {
                            //JFrame usado para mostrar opções de pontos
                            JFrame listarPontos = new JFrame("Lista de Pontos");
                            listarPontos.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            listarPontos.setSize(500, 450);
                            listarPontos.setLocationRelativeTo(null);

                            JPanel painelPrincipal = new JPanel(new BorderLayout());

                            JPanel painelSecundario = new JPanel();
                            painelSecundario.setLayout(new BoxLayout(painelSecundario, BoxLayout.Y_AXIS));
                            painelSecundario.setBackground(new Color(245, 255, 245));

                            JLabel tituloLista = new JLabel("Lista de Pontos");
                            tituloLista.setFont(new Font("Segoe UI", Font.BOLD, 24));
                            tituloLista.setForeground(new Color(0, 100, 0));
                            tituloLista.setAlignmentX(Component.CENTER_ALIGNMENT);
                            painelSecundario.add(Box.createRigidArea(new Dimension(0, 20)));
                            painelSecundario.add(tituloLista);
                            painelSecundario.add(Box.createRigidArea(new Dimension(0, 15)));

                            for (PontoDeColeta ponto : pontos) //percorre lista de pontos
                                {
                                    JPanel pontoPanel = new JPanel();
                                    pontoPanel.setLayout(new BoxLayout(pontoPanel, BoxLayout.Y_AXIS));
                                    pontoPanel.setBackground(new Color(245, 255, 245));
                                    pontoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                                    pontoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                                    //cria um texto para cada ponto com seu ponto de referência
                                    JLabel pontoDeRef = new JLabel(ponto.pontoDeRef);
                                    pontoDeRef.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                                    pontoDeRef.setAlignmentX(Component.CENTER_ALIGNMENT);

                                    //cria um botão para cada ponto com a função de edita-lô
                                    JButton editar = new JButton("Editar Ponto");
                                    editar.setPreferredSize(new Dimension(100, 30));
                                    editar.setAlignmentX(Component.CENTER_ALIGNMENT);
                                    editar.setBackground(new Color(120, 200, 120));
                                    editar.setForeground(Color.BLACK);
                                    editar.setFocusPainted(false);
                                    editar.setFont(new Font("Segoe UI", Font.BOLD, 12));
                                    editar.setCursor(new Cursor(Cursor.HAND_CURSOR));

                                    editar.addActionListener(e1 -> {
                                        try (Socket socket = new Socket("localhost", 8080);
                                             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true)
                                            )
                                                {
                                                    listarPontos.dispose();
                                                    PontoDeColeta pontoEditado = PontoDeColeta.editarDadosDePonto(ponto); //pede para o usuário informar o ponto com as informações editadas
                                                    saida.println("editar" + ponto + ":" + pontoEditado); //envia a requisição ao servidor para editar o ponto (formato: "editarponto:pontoEditado")
                                                    String resposta = entrada.readLine();

                                                    if (resposta.equals("success")) //caso o servidor responda "success"
                                                        {
                                                            JOptionPane.showMessageDialog(listarPontos, "Ponto Alterado"); //é mostrada a mensagem "Ponto Alterado"
                                                            frame.setVisible(true);
                                                        }
                                                    else //caso não
                                                        {
                                                            JOptionPane.showMessageDialog(listarPontos,"Erro: Não foi possível editar o ponto!"); //é mostrada a mensagem "Erro: Não foi possível editar o ponto!"
                                                            frame.setVisible(true);
                                                        }
                                                }
                                        catch (IOException ex)
                                                {
                                                    JOptionPane.showMessageDialog(listarPontos, "Erro ao conectar com o servidor!", "Erro", JOptionPane.ERROR_MESSAGE);
                                                }
                                    });

                                    pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                                    pontoPanel.add(pontoDeRef);
                                    pontoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                                    pontoPanel.add(editar);
                                    pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                                    painelSecundario.add(pontoPanel);
                                    painelSecundario.add(Box.createRigidArea(new Dimension(0, 10)));
                                }

                            JScrollPane scroll = new JScrollPane(painelSecundario);
                            scroll.setBorder(BorderFactory.createEmptyBorder());
                            painelPrincipal.add(scroll, BorderLayout.CENTER);

                            // Botão caso o usuário queira sair da tela sem editar nenhum ponto
                            JButton cancelarEdicao = new JButton("Fechar");
                            cancelarEdicao.setBackground(new Color(200, 120, 120));
                            cancelarEdicao.setForeground(Color.BLACK);
                            cancelarEdicao.setFocusPainted(false);
                            cancelarEdicao.setFont(new Font("Segoe UI", Font.BOLD, 14));
                            cancelarEdicao.setPreferredSize(new Dimension(100, 35));
                            cancelarEdicao.setCursor(new Cursor(Cursor.HAND_CURSOR));

                            cancelarEdicao.addActionListener(e3 -> {
                                listarPontos.dispose();
                                frame.setVisible(true);
                            });

                            JPanel painelBotao = new JPanel();
                            painelBotao.setBackground(new Color(245, 255, 245));
                            painelBotao.add(cancelarEdicao);
                            painelPrincipal.add(painelBotao, BorderLayout.SOUTH);

                            listarPontos.setContentPane(painelPrincipal);
                            listarPontos.setVisible(true);
                        }
                    else
                        {
                            frame.setVisible(true);
                        }
                });

                // Botão Excluir Ponto
                JButton excluirPonto = new JButton("Excluir Ponto de Coleta");
                excluirPonto.setBackground(new Color(120, 200, 120));
                excluirPonto.setForeground(Color.BLACK);
                excluirPonto.setFocusPainted(false);
                excluirPonto.setFont(new Font("Segoe UI", Font.BOLD, 16));
                excluirPonto.setAlignmentX(Component.CENTER_ALIGNMENT);
                excluirPonto.setCursor(new Cursor(Cursor.HAND_CURSOR));
                excluirPonto.setMaximumSize(new Dimension(250, 40));
                panel.add(excluirPonto);
                panel.add(Box.createRigidArea(new Dimension(0, 15)));

                excluirPonto.addActionListener(e -> {
                    frame.dispose();
                    ArrayList<PontoDeColeta> pontos = PontoDeColeta.listarPontos(); //carrega pontos armazenados no servidor usando metodo "listarPontos"

                    if  (!pontos.isEmpty()) //verifíca se a lista de pontos está vazia
                        {
                            //JFrame usado para mostrar opções de pontos
                            JFrame listarPontos = new JFrame("Lista de Pontos");
                            listarPontos.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
                            listarPontos.setSize(500, 450);
                            listarPontos.setLocationRelativeTo(null);

                            JPanel painelPrincipal = new JPanel(new BorderLayout());

                            JPanel painelSecundario = new JPanel();
                            painelSecundario.setLayout(new BoxLayout(painelSecundario, BoxLayout.Y_AXIS));
                            painelSecundario.setBackground(new Color(245, 255, 245));

                            JLabel tituloLista = new JLabel("Lista de Pontos");
                            tituloLista.setFont(new Font("Segoe UI", Font.BOLD, 24));
                            tituloLista.setForeground(new Color(0, 100, 0));
                            tituloLista.setAlignmentX(Component.CENTER_ALIGNMENT);
                            painelSecundario.add(Box.createRigidArea(new Dimension(0, 20)));
                            painelSecundario.add(tituloLista);
                            painelSecundario.add(Box.createRigidArea(new Dimension(0, 15)));

                            for (PontoDeColeta ponto : pontos) //percorre lista de pontos
                                {
                                    JPanel pontoPanel = new JPanel();
                                    pontoPanel.setLayout(new BoxLayout(pontoPanel, BoxLayout.Y_AXIS));
                                    pontoPanel.setBackground(new Color(245, 255, 245));
                                    pontoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                                    pontoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                                    //cria um texto para cada ponto com seu ponto de referência
                                    JLabel pontoDeRef = new JLabel(ponto.pontoDeRef);
                                    pontoDeRef.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                                    pontoDeRef.setAlignmentX(Component.CENTER_ALIGNMENT);

                                    //cria um botão para cada ponto com a função de exclui-lô
                                    JButton excluir = new JButton("Excluir Ponto");
                                    excluir.setPreferredSize(new Dimension(100, 30));
                                    excluir.setAlignmentX(Component.CENTER_ALIGNMENT);
                                    excluir.setBackground(new Color(120, 200, 120));
                                    excluir.setForeground(Color.BLACK);
                                    excluir.setFocusPainted(false);
                                    excluir.setFont(new Font("Segoe UI", Font.BOLD, 12));
                                    excluir.setCursor(new Cursor(Cursor.HAND_CURSOR));

                                    excluir.addActionListener(e1 -> {
                                        try (Socket socket = new Socket("localhost", 8080);
                                             BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                                             PrintWriter saida = new PrintWriter(socket.getOutputStream(), true)
                                            )
                                                {
                                                    saida.println("excluir" + ponto); //envia a requisição ao servidor para excluir o ponto (formato: "excluirponto")
                                                    String resposta = entrada.readLine();

                                                    if (resposta.equals("success")) //caso o servidor responda "success"
                                                        {
                                                            JOptionPane.showMessageDialog(listarPontos, "Ponto Excluido");

                                                            // remove visualmente o painel do ponto
                                                            painelSecundario.remove(pontoPanel);
                                                            painelSecundario.revalidate();
                                                            painelSecundario.repaint();
                                                        }
                                                    else //caso não
                                                        {
                                                            JOptionPane.showMessageDialog(listarPontos, "Erro: Não foi possível excluir o ponto!");
                                                        }
                                                }
                                        catch (IOException ex)
                                                {
                                                    JOptionPane.showMessageDialog(listarPontos, "Erro ao conectar com o servidor!", "Erro", JOptionPane.ERROR_MESSAGE);
                                                }
                                    });

                                    pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                                    pontoPanel.add(pontoDeRef);
                                    pontoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                                    pontoPanel.add(excluir);
                                    pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                                    painelSecundario.add(pontoPanel);
                                    painelSecundario.add(Box.createRigidArea(new Dimension(0, 10)));
                                }

                            JScrollPane scroll = new JScrollPane(painelSecundario);
                            scroll.setBorder(BorderFactory.createEmptyBorder());
                            painelPrincipal.add(scroll, BorderLayout.CENTER);

                            // Botão caso o usuário queira sair da tela sem editar nenhum ponto
                            JButton cancelarEdicao = new JButton("Fechar");
                            cancelarEdicao.setBackground(new Color(200, 120, 120));
                            cancelarEdicao.setForeground(Color.BLACK);
                            cancelarEdicao.setFocusPainted(false);
                            cancelarEdicao.setFont(new Font("Segoe UI", Font.BOLD, 14));
                            cancelarEdicao.setPreferredSize(new Dimension(100, 35));
                            cancelarEdicao.setCursor(new Cursor(Cursor.HAND_CURSOR));

                            cancelarEdicao.addActionListener(e3 -> {
                                listarPontos.dispose();
                                frame.setVisible(true);
                            });

                            JPanel painelBotao = new JPanel();
                            painelBotao.setBackground(new Color(245, 255, 245));
                            painelBotao.add(cancelarEdicao);
                            painelPrincipal.add(painelBotao, BorderLayout.SOUTH);

                            listarPontos.setContentPane(painelPrincipal);
                            listarPontos.setVisible(true);
                        }
                    else
                        {
                            frame.setVisible(true);
                        }
                });

                // Botão Encerrar Programa
                JButton encerrarProg = new JButton("Encerrar o Programa");
                encerrarProg.setBackground(new Color(200, 120, 120));
                encerrarProg.setForeground(Color.BLACK);
                encerrarProg.setFocusPainted(false);
                encerrarProg.setFont(new Font("Segoe UI", Font.BOLD, 16));
                encerrarProg.setAlignmentX(Component.CENTER_ALIGNMENT);
                encerrarProg.setCursor(new Cursor(Cursor.HAND_CURSOR));
                encerrarProg.setMaximumSize(new Dimension(250, 40));
                encerrarProg.addActionListener(e -> System.exit(0));
                panel.add(encerrarProg);

                frame.add(panel);
                frame.setVisible(true);
            }
    }