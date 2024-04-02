package me.alphamode.mcbig.phys;

import java.util.Optional;
import javax.annotation.Nullable;

import me.alphamode.mcbig.core.BigVec3;
import me.alphamode.mcbig.math.BigDecimal;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

public class BigAABB {
    private static final double EPSILON = 1.0E-7;
    public final me.alphamode.mcbig.math.BigDecimal minX;
    public final double minY;
    public final me.alphamode.mcbig.math.BigDecimal minZ;
    public final me.alphamode.mcbig.math.BigDecimal maxX;
    public final double maxY;
    public final me.alphamode.mcbig.math.BigDecimal maxZ;

    public BigAABB(me.alphamode.mcbig.math.BigDecimal x0, double y0, me.alphamode.mcbig.math.BigDecimal z0, me.alphamode.mcbig.math.BigDecimal x1, double y1, me.alphamode.mcbig.math.BigDecimal z1) {
        this.minX = x0.min(x1);
        this.minY = Math.min(y0, y1);
        this.minZ = z0.min(z1);
        this.maxX = x0.max(x1);
        this.maxY = Math.max(y0, y1);
        this.maxZ = z0.max(z1);
    }

    public BigAABB(double x0, double y0, double z0, double x1, double y1, double z1) {
        this(me.alphamode.mcbig.math.BigDecimal.val(x0), y0, me.alphamode.mcbig.math.BigDecimal.val(z0), me.alphamode.mcbig.math.BigDecimal.val(x1), y1, me.alphamode.mcbig.math.BigDecimal.val(z1));
    }

    public BigAABB(BlockPos p_82305_) {
        this(
                p_82305_.getBigX().toBigDecimal(),
                (double)p_82305_.getBigY().doubleValue(),
                p_82305_.getBigZ().toBigDecimal(),
                (p_82305_.getBigX().add()).toBigDecimal(),
                (double)(p_82305_.getBigY().add()).doubleValue(),
                (p_82305_.getBigZ().add()).toBigDecimal()
        );
    }

    public BigAABB(BigVec3 p_82302_, BigVec3 p_82303_) {
        this(p_82302_.x, p_82302_.y, p_82302_.z, p_82303_.x, p_82303_.y, p_82303_.z);
    }

    public static BigAABB of(BoundingBox p_82322_) {
        return new BigAABB(
                p_82322_.minX().toBigDecimal(),
                (double)p_82322_.minY().doubleValue(),
                p_82322_.minZ().toBigDecimal(),
                (p_82322_.maxX().add()).toBigDecimal(),
                (double)(p_82322_.maxY().add()).doubleValue(),
                (p_82322_.maxZ().add()).toBigDecimal()
        );
    }

    public static BigAABB unitCubeFromLowerCorner(BigVec3 p_82334_) {
        return new BigAABB(p_82334_.x, p_82334_.y, p_82334_.z, p_82334_.x.add(me.alphamode.mcbig.core.BigConstants.ONE), p_82334_.y + 1.0, p_82334_.z.add(me.alphamode.mcbig.core.BigConstants.ONE));
    }

    public static BigAABB encapsulatingFullBlocks(BlockPos p_309165_, BlockPos p_308877_) {
        return new BigAABB(
                p_309165_.getBigX().min(p_308877_.getBigX()).toBigDecimal(),
                (double)p_309165_.getBigY().min(p_308877_.getBigY()).doubleValue(),
                p_309165_.getBigZ().min(p_308877_.getBigZ()).toBigDecimal(),
                (p_309165_.getBigX().max(p_308877_.getBigX()).add()).toBigDecimal(),
                (double)(p_309165_.getBigY().max(p_308877_.getBigY()).add()).doubleValue(),
                (p_309165_.getBigZ().max(p_308877_.getBigZ()).add()).toBigDecimal()
        );
    }

    public BigAABB setMinX(me.alphamode.mcbig.math.BigDecimal p_165881_) {
        return new BigAABB(p_165881_, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public BigAABB setMinY(double p_165888_) {
        return new BigAABB(this.minX, p_165888_, this.minZ, this.maxX, this.maxY, this.maxZ);
    }

    public BigAABB setMinZ(me.alphamode.mcbig.math.BigDecimal p_165890_) {
        return new BigAABB(this.minX, this.minY, p_165890_, this.maxX, this.maxY, this.maxZ);
    }

    public BigAABB setMaxX(me.alphamode.mcbig.math.BigDecimal p_165892_) {
        return new BigAABB(this.minX, this.minY, this.minZ, p_165892_, this.maxY, this.maxZ);
    }

    public BigAABB setMaxY(double p_165894_) {
        return new BigAABB(this.minX, this.minY, this.minZ, this.maxX, p_165894_, this.maxZ);
    }

    public BigAABB setMaxZ(me.alphamode.mcbig.math.BigDecimal p_165896_) {
        return new BigAABB(this.minX, this.minY, this.minZ, this.maxX, this.maxY, p_165896_);
    }

    public me.alphamode.mcbig.math.BigDecimal min(Direction.Axis p_82341_) {
        return p_82341_.choose(this.minX, me.alphamode.mcbig.math.BigDecimal.val(this.minY), this.minZ);
    }

    public me.alphamode.mcbig.math.BigDecimal max(Direction.Axis p_82375_) {
        return p_82375_.choose(this.maxX, me.alphamode.mcbig.math.BigDecimal.val(this.maxY), this.maxZ);
    }

    @Override
    public boolean equals(Object p_82398_) {
        if (this == p_82398_) {
            return true;
        } else if (!(p_82398_ instanceof BigAABB)) {
            return false;
        } else {
            BigAABB aabb = (BigAABB)p_82398_;
            if (aabb.minX.compareTo(this.minX) != 0) {
                return false;
            } else if (Double.compare(aabb.minY, this.minY) != 0) {
                return false;
            } else if (aabb.minZ.compareTo(this.minZ) != 0) {
                return false;
            } else if (aabb.maxX.compareTo(this.maxX) != 0) {
                return false;
            } else if (Double.compare(aabb.maxY, this.maxY) != 0) {
                return false;
            } else {
                return aabb.maxZ.compareTo(this.maxZ) == 0;
            }
        }
    }

    @Override
    public int hashCode() {
        long i = Double.doubleToLongBits(this.minX.doubleValue());
        int j = (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.minY);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.minZ.doubleValue());
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxX.doubleValue());
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxY);
        j = 31 * j + (int)(i ^ i >>> 32);
        i = Double.doubleToLongBits(this.maxZ.doubleValue());
        return 31 * j + (int)(i ^ i >>> 32);
    }

    public BigAABB contract(double p_82311_, double p_82312_, double p_82313_) {
        me.alphamode.mcbig.math.BigDecimal d0 = this.minX;
        double d1 = this.minY;
        me.alphamode.mcbig.math.BigDecimal d2 = this.minZ;
        me.alphamode.mcbig.math.BigDecimal d3 = this.maxX;
        double d4 = this.maxY;
        me.alphamode.mcbig.math.BigDecimal d5 = this.maxZ;
        if (p_82311_ < 0.0) {
            d0 = d0.subtract(p_82311_);
        } else if (p_82311_ > 0.0) {
            d3 = d3.subtract(p_82311_);
        }

        if (p_82312_ < 0.0) {
            d1 -= p_82312_;
        } else if (p_82312_ > 0.0) {
            d4 -= p_82312_;
        }

        if (p_82313_ < 0.0) {
            d2 = d2.subtract(p_82313_);
        } else if (p_82313_ > 0.0) {
            d5 = d5.subtract(p_82313_);
        }

        return new BigAABB(d0, d1, d2, d3, d4, d5);
    }

    public BigAABB expandTowards(BigVec3 p_82370_) {
        return this.expandTowards(p_82370_.x, p_82370_.y, p_82370_.z);
    }

    public BigAABB expandTowards(me.alphamode.mcbig.math.BigDecimal p_82364_, double p_82365_, me.alphamode.mcbig.math.BigDecimal p_82366_) {
        me.alphamode.mcbig.math.BigDecimal d0 = this.minX;
        double d1 = this.minY;
        me.alphamode.mcbig.math.BigDecimal d2 = this.minZ;
        me.alphamode.mcbig.math.BigDecimal d3 = this.maxX;
        double d4 = this.maxY;
        me.alphamode.mcbig.math.BigDecimal d5 = this.maxZ;
        if (p_82364_.compareTo(me.alphamode.mcbig.math.BigDecimal.ZERO) < 0) {
            d0 = d0.add(p_82364_);
        } else if (p_82364_.compareTo(me.alphamode.mcbig.math.BigDecimal.ZERO) > 0) {
            d3 = d3.add(p_82364_);
        }

        if (p_82365_ < 0.0) {
            d1 += p_82365_;
        } else if (p_82365_ > 0.0) {
            d4 += p_82365_;
        }

        if (p_82366_.compareTo(me.alphamode.mcbig.math.BigDecimal.ZERO) < 0) {
            d2 = d2.add(p_82366_);
        } else if (p_82366_.compareTo(me.alphamode.mcbig.math.BigDecimal.ZERO) > 0) {
            d5 = d5.add(p_82366_);
        }

        return new BigAABB(d0, d1, d2, d3, d4, d5);
    }

    public BigAABB inflate(double p_82378_, double p_82379_, double p_82380_) {
        me.alphamode.mcbig.math.BigDecimal d0 = this.minX.subtract(p_82378_);
        double d1 = this.minY - p_82379_;
        me.alphamode.mcbig.math.BigDecimal d2 = this.minZ.subtract(p_82380_);
        me.alphamode.mcbig.math.BigDecimal d3 = this.maxX.add(p_82378_);
        double d4 = this.maxY + p_82379_;
        me.alphamode.mcbig.math.BigDecimal d5 = this.maxZ.add(p_82380_);
        return new BigAABB(d0, d1, d2, d3, d4, d5);
    }

    public BigAABB inflate(double p_82401_) {
        return this.inflate(p_82401_, p_82401_, p_82401_);
    }

    public BigAABB intersect(BigAABB p_82324_) {
        me.alphamode.mcbig.math.BigDecimal d0 = this.minX.max(p_82324_.minX);
        double d1 = Math.max(this.minY, p_82324_.minY);
        me.alphamode.mcbig.math.BigDecimal d2 = this.minZ.max(p_82324_.minZ);
        me.alphamode.mcbig.math.BigDecimal d3 = this.maxX.min(p_82324_.maxX);
        double d4 = Math.min(this.maxY, p_82324_.maxY);
        me.alphamode.mcbig.math.BigDecimal d5 = this.maxZ.min(p_82324_.maxZ);
        return new BigAABB(d0, d1, d2, d3, d4, d5);
    }

    public BigAABB minmax(BigAABB p_82368_) {
        me.alphamode.mcbig.math.BigDecimal d0 = this.minX.min(p_82368_.minX);
        double d1 = Math.min(this.minY, p_82368_.minY);
        me.alphamode.mcbig.math.BigDecimal d2 = this.minZ.min(p_82368_.minZ);
        me.alphamode.mcbig.math.BigDecimal d3 = this.maxX.max(p_82368_.maxX);
        double d4 = Math.max(this.maxY, p_82368_.maxY);
        me.alphamode.mcbig.math.BigDecimal d5 = this.maxZ.max(p_82368_.maxZ);
        return new BigAABB(d0, d1, d2, d3, d4, d5);
    }

    public BigAABB move(double p_82387_, double p_82388_, double p_82389_) {
        return new BigAABB(this.minX.add(p_82387_), this.minY + p_82388_, this.minZ.add(p_82389_), this.maxX.add(p_82387_), this.maxY + p_82388_, this.maxZ.add(p_82389_));
    }

    public BigAABB move(BlockPos p_82339_) {
        return new BigAABB(
                this.minX.add(p_82339_.getBigX().toBigDecimal()),
                this.minY + (double)p_82339_.getBigY().doubleValue(),
                this.minZ.add(p_82339_.getBigZ().toBigDecimal()),
                this.maxX.add(p_82339_.getBigX().toBigDecimal()),
                this.maxY + (double)p_82339_.getBigY().doubleValue(),
                this.maxZ.add(p_82339_.getBigZ().toBigDecimal())
        );
    }

    public BigAABB move(Vec3 p_82384_) {
        return this.move(p_82384_.x, p_82384_.y, p_82384_.z);
    }

    public boolean intersects(BigAABB p_82382_) {
        return this.intersects(p_82382_.minX, p_82382_.minY, p_82382_.minZ, p_82382_.maxX, p_82382_.maxY, p_82382_.maxZ);
    }

    public boolean intersects(me.alphamode.mcbig.math.BigDecimal p_82315_, double p_82316_, me.alphamode.mcbig.math.BigDecimal p_82317_, me.alphamode.mcbig.math.BigDecimal p_82318_, double p_82319_, me.alphamode.mcbig.math.BigDecimal p_82320_) {
        return this.minX.compareTo(p_82318_) < 0 && this.maxX.compareTo(p_82315_) > 0 && this.minY < p_82319_ && this.maxY > p_82316_ && this.minZ.compareTo(p_82320_) < 0 && this.maxZ.compareTo(p_82317_) > 0;
    }

    public boolean intersects(BigVec3 p_82336_, BigVec3 p_82337_) {
        return this.intersects(
                p_82336_.x.min(p_82337_.x),
                Math.min(p_82336_.y, p_82337_.y),
                p_82336_.z.min(p_82337_.z),
                p_82336_.x.max(p_82337_.x),
                Math.max(p_82336_.y, p_82337_.y),
                p_82336_.z.max(p_82337_.z)
        );
    }

    public boolean contains(BigVec3 p_82391_) {
        return this.contains(p_82391_.x, p_82391_.y, p_82391_.z);
    }

    public boolean contains(me.alphamode.mcbig.math.BigDecimal p_82394_, double p_82395_, me.alphamode.mcbig.math.BigDecimal p_82396_) {
        return p_82394_.compareTo(this.minX) >= 0 && p_82394_.compareTo(this.maxX) < 0 && p_82395_ >= this.minY && p_82395_ < this.maxY && p_82396_.compareTo(this.minZ) >= 0 && p_82396_.compareTo(this.maxZ) < 0;
    }

    public double getSize() {
        double d0 = this.getXsize();
        double d1 = this.getYsize();
        double d2 = this.getZsize();
        return (d0 + d1 + d2) / 3.0;
    }

    public double getXsize() {
        return this.maxX.subtract(this.minX).doubleValue();
    }

    public double getYsize() {
        return this.maxY - this.minY;
    }

    public double getZsize() {
        return this.maxZ.subtract(this.minZ).doubleValue();
    }

    public BigAABB deflate(double p_165898_, double p_165899_, double p_165900_) {
        return this.inflate(-p_165898_, -p_165899_, -p_165900_);
    }

    public BigAABB deflate(double p_82407_) {
        return this.inflate(-p_82407_);
    }

    public Optional<BigVec3> clip(BigVec3 p_82372_, BigVec3 p_82373_) {
        me.alphamode.mcbig.math.BigDecimal[] adouble = new me.alphamode.mcbig.math.BigDecimal[]{me.alphamode.mcbig.core.BigConstants.ONE};
        double d0 = p_82373_.x.subtract(p_82372_.x).doubleValue();
        double d1 = p_82373_.y - p_82372_.y;
        double d2 = p_82373_.z.subtract(p_82372_.z).doubleValue();
        Direction direction = getDirection(this, p_82372_, adouble, null, d0, d1, d2);
        if (direction == null) {
            return Optional.empty();
        } else {
            me.alphamode.mcbig.math.BigDecimal d3 = adouble[0];
            return Optional.of(p_82372_.add(d3.multiply(d0), d3.multiply(d1).doubleValue(), d3.multiply(d2)));
        }
    }

    @Nullable
    public static BlockHitResult clip(Iterable<BigAABB> p_82343_, BigVec3 p_82344_, BigVec3 p_82345_, BlockPos p_82346_) {
        me.alphamode.mcbig.math.BigDecimal[] adouble = new me.alphamode.mcbig.math.BigDecimal[]{me.alphamode.mcbig.core.BigConstants.ONE};
        Direction direction = null;
        double d0 = p_82345_.x.subtract(p_82344_.x).doubleValue();
        double d1 = p_82345_.y - p_82344_.y;
        double d2 = p_82345_.z.subtract(p_82344_.z).doubleValue();

        for(BigAABB aabb : p_82343_) {
            direction = getDirection(aabb.move(p_82346_), p_82344_, adouble, direction, d0, d1, d2);
        }

        if (direction == null) {
            return null;
        } else {
            me.alphamode.mcbig.math.BigDecimal d3 = adouble[0];
            return new BlockHitResult(p_82344_.add(d3.multiply(d0), d3.multiply(d1).doubleValue(), d3.multiply(d2)), direction, p_82346_, false);
        }
    }

    @Nullable
    private static Direction getDirection(
            BigAABB p_82326_, BigVec3 p_82327_, me.alphamode.mcbig.math.BigDecimal[] p_82328_, @Nullable Direction p_82329_, double p_82330_, double p_82331_, double p_82332_
    ) {
        if (p_82330_ > EPSILON) {
            p_82329_ = clipPoint(
                    p_82328_,
                    p_82329_,
                    p_82330_,
                    p_82331_,
                    p_82332_,
                    p_82326_.minX,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.minY),
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.maxY),
                    p_82326_.minZ,
                    p_82326_.maxZ,
                    Direction.WEST,
                    p_82327_.x,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82327_.y),
                    p_82327_.z
            );
        } else if (p_82330_ < -EPSILON) {
            p_82329_ = clipPoint(
                    p_82328_,
                    p_82329_,
                    p_82330_,
                    p_82331_,
                    p_82332_,
                    p_82326_.maxX,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.minY),
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.maxY),
                    p_82326_.minZ,
                    p_82326_.maxZ,
                    Direction.EAST,
                    p_82327_.x,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82327_.y),
                    p_82327_.z
            );
        }

        if (p_82331_ > EPSILON) {
            p_82329_ = clipPoint(
                    p_82328_,
                    p_82329_,
                    p_82331_,
                    p_82332_,
                    p_82330_,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.minY),
                    p_82326_.minZ,
                    p_82326_.maxZ,
                    p_82326_.minX,
                    p_82326_.maxX,
                    Direction.DOWN,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82327_.y),
                    p_82327_.z,
                    p_82327_.x
            );
        } else if (p_82331_ < -EPSILON) {
            p_82329_ = clipPoint(
                    p_82328_,
                    p_82329_,
                    p_82331_,
                    p_82332_,
                    p_82330_,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.maxY),
                    p_82326_.minZ,
                    p_82326_.maxZ,
                    p_82326_.minX,
                    p_82326_.maxX,
                    Direction.UP,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82327_.y),
                    p_82327_.z,
                    p_82327_.x
            );
        }

        if (p_82332_ > EPSILON) {
            p_82329_ = clipPoint(
                    p_82328_,
                    p_82329_,
                    p_82332_,
                    p_82330_,
                    p_82331_,
                    p_82326_.minZ,
                    p_82326_.minX,
                    p_82326_.maxX,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.minY),
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.maxY),
                    Direction.NORTH,
                    p_82327_.z,
                    p_82327_.x,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82327_.y)
            );
        } else if (p_82332_ < -EPSILON) {
            p_82329_ = clipPoint(
                    p_82328_,
                    p_82329_,
                    p_82332_,
                    p_82330_,
                    p_82331_,
                    p_82326_.maxZ,
                    p_82326_.minX,
                    p_82326_.maxX,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.minY),
                    me.alphamode.mcbig.math.BigDecimal.val(p_82326_.maxY),
                    Direction.SOUTH,
                    p_82327_.z,
                    p_82327_.x,
                    me.alphamode.mcbig.math.BigDecimal.val(p_82327_.y)
            );
        }

        return p_82329_;
    }

    @Nullable
    private static Direction clipPoint(
            me.alphamode.mcbig.math.BigDecimal[] p_82348_,
            @Nullable Direction p_82349_,
            double p_82350_,
            double p_82351_,
            double p_82352_,
            me.alphamode.mcbig.math.BigDecimal p_82353_,
            me.alphamode.mcbig.math.BigDecimal p_82354_,
            me.alphamode.mcbig.math.BigDecimal p_82355_,
            me.alphamode.mcbig.math.BigDecimal p_82356_,
            me.alphamode.mcbig.math.BigDecimal p_82357_,
            Direction p_82358_,
            me.alphamode.mcbig.math.BigDecimal p_82359_,
            me.alphamode.mcbig.math.BigDecimal p_82360_,
            me.alphamode.mcbig.math.BigDecimal p_82361_
    ) {
        me.alphamode.mcbig.math.BigDecimal d0 = (p_82353_.subtract(p_82359_)).divide(p_82350_);
        me.alphamode.mcbig.math.BigDecimal d1 = p_82360_.add(d0.multiply(p_82351_));
        me.alphamode.mcbig.math.BigDecimal d2 = p_82361_.add(d0.multiply(p_82352_));
        if (me.alphamode.mcbig.math.BigDecimal.ZERO.compareTo(d0) < 0 && d0.compareTo(p_82348_[0]) < 0 && p_82354_.subtract(EPSILON).compareTo(d1) < 0 && d1.compareTo(p_82355_.add(EPSILON)) < 0 && p_82356_.subtract(EPSILON).compareTo(d2) < 0 && d2.compareTo(p_82357_.add(EPSILON)) < 0) {
            p_82348_[0] = d0;
            return p_82358_;
        } else {
            return p_82349_;
        }
    }

    public double distanceToSqr(BigVec3 p_273572_) {
        double d0 = this.minX.subtract(p_273572_.x).max(p_273572_.x.subtract(this.maxX)).max(me.alphamode.mcbig.math.BigDecimal.ZERO).doubleValue();
        double d1 = Math.max(Math.max(this.minY - p_273572_.y, p_273572_.y - this.maxY), 0.0);
        double d2 = this.minZ.subtract(p_273572_.z).max(p_273572_.z.subtract(this.maxZ)).max(me.alphamode.mcbig.math.BigDecimal.ZERO).doubleValue();
        return Mth.lengthSquared(d0, d1, d2);
    }

    @Override
    public String toString() {
        return "AABB[" + this.minX + ", " + this.minY + ", " + this.minZ + "] -> [" + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
    }

    public boolean hasNaN() {
        return Double.isNaN(this.minX.doubleValue())
                || Double.isNaN(this.minY)
                || Double.isNaN(this.minZ.doubleValue())
                || Double.isNaN(this.maxX.doubleValue())
                || Double.isNaN(this.maxY)
                || Double.isNaN(this.maxZ.doubleValue());
    }

    public BigVec3 getCenter() {
        return new BigVec3(Mth.lerp(0.5, this.minX, this.maxX), Mth.lerp(0.5, this.minY, this.maxY), Mth.lerp(0.5, this.minZ, this.maxZ));
    }

    public static BigAABB ofSize(BigVec3 pos, me.alphamode.mcbig.math.BigDecimal x, double y, me.alphamode.mcbig.math.BigDecimal z) {
        return new BigAABB(
                pos.x.subtract(x.divide(me.alphamode.mcbig.core.BigConstants.TWO)),
                pos.y - y / 2.0,
                pos.z.subtract(z.divide(me.alphamode.mcbig.core.BigConstants.TWO)),
                pos.x.add(x.divide(me.alphamode.mcbig.core.BigConstants.TWO)),
                pos.y + y / 2.0,
                pos.z.add(z.divide(me.alphamode.mcbig.core.BigConstants.TWO))
        );
    }

    public static BigAABB ofSize(BigVec3 pos, double x, double y, double z) {
        return ofSize(pos, BigDecimal.val(x), y, BigDecimal.val(z));
    }
}
