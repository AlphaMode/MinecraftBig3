package me.alphamode.mcbig.math;

public interface IntegerBacking<V extends Number> {
    int intValue();

    long longValue();

    String rawValue();

    IntegerBacking add(V toAdd);

    DecimalBacking convertToDecimal();
}
