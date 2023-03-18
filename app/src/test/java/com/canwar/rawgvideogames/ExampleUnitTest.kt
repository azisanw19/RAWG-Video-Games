package com.canwar.rawgvideogames

import android.util.Log
import com.canwar.rawgvideogames.api.ApiConfig
import org.junit.Test
import retrofit2.Callback

import org.junit.Assert.*
import retrofit2.Call
import retrofit2.Response

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }
/*
    @Test
    fun testApi() {
        val client = ApiConfig.getApiService().getGames(1, 10)
        client.enqueue(object : Callback<GameResponse> {
            override fun onResponse(call: Call<GameResponse>, response: Response<GameResponse>) {
                if (response.isSuccessful) {
                    print(response.body().toString())
                } else {
                    print("onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GameResponse>, t: Throwable) {
                print("onFailure: ${t.message.toString()}")
            }

        })
    }

 */
}