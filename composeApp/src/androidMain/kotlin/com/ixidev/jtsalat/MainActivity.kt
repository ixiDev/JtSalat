package com.ixidev.jtsalat

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import org.koin.core.component.KoinComponent

class MainActivity : AppCompatActivity(), KoinComponent {
    //    private val locationTracker: LocationTracker by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        locationTracker.bind(lifecycle, this, fragmentManager = supportFragmentManager)
        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}