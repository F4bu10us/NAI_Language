package pack;

import java.util.ArrayList;

public class Perceptron {
    String language;
    private double [] weight = new double[26];
    private double threshold = 0.5;
    private int correctAnswers;

    Perceptron(String lang){
        language = lang;
        for(int i = 0; i<weight.length; ++i){
            weight[i]=threshold;
        }
    }

    public void train(ArrayList<Entity> data){
        int trainingAmount = 0;
        for(Entity e : data){
            if(e.language.equals(language)){
                e.correctResult=1;
            }else{
                e.correctResult=0;
            }
        }
        outPut(data);
        int errors = data.size() - correctAnswers;
        while (errors!=0){
            System.out.println(++trainingAmount);
            for(Entity e : data){
                double difference = e.correctResult - e.myResult;
                if(difference==0)continue;
                double fakeInput = -1*difference*Main.alpha;
                for(int i = 0; i < weight.length; ++i){
                    weight[i]+= difference*Main.alpha*e.vector[i];
                }
                threshold+=fakeInput;
            }
            outPut(data);
            errors = data.size() - correctAnswers;
        }
    }

    private void outPut(ArrayList<Entity> data){
        correctAnswers = 0;
        for(Entity e : data){
            calc(e);
            if(e.myResult == e.correctResult){
                ++correctAnswers;
            }
        }
        System.out.println("ERRORS:" + (data.size()-correctAnswers));
    }


    public double calc(Entity e){
        double result = 0;
        for(int i = 0; i<e.vector.length; ++i){
            result+=e.vector[i]*weight[i];
        }
        result-=threshold;
        if(result>=0) e.myResult = 1;
        else e.myResult = 0;
        return  result;

    }

}