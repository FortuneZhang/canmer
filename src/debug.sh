#!/usr/bin
echo 'auto'

adb clean

adb uninstall com.learn.demo02

ant debug

adb install -r bin/demo02-debug.apk

adb shell am start -n com.learn.demo02/com.learn.demo02.MainActivity

echo 'activtiy running'
