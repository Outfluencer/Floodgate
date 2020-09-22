/*
 * Copyright (c) 2019-2020 GeyserMC. http://geysermc.org
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 * @author GeyserMC
 * @link https://github.com/GeyserMC/Floodgate
 */

package org.geysermc.floodgate.addon;

import com.google.inject.Inject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import org.geysermc.floodgate.addon.addonmanager.AddonManagerHandler;
import org.geysermc.floodgate.api.inject.InjectorAddon;
import org.geysermc.floodgate.inject.CommonPlatformInjector;

public final class AddonManagerAddon implements InjectorAddon {
    @Inject private CommonPlatformInjector injector;

    @Override
    public void onInject(Channel channel, boolean proxyToServer) {
        channel.pipeline().addLast("floodgate_addon", new AddonManagerHandler(injector, channel));
    }

    @Override
    public void onLoginDone(Channel channel) {
        // AddonManagerHandler is also responsible for removing the addons when the channel closes
    }

    @Override
    public void onRemoveInject(Channel channel) {
        ChannelHandler handler = channel.pipeline().get("floodgate_addon");
        if (handler != null) {
            channel.pipeline().remove(handler);
        }
    }

    @Override
    public boolean shouldInject() {
        return true;
    }
}
