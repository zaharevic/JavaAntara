package model.Food;

public abstract class Food {
    int saturation;
    boolean used = false;

    public void eated(){
        used = true;
    }

    public boolean getUsed(){
        return used;
    }

    public int getSaturation(){
        return saturation;
    }
}
