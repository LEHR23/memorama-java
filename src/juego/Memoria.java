package juego;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;

public class Memoria extends JFrame {

    private JLabel fondo;
    private JButton jugar;
    private JLabel imagenes[][];
    private int matriz[][];
    private ImageIcon imagenesh[];
    private Icon iconos[];
    private int seleccionanterior, posx, posy, posxa, posya;
    private Icon iconodetras;
    private int yagano, espera, clicks;
    private Timer t;

    public Memoria() {
        t = new Timer(500, laespera);
        clicks = espera = 0;
        seleccionanterior = 0;
        posxa = 0;
        posya = 0;
        posx = 0;
        posy = 0;
        imagenes = new JLabel[4][4];
        matriz = new int[4][4];
        imagenesh = new ImageIcon[8];
        iconos = new Icon[8];
        //ventana
        setLayout(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(0, 0, 900, 700);
        setLocationRelativeTo(null);
        setResizable(false);
        ImageIcon imagen = new ImageIcon("imagenes/fondohalo.jpg");
        Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(900, 700,
                Image.SCALE_DEFAULT));
        fondo = new JLabel(icono);
        fondo.setBounds(0, 0, 900, 700);
        add(fondo, -1);

        //boton jugar
        ImageIcon imagenjugar = new ImageIcon("imagenes/jugar.png");
        Icon iconojugar = new ImageIcon(imagenjugar.getImage().getScaledInstance(150, 50,
                Image.SCALE_DEFAULT));
        jugar = new JButton(iconojugar);
        jugar.setBounds(10, 590, 150, 50);
        jugar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("jugar");
                desordenamiento();
            }
        });
        add(jugar);

        //matriz de labels
        ImageIcon detras = new ImageIcon("imagenes/halodetras.jpg");
        iconodetras = new ImageIcon(detras.getImage().getScaledInstance(170,
                120, Image.SCALE_DEFAULT));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matriz[i][j] = 0;
                imagenes[i][j] = new JLabel(iconodetras);
                imagenes[i][j].setBounds((j * 180) + 150, (i * 130) + 60, 170, 120);
                imagenes[i][j].setVisible(false);
                imagenes[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        for (int k = 0; k < 4; k++) {
                            for (int l = 0; l < 4; l++) {
                                if (e.getSource() == imagenes[k][l]) {
                                    imagenesclick(k, l);
                                }
                            }
                        }
                    }
                });
                add(imagenes[i][j], 0);
            }
        }

        //cargar imagenes
        for (int i = 0; i < 8; i++) {
            imagenesh[i] = new ImageIcon("imagenes/halo" + (i + 1) + ".jpg");
            iconos[i] = new ImageIcon(imagenesh[i].getImage().getScaledInstance(
                    170, 120, Image.SCALE_DEFAULT));
        }
    }

    private void desordenamiento() {
        yagano = 0;
        clicks = 0;
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                matriz[i][j] = 0;
                imagenes[i][j].setVisible(true);
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int cont = 0;
                do {
                    cont = 0;
                    matriz[i][j] = random.nextInt(8) + 1;
                    for (int k = 0; k < 4; k++) {
                        for (int l = 0; l < 4; l++) {
                            if (!(i == k && j == l) && matriz[i][j] == matriz[k][l]) {
                                cont++;
                            }
                        }
                    }
                } while (cont > 1);
                System.out.print(matriz[i][j] + " ");
            }
            System.out.println();
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                imagenes[i][j].setIcon(iconodetras);
            }
        }
    }

    private void imagenesclick(int i, int j) {
        clicks++;
        if (imagenes[i][j].getIcon() == iconodetras&&clicks<3) {
            imagenes[i][j].setIcon(iconos[matriz[i][j] - 1]);
        }
        if (seleccionanterior != 0 && !(posx == i && posy == j)&&clicks<3) {
            if (seleccionanterior != matriz[i][j]) {
                espera = 0;
                t.start();
                posxa = i;
                posya = j;
            } else {
                yagano += 2;
                clicks = 0;
            }
            seleccionanterior = 0;
        } else {
            if (seleccionanterior != 0) {
                imagenes[i][j].setIcon(iconodetras);
                seleccionanterior = 0;
                clicks = 0;
            } else {
                if(clicks<3){
                posx = i;
                posy = j;
                seleccionanterior = matriz[i][j];
                }
            }
        }
        if (yagano == 16) {
            JOptionPane.showMessageDialog(null, "HAZ GANADO!!!");
        }
    }

    private ActionListener laespera = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            espera++;
            System.out.println("si" + espera);
            if (espera == 1) {
                espera = 0;
                voltear();
                t.stop();
            }
        }
    };

    private void voltear() {
        clicks = 0;
        imagenes[posxa][posya].setIcon(iconodetras);
        imagenes[posx][posy].setIcon(iconodetras);
    }

}
