#!/usr/bin
echo 'auto'

adb clean

adb uninstall com.learn.canmer

ant debug

adb install -r bin/canmer-debug.apk

adb shell am start -n com.learn.canmer/com.learn.canmer.MainActivity

echo 'activtiy running'
