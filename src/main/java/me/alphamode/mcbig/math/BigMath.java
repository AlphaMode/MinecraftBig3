package me.alphamode.mcbig.math;

import java.math.MathContext;

public class BigMath {
    public static BigInteger floorDiv(final BigInteger x, final BigInteger y) {
        if (x.signum() * y.signum() >= 0) {
            return x.divide(y);
        }
        final java.math.BigInteger[] qr = x.divideAndRemainder(y.value);
        return new BigInteger(qr[1].signum() == 0 ? qr[0] : qr[0].subtract(java.math.BigInteger.ONE));
    }

    public static BigInteger floorDiv(final BigInteger x, long yLong) {
        java.math.BigInteger y = java.math.BigInteger.valueOf(yLong);
        if (x.signum() * y.signum() >= 0) {
            return x.divide(y);
        }
        final java.math.BigInteger[] qr = x.divideAndRemainder(y);
        return new BigInteger(qr[1].signum() == 0 ? qr[0] : qr[0].subtract(java.math.BigInteger.ONE));
    }

    public static BigInteger floorMod(BigInteger dividend, BigInteger divisor) {
        BigInteger result = dividend.mod(divisor);
        if (result.signum() < 0) {
            result = result.add(divisor);
        }
        return result;
    }

    public static BigInteger floorMod(BigInteger dividend, long longDivisor) {
        var divisor = BigInteger.val(longDivisor);
        BigInteger result = dividend.mod(divisor);
        if (result.signum() < 0) {
            result = result.add(divisor);
        }
        return result;
    }

    public static BigDecimal sqrt(BigDecimal a) {
        return new BigDecimal(a.getBacking().sqrt(MathContext.DECIMAL128));
    }
}