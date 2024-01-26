package org.example;

public enum Type {
    ECONOMY(1), STANDARD(1.5),PREMIUM(3);

    private final double multiplier;

    Type(double multiplier) {
        this.multiplier = multiplier;
    }

    public double getMultiplier() {
        return multiplier;
    }


    public double getMultiplyer() {
        return 0;
    }
}
