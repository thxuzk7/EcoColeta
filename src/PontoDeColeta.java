import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class PontoDeColeta
    {
        public String rua;
        public String numero;
        public String pontoDeRef; // Ponto de referência é o identificador principal no UI.
        public String bairro;
        public String cidade;
        public String uF;
        public String cEP;
        public ArrayList<String> materiaisAceitos;

        //Construtor completo do PontoDeColeta.
        public PontoDeColeta(String rua, String numero, String pontoDeRef, String bairro, String cidade, String uf,
                             String cep, ArrayList<String> materiaisAceitos
                            )
                                {
                                    this.rua = rua;
                                    this.numero = numero;
                                    this.pontoDeRef = pontoDeRef;
                                    this.bairro = bairro;
                                    this.cidade = cidade;
                                    this.uF = uf;
                                    this.cEP = cep;
                                    this.materiaisAceitos = materiaisAceitos;
                                }

        //Construtor simplificado (chama o completo com uma lista de materiais vazia).
        public PontoDeColeta(String rua, String numero, String pontoDeRef,
                             String bairro, String cidade, String uf, String cep
                            )
                                {
                                    this(rua, numero, pontoDeRef, bairro, cidade, uf, cep, new ArrayList<>());
                                }

        /*
         * Serializa o objeto PontoDeColeta para uma string, usando ';' como separador de campos
         * e ',' como separador dos materiais.
         * Este formato é usado para comunicação de dados com o Servidor.
         */
        public String serializar()
            {
                // Concatena todos os campos, incluindo a lista de materiais unida por vírgulas.
                return rua + ";" + numero + ";" + pontoDeRef + ";" + bairro + ";" +
                        cidade + ";" + uF + ";" + cEP + ";" + String.join(",", materiaisAceitos);
            }

        /**
         * Sobrescreve o método toString() para retornar a string serializada.
         * Isso é usado no GerenciadorDeRequisicoes como uma "chave única" para comparação.
         */
        @Override
        public String toString()
            {
                return rua + ";" + numero + ";" + pontoDeRef + ";" + bairro + ";" + cidade + ";" + uF + ";" + cEP
                        + ";" + String.join(",", materiaisAceitos);
            }

        /*
         * Exibe um formulário para o administrador inserir os dados de um novo Ponto de Coleta.
         * Implementa validação básica (campos obrigatórios).
         * @return O objeto PontoDeColeta preenchido, ou null se o usuário cancelar.
         */
        public static PontoDeColeta pedirDadosDePonto()
            {
                PontoDeColeta ponto = null;

                // Loop para garantir que o usuário preencha todos os campos obrigatórios em caso de erro.
                do
                    {
                        // Cria a janela de diálogo para o formulário.
                        JFrame frame = ConfUI.criarFrame("Adicionar Ponto de Coleta", 400, 500);

                        // Configura o painel com GridBagLayout para alinhamento e espaçamento flexíveis.
                        JPanel panel = new JPanel(new GridBagLayout());
                        panel.setBackground(new Color(245, 255, 245));
                        GridBagConstraints gbc = new GridBagConstraints();
                        gbc.insets = new Insets(8, 8, 8, 8); // Espaçamento entre componentes
                        gbc.fill = GridBagConstraints.HORIZONTAL; // Expande horizontalmente.

                        // Título do formulário.
                        JLabel titulo = ConfUI.criarTitulo("Adicionar Ponto de Coleta", 25);
                        gbc.gridx = 0;
                        gbc.gridy = 0;
                        gbc.gridwidth = 2; // Ocupa duas colunas para o título.
                        panel.add(titulo, gbc);

                        // Criação dinâmica de labels e campos de texto para o endereço.
                        String[] labels = {"Rua*", "Número", "Ponto de Referência", "Bairro*", "Cidade*", "UF*", "CEP"};
                        JTextField[] campos = new JTextField[labels.length];

                        for (int i = 0; i < labels.length; i++)
                            {
                                gbc.gridwidth = 1; // Volta a ocupar uma coluna.
                                gbc.gridy = i + 1;

                                JLabel lbl = new JLabel(labels[i]);
                                lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                                lbl.setForeground(new Color(50, 50, 50));
                                gbc.gridx = 0; // Coluna 0 para a Label
                                panel.add(lbl, gbc);

                                JTextField txt = new JTextField(20);
                                campos[i] = txt;
                                gbc.gridx = 1; // Coluna 1 para o Campo de Texto
                                panel.add(txt, gbc);
                            }

                        // Seção de seleção de materiais aceitos.
                        gbc.gridy = labels.length + 1;
                        gbc.gridx = 0;
                        gbc.gridwidth = 2;
                        JLabel lblMateriais = new JLabel("Materiais aceitos*:");
                        lblMateriais.setFont(new Font("Segoe UI", Font.BOLD, 14));
                        lblMateriais.setForeground(new Color(0, 80, 0));
                        panel.add(lblMateriais, gbc);

                        // Checkboxes para seleção de materiais.
                        JCheckBox plastico = new JCheckBox("Plástico");
                        JCheckBox vidro = new JCheckBox("Vidro");
                        JCheckBox papel = new JCheckBox("Papel");
                        JCheckBox metal = new JCheckBox("Metal");
                        JCheckBox organico = new JCheckBox("Orgânico");

                        // Adiciona as checkboxes ao painel, cada uma em uma nova linha.
                        gbc.gridy++;
                        panel.add(plastico, gbc);
                        gbc.gridy++;
                        panel.add(vidro, gbc);
                        gbc.gridy++;
                        panel.add(papel, gbc);
                        gbc.gridy++;
                        panel.add(metal, gbc);
                        gbc.gridy++;
                        panel.add(organico, gbc);

                        // Exibe o painel em um JOptionPane como um diálogo modal (OK/CANCEL).
                        int result = JOptionPane.showConfirmDialog(frame, panel, "Adicionar Ponto de Coleta",
                                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                        if  (result == JOptionPane.OK_OPTION)
                            {
                                // Coleta os materiais selecionados.
                                ArrayList<String> materiaisAceitos = new ArrayList<>();
                                if (plastico.isSelected()) materiaisAceitos.add("Plástico");
                                if (vidro.isSelected()) materiaisAceitos.add("Vidro");
                                if (papel.isSelected()) materiaisAceitos.add("Papel");
                                if (metal.isSelected()) materiaisAceitos.add("Metal");
                                if (organico.isSelected()) materiaisAceitos.add("Orgânico");

                                // Validação de campos obrigatórios (*).
                                // [0]=Rua, [3]=Bairro, [4]=Cidade, [5]=UF
                                if  (campos[0].getText().isBlank() || campos[3].getText().isBlank() || campos[4].getText().isBlank() || campos[5].getText().isBlank())
                                    {
                                        JOptionPane.showMessageDialog(frame,"Preencha todos os campos obrigatórios (*)!","Erro", JOptionPane.ERROR_MESSAGE);
                                        continue; // Volta ao início do loop 'do-while'.
                                    }

                                // Validação de materiais aceitos (pelo menos um).
                                if  (materiaisAceitos.isEmpty())
                                    {
                                        JOptionPane.showMessageDialog(frame,"Selecione pelo menos um material!","Erro", JOptionPane.ERROR_MESSAGE);
                                        continue; // Volta ao início do loop 'do-while'.
                                    }

                                // Cria o objeto PontoDeColeta com os dados inseridos.
                                ponto = new PontoDeColeta(
                                        campos[0].getText(),
                                        campos[1].getText(),
                                        campos[2].getText(),
                                        campos[3].getText(),
                                        campos[4].getText(),
                                        campos[5].getText(),
                                        campos[6].getText(),
                                        materiaisAceitos
                                );
                            }
                        else
                            {
                                // Se o usuário cancelar, retorna ao painel Admin.
                                PainelAdmin.requisicoesAdmin();
                                return null;
                            }
                    }
                while(ponto == null); // Repete enquanto 'ponto' for nulo

                return ponto;
            }

        /*
         * Exibe um formulário preenchido com os dados atuais de um ponto de coleta para edição.
         * Após a edição, envia a requisição "editar" para o servidor.
         */
        public static void editarDadosDePonto(PontoDeColeta ponto)
            {
                // Cria a janela de edição (estrutura similar ao pedirDadosDePonto).
                JFrame frame = ConfUI.criarFrame("Editar Ponto de Coleta", 400, 450);

                JPanel panel = new JPanel(new GridBagLayout());
                panel.setBackground(new Color(245, 255, 245));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(8, 8, 8, 8);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel titulo = ConfUI.criarTitulo("Editar Ponto de Coleta", 25);
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                panel.add(titulo, gbc);

                String[] labels = {"Rua:", "Número:", "Ponto de Referência:", "Bairro:", "Cidade:", "UF:", "CEP:"};
                JTextField[] campos = new JTextField[labels.length];
                // Array com os valores atuais do objeto 'ponto'.
                String[] valores = {ponto.rua, ponto.numero, ponto.pontoDeRef, ponto.bairro, ponto.cidade, ponto.uF, ponto.cEP};

                // Cria os campos de texto e preenche com os dados existentes.
                for (int i = 0; i < labels.length; i++)
                    {
                        gbc.gridwidth = 1;
                        gbc.gridy = i + 1;

                        JLabel lbl = new JLabel(labels[i]);
                        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lbl.setForeground(new Color(50, 50, 50));
                        gbc.gridx = 0;
                        panel.add(lbl, gbc);

                        JTextField txt = new JTextField(20);
                        txt.setText(valores[i]); // Define o texto inicial.
                        campos[i] = txt;
                        gbc.gridx = 1;
                        panel.add(txt, gbc);
                    }

                // Configura as checkboxes para refletir os materiais já aceitos.
                gbc.gridy = labels.length + 1;
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                JLabel lblMateriais = new JLabel("Materiais aceitos:");
                panel.add(lblMateriais, gbc);

                // Checkboxes preenchidas com o status atual do ponto.
                JCheckBox plastico = new JCheckBox("Plástico", ponto.materiaisAceitos.contains("Plástico"));
                JCheckBox vidro = new JCheckBox("Vidro", ponto.materiaisAceitos.contains("Vidro"));
                JCheckBox papel = new JCheckBox("Papel", ponto.materiaisAceitos.contains("Papel"));
                JCheckBox metal = new JCheckBox("Metal", ponto.materiaisAceitos.contains("Metal"));
                JCheckBox organico = new JCheckBox("Orgânico", ponto.materiaisAceitos.contains("Orgânico"));

                gbc.gridy++;
                panel.add(plastico, gbc);
                gbc.gridy++;
                panel.add(vidro, gbc);
                gbc.gridy++;
                panel.add(papel, gbc);
                gbc.gridy++;
                panel.add(metal, gbc);
                gbc.gridy++;
                panel.add(organico, gbc);

                int result = JOptionPane.showConfirmDialog(frame, panel, "Editar Ponto de Coleta",
                        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if  (result == JOptionPane.OK_OPTION)
                    {
                        // Coleta os dados do novo ponto.
                        ArrayList<String> materiaisAceitos = new ArrayList<>();
                        if (plastico.isSelected()) materiaisAceitos.add("Plástico");
                        if (vidro.isSelected()) materiaisAceitos.add("Vidro");
                        if (papel.isSelected()) materiaisAceitos.add("Papel");
                        if (metal.isSelected()) materiaisAceitos.add("Metal");
                        if (organico.isSelected()) materiaisAceitos.add("Orgânico");

                        PontoDeColeta pontoNovo = new PontoDeColeta(
                                campos[0].getText(),
                                campos[1].getText(),
                                campos[2].getText(),
                                campos[3].getText(),
                                campos[4].getText(),
                                campos[5].getText(),
                                campos[6].getText(),
                                materiaisAceitos
                        );

                        // Lógica de comunicação com o servidor para edição.
                        try (Socket server = new Socket("localhost", 8080);
                             BufferedReader entrada = new BufferedReader(new InputStreamReader(server.getInputStream()));
                             PrintWriter saida = new PrintWriter(server.getOutputStream(), true)
                            )
                                {
                                    // Formato da requisição de edição: "editar<ponto_antigo>:<ponto_novo>".
                                    String requisicao = "editar" + ponto.toString() + ":" + pontoNovo.toString();
                                    saida.println(requisicao);

                                    String resposta = entrada.readLine();
                                    if  ("success".equals(resposta))
                                        {
                                            JOptionPane.showMessageDialog(frame, "Ponto editado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                                        }
                                    else
                                        {
                                            JOptionPane.showMessageDialog(frame, "Erro ao editar ponto: " + resposta, "Erro", JOptionPane.ERROR_MESSAGE);
                                        }
                                }
                        // Tratamento de exceção de rede.
                        catch (Exception e)
                                {
                                    JOptionPane.showMessageDialog(frame, "Erro de conexão: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                                }
                    }
                else
                    {
                        JOptionPane.showMessageDialog(frame, "Edição cancelada. Ponto não foi alterado.", "Cancelado", JOptionPane.WARNING_MESSAGE);
                    }
            }

        /**
         * Envia uma requisição "listar" ao servidor e recupera a lista de Pontos de Coleta.
         * @return Uma ArrayList de PontoDeColeta, ou null se não houver pontos ou houver erro de conexão.
         */
        public static ArrayList<PontoDeColeta> listarPontos()
            {
                ArrayList<PontoDeColeta> lista = new ArrayList<>();

                // Lógica de comunicação com o servidor para listar.
                try (Socket server = new Socket("localhost", 8080);
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(server.getInputStream()));
                     PrintWriter saida = new PrintWriter(server.getOutputStream(), true)
                    )
                        {
                            saida.println("listar"); // Envia o comando de listar.
                            String resposta = entrada.readLine(); // Lê a primeira linha de resposta.

                            if  ("null".equals(resposta))
                                {
                                    // Nenhum ponto cadastrado.
                                    JOptionPane.showMessageDialog(null, "Nenhum ponto cadastrado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                                    return null;
                                }
                            else
                                {
                                    // Lê as respostas até encontrar o marcador "FIM".
                                    while (!"FIM".equals(resposta))
                                        {
                                            // Processa a string serializada do ponto.
                                            String partes[] = resposta.split(";");

                                            // Cria o objeto PontoDeColeta a partir das partes.
                                            if  (partes.length == 7) // Construtor sem materiais
                                                {
                                                    PontoDeColeta novo = new PontoDeColeta(
                                                            partes[0], partes[1], partes[2],
                                                            partes[3], partes[4], partes[5], partes[6]
                                                    );
                                                    lista.add(novo);
                                                }
                                            else if (partes.length == 8) // Construtor com materiais
                                                {
                                                    ArrayList<String> materiais = new ArrayList<>();

                                                    if  (!partes[7].isEmpty())
                                                        {
                                                            for (String m : partes[7].split(","))
                                                                {
                                                                    materiais.add(m.trim());
                                                                }
                                                        }

                                                    PontoDeColeta novo = new PontoDeColeta(
                                                            partes[0], partes[1], partes[2],
                                                            partes[3], partes[4], partes[5], partes[6], materiais
                                                    );
                                                    lista.add(novo);
                                                }

                                            resposta = entrada.readLine(); // Lê a próxima linha.
                                        }
                                }
                        }
                // Tratamento de exceção de rede.
                catch(Exception ex)
                        {
                            JOptionPane.showMessageDialog(null, "Erro: não foi possível se conectar ao servidor.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                        }

                return lista;
            }

        public static void exibirlista(JFrame frame)
            {
                // Chama o metodo estático para buscar a lista de pontos no servidor.
                ArrayList<PontoDeColeta> pontos = PontoDeColeta.listarPontos();

                // Verifica se a lista está vazia ou houve erro de conexão (tratado em listarPontos).
                if  (pontos == null || pontos.isEmpty())
                    {
                        frame.setVisible(true);
                        return;

                    }

                // Cria a nova janela para exibir a lista de pontos.
                JFrame listarPontos = new JFrame("Lista de Pontos");
                listarPontos.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                listarPontos.setSize(500, 450);
                listarPontos.setLocationRelativeTo(null);

                JPanel painelPrincipal = new JPanel(new BorderLayout());

                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                panel.setBackground(new Color(245, 255, 245));

                JLabel tituloLista = ConfUI.criarTitulo("Pontos de Coleta", 25);

                panel.add(Box.createRigidArea(new Dimension(0, 20)));
                panel.add(tituloLista);
                panel.add(Box.createRigidArea(new Dimension(0, 15)));

                // Itera sobre a lista de pontos e cria um componente visual para cada um.
                for (PontoDeColeta ponto : pontos)
                    {
                        JPanel pontoPanel = new JPanel();
                        pontoPanel.setLayout(new BoxLayout(pontoPanel, BoxLayout.Y_AXIS));
                        pontoPanel.setBackground(new Color(245, 255, 245));
                        pontoPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
                        pontoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

                        // Exibe o Ponto de Referência e os Materiais Aceitos.
                        JLabel lblPonto = new JLabel(ponto.pontoDeRef);
                        lblPonto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                        lblPonto.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JLabel lblMateriais = new JLabel("Materiais: " + String.join(", ", ponto.materiaisAceitos));
                        lblMateriais.setFont(new Font("Segoe UI", Font.ITALIC, 12));
                        lblMateriais.setForeground(new Color(80, 80, 80));
                        lblMateriais.setAlignmentX(Component.CENTER_ALIGNMENT);

                        JButton verPonto = new JButton("Ver Ponto");
                        verPonto.setPreferredSize(new Dimension(100, 30));
                        verPonto.setAlignmentX(Component.CENTER_ALIGNMENT);

                        // Configurações de estilo específicas para o botão "Ver Ponto".
                        verPonto.setBackground(new Color(120, 200, 120));
                        verPonto.setForeground(Color.BLACK);
                        verPonto.setFocusPainted(false);
                        verPonto.setFont(new Font("Segoe UI", Font.BOLD, 12));
                        verPonto.setCursor(new Cursor(Cursor.HAND_CURSOR));

                        // Listener para o botão 'Ver Ponto'.
                        verPonto.addActionListener(e1 -> {
                            // Constrói a string com todos os detalhes do ponto.
                            String detalhes = "Rua: " + ponto.rua + "\n"
                                    + "Número: " + ponto.numero + "\n"
                                    + "Ponto de Referência: " + ponto.pontoDeRef + "\n"
                                    + "Bairro: " + ponto.bairro + "\n"
                                    + "Cidade: " + ponto.cidade + "\n"
                                    + "UF: " + ponto.uF + "\n"
                                    + "CEP: " + ponto.cEP + "\n"
                                    + "Materiais Aceitos: " + String.join(", ", ponto.materiaisAceitos);
                            // Exibe os detalhes em um JOptionPane (diálogo de informação).
                            JOptionPane.showMessageDialog(listarPontos, detalhes, "Detalhes do Ponto", JOptionPane.INFORMATION_MESSAGE);
                        });

                        // Adiciona componentes ao painel individual do ponto.
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                        pontoPanel.add(lblPonto);
                        pontoPanel.add(lblMateriais);
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
                        pontoPanel.add(verPonto);
                        pontoPanel.add(Box.createRigidArea(new Dimension(0, 10)));

                        panel.add(pontoPanel);
                        panel.add(Box.createRigidArea(new Dimension(0, 10)));
                    }

                // Área de rolagem para a lista.
                JScrollPane scroll = new JScrollPane(panel);
                scroll.setBorder(BorderFactory.createEmptyBorder());
                painelPrincipal.add(scroll, BorderLayout.CENTER);

                // Botão para fechar a lista e voltar ao menu do cidadão.
                JButton fechar = ConfUI.criarBotao("Fechar", 100, 35);

                fechar.addActionListener(e2 -> {
                    listarPontos.dispose(); // Fecha a lista.
                    frame.setVisible(true); // Reabre o painel do cidadão.
                });

                // Painel de rodapé para o botão 'Fechar'.
                JPanel painelRodape = new JPanel();
                painelRodape.setBackground(new Color(245, 255, 245));
                painelRodape.add(fechar);
                painelPrincipal.add(painelRodape, BorderLayout.SOUTH);

                listarPontos.setContentPane(painelPrincipal);
                listarPontos.setVisible(true);
            }
    }