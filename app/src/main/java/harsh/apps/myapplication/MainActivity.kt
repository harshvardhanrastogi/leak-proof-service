package harsh.apps.myapplication

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    var numberTextView: TextView? = null
    var isConfigChanged = false
    var state: State? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numberTextView = findViewById(R.id.number)

        if (state == null) {
            state = State()
            val intent = Intent(this, MathService::class.java)
            applicationContext.bindService(intent, state as ServiceConnection, Context.BIND_AUTO_CREATE)
        } else if (state?.lastNumber != 0) {
            setNumber(state!!.lastNumber)
        }

        state?.attach(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isConfigChanged) {
            applicationContext.unbindService(state as ServiceConnection)
        }
    }

    fun setNumber(number: Int) {
        runOnUiThread { numberTextView?.text = number.toString() }
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        isConfigChanged = true
        return state as Any
    }

    class State : NumberGenerationListener, ServiceConnection {
        var binder: MathBinder? = null
        var lastNumber: Int = 0
        private var activity: MainActivity? = null
        override fun onNumberGenerate(number: Int) {
            lastNumber = number
            activity?.setNumber(number)
        }

        fun attach(mainActivity: MainActivity?) {
            this.activity = mainActivity
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            binder = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            binder = service as MathBinder
            binder?.startGeneration(this)
        }

    }
}
