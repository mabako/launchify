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

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;

/**
 * @author Marcus Bauer
 */
public final class SpotifyHelper {
    /**
     * Delay before Keycodes for Start are sent; this is mostly dependant on how long it takes Spotify to load.
     */
    public static final int DELAY_MILLIS = 3500;

    /**
     * Name of the Spotify package.
     */
    public static final String SPOTIFY_PACKAGE_NAME = "com.spotify.mobile.android.ui";

    /**
     * private Constructor as we hardly need this.
     */
    private SpotifyHelper() {
        // empty
    }

    /**
     * Starts Spotify and, if successful so, simulates pressing the play button.
     *
     * @param context Context
     */
    public static void start(Context context) {
        Log.i(SpotifyHelper.class.getName(), "start");

        Intent intent = context.getPackageManager().getLaunchIntentForPackage(SPOTIFY_PACKAGE_NAME);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

        sendInput(context);
    }

    /**
     * Sends some media keycode across the device.
     *
     * @param context Context to send it from
     */
    private static void sendInput(final Context context) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int keycode = KeyEvent.KEYCODE_MEDIA_PLAY;
                Log.i(getClass().getName(), "Sending Keycode " + keycode);
                Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                intent.putExtra(Intent.EXTRA_KEY_EVENT, new KeyEvent(KeyEvent.ACTION_UP, keycode));

                context.sendOrderedBroadcast(intent, null);
            }
        };

        Handler handler = new Handler();
        handler.post(r);
        handler.postDelayed(r, DELAY_MILLIS);
    }
}
