package com.mugveiga.coinsalert

import android.util.Log
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber.DebugTree
import timber.log.Timber.Forest.plant
import timber.log.Timber.Tree


@HiltAndroidApp
class CoinsAlertApplication : MultiDexApplication() {

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      plant(DebugTree())
    } else {
      plant(CrashReportingTree())
    }
  }

  /** A tree which logs important information for crash reporting.  */
  private class CrashReportingTree : Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
      if (priority == Log.VERBOSE || priority == Log.DEBUG) {
        return
      }
      // TODO connect firebase crashlytics
//      FakeCrashLibrary.log(priority, tag, message)
      if (t != null) {
        if (priority == Log.ERROR) {
//          FakeCrashLibrary.logError(t)
        } else if (priority == Log.WARN) {
//          FakeCrashLibrary.logWarning(t)
        }
      }
    }
  }
}
