import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PainelCidadao
    {
        public static void requisicoesUser() //Inicializa e exibe o painel principal do usuário comum (Cidadão).
            {
                JFrame frame = ConfUI.criarFrame("EcoColeta - Painel do Usuário", 450, 320);

                // Painel principal usa BoxLayout vertical para empilhar componentes.
                JPanel panelMain = new JPanel();
                panelMain.setBackground(new Color(245, 255, 245));
                panelMain.setLayout(new BoxLayout(panelMain, BoxLayout.Y_AXIS));

                JLabel titulo = ConfUI.criarTitulo("Painel do Usuário", 25);
                JLabel subtitulo = ConfUI.criarSubTitulo("Escolha uma opção para continuar", 15);
                JButton listar = ConfUI.criarBotao("Listar Pontos de Coleta", 250, 40);
                JButton encerrarProg = ConfUI.criarBotao("Encerrar Programa",250, 40);

                // Listener para Encerrar Programa.
                encerrarProg.addActionListener(e -> System.exit(0));

                // Listener para Listar Pontos de Coleta.
                listar.addActionListener(e -> {
                    frame.dispose(); // Fecha a tela de menu do cidadão.
                    PontoDeColeta.exibirlista(frame);
                });

                // Adiciona os componentes ao painel principal do cidadão.
                panelMain.add(Box.createRigidArea(new Dimension(0, 20)));
                panelMain.add(titulo);
                panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
                panelMain.add(subtitulo);
                panelMain.add(Box.createRigidArea(new Dimension(0, 30)));
                panelMain.add(listar);
                panelMain.add(Box.createRigidArea(new Dimension(0, 10)));
                panelMain.add(encerrarProg);

                frame.setContentPane(panelMain);
                frame.setVisible(true);
            }
    }