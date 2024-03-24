package me.alphamode.mcbig.math;

public class LongBacking implements IntegerBacking {
    private final long value;

    public LongBacking(long value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return (int) this.value;
    }

    @Override
    public long longValue() {
        return this.value;
    }

    @Override
    public String rawValue() {
        return Long.toString(this.value);
    }

    @Override
    public IntegerBacking add(Number toAdd) {
        return null;
    }

    @Override
    public DecimalBacking convertToDecimal() {
        return new DoubleBacking(value);
    }
}
