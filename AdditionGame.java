import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class AdditionGame extends JPanel {
    private int number1, number2;
    private final JLabel questionLabel;
    private JTextField answerField;
    private ExerciseCompletionListener listener;

    public AdditionGame(CardLayout cardLayout, JPanel panelContainer) {
        setLayout(new FlowLayout());

        questionLabel = new JLabel();
        answerField = new JTextField(3);
        updateExercise();

        JButton submitButton = new JButton("Check Answer");
        JLabel resultLabel = new JLabel("");

        JButton toMenuButton = new JButton("Go to Menu");
        toMenuButton.setBounds(250, 530, 100, 30);
        toMenuButton.addActionListener(e -> cardLayout.show(panelContainer, "Menu"));

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int answer = Integer.parseInt(answerField.getText());
                if (answer == number1 + number2) {
                    resultLabel.setText("Correct!");
                    if (listener != null) {
                        listener.onExerciseCompleted();
                    }
                    submitButton.setEnabled(false);
                } else {
                    resultLabel.setText("Incorrect, try a new one!");
                    updateExercise();
                }
            }
        });

        add(questionLabel);
        add(answerField);
        add(submitButton);
        add(resultLabel);
        add(toMenuButton);

        setVisible(true);
    }

    public void setExerciseCompletionListener(ExerciseCompletionListener listener) {
        this.listener = listener;
    }

    private void generateRandomNumbers() {
        Random random = new Random();
        number1 = random.nextInt(10); // numbers from 0 to 9
        number2 = random.nextInt(10);
    }

    private void updateExercise() {
        generateRandomNumbers();
        questionLabel.setText("What is " + number1 + " + " + number2 + "? ");
        answerField.setText("");
    }

}

