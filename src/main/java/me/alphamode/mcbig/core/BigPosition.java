package me.alphamode.mcbig.core;

import me.alphamode.mcbig.math.BigDecimal;
import net.minecraft.core.Position;

public interface BigPosition extends Position {
    BigDecimal bigX();

    BigDecimal bigZ();
}
