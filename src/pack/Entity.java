package pack;

class Entity {
    String language;
    double [] vector = new double[26];
    int correctResult, myResult;

    Entity(double count, double [] v, String lang){
        language = lang;
        double sum = 0;
        for(int i = 0; i<v.length; ++i){
            vector[i]=v[i]/count;
            sum += Math.pow(vector[i],2);
        }
        sum = Math.sqrt(sum);
        for(int i = 0; i<v.length; ++i){
           vector[i]=vector[i]/sum;
        }
    }
}
