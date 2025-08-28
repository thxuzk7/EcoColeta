import java.io.*;
import java.net.*;

public class Cliente
    {
        public static void main(String[] args)
            {
                try (Socket socket = new Socket("localhost", 8080)) //Conecta no servidor rodando na mesma máquina (localhost) e na porta 8080
                    {
                        System.out.println("Conexão estabelecida com o servidor.");
                    }
                catch (IOException e)
                    {
                        e.printStackTrace();
                    }
            }
    }