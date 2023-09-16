package kg.hackathon.template.activity

import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kg.hackathon.template.R
import kg.hackathon.template.base.BaseActivity
import kg.hackathon.template.databinding.ActivityAppBinding

@AndroidEntryPoint
class AppActivity : BaseActivity<ActivityAppBinding>(ActivityAppBinding::inflate) {

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        initMenu()
    }

    private fun initMenu() {
        vb.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.followed -> {
                    findNavController(R.id.nav_host_app).navigate(R.id.mainFragment)
                    true
                }
                R.id.search -> {
                    findNavController(R.id.nav_host_app).navigate(R.id.searchFragment)
                    true
                }
                R.id.cart -> {
                    findNavController(R.id.nav_host_app).navigate(R.id.mainFragment)
                    true
                }
                R.id.fav -> {
                    findNavController(R.id.nav_host_app).navigate(R.id.mainFragment)
                    true
                }
                R.id.account -> {
                    findNavController(R.id.nav_host_app).navigate(R.id.accountFragment)
                    true
                }
                else -> { true }
            }
        }
    }

    fun bottomMenuVisibility(visibility: Boolean) {
        vb.bottomNav.isVisible = visibility
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_app).navigateUp() || super.onSupportNavigateUp()
    }
}