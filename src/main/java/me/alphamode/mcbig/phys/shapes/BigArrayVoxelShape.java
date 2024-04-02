package me.alphamode.mcbig.phys.shapes;

import it.unimi.dsi.fastutil.doubles.DoubleArrayList;
import it.unimi.dsi.fastutil.doubles.DoubleList;
import java.util.Arrays;
import java.util.stream.Collectors;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import me.alphamode.mcbig.math.BigDecimal;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.shapes.DiscreteVoxelShape;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BigArrayVoxelShape extends VoxelShape {
    private final ObjectList<BigDecimal> xs;
    private final ObjectList<BigDecimal> ys;
    private final ObjectList<BigDecimal> zs;

    protected BigArrayVoxelShape(DiscreteVoxelShape p_82572_, BigDecimal[] p_82573_, BigDecimal[] p_82574_, BigDecimal[] p_82575_) {
        this(
                p_82572_,
                ObjectArrayList.wrap(Arrays.copyOf(p_82573_, p_82572_.getXSize() + 1)),
                ObjectArrayList.wrap(Arrays.copyOf(p_82574_, p_82572_.getYSize() + 1)),
                ObjectArrayList.wrap(Arrays.copyOf(p_82575_, p_82572_.getZSize() + 1))
        );
    }

    public BigArrayVoxelShape(DiscreteVoxelShape p_82567_, ObjectList<BigDecimal> p_82568_, ObjectList<BigDecimal> p_82569_, ObjectList<BigDecimal> p_82570_) {
        super(p_82567_);
        int i = p_82567_.getXSize() + 1;
        int j = p_82567_.getYSize() + 1;
        int k = p_82567_.getZSize() + 1;
        if (i == p_82568_.size() && j == p_82569_.size() && k == p_82570_.size()) {
            this.xs = p_82568_;
            this.ys = p_82569_;
            this.zs = p_82570_;
        } else {
            throw (IllegalArgumentException)Util.pauseInIde(
                    new IllegalArgumentException("Lengths of point arrays must be consistent with the size of the VoxelShape.")
            );
        }
    }

    @Override
    protected DoubleList getCoords(Direction.Axis p_82577_) {
        switch(p_82577_) {
            case X:
                return this.xs.stream().map(BigDecimal::doubleValue).collect(Collectors.toCollection(DoubleArrayList::new));
            case Y:
                return this.ys.stream().map(BigDecimal::doubleValue).collect(Collectors.toCollection(DoubleArrayList::new));
            case Z:
                return this.zs.stream().map(BigDecimal::doubleValue).collect(Collectors.toCollection(DoubleArrayList::new));
            default:
                throw new IllegalArgumentException();
        }
    }

    @Override
    protected ObjectList<BigDecimal> getBigCoords(Direction.Axis p_82577_) {
        switch(p_82577_) {
            case X:
                return this.xs;
            case Y:
                return this.ys;
            case Z:
                return this.zs;
            default:
                throw new IllegalArgumentException();
        }
    }
}
