package io.github.tr7zw.fabricbukkit.mixin.client;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.tr7zw.fabricbukkit.craftfabric.ServerImpl;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;

@Mixin(IntegratedServer.class)
public class IntegratedServerMixin {

    @Inject(at = @At("HEAD"), method = "setupServer")
    private void setupServer(CallbackInfoReturnable<Boolean> info) {
	Object server = this;
	Bukkit.setServer(new ServerImpl((MinecraftServer) server));
    }

    @Inject(at = @At("HEAD"), method = "shutdown")
    private void shutdown(CallbackInfo info) {
	System.out.println("FabricBukkit prepare-stopping!");
    }

    @Inject(at = @At("RETURN"), method = "shutdown")
    private void shutdownFinal(CallbackInfo info) {
	System.out.println("FabricBukkit stopped!");
	try {
	    Field f = Bukkit.class.getDeclaredField("server");
	    f.setAccessible(true);
	    f.set(null, null);
	} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

}
