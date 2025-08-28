import java.io.*;
import java.net.*;

public class Servidor
    {
        private static final int PORTA = 8080; // Porta usada para comunicação

        public static void main(String[] args)
            {
                try (ServerSocket server = new ServerSocket(PORTA))
                    {
                        System.out.println("Servidor rodando na porta " + PORTA);

                        while(true) // loop infinito: sempre esperando novos clientes
                            {
                                Socket cliente = server.accept(); // espera até um cliente conectar
                                System.out.println("Cliente conectado: " + cliente.getInetAddress());

                                cliente.close(); // fecha conexão com o cliente
                            }
                    }
                catch (IOException e)
                    {
                        e.printStackTrace();
                    }
            }
    }