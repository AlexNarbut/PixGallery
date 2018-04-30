-dontobfuscate
-renamesourcefileattribute SourceFile
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*,code/removal/simple,code/removal/advanced
-optimizationpasses 5
-allowaccessmodification
-dontpreverify
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-verbose

-assumenosideeffects class android.util.Log {
    public static int v(...);
    public static int d(...);
}

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes Exceptions

# native methods start
-keepclasseswithmembernames class * {
    native <methods>;
}

# native methods end

# keep setters in Views so that animations can still work.
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}
# keep setters end


# activity methods start
-keepclassmembers class * extends android.app.Activity {
   public void *(android.view.View);
}
# activity methods end

# enumeration classes start
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

-keepclassmembers class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keepclassmembers class **.R$* {
    public static <fields>;
}
# enumeration classes end

# appcompat start
-dontwarn android.support.**

-dontwarn sun.misc.Unsafe
-dontwarn sun.reflect.**
-dontwarn android.support.**
-dontwarn javax.annotation.**

-keep public class * extends android.app.Activity
-keep public class * extends android.support.v7.app.AppCompatActivity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
# appcompat end

# butterknife start
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }
# butterknife end

# rxjava start
-keep class rx.schedulers.Schedulers {
    public static <methods>;}
-keep class rx.schedulers.ImmediateScheduler {
    public <methods>;}
-keep class rx.schedulers.TestScheduler {
    public <methods>;}
-keep class rx.schedulers.Schedulers {
    public static ** test();}
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;}
# rxjava end

# icepick start
-dontwarn icepick.**
-keep class icepick.** { *; }
-keep class **$$Icepick { *; }
-keepclasseswithmembernames class * {
    @icepick.* <fields>;
}
-keepnames class * { @icepick.State *;}
# icepick end

# FragmentArgs start
-keep class com.hannesdorfmann.fragmentargs.** { *; }
# Fragment Args end

# Lombok start
-dontwarn lombok.**
# Lombok end

# Glide start
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# Glide end

# retrofit2 start
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
# retrofit2 end


# OkHttp start

-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }
-dontwarn okhttp3.**
-dontwarn okio.**

# OkHttp end


# For using GSON @Expose annotation

-keepattributes EnclosingMethod

# Gson specific classes
-keep class com.google.gson.stream.** { *; }


# Parcel library start
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

-keep class org.parceler.Parceler$$Parcels
# Parcel library end


# Parcel library end
