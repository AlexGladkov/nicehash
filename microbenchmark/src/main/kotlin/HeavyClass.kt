import android.util.Log

class HeavyClass {

    companion object {
        fun heavyFunction() {
            for (i in 0..<1000) {
                Log.e("TAG", "Hello $i")
            }
        }
    }
}