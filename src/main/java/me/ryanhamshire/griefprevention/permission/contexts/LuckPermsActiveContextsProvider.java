/*
 * This file is part of GriefPrevention, licensed under the MIT License (MIT).
 *
 * Copyright (c) bloodmc
 * Copyright (c) contributors
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
 */
package me.ryanhamshire.griefprevention.permission.contexts;

import me.lucko.luckperms.api.context.ImmutableContextSet;
import me.lucko.luckperms.sponge.service.context.DelegatingMutableContextSet;
import me.lucko.luckperms.sponge.service.model.ProxiedSubject;

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;

import java.util.Set;

/**
 * Means to obtain a LP subject's active contexts in a mutable form, without copying
 * the result of {@link Subject#getActiveContexts()} to a new set.
 */
public class LuckPermsActiveContextsProvider extends ActiveContextsProvider {

    @Override
    public Set<Context> getActiveContexts(Subject subject) {
        if (subject instanceof ProxiedSubject) {
            ImmutableContextSet activeContexts = ((ProxiedSubject) subject).getActiveContextSet();
            return new DelegatingMutableContextSet(activeContexts.mutableCopy());
        }

        return super.getActiveContexts(subject);
    }
}
