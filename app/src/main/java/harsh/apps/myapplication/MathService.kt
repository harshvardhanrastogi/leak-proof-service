package harsh.apps.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import leakcanary.LeakSentry

/**
 * Created by Harsh Rastogi on 30/5/19.
 */
class MathService : Service() {

    private var binder: MathBinder? = null

    override fun onCreate() {
        super.onCreate()
        binder = MathBinder()
    }

    override fun onBind(intent: Intent): IBinder? {
        return binder
    }

    override fun onDestroy() {
        super.onDestroy()
        LeakSentry.refWatcher
    }
}
