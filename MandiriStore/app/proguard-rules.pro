# Keep Parcelable model classes
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# Keep Activity, Fragment, and ViewModel classes
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Fragment
-keep public class * extends androidx.fragment.app.Fragment
-keep public class * extends androidx.lifecycle.ViewModel

# Retain classes with specific annotations (useful for Retrofit, Gson, etc.)
-keepattributes *Annotation*
-keep class com.example.app.network.** { *; }

# Ignore warnings for certain libraries
-dontwarn okhttp3.**
-dontwarn retrofit2.**
-dontwarn javax.annotation.**
