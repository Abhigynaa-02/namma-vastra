.PHONY: build install build-install clean lint run

ANDROID_HOME ?= /home/gk/Android/Sdk

build:
	ANDROID_HOME=$(ANDROID_HOME) ./gradlew assembleDebug

install:
	adb install -r app/build/outputs/apk/debug/app-debug.apk

build-install: build install

clean:
	ANDROID_HOME=$(ANDROID_HOME) ./gradlew clean

lint:
	ANDROID_HOME=$(ANDROID_HOME) ./gradlew lint

run:
	adb shell am start -n com.namhavastra.app/.MainActivity
