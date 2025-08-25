package com.example.cupid.ui.view.OnBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.cupid.R
import com.example.cupid.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class OnBoardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(navController: NavHostController) {
    val pages = listOf(
        OnBoardingPage(
            imageRes = R.drawable.image1,
            title = "Algorithm",
            description = "Users going through a vetting process to ensure you never match with bots."
        ),
        OnBoardingPage(
            imageRes = R.drawable.image2,
            title = "Connect",
            description = "Find meaningful connections with verified profiles."
        ),
        OnBoardingPage(
            imageRes = R.drawable.image3,
            title = "Enjoy",
            description = "Have fun and enjoy meeting new people securely."
        )
    )
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()


    LaunchedEffect(pagerState.currentPage) {
        delay(3000)
        val nextPage = (pagerState.currentPage + 1) % pages.size
        coroutineScope.launch {
            pagerState.animateScrollToPage(nextPage)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(20.dp))

        HorizontalPager(state = pagerState, modifier = Modifier.weight(1f)) { page ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = pages[page].imageRes),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(20.dp))
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = pages[page].title,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFE94057)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = pages[page].description,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = Color(0xFF333333),
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pages.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(if (isSelected) 10.dp else 8.dp)
                        .background(
                            if (isSelected) Color(0xFFE94057) else Color.LightGray,
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {

                navController.navigate(Screen.SignUp.route)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE94057)),
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Text("Create an account", color = Color.White, fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Already have an account? ", color = Color.Gray)
            TextButton(onClick = {  }) {
                Text("Sign In", color = Color(0xFFE94057), fontWeight = FontWeight.Bold)
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun OnBoardingPreview(){
    val navController = rememberNavController()
    OnBoardingScreen(navController)
}
