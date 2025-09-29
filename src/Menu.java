import javax.swing.*;
import java.awt.*;

public class Menu
    {
        // Declaração do JFrame principal da aplicação.
        // É estático para ser acessado e manipulado por outras classes (para fechar/abrir a tela).
        public static JFrame frame = ConfUI.criarFrame("EcoColeta", 450, 320);

        public static void menuInicial() //Inicializa e exibe o menu inicial da aplicação.
            {
                // Tenta definir o Look and Feel da interface gráfica para o padrão do sistema operacional.
                try
                    {
                        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    }
                // Captura qualquer exceção caso não seja possível trocar o visual (erro não crítico).
                catch(Exception e)
                    {
                        JOptionPane.showMessageDialog(null,"Erro: Não foi possível trocar o visual padrão do swing.");
                    }

                // Cria os componentes de UI usando a classe utilitária ConfUI.
                JLabel titulo = ConfUI.criarTitulo("EcoColeta", 25);
                JLabel subtitulo = ConfUI.criarSubTitulo("Como deseja acessar o sistema?", 16);
                JButton btnAdmin = ConfUI.criarBotao("Administrador", 250, 40);

                // Configuração do Listener para o botão 'Administrador'.
                btnAdmin.addActionListener(e -> {
                    // Fecha a janela atual.
                    frame.dispose();
                    // Chama a tela de login do administrador.
                    Login.loginAdmin();
                });

                JButton btnCidadao = ConfUI.criarBotao("Cidadão", 250, 40);

                // Configuração do Listener para o botão 'Cidadão'.
                btnCidadao.addActionListener(e -> {
                    // Fecha a janela atual.
                    frame.dispose();
                    // Chama o painel de requisições do cidadão (listagem de pontos).
                    PainelCidadao.requisicoesUser();
                });

                // Adiciona componentes ao frame usando Box.createRigidArea para espaçamento vertical
                // e centralização implícita devido ao BoxLayout.Y_AXIS configurado em ConfUI.criarFrame.
                frame.add(Box.createRigidArea(new Dimension(0, 20)));
                frame.add(titulo);
                frame.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.add(subtitulo);
                frame.add(Box.createRigidArea(new Dimension(0, 30)));
                frame.add(btnAdmin);
                frame.add(Box.createRigidArea(new Dimension(0, 10)));
                frame.add(btnCidadao);

                // Torna o frame visível, exibindo a tela para o usuário.
                frame.setVisible(true);
            }
    }