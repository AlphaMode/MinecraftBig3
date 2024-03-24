package me.alphamode.mcbig.math;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class BigInteger extends Number implements Comparable<BigInteger> {
    public static final Codec<BigInteger> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.fieldOf("value").forGetter(bigInteger -> Long.toString(bigInteger.value))
    ).apply(instance, BigInteger::val));
    public static final BigInteger ZERO = new BigInteger(0);
    public static final BigInteger ONE = new BigInteger(1);
    private final long value;

    public BigInteger(long backing) {
        this.value = backing;
    }

    public BigInteger(byte[] bytes) {
        long result = 0;
        for (int i = 0; i < Long.BYTES; i++) {
            result <<= Byte.SIZE;
            result |= (bytes[i] & 0xFF);
        }
        this.value = result;
    }

    public static BigInteger constant(long val) {
        return new BigInteger(val);
    }

    public static BigInteger val(String val) {
        return new BigInteger(Long.parseLong(val));
    }

    public static BigInteger val(long val) {
        return new BigInteger(val);
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
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

//    public DecimalBacking getOrCacheDecimal() {
//        if (this.decimalBacking == null)
//            this.decimalBacking = this.backing.convertToDecimal();
//        return this.decimalBacking;
//    }

    public BigDecimal add(double val) {
        return new BigDecimal(this.value + val);
    }

    public BigInteger add() {
        return new BigInteger(this.value + 1);
    }

    public BigInteger add(long val) {
        return new BigInteger(this.value + val);
    }

    public BigInteger add(BigInteger val) {
        return new BigInteger(this.value + val.value);
    }

    public BigInteger subtract(BigInteger val) {
        return new BigInteger(this.value - val.value);
    }

    public BigInteger subtract() {
        return new BigInteger(this.value - 1);
    }

    public BigInteger subtract(long val) {
        return new BigInteger(this.value - val);
    }

    public BigDecimal subtract(double val) {
        return new BigDecimal(this.value - val);
    }

    public BigDecimal subtract(BigDecimal val) {
        return new BigDecimal(this.value - val.doubleValue());
    }

    public BigInteger abs() {
        return new BigInteger(Math.abs(this.value));
    }

    public BigInteger multiply(BigInteger val) {
        return new BigInteger(this.value * val.value);
    }

    public BigInteger multiply(long val) {
        return new BigInteger(this.value * val);
    }

    public BigInteger remainder(long val) {
        return new BigInteger(this.value % val);
    }

    public BigInteger remainder(BigInteger val) {
        return new BigInteger(this.value % val.value);
    }

    public BigInteger negate() {
        return new BigInteger(-this.value);
    }

    public BigInteger max(BigInteger other) {
        return BigInteger.val(Math.max(this.value, other.value));
    }

    public BigInteger min(BigInteger other) {
        return BigInteger.val(Math.min(this.value, other.value));
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
        return BigInteger.val(this.value >> n);
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
        return BigInteger.val(this.value << n);
    }

    @Override
    public int compareTo(@NotNull BigInteger o) {
        return Long.compare(this.value, o.value);
    }

    public int compareTo(long o) {
        return Long.compare(this.value, o);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BigInteger other) {
            return this.value == other.value;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.value);
    }

    public BigDecimal toBigDecimal() {
        return new BigDecimal(this.value);
    }

    @Override
    public String toString() {
        return Long.toString(this.value);
    }

    public BigInteger and(BigInteger other) {
        return BigInteger.val(this.value & other.value);
    }

    public BigInteger divide(int i) {
        return new BigInteger(this.value / i);
    }

    public BigInteger divide(BigInteger i) {
        return new BigInteger(this.value / i.value);
    }

    public byte[] toByteArray() {
        long l = this.value;
        byte[] result = new byte[Long.BYTES];
        for (int i = Long.BYTES - 1; i >= 0; i--) {
            result[i] = (byte)(l & 0xFF);
            l >>= Byte.SIZE;
        }
        return result;
    }
}
