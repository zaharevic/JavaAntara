package model.Food;

public abstract class Food {
    private final int saturation;
    private boolean used = false;

    protected Food(int saturation) {
        this.saturation = saturation;
    }

    public void eated() {
        used = true;
    }

    public int getSaturation() {
        return saturation;
    }
}