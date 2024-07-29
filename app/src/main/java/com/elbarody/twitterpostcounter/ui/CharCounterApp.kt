package com.elbarody.twitterpostcounter.ui

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.elbarody.base.theme.TwitterPostCounterTheme
import com.elbarody.post_counter.ui.TwitterCounterScreen
import com.elbarody.twitterpostcounter.ui.MainDestinations.TWITTER_COUNTER

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun CharCounterApp(
    appNavController: NavHostController
) {
    SharedTransitionLayout {
        TwitterPostCounterTheme {
            NavHost(
                modifier = Modifier.fillMaxSize(),
                navController = appNavController,
                startDestination = TWITTER_COUNTER,
            ) {

                composable(route = TWITTER_COUNTER) {
                    TwitterCounterScreen()
                }
            }
        }
    }
}