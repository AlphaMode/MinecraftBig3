package me.alphamode.mcbig.core;

import com.mojang.logging.LogUtils;
import me.alphamode.mcbig.math.BigDecimal;
import me.alphamode.mcbig.math.BigInteger;
import org.slf4j.Logger;

public class BigConstants {
    public static final BigDecimal POINT_ONE = BigDecimal.val(0.1D);
    public static final BigDecimal DISTANCE = BigDecimal.val(256.0D);
    public static final BigDecimal NINE = BigDecimal.val(9.0D);
    public static final BigDecimal BEE = BigDecimal.val(0.3F);
    public static final BigDecimal BOAT = BigDecimal.val(0.7D);
    public static final BigDecimal GUARDIAN = BigDecimal.val(1.5D);
    public static final BigDecimal MINECART = BigDecimal.val(0.8D);
    public static final BigDecimal EXPLODE_DISTANCE = BigDecimal.val(4096.0D);
    public static final BigDecimal FOUR = BigDecimal.val(4.0D);
    public static final BigDecimal THREE = BigDecimal.val(3.0D);
    public static final BigDecimal TEN = BigDecimal.val(10.0D);
    public static final BigDecimal SIX = BigDecimal.val(6.0D);
    public static final BigDecimal FIFTEEN = BigDecimal.val(15.0D);
    public static boolean BIG_MODE = true;

    public static final Logger LOGGER = LogUtils.getLogger();

    public static final BigDecimal POSITIVE_INFINITY = BigDecimal.val(Double.MAX_VALUE);
    public static final BigDecimal NEGATIVE_INFINITY = BigDecimal.val(Double.MAX_VALUE);
    public static final BigDecimal ALMOST_ONE = BigDecimal.val(0.9999999D);
    public static final BigDecimal FIND_BITS = BigDecimal.val(1.0000001D);
    public static final BigDecimal PARTICLE_TICK = BigDecimal.val(0.2D);
    public static final BigDecimal PARTICLE = BigDecimal.val(0.125D);
    public static final BigDecimal PARTICLES = BigDecimal.val(0.25D);
    public static final BigDecimal EPSILON = BigDecimal.val(1.0E-7D);
    public static final BigDecimal NEG_EPSILON = BigDecimal.val(-1.0E-7D);
    public static final BigDecimal ENTITY = BigDecimal.val(1.0E-6D);
    public static final BigDecimal AABB = BigDecimal.val(0.5D);
    public static final BigDecimal LEASH = BigDecimal.val(0.375D);
    public static final BigDecimal BED = BigDecimal.val(0.6875D);
    public static final BigDecimal GOLEM = BigDecimal.val(0.05D);
    public static final BigDecimal TWO = BigDecimal.val(2.0D);
    public static final BigDecimal FIVE = BigDecimal.val(5.0D);
    public static final BigDecimal EIGHT = BigDecimal.val(8.0D);
    public static final BigDecimal VAL = BigDecimal.val(0.33F);
    public static final BigDecimal TWELVE = BigDecimal.val(12.0D);
    public static final BigDecimal SIXTEEN = BigDecimal.val(16.0D);
    public static final BigDecimal BREAK_PROGRESS = BigDecimal.val(1024.0D);
    public static final BigDecimal BIG_VAL = BigDecimal.val(2048.0D);
    public static final BigDecimal EPSILON_EQUAL = BigDecimal.val(1.0E-5F);
    public static final BigDecimal FORTY = BigDecimal.val(40.0D);
    public static final BigDecimal DRAGON = BigDecimal.val(105.0D);
    public static final BigDecimal TEXT = BigDecimal.val(0.75D);

    public static class Ints {
        public static final BigInteger WORLD_HEIGHT_LIMIT = BigInteger.constant(4096L);
        public static final BigInteger TWO = BigInteger.constant(2);
        public static final BigInteger EIGHT = BigInteger.constant(8);
        public static final BigInteger THREE = BigInteger.constant(3);
        public static final BigInteger TWELVE = BigInteger.constant(12);
        public static final BigInteger TEN = BigInteger.constant(10);
        public static final BigInteger FOUR = BigInteger.constant(4);
        public static final BigInteger FIVE = BigInteger.constant(5);
        public static boolean BIG_MODE = true;
        public static final BigInteger FIFTEEN = BigInteger.constant(15);
        public static final BigInteger SIXTEEN = BigInteger.constant(16);
        public static final BigInteger SIXTY = BigInteger.constant(60);
        public static final BigInteger THIRTY_ONE = BigInteger.constant(31);
        public static final BigInteger THIRTY_TWO = BigInteger.constant(32);
        public static final BigInteger SEVEN = BigInteger.constant(7);
    }
}