# EsCalendarView
highly Customizable Horizontal future Calendar view starts from today for selections in the future.

![sample.gif](git-assets/sample.gif)

## Including in your project
[![Download](https://api.bintray.com/packages/devmagician/maven/transformationlayout/images/download.svg) ](https://bintray.com/devmagician/maven/transformationlayout/_latestVersion)
[![JitPack](https://jitpack.io/v/skydoves/TransformationLayout.svg)](https://jitpack.io/#skydoves/TransformationLayout)
### Gradle 
Add below codes to your **root** `build.gradle` file (not your module build.gradle file).
```gradle
allprojects {
    repositories {
    	....
	maven { url 'https://jitpack.io' }
    }
}
```
And add a dependency code to your **APP**'s `build.gradle` file.
```gradle
dependencies {
	  implementation 'com.github.EsmaeelNabil:EsCalendarView:1.0'
}
```

## Usage

Add following XML inside your XML layout file.


### EsCalendarView XML
Here is a basic example of implementing `TransformationLayout`. <br>
We must wrap one or more views that we want to transform.

```gradle
<com.esmaeel.calendarlibrary.EsCalendarView
        app:previousMonthTextSize="@dimen/_5ssp"
        app:previousMonthTextColor="@color/md_grey_900"
        app:currentMonthTextColor="@color/white"
        app:currentMonthTextSize="@dimen/_5ssp"
        android:paddingTop="8dp"
        android:id="@+id/myCalendar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:dayNameTextSize="@dimen/_2ssp"
        app:dayNumberTextSize="@dimen/_5ssp"
        app:includeToday="true"
        app:itemSize="@dimen/_40sdp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:normalDayBackgroundColor="@color/creemy_red"
        app:selectedDayNameTextColor="@color/header_black"
        app:selectedDayNumberTextColor="@color/black_light"
        app:selectedDayBackgroundColor="@color/white" />

```

# Kotlin implementaion

```gradle
class MainActivity : AppCompatActivity(), EsCalendarListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myCalendar.setOnDateSelectedListener(this)

    }

    override fun onDateSelectedListener(model: DateModel?, position: Int) {
        Toast.makeText(applicationContext, model?.apiDate, Toast.LENGTH_LONG).show();
    }
}
```
