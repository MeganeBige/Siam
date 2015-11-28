package Siam.Interface;

import Siam.Enum.Camp;
import Siam.Constantes;
import Siam.Enum.Theme;
import Siam.Jeu;
import Siam.Joueur;
import Siam.Sons.Musique;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

public class EcranVictoire implements ActionListener, Constantes {

    private Jeu jeu;
    private JFrame fenetre;
    private OutilsFont outilsFont;

    private JButton continuer;
    private JButton retourMenu;
    private JLabel gagnant;

    private Theme theme;
    private Musique musique;
    private boolean son;

    public EcranVictoire(Jeu jeu, JFrame fenetre, Camp campGagnant, Theme theme, Musique musique, boolean son){
        this.jeu = jeu;
        this.fenetre = fenetre;
        this.theme = theme;
        this.musique = musique;
        this.son = son;

        initEcranVictoire(campGagnant);
        afficheEcranVictoire();
        setControlEcranVictoire(this);

        fenetre.setSize(LARGEUR_FENETRE, HAUTEUR_FENETRE);
        fenetre.setLocationRelativeTo(null);
        fenetre.setResizable(false);
        fenetre.setTitle("Siam");
        fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fenetre.setVisible(true);
    }

    public void initEcranVictoire(Camp campGagnant){
        StringBuilder str = new StringBuilder("Victoire des ");
        if(campGagnant == Camp.ELEPHANT)  str.append("Eléphants");
        else str.append("Rhinocéros");
        gagnant = new JLabel(String.valueOf(str));
        outilsFont = new OutilsFont();
        continuer = new JButton("Continuer");
        retourMenu = new JButton("Retour au Menu");
    }

    public void afficheEcranVictoire() {
        JPanel panPrincipal = new JPanel();
        JPanel phraseGagnant = new JPanel();
        JPanel vide = new JPanel();
        JPanel boutonPanel = new JPanel();

        panPrincipal.setOpaque(false);
        phraseGagnant.setOpaque(false);
        vide.setOpaque(false);
        boutonPanel.setOpaque(false);

        panPrincipal = new JPanel() {
            BufferedImage image = ImageLibrairie.imageLibrairie.getImage(theme, "FondMenu");

            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(image, 0, 0, LARGEUR_FENETRE, HAUTEUR_FENETRE, this);
            }
        };

        changerPolice();


        phraseGagnant.add(gagnant);
        boutonPanel.add(continuer);
        boutonPanel.add(retourMenu);
        boutonPanel.setLayout(new GridLayout(1, 2));
        panPrincipal.add(vide);
        panPrincipal.add(phraseGagnant);
        panPrincipal.add(boutonPanel);
        panPrincipal.setLayout(new GridLayout(3,1));

        fenetre.setContentPane(panPrincipal);
    }

    private void changerPolice() {
        if (theme == Theme.STANDARD) {
            outilsFont.changerFontJLabel(gagnant, 80, Color.orange, outilsFont.getFontTexte());
            outilsFont.changerFontButton(continuer, 40, Color.orange, outilsFont.getFontMenu());
            outilsFont.changerFontButton(retourMenu, 40, Color.orange, outilsFont.getFontMenu());
        } else if (theme == Theme.NOEL) {
            outilsFont.changerFontJLabel(gagnant, 80, Color.black, outilsFont.getFontTexte());
            outilsFont.changerFontButton(continuer, 40, Color.black, outilsFont.getFontMenu());
            outilsFont.changerFontButton(retourMenu, 40, Color.black, outilsFont.getFontMenu());
        }
    }

    private void setControlEcranVictoire(ActionListener listener) {
        continuer.addActionListener(listener);
        retourMenu.addActionListener(listener);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == continuer) {
            jeu.setTheme(theme);
            jeu.setMusique(musique);
            jeu.setSon(son);
            jeu.initJeu(new Joueur(Camp.ELEPHANT), new Joueur(Camp.RHINOCEROS));
            jeu.start();
        }
        if (source == retourMenu) {
            new Menu(jeu, fenetre, theme, musique, son);
        }
    }
}