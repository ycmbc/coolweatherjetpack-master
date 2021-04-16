package com.coolweather.coolweatherjetpack

import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.util.Log
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.junit.Test


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
//    @Test
//    fun addition_isCorrect() {
//        assertEquals(4, 2 + 2)
//    }

    //协程的创建方式

    @Test
    fun main() {

        //方式一.GlobalScope.launch创建的是一个顶层协程，相当于创建了一个子线程
//        GlobalScope.launch {
//            println("ThreadName is ${Thread.currentThread().name}")
//        }
//        Thread.sleep(100)
//        println("ThreadName is ${Thread.currentThread().name}")

        //方式二.runBlocking创建的协程是会阻塞其所在的线程的,通过runBlocking开启协程域并没没有创建线程，依然在主线程中
//        runBlocking {
//            println("ThreadName is ${Thread.currentThread().name}")
//            println("create coroutine start")
//            //delay,它的作用就是使其所在协程暂时挂起，延迟指定时间后再执行协程域内下面的代码，但是它并不会阻塞线程的执行，
//            //所以也不会影响其他协程的运行。注意：它只能在协程域中或挂起函数中调用。
//            delay(1000)
//            println("create coroutine end")
//        }
//
//        println("ThreadName is ${Thread.currentThread().name}")


        //方式三.launch函数只能在协程域下调用，它是用于创建子协程的。子协程的特点是：如果外层作用域的协程结束了，该作用域下的子协程也会全部结束
        //两个子协程的日志是交替打印的，说明它们确实像多线程那样并发运行的,然而这两个子协程却是在同一个线程中的,所以协程实现的并发效率高于线程
//        runBlocking {
//            //这当中的launch均在主线程
//            for (i in 0..3) {
//                launch {
//                    println("launch1")
//                    printDot()
//                    println("launch1 end")
//                }
//            }
//        }

        //方式四.coroutineScope也是一个挂起函数，它的特点是：继承外部的协程作用域并创建一个子作用域(并没有创建子协程，
        // 不同于launch)，借助这个特性我们就可以给任意挂起函数提供协程作用域了
//        suspend fun printDot(i: Int) = coroutineScope {
//            launch {
//                println("$i")
//                delay(300)
//            }
//        }
        //另外coroutineScope 函数可以保证其作用域内的所有代码和子协程在全部执行完之前，会阻塞当前协程
//        runBlocking {
//            val job = Job()
//            val scope = CoroutineScope(job)
//            scope.launch {
//                println("result is ")
//                val result = withContext(Dispatchers.Default) {
//                    5 + 10
//                }
//                println("result is $result")
//            }
//            job.start()
//        }

        //方式五.CoroutineScope.launch  创建一个Job对象，并调用CoroutineScope(job)函数，
        // 该函数返回一个CoroutineScope对象，有了这个对象我们就能使用CoroutineScope.launch{}创建协程了，
        // 使用这个对象创建的所有协程都是关联到刚才我们创建的job对象的作用域下，这样我们就能使用job.cancel()取消所有协程

//        test()

        val hashMap = hashMapOf<String, String>()
        hashMap["name"] = "yc"
        hashMap["age"] = "12"
        hashMap.edit()

        val a: String? = null
        println(a?.length) //若为空抛出空指针异常

        println(myObject.constNameObject)
        println(MyClass.constNameCompanionObject)

        doSomething(1,2,3)
    }

    private fun MutableMap<String, String>.edit() {
        println(get("age"))
    }

    fun isOdd(x: Int) = x % 2 != 0

    fun test() {
        var list = listOf(1, 2, 3, 4, 5)
//        println(list.filter { isOdd(it) })
        println(list.filter(this::isOdd))
    }

    //Kotlin中提供了关键suspend来修饰定义挂起函数,方便将其中协程作用域的代码抽取出一个方法, 虽然suspend能将一个函数声明成挂起函数，
    //但是无法提供协程作用域，所以我们无法在挂起函数中调用launch函数创建子协程
    suspend fun printDot() {
        println(".")
        delay(1000)
    }

    //常量1
    object myObject {
        const val constNameObject: String = "constNameObject"
    }

    //常量2
    class MyClass {
        companion object Factory{
            const val constNameCompanionObject: String = "constNameCompanionObject"
        }
    }

    fun doSomething(vararg inputs: Int) {
        inputs.iterator().forEach {
            println(it)
        }
        for (input in inputs) {
            println(input)
        }
    }
}
