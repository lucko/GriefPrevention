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

import org.spongepowered.api.service.context.Context;
import org.spongepowered.api.service.permission.Subject;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ActiveContextsProvider {
    private static ActiveContextsProvider instance = null;

    public static ActiveContextsProvider get() {
        if (instance == null) {
            if (isLuckPerms()) {
                instance = new LuckPermsActiveContextsProvider();
            } else {
                instance = new ActiveContextsProvider();
            }

        }
        return instance;
    }

    public Set<Context> getActiveContexts(Subject s) {
        return new HashSet<>(s.getActiveContexts());
    }

    private static boolean isLuckPerms() {
        try {
            Class<?> clazz = Class.forName("me.lucko.luckperms.sponge.service.model.ProxiedSubject");
            Method method = clazz.getDeclaredMethod("getActiveContextSet");
            Objects.requireNonNull(method);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
