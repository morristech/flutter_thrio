// The MIT License (MIT)
//
// Copyright (c) 2019 Hellobike Group
//
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the "Software"),
// to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the
// Software is furnished to do so, subject to the following conditions:
// The above copyright notice and this permission notice shall be included
// in all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
// OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
// THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
// FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS
// IN THE SOFTWARE.

#import "NavigatorRouteObserverChannel.h"
#import "ThrioNavigator+RouteObservers.h"
#import "ThrioNavigator+Internal.h"

@interface NavigatorRouteObserverChannel ()

@property (nonatomic, strong) ThrioChannel *channel;

@end

@implementation NavigatorRouteObserverChannel

- (instancetype)initWithChannel:(ThrioChannel *)channel {
    self = [super init];
    if (self) {
        _channel = channel;
        [self _on:@"didPush"];
        [self _on:@"didPop"];
        [self _on:@"didPopTo"];
        [self _on:@"didRemove"];
    }
    return self;
}

- (void)_on:(NSString *)method {
    [_channel registryMethod:method
                         handler:^void (NSDictionary<NSString *, id> *arguments,
                                        ThrioIdCallback _Nullable result) {
        NavigatorRouteSettings *routeSettings = [NavigatorRouteSettings settingsFromArguments:arguments[@"route"]];
        NavigatorRouteSettings *previousRouteSettings;
        if (arguments[@"previousRoute"]) {
            previousRouteSettings = [NavigatorRouteSettings settingsFromArguments:arguments[@"previousRoute"]];
        }

    #pragma clang diagnostic push
    #pragma clang diagnostic ignored "-Warc-performSelector-leaks"
        SEL navigationSelector = NSSelectorFromString([NSString stringWithFormat:@"thrio_%@Url:index:", method]);
        [ThrioNavigator.navigationController performSelector:navigationSelector
                                                  withObject:routeSettings.url
                                                  withObject:routeSettings.index];

        SEL observerSelector = NSSelectorFromString([NSString stringWithFormat:@"%@:previousRoute:", method]);

        [ThrioNavigator performSelector:observerSelector withObject:routeSettings withObject:previousRouteSettings];

    #pragma clang diagnostic pop
    }];
}

@end
