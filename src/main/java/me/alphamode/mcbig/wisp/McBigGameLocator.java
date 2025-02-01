package me.alphamode.mcbig.wisp;

import me.alphamode.wisp.loader.api.ArgumentList;
import me.alphamode.wisp.loader.api.GameLocator;
import me.alphamode.wisp.loader.api.PluginContext;
import me.alphamode.wisp.loader.api.WispLoader;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class McBigGameLocator implements GameLocator {

    @Override
    public void launch(ArgumentList arguments) {
        try {
            MethodHandles.lookup().findStatic(Class.forName(getMainClass()), "main", MethodType.methodType(Void.TYPE, String[].class)).asFixedArity().invokeExact(arguments.toArray());
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

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

        WispLoader.LOGGER.warn("EEE");

//        gameLibs.add(findPathForMaven("net.neoforged", "neoforge", "", "client", "3.0.0"));

        return gameLibs;
    }

    static Path findLibsPath() {
        var libraryDirectoryProp = System.getProperty("libraryDirectory");
        if (libraryDirectoryProp == null) {
            throw new IllegalStateException("Missing libraryDirectory system property");
        }
        var libsPath = Path.of(libraryDirectoryProp);
        if (!Files.isDirectory(libsPath)) {
            throw new IllegalStateException("libraryDirectory system property refers to a non-directory: " + libsPath);
        }
        return libsPath;
    }

    public static Path findPathForMaven(String group, String artifact, String extension, String classifier, String version) {
        return findPathForMaven(new MavenCoordinate(group, artifact, extension, classifier, version));
    }

    public static Path findPathForMaven(MavenCoordinate artifact) {
        return findLibsPath().resolve(artifact.toRelativeRepositoryPath());
    }
}
