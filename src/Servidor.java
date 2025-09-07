//Importação de classes necessárias
import javax.swing.*;
import  java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class Servidor
    {
        private static final int PORTA = 8080; //Porta onde o servidor vai rodar

        public static void main(String[] args)
            {
                List<PontoDeColeta> pontos = new ArrayList<>(); //ArrayList que armazena os pontos de coleta

                try (ServerSocket server = new ServerSocket(PORTA))
                    {
                        while(true) //sempre pronto para aceitar conexões.
                            {
                                Socket cliente = server.accept(); //Espera até que um cliente se conecte. Retorna um objeto Socket representando essa conexão específica.

                                //cria os canais de comunicação com o cliente
                                BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream())); //"entrada" recebe dados do cliente
                                PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true); //"saida" envia dados ao cliente

                                String requisicao = entrada.readLine(); //lê as requisições enviada pelo cliente

                                if  (requisicao.equals("listar"))
                                    {
                                        if  (pontos.isEmpty()) //verifica se a lista esta vazia
                                            {
                                                saida.println("null");
                                            }
                                        else
                                            {
                                                for (PontoDeColeta p : pontos) //percorre toda a lista de pontos
                                                    {
                                                        saida.println(p); //envia cada ponto ao cliente sequencialmente
                                                    }

                                                saida.println("FIM"); //após enviar todos os pontos, envia "FIM" para indicar o final da lista
                                            }
                                    }
                                else if(requisicao.startsWith("adicionar")) //verifica se a requisição recebida começa com "adicionar"
                                    {
                                        String dados = requisicao.substring("adicionar".length()); //retira "adicionar" do começo da requisição, ficando somente o ponto
                                        String[] partes = dados.split(";"); //split divide o ponto em partes usando o delimitador ";"
                                        
                                        if  (partes.length == 7)
                                            {
                                                PontoDeColeta ponto = new PontoDeColeta(partes[0], partes[1], partes[2], partes[3], partes[4], partes[5], partes[6]); 
                                                pontos.add(ponto);
                                                
                                                saida.println("Ponto adicionado com sucesso!"); //mensagem mostrada ao cliente caso ponto seja adicionado
                                            }
                                        else
                                            {
                                                saida.println("Erro: ponto de coleta não adicionado."); //mensagem mostrada ao cliente caso ponto não seja adicionado
                                            }
                                    }
                                else if(requisicao.startsWith("editar")) //verifica se a requisição recebida começa com "editar"
                                    {
                                        String dados = requisicao.substring("editar".length()); //retira "editar" do começo da requisição, ficando somente "ponto:pontoEditado"
                                        int indexDoSeparador = dados.indexOf(':'); //guarda o index do carácter ":"
    
                                        String ponto = dados.substring(0, indexDoSeparador); //"ponto" guarda do início da String "dados" até o ':' (ponto)
                                        String pontoEditado = dados.substring(indexDoSeparador + 1); //"pontoEditado" guarda um carácter após ':' até o final (pontoEditado)

                                        PontoDeColeta pontoAntigo = getPontoDeColeta(ponto);
                                        PontoDeColeta pontoNovo = getPontoDeColeta(pontoEditado);

                                        for (int i = 0; i < pontos.size(); i++) //percorre a lista de pontos procurando o ponto antigo
                                            {
                                                if  (pontos.get(i).toString().equals(pontoAntigo.toString()))
                                                    {
                                                        pontos.set(i, pontoNovo); //troca ponto antigo pelo novo
                                                        saida.println("success");
                                                        break;
                                                    }
                                            }
    
                                    }
                                else if(requisicao.startsWith("excluir")) //verifica se a requisição recebida começa com "excluir"
                                    {
                                        String dados = requisicao.substring("excluir".length()).trim(); //retira "excluir" do começo da requisição, ficando somente o ponto

                                        for (int i = 0; i < pontos.size(); i++) //percorre lista
                                            {
                                                if  (pontos.get(i).toString().equals(dados))
                                                    {
                                                        pontos.remove(i); // remove o ponto
                                                        saida.println("success");
                                                        break; // encerra o loop após remover
                                                    }
                                            }
                                    }
                            }
                    }
                catch(IOException e) //trata possíveis erros de entrada/saída ao subir o servidor
                    {
                        JOptionPane.showMessageDialog(null,"Não foi possível iniciar o servidor.\nErro: " + e.getMessage(),"Erro de Servidor",JOptionPane.ERROR_MESSAGE);
                    }
            }

        private static PontoDeColeta getPontoDeColeta(String ponto) //transforma uma String em um Objeto "PontoDeColeta"
            {
                String[] dadosPonto = ponto.split(";"); //separa os dados do ponto

                PontoDeColeta objetoPonto = new PontoDeColeta //cria ponto
                        (
                            dadosPonto[0],
                            dadosPonto[1],
                            dadosPonto[2],
                            dadosPonto[3],
                            dadosPonto[4],
                            dadosPonto[5],
                            dadosPonto[6]
                        );

                return objetoPonto;
            }
    }