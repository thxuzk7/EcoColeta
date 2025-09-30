import javax.swing.*;
import java.awt.*;

public class PainelCidadao
    {
        public static void requisicoesUser()
            {
                JFrame frame = ConfUI.criarFrame("EcoColeta - Painel Cidadão", 450, 320);

                // Painel principal com BorderLayout
                JPanel panelMain = new JPanel(new BorderLayout());
                panelMain.setBackground(new Color(245, 255, 245));

                // Painel central (conteúdo principal)
                JPanel panelCentro = new JPanel();
                panelCentro.setBackground(new Color(245, 255, 245));
                panelCentro.setLayout(new BoxLayout(panelCentro, BoxLayout.Y_AXIS));

                // Cria Titulo e Subtitulo
                JLabel titulo = ConfUI.criarTitulo("Painel do Cidadão", 25);
                JLabel subtitulo = ConfUI.criarSubTitulo("Escolha uma opção para continuar", 15);

                // Cria Botões principais
                JButton listar = ConfUI.criarBotao("Listar Pontos de Coleta", 250, 40);

                // Cria Botões Deslogar e Encerrar (menor e fixo no rodapé)
                JButton deslogar = ConfUI.criarBotao("Deslogar", 100, 30);
                JButton encerrar = ConfUI.criarBotao("Encerrar Programa", 100, 30);

                // Listeners principais
                listar.addActionListener(e -> {
                    frame.dispose();
                    PontoDeColeta.exibirlista(frame);
                });

                encerrar.addActionListener(e -> System.exit(0));

                deslogar.addActionListener(e -> {
                    frame.dispose();
                    Menu.menuInicial();
                });

                // Monta painel central
                panelCentro.add(Box.createRigidArea(new Dimension(0, 30)));
                panelCentro.add(titulo);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 10)));
                panelCentro.add(subtitulo);
                panelCentro.add(Box.createRigidArea(new Dimension(0, 30)));
                panelCentro.add(listar);

                // Painel de rodapé (para o botão deslogar)
                JPanel panelRodape = new JPanel();
                panelRodape.setBackground(new Color(230, 240, 230));
                panelRodape.add(deslogar);
                panelRodape.add(encerrar);

                // Adiciona nos lugares certos do BorderLayout
                panelMain.add(panelCentro, BorderLayout.CENTER);
                panelMain.add(panelRodape, BorderLayout.SOUTH);

                frame.setContentPane(panelMain);
                frame.setVisible(true);
            }
    }