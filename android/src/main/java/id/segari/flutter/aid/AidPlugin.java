package id.segari.flutter.aid;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.provider.Settings;

import androidx.annotation.NonNull;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** AidPlugin */
public class AidPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  private ContentResolver contentResolver;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    contentResolver = flutterPluginBinding.getApplicationContext().getContentResolver();
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "aid");
    channel.setMethodCallHandler(this);
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    if (call.method.equals("getId")) {
      try{
        result.success(getAndroidId());
      }catch (Exception e){
        result.error("ERROR_GETTING_ID", "Failed to get Android ID", e.getLocalizedMessage());
      }
    } else {
      result.notImplemented();
    }
  }

  @SuppressLint("HardwareIds")
  private String getAndroidId(){
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID);
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
