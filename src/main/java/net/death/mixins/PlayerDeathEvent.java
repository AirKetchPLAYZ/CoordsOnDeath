
package net.death.mixins;

import net.death.ChatHudRef;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
import net.minecraft.text.LiteralText;
import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public abstract class PlayerDeathEvent {


	@Shadow
	MinecraftClient client;
	@Shadow
	ClientWorld world;

	@Inject(at = @At("HEAD"), method = "onCombatEvent")
	public void onCombatEvent(CombatEventS2CPacket packet, CallbackInfo i) {

		NetworkThreadUtils.forceMainThread(packet, ((ClientPlayNetworkHandler) (Object) this), this.client);
		if (packet.type == CombatEventS2CPacket.Type.ENTITY_DIED) {
			Entity entity = this.world.getEntityById(packet.entityId);
			if (entity == this.client.player) {
				ChatHudRef.hud.addMessage(new LiteralText("Rip! You died at " + getCoordsString(this.client.player)));
			}
		}

	}
	private String getCoordsString(PlayerEntity playerEntity) {
		
		return "X: " + playerEntity.getX() + ", Y: " + playerEntity.getY() + ", Z: " + playerEntity.getZ();
	}
}