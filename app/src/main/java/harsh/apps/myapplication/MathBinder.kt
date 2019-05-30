package harsh.apps.myapplication


import android.os.*

import java.util.Random

/**
 * Created by Harsh Rastogi on 30/5/19.
 */
class MathBinder : Binder() {

    private val numberGenerator: NumberGenerator
    private val numberGeneratorThread: NumberGeneratorThread

    init {
        numberGeneratorThread = NumberGeneratorThread()
        numberGeneratorThread.start()
        val looper = numberGeneratorThread.looper
        numberGenerator = NumberGenerator(looper)
    }

    fun startGeneration(numberGenerationListener: NumberGenerationListener) {
        numberGenerator.numberGenerationListener = numberGenerationListener
        numberGenerator.sendEmptyMessage(0)
    }

    class NumberGenerator internal constructor(looper: Looper) : Handler(looper) {
        var numberGenerationListener: NumberGenerationListener? = null


        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            numberGenerationListener?.onNumberGenerate(generateRandomNumber())

            sendEmptyMessageDelayed(0, 5000)

        }
    }

    class NumberGeneratorThread internal constructor() : HandlerThread("Number Generator Thread")

    companion object {

        private fun generateRandomNumber(): Int {
            val r = Random()
            return r.nextInt()
        }
    }
}
