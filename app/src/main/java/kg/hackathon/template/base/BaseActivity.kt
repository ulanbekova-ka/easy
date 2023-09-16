package kg.hackathon.template.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import kg.hackathon.template.utils.LocaleHelper
import java.util.*

abstract class BaseActivity<VB : ViewBinding>(
    val bindFactory: (LayoutInflater) -> VB
) : AppCompatActivity() {

    val vb by lazy { bindFactory(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(vb.root)
        setupViews()
    }

    open fun setupViews() {}

    override fun attachBaseContext(newBase: Context) {
        val context = LocaleHelper.onAttach(newBase)
        super.attachBaseContext(context)
    }

    fun setAppLocale(language: String) {
        val applicationConf = applicationContext.resources.configuration.apply {
            setLocale(Locale(language))
        }
        createConfigurationContext(applicationConf)
        LocaleHelper.setLocale(this, language)
        recreate()
    }
}