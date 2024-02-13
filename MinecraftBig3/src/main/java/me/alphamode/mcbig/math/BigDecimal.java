package me.alphamode.mcbig.math;

import org.jetbrains.annotations.NotNull;

public class BigDecimal extends Number implements Comparable<BigDecimal> {
    public static final BigDecimal ZERO = val(0.0);
    private final double value;

    public static BigDecimal val(double value) {
        return new BigDecimal(value);
    }

    public BigDecimal(double val) {
        this.value = val;
    }

    public BigDecimal subtract() {
        return subtract(1);
    }

    public BigDecimal subtract(BigDecimal val) {
        return new BigDecimal(this.value - val.value);
    }

    public BigDecimal subtract(double val) {
        return new BigDecimal(this.value - val);
    }

    @Override
    public int intValue() {
        return (int) this.value;
    }

    @Override
    public long longValue() {
        return (long) this.value;
    }

    @Override
    public float floatValue() {
        return (float) this.value;
    }

    @Override
    public double doubleValue() {
        return this.value;
    }

    @Override
    public int compareTo(@NotNull BigDecimal o) {
        return Double.compare(this.value, o.value);
    }
}
