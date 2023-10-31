package methods;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Filtros {
    public static BufferedImage suavizar(BufferedImage entrada) throws IOException {
        int largura = entrada.getWidth();
        int altura = entrada.getHeight();

        BufferedImage saida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        double[] filtro_gau = {
                0.0625, 0.125, 0.0625,
                0.125, 0.25, 0.125,
                0.0625, 0.125, 0.0625
        };

        Color px1, px2, px3, px4, px5, px6, px7, px8, px9;

        for (int linha = 1; linha < largura-2; linha++){
            for (int coluna = 1; coluna < altura-2; coluna++){

                px1 = new Color( entrada.getRGB(linha-1, coluna-1) );
                px2 = new Color( entrada.getRGB(linha-1, coluna) );
                px3 = new Color( entrada.getRGB(linha-1, coluna+1) );
                px4 = new Color( entrada.getRGB(linha, coluna-1) );
                px5 = new Color( entrada.getRGB(linha, coluna) );
                px6 = new Color( entrada.getRGB(linha, coluna+1) );
                px7 = new Color( entrada.getRGB(linha+1, coluna-1) );
                px8 = new Color( entrada.getRGB(linha+1, coluna) );
                px9 = new Color( entrada.getRGB(linha+1, coluna+1) );

                int pixel = (int) (
                        (filtro_gau[0] * px1.getRed())+
                                (filtro_gau[1] * px2.getRed())+
                                (filtro_gau[2] * px3.getRed())+
                                (filtro_gau[3] * px4.getRed())+
                                (filtro_gau[4] * px5.getRed())+
                                (filtro_gau[5] * px6.getRed())+
                                (filtro_gau[6] * px7.getRed())+
                                (filtro_gau[7] * px8.getRed())+
                                (filtro_gau[8] * px9.getRed())
                );

                if (pixel > 255) {
                    pixel = 255;
                }
                if (pixel < 0) {
                    pixel = 0;
                }

                Color novaCor = new Color(pixel, pixel, pixel);
                saida.setRGB(linha, coluna, novaCor.getRGB());

            }
        }
        return saida;
    }

    public static BufferedImage realceBorda(BufferedImage entrada) throws IOException {
        int largura = entrada.getWidth();
        int altura = entrada.getHeight();

        BufferedImage saida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        int[] realce = {-1, -1, -1, -1, 8, -1, -1, -1, -1};

        Color px1, px2, px3, px4, px5, px6, px7, px8, px9;

        for (int linha = 1; linha < largura-2; linha++){
            for (int coluna = 1; coluna < altura-2; coluna++){

                px1 = new Color( entrada.getRGB(linha-1, coluna-1) );
                px2 = new Color( entrada.getRGB(linha-1, coluna) );
                px3 = new Color( entrada.getRGB(linha-1, coluna+1) );
                px4 = new Color( entrada.getRGB(linha, coluna-1) );
                px5 = new Color( entrada.getRGB(linha, coluna) );
                px6 = new Color( entrada.getRGB(linha, coluna+1) );
                px7 = new Color( entrada.getRGB(linha+1, coluna-1) );
                px8 = new Color( entrada.getRGB(linha+1, coluna) );
                px9 = new Color( entrada.getRGB(linha+1, coluna+1) );

                int pixel = (int) (
                        (realce[0] * px1.getRed())+
                                (realce[1] * px2.getRed())+
                                (realce[2] * px3.getRed())+
                                (realce[3] * px4.getRed())+
                                (realce[4] * px5.getRed())+
                                (realce[5] * px6.getRed())+
                                (realce[6] * px7.getRed())+
                                (realce[7] * px8.getRed())+
                                (realce[8] * px9.getRed())
                );

                if (pixel > 255) {
                    pixel = 255;
                }
                if (pixel < 0) {
                    pixel = 0;
                }

                Color novaCor = new Color(pixel, pixel, pixel);
                saida.setRGB(linha, coluna, novaCor.getRGB());

            }
        }
        return saida;
    }
    public static BufferedImage inverterAsCores(BufferedImage entrada) {
        int largura = entrada.getWidth();
        int altura = entrada.getHeight();

        BufferedImage saida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int linha = 0; linha < largura; linha++) {
            for (int coluna = 0; coluna < altura; coluna++) {
                Color pixel = new Color(entrada.getRGB(linha, coluna));

                int red = 255 - pixel.getRed();
                int green = 255 - pixel.getGreen();
                int blue = 255 - pixel.getBlue();

                Color novaCor = new Color(red, green, blue);
                saida.setRGB(linha, coluna, novaCor.getRGB());
            }
        }

        return saida;
    }
    public static BufferedImage BinarizarAImagem(BufferedImage entrada) {
        int limiar = 100;
        int largura = entrada.getWidth();
        int altura = entrada.getHeight();
        BufferedImage saida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);
        saida.getGraphics().drawImage(entrada, 0, 0, null);


        int threshold = 150;
        for (int y = 0; y < saida.getHeight(); y++) {
            for (int x = 0; x < saida.getWidth(); x++) {
                int pixel = saida.getRGB(x, y);
                int grayValue = (pixel >> 16) & 0xFF;
                int binaryValue = (grayValue < threshold) ? 0 : 255;
                int newPixel = (binaryValue << 16) | (binaryValue << 8) | binaryValue;
                saida.setRGB(x, y, newPixel);
            }
        }
        return saida;
    }
    public static BufferedImage AplicarFiltros(BufferedImage entrada, int[] filtro) {
        int largura = entrada.getWidth();
        int altura = entrada.getHeight();
        BufferedImage saida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        for (int y = 1; y < altura - 1; y++) {
            for (int x = 1; x < largura - 1; x++) {
                int pixel = 0;
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        int rgb = entrada.getRGB(x + i, y + j);
                        int cinza = (int) (0.299 * ((rgb >> 16) & 0xFF) + 0.587 * ((rgb >> 8) & 0xFF) + 0.114 * (rgb & 0xFF));
                        pixel += filtro[(i + 1) * 3 + (j + 1)] * cinza;
                    }
                }
                pixel = Math.min(255, Math.max(0, pixel));
                saida.setRGB(x, y, (pixel << 16) | (pixel << 8) | pixel);
            }
        }
        return saida;
    }
    public static void HistogramaColorido(BufferedImage entrada) {
        int largura = entrada.getWidth();
        int altura = entrada.getHeight();
        BufferedImage saida = new BufferedImage(largura, altura, BufferedImage.TYPE_INT_RGB);

        int[] histogramaRed = new int[256];
        int[] histogramaGreen = new int[256];
        int[] histogramaBlue = new int[256];

        for (int y = 0; y < saida.getHeight(); y++) {
            for (int x = 0; x < saida.getWidth(); x++) {
                int pixel = saida.getRGB(x, y);
                int red = (pixel >> 16) & 0xFF;
                int green = (pixel >> 8) & 0xFF;
                int blue = pixel & 0xFF;

                histogramaRed[red]++;
                histogramaGreen[green]++;
                histogramaBlue[blue]++;
            }
        }
        for (int i = 0; i < 256; i++) {
            System.out.println("Cor vermelha " + i + ": " + histogramaRed[i] + " vezes");
            System.out.println("Cor verde " + i + ": " + histogramaGreen[i] + " vezes");
            System.out.println("Cor azul " + i + ": " + histogramaBlue[i] + " vezes");
        }
    }
}