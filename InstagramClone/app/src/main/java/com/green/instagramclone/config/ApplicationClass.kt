package com.green.instagramclone.config

import android.app.Application
import android.content.SharedPreferences
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import com.green.instagramclone.src.main.MainActivity
import com.green.instagramclone.src.main.home.HomeFragment
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {
    companion object {
        // const val API_URL = "http://product.steeaady.site/" // product server
        const val API_URL = "https://test.steeaady.site/" // test server

        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념
        lateinit var sSharedPreferences: SharedPreferences

        // JWT Token Header 키 값
        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"

        val USER_NICKNAME_IDX = "userNickNameIdx"
        val USER_PROFILE_PICTURE = "userProfilePicture"

        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용
        lateinit var sRetrofit: Retrofit


        lateinit var mainActivity: MainActivity
        lateinit var homeFragment: HomeFragment
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = applicationContext.getSharedPreferences("INSTAGRAM_CLONE_APP", MODE_PRIVATE)

        // retrofit 인스턴스 생성
        initRetrofitInstance()

    }

    // 레트로핏 인스턴스를 생성하고, 레트로핏에 각종 설정값들을 지정해줍니다.
    // 연결 타임아웃시간은 5초로 지정이 되어있고, HttpLoggingInterceptor를 붙여서 어떤 요청이 나가고 들어오는지를 보여줍니다.
    private fun initRetrofitInstance() {
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(5000, TimeUnit.MILLISECONDS)
            .connectTimeout(5000, TimeUnit.MILLISECONDS)
            // 로그캣에 okhttp.OkHttpClient로 검색하면 http 통신 내용을 보여줍니다.
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .build()

        // sRetrofit 이라는 전역변수에 API url, 인터셉터, Gson을 넣어주고 빌드해주는 코드
        // 이 전역변수로 http 요청을 서버로 보내면 됩니다.
        sRetrofit = Retrofit.Builder()
            .baseUrl(API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}