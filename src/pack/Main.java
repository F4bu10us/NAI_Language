package pack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static double alpha;
    private double max_output;
    private double count;
    private double[] vector = new double[26];
    private String max_Output_Language = "";
    private String[] langs = {
            "English"
            , "French"
            , "German"
            , "Spanish"
    };
    private Scanner scan = new Scanner(System.in);
    private BufferedReader br;
    private ArrayList<Entity> trainData = new ArrayList<>();
    private ArrayList<Perceptron> perceptrons = new ArrayList<>();

    private Main() throws IOException {
        initializeAlpha();
        trainDataAndPerceptrons();
        tester();
        inputter();
    }

    private void trainDataAndPerceptrons() throws IOException {
        for (String s : langs) {
            perceptrons.add(new Perceptron(s));
            br = new BufferedReader(new FileReader("./train./" + s + ".txt"));
            forReading();
            trainData.add(new Entity(count, vector, s));
        }
        br.close();
        for (Perceptron p : perceptrons) {
            p.train(trainData);
            System.out.println("Training for " + p.language + " has ended.");
        }
    }

    private void initializeAlpha() {
        System.out.println("Enter the learning rate");
        alpha = scan.nextDouble();
        scan.nextLine();
        if (alpha <= 0 || alpha >= 1) System.exit(1);
    }

    public void tester() throws IOException {
        for (String s : langs) {
            System.out.println("Testing " + s);
            br = new BufferedReader(new FileReader("./test./" + s + ".txt"));
            forReading();
            maxOutputter(s);
        }
        br.close();
    }

    private void forReading() throws IOException {
        String line;
        count = 0;
        for (int i = 0; i < vector.length; ++i) vector[i] = 0;
        while ((line = br.readLine()) != null) {
            createVector(line);
        }
    }

    private void createVector(String line) {
        line = line.toLowerCase();
        for (int i = 0; i < line.length(); ++i) {
            for (int j = 'a'; j <= 'z'; ++j) {
                if (line.charAt(i) == j) {
                    ++vector[j - 'a'];
                    ++count;
                }
            }
        }
    }
    // a=97; z =122

    private void inputter(){
        while (true) {
            System.out.println("Enter your own sentence");
            String s = scan.nextLine();
            if(s.equals("0"))break;
            count = 0;
            for (int i = 0; i < vector.length; ++i) vector[i] = 0;
            createVector(s);
            maxOutputter("Unknown");
        }
    }

    private void maxOutputter(String lang) {
        for (Perceptron p : perceptrons) {
            double current_output = p.calc(new Entity(count, vector, lang));
            System.out.println("Result for: " + p.language + " = " + current_output);
            if (max_Output_Language.equals("")) {
                max_Output_Language = p.language;
                max_output = current_output;
            } else if (max_output < current_output) {
                max_Output_Language = p.language;
                max_output = current_output;
            }
        }
        System.out.println("This was " + max_Output_Language);
        max_output = 0;
        max_Output_Language = "";
    }

    public static void main(String[] args) throws IOException {
        new Main();
    }
}