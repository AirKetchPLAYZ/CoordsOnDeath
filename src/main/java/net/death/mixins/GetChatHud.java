
package net.death.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.death.ChatHudRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHud;

@Mixin(ChatHud.class)
public class GetChatHud {

	@Inject(at = @At("RETURN"), method = "<init>")
	public void init(MinecraftClient c, CallbackInfo i) {
		System.out.println("Found chat");
		ChatHudRef.hud = (ChatHud) (Object) this;
		
	}
}