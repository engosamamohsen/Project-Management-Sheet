package com.example.projectmanagement.widgets

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color // Correct import for Color in Compose
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavHostController
import com.example.projectmanagement.R
import com.example.projectmanagement.app.MyApplication
import com.example.projectmanagement.constants.AppColor
import com.example.projectmanagement.constants.AppColor.backgroundPrimaryColor
import com.example.projectmanagement.constants.ISize
import com.example.projectmanagement.constants.HomeScreen
import com.example.projectmanagement.model.Slider
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SliderView(modifier: Modifier = Modifier, navController: NavHostController) {
    val sliders = listOf(
        Slider(
            image = R.drawable.intro_1,
            header = MyApplication.appContext.getString(R.string.simplify_project_management),
            desc = MyApplication.appContext.getString(R.string.add_new_projects_easily)
        ),
        Slider(
            image = R.drawable.intro_2,
            header = MyApplication.appContext.getString(R.string.get_detailed_reports_at_your_fingertips),
            desc = MyApplication.appContext.getString(R.string.access_comprehensive_reports_for_managers)
        ),
        Slider(
            image = R.drawable.intro_3,
            header = MyApplication.appContext.getString(R.string.evaluate_project_success_through_insights),
            desc = MyApplication.appContext.getString(R.string.analyze_project_results_and_criteria)
        )
    )

    var pagePosition by remember {
        mutableStateOf(0)
    }
    var navigate by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(navigate) {
        if(navigate){
            navigate = false
            navController.navigate(HomeScreen)
        }
    }

    val scope = rememberCoroutineScope()


    val pagerState = rememberPagerState(pageCount = {
        sliders.size
    })
    LaunchedEffect(pagerState.currentPage) {
        pagePosition = pagerState.currentPage
    }
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            IAppBarIntro(navController = navController, showBackImage = false, textEnd = stringResource(R.string.escape), textEndClick = {
                navController.navigate(HomeScreen)
            });
        }
    ) { paddingValues ->

        Box {
            Column(modifier = Modifier.align(Alignment.Center)) {
                Column(modifier = Modifier
                    .fillMaxSize()
                    .background(brush = backgroundPrimaryColor)
                    .padding(paddingValues)
                    .padding(10.dp).weight(1f)
//                .fillMaxSize()
                    ) {
                    HorizontalPager(
                        state = pagerState,
                        modifier = modifier
                            .fillMaxWidth()
                            .padding(20.dp)
                            .height(200.dp) // Adjust the height as needed
                    ) { page ->
//            pagePosition = page
                        Image(
                            modifier = Modifier.fillMaxWidth(),
                            painter = painterResource(sliders[page].image),
                            contentDescription = "intro",
                        )
                        Spacer(modifier = Modifier.height(80.dp))
                    }

                    AppTextBold(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = ISize.paddingPage.dp),
                        text = sliders[pagePosition].header,
                        fontSize = 24,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    AppTextMedium(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = ISize.paddingPage.dp),
                        text = sliders[pagePosition].desc,
                        fontSize = 16,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        for (pageDot in sliders.indices) {
                            IndicatorDot(
                                modifier = Modifier.padding(horizontal = 7.dp),
                                color = if (pagePosition == pageDot) AppColor.primaryColor else AppColor.primaryColorOpacity
                            );
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))

                }
                AppButtonPrimary(
                    modifier = Modifier.padding(start = 45.dp, end = 45.dp, bottom = 87.dp),
                    text = if(pagePosition < sliders.size - 1 ) stringResource(R.string.next) else stringResource(R.string.start_now)
                ) {
                    Log.d("SliderView", "SliderView: ${pagePosition}")
                    scope.launch {
                        if (pagePosition < sliders.size - 1) {
                            pagerState.animateScrollToPage(pagePosition + 1) // Move to the next page
                        } else {
                            navigate = true
                        }
                    }
                }
            }

        }

    }

}

@Composable
fun IndicatorDot(modifier: Modifier = Modifier, size: Int = 10, color: Color) {
    Box(
        modifier = modifier
            .size(size.dp)
            .clip(CircleShape)
            .background(color)
    )
}