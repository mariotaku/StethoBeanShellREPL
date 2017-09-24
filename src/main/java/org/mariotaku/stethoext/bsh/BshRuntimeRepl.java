/*
 * Copyright (c) 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package org.mariotaku.stethoext.bsh;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.facebook.stetho.inspector.console.RuntimeRepl;

import bsh.Interpreter;

class BshRuntimeRepl implements RuntimeRepl {

    @NonNull
    private final Interpreter mInterpreter;

    BshRuntimeRepl(@NonNull Interpreter interpreter) {
        mInterpreter = interpreter;
    }

    @Nullable
    @Override
    public Object evaluate(@NonNull String expression) throws Throwable {
        return mInterpreter.eval(expression);
    }

}
