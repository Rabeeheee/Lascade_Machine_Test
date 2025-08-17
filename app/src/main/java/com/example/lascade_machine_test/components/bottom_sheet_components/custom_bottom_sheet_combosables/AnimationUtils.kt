//package com.example.lascade_machine_test.components.bottom_sheet_components
//
//import androidx.compose.animation.core.Animatable
//import androidx.compose.animation.core.Spring
//import androidx.compose.animation.core.spring
//import androidx.compose.animation.core.tween
//import androidx.compose.foundation.lazy.LazyListState
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.launch
//
//object AnimationUtils {
//    const val ANIMATION_DURATION = 100
//    const val SCALE_DOWN = 0.95f
//    const val ALPHA_DIM = 0.3f
//    const val OFFSET_DISTANCE = 50f
//
//    suspend fun performPageTransition(
//        animationOffset: Animatable<Float, *>,
//        animationAlpha: Animatable<Float, *>,
//        animationScale: Animatable<Float, *>,
//        newPage: Int,
//        currentPage: Int,
//        animationScope: CoroutineScope,
//        lazyListState: LazyListState,
//        onPageChanged: (Int) -> Unit
//    ) {
//        val direction = if (newPage > currentPage) 1f else -1f
//
//        animationScope.launch {
//            animationAlpha.animateTo(
//                targetValue = ALPHA_DIM,
//                animationSpec = tween(durationMillis = ANIMATION_DURATION / 3)
//            )
//        }
//
//        animationScope.launch {
//            animationScale.animateTo(
//                targetValue = SCALE_DOWN,
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessMedium
//                )
//            )
//        }
//
//        animationOffset.animateTo(
//            targetValue = direction * OFFSET_DISTANCE,
//            animationSpec = tween(durationMillis = ANIMATION_DURATION / 3)
//        )
//
//        lazyListState.scrollToItem(newPage)
//        onPageChanged(newPage)
//
//        animationOffset.snapTo(-direction * OFFSET_DISTANCE)
//
//
//        animationScope.launch {
//            animationOffset.animateTo(
//                targetValue = 0f,
//                animationSpec = spring(
//                    dampingRatio = Spring.DampingRatioMediumBouncy,
//                    stiffness = Spring.StiffnessMedium
//                )
//            )
//        }
//
//        animationScope.launch {
//            animationAlpha.animateTo(
//                targetValue = 1f,
//                animationSpec = tween(durationMillis = ANIMATION_DURATION / 2)
//            )
//        }
//
//        animationScale.animateTo(
//            targetValue = 1f,
//            animationSpec = spring(
//                dampingRatio = Spring.DampingRatioMediumBouncy,
//                stiffness = Spring.StiffnessMedium
//            )
//        )
//
//        delay(100)
//    }
//}
//
//// Extension functions for easier use
//suspend fun Animatable<Float, *>.smoothAnimateTo(
//    targetValue: Float,
//    dampingRatio: Float = Spring.DampingRatioMediumBouncy,
//    stiffness: Float = Spring.StiffnessMedium
//) {
//    animateTo(
//        targetValue = targetValue,
//        animationSpec = spring(
//            dampingRatio = dampingRatio,
//            stiffness = stiffness
//        )
//    )
//}
//
//suspend fun Animatable<Float, *>.tweenAnimateTo(
//    targetValue: Float,
//    duration: Int = AnimationUtils.ANIMATION_DURATION
//) {
//    animateTo(
//        targetValue = targetValue,
//        animationSpec = tween(durationMillis = duration)
//    )
//}