package com.jahirtrap.walljump.network.message;

import com.jahirtrap.walljump.init.WallJumpModConfig;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MessageWallJump implements IMessage<MessageWallJump> {
    public void encode(MessageWallJump message, FriendlyByteBuf buffer) {
    }

    public MessageWallJump decode(FriendlyByteBuf buffer) {
        return new MessageWallJump();
    }

    public void handle(MessageWallJump message, Supplier<NetworkEvent.Context> supplier) {
        supplier.get().enqueueWork(() ->
        {
            ServerPlayer player = supplier.get().getSender();
            if (player != null) {
                player.fallDistance = 0.0F;
                player.causeFoodExhaustion(WallJumpModConfig.COMMON.exhaustionWallJump.get().floatValue());
            }
        });
        supplier.get().setPacketHandled(true);
    }
}