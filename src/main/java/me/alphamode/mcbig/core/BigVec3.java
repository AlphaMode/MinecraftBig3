package me.alphamode.mcbig.core;

import com.mojang.serialization.Codec;
import java.util.EnumSet;
import java.util.List;

import me.alphamode.mcbig.math.BigDecimal;
import me.alphamode.mcbig.math.BigMath;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import org.joml.Vector3f;

public class BigVec3 implements Position, BigPosition {
    public static final Codec<BigVec3> CODEC = Codec.DOUBLE
            .listOf()
            .comapFlatMap(
                    p_231079_ -> Util.fixedSize(p_231079_, 3).map(p_231081_ -> new BigVec3(p_231081_.get(0), p_231081_.get(1), p_231081_.get(2))),
                    p_231083_ -> List.of(p_231083_.x(), p_231083_.y(), p_231083_.z())
            );
    public static final BigVec3 ZERO = new BigVec3(0.0, 0.0, 0.0);
    public final BigDecimal x;
    public final double y;
    public final BigDecimal z;

    public static BigVec3 fromRGB24(int rgb) {
        double r = (double)(rgb >> 16 & 0xFF) / 255.0;
        double g = (double)(rgb >> 8 & 0xFF) / 255.0;
        double b = (double)(rgb & 0xFF) / 255.0;
        return new BigVec3(r, g, b);
    }

    public static BigVec3 atLowerCornerOf(me.alphamode.mcbig.core.Vec3l p_82529_) {
        return new BigVec3(p_82529_.getBigX().toBigDecimal(), p_82529_.getBigY().doubleValue(), p_82529_.getBigZ().toBigDecimal());
    }

    public static BigVec3 atLowerCornerWithOffset(me.alphamode.mcbig.core.Vec3l p_272866_, double p_273680_, double p_273668_, double p_273687_) {
        return new BigVec3(p_272866_.getBigX().toBigDecimal().add(p_273680_), p_272866_.getBigY().doubleValue() + p_273668_, p_272866_.getBigZ().toBigDecimal().add(p_273687_));
    }

    public static BigVec3 atCenterOf(me.alphamode.mcbig.core.Vec3l p_82513_) {
        return atLowerCornerWithOffset(p_82513_, 0.5, 0.5, 0.5);
    }

    public static BigVec3 atBottomCenterOf(me.alphamode.mcbig.core.Vec3l p_82540_) {
        return atLowerCornerWithOffset(p_82540_, 0.5, 0.0, 0.5);
    }

    public static BigVec3 upFromBottomCenterOf(me.alphamode.mcbig.core.Vec3l p_82515_, double p_82516_) {
        return atLowerCornerWithOffset(p_82515_, 0.5, p_82516_, 0.5);
    }

    public BigVec3(BigDecimal x, double y, BigDecimal z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public BigVec3(double x, double y, double z) {
        this.x = BigDecimal.val(x);
        this.y = y;
        this.z = BigDecimal.val(z);
    }

    public BigVec3(Vector3f p_253821_) {
        this(p_253821_.x(), p_253821_.y(), p_253821_.z());
    }

    public BigVec3 vectorTo(BigVec3 p_82506_) {
        return new BigVec3(p_82506_.x.subtract(this.x), p_82506_.y - this.y, p_82506_.z.subtract(this.z));
    }

    public BigVec3 normalize() {
        BigDecimal d0 = BigMath.sqrt(this.x.multiply(this.x).add(this.y * this.y).add(this.z.multiply(this.z)));
        return d0.compareTo(1.0E-4) < 0 ? ZERO : new BigVec3(this.x.divide(d0), BigDecimal.val(this.y).divide(d0).doubleValue(), this.z.divide(d0));
    }

    public BigDecimal dot(BigVec3 p_82527_) {
        return this.x.multiply(p_82527_.x).add(this.y * p_82527_.y).add(this.z.multiply(p_82527_.z));
    }

    public BigVec3 cross(BigVec3 p_82538_) {
        return new BigVec3(BigDecimal.val(this.y).multiply(p_82538_.z).subtract(this.z.multiply(p_82538_.y)), this.z.multiply(p_82538_.x).subtract(this.x.multiply(p_82538_.z)).doubleValue(), this.x.multiply(p_82538_.y).subtract(BigDecimal.val(this.y).multiply(p_82538_.x)));
    }

    public BigVec3 subtract(BigVec3 p_82547_) {
        return this.subtract(p_82547_.x, p_82547_.y, p_82547_.z);
    }

    public BigVec3 subtract(BigDecimal xOff, double yOff, BigDecimal zOff) {
        return this.add(xOff.negate(), -yOff, zOff.negate());
    }

    public BigVec3 subtract(double xOff, double yOff, double zOff) {
        return this.subtract(BigDecimal.val(xOff), yOff, BigDecimal.val(zOff));
    }

    public BigVec3 add(BigVec3 toAdd) {
        return this.add(toAdd.x, toAdd.y, toAdd.z);
    }

    public BigVec3 add(Vec3 toAdd) {
        return this.add(toAdd.x, toAdd.y, toAdd.z);
    }

    public BigVec3 add(BigDecimal xOff, double yOff, BigDecimal zOff) {
        return new BigVec3(this.x.add(xOff), this.y + yOff, this.z.add(zOff));
    }

    public BigVec3 add(double xOff, double yOff, double zOff) {
        return add(BigDecimal.val(xOff), yOff, BigDecimal.val(zOff));
    }

    public boolean closerThan(BigPosition p_82510_, double p_82511_) {
        return this.distanceToSqr(p_82510_.x(), p_82510_.y(), p_82510_.z()) < p_82511_ * p_82511_;
    }

    public double distanceTo(BigVec3 p_82555_) {
        double d0 = p_82555_.x.subtract(this.x).doubleValue();
        double d1 = p_82555_.y - this.y;
        double d2 = p_82555_.z.subtract(this.z).doubleValue();
        return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
    }

    public double distanceToSqr(BigVec3 p_82558_) {
        double d0 = p_82558_.x.subtract(this.x).doubleValue();
        double d1 = p_82558_.y - this.y;
        double d2 = p_82558_.z.subtract(this.z).doubleValue();
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceToSqr(Vec3 p_82558_) {
        return distanceToSqr(p_82558_.toBig());
    }

    public double distanceToSqr(BigDecimal x, double y, BigDecimal z) {
        double d0 = x.subtract(this.x).doubleValue();
        double d1 = y - this.y;
        double d2 = z.subtract(this.z).doubleValue();
        return d0 * d0 + d1 * d1 + d2 * d2;
    }

    public double distanceToSqr(double x, double y, double z) {
        return distanceToSqr(BigDecimal.val(x), y, BigDecimal.val(z));
    }

    public boolean closerThan(BigVec3 p_312866_, double p_312928_, double p_312788_) {
        double d0 = p_312866_.bigX().subtract(this.x).doubleValue();
        double d1 = p_312866_.y() - this.y;
        double d2 = p_312866_.bigZ().subtract(this.z).doubleValue();
        return Mth.lengthSquared(d0, d2) < Mth.square(p_312928_) && Math.abs(d1) < p_312788_;
    }

    public BigVec3 scale(double p_82491_) {
        return this.multiply(p_82491_, p_82491_, p_82491_);
    }

    public BigVec3 reverse() {
        return this.scale(-1.0);
    }

    public BigVec3 multiply(BigVec3 p_82560_) {
        return this.multiply(p_82560_.x, p_82560_.y, p_82560_.z);
    }

    public BigVec3 multiply(BigDecimal xMul, double yMul, BigDecimal zMul) {
        return new BigVec3(this.x.multiply(xMul), this.y * yMul, this.z.multiply(zMul));
    }

    public BigVec3 multiply(double xMul, double yMul, double zMul) {
        return multiply(BigDecimal.val(xMul), yMul, BigDecimal.val(zMul));
    }

    public BigVec3 offsetRandom(RandomSource p_272810_, float p_273473_) {
        return this.add(
                (double)((p_272810_.nextFloat() - 0.5F) * p_273473_),
                (double)((p_272810_.nextFloat() - 0.5F) * p_273473_),
                (double)((p_272810_.nextFloat() - 0.5F) * p_273473_)
        );
    }

    public BigDecimal length() {
        return BigMath.sqrt(this.x.multiply(this.x).add(this.y * this.y).add(this.z.multiply(this.z)));
    }

    public BigDecimal lengthSqr() {
        return this.x.multiply(this.x).add(this.y * this.y).add(this.z.multiply(this.z));
    }

    public BigDecimal horizontalDistance() {
        return BigMath.sqrt(this.x.multiply(this.x).add(this.z.multiply(this.z)));
    }

    public BigDecimal horizontalDistanceSqr() {
        return this.x.multiply(this.x).add(this.z.multiply(this.z));
    }

    @Override
    public boolean equals(Object p_82552_) {
        if (this == p_82552_) {
            return true;
        } else if (!(p_82552_ instanceof BigVec3)) {
            return false;
        } else {
            BigVec3 vec3 = (BigVec3)p_82552_;
            if (vec3.x.compareTo(this.x) != 0) {
                return false;
            } else if (Double.compare(vec3.y, this.y) != 0) {
                return false;
            } else {
                return vec3.z.compareTo(this.z) == 0;
            }
        }
    }

    @Override
    public int hashCode() {
        long j = Double.doubleToLongBits(this.x.doubleValue());
        int i = (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.y);
        i = 31 * i + (int)(j ^ j >>> 32);
        j = Double.doubleToLongBits(this.z.doubleValue());
        return 31 * i + (int)(j ^ j >>> 32);
    }

    @Override
    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

    public BigVec3 lerp(BigVec3 p_165922_, double p_165923_) {
        return new BigVec3(Mth.lerp(p_165923_, this.x, p_165922_.x), Mth.lerp(p_165923_, this.y, p_165922_.y), Mth.lerp(p_165923_, this.z, p_165922_.z));
    }

    public BigVec3 xRot(float p_82497_) {
        float f = Mth.cos(p_82497_);
        float f1 = Mth.sin(p_82497_);
        BigDecimal d0 = this.x;
        BigDecimal d1 = BigDecimal.val(this.y * (double)f).add(this.z.multiply(f1));
        BigDecimal d2 = this.z.multiply(f).subtract(this.y * (double)f1);
        return new BigVec3(d0, d1.doubleValue(), d2);
    }

    public BigVec3 yRot(float p_82525_) {
        float f = Mth.cos(p_82525_);
        float f1 = Mth.sin(p_82525_);
        BigDecimal d0 = this.x.multiply(f).add(this.z.multiply(f1));
        double d1 = this.y;
        BigDecimal d2 = this.z.multiply(f).subtract(this.x.multiply(f1));
        return new BigVec3(d0, d1, d2);
    }

    public BigVec3 zRot(float p_82536_) {
        float f = Mth.cos(p_82536_);
        float f1 = Mth.sin(p_82536_);
        BigDecimal d0 = this.x.multiply(f).add(this.y * (double)f1);
        BigDecimal d1 = BigDecimal.val(this.y * (double)f).subtract(this.x.multiply(f1));
        BigDecimal d2 = this.z;
        return new BigVec3(d0, d1.doubleValue(), d2);
    }

    public static BigVec3 directionFromRotation(Vec2 p_82504_) {
        return directionFromRotation(p_82504_.x, p_82504_.y);
    }

    public static BigVec3 directionFromRotation(float p_82499_, float p_82500_) {
        float f = Mth.cos(-p_82500_ * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f1 = Mth.sin(-p_82500_ * (float) (Math.PI / 180.0) - (float) Math.PI);
        float f2 = -Mth.cos(-p_82499_ * (float) (Math.PI / 180.0));
        float f3 = Mth.sin(-p_82499_ * (float) (Math.PI / 180.0));
        return new BigVec3((double)(f1 * f2), (double)f3, (double)(f * f2));
    }

    public BigVec3 align(EnumSet<Direction.Axis> p_82518_) {
        BigDecimal d0 = p_82518_.contains(Direction.Axis.X) ? Mth.bigFloor(this.x).toBigDecimal() : this.x;
        double d1 = p_82518_.contains(Direction.Axis.Y) ? (double)Mth.floor(this.y) : this.y;
        BigDecimal d2 = p_82518_.contains(Direction.Axis.Z) ? Mth.bigFloor(this.z).toBigDecimal() : this.z;
        return new BigVec3(d0, d1, d2);
    }

    public BigDecimal get(Direction.Axis p_82508_) {
        return p_82508_.choose(this.x, BigDecimal.val(this.y), this.z);
    }

    public BigVec3 with(Direction.Axis p_193104_, BigDecimal p_193105_) {
        BigDecimal d0 = p_193104_ == Direction.Axis.X ? p_193105_ : this.x;
        double d1 = p_193104_ == Direction.Axis.Y ? p_193105_.doubleValue() : this.y;
        BigDecimal d2 = p_193104_ == Direction.Axis.Z ? p_193105_ : this.z;
        return new BigVec3(d0, d1, d2);
    }

    public BigVec3 relative(Direction p_231076_, BigDecimal p_231077_) {
        Vec3i vec3i = p_231076_.getNormal();
        return new BigVec3(this.x.add(p_231077_.multiply(vec3i.getX())), this.y + p_231077_.doubleValue() * (double)vec3i.getY(), this.z.add(p_231077_.multiply(vec3i.getZ())));
    }

    @Override
    public final double x() {
        return this.x.doubleValue();
    }

    @Override
    public final double y() {
        return this.y;
    }

    @Override
    public final double z() {
        return this.z.doubleValue();
    }

    @Override
    public final BigDecimal bigX() {
        return this.x;
    }

    @Override
    public final BigDecimal bigZ() {
        return this.z;
    }

    public Vector3f toVector3f() {
        return new Vector3f(this.x.floatValue(), (float) this.y, this.z.floatValue());
    }

    public Vec3 toVanilla() {
        return new Vec3(x(), y(), z());
    }
}
