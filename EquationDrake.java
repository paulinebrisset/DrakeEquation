package tp2;

import java.awt.*;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.event.*;

public class EquationDrake {
    private JFrame frame;
    private JPanel panel;
    private GridBagConstraints constraints;

    private JLabel labelTitle, labelFormula, labelR, labelFp, labelNe, labelFl, labelFi, labelFc, labelL, labelResult;
    private JSlider sliderR, sliderFp, sliderNe, sliderFl, sliderFi, sliderFc, sliderL;
    private JTextField textFieldR, textFieldFp, textFieldNe, textFieldFl, textFieldFi, textFieldFc, textFieldL,
            resultField;
    private JButton startButton, stopButton;
    private SwingWorker<Void, Void> simulationWorker;
    private Timer timer;

    public EquationDrake() {
        frame = new JFrame("Équation de Drake");
        frame.setPreferredSize(new Dimension(800, 600)); // Fenêtre plus grande
        panel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();

        labelTitle = new JLabel("Équation de Drake");
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelFormula = new JLabel("N = R* * fp * ne * fl * fi * fc * L");
        labelFormula.setFont(new Font("SansSerif", Font.PLAIN, 16));

        labelR = new JLabel("Taux de formation d'étoiles (R*)");
        sliderR = createSlider(100, 10000, 2000); // Plage de 0 à 100 avec valeur initiale à 20
        textFieldR = createResultField(20);

        labelFp = new JLabel("Fraction d'étoiles avec des systèmes planétaires (fp)");
        sliderFp = createSlider(0, 100, 10); // Plage de 0 à 100 avec valeur initiale à 10
        textFieldFp = createResultField(10);

        labelNe = new JLabel("Nombre moyen de planètes potentiellement habitables (ne)");
        sliderNe = createSlider(0, 100, 50); // Plage de 0 à 100 avec valeur initiale à 50
        textFieldNe = createResultField(50);

        labelFl = new JLabel("Fraction de planètes où la vie se développe (fl)");
        sliderFl = createSlider(0, 100, 50); // Plage de 0 à 100 avec valeur initiale à 50
        textFieldFl = createResultField(50);

        labelFi = new JLabel("Fraction de planètes avec une vie intelligente (fi)");
        sliderFi = createSlider(0, 100, 50); // Plage de 0 à 100 avec valeur initiale à 50
        textFieldFi = createResultField(50);

        labelFc = new JLabel("Fraction de planètes avec une civilisation technologique (fc)");
        sliderFc = createSlider(0, 100, 10); // Plage de 0 à 100 avec valeur initiale à 10
        textFieldFc = createResultField(10);

        labelL = new JLabel("Durée de communication (L)");
        sliderL = createSlider(100, 100000, 100000); // Plage de 0 à 1 000 000 avec valeur initiale à 100 000
        textFieldL = createResultField(100000);

        labelResult = new JLabel("Résultat de l'équation :");
        resultField = createResultField(0);

        startButton = new JButton("Start");
        stopButton = new JButton("Stop");

        addComponentsToPanel();
        addListeners();
        calculateResult(false); // Calcul initial lors de la création de l'instance.

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    // Interface
    private JSlider createSlider(int min, int max, int initialValue) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, initialValue);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        return slider;
    }

    private JTextField createResultField(int initialValue) {
        JTextField resultField = new JTextField(10);
        resultField.setEditable(false);
        resultField.setText(Integer.toString(initialValue));
        return resultField;
    }

    private void addComponentsToPanel() {
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        panel.add(labelTitle, constraints);

        constraints.gridy++;
        panel.add(labelFormula, constraints);

        constraints.gridy++;
        panel.add(labelR, constraints);
        constraints.gridx = 1;
        panel.add(sliderR, constraints);
        constraints.gridx = 2;
        panel.add(textFieldR, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelFp, constraints);
        constraints.gridx = 1;
        panel.add(sliderFp, constraints);
        constraints.gridx = 2;
        panel.add(textFieldFp, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelNe, constraints);
        constraints.gridx = 1;
        panel.add(sliderNe, constraints);
        constraints.gridx = 2;
        panel.add(textFieldNe, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelFl, constraints);
        constraints.gridx = 1;
        panel.add(sliderFl, constraints);
        constraints.gridx = 2;
        panel.add(textFieldFl, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelFi, constraints);
        constraints.gridx = 1;
        panel.add(sliderFi, constraints);
        constraints.gridx = 2;
        panel.add(textFieldFi, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelFc, constraints);
        constraints.gridx = 1;
        panel.add(sliderFc, constraints);
        constraints.gridx = 2;
        panel.add(textFieldFc, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelL, constraints);
        constraints.gridx = 1;
        panel.add(sliderL, constraints);
        constraints.gridx = 2;
        panel.add(textFieldL, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(labelResult, constraints);
        constraints.gridx = 1;
        panel.add(resultField, constraints);
        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(startButton, constraints);
        constraints.gridx = 1;
        panel.add(stopButton, constraints);
    }

    // Listeners
    private void addListeners() {
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                calculateResult(false);
            }
        };

        sliderR.addChangeListener(changeListener);
        sliderFp.addChangeListener(changeListener);
        sliderNe.addChangeListener(changeListener);
        sliderFl.addChangeListener(changeListener);
        sliderFi.addChangeListener(changeListener);
        sliderFc.addChangeListener(changeListener);
        sliderL.addChangeListener(changeListener);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startSimulation();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });
    }

    private void calculateResult(boolean isFromSimulation) {
        boolean nUnder1 = false;
        System.out.println("on est dans calculte result");
        double R = sliderR.getValue() / 100.0;
        double L = sliderL.getValue() / 100.0;
        double fp = sliderFp.getValue() / 100.0;
        double ne = sliderNe.getValue() / 100.0;
        double fl = sliderFl.getValue() / 100.0;
        double fi = sliderFi.getValue() / 100.0;
        double fc = sliderFc.getValue() / 100.0;
        double N = R * fp * ne * fl * fi * fc * L;

        textFieldR.setText(String.format("%.2f", R));
        textFieldFp.setText(String.format("%.2f", fp));
        textFieldNe.setText(String.format("%.2f", ne));
        textFieldFl.setText(String.format("%.2f", fl));
        textFieldFi.setText(String.format("%.2f", fi));
        textFieldFc.setText(String.format("%.2f", fc));
        textFieldL.setText(String.format("%.2f", L));
        resultField.setText(String.format("%.2f", N));

        if (N < 1) {
            nUnder1 = true;
        }
        if (N < 1 || isFromSimulation) {
            do {
                fl = generateRandomValue();
                fi = generateRandomValue();
                fc = generateRandomValue();
                N = R * fp * ne * fl * fi * fc * L;
                textFieldFl.setText(String.format("%.2f", fl));
                textFieldFi.setText(String.format("%.2f", fi));
                textFieldFc.setText(String.format("%.2f", fc));
                resultField.setText(String.format("%.2f", N));
            } while (N < 1);
        }
        // Display the JOptionPane message only if nUnder1 is true
        if (nUnder1) {
            System.out.println("N était inférieur à 1, des valeurs ont été générées aléatoirement pour fl, fi et fc");
        }
    }

    private double generateRandomValue() {
        return new Random().nextDouble();
    }

    private void stopSimulation() {
        if (simulationWorker != null && !simulationWorker.isDone()) {
            simulationWorker.cancel(true);
        }
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }
    }

    private void startSimulation() {
        // Stop previous simulation if running
        stopSimulation();

        simulationWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                timer = new Timer(1000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (!isCancelled()) {
                            SwingUtilities.invokeLater(new Runnable() {
                                public void run() {
                                    calculateResult(true);
                                }
                            });
                        }
                    }
                });
                timer.start();
                return null;
            }
        };
        simulationWorker.execute();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EquationDrake();
            }
        });
    }
}
