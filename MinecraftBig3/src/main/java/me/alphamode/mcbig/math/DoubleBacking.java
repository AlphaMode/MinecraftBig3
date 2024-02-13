package me.alphamode.mcbig.math;

public class DoubleBacking implements DecimalBacking {
    private final double value;

    public DoubleBacking(double value) {
        this.value = value;
    }

    @Override
    public float floatValue() {
        return (float) this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }
}
