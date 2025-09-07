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

        //construtor da classe
        public PontoDeColeta(String rua, String numero, String pontoDeRef, String bairro, String cidade, String uF, String cEP)
            {
                this.rua = rua;
                this.numero = numero;
                this.pontoDeRef = pontoDeRef;
                this.bairro = bairro;
                this.cidade = cidade;
                this.uF = uF;
                this.cEP = cEP;
            }

        //representação do ponto de coleta como string (usada para envio ao servidor)
        @Override
        public String toString()
            {
                return rua + ";" + numero + ";" + pontoDeRef + ";" + bairro + ";" + cidade + ";" + uF + ";" + cEP;
            }

        public static PontoDeColeta pedirDadosDePonto()
            {
                JFrame frame = new JFrame("Adicionar Ponto de Coleta");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 350);
                frame.setLocationRelativeTo(null);

                // Painel principal com GridBagLayout para melhor controle do espaçamento
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

                // Campos de entrada
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

                //exibe a janela de entrada com botões OK e Cancel
                int result = JOptionPane.showConfirmDialog(frame, panel, "Adicionar Ponto de Coleta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if  (result == JOptionPane.OK_OPTION)
                    {
                        //cria e retorna um novo ponto de coleta com os dados preenchidos
                        return new PontoDeColeta(
                                campos[0].getText(),
                                campos[1].getText(),
                                campos[2].getText(),
                                campos[3].getText(),
                                campos[4].getText(),
                                campos[5].getText(),
                                campos[6].getText()
                        );
                    }
                else
                    {
                        return null; //retorna null se o usuário cancelar
                    }
            }

        //metodo para editar um ponto de coleta existente
        public static PontoDeColeta editarDadosDePonto(PontoDeColeta ponto)
            {
                JFrame frame = new JFrame("Editar Ponto de Coleta");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 350);
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

                // Campos de entrada pré-preenchidos
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

                int result = JOptionPane.showConfirmDialog(frame, panel, "Editar Ponto de Coleta", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

                if  (result == JOptionPane.OK_OPTION)
                    {
                        return new PontoDeColeta(
                                campos[0].getText(),
                                campos[1].getText(),
                                campos[2].getText(),
                                campos[3].getText(),
                                campos[4].getText(),
                                campos[5].getText(),
                                campos[6].getText()
                        );
                    }
                else
                    {
                        return null;
                    }
            }

        //metodo para listar todos os pontos armazenados no servidor
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

                            if  ("null".equals(resposta)) //Mostra mensagem se não houver pontos
                                {
                                    JFrame frame = new JFrame("Pontos");
                                    JOptionPane.showMessageDialog(frame,"Nenhum ponto cadastrado.","Aviso",JOptionPane.INFORMATION_MESSAGE);
                                }
                            else
                                {
                                    while(resposta != null && !resposta.equals("FIM"))
                                        {
                                            String partes[] = resposta.split(";");

                                            if  (partes.length == 7)
                                                {
                                                    PontoDeColeta novo = new PontoDeColeta(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6]);

                                                    lista.add(novo);
                                                }

                                            resposta = entrada.readLine();
                                        }
                                }
                        }
                catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }

                return lista; //retorna lista de pontos
            }
    }