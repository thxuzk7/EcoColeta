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

                //Janela principal do painel administrativo
                JFrame frame = new JFrame("Painel Administrativo");
                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.setSize(500, 450);
                frame.setLocationRelativeTo(null);

                // Panel que guardará titulo,subtitulo, botões...
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBackground(new Color(245, 245, 245));

                // Título
                JLabel titulo = new JLabel("Painel Administrativo", JLabel.CENTER);
                titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
                titulo.setForeground(new Color(0, 100, 0)); // verde escuro
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
                panel.add(Box.createRigidArea(new Dimension(0, 20)));
                panel.add(titulo);
                panel.add(Box.createRigidArea(new Dimension(0, 20)));

                // Botões principais
                JButton adicionar = criarBotao("Adicionar Ponto");
                JButton editar = criarBotao("Editar Ponto");
                JButton excluir = criarBotao("Excluir Ponto");
                JButton encerrar = criarBotao("Encerrar Programa");

                panel.add(adicionar);
                panel.add(Box.createRigidArea(new Dimension(0, 15)));
                panel.add(editar);
                panel.add(Box.createRigidArea(new Dimension(0, 15)));
                panel.add(excluir);
                panel.add(Box.createRigidArea(new Dimension(0, 15)));
                panel.add(encerrar);

                frame.add(panel);
                frame.setVisible(true); //torna janela visivel

                // AÇÕES DOS BOTÕES
                adicionar.addActionListener(e -> {
                    frame.dispose();
                    PontoDeColeta ponto = PontoDeColeta.pedirDadosDePonto();
                    if (ponto != null)
                        {
                            try (Socket socket = new Socket("localhost", 8080))
                                {
                                    PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
                                    saida.println("adicionar" + ponto);
                                    JOptionPane.showMessageDialog(frame, "Ponto adicionado com sucesso!");
                                    frame.setVisible(true);
                                }
                            catch (IOException ex)
                                {
                                    JOptionPane.showMessageDialog(null, "Erro: não foi possível se conectar ao servidor.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
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
                    if (pontos != null)
                        {
                            frame.dispose();
                            abrirListaParaExclusao(frame, pontos);
                        }
                });

                encerrar.addActionListener(e -> System.exit(0));
            }

        private static JButton criarBotao(String texto)
            {
                JButton botao = new JButton(texto);
                botao.setBackground(new Color(200, 255, 200));
                botao.setForeground(new Color(0, 100, 0));
                botao.setFocusPainted(false);
                botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
                botao.setAlignmentX(Component.CENTER_ALIGNMENT);
                botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
                botao.setMaximumSize(new Dimension(250, 40));
                return botao;
            }

        private static void abrirListaParaEdicao(JFrame frame, ArrayList<PontoDeColeta> pontos) {
            // Cria uma nova janela para listar os pontos de coleta
            JFrame listaFrame = new JFrame("Editar Ponto");
            listaFrame.setSize(400, 400);
            listaFrame.setLocationRelativeTo(frame); // Centraliza em relação à janela principal

            // Painel principal que vai armazenar todos os pontos listados
            JPanel listaPanel = new JPanel();
            listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));

            // Percorre todos os pontos recebidos
            for (PontoDeColeta ponto : pontos) {
                // Painel de cada ponto individual
                JPanel pontoPanel = new JPanel();
                pontoPanel.setLayout(new BoxLayout(pontoPanel, BoxLayout.Y_AXIS));
                pontoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                pontoPanel.setBackground(new Color(245, 245, 245));

                // Exibe a referência do ponto (ex.: "Praça Central")
                JLabel pontoDeRef = new JLabel("Ref: " + ponto.pontoDeRef);
                pontoDeRef.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                pontoDeRef.setForeground(new Color(0, 100, 0));
                pontoDeRef.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Exibe os materiais aceitos no ponto (ex.: vidro, plástico, papel...)
                JLabel materiaisLabel = new JLabel("Materiais: " + String.join(", ", ponto.materiaisAceitos));
                materiaisLabel.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                materiaisLabel.setForeground(new Color(0, 0, 0));
                materiaisLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Botão de editar
                JButton editarBtn = new JButton("Editar");
                editarBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                editarBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

                // Quando o usuário clicar no botão "Editar"
                editarBtn.addActionListener(ev -> {
                    // Criamos campos de texto já preenchidos com os dados atuais do ponto
                    JTextField ruaField = new JTextField(ponto.rua, 20);
                    JTextField numeroField = new JTextField(ponto.numero, 5);
                    JTextField bairroField = new JTextField(ponto.bairro, 15);
                    JTextField cidadeField = new JTextField(ponto.cidade, 15);
                    JTextField ufField = new JTextField(ponto.uF, 2);
                    JTextField cepField = new JTextField(ponto.cEP, 9);
                    JTextField refField = new JTextField(ponto.pontoDeRef, 20);

                    // Painel que organiza os campos em forma de formulário
                    JPanel editPanel = new JPanel(new GridLayout(0, 2, 5, 5));
                    editPanel.add(new JLabel("Rua:")); editPanel.add(ruaField);
                    editPanel.add(new JLabel("Número:")); editPanel.add(numeroField);
                    editPanel.add(new JLabel("Bairro:")); editPanel.add(bairroField);
                    editPanel.add(new JLabel("Cidade:")); editPanel.add(cidadeField);
                    editPanel.add(new JLabel("UF:")); editPanel.add(ufField);
                    editPanel.add(new JLabel("CEP:")); editPanel.add(cepField);
                    editPanel.add(new JLabel("Referência:")); editPanel.add(refField);

                    // Abre uma janela de diálogo com os dados já preenchidos
                    int result = JOptionPane.showConfirmDialog(
                            listaFrame, editPanel, "Editar Ponto",
                            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE
                    );

                    // Se o usuário clicar em OK, cria um novo objeto com os valores editados
                    if (result == JOptionPane.OK_OPTION) {
                        PontoDeColeta pontoEditado = new PontoDeColeta(
                                ruaField.getText(),
                                numeroField.getText(),
                                refField.getText(),
                                bairroField.getText(),
                                cidadeField.getText(),
                                ufField.getText(),
                                cepField.getText()
                        );

                        try (Socket socket = new Socket("localhost", 8080)) {
                            // Comunicação com o servidor para enviar o ponto atualizado
                            PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
                            BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                            // Aqui você deve definir um protocolo de comunicação
                            // (exemplo simples: enviar a ação + dados antigos + dados novos)
                            saida.println("editar" + ponto + ":" + pontoEditado);

                            // Recebe a resposta do servidor
                            String resposta = entrada.readLine();

                            // Exibe mensagem de sucesso ou erro conforme resposta
                            if ("success".equals(resposta)) {
                                JOptionPane.showMessageDialog(frame, "Ponto editado com sucesso!");
                                listaFrame.dispose(); // Fecha a janela de listagem
                            } else {
                                JOptionPane.showMessageDialog(frame, "Erro: Não foi possível editar o ponto.");
                            }
                        } catch (IOException ex) {
                            // Caso o servidor não esteja rodando ou a conexão falhe
                            JOptionPane.showMessageDialog(
                                    null,
                                    "Erro: não foi possível se conectar ao servidor.",
                                    "Aviso",
                                    JOptionPane.INFORMATION_MESSAGE
                            );
                        }
                    }
                });

                // Adiciona os elementos ao painel de cada ponto
                pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                pontoPanel.add(pontoDeRef);
                pontoPanel.add(materiaisLabel);
                pontoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                pontoPanel.add(editarBtn);
                pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                // Adiciona o painel do ponto à lista
                listaPanel.add(pontoPanel);
            }

            // Adiciona barra de rolagem caso existam muitos pontos
            JScrollPane scrollPane = new JScrollPane(listaPanel);
            listaFrame.add(scrollPane);
            listaFrame.setVisible(true); // Exibe a janela
        }

        private static void abrirListaParaExclusao(JFrame frame, ArrayList<PontoDeColeta> pontos)
            {
                JFrame listaFrame = new JFrame("Excluir Ponto");
                listaFrame.setSize(400, 400);
                listaFrame.setLocationRelativeTo(frame);

                JPanel listaPanel = new JPanel();
                listaPanel.setLayout(new BoxLayout(listaPanel, BoxLayout.Y_AXIS));

                for (PontoDeColeta ponto : pontos)
                    {
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

                        excluirBtn.addActionListener(ev -> {
                            try (Socket socket = new Socket("localhost", 8080))
                                {
                                    PrintWriter saida = new PrintWriter(socket.getOutputStream(), true);
                                    BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                                    saida.println("excluir" + ponto);
                                    String resposta = entrada.readLine();

                                    if(resposta.equals("success"))
                                        {
                                            JOptionPane.showMessageDialog(frame, "Ponto excluído com sucesso!");
                                            listaFrame.dispose();
                                        }
                                    else{
                                            JOptionPane.showMessageDialog(frame, "Erro: Não foi possível excluír o ponto.");
                                            listaFrame.dispose();
                                        }
                                }
                            catch (IOException ex)
                                {
                                    JOptionPane.showMessageDialog(frame, "Erro: não foi possível excluir o ponto.");
                                }
                        });

                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                        pontoPanel.add(pontoDeRef);
                        pontoPanel.add(materiaisLabel);
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                        pontoPanel.add(excluirBtn);
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                        listaPanel.add(pontoPanel);
                    }

                JScrollPane scrollPane = new JScrollPane(listaPanel);
                listaFrame.add(scrollPane);
                listaFrame.setVisible(true);
            }
    }