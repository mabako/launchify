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

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

/**
 * Helper class to permanently listen to Headphone-Plugging events.
 *
 * @author Marcus Bauer
 */
public class HeadphoneService extends Service {
    private HeadphoneReceiver receiver;

    /**
     * Register the Receiver.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(HeadphoneService.class.getName(), "Creating Headphone Receiver");
        receiver = new HeadphoneReceiver();
        registerReceiver(receiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
    }

    /**
     * Unregister the Receiver.
     */
    @Override
    public void onDestroy() {
        Log.d(HeadphoneService.class.getName(), "Removing Headphone Receiver");
        unregisterReceiver(receiver);

        super.onDestroy();
    }

    /**
     * Ignore any Binder thing.
     *
     * @param intent unused
     * @return {@code null}
     */
    public IBinder onBind(Intent intent) {
        return null;
    }
}
