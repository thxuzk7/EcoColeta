//Importação de classes necessárias
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class PontoDeColeta
    {
        //atributos do ponto de coleta
        public String rua;
        public String numero;
        public String pontoDeRef;
        public String bairro;
        public String cidade;
        public String uF;
        public String cEP;
        public ArrayList<String> materiaisAceitos;

        //construtor da classe
        public PontoDeColeta(String rua, String numero, String pontoDeRef,
                             String bairro, String cidade, String uf, String cep,
                             ArrayList<String> materiaisAceitos
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

        public PontoDeColeta(String rua, String numero, String pontoDeRef,
                             String bairro, String cidade, String uf, String cep
                            )
                                {
                                    this(rua, numero, pontoDeRef, bairro, cidade, uf, cep, new ArrayList<>());  //cria lissta vazia
                                }

        //representação do ponto de coleta como string (usada para envio ao servidor)
        @Override
        public String toString()
            {

                return rua + ";" + numero + ";" + pontoDeRef + ";" + bairro + ";" + cidade + ";" + uF + ";" + cEP
                        + ";" + String.join(",", materiaisAceitos);
            }

        //pedir os dados de um novo ponto de coleta
        public static PontoDeColeta pedirDadosDePonto()
            {
                JFrame frame = new JFrame("Adicionar Ponto de Coleta");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 450);
                frame.setLocationRelativeTo(null);

                // Painel principal
                JPanel panel = new JPanel(new GridBagLayout());
                panel.setBackground(new Color(245, 255, 245));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(8, 8, 8, 8);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel titulo = new JLabel("Adicionar Ponto de Coleta");
                titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
                titulo.setForeground(new Color(0, 100, 0));
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                panel.add(titulo, gbc);

                // Campos de entrada de texto
                String[] labels = {"Rua:", "Número:", "Ponto de Referência:", "Bairro:", "Cidade:", "UF:", "CEP:"};
                JTextField[] campos = new JTextField[labels.length];

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
                        campos[i] = txt;
                        gbc.gridx = 1;
                        panel.add(txt, gbc);
                    }

                // Checkboxes para materiais recicláveis
                gbc.gridy = labels.length + 1;
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                JLabel lblMateriais = new JLabel("Materiais aceitos:");
                lblMateriais.setFont(new Font("Segoe UI", Font.BOLD, 14));
                lblMateriais.setForeground(new Color(0, 80, 0));
                panel.add(lblMateriais, gbc);

                JCheckBox plastico = new JCheckBox("Plástico");
                JCheckBox vidro = new JCheckBox("Vidro");
                JCheckBox papel = new JCheckBox("Papel");
                JCheckBox metal = new JCheckBox("Metal");
                JCheckBox organico = new JCheckBox("Orgânico");

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

                // Exibe a janela com OK/Cancelar
                int result = JOptionPane.showConfirmDialog(frame, panel, "Adicionar Ponto de Coleta",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if (result == JOptionPane.OK_OPTION)
                    {
                        // Junta os materiais escolhidos em uma lista
                        ArrayList<String> materiaisAceitos = new ArrayList<>();
                        if (plastico.isSelected()) materiaisAceitos.add("Plástico");
                        if (vidro.isSelected()) materiaisAceitos.add("Vidro");
                        if (papel.isSelected()) materiaisAceitos.add("Papel");
                        if (metal.isSelected()) materiaisAceitos.add("Metal");
                        if (organico.isSelected()) materiaisAceitos.add("Orgânico");

                        // Cria e retorna o ponto de coleta
                        return new PontoDeColeta(
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
                        PainelAdmin.requisicoesAdmin();
                        return null;
                    }
            }

        //editar um ponto de coleta existente
        public static PontoDeColeta editarDadosDePonto(PontoDeColeta ponto)
            {
                JFrame frame = new JFrame("Editar Ponto de Coleta");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 450);
                frame.setLocationRelativeTo(null);

                JPanel panel = new JPanel(new GridBagLayout());
                panel.setBackground(new Color(245, 255, 245));
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(8, 8, 8, 8);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                JLabel titulo = new JLabel("Editar Ponto de Coleta");
                titulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
                titulo.setForeground(new Color(0, 100, 0));
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 2;
                panel.add(titulo, gbc);

                // Campos de texto preenchidos com valores existentes
                String[] labels = {"Rua:", "Número:", "Ponto de Referência:", "Bairro:", "Cidade:", "UF:", "CEP:"};
                JTextField[] campos = new JTextField[labels.length];
                String[] valores = {ponto.rua, ponto.numero, ponto.pontoDeRef, ponto.bairro, ponto.cidade, ponto.uF, ponto.cEP};

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
                        txt.setText(valores[i]);
                        campos[i] = txt;
                        gbc.gridx = 1;
                        panel.add(txt, gbc);
                    }

                // Checkboxes para editar materiais já existentes
                gbc.gridy = labels.length + 1;
                gbc.gridx = 0;
                gbc.gridwidth = 2;
                JLabel lblMateriais = new JLabel("Materiais aceitos:");
                panel.add(lblMateriais, gbc);

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

                int result = JOptionPane.showConfirmDialog(frame, panel, "Editar Ponto de Coleta",JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if  (result == JOptionPane.OK_OPTION)
                    {
                        ArrayList<String> materiaisAceitos = new ArrayList<>();
                        if (plastico.isSelected()) materiaisAceitos.add("Plástico");
                        if (vidro.isSelected()) materiaisAceitos.add("Vidro");
                        if (papel.isSelected()) materiaisAceitos.add("Papel");
                        if (metal.isSelected()) materiaisAceitos.add("Metal");
                        if (organico.isSelected()) materiaisAceitos.add("Orgânico");

                        return new PontoDeColeta(
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
                        return null;
                    }
            }

        //listar todos os pontos armazenados no servidor
        public static ArrayList<PontoDeColeta> listarPontos()
            {
                ArrayList<PontoDeColeta> lista = new ArrayList<>();

                try (Socket server = new Socket("localhost", 8080);
                     BufferedReader entrada = new BufferedReader(new InputStreamReader(server.getInputStream()));
                     PrintWriter saida = new PrintWriter(server.getOutputStream(), true)
                    )
                        {
                            saida.println("listar");
                            String resposta = entrada.readLine();

                            if  ("null".equals(resposta))
                                {
                                    JOptionPane.showMessageDialog(null, "Nenhum ponto cadastrado.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                                    return null;
                                }
                            else
                                {
                                    while (!resposta.equals("FIM"))
                                        {
                                            String partes[] = resposta.split(";");

                                            if  (partes.length == 7)
                                                {
                                                    PontoDeColeta novo = new PontoDeColeta(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6]);
                                                    lista.add(novo);
                                                }
                                            else if(partes.length == 8)
                                                {
                                                    ArrayList<String> materiais = new ArrayList<>();

                                                    if (!partes[7].isEmpty())
                                                        {
                                                            for (String m : partes[7].split(","))
                                                                {
                                                                    materiais.add(m.trim());
                                                                }
                                                        }

                                                    PontoDeColeta novo = new PontoDeColeta(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6], materiais);
                                                    lista.add(novo);
                                                }
                                        }
                                }
                        }
                catch(Exception ex)
                    {
                        JOptionPane.showMessageDialog(null, "Erro: não foi possível se conectar ao servidor.", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                    }

                return lista; //retorna lista de pontos
            }
    }