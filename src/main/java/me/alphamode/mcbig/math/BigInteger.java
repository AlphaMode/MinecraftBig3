package me.alphamode.mcbig.math;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;
import java.util.Objects;

public class BigInteger extends Number implements Comparable<BigInteger> {
    public static final Codec<BigInteger> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("value").forGetter(BigInteger::toString)
    ).apply(instance, BigInteger::val));
    public static final BigInteger ZERO = new BigInteger(java.math.BigInteger.ZERO);
    public static final BigInteger ONE = new BigInteger(java.math.BigInteger.ONE);
    protected final java.math.BigInteger value;

    public BigInteger(java.math.BigInteger backing) {
        this.value = backing;
    }

    public BigInteger(String backing) {
        this.value = new java.math.BigInteger(backing);
    }

    public BigInteger(byte[] bytes) {
        this.value = new java.math.BigInteger(bytes);
    }

    public static BigInteger command(StringReader reader) throws CommandSyntaxException {
        try {
            return new BigInteger(reader.readString());
        } catch (NumberFormatException e) {
            throw CommandSyntaxException.BUILT_IN_EXCEPTIONS.readerInvalidInt().createWithContext(reader, e);
        } catch (CommandSyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public static BigInteger constant(long val) {
        return new BigInteger(java.math.BigInteger.valueOf(val));
    }

    public static BigInteger val(String val) {
        return new BigInteger(new java.math.BigInteger(val));
    }

    public static BigInteger val(long val) {
        return new BigInteger(java.math.BigInteger.valueOf(val));
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
        return value.floatValue();
    }

    @Override
    public double doubleValue() {
        return value.doubleValue();
    }

//    public DecimalBacking getOrCacheDecimal() {
//        if (this.decimalBacking == null)
//            this.decimalBacking = this.backing.convertToDecimal();
//        return this.decimalBacking;
//    }

    public BigDecimal add(double val) {
        return new BigDecimal(new java.math.BigDecimal(this.value).add(java.math.BigDecimal.valueOf(val)));
    }

    public BigInteger add() {
        return new BigInteger(this.value.add(java.math.BigInteger.ONE));
    }

    public BigInteger add(long val) {
        return new BigInteger(this.value.add(java.math.BigInteger.valueOf(val)));
    }

    public BigInteger add(BigInteger val) {
        return new BigInteger(this.value.add(val.value));
    }

    public BigInteger subtract(BigInteger val) {
        return new BigInteger(this.value.subtract(val.value));
    }

    public BigInteger subtract() {
        return new BigInteger(this.value.subtract(java.math.BigInteger.ONE));
    }

    public BigInteger subtract(long val) {
        return new BigInteger(this.value.subtract(java.math.BigInteger.valueOf(val)));
    }

    public BigDecimal subtract(double val) {
        return new BigDecimal(new java.math.BigDecimal(this.value).subtract(new java.math.BigDecimal(val)));
    }

    public BigDecimal subtract(BigDecimal val) {
        return new BigDecimal(new java.math.BigDecimal(this.value).subtract(val.getBacking()));
    }

    public BigInteger abs() {
        return new BigInteger(this.value.abs());
    }

    public BigInteger multiply(BigInteger val) {
        return new BigInteger(this.value.multiply(val.value));
    }

    public BigInteger multiply(long val) {
        return new BigInteger(this.value.multiply(java.math.BigInteger.valueOf(val)));
    }

    public BigInteger remainder(long val) {
        return new BigInteger(this.value.remainder(java.math.BigInteger.valueOf(val)));
    }

    public BigInteger remainder(BigInteger val) {
        return new BigInteger(this.value.remainder(val.value));
    }

    public BigInteger negate() {
        return new BigInteger(this.value.negate());
    }

    public BigInteger max(BigInteger other) {
        return new BigInteger(this.value.max(other.value));
    }

    public BigInteger min(BigInteger other) {
        return new BigInteger(this.value.min(other.value));
    }

    /**
     * Returns a BigInteger whose value is {@code (this >> n)}.  Sign
     * extension is performed.  The shift distance, {@code n}, may be
     * negative, in which case this method performs a left shift.
     * (Computes <code>floor(this / 2<sup>n</sup>)</code>.)
     *
     * @param  n shift distance, in bits.
     * @return {@code this >> n}
     * @see #shiftLeft
     */
    public BigInteger shiftRight(int n) {
        return new BigInteger(this.value.shiftRight(n));
    }

    /**
     * Returns a BigInteger whose value is {@code (this << n)}.
     * The shift distance, {@code n}, may be negative, in which case
     * this method performs a right shift.
     * (Computes <code>floor(this * 2<sup>n</sup>)</code>.)
     *
     * @param  n shift distance, in bits.
     * @return {@code this << n}
     * @see #shiftRight
     */
    public BigInteger shiftLeft(int n) {
        return new BigInteger(this.value.shiftLeft(n));
    }

    @Override
    public int compareTo(@NotNull BigInteger o) {
        return this.value.compareTo(o.value);
    }

    public int compareTo(long o) {
        return this.value.compareTo(java.math.BigInteger.valueOf(o));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BigInteger other) {
            return Objects.equals(this.value, other.value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.value.hashCode();
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.value);
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public BigInteger and(BigInteger other) {
        return new BigInteger(this.value.and(other.value));
    }

    public BigInteger divide(int i) {
        return new BigInteger(this.value.divide(java.math.BigInteger.valueOf(i)));
    }

    public BigInteger divide(BigInteger i) {
        return new BigInteger(this.value.divide(i.value));
    }

    public BigInteger divide(java.math.BigInteger i) {
        return new BigInteger(this.value.divide(i));
    }

    public byte[] toByteArray() {
        return this.value.toByteArray();
    }

    public int signum() {
        return this.value.signum();
    }

    public java.math.BigInteger[] divideAndRemainder(java.math.BigInteger val) {
        return this.value.divideAndRemainder(val);
    }

    public BigInteger mod(BigInteger divisor) {
        return new BigInteger(this.value.mod(divisor.value));
    }
}
