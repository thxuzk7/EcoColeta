//Importação de classes necessárias
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor
    {
        private static final int PORTA = 8080; // Porta onde o servidor vai rodar

        public static void main(String[] args)
            {
                List<PontoDeColeta> pontos = new ArrayList<>(); // Lista que armazena os pontos de coleta

                try (ServerSocket server = new ServerSocket(PORTA))
                    {
                        while(true) // Sempre pronto para aceitar conexões
                            {
                                Socket cliente = server.accept(); // Espera até que um cliente se conecte

                                // Canais de comunicação com o cliente
                                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                                PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true);

                                String requisicao = entrada.readLine(); // Lê a requisição enviada pelo cliente

                                if  (requisicao.equals("listar"))
                                    {
                                        if  (pontos.isEmpty())
                                            {
                                                saida.println("null");
                                            }
                                        else
                                            {
                                                for (PontoDeColeta p : pontos)
                                                    {
                                                        saida.println(p); // envia ponto por ponto
                                                    }
                                                saida.println("FIM"); // fim da lista
                                            }
                                    }
                                else if(requisicao.startsWith("adicionar"))
                                    {
                                        String dados = requisicao.substring("adicionar".length()).trim(); // remove a palavra "adicionar" da string
                                        String[] partes = dados.split(";"); // separa os campos do ponto por ";"

                                        // verifica se tem 7 campos (sem materiais) ou 8 campos (com materiais)
                                        if  (partes.length >= 7)
                                            {
                                                ArrayList<String> materiais = new ArrayList<>(); // lista para os materiais do ponto

                                                if  (partes.length == 8)
                                                    {
                                                        // se tiver o 8º campo, ele contém os materiais separados por vírgula
                                                        String[] mats = partes[7].split(",");

                                                        for (String m : mats)
                                                            {
                                                                materiais.add(m.trim()); // adiciona cada material na lista
                                                            }
                                                    }

                                                // Cria o ponto com materiais (pode estar vazio se não foi informado)
                                                PontoDeColeta ponto = new PontoDeColeta(
                                                        partes[0], partes[1], partes[2],
                                                        partes[3], partes[4], partes[5], partes[6],
                                                        materiais
                                                );

                                                pontos.add(ponto); // adiciona o ponto na lista do servidor
                                            }
                                    }
                                else if (requisicao.startsWith("editar"))
                                    {
                                        String dados = requisicao.substring("editar".length()); // remove a palavra "editar"
                                        int indexDoSeparador = dados.indexOf(':'); // encontra o separador entre ponto antigo e novo

                                        String ponto = dados.substring(0, indexDoSeparador); // pega dados do ponto antigo
                                        String pontoEditado = dados.substring(indexDoSeparador + 1); // pega dados do ponto novo

                                        PontoDeColeta pontoAntigo = getPontoDeColeta(ponto); // cria objeto do ponto antigo
                                        PontoDeColeta pontoNovo = getPontoDeColeta(pontoEditado); // cria objeto do ponto novo

                                        for (int i = 0; i < pontos.size(); i++)
                                            {
                                                if  (pontos.get(i).toString().equals(pontoAntigo.toString())) // encontra o ponto antigo na lista
                                                    {
                                                        pontos.set(i, pontoNovo); // substitui pelo ponto editado
                                                        saida.println("success"); // confirma edição
                                                        break; // sai do loop
                                                    }
                                            }
                                    }
                                else if (requisicao.startsWith("excluir"))
                                    {
                                        String dados = requisicao.substring("excluir".length()).trim(); // remove a palavra "excluir"

                                        for (int i = 0; i < pontos.size(); i++)
                                            {
                                                if  (pontos.get(i).toString().equals(dados)) // verifica se o ponto existe
                                                    {
                                                        pontos.remove(i); // remove da lista
                                                        saida.println("success"); // confirma exclusão
                                                        break; // sai do loop
                                                    }
                                            }
                                    }
                            }
                    }
                catch (IOException e)
                    {
                        // Mostra mensagem de erro caso não consiga iniciar o servidor
                        JOptionPane.showMessageDialog(null,
                                "Não foi possível iniciar o servidor.\nErro: " + e.getMessage(),
                                "Erro de Servidor", JOptionPane.ERROR_MESSAGE);
                    }
            }

            private static PontoDeColeta getPontoDeColeta(String ponto)
                {
                    String[] dadosPonto = ponto.split(";"); // separa os campos do ponto
                    ArrayList<String> materiais = new ArrayList<>(); // lista de materiais

                    if  (dadosPonto.length == 8) // se houver materiais
                        {
                            String[] mats = dadosPonto[7].split(","); // separa por vírgula
                            for (String m : mats)
                                {
                                    materiais.add(m.trim()); // adiciona cada material
                                }
                        }

                    // Retorna novo objeto PontoDeColeta com todos os campos
                    return new PontoDeColeta(
                            dadosPonto[0], dadosPonto[1], dadosPonto[2],
                            dadosPonto[3], dadosPonto[4], dadosPonto[5],
                            dadosPonto[6], materiais
                    );
                }
    }