/*
    GriefPrevention Server Plugin for Minecraft
    Copyright (C) 2012 Ryan Hamshire

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package me.ryanhamshire.GriefPrevention;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.Texts;
import org.spongepowered.common.text.SpongeTexts;

//sends a message to a player
//used to send delayed messages, for example help text triggered by a player's chat
class SendPlayerMessageTask implements Runnable {

    private Player player;
    private Text message;

    public SendPlayerMessageTask(Player player, Text message) {
        this.player = player;
        this.message = message;
    }

    @Override
    public void run() {
        if (player == null) {
            GriefPrevention.AddLogEntry(SpongeTexts.toComponent(message).getUnformattedText());
            return;
        }

        // if the player is dead, save it for after his respawn
        if (((net.minecraft.entity.player.EntityPlayerMP) this.player).isDead) {
            PlayerData playerData = GriefPrevention.instance.dataStore.getPlayerData(this.player.getUniqueId());
            playerData.messageOnRespawn = this.message;
        }

        // otherwise send it immediately
        else {
            GriefPrevention.sendMessage(this.player, Texts.of(this.message));
        }
    }
}
