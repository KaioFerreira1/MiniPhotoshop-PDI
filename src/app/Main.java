package app;

import methods.Filtros;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;

import static methods.Filtros.HistogramaColorido;

public class Main {

    private JFrame frame;
    private JLabel imageLabel;
    private JButton carregarImagem;
    private JButton realceDeBordaBotao;
    private JButton suavizarBotao;
    private JButton inverterCoresBotao;
    private JButton binarizarImagemBotao;
    private JButton filtroDeBordaBotao;
    private JButton histogramaBotao;
    private BufferedImage imagem;

    public Main() {
        frame = new JFrame("Filtros de Imagem");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setVisible(true);

        imageLabel = new JLabel();
        JScrollPane scrollPane = new JScrollPane(imageLabel);
        frame.add(scrollPane, BorderLayout.CENTER);

        //aqui cria os botões
        JPanel barraDeBotoes = new JPanel();
        carregarImagem = new JButton("Carregar Imagem");
        realceDeBordaBotao = new JButton("Realce de Borda");
        suavizarBotao = new JButton("Suavizar (Gaussiano)");
        inverterCoresBotao = new JButton("Inverter Cores");
        binarizarImagemBotao = new JButton("Binarizar Imagem");
        filtroDeBordaBotao = new JButton("Borda e suavizar");
        histogramaBotao = new JButton("histograma");

        //aqui adiciona eles a barra
        barraDeBotoes.add(carregarImagem);
        barraDeBotoes.add(realceDeBordaBotao);
        barraDeBotoes.add(suavizarBotao);
        barraDeBotoes.add(inverterCoresBotao);
        barraDeBotoes.add(binarizarImagemBotao);
        barraDeBotoes.add(filtroDeBordaBotao);
        barraDeBotoes.add(histogramaBotao);
        frame.add(barraDeBotoes, BorderLayout.SOUTH);

        carregarImagem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        imagem = ImageIO.read(selectedFile);
                        exibirImagem(imagem);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        realceDeBordaBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagem != null) {
                    BufferedImage imagemSaida = null;
                    try {
                        imagemSaida = Filtros.realceBorda(imagem);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    exibirImagem(imagemSaida);
                }
            }
        });

        suavizarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagem != null) {
                    BufferedImage imagemSaida = null;
                    try {
                        imagemSaida = Filtros.suavizar(imagem);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    exibirImagem(imagemSaida);
                }
            }
        });

        inverterCoresBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagem != null) {
                    BufferedImage imagemSaida = null;
                    imagemSaida = Filtros.inverterAsCores(imagem);
                    exibirImagem(imagemSaida);
                }
            }
        });
        binarizarImagemBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagem != null) {
                    BufferedImage imagemSaida = null;
                    imagemSaida = Filtros.BinarizarAImagem(imagem);
                    exibirImagem(imagemSaida);
                }
            }
        });

        filtroDeBordaBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int[] filtro1 = {1, 1, 1, 0, 0, 0, -1, -1, -1};
                int[] filtro2 = {1, 0, -1, 1, 0, -1, 1, 0, -1};
                int[] filtro3 = {-1, -1, -1, -1, 8, -1, -1, -1, -1};
                int[] filtro4 = {0, -1, 0, -1, 5, -1, 0, -1, 0};
                int[] filtro5 = {0, 0, 0, -1, 1, 0, 0, 0, 0};
                int[] filtro6 = {0, 1, 0, 1, -4, 1, 0, 1, 0};
                int[] filtro7 = {-2, -1, 0, -1, 1, 1, 0, 1, 2};

                if (imagem != null) {
                    BufferedImage imagemSaida = null;
                    imagemSaida = Filtros.AplicarFiltros(imagem, filtro1);
                    exibirImagem(imagemSaida);
                }
            }
        });

        histogramaBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (imagem != null) {
                    HistogramaColorido(imagem);
                }
            }
        });
        frame.setVisible(true);
    }

    private void exibirImagem(BufferedImage image) {
        ImageIcon imageIcon = new ImageIcon(image);
        imageLabel.setIcon(imageIcon);
        imageLabel.revalidate();
        imageLabel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Main();
            }
        });
    }
}
