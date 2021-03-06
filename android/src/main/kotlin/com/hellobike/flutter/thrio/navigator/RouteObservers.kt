/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2019 Hellobike Group
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
 * IN THE SOFTWARE.
 */

package com.hellobike.flutter.thrio.navigator

import com.hellobike.flutter.thrio.registry.RegistrySet
import com.hellobike.flutter.thrio.navigator.Log
internal object RouteObservers : RouteObserver {
    private const val TAG = "RouteObservers"

    val observers by lazy { RegistrySet<RouteObserver>() }

    override fun didPush(routeSettings: RouteSettings, previousRouteSettings: RouteSettings?) {
        observers.forEach {
            it.didPush(routeSettings, previousRouteSettings)
        }
        Log.i(TAG, "didPush: url->${routeSettings.url} " +
                "index->${routeSettings.index} " +
                "params->${routeSettings.params?.toString()}")
    }

    override fun didPop(routeSettings: RouteSettings, previousRouteSettings: RouteSettings?) {
        observers.forEach {
            it.didPop(routeSettings, previousRouteSettings)
        }
        Log.i(TAG, "didPop: url->${routeSettings.url} " +
                "index->${routeSettings.index} " +
                "params->${routeSettings.params?.toString()}")
    }

    override fun didPopTo(routeSettings: RouteSettings, previousRouteSettings: RouteSettings?) {
        observers.forEach {
            it.didPopTo(routeSettings, previousRouteSettings)
        }
        Log.i(TAG, "didPopTo: url->${routeSettings.url} " +
                "index->${routeSettings.index} " +
                "params->${routeSettings.params?.toString()}")
    }

    override fun didRemove(routeSettings: RouteSettings, previousRouteSettings: RouteSettings?) {
        observers.forEach {
            it.didRemove(routeSettings, previousRouteSettings)
        }
        Log.i(TAG, "didRemove: url->${routeSettings.url} " +
                "index->${routeSettings.index} " +
                "params->${routeSettings.params?.toString()}")
    }
}