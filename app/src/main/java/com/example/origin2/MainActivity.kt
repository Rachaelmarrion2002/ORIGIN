package com.example.origin2

import LoaderIntro
import OnBoardingData
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.lifecycleScope
import com.example.origin2.ui.theme.ORIGIN2Theme
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        @SuppressLint("CustomSplashScreen")
        class SplashActivity: ComponentActivity() {

            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                lifecycleScope.launchWhenCreated {
                    delay(3000)

                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

        }
        super.onCreate(savedInstanceState)
        setContent {
            ORIGIN2Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainFunction()
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalPagerApi::class)
@Composable
fun  MainFunction() {
    val items = ArrayList<OnBoardingData>()

//    items.add(
//        OnBoardingData(
//            R.raw.coffee,
//            title = "Get Started ",
//            desc = "Find the best coffee for you",
//
//        )
//    )
    val pagerState = com.google.accompanist.pager.rememberPagerState(
        initialPage = 0,
        infiniteLoop = false,
        pageCount = items.size

    )
    OnBoardingPager(
        item = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
    )

}
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPager(
    item: List<OnBoardingData>,
    pagerState: PagerState,
    modifier: Modifier
){
    Box(modifier = Modifier){
        Column (
            horizontalAlignment = Alignment.CenterHorizontally
        ){
           HorizontalPager(
               state = pagerState
           ) {page ->
              Column (
                  modifier = Modifier
                      .padding(60.dp)
                      .fillMaxWidth(),
                  horizontalAlignment = Alignment.CenterHorizontally
              ){
                  Image(
                      painter = painterResource(id = R.drawable.img),
                      contentDescription =item[page].title ,
                      modifier = Modifier
                          .fillMaxSize()
                  )
                  LoaderIntro(
                      modifier = Modifier
                          .size(200.dp)
                          .fillMaxWidth()
                          .align(alignment = Alignment.CenterHorizontally), item[page].image,
                  )
                  Text(
                      text = item[page].title,
                      modifier = Modifier
                          .padding(top = 50 .dp),
                      color = Color.Black,
                      textAlign = TextAlign.Start
                  )
                  Text(
                      text = item[page].desc,
                      modifier = Modifier
                          .padding(top = 30.dp, start = 10 .dp, end = 20 .dp),
                      color = Color.Black,
                      textAlign = TextAlign.Start
                  )
              }

           }
           PagerIndicator(item.size,pagerState.currentPage)
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
        ){
            BottomSection(pagerState.currentPage)
        }
    }
}
@Composable
fun PagerIndicator(
    size:Int,
    currentPage:Int
){
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 20 .dp)
    ){
        repeat(size){
            Indicator(isSelected = it == currentPage)
        }
    }
}
@Composable
fun Indicator(isSelected:Boolean){
    val width = animateDpAsState(targetValue = if (isSelected) 25 .dp else 10 .dp)
    Box(
        modifier = Modifier
            .padding(1.dp)
            .height(10.dp)
            .width(width.value)
            .clip(CircleShape)
            .background(
                if (isSelected) Red else White
            )
    )
}
@Composable
fun BottomSection(currentPage: Int){
   Row (
       modifier = Modifier
           .padding(bottom = 20.dp)
           .fillMaxWidth(),
       horizontalArrangement = if (currentPage != 2) Arrangement.SpaceBetween else Arrangement.End
   ){
      if (currentPage == 2){
          OutlinedButton(
              onClick = {},
              shape = RoundedCornerShape(50),
          )
          {
             Text(
                 text = "Get Started",
                 modifier = Modifier
                     .padding(vertical = 8 .dp, horizontal = 40 .dp),
                 color = Black
             )
          }
      }else{
          SkipNextButton(text = "Skip",Modifier.padding(start = 20 .dp))
          SkipNextButton(text = "Next",Modifier.padding(start = 20 .dp))
      }
   }
}
@Composable
fun SkipNextButton(text: String, modifier: Modifier){
    Text(
        text = text,
        color = Color.Black,
        modifier = modifier,
        fontSize = 18.sp,
        style = MaterialTheme.typography.bodyLarge,
        fontWeight = FontWeight.Medium
    )
}

@Preview(showBackground = true)
@Composable
fun MainFunctionPreview() {
    ORIGIN2Theme {
        MainFunction()
    }
}