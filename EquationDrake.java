package tp2;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;

public class EquationDrake {
    private JFrame frame;
    private JPanel panel;
    private GridBagConstraints constraints;

    private JLabel labelTitle, labelFormula, labelR, labelFp, labelNe, labelFl, labelFi, labelFc, labelL, labelResult;
    private JSlider sliderR, sliderFp, sliderNe, sliderFl, sliderFi, sliderFc, sliderL;
    private JTextField resultFieldR, resultFieldFp, resultFieldNe, resultFieldFl, resultFieldFi, resultFieldFc, resultFieldL, resultField;

    public EquationDrake() {
        frame = new JFrame("Équation de Drake");
        frame.setPreferredSize(new Dimension(800, 600)); // Fenêtre plus grande
        panel = new JPanel(new GridBagLayout());
        constraints = new GridBagConstraints();

        labelTitle = new JLabel("Équation de Drake");
        labelTitle.setFont(new Font("SansSerif", Font.BOLD, 24));
        labelFormula = new JLabel("<html>N = R* * fp * ne * fl * fi * fc * L<br>(N = Nombre de civilisations extraterrestres dans notre galaxie)</html>");
        labelFormula.setFont(new Font("SansSerif", Font.PLAIN, 16));

        labelR = new JLabel("Taux de formation d'étoiles (R*)");
        sliderR = createSlider(100, 10000, 2000); // Plage de 0 à 100 avec valeur initiale à 20
        resultFieldR = createResultField(20);

        labelFp = new JLabel("Fraction d'étoiles avec des systèmes planétaires (fp)");
        sliderFp = createSlider(0, 100, 10); // Plage de 0 à 100 avec valeur initiale à 10
        resultFieldFp = createResultField(10);

        labelNe = new JLabel("Nombre moyen de planètes potentiellement habitables (ne)");
        sliderNe = createSlider(0, 100, 50); // Plage de 0 à 100 avec valeur initiale à 50
        resultFieldNe = createResultField(50);

        labelFl = new JLabel("Fraction de planètes où la vie se développe (fl)");
        sliderFl = createSlider(0, 100, 50); // Plage de 0 à 100 avec valeur initiale à 50
        resultFieldFl = createResultField(50);

        labelFi = new JLabel("Fraction de planètes avec une vie intelligente (fi)");
        sliderFi = createSlider(0, 100, 50); // Plage de 0 à 100 avec valeur initiale à 50
        resultFieldFi = createResultField(50);

        labelFc = new JLabel("Fraction de planètes avec une civilisation technologique (fc)");
        sliderFc = createSlider(0, 100, 10); // Plage de 0 à 100 avec valeur initiale à 10
        resultFieldFc = createResultField(10);

        labelL = new JLabel("Durée de communication (L)");
        sliderL = createSlider(100, 100000, 100000); // Plage de 0 à 1 000 000 avec valeur initiale à 100 000
        resultFieldL = createResultField(100000);

        labelResult = new JLabel("Résultat de l'équation :");
        resultField = createResultField(0);

        addComponentsToPanel();
        addListeners();
        calculateResult(); // Calcul initial lors de la création de l'instance.

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

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
        panel.add(resultFieldR, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelFp, constraints);
        constraints.gridx = 1;
        panel.add(sliderFp, constraints);
        constraints.gridx = 2;
        panel.add(resultFieldFp, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelNe, constraints);
        constraints.gridx = 1;
        panel.add(sliderNe, constraints);
        constraints.gridx = 2;
        panel.add(resultFieldNe, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelFl, constraints);
        constraints.gridx = 1;
        panel.add(sliderFl, constraints);
        constraints.gridx = 2;
        panel.add(resultFieldFl, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelFi, constraints);
        constraints.gridx = 1;
        panel.add(sliderFi, constraints);
        constraints.gridx = 2;
        panel.add(resultFieldFi, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelFc, constraints);
        constraints.gridx = 1;
        panel.add(sliderFc, constraints);
        constraints.gridx = 2;
        panel.add(resultFieldFc, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        panel.add(labelL, constraints);
        constraints.gridx = 1;
        panel.add(sliderL, constraints);
        constraints.gridx = 2;
        panel.add(resultFieldL, constraints);

        constraints.gridy++;
        constraints.gridx = 0;
        constraints.anchor = GridBagConstraints.CENTER;
        panel.add(labelResult, constraints);
        constraints.gridx = 1;
        panel.add(resultField, constraints);
    }

    private void addListeners() {
        ChangeListener changeListener = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                calculateResult();
            }
        };

        sliderR.addChangeListener(changeListener);
        sliderFp.addChangeListener(changeListener);
        sliderNe.addChangeListener(changeListener);
        sliderFl.addChangeListener(changeListener);
        sliderFi.addChangeListener(changeListener);
        sliderFc.addChangeListener(changeListener);
        sliderL.addChangeListener(changeListener);
    }

    private void calculateResult() {
        double R = sliderR.getValue() / 100.0;
        double fp = sliderFp.getValue() / 100.0;
        double ne = sliderNe.getValue() / 100.0;
        double fl = sliderFl.getValue() / 100.0;
        double fi = sliderFi.getValue() / 100.0;
        double fc = sliderFc.getValue() / 100.0;
        double L = sliderL.getValue() / 100.0;

        double N = R * fp * ne * fl * fi * fc * L;

        resultFieldR.setText(String.format("%.2f", R));
        resultFieldFp.setText(String.format("%.2f", fp));
        resultFieldNe.setText(String.format("%.2f", ne));
        resultFieldFl.setText(String.format("%.2f", fl));
        resultFieldFi.setText(String.format("%.2f", fi));
        resultFieldFc.setText(String.format("%.2f", fc));
        resultFieldL.setText(String.format("%.2f", L));

        resultField.setText(String.format("%.2f", N));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new EquationDrake();
            }
        });
    }
}
