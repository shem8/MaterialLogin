# MaterialLogin

A material-designed login (and register) view

![](art/example.gif)


Download
--------

Grab via Gradle:
```groovy
compile 'com.github.shem8:material-login:2.1.1'
```
or Maven:
```xml
<dependency>
  <groupId>com.github.shem8</groupId>
  <artifactId>material-login</artifactId>
  <version>2.1.0</version>
</dependency>
```

You should also add [CircularReveal lib](https://github.com/ozodrukh/CircularReveal) for 2.3 support- first add remote maven url

```groovy
    repositories {
        maven {
            url "https://jitpack.io"
        }
    }
```
    
then add a library dependency

```groovy
    dependencies {
        compile ('com.github.ozodrukh:CircularReveal:1.3.1@aar') {
            transitive = true;
        }
    }
```


Usage
-----

Add the LoginView to your layout

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <shem.com.materiallogin.MaterialLoginView
        android:id="@+id/login"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</FrameLayout>
```

Then set your `MaterialLoginViewListener` to the view in code to handle register and login events:
```java
final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.login);
((DefaultLoginView)login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {
    @Override
    public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
        //Handle login
    }
});

((DefaultRegisterView)login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
    @Override
    public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
        //Handle register
    }
});
```

You can also fork the project and see the example app.



Customize
--------

![](art/custom.gif)

You can change the view colors by override it in you colors.xml:

```xml
    <color name="material_login_login_color">#000000</color>
    <color name="material_login_register_color">#00ff37</color>
    <color name="material_login_login_error_color">#ffbebe</color>
    <color name="material_login_register_error_color">#600002</color>
```

You can also customize the view by setting views attributes:

For the MaterialLoginView:

|Attribute name | Default value|
|-------------- | -------------|
|registerIcon | ![](https://github.com/google/material-design-icons/blob/master/content/drawable-mdpi/ic_add_black_24dp.png)|
|registerEnabled | true|
|loginView | DefaultLoginView|
|registerView | DefaultRegisterView|

For the DefaultLoginView:

|Attribute name | Default value|
|-------------- | -------------|
|loginTitle | Login|
|loginHint | Name|
|loginPasswordHint | Password|
|loginActionText | GO|
|loginTextColor | #000000|

For the DefaultRegisterView:

|Attribute name | Default value|
|-------------- | -------------|
|registerTitle | Register|
|registerHint | Name|
|registerPasswordHint | Password|
|registerRepeatPasswordHint | Repeat Password|
|registerActionText | NEXT|
|registerTextColor | #000000|



Thanks
--------

I first saw this design by the great [Boris Borisov][1] and thought it will be nice to make it available on Android apps.



Contact Me
-----------

Pull requests are more than welcome, I'm planning to add lots of options to customize the view, and hope to do this soon.
You can also contact me by mail: smagnezi8@gmail.com



License
--------

    Copyright 2015 Shem Magnezi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[1]: http://www.materialup.com/posts/compact-login
