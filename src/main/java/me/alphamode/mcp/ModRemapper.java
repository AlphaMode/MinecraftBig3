package me.alphamode.mcp;

import net.fabricmc.mappingio.MappingReader;
import net.fabricmc.mappingio.MappingUtil;
import net.fabricmc.mappingio.tree.MappingTree;
import net.fabricmc.mappingio.tree.MemoryMappingTree;
import net.fabricmc.tinyremapper.IMappingProvider;
import net.fabricmc.tinyremapper.NonClassCopyMode;
import net.fabricmc.tinyremapper.OutputConsumerPath;
import net.fabricmc.tinyremapper.TinyRemapper;

import java.io.IOException;
import java.nio.file.Path;
import java.util.regex.Pattern;

public class ModRemapper {
    public static void main(String[] args) throws IOException {
        MemoryMappingTree mappings = new MemoryMappingTree();

        MappingReader.read(Path.of("/home/alpha/github/ExplosivesPlus/regularMcp/conf/mappings.tiny"), mappings);

        TinyRemapper remapper = TinyRemapper.newRemapper()
                .withMappings(create(mappings, "client", "named", true))
                .renameInvalidLocals(true)
                .rebuildSourceFilenames(true)
                .invalidLvNamePattern(Pattern.compile("\\$\\$\\d+"))
                .inferNameFromSameLvIndex(true)
                .build();

        var output = Path.of("mapped/Explosives+3.4-mapped.jar");
        output.getParent().toFile().mkdirs();
        try (OutputConsumerPath path = new OutputConsumerPath.Builder(output).assumeArchive(true).build()) {
            remapper.readClassPath(Path.of("/home/alpha/github/ExplosivesPlus/regularMcp/minecraft/jars/deobfuscated.jar"));

            var oldMod = Path.of("/home/alpha/github/ExplosivesPlus/jars/Explosives+v3.4.jar");
            path.addNonClassFiles(oldMod, NonClassCopyMode.FIX_META_INF, remapper);
            remapper.readInputs(oldMod);

            remapper.apply(path);
        }
        remapper.finish();
    }

    private static IMappingProvider.Member memberOf(String className, String memberName, String descriptor) {
        return new IMappingProvider.Member(className, memberName, descriptor);
    }

    public static IMappingProvider create(Path mappings, String from, String to, boolean remapLocalVariables) throws IOException {
        MemoryMappingTree mappingTree = new MemoryMappingTree();
        MappingReader.read(mappings, mappingTree);
        return create(mappingTree, from, to, remapLocalVariables);
    }

    public static IMappingProvider create(MappingTree mappings, String from, String to, boolean remapLocalVariables) {
        return (acceptor) -> {
            final int fromId = mappings.getNamespaceId(from);
            final int toId = mappings.getNamespaceId(to);

            for (MappingTree.ClassMapping classDef : mappings.getClasses()) {
                String className = classDef.getName(fromId);
                String dstName = classDef.getName(toId);

                if (dstName == null) {
                    // Unsure if this is correct, should be better than crashing tho.
                    dstName = className;
                }

                if (className == null)
                    continue;

                acceptor.acceptClass(className, dstName);

                for (MappingTree.FieldMapping field : classDef.getFields()) {
                    if (field.getName(fromId) == null)
                        continue;
                    acceptor.acceptField(memberOf(className, field.getName(fromId), field.getDesc(fromId)), field.getName(toId));
                }

                for (MappingTree.MethodMapping method : classDef.getMethods()) {
                    if (method.getName(fromId) == null)
                        continue;
                    IMappingProvider.Member methodIdentifier = memberOf(className, method.getName(fromId), method.getDesc(fromId));
                    acceptor.acceptMethod(methodIdentifier, method.getName(toId));

                    if (remapLocalVariables) {
                        for (MappingTree.MethodArgMapping parameter : method.getArgs()) {
                            String name = parameter.getName(toId);

                            if (name == null) {
                                continue;
                            }

                            acceptor.acceptMethodArg(methodIdentifier, parameter.getLvIndex(), name);
                        }

                        for (MappingTree.MethodVarMapping localVariable : method.getVars()) {
                            acceptor.acceptMethodVar(methodIdentifier, localVariable.getLvIndex(),
                                    localVariable.getStartOpIdx(), localVariable.getLvtRowIndex(),
                                    localVariable.getName(toId));
                        }
                    }
                }
            }
        };
    }
}
