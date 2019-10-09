package com.develop.grizzzly.pediatry.activities

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.develop.grizzzly.pediatry.R
import com.develop.grizzzly.pediatry.db.DatabaseAccess
import com.develop.grizzzly.pediatry.network.WebAccess
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import kotlinx.android.synthetic.main.activity_start.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TAG = "START ACTIVITY"

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
        AppCenter.start(
            application, "b9feccee-76bf-402e-a033-d1c45613c559",
            Analytics::class.java, Crashes::class.java
        )

        //TODO: надо предусмотреть отсутсвие подключения к сети в каждом запросе
//        if (!verifyAvailableNetwork(this)) {
//            showToast(
//                this,
//                R.layout.custom_toast,
//                "Нет подключения к интернету. Возобновите подключение и перезапустите приложение."
//            )
//            return
//        }

        GlobalScope.launch {

            val user = DatabaseAccess.database.userDao().findUser(0)
            Log.d(TAG, user.toString())
            try {

                val ads = WebAccess.adApi.getAds()
                if (ads.isSuccessful) {
                    val list = ads.body()?.ads ?: listOf()
                    Log.d("TAG", "AND THE LIST IS $list")
                    DatabaseAccess.database.adDao().saveAds(list)
                } else {
                    Log.d("TAG", "LOST LULW")
                }

                val response = WebAccess.pediatryApi.login(user?.email, user?.password)
                delay(1500)
                try {
                    if (response.isSuccessful) {
                        if (response.body()?.status != 200L) {
                            val navController = nav_host_fragment.findNavController()
                            navController.navigate(R.id.action_start_to_login)
                        } else {
                            Log.d(TAG, response.toString())
                            Log.d(TAG, response.body().toString())
                            WebAccess.id = response.body()?.response?.id ?: 0
                            WebAccess.token = response.body()?.response?.token ?: ""
                            val intent = Intent(baseContext, MainActivity::class.java)
                            WebAccess.offlineLog = false
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }

                    } else {
                        val navController = nav_host_fragment.findNavController()
                        navController.navigate(R.id.action_start_to_login)
                    }
                } catch (e: Exception) {
                    if (user != null) {
                        val intent = Intent(baseContext, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    } else {
                        val navController = nav_host_fragment.findNavController()
                        navController.navigate(R.id.action_start_to_login)
                    }

                }
            } catch (e: Exception) {
                if (user != null) {
                    val intent = Intent(baseContext, MainActivity::class.java)
                    intent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    val navController = nav_host_fragment.findNavController()
                    navController.navigate(R.id.action_start_to_login)
                }
            }


        }
    }

    override fun onStart() {
        supportActionBar?.hide()
        super.onStart()
    }

    private fun verifyAvailableNetwork(activity: AppCompatActivity): Boolean {
        val connectivityManager =
            activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

}
