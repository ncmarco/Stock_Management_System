/**
 * @author Nino-Coman Marco
 */

package ui;

import classes.*;
import dao.*;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SistemUI {
    /**
     * Main
     * @param args
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sistem de Management al Stocului");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        CardLayout cardLayout = new CardLayout();
        JPanel cardPanel = new JPanel(cardLayout);

        JPanel mainMenuPanel = createMainMenu(cardLayout, cardPanel);
        JPanel panouCautareProdus = creeazaPanouCautareProdus(cardLayout, cardPanel);
        JPanel panouCautareDupaNume = creeazaPanouCautareDupaNume(cardLayout, cardPanel);
        JPanel panouCautareDupaCategorie = creeazaPanouCautareDupaCategorie(cardLayout, cardPanel);
        JPanel panouAdaugaProdus = creeazaPanouAdaugaProdus(cardLayout, cardPanel);
        JPanel panouStergereProdus = creeazaPanouStergereProdus(cardLayout, cardPanel);
        JPanel panouAdaugareCategorie = creeazaPanouAdaugaCategorie(cardLayout, cardPanel);
        JPanel panouVeziFurnizori = creeazaPanouVeziFurnizori(cardLayout, cardPanel);
        JPanel panouVeziProduse = creeazaPanouVeziProduse(cardLayout, cardPanel);

        cardPanel.add(mainMenuPanel, "Main Menu");
        cardPanel.add(panouCautareProdus, "Cautare Produs");
        cardPanel.add(panouCautareDupaNume, "Cautare Dupa Nume");
        cardPanel.add(panouCautareDupaCategorie, "Cautare Dupa Categorie");
        cardPanel.add(panouAdaugaProdus, "Adauga Produs");
        cardPanel.add(panouStergereProdus, "Stergere Produs");
        cardPanel.add(panouAdaugareCategorie, "Adaugare Categorie");
        cardPanel.add(panouVeziFurnizori, "Vezi Furnizori");
        cardPanel.add(panouVeziProduse, "Vezi Produse");

        frame.add(cardPanel);
        frame.setVisible(true);
    }

    /**
     * Metoda care creeaza panoul pentru meniul principal
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel createMainMenu(CardLayout cardLayout, JPanel cardPanel) {
        JPanel mainMenuPanel = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Sistem de Management al Stocului", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(250, 161, 117));
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(20, 5, 20, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        sidePanel.add(createRoundedButton("Adaugă produs", () -> cardLayout.show(cardPanel, "Adauga Produs")), gbc);
        sidePanel.add(createRoundedButton("Șterge Produs", () -> cardLayout.show(cardPanel, "Stergere Produs")), gbc);
        sidePanel.add(createRoundedButton("Caută produs", () -> cardLayout.show(cardPanel, "Cautare Produs")), gbc);
        sidePanel.add(createRoundedButton("Adaugă categorie", () -> cardLayout.show(cardPanel, "Adaugare Categorie")), gbc);
        sidePanel.add(createRoundedButton("Vezi Furnizori", () -> {
            JPanel panouVeziFurnizori = creeazaPanouVeziFurnizori(cardLayout, cardPanel);
            cardPanel.add(panouVeziFurnizori, "Vezi Furnizori");
            cardLayout.show(cardPanel, "Vezi Furnizori");
        }), gbc);

        sidePanel.add(createRoundedButton("Vezi Produse", () -> {
            JPanel panouVeziProduse = creeazaPanouVeziProduse(cardLayout, cardPanel);
            cardPanel.add(panouVeziProduse, "Vezi Produse");
            cardLayout.show(cardPanel, "Vezi Produse");
        }), gbc);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(53, 65, 90));

        mainMenuPanel.add(titleLabel, BorderLayout.NORTH);
        mainMenuPanel.add(sidePanel, BorderLayout.WEST);
        mainMenuPanel.add(mainPanel, BorderLayout.CENTER);

        return mainMenuPanel;
    }

    /**
     * Metoda care creeaza panoul pentru cautarea unui produs
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel creeazaPanouCautareProdus(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panouCautareProdus = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Caută un produs", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(250, 161, 117));
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(10, 5, 10, 5);
        gbc.anchor = GridBagConstraints.NORTH;

        sidePanel.add(createRoundedButton("După nume", () -> cardLayout.show(cardPanel, "Cautare Dupa Nume")), gbc);
        sidePanel.add(createRoundedButton("După categorie", () -> cardLayout.show(cardPanel, "Cautare Dupa Categorie")), gbc);

        gbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), gbc);

        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), gbc);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(53, 65, 90));

        panouCautareProdus.add(titleLabel, BorderLayout.NORTH);
        panouCautareProdus.add(sidePanel, BorderLayout.WEST);
        panouCautareProdus.add(mainPanel, BorderLayout.CENTER);

        return panouCautareProdus;
    }

    /**
     * Metoda care creeaza panoul pentru crearea unui produs dupa nume
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel creeazaPanouCautareDupaNume(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panouCautareDupaNume = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Caută un produs după nume", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(53, 65, 90));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel labelNume = new JLabel("Nume Produs:");
        labelNume.setForeground(new Color(250, 161, 117));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(labelNume, gbc);

        JTextField textFieldNume = new JTextField(20);
        textFieldNume.setBackground(new Color(75, 85, 110)); // Fundal personalizat
        textFieldNume.setForeground(new Color(250, 161, 117)); // Culoare text personalizata
        textFieldNume.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2)); // Margine
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(textFieldNume, gbc);

        JTextArea textAreaRezultate = new JTextArea(15, 52);
        textAreaRezultate.setBackground(new Color(75, 85, 110)); // Fundal
        textAreaRezultate.setForeground(new Color(250, 161, 117)); // Culoare text
        textAreaRezultate.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        textAreaRezultate.setEditable(false);
        JScrollPane scrollPane = createCustomScrollPane(textAreaRezultate); // Scroll pentru text area
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(scrollPane, gbc);

        JButton btnCauta = createRoundedButtonInvertedColors("Caută", () -> {
            String numeProdus = textFieldNume.getText();
            if (numeProdus.isEmpty()) {
                JOptionPane.showMessageDialog(cardPanel, "Introduceți un nume de produs!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ProdusDAO produsDAO = new ProdusDAO();
                List<Produs> rezultate = produsDAO.findProduseByName(numeProdus);

                if (rezultate.isEmpty()) {
                    textAreaRezultate.setText("Nu au fost găsite produse pentru numele introdus.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Produs produs : rezultate) {
                        sb.append(produs.toString()).append("\n"); // Foloseste toString() direct
                    }
                    textAreaRezultate.setText(sb.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(cardPanel, "A apărut o eroare în timpul căutării!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(btnCauta, gbc);

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(250, 161, 117));
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints sideGbc = new GridBagConstraints();
        sideGbc.gridx = 0;
        sideGbc.gridy = GridBagConstraints.RELATIVE;
        sideGbc.insets = new Insets(10, 5, 10, 5);
        sideGbc.anchor = GridBagConstraints.NORTH;

        sideGbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), sideGbc);
        sideGbc.weighty = 0;
        sideGbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), sideGbc);

        panouCautareDupaNume.add(titleLabel, BorderLayout.NORTH);
        panouCautareDupaNume.add(sidePanel, BorderLayout.WEST);
        panouCautareDupaNume.add(mainPanel, BorderLayout.CENTER);

        return panouCautareDupaNume;
    }

    /**
     * Metoda pentru crearea panoului care cauta dupa categorie
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel creeazaPanouCautareDupaCategorie(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panouCautareDupaCategorie = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Caută un produs după categorie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(53, 65, 90));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        JLabel labelCategorie = new JLabel("Categorie Produs:");
        labelCategorie.setForeground(new Color(250, 161, 117));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(labelCategorie, gbc);

        JTextField textFieldCategorie = new JTextField(20);
        textFieldCategorie.setBackground(new Color(75, 85, 110));
        textFieldCategorie.setForeground(new Color(250, 161, 117));
        textFieldCategorie.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(textFieldCategorie, gbc);

        JTextArea textAreaRezultate = new JTextArea(15, 52);
        textAreaRezultate.setBackground(new Color(75, 85, 110));
        textAreaRezultate.setForeground(new Color(250, 161, 117));
        textAreaRezultate.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
        textAreaRezultate.setEditable(false);
        JScrollPane scrollPane = createCustomScrollPane(textAreaRezultate);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        mainPanel.add(scrollPane, gbc);

        JButton btnCauta = createRoundedButtonInvertedColors("Caută", () -> {
            String numeCategorie = textFieldCategorie.getText();
            if (numeCategorie.isEmpty()) {
                JOptionPane.showMessageDialog(cardPanel, "Introduceți un nume de categorie!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                ProdusDAO produsDAO = new ProdusDAO();
                List<Produs> rezultate = produsDAO.findProduseByCategorie(numeCategorie);

                if (rezultate.isEmpty()) {
                    textAreaRezultate.setText("Nu au fost găsite produse pentru categoria introdusă.");
                } else {
                    StringBuilder sb = new StringBuilder();
                    for (Produs produs : rezultate) {
                        sb.append(produs.toString()).append("\n");
                    }
                    textAreaRezultate.setText(sb.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(cardPanel, "A apărut o eroare în timpul căutării!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        mainPanel.add(btnCauta, gbc);

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(250, 161, 117));
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints sideGbc = new GridBagConstraints();
        sideGbc.gridx = 0;
        sideGbc.gridy = GridBagConstraints.RELATIVE;
        sideGbc.insets = new Insets(10, 5, 10, 5);
        sideGbc.anchor = GridBagConstraints.NORTH;

        sideGbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), sideGbc);
        sideGbc.weighty = 0;
        sideGbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), sideGbc);

        panouCautareDupaCategorie.add(titleLabel, BorderLayout.NORTH);
        panouCautareDupaCategorie.add(sidePanel, BorderLayout.WEST);
        panouCautareDupaCategorie.add(mainPanel, BorderLayout.CENTER);

        return panouCautareDupaCategorie;
    }

    /**
     * Metoda care creeaza butonul custom rotunjit
     * @param text
     * @param onClick
     * @return
     */
    private static JButton createRoundedButton(String text, Runnable onClick) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                super.paintComponent(g);
                g2.dispose();
            }
        };
        button.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
        button.setForeground(new Color(250, 161, 117));
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));

        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(75, 85, 110));
                button.setForeground(new Color(247, 108, 38));
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(53, 65, 90));
                button.setForeground(new Color(250, 161, 117));
                button.repaint();
            }
        });

        if (onClick != null) {
            button.addActionListener(e -> onClick.run());
        }

        button.setBackground(new Color(53, 65, 90));
        return button;
    }

    /**
     * Metoda pentru crearea unui buton rotunjit, dar cu culori inversate
     * @param text
     * @param onClick
     * @return
     */
    private static JButton createRoundedButtonInvertedColors(String text, Runnable onClick) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                super.paintComponent(g);
                g2.dispose();
            }
        };

        Color normalBackground = new Color(250, 161, 117);
        Color normalForeground = new Color(53, 65, 90);
        Color hoverBackground = new Color(247, 108, 38);
        Color hoverForeground = new Color(75, 85, 110);

        button.setFont(new Font("JetBrains Mono", Font.BOLD, 12));
        button.setForeground(normalForeground);
        button.setBackground(normalBackground);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverBackground);
                button.setForeground(hoverForeground);
                button.repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(normalBackground);
                button.setForeground(normalForeground);
                button.repaint();
            }
        });

        if (onClick != null) {
            button.addActionListener(e -> onClick.run());
        }

        return button;
    }

    /**
     * Metoda pentru crearea unui scrollbar custom cu culorile interfetei
     * @param textArea
     * @return
     */
    private static JScrollPane createCustomScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane(textArea);

        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setUI(new CustomScrollBarUI());
        verticalScrollBar.setPreferredSize(new Dimension(12, 0));

        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setUI(new CustomScrollBarUI());
        horizontalScrollBar.setPreferredSize(new Dimension(0, 12));

        return scrollPane;
    }

    /**
     * Metoda pentru crearea panoului cu campuri pentru produse alimentare
     * @return
     */
    private static JPanel creeazaPanouAlimentar() {
        JPanel panelAlimentar = new JPanel(new GridBagLayout());
        panelAlimentar.setBackground(new Color(53, 65, 90));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblDataExpirarii = new JLabel("Data Expirării (YYYY-MM-DD):");
        lblDataExpirarii.setForeground(new Color(250, 161, 117));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelAlimentar.add(lblDataExpirarii, gbc);

        JTextField tfDataExpirarii = new JTextField(15);
        tfDataExpirarii.setBackground(new Color(75, 85, 110));
        tfDataExpirarii.setForeground(new Color(250, 161, 117));
        tfDataExpirarii.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        gbc.gridx = 1;
        panelAlimentar.add(tfDataExpirarii, gbc);

        JLabel lblGreutate = new JLabel("Greutate (g):");
        lblGreutate.setForeground(new Color(250, 161, 117));
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelAlimentar.add(lblGreutate, gbc);

        JTextField tfGreutate = new JTextField(15);
        tfGreutate.setBackground(new Color(75, 85, 110));
        tfGreutate.setForeground(new Color(250, 161, 117));
        tfGreutate.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        gbc.gridx = 1;
        panelAlimentar.add(tfGreutate, gbc);

        JTextField[] fieldsAlimentar = {tfDataExpirarii, tfGreutate};
        panelAlimentar.putClientProperty("textFields", fieldsAlimentar);

        return panelAlimentar;
    }

    /**
     * Metoda pentru crearea panoului cu campurile specifice produselor electronice
     * @return
     */
    private static JPanel creeazaPanouElectronic() {
        JPanel panelElectronic = new JPanel(new GridBagLayout());
        panelElectronic.setBackground(new Color(53, 65, 90));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        String[] labels = {"Lățime (cm):", "Lungime (cm):", "Grosime (cm):", "Greutate (g):"};
        JTextField[] textFields = new JTextField[labels.length];

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(new Color(250, 161, 117));
            label.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = i;
            panelElectronic.add(label, gbc);

            textFields[i] = new JTextField(15);
            textFields[i].setBackground(new Color(75, 85, 110));
            textFields[i].setForeground(new Color(250, 161, 117));
            textFields[i].setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
            gbc.gridx = 1;
            panelElectronic.add(textFields[i], gbc);
        }

        panelElectronic.putClientProperty("textFields", textFields);

        return panelElectronic;
    }

    /**
     * Metoda pentru crearea panoului cu campurile specifice produselor electrocasnice
     * @return
     */
    private static JPanel creeazaPanouElectrocasnic() {
        JPanel panelElectrocasnic = new JPanel(new GridBagLayout());
        panelElectrocasnic.setBackground(new Color(53, 65, 90));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel labelClasaEnergetica = new JLabel("Clasa Energetică:");
        labelClasaEnergetica.setForeground(new Color(250, 161, 117));
        labelClasaEnergetica.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelElectrocasnic.add(labelClasaEnergetica, gbc);

        JComboBox<String> dropdownClasaEnergetica = new JComboBox<>(new String[]{"A", "B", "C", "D", "E", "F", "G"});
        dropdownClasaEnergetica.setBackground(new Color(75, 85, 110));
        dropdownClasaEnergetica.setForeground(new Color(250, 161, 117));
        dropdownClasaEnergetica.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        dropdownClasaEnergetica.setPreferredSize(new Dimension(200, 25)); // Dimensiune standard
        panelElectrocasnic.putClientProperty("dropdownClasaEnergetica", dropdownClasaEnergetica);

        gbc.gridx = 1;
        gbc.gridy = 0;
        panelElectrocasnic.add(dropdownClasaEnergetica, gbc);

        panelElectrocasnic.putClientProperty("dropdownClasaEnergetica", dropdownClasaEnergetica);

        return panelElectrocasnic;
    }

    /**
     * Metoda pentru crearea panoului cu campurile specifice produselor electrocasnice
     * @return
     */
    private static JPanel creeazaPanouIgienaCuratare() {
        JPanel panelIgienaCuratare = new JPanel(new GridBagLayout());
        panelIgienaCuratare.setBackground(new Color(53, 65, 90));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblGreutate = new JLabel("Greutate (g):");
        lblGreutate.setForeground(new Color(250, 161, 117));
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelIgienaCuratare.add(lblGreutate, gbc);

        JTextField tfGreutate = new JTextField(15);
        tfGreutate.setBackground(new Color(75, 85, 110));
        tfGreutate.setForeground(new Color(250, 161, 117));
        tfGreutate.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        gbc.gridx = 1;
        panelIgienaCuratare.add(tfGreutate, gbc);

        JTextField[] fieldsIgienaCuratare = {tfGreutate};
        panelIgienaCuratare.putClientProperty("textFields", fieldsIgienaCuratare);

        return panelIgienaCuratare;
    }

    /**
     * Metoda pentru crearea panoului de adaugare de produse
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel creeazaPanouAdaugaProdus(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panouAdaugaProdus = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Adaugă un produs", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(53, 65, 90));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        String[] labels = {"Nume produs:", "Cantitate:", "Preț:", "Categorie:", "Furnizor:", "Raft:", "Coloană:", "Tip produs:"};
        JTextField[] textFields = new JTextField[labels.length - 1];
        JComboBox<String> comboBoxTipProdus = new JComboBox<>(new String[]{"alimentar", "electronic", "electrocasnic", "igiena_curatare"});

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(new Color(250, 161, 117));
            label.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weighty = 0.0;
            mainPanel.add(label, gbc);

            if (i < labels.length - 1) {
                textFields[i] = new JTextField(20);
                textFields[i].setBackground(new Color(75, 85, 110));
                textFields[i].setForeground(new Color(250, 161, 117));
                textFields[i].setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
                textFields[i].setPreferredSize(new Dimension(200, 25)); // Dimensiune max
                gbc.gridx = 1;
                mainPanel.add(textFields[i], gbc);
            } else {
                comboBoxTipProdus.setBackground(new Color(75, 85, 110));
                comboBoxTipProdus.setForeground(new Color(250, 161, 117));
                comboBoxTipProdus.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
                comboBoxTipProdus.setPreferredSize(new Dimension(200, 25)); // Dimensiune max
                gbc.gridx = 1;
                mainPanel.add(comboBoxTipProdus, gbc);
            }
        }

        JPanel panouInformatiiSuplimentare = new JPanel();
        panouInformatiiSuplimentare.setBackground(new Color(53, 65, 90));
        panouInformatiiSuplimentare.setLayout(new CardLayout());

        JPanel panelAlimentar = creeazaPanouAlimentar();
        JPanel panelElectronic = creeazaPanouElectronic();
        JPanel panelElectrocasnic = creeazaPanouElectrocasnic();
        JPanel panelIgienaCuratare = creeazaPanouIgienaCuratare();

        panouInformatiiSuplimentare.add(panelAlimentar, "alimentar");
        panouInformatiiSuplimentare.add(panelElectronic, "electronic");
        panouInformatiiSuplimentare.add(panelElectrocasnic, "electrocasnic");
        panouInformatiiSuplimentare.add(panelIgienaCuratare, "igiena_curatare");

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(panouInformatiiSuplimentare, gbc);

        comboBoxTipProdus.addActionListener(e -> {
            CardLayout cl = (CardLayout) panouInformatiiSuplimentare.getLayout();
            cl.show(panouInformatiiSuplimentare, (String) comboBoxTipProdus.getSelectedItem());
        });

        JButton btnAdauga = createRoundedButtonInvertedColors("Adaugă produs", () -> {
            String tipProdus = (String) comboBoxTipProdus.getSelectedItem();

            try {
                CategorieDAO categorieDAO = new CategorieDAO();
                FurnizorDAO furnizorDAO = new FurnizorDAO();

                String numeProdus = textFields[0].getText();
                int cantitate = Integer.parseInt(textFields[1].getText());
                double pret = Double.parseDouble(textFields[2].getText());
                Categorie categorie = categorieDAO.findCategorieByName(textFields[3].getText());
                Furnizor furnizor = furnizorDAO.findFurnizorByName(textFields[4].getText());
                int raft = Integer.parseInt(textFields[5].getText());
                int coloana = Integer.parseInt(textFields[6].getText());

                switch (tipProdus) {
                    case "alimentar": {
                        JTextField[] fieldsAlimentar = (JTextField[]) panelAlimentar.getClientProperty("textFields");
                        java.sql.Date dataExpirarii = java.sql.Date.valueOf(fieldsAlimentar[0].getText());
                        int greutate = Integer.parseInt(fieldsAlimentar[1].getText());

                        ProdusAlimentar produsAlimentar = new ProdusAlimentar(
                                numeProdus, cantitate, pret, categorie, furnizor, new Locatie(raft, coloana),
                                dataExpirarii, greutate);
                        ProdusAlimentarDAO produsAlimentarDAO = new ProdusAlimentarDAO();
                        produsAlimentarDAO.insertProdusAlimentar(produsAlimentar);
                        break;
                    }
                    case "electronic": {
                        JTextField[] fieldsElectronic = (JTextField[]) panelElectronic.getClientProperty("textFields");
                        double latime = Double.parseDouble(fieldsElectronic[0].getText());
                        double lungime = Double.parseDouble(fieldsElectronic[1].getText());
                        double grosime = Double.parseDouble(fieldsElectronic[2].getText());
                        int greutate = Integer.parseInt(fieldsElectronic[3].getText());

                        ProdusElectronic produsElectronic = new ProdusElectronic(
                                numeProdus, cantitate, pret, categorie, furnizor, new Locatie(raft,coloana),
                                latime, lungime, grosime, greutate);
                        ProdusElectronicDAO produsElectronicDAO = new ProdusElectronicDAO();
                        produsElectronicDAO.insertProdusElectronic(produsElectronic);
                        break;
                    }
                    case "electrocasnic": {
                        JComboBox<String> dropdownClasaEnergetica = (JComboBox<String>) panelElectrocasnic.getClientProperty("dropdownClasaEnergetica");
                        String clasaEnergetica = (String) dropdownClasaEnergetica.getSelectedItem();

                        ProdusElectrocasnic produsElectrocasnic = new ProdusElectrocasnic(
                                numeProdus, cantitate, pret, categorie, furnizor, new Locatie(raft,coloana),
                                ClasaEnergetica.valueOf(clasaEnergetica));
                        ProdusElectrocasnicDAO produsElectrocasnicDAO = new ProdusElectrocasnicDAO();
                        produsElectrocasnicDAO.insertProdusElectrocasnic(produsElectrocasnic);
                        break;
                    }
                    case "igiena_curatare": {
                        JTextField[] fieldsIgienaCuratare = (JTextField[]) panelIgienaCuratare.getClientProperty("textFields");
                        double greutate = Double.parseDouble(fieldsIgienaCuratare[0].getText());

                        ProdusIgienaCuratare produsIgienaCuratare = new ProdusIgienaCuratare(
                                numeProdus, cantitate, pret, categorie, furnizor,new Locatie(raft,coloana),
                                greutate);
                        ProdusIgienaCuratareDAO produsIgienaCuratareDAO = new ProdusIgienaCuratareDAO();
                        produsIgienaCuratareDAO.insertProdusIgienaCuratare(produsIgienaCuratare);
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Tip produs necunoscut: " + tipProdus);
                }

                JOptionPane.showMessageDialog(cardPanel, "Produsul a fost adăugat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(cardPanel, "Eroare la adăugarea produsului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weighty = 0.0;
        mainPanel.add(btnAdauga, gbc);

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(250, 161, 117));
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints sideGbc = new GridBagConstraints();
        sideGbc.gridx = 0;
        sideGbc.gridy = GridBagConstraints.RELATIVE;
        sideGbc.insets = new Insets(10, 5, 10, 5);
        sideGbc.anchor = GridBagConstraints.NORTH;

        sideGbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), sideGbc);
        sideGbc.weighty = 0;
        sideGbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), sideGbc);

        panouAdaugaProdus.add(titleLabel, BorderLayout.NORTH);
        panouAdaugaProdus.add(sidePanel, BorderLayout.WEST);
        panouAdaugaProdus.add(mainPanel, BorderLayout.CENTER);

        return panouAdaugaProdus;
    }

    /**
     * Metoda pentru crearea panoului de stergere a unui produs
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel creeazaPanouStergereProdus(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panouStergereProdus = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Șterge un produs", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(53, 65, 90));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel labelNume = new JLabel("Nume produs:");
        labelNume.setForeground(new Color(250, 161, 117));
        labelNume.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(labelNume, gbc);

        JTextField textFieldNume = new JTextField(20);
        textFieldNume.setBackground(new Color(75, 85, 110));
        textFieldNume.setForeground(new Color(250, 161, 117));
        textFieldNume.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        textFieldNume.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        mainPanel.add(textFieldNume, gbc);

        JLabel labelCantitate = new JLabel("Cantitate de șters:");
        labelCantitate.setForeground(new Color(250, 161, 117));
        labelCantitate.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(labelCantitate, gbc);

        JTextField textFieldCantitate = new JTextField(20);
        textFieldCantitate.setBackground(new Color(75, 85, 110));
        textFieldCantitate.setForeground(new Color(250, 161, 117));
        textFieldCantitate.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        textFieldCantitate.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        mainPanel.add(textFieldCantitate, gbc);

        JLabel labelTipProdus = new JLabel("Tip produs:");
        labelTipProdus.setForeground(new Color(250, 161, 117));
        labelTipProdus.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(labelTipProdus, gbc);

        JComboBox<String> comboBoxTipProdus = new JComboBox<>(new String[]{"alimentar", "electronic", "electrocasnic", "igiena_curatare"});
        comboBoxTipProdus.setBackground(new Color(75, 85, 110));
        comboBoxTipProdus.setForeground(new Color(250, 161, 117));
        comboBoxTipProdus.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        comboBoxTipProdus.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        mainPanel.add(comboBoxTipProdus, gbc);

        JButton btnSterge = createRoundedButtonInvertedColors("Șterge produs", () -> {
            String numeProdus = textFieldNume.getText();
            String cantitateText = textFieldCantitate.getText();
            String tipProdus = (String) comboBoxTipProdus.getSelectedItem();

            try {
                int cantitateDeSters = Integer.parseInt(cantitateText);
                switch (tipProdus) {
                    case "alimentar":
                        ProdusAlimentarDAO produsAlimentarDAO = new ProdusAlimentarDAO();
                        produsAlimentarDAO.deleteProdusAlimentarByName(numeProdus, cantitateDeSters);
                        break;
                    case "electronic":
                        ProdusElectronicDAO produsElectronicDAO = new ProdusElectronicDAO();
                        produsElectronicDAO.deleteProdusElectronicByName(numeProdus, cantitateDeSters);
                        break;
                    case "electrocasnic":
                        ProdusElectrocasnicDAO produsElectrocasnicDAO = new ProdusElectrocasnicDAO();
                        produsElectrocasnicDAO.deleteProdusElectrocasnicByName(numeProdus, cantitateDeSters);
                        break;
                    case "igiena_curatare":
                        ProdusIgienaCuratareDAO produsIgienaCuratareDAO = new ProdusIgienaCuratareDAO();
                        produsIgienaCuratareDAO.deleteProdusIgienaCuratareByName(numeProdus, cantitateDeSters);
                        break;
                    default:
                        throw new IllegalArgumentException("Tip produs necunoscut: " + tipProdus);
                }

                JOptionPane.showMessageDialog(cardPanel, "Produsul a fost șters sau actualizat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(cardPanel, "Eroare la ștergerea produsului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        mainPanel.add(btnSterge, gbc);

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(250, 161, 117));
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints sideGbc = new GridBagConstraints();
        sideGbc.gridx = 0;
        sideGbc.gridy = GridBagConstraints.RELATIVE;
        sideGbc.insets = new Insets(10, 5, 10, 5);
        sideGbc.anchor = GridBagConstraints.NORTH;

        sideGbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), sideGbc);
        sideGbc.weighty = 0;
        sideGbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), sideGbc);

        panouStergereProdus.add(titleLabel, BorderLayout.NORTH);
        panouStergereProdus.add(sidePanel, BorderLayout.WEST);
        panouStergereProdus.add(mainPanel, BorderLayout.CENTER);

        return panouStergereProdus;
    }

    /**
     * Metoda pentru crearea panoului de adaugare a unei categorii
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel creeazaPanouAdaugaCategorie(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panouAdaugaCategorie = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Adaugă o categorie", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(53, 65, 90));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        JLabel labelNumeCategorie = new JLabel("Nume categorie:");
        labelNumeCategorie.setForeground(new Color(250, 161, 117));
        labelNumeCategorie.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(labelNumeCategorie, gbc);

        JTextField textFieldNumeCategorie = new JTextField(20);
        textFieldNumeCategorie.setBackground(new Color(75, 85, 110));
        textFieldNumeCategorie.setForeground(new Color(250, 161, 117));
        textFieldNumeCategorie.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
        textFieldNumeCategorie.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        mainPanel.add(textFieldNumeCategorie, gbc);

        JButton btnAdaugaCategorie = createRoundedButtonInvertedColors("Adaugă categorie", () -> {
            String numeCategorie = textFieldNumeCategorie.getText();

            if (numeCategorie.isEmpty()) {
                JOptionPane.showMessageDialog(cardPanel, "Introduceți un nume pentru categorie!", "Eroare", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                CategorieDAO categorieDAO = new CategorieDAO();
                categorieDAO.insertCategorieByName(numeCategorie);
                JOptionPane.showMessageDialog(cardPanel, "Categoria a fost adăugată cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(cardPanel, e.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(btnAdaugaCategorie, gbc);

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(250, 161, 117));
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints sideGbc = new GridBagConstraints();
        sideGbc.gridx = 0;
        sideGbc.gridy = GridBagConstraints.RELATIVE;
        sideGbc.insets = new Insets(10, 5, 10, 5);
        sideGbc.anchor = GridBagConstraints.NORTH;

        sideGbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), sideGbc);
        sideGbc.weighty = 0;
        sideGbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), sideGbc);

        panouAdaugaCategorie.add(titleLabel, BorderLayout.NORTH);
        panouAdaugaCategorie.add(sidePanel, BorderLayout.WEST);
        panouAdaugaCategorie.add(mainPanel, BorderLayout.CENTER);

        return panouAdaugaCategorie;
    }

    /**
     * Metoda pentru crearea panoului de vizualizare a furnizorilor
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel creeazaPanouVeziFurnizori(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panouVeziFurnizori = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Lista Furnizorilor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(53, 65, 90));

        JTextArea textAreaFurnizori = new JTextArea();
        textAreaFurnizori.setEditable(false);
        textAreaFurnizori.setBackground(new Color(75, 85, 110));
        textAreaFurnizori.setForeground(new Color(250, 161, 117));
        textAreaFurnizori.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(textAreaFurnizori);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));

        try {
            FurnizorDAO furnizorDAO = new FurnizorDAO();
            List<Furnizor> furnizori = furnizorDAO.findAllFurnizori();

            if (furnizori.isEmpty()) {
                textAreaFurnizori.setText("Nu există furnizori în baza de date.");
            } else {
                StringBuilder sb = new StringBuilder();
                for (Furnizor furnizor : furnizori) {
                    sb.append(furnizor.toString()).append("\n\n");
                }
                textAreaFurnizori.setText(sb.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            textAreaFurnizori.setText("A apărut o eroare în timpul încărcării furnizorilor.");
        }

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setBackground(new Color(250, 161, 117));

        GridBagConstraints sideGbc = new GridBagConstraints();
        sideGbc.gridx = 0;
        sideGbc.gridy = GridBagConstraints.RELATIVE;
        sideGbc.insets = new Insets(10, 5, 10, 5);
        sideGbc.anchor = GridBagConstraints.NORTH;

        sideGbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), sideGbc);
        sideGbc.weighty = 0;
        sideGbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), sideGbc);

        panouVeziFurnizori.add(titleLabel, BorderLayout.NORTH);
        panouVeziFurnizori.add(sidePanel, BorderLayout.WEST);
        panouVeziFurnizori.add(mainPanel, BorderLayout.CENTER);

        return panouVeziFurnizori;
    }

    /**
     * Metoda pentru crearea panoului de vizualizare a produselor
     * @param cardLayout
     * @param cardPanel
     * @return
     */
    private static JPanel creeazaPanouVeziProduse(CardLayout cardLayout, JPanel cardPanel) {
        JPanel panouVeziProduse = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Lista Produselor", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(53, 65, 90));

        DefaultListModel<Produs> listModel = new DefaultListModel<>();
        JList<Produs> productList = new JList<>(listModel);
        productList.setCellRenderer(new ListCellRenderer<>() {
            @Override
            public Component getListCellRendererComponent(JList<? extends Produs> list, Produs value, int index, boolean isSelected, boolean cellHasFocus) {
                JTextArea textArea = new JTextArea(value.toString());
                textArea.setWrapStyleWord(true);
                textArea.setLineWrap(true);
                textArea.setEditable(false);
                textArea.setFont(new Font("JetBrains Mono", Font.PLAIN, 14));
                textArea.setBackground(isSelected ? new Color(53, 65, 90) : new Color(75, 85, 110));
                textArea.setForeground(isSelected ? new Color(250, 161, 117) : new Color(250, 161, 117));
                textArea.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), isSelected ? 2 : 1));
                return textArea;
            }
        });

        JScrollPane scrollPane = new JScrollPane(productList);
        scrollPane.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(12, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 12));

        try {
            ProdusDAO produsDAO = new ProdusDAO();
            List<Produs> produse = produsDAO.findAllProduse();

            if (produse.isEmpty()) {
                listModel.addElement(null);
            } else {
                for (Produs produs : produse) {
                    listModel.addElement(produs);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            listModel.addElement(null);
        }

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel sidePanel = new JPanel(new GridBagLayout());
        sidePanel.setBackground(new Color(250, 161, 117));

        GridBagConstraints sideGbc = new GridBagConstraints();
        sideGbc.gridx = 0;
        sideGbc.gridy = GridBagConstraints.RELATIVE;
        sideGbc.insets = new Insets(10, 5, 10, 5);
        sideGbc.anchor = GridBagConstraints.NORTH;

        JButton btnActualizeazaProdus = createRoundedButton("Actualizează", () -> {
            Produs selectedProduct = productList.getSelectedValue();
            if (selectedProduct != null) {
                JPanel panouActualizeazaProdus = creeazaPanouActualizeazaProdus(cardLayout, cardPanel, selectedProduct);
                cardPanel.add(panouActualizeazaProdus, "Actualizeaza Produs");
                cardLayout.show(cardPanel, "Actualizeaza Produs");
            } else {
                JOptionPane.showMessageDialog(cardPanel, "Selectați un produs pentru actualizare!", "Eroare", JOptionPane.WARNING_MESSAGE);
            }
        });
        sideGbc.gridy++;
        sidePanel.add(btnActualizeazaProdus, sideGbc);

        sideGbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), sideGbc);
        sideGbc.weighty = 0;
        sideGbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), sideGbc);

        panouVeziProduse.add(titleLabel, BorderLayout.NORTH);
        panouVeziProduse.add(sidePanel, BorderLayout.WEST);
        panouVeziProduse.add(mainPanel, BorderLayout.CENTER);

        return panouVeziProduse;
    }

    /**
     * Metoda pentru crearea panoului pentru actualizarea informatiilor unui produs
     * @param cardLayout
     * @param cardPanel
     * @param produsSelectat
     * @return
     */
    private static JPanel creeazaPanouActualizeazaProdus(CardLayout cardLayout, JPanel cardPanel, Produs produsSelectat) {
        JPanel panouActualizeazaProdus = new JPanel(new BorderLayout());

        JLabel titleLabel = new JLabel("Actualizează un produs", SwingConstants.CENTER);
        titleLabel.setFont(new Font("JetBrains Mono", Font.BOLD, 20));
        titleLabel.setOpaque(true);
        titleLabel.setBackground(new Color(250, 161, 117));
        titleLabel.setForeground(new Color(53, 65, 90));
        titleLabel.setPreferredSize(new Dimension(800, 50));

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(53, 65, 90));
        mainPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        String[] labels = {"Nume produs:", "Cantitate:", "Preț:", "Categorie:", "Furnizor:", "Raft:", "Coloană:", "Tip produs:"};
        JTextField[] textFields = new JTextField[labels.length - 1];
        JComboBox<String> comboBoxTipProdus = new JComboBox<>(new String[]{"alimentar", "electronic", "electrocasnic", "igiena_curatare"});

        JPanel panouInformatiiSuplimentare = new JPanel();
        panouInformatiiSuplimentare.setBackground(new Color(53, 65, 90));
        panouInformatiiSuplimentare.setLayout(new CardLayout());

        JPanel panelAlimentar = creeazaPanouAlimentar();
        JPanel panelElectronic = creeazaPanouElectronic();
        JPanel panelElectrocasnic = creeazaPanouElectrocasnic();
        JPanel panelIgienaCuratare = creeazaPanouIgienaCuratare();

        panouInformatiiSuplimentare.add(panelAlimentar, "alimentar");
        panouInformatiiSuplimentare.add(panelElectronic, "electronic");
        panouInformatiiSuplimentare.add(panelElectrocasnic, "electrocasnic");
        panouInformatiiSuplimentare.add(panelIgienaCuratare, "igiena_curatare");

        for (int i = 0; i < labels.length; i++) {
            JLabel label = new JLabel(labels[i]);
            label.setForeground(new Color(250, 161, 117));
            label.setFont(new Font("JetBrains Mono", Font.BOLD, 14));
            gbc.gridx = 0;
            gbc.gridy = i;
            gbc.weighty = 0.0;
            mainPanel.add(label, gbc);

            if (i < labels.length - 1) {
                textFields[i] = new JTextField(20);
                textFields[i].setBackground(new Color(75, 85, 110));
                textFields[i].setForeground(new Color(250, 161, 117));
                textFields[i].setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
                textFields[i].setPreferredSize(new Dimension(200, 25));
                gbc.gridx = 1;
                mainPanel.add(textFields[i], gbc);
            } else {
                comboBoxTipProdus.setBackground(new Color(75, 85, 110));
                comboBoxTipProdus.setForeground(new Color(250, 161, 117));
                comboBoxTipProdus.setBorder(BorderFactory.createLineBorder(new Color(250, 161, 117), 2));
                comboBoxTipProdus.setPreferredSize(new Dimension(200, 25));
                gbc.gridx = 1;
                mainPanel.add(comboBoxTipProdus, gbc);
            }
        }

        textFields[0].setText(produsSelectat.getNumeProdus());
        textFields[1].setText(String.valueOf(produsSelectat.getCantitate()));
        textFields[2].setText(String.valueOf(produsSelectat.getPret()));
        textFields[3].setText(produsSelectat.getCategorie().getNumeCategorie());
        textFields[4].setText(produsSelectat.getFurnizor().getNumeFurnizor());
        textFields[5].setText(String.valueOf(produsSelectat.getLoc().getRaft()));
        textFields[6].setText(String.valueOf(produsSelectat.getLoc().getColoana()));
        try {
            ProdusDAO produsDAO = new ProdusDAO();
            String tipProdus = produsDAO.getTipProdusById(produsSelectat.getIdProdus());

            if (tipProdus != null && !tipProdus.isEmpty()) {
                comboBoxTipProdus.setSelectedItem(tipProdus);

                CardLayout cl = (CardLayout) panouInformatiiSuplimentare.getLayout();
                cl.show(panouInformatiiSuplimentare, tipProdus);

                switch (tipProdus) {
                    case "alimentar":
                        JTextField[] fieldsAlimentar = (JTextField[]) panelAlimentar.getClientProperty("textFields");
                        ProdusAlimentar produsAlimentar = (ProdusAlimentar) produsSelectat;
                        fieldsAlimentar[0].setText(produsAlimentar.getDataExpirarii().toString());
                        fieldsAlimentar[1].setText(String.valueOf(produsAlimentar.getGreutate()));
                        break;

                    case "electronic":
                        JTextField[] fieldsElectronic = (JTextField[]) panelElectronic.getClientProperty("textFields");
                        ProdusElectronic produsElectronic = (ProdusElectronic) produsSelectat;
                        fieldsElectronic[0].setText(String.valueOf(produsElectronic.getLatime()));
                        fieldsElectronic[1].setText(String.valueOf(produsElectronic.getLungime()));
                        fieldsElectronic[2].setText(String.valueOf(produsElectronic.getGrosime()));
                        fieldsElectronic[3].setText(String.valueOf(produsElectronic.getGreutate()));
                        break;

                    case "electrocasnic":
                        JComboBox<String> dropdownClasaEnergetica = (JComboBox<String>) panelElectrocasnic.getClientProperty("dropdownClasaEnergetica");
                        ProdusElectrocasnic produsElectrocasnic = (ProdusElectrocasnic) produsSelectat;
                        dropdownClasaEnergetica.setSelectedItem(produsElectrocasnic.getClasaEnergetica().name());
                        break;

                    case "igiena_curatare":
                        JTextField[] fieldsIgienaCuratare = (JTextField[]) panelIgienaCuratare.getClientProperty("textFields");
                        ProdusIgienaCuratare produsIgienaCuratare = (ProdusIgienaCuratare) produsSelectat;
                        fieldsIgienaCuratare[0].setText(String.valueOf(produsIgienaCuratare.getGreutate()));
                        break;

                    default:
                        JOptionPane.showMessageDialog(cardPanel, "Tip produs necunoscut!", "Eroare", JOptionPane.ERROR_MESSAGE);
                        break;
                }
            } else {
                JOptionPane.showMessageDialog(cardPanel, "Tip produs necunoscut sau invalid!", "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(cardPanel, "Eroare la determinarea tipului produsului!", "Eroare", JOptionPane.ERROR_MESSAGE);
        }

        gbc.gridx = 0;
        gbc.gridy = labels.length;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        mainPanel.add(panouInformatiiSuplimentare, gbc);

        comboBoxTipProdus.addActionListener(e -> {
            CardLayout cl = (CardLayout) panouInformatiiSuplimentare.getLayout();
            cl.show(panouInformatiiSuplimentare, (String) comboBoxTipProdus.getSelectedItem());
        });

        JButton btnSalveaza = createRoundedButtonInvertedColors("Salvează modificările", () -> {
            String tipProdus = (String) comboBoxTipProdus.getSelectedItem();

            try {
                ProdusDAO produsDAO = new ProdusDAO();
                CategorieDAO categorieDAO = new CategorieDAO();
                FurnizorDAO furnizorDAO = new FurnizorDAO();

                produsSelectat.setNumeProdus(textFields[0].getText());
                produsSelectat.setCantitate(Integer.parseInt(textFields[1].getText()));
                produsSelectat.setPret(Double.parseDouble(textFields[2].getText()));
                produsSelectat.setCategorie(categorieDAO.findCategorieByName(textFields[3].getText()));
                produsSelectat.setFurnizor(furnizorDAO.findFurnizorByName(textFields[4].getText()));
                produsSelectat.getLoc().setRaft(Integer.parseInt(textFields[5].getText()));
                produsSelectat.getLoc().setColoana(Integer.parseInt(textFields[6].getText()));

                switch (tipProdus) {
                    case "alimentar": {
                        JTextField[] fieldsAlimentar = (JTextField[]) panelAlimentar.getClientProperty("textFields");
                        ((ProdusAlimentar) produsSelectat).setDataExpirarii(java.sql.Date.valueOf(fieldsAlimentar[0].getText()));
                        ((ProdusAlimentar) produsSelectat).setGreutate(Integer.parseInt(fieldsAlimentar[1].getText()));
                        break;
                    }
                    case "electronic": {
                        JTextField[] fieldsElectronic = (JTextField[]) panelElectronic.getClientProperty("textFields");
                        ((ProdusElectronic) produsSelectat).setLatime(Double.parseDouble(fieldsElectronic[0].getText()));
                        ((ProdusElectronic) produsSelectat).setLungime(Double.parseDouble(fieldsElectronic[1].getText()));
                        ((ProdusElectronic) produsSelectat).setGrosime(Double.parseDouble(fieldsElectronic[2].getText()));
                        ((ProdusElectronic) produsSelectat).setGreutate(Integer.parseInt(fieldsElectronic[3].getText()));
                        break;
                    }
                    case "electrocasnic": {
                        JComboBox<String> dropdownClasaEnergetica = (JComboBox<String>) panelElectrocasnic.getClientProperty("dropdownClasaEnergetica");
                        ((ProdusElectrocasnic) produsSelectat).setClasaEnergetica(ClasaEnergetica.valueOf((String) dropdownClasaEnergetica.getSelectedItem()));
                        break;
                    }
                    case "igiena_curatare": {
                        JTextField[] fieldsIgienaCuratare = (JTextField[]) panelIgienaCuratare.getClientProperty("textFields");
                        ((ProdusIgienaCuratare) produsSelectat).setGreutate(Double.parseDouble(fieldsIgienaCuratare[0].getText()));
                        break;
                    }
                    default:
                        throw new IllegalArgumentException("Tip produs necunoscut: " + tipProdus);
                }

                produsDAO.actualizeazaProdus(produsSelectat, tipProdus);
                JOptionPane.showMessageDialog(cardPanel, "Produsul a fost actualizat cu succes!", "Succes", JOptionPane.INFORMATION_MESSAGE);

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(cardPanel, "Eroare la actualizarea produsului: " + ex.getMessage(), "Eroare", JOptionPane.ERROR_MESSAGE);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = labels.length + 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.weighty = 0.0;
        mainPanel.add(btnSalveaza, gbc);

        JPanel sidePanel = new JPanel();
        sidePanel.setBackground(new Color(250, 161, 117));
        sidePanel.setLayout(new GridBagLayout());

        GridBagConstraints sideGbc = new GridBagConstraints();
        sideGbc.gridx = 0;
        sideGbc.gridy = GridBagConstraints.RELATIVE;
        sideGbc.insets = new Insets(10, 5, 10, 5);
        sideGbc.anchor = GridBagConstraints.NORTH;

        sideGbc.weighty = 1.0;
        sidePanel.add(Box.createVerticalGlue(), sideGbc);
        sideGbc.weighty = 0;
        sideGbc.anchor = GridBagConstraints.SOUTH;
        sidePanel.add(createRoundedButton("Înapoi la meniu", () -> cardLayout.show(cardPanel, "Main Menu")), sideGbc);

        panouActualizeazaProdus.add(titleLabel, BorderLayout.NORTH);
        panouActualizeazaProdus.add(sidePanel, BorderLayout.WEST);
        panouActualizeazaProdus.add(mainPanel, BorderLayout.CENTER);

        return panouActualizeazaProdus;
    }
}
