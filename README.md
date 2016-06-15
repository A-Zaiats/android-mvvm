# android-mvvm
A Model-View-ViewModel library for Android apps.
Check [Android-mvvm page](http://a-zaiats.github.io/android-mvvm/) for more information.

[![Build Status](https://travis-ci.org/A-Zaiats/android-mvvm.svg?branch=master)](https://travis-ci.org/A-Zaiats/android-mvvm)
[![codecov](https://codecov.io/gh/A-Zaiats/android-mvvm/coverage.svg?branch=master)](https://codecov.io/gh/A-Zaiats/android-mvvm)
[ ![Download](https://api.bintray.com/packages/a-zaiats/maven/io.github.azaiats.androidmvvm/images/download.svg) ](https://bintray.com/a-zaiats/maven/io.github.azaiats.androidmvvm/_latestVersion)

## Features
This library helps reduce boilerplate code in android applications:
- a useful implementation of Model-View-ViewModel pattern by leveraging the new Android Data Binding.
- automating bind ViewModel with view.
- ViewModel is isolated from Activity/Fragment lifecycle. It's particularly good at surviving configuration changes when Android destroys your Activity and Views and replaces them with new ones.

## Installation

    repositories {
        maven { url "https://dl.bintray.com/a-zaiats/maven" }
    }

    dependencies {
        compile 'io.github.azaiats.androidmvvm:androidmvvm-core:0.2.1'
        compile 'io.github.azaiats.androidmvvm:androidmvvm-navigation:0.2.1' // optional
    }

Don't forget to **enable Data Binding** in your module:

    android {
        dataBinding {
            enabled = true;
        }
    }


## Changelog
The changelog can be found in the [release section](https://github.com/A-Zaiats/android-mvvm/releases)

## Contributors
- Andrei Zaiats (andrei.zaiats@gmail.com)

The library was inspired by:
- [Android ViewModelBinding](https://github.com/jakubkinst/Android-ViewModelBinding) library by @jakubkinst
- [Mosby](https://github.com/sockeqwe/mosby) library by @sockeqwe

## License
    Copyright 2016 Andrei Zaiats

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
