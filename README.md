# leak-proof-service
This is an example on how you can implement a memory leak proof bound service. I have followed https://github.com/commonsguy/cw-android/tree/master/Service/WeatherAPI by @commonsguy.
For memory leak tracking I have used LeakCanary's leakSentry which is really easy to integrate. The issue arises when we follow the procedure of creating a bound service given by developers coloumn.
Having a binder inner class is the real culprit which holds a reference of its outer class hence holding the service instance. 
Here we make a seperate class of binder service escaping any chance of outer class' reference hold.
