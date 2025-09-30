import javax.swing.*;
import java.awt.*;

public class Menu
    {
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

                // Painel principal com BorderLayout
                JPanel panelMain = new JPanel(new BorderLayout());
                panelMain.setBackground(new Color(245, 255, 245));

                // Painel central (conteúdo principal)
                JPanel panelCentro = new JPanel();
                panelCentro.setBackground(new Color(245, 255, 245));
                panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

                // Cria a Tela
                JFrame frame = ConfUI.criarFrame("EcoColeta", 450, 320);

                // Cria Titulo e Subtitulo
                JLabel titulo = ConfUI.criarTitulo("EcoColeta", 25);
                JLabel subtitulo = ConfUI.criarSubTitulo("Como deseja acessar o sistema?", 16);

                //Cria Botões principais
                JButton btnAdmin = ConfUI.criarBotao("Administrador", 250, 40);
                JButton btnCidadao = ConfUI.criarBotao("Cidadão", 250, 40);

                // Cria Botão Encerrar (menor e fixo no rodapé)
                JButton encerrar = ConfUI.criarBotao("Encerrar Programa", 30, 10);

                // Configuração do Listener para o botão 'Administrador'.
                btnAdmin.addActionListener(e -> {
                    // Fecha a janela atual.
                    frame.dispose();
                    // Chama a tela de login do administrador.
                    Login.loginAdmin();
                });

                // Configuração do Listener para o botão 'Cidadão'.
                btnCidadao.addActionListener(e -> {
                    // Fecha a janela atual.
                    frame.dispose();
                    // Chama o painel de requisições do cidadão (listagem de pontos).
                    PainelCidadao.requisicoesUser();
                });

                // Listener para Encerrar Programa.
                encerrar.addActionListener(e -> System.exit(0));

                // Adiciona componentes no painel central
                panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
                panelCentro.add(titulo);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
                panelCentro.add(subtitulo);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 20)));
                panelCentro.add(btnAdmin);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
                panelCentro.add(btnCidadao);

                // Painel de rodapé (para o botão deslogar)
                JPanel panelRodape = new JPanel();
                panelRodape.setBackground(new Color(230, 240, 230));
                panelRodape.add(encerrar);

                // Adiciona nos lugares certos do BorderLayout
                panelMain.add(panelCentro, BorderLayout.CENTER);
                panelMain.add(panelRodape, BorderLayout.SOUTH);

                frame.setContentPane(panelMain);
                frame.setVisible(true); // Torna o frame visível, exibindo a tela para o usuário.
            }
    }