package me.alphamode.mcbig.core;

import com.google.common.base.MoreObjects;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.concurrent.Immutable;

import me.alphamode.mcbig.math.BigDecimal;
import me.alphamode.mcbig.math.BigInteger;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;

@Immutable
public class Vec3l implements Comparable<Vec3l> {
    public static final Codec<Vec3l> CODEC = ModdedCodec.STRING_STREAM
            .comapFlatMap(
                    p_123318_ -> Util.fixedSize(p_123318_, 3).map(p_175586_ -> new Vec3l(BigInteger.val(p_175586_[0]), BigInteger.val(p_175586_[1]), BigInteger.val(p_175586_[2]))),
                    p_123313_ -> Stream.of(p_123313_.getBigX().toString(), p_123313_.getBigY().toString(), p_123313_.getBigZ().toString())
            );
    public static final Vec3l ZERO = new Vec3l(BigInteger.ZERO, BigInteger.ZERO, BigInteger.ZERO);
    private BigInteger x;
    private BigInteger y;
    private BigInteger z;

    public static Codec<Vec3l> offsetCodec(int p_194651_) {
        return ExtraCodecs.validate(
                CODEC,
                p_274739_ -> p_274739_.getBigX().abs().compareTo(p_194651_) < 0 && p_274739_.getBigY().abs().compareTo(p_194651_) < 0 && p_274739_.getBigZ().abs().compareTo(p_194651_) < 0
                        ? DataResult.success(p_274739_)
                        : DataResult.error(() -> "Position out of range, expected at most " + p_194651_ + ": " + p_274739_)
        );
    }

    public Vec3l(BigInteger x, BigInteger y, BigInteger z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3l(long x, long y, long z) {
        this.x = BigInteger.val(x);
        this.y = BigInteger.val(y);
        this.z = BigInteger.val(z);
    }

    @Override
    public boolean equals(Object p_123327_) {
        if (this == p_123327_) {
            return true;
        } else if (!(p_123327_ instanceof Vec3l)) {
            return false;
        } else {
            Vec3l Vec3l = (Vec3l)p_123327_;
            if (!this.getBigX().equals(Vec3l.getBigX())) {
                return false;
            } else if (!this.getBigY().equals(Vec3l.getBigY())) {
                return false;
            } else {
                return this.getBigZ().equals(Vec3l.getBigZ());
            }
        }
    }

    @Override
    public int hashCode() {
        return (this.getBigY().add(this.getBigZ().multiply(31))).multiply(31).add(this.getBigX()).intValue();
    }

    public int compareTo(Vec3l p_123330_) {
        if (this.getBigY().equals(p_123330_.getBigY())) {
            return this.getBigZ().equals(p_123330_.getBigZ()) ? this.getBigX().subtract(p_123330_.getBigX()).intValue() : this.getBigZ().subtract(p_123330_.getBigZ()).intValue();
        } else {
            return this.getBigY().subtract(p_123330_.getBigY()).intValue();
        }
    }

    public int getX() {
        return this.x.intValue();
    }

    public int getY() {
        return this.y.intValue();
    }

    public int getZ() {
        return this.z.intValue();
    }

    public BigInteger getBigX() {
        return this.x;
    }

    public BigInteger getBigY() {
        return this.y;
    }

    public BigInteger getBigZ() {
        return this.z;
    }

    protected Vec3l setX(BigInteger p_175605_) {
        this.x = p_175605_;
        return this;
    }

    protected Vec3l setY(BigInteger p_175604_) {
        this.y = p_175604_;
        return this;
    }

    protected Vec3l setZ(BigInteger p_175603_) {
        this.z = p_175603_;
        return this;
    }

    public Vec3l offset(long xOff, long yOff, long zOff) {
        return xOff == 0 && yOff == 0 && zOff == 0 ? this : new Vec3l(this.getBigX().add(xOff), this.getBigY().add(yOff), this.getBigZ().add(zOff));
    }

    public Vec3l offset(BigInteger xOff, BigInteger yOff, BigInteger zOff) {
        return xOff.equals(BigInteger.ZERO) && yOff.equals(BigInteger.ZERO) && zOff.equals(BigInteger.ZERO) ? this : new Vec3l(this.getBigX().add(xOff), this.getBigY().add(yOff), this.getBigZ().add(zOff));
    }

    public Vec3l offset(Vec3l p_175597_) {
        return this.offset(p_175597_.getBigX(), p_175597_.getBigY(), p_175597_.getBigZ());
    }

    public Vec3l subtract(Vec3l p_175596_) {
        return this.offset(p_175596_.getBigX().negate(), p_175596_.getBigY().negate(), p_175596_.getBigZ().negate());
    }

    public Vec3l multiply(int val) {
        if (val == 1) {
            return this;
        } else {
            return val == 0 ? ZERO : new Vec3l(this.getBigX().multiply(val), this.getBigY().multiply(val), this.getBigZ().multiply(val));
        }
    }

    public Vec3l above() {
        return this.above(1);
    }

    public Vec3l above(int p_123336_) {
        return this.relative(Direction.UP, p_123336_);
    }

    public Vec3l below() {
        return this.below(1);
    }

    public Vec3l below(int p_123335_) {
        return this.relative(Direction.DOWN, p_123335_);
    }

    public Vec3l north() {
        return this.north(1);
    }

    public Vec3l north(int p_175601_) {
        return this.relative(Direction.NORTH, p_175601_);
    }

    public Vec3l south() {
        return this.south(1);
    }

    public Vec3l south(int p_175600_) {
        return this.relative(Direction.SOUTH, p_175600_);
    }

    public Vec3l west() {
        return this.west(1);
    }

    public Vec3l west(int p_175599_) {
        return this.relative(Direction.WEST, p_175599_);
    }

    public Vec3l east() {
        return this.east(1);
    }

    public Vec3l east(int p_175598_) {
        return this.relative(Direction.EAST, p_175598_);
    }

    public Vec3l relative(Direction p_175592_) {
        return this.relative(p_175592_, 1);
    }

    public Vec3l relative(Direction p_123321_, int offset) {
        return offset == 0
                ? this
                : new Vec3l(
                this.getBigX().add(p_123321_.getStepX() * offset), this.getBigY().add(p_123321_.getStepY() * offset), this.getBigZ().add(p_123321_.getStepZ() * offset)
        );
    }

    public Vec3l relative(Direction.Axis p_175590_, int p_175591_) {
        if (p_175591_ == 0) {
            return this;
        } else {
            int i = p_175590_ == Direction.Axis.X ? p_175591_ : 0;
            int j = p_175590_ == Direction.Axis.Y ? p_175591_ : 0;
            int k = p_175590_ == Direction.Axis.Z ? p_175591_ : 0;
            return new Vec3l(this.getBigX().add(i), this.getBigY().add(j), this.getBigZ().add(k));
        }
    }

    public Vec3l cross(Vec3l p_123325_) {
        return new Vec3l(
                this.getBigY().multiply(p_123325_.getBigZ()).subtract(this.getBigZ().multiply(p_123325_.getBigY())),
                this.getBigZ().multiply(p_123325_.getBigX()).subtract(this.getBigX().multiply(p_123325_.getBigZ())),
                this.getBigX().multiply(p_123325_.getBigY()).subtract(this.getBigY().multiply(p_123325_.getBigX()))
        );
    }

    public boolean closerThan(Vec3l p_123315_, double p_123316_) {
        return this.distSqr(p_123315_) < Mth.square(p_123316_);
    }

    public boolean closerToCenterThan(Position p_203196_, double p_203197_) {
        return this.distToCenterSqr(p_203196_) < Mth.square(p_203197_);
    }

    public double distSqr(Vec3l p_123332_) {
        return this.distToLowCornerSqr(p_123332_.getBigX().toBigDecimal(), p_123332_.getBigY().toBigDecimal(), p_123332_.getBigZ().toBigDecimal());
    }

    public double distToCenterSqr(Position p_203194_) {
        return this.distToCenterSqr(BigDecimal.val(p_203194_.x()), BigDecimal.val(p_203194_.y()), BigDecimal.val(p_203194_.z()));
    }

    public double distToCenterSqr(BigDecimal p_203199_, BigDecimal p_203200_, BigDecimal p_203201_) {
        double d0 = this.getBigX().add(0.5).subtract(p_203199_).doubleValue();
        double d1 = this.getBigY().add(0.5).subtract(p_203200_).doubleValue();
        double d2 = this.getBigZ().add(0.5).subtract(p_203201_).doubleValue();
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distToLowCornerSqr(BigDecimal p_203203_, BigDecimal p_203204_, BigDecimal p_203205_) {
        double d0 = this.getBigX().subtract(p_203203_).doubleValue();
        double d1 = this.getBigY().subtract(p_203204_).doubleValue();
        double d2 = this.getBigZ().subtract(p_203205_).doubleValue();
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distToLowCornerSqr(double p_203203_, double p_203204_, double p_203205_) {
        double d0 = this.getBigX().subtract(p_203203_).doubleValue();
        double d1 = this.getBigY().subtract(p_203204_).doubleValue();
        double d2 = this.getBigZ().subtract(p_203205_).doubleValue();
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public int distManhattan(Vec3l p_123334_) {
        float f = p_123334_.getBigX().subtract(this.getBigX()).abs().floatValue();
        float f1 = p_123334_.getBigY().subtract(this.getBigY()).abs().floatValue();
        float f2 = p_123334_.getBigZ().subtract(this.getBigZ()).abs().floatValue();
        return (int)(f + f1 + f2);
    }

    public me.alphamode.mcbig.math.BigInteger get(Direction.Axis p_123305_) {
        return p_123305_.choose(this.x, this.y, this.z);
    }

    public int getInt(Direction.Axis p_123305_) {
        return p_123305_.choose(this.x.intValue(), this.y.intValue(), this.z.intValue());
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("x", this.getBigX()).add("y", this.getBigY()).add("z", this.getBigZ()).toString();
    }

    public String toShortString() {
        return this.getBigX() + ", " + this.getBigY() + ", " + this.getBigZ();
    }
}
