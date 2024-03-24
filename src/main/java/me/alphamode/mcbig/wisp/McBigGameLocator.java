package me.alphamode.mcbig.wisp;

import me.alphamode.wisp.loader.impl.minecraft.AbstractGameLocator;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class McBigGameLocator extends AbstractGameLocator {
    @Override
    public String getMainClass() {
        return "net.minecraft.client.main.Main";
    }

    @Override
    public Path locateGame(List<Path> list, String[] args) {
        return null;
    }

    @Override
    public List<Path> getGameClassPaths(String[] args) {
        var gameLibsPaths = System.getProperty("java.class.path").split(File.pathSeparator);
        ArrayList<Path> gameLibs = new ArrayList<>();

        for (String gameLib : gameLibsPaths) {
            gameLibs.add(Path.of(gameLib));
        }

        var game = locateGame(gameLibs, args);
        if (game != null)
            gameLibs.add(game);

        return gameLibs;
    }
}
