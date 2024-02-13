package me.alphamode.mcbig.wisp;

import me.alphamode.wisp.loader.api.LoaderPlugin;
import me.alphamode.wisp.loader.api.PluginContext;
import me.alphamode.wisp.loader.api.WispLoader;

import java.util.UUID;

public class McBigPlugin implements LoaderPlugin {
    @Override
    public void preInit(PluginContext context) {
        context.registerGameLocator(new McBigGameLocator());
    }

    @Override
    public void init(PluginContext context) {
        if (WispLoader.get().isDevelopment()) {
            context.getArgumentList().replace("version", "wisp-dev-mcbig");
        }
    }
}
