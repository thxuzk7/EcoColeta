import javax.swing.*;
import java.io.*;
import java.net.*;

public class Servidor
    {
        // Define a porta em que o servidor irá escutar.
        private static final int PORTA = 8080;

        public static void main(String[] args) // Metodo principal que inicializa e executa o servidor.
            {
                // Instancia a classe responsável pela lógica de negócio (processamento das requisições).
                GerenciadorDeRequisicoes gerenciador = new GerenciadorDeRequisicoes();

                // Cria e abre um ServerSocket na porta definida. O 'try-with-resources' garante o fechamento.
                try (ServerSocket server = new ServerSocket(PORTA))
                    {
                        System.out.println("Servidor iniciado na porta " + PORTA);

                        // Loop infinito para aceitar conexões de múltiplos clientes.
                        while (true)
                            {
                                // Aguarda por uma conexão de cliente (server.accept()).
                                // 'try-with-resources' garante o fechamento do Socket, BufferedReader e PrintWriter.
                                try (Socket cliente = server.accept();
                                     // Objeto para ler dados enviados pelo cliente.
                                     BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
                                     // Objeto para enviar dados ao cliente.
                                     PrintWriter saida = new PrintWriter(cliente.getOutputStream(), true)
                                    )
                                        {
                                            // Lê a requisição completa enviada pelo cliente
                                            String requisicao = entrada.readLine();
                                            // Processa a requisição usando o GerenciadorDeRequisicoes.
                                            String[] respostas = gerenciador.processar(requisicao);

                                            // Envia todas as linhas de resposta de volta ao cliente.
                                            for (String r : respostas)
                                                {
                                                    saida.println(r);
                                                }
                                        }
                                // Captura exceções de comunicação com um cliente específico.
                                catch(IOException ex)
                                        {
                                            System.err.println("Erro ao comunicar com cliente: " + ex.getMessage());
                                        }
                            }
                    }
                // Captura exceções críticas, como falha ao abrir a porta do servidor.
                catch (IOException e)
                    {
                        JOptionPane.showMessageDialog(null,
                                "Não foi possível iniciar o servidor.\nErro: " + e.getMessage(),
                                "Erro de Servidor", JOptionPane.ERROR_MESSAGE);
                    }
            }
    }