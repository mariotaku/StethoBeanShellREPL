/*
 * Copyright (c) 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package org.mariotaku.stethoext.bsh;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.inspector.console.RuntimeRepl;
import com.facebook.stetho.inspector.console.RuntimeReplFactory;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import bsh.EvalError;
import bsh.Interpreter;

public class BshRuntimeReplFactoryBuilder {

    /**
     * Android application context.
     */
    private final Context mContext;
    private final Map<String, Object> mVariables;

    public static RuntimeReplFactory defaultFactory(@NonNull Context context) {
        return new BshRuntimeReplFactoryBuilder(context).build();
    }

    public BshRuntimeReplFactoryBuilder(@NonNull Context context) {
        mContext = context;
        mVariables = new LinkedHashMap<>();
    }

    /**
     * Build the runtime REPL instance to be supplied to the Stetho {@code Runtime} module.
     */
    public RuntimeReplFactory build() {
        return new RuntimeReplFactory() {
            @Override
            public RuntimeRepl newInstance() {
                return new BshRuntimeRepl(initInterpretor());
            }
        };
    }

    public BshRuntimeReplFactoryBuilder set(@NonNull String name, Object value) {
        mVariables.put(name, value);
        return this;
    }

    public BshRuntimeReplFactoryBuilder unset(@NonNull String name) {
        mVariables.remove(name);
        return this;
    }

    @NonNull
    private Interpreter initInterpretor() {
        Interpreter interpreter = new Interpreter();
        try {
            for (Entry<String, Object> entry : mVariables.entrySet()) {
                interpreter.set(entry.getKey(), entry.getValue());
            }
            interpreter.set("context", mContext);
            interpreter.set("application", mContext.getApplicationContext());
        } catch (EvalError evalError) {
            throw new RuntimeException(evalError);
        }
        return interpreter;
    }

}
