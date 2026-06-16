package com.example.beautyclub

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.lifecycleScope
import com.example.beautyclub.data.AppContainer
import com.example.beautyclub.data.DummyData
import com.example.beautyclub.navigation.NavGraph
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val appContainer = AppContainer(this)

        lifecycleScope.launch {
//            appContainer.memberRepository.addMember(
//                DummyData.member
//            )
        }

        setContent {
            NavGraph()
        }
    }
}