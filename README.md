# How to

To get a Git project into your build:

**Step 1.** Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```css
	allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```

**Step 2.** Add the dependency

[![link](https://www.jitpack.io/v/XingRay/Observer.svg)](https://www.jitpack.io/#XingRay/Observer)



```css
	dependencies {
	        implementation 'com.github.XingRay:Observer:0.0.6'
	}
```


