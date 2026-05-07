import 'dart:async';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

class Aid {
  static const MethodChannel _channel = MethodChannel('aid');

  static Future<String?> getId() async {
    if (defaultTargetPlatform != TargetPlatform.android) return null;
    final String? version = await _channel.invokeMethod<String>('getId');
    return version;
  }
}
