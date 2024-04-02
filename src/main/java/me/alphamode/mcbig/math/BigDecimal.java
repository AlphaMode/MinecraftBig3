package me.alphamode.mcbig.math;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import org.jetbrains.annotations.NotNull;

import java.math.MathContext;
import java.math.RoundingMode;

public class BigDecimal extends Number implements Comparable<BigDecimal> {
    public static final BigDecimal ZERO = val(0.0);
    private final java.math.BigDecimal value;

    public static BigDecimal val(double value) {
        return new BigDecimal(java.math.BigDecimal.valueOf(value));
    }

    public static BigDecimal command(StringReader reader) throws CommandSyntaxException {
        try {
            return new BigDecimal(reader.readString());
        } catch (NumberFormatException e) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidDouble().createWithContext(reader, e);
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public BigDecimal(java.math.BigDecimal val) {
        this.value = val;
    }

    public BigDecimal(java.math.BigInteger val) {
        this.value = new java.math.BigDecimal(val);
    }

    public BigDecimal(String val) {
        java.math.BigDecimal backing;
        try {
            backing = new java.math.BigDecimal(val);
        } catch (NumberFormatException e) {
            backing = java.math.BigDecimal.valueOf(Double.parseDouble(val));
        }
        this.value = backing;
    }

    public BigDecimal subtract() {
        return subtract(1);
    }

    public BigDecimal subtract(BigDecimal val) {
        return new BigDecimal(this.value.subtract(val.value));
    }

    public BigDecimal subtract(double val) {
        return new BigDecimal(this.value.subtract(java.math.BigDecimal.valueOf(val)));
    }

    public BigDecimal add(BigDecimal val) {
        return new BigDecimal(this.value.add(val.value));
    }

    public BigDecimal add(double val) {
        return new BigDecimal(this.value.add(java.math.BigDecimal.valueOf(val)));
    }

    public BigDecimal multiply(BigDecimal val) {
        return new BigDecimal(this.value.multiply(val.value));
    }

    public BigDecimal multiply(double val) {
        return new BigDecimal(this.value.multiply(java.math.BigDecimal.valueOf(val)));
    }

    public BigDecimal divide(double val) {
        return new BigDecimal(this.value.divide(java.math.BigDecimal.valueOf(val), RoundingMode.HALF_EVEN));
    }

    public BigDecimal divide(BigDecimal val) {
        return new BigDecimal(this.value.divide(val.value, RoundingMode.HALF_EVEN));
    }

    public BigDecimal floor() {
        return new BigDecimal(this.value.setScale(0, RoundingMode.FLOOR));
    }

    public BigDecimal ceil() {
        return new BigDecimal(this.value.setScale(0, RoundingMode.CEILING));
    }

    public BigDecimal min(BigDecimal val) {
        return new BigDecimal(this.value.min(val.value));
    }

    public BigDecimal max(BigDecimal val) {
        return new BigDecimal(this.value.max(val.value));
    }

    public BigDecimal negate() {
        return new BigDecimal(this.value.negate());
    }

    public BigDecimal abs() {
        return new BigDecimal(this.value.abs());
    }

    public BigDecimal round() {
        return new BigDecimal(this.value.round(MathContext.DECIMAL128));
    }

    public BigInteger toBigInteger() {
        return new BigInteger(this.value.toBigInteger());
    }

    @Override
    public int intValue() {
        return this.value.intValue();
    }

    @Override
    public long longValue() {
        return this.value.longValue();
    }

    @Override
    public float floatValue() {
        return this.value.floatValue();
    }

    @Override
    public double doubleValue() {
        return this.value.doubleValue();
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Double d)
            return this.value.equals(BigDecimal.val(d));
        if (obj instanceof BigDecimal d)
            return d.value.equals(this.value);
        if (obj instanceof java.math.BigDecimal d)
            return d.equals(this.value);
        return false;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    @Override
    public int compareTo(@NotNull BigDecimal o) {
        return this.value.compareTo(o.value);
    }

    public int compareTo(double o) {
        return this.value.compareTo(java.math.BigDecimal.valueOf(o));
    }

    public java.math.BigDecimal getBacking() {
        return this.value;
    }
}
