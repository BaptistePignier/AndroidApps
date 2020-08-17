#!/bin/sh
export PATH=$PATH:$ANDROID_HOME/build-tools/29.0.3

androiJAR="/home/miradox/comp/android/android-sdk/platforms/android-29/android.jar"

echo "CLEAN"

rm -fr obj/com
rm -fr bin/*
rm -fr gen/*

echo "AAPT2"

aapt2 compile --dir res/ -o res/res.zip
aapt2 compile --dir libs/constraint-layout-1.1.3-res -o libs/constraint-layout-1.1.3-res.zip
aapt2 link -I $androiJAR -R libs/constraint-layout-1.1.3-res.zip --auto-add-overlay --manifest src/AndroidManifest.xml --java gen/ --extra-packages android.support.constraint -o bin/AndroidTest.unsigned.apk res/res.zip

rm -f libs/constraint-layout-1.1.3-res.zip

for i in libs/*.jar; do
  libs="$libs:$i"
  dxlibs="$dxlibs $i"
  d8libs="$d8libs --classpath $i"
done
libs="${libs#:}"
all_java=$(find src gen -type f -name '*.java') #Find all R.java (from lib and src) and .java files

echo "JAVAC"

javac -bootclasspath $androiJAR -d obj/ -classpath $libs -sourcepath src:gen $all_java

echo "D8"

all_class_file=$(find obj -type f) #Find all .class files generate by previous command
d8 --release $d8libs --lib $androiJAR --output bin/ $all_class_file $dxlibs

echo "ZIP"

zip -uj bin/AndroidTest.unsigned.apk bin/classes.dex 

echo "JARSIGNER"

jarsigner -verbose -keystore AndroidTest.keystore -storepass bonjour -keypass bonjour -signedjar bin/AndroidTest.signed.apk bin/AndroidTest.unsigned.apk AndroidTestKey

echo "ZIPALIGN"

zipalign -vf 4 bin/AndroidTest.signed.apk bin/AndroidTest.apk

echo "ADB"

adb install -r bin/AndroidTest.apk
