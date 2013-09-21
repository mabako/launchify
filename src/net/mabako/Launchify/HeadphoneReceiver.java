/*
 * Copyright (c) 2013 Marcus Bauer
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.mabako.Launchify;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Receiver for Headphone plugging state.
 *
 * @author Marcus Bauer
 */
public class HeadphoneReceiver extends BroadcastReceiver {
    /**
     * Ignore the first message to be received, that's more of a "current status" message which shouldn't apply anyhow.
     */
    private boolean ignoreNextMessage = true;

    /**
     * Watch for state changes.
     *
     * @param context Context
     * @param intent  Intent
     */
    public void onReceive(Context context, Intent intent) {
        if (!ignoreNextMessage) {
            if (intent.hasExtra("state")) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        // not plugged in
                        SpotifyHelper.stop(context);
                        break;
                    case 1:
                        // plugged in
                        SpotifyHelper.start(context);
                        break;
                    default:
                        Log.e(HeadphoneReceiver.class.getName(), "Invalid State for plugging in: " + state);
                }
            }
        } else {
            ignoreNextMessage = false;
            Log.i(HeadphoneReceiver.class.getName(), "Ignoring this message");
        }
    }
}
