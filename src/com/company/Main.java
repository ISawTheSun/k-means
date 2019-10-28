package com.company;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    static int n; //liczba atrybutow
    static int k; //liczba centroidow

    public static void main(String[] args) throws InterruptedException {
        enterK();
        List<Iris> data = getData(new File("iris.data.txt"));
        List<Cluster> clusters = getClusters(k);

        doKmeans(data, clusters);

    }

    public static int enterK(){
        k = -1;

        while(k<0) {
            try {
                System.out.print("Enter k: ");
                Scanner in = new Scanner(System.in);
                k = in.nextInt();
                if(k<0)
                    throw new Exception();
            } catch (InputMismatchException e) {
                System.out.println("Enter an integer");
            } catch (Exception e) {
                System.out.println("k should be greater than 0");
            }
        }

        return k;
    }

    public static List<Cluster> getClusters(int k){
        List<Cluster> clusters = new ArrayList<>();

        for (int i = 0; i < k; i++) {
            List<Double> atrybuty = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                atrybuty.add(Math.random() * 5);
            }
            //System.out.println(atrybuty);
            clusters.add(new Cluster(atrybuty, "C"+String.valueOf(i+1)));
        }

        return clusters;
    }

    public static void doKmeans(List<Iris> data, List<Cluster>clusters){
        int iteration = 0;
        boolean isGrouped = false;
        boolean Continue = true;
        boolean ChangeClusters = false;


        while(!isGrouped){
            isGrouped = true;
            iteration++;
            double SUM = 0;


            for (int i = 0; i < data.size(); i++) {
                double Length = 1000;
                Cluster currentCluster = data.get(i).getCluster();

                for (int j = 0; j < clusters.size(); j++) {
                    double TmpLength = getLength(data.get(i).getAtrybuty(), clusters.get(j).getAtrybuty());

                    if(TmpLength < Length){
                        data.get(i).setCluster(clusters.get(j));
                        Length = TmpLength;
                    }
                }

                SUM += Math.pow(Length, 2);

                data.get(i).getCluster().Objects.add(data.get(i));

                if(Continue && currentCluster != data.get(i).getCluster()){
                    isGrouped = false;
                    Continue = false;
                    ChangeClusters = true;
                }
            }

            System.out.println("\nIteration №" + iteration + ": SUM lengths = " + SUM);

            if(ChangeClusters)
                changeClusters(clusters);

            Continue = true;
            ChangeClusters = false;
        }

        for (int i = 0; i < clusters.size(); i++) {
            System.out.println(clusters.get(i).toString());
        }

        System.out.println("Iterations: "+iteration);
        getPercentage(data, clusters);
    }

    public static void changeClusters(List<Cluster> clusters){
        for (int i = 0; i < clusters.size(); i++) {
            if(clusters.get(i).Objects.size() > 0){
                clusters.get(i).clearAtrybuty();
                for (int l = 0; l < n; l++) {
                    List<Double> tmp = new ArrayList<>();
                    for (int j = 0; j < clusters.get(i).Objects.size(); j++) {
                        tmp.add(clusters.get(i).Objects.get(j).getAtrybuty().get(l));
                    }
                    double value = getAverage(tmp);
                    clusters.get(i).changeAtrybutyAt(l, value);
                }
            }
        }
    }

    public static void getPercentage(List<Iris> data, List<Cluster> clusters){

        Set<String> clases = new HashSet<>();

        for (int i = 0; i < data.size(); i++) {
            clases.add(data.get(i).getAtrybutDecyzyjny());
        }

        for (int i = 0; i < clusters.size(); i++) {
            System.out.println("\n"+clusters.get(i).getName()+": ");
            HashMap<String, Integer> percentages = new HashMap<>();
            int allCount = clusters.get(i).Objects.size();
            if(allCount == 0){
                for (String atrybut: clases) {
                    percentages.put(atrybut, 0);
                }
            }
            else {

                for (String atrybut : clases) {
                    int classCount = 0;
                    for (int j = 0; j < clusters.get(i).Objects.size(); j++) {
                        if (clusters.get(i).Objects.get(j).getAtrybutDecyzyjny().equals(atrybut))
                            classCount++;
                    }

                    int percentage = classCount*100/allCount;

                    percentages.put(atrybut, percentage);
                }
            }

            for (String key: percentages.keySet()) {
                System.out.println(key+": "+percentages.get(key)+"%");
            }
        }

    }


    public static Double getAverage(List<Double> A){
        double average = 0;
        for (int i = 0; i < A.size(); i++) {
            average += A.get(i);
        }

        return average/A.size();
    }


    public static Double getLength(List<Double> A, List<Double> B){
        double sum = 0;
        for (int i = 0; i < A.size(); i++) {
            sum += Math.pow(A.get(i)-B.get(i), 2);
        }
        return Math.sqrt(sum);
    }



    public static List<Iris> getData(File file) {
        List<Iris> data = new ArrayList<>();

        StringBuilder sb = new StringBuilder();
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            int f;
            while ((f = fis.read()) != -1)
                sb.append((char) f);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Pattern p = Pattern.compile("([\\d.,]+)([\\w-]+)");
        Matcher m = p.matcher(sb);

        List<Double> atrybuty = null;
        while (m.find()) {
            atrybuty = new ArrayList<>();
            String [] tmp = m.group(1).split(",");
            for (int i = 0; i < tmp.length; i++) {
                atrybuty.add(Double.parseDouble(tmp[i]));
            }

            data.add(new Iris(atrybuty, m.group(2)));
        }
        n = atrybuty.size();

        return data;
    }
}
