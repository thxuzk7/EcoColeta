import javax.swing.*;
import java.awt.*;

public class ConfUI
    {
        public static JFrame criarFrame(String texto, int width, int height) //Cria e configura um JFrame padrão para a aplicação.
            {
                JFrame frame = new JFrame(texto);
                // Define que o programa deve encerrar quando a janela for fechada.
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(width, height);
                // Usa BoxLayout na direção vertical (Y_AXIS), o que facilita o alinhamento central
                // (usado com setAlignmentX(Component.CENTER_ALIGNMENT) nos componentes).
                frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
                // Centraliza a janela na tela.
                frame.setLocationRelativeTo(null);
                // Define a cor de fundo padrão para um tom de verde claro (tema ecológico).
                frame.getContentPane().setBackground(new Color(245, 255, 245));
                return frame;
            }

        public static JLabel criarTitulo(String texto, int tamanho) //Cria e configura um JLabel para ser usado como título principal.
            {
                // Centraliza o texto dentro do próprio JLabel.
                JLabel titulo = new JLabel(texto, SwingConstants.CENTER);
                // Define a fonte como 'Segoe UI', negrito e tamanho especificado.
                titulo.setFont(new Font("Segoe UI", Font.BOLD, tamanho));
                // Define a cor da fonte como um tom de verde escuro.
                titulo.setForeground(new Color(0, 100, 0));
                // Centraliza o JLabel dentro do BoxLayout do contêiner pai.
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
                return titulo;
            }

        public static JLabel criarSubTitulo(String texto, int tamanho) //Cria e configura um JLabel para ser usado como subtítulo.
            {
                JLabel subtitulo = new JLabel(texto, SwingConstants.CENTER);
                // Fonte simples (PLANT), menor e em um tom de cinza.
                subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, tamanho));
                subtitulo.setForeground(new Color(80, 80, 80));
                subtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
                return subtitulo;
            }

        public static JButton criarBotao(String texto, int width, int height) //Cria e configura um JButton padrão para a aplicação.
            {
                JButton botao = new JButton(texto);
                // Define o esquema de cores: fundo verde claro, texto verde escuro (tema ecológico).
                botao.setBackground(new Color(200, 255, 200));
                botao.setForeground(new Color(0, 100, 0));
                // Remove o foco pintado, que é o contorno que aparece após o clique.
                botao.setFocusPainted(false);
                botao.setFont(new Font("Segoe UI", Font.BOLD, 16));
                botao.setAlignmentX(Component.CENTER_ALIGNMENT);
                // Altera o cursor para a mão, indicando que o elemento é clicável.
                botao.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Define o tamanho máximo, importante para o BoxLayout.
                botao.setMaximumSize(new Dimension(width, height));
                return botao;
            }
    }