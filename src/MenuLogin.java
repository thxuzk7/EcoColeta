// Importando bibliotecas necessárias
import javax.swing.*;
import java.awt.*;

public class MenuLogin
    {
        public static void login()
            {
                //Tenta deixar a janela com o visual padrão do sistema (Windows, Linux, Mac)
                try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    }
                catch(Exception e)
                    {
                        e.printStackTrace(); //Se der erro, mostra no console
                    }

                //Criando a janela principal
                JFrame frame = new JFrame("EcoColeta - Menu Inicial"); //Cria uma janela com esse título
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Se fechar a janela, encerra o programa
                frame.setSize(400, 280); //Tamanho da janela (largura x altura em pixels)
                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS)); //Organiza os elementos em coluna (um embaixo do outro)

                //Criando o título
                JLabel titulo = new JLabel("EcoColeta", SwingConstants.CENTER); //Texto centralizado "EcoColeta"
                titulo.setFont(new Font("Arial", Font.BOLD, 28)); //Fonte Arial, negrito, tamanho 28
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT); //Centraliza o texto no meio da janela

                //Criando o subtítulo
                JLabel subtitulo = new JLabel("Selecione uma opção para continuar", SwingConstants.CENTER);
                subtitulo.setFont(new Font("Arial", Font.PLAIN, 16)); //Texto normal, fonte Arial tamanho 16
                subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT); //Centralizado também

                //Criando o botão Administrador
                JButton btnAdmin = new JButton("Acessar como Administrador"); //Texto do botão
                btnAdmin.setBackground(new Color(200, 200, 200)); //Cor de fundo cinza claro
                btnAdmin.setForeground(Color.BLACK); //Texto preto
                btnAdmin.setFocusPainted(false); //Tira o contorno azul feio do botão
                btnAdmin.setFont(new Font("Arial", Font.BOLD, 16)); //Fonte Arial, negrito, tamanho 16
                btnAdmin.setAlignmentX(Component.CENTER_ALIGNMENT); //Centraliza o botão

                //Ação do botão Administrador
                btnAdmin.addActionListener(e -> {
                    //opções de requisições
                    frame.dispose(); //fecha a janela
                    Requisicoes.requisicoesAdmin(); //chama as funções para Admin
                });

                //Criando o botão Usuário
                JButton btnUsuario = new JButton("Entrar como Usuário"); //Texto do botão
                btnUsuario.setBackground(new Color(200, 200, 200)); //Cor de fundo cinza claro
                btnUsuario.setForeground(Color.BLACK); //Texto preto
                btnUsuario.setFocusPainted(false); //Sem contorno azul
                btnUsuario.setFont(new Font("Arial", Font.BOLD, 16)); //Fonte Arial, negrito, tamanho 16
                btnUsuario.setAlignmentX(Component.CENTER_ALIGNMENT); //Centraliza o botão

                //Ação do botão Usuário
                btnUsuario.addActionListener(e -> {
                    frame.dispose(); //fecha a janela
                    Requisicoes.requisicoesUser(); //chama as funções para User
                });

                //Adicionando espaçamentos e elementos na janela na ordem certa
                frame.add(Box.createRigidArea(new Dimension(0, 20))); //Espaço vazio
                frame.add(titulo); //Adiciona o título
                frame.add(Box.createRigidArea(new Dimension(0, 10))); //Espaço vazio
                frame.add(subtitulo); //Adiciona o subtítulo
                frame.add(Box.createRigidArea(new Dimension(0, 30))); //Espaço vazio
                frame.add(btnAdmin); //Adiciona o botão Administrador
                frame.add(Box.createRigidArea(new Dimension(0, 15))); //Espaço vazio
                frame.add(btnUsuario); //Adiciona o botão Usuário

                //Centraliza a janela no meio da tela
                frame.setLocationRelativeTo(null);

                //Torna a janela visível (sem isso ela não aparece)
                frame.setVisible(true);
            }
    }