package model;

import java.util.Random;
import java.util.Scanner;

public  class Kotik {
    StringBuilder sb;
    Random rnd = new Random();
    Scanner sc = new Scanner(System.in);
    private static int catsCounter = 1;
    private int hunger;
    private int pettines;
    private String name;
    private int weight;
    private String meow;

    static {
        catsCounter++;
    }

    public Kotik(Integer pettines, String name, Integer weight, String meow) {
        this.hunger = 100;
        this.pettines = pettines;
        this.name = name;
        this.weight = weight;
        this.meow = meow;
    }

    public Kotik(){
        this(0, "Unknown",0,"Unknown");
    }

    public void setKotik(int pettines, String name, int weight, String meow){
        setPettines(pettines);
        setName(name);
        setWeight(weight);
        setMeow(meow);
    }

    public void printStat(){
        sb = new StringBuilder();
        sb.append("-----------------------------------------------------\nИмя котика: ");
        sb.append(getName());
        sb.append("\nПривлекательность: ");
        sb.append(getPettines());
        sb.append("\nВес: ");
        sb.append(getWeight());
        sb.append("кг.\nЗвук мяуканья: ");
        sb.append(getMeow());
        sb.append("\n-----------------------------------------------------");
        System.out.println(sb.toString());
    }

    public void liveAnotherDay() {
        boolean res;
        int hour = 0;
        while (hour < 24){
            switch (rnd.nextInt(4)) {
                case 0:
                    res = sleep();
                    break;
                case 1:
                    res = chaseMouse();
                    break;
                case 2:
                    res = play();
                    break;
                case 3:
                    res = shapeClaws();
                    break;
                default:
                    res = false;
            }
            if (!res) {
                eat();
            }
            hour++;
        }
    }

    public boolean eat(int satiety){
        setHunger(getHunger()+satiety);
        System.out.println("*Котик поел*");
        return true;
    }

    public boolean eat(int satiety, String foodName){
        setHunger(getHunger()+satiety);
        sb = new StringBuilder();
        sb.append("*Котик поел ");
        sb.append(foodName);
        sb.append("*");
        System.out.println(sb.toString());
        return true;
    }

    public boolean eat(){
        return eat(20,"сухой корм");
    }

    public boolean sleep(){
        if(getHunger() > 0){
            System.out.println("*Котик сопит*");
            setHunger(getHunger() - 3);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean chaseMouse(){
        if(getHunger() > 0){
            System.out.println("*Котик бегает за мышкой*");
            setHunger(getHunger() - 7);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean play(){
        if(getHunger() > 0){
            System.out.println("*Котик играет*");
            setHunger(getHunger() - 5);
            return true;
        }
        else{
            return false;
        }
    }

    public boolean shapeClaws(){
        if(getHunger() > 0){
            System.out.println("*Котик точит когти*");
            setHunger(getHunger() - 2);
            return true;
        }
        else{
            return false;
        }
    }


    public int getHunger() {
        return hunger;
    }

    public void setHunger(int hunger) {
        if(hunger > 100){
            hunger = 100;
            this.hunger = hunger;
        } else if (hunger< 0) {
            this.hunger = 0;
        }else {
            this.hunger = hunger;
        }
    }

    public void setPettines(int pettines) {
        this.pettines = pettines;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setMeow(String meow) {
        this.meow = meow;
    }

    public static int getCatsCounter() {
        return catsCounter;
    }

    public int getPettines() {
        return pettines;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getMeow() {
        return meow;
    }
}