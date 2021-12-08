package com.alvaro.composeplayground.basics
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.alvaro.composeplayground.basics.ShowConstraintLayout
import kotlinx.coroutines.launch

val TAG = "ThisIsATAG"

@Composable
fun SetView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
            .border(width = 5.dp, color = Color.White)
            .padding(top = 50.dp, start = 10.dp)
            .border(width = 5.dp, color = Color.Magenta)
    ) {

        Text(
            text = "Hola Mundo",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .offset(x = 15.dp, y = 30.dp)
                .background(Color.Gray)
                .border(width = 5.dp, color = Color.Green)
                .padding(all = 15.dp)
                .clickable {
                    println("$TAG, text selected")
                }
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Other text",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .offset(x = 15.dp, y = 30.dp)
                .background(Color.Gray)
                .border(width = 5.dp, color = Color.Blue)
                .padding(all = 15.dp)
                .clickable {
                    println("$TAG, other text pressed")
                }
        )


    }
}

@Composable
fun DefaultView(imageId: Int) {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(scroll)
            .fillMaxHeight()
            .fillMaxWidth()
            .background(color = Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top //not doing anything
    ) {

        Image(
            painter = painterResource(id = imageId),
            modifier = Modifier.height(300.dp),
            contentScale = ContentScale.Crop,
            contentDescription = ""
        )

        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Happy meal.")
        }

    }
}

@Composable
fun ShowCard(imageId: Int) {
    val painter = painterResource(id = imageId)
    val title = "Title for happy meal"
    val description = "Description for happy meal"
    Box(
        modifier = Modifier
            .fillMaxWidth(0.5f)
            .padding(16.dp)
    ) {
        ImageCard(
            painter = painter,
            contentDescription = description,
            title = title
        )
    }
}

@Composable
fun ImageCard(
    painter: Painter,
    contentDescription: String,
    title: String,
    modifier: Modifier = Modifier
) {
    //container box
    Box(modifier = Modifier.height(200.dp)) {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            elevation = 5.dp
        ) {
            Image(
                painter = painter,
                contentDescription = contentDescription,
                contentScale = ContentScale.Crop
            )
            //gradient color
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black
                            ),
                            startY = 300f
                        )
                    )
            )
            //title box
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                contentAlignment = Alignment.BottomStart
            ) {
                Text(
                    text = title,
                    style = TextStyle(
                        color = Color.White, fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Composable
fun ShowTextStyled(font1: Int, font2: Int) {
    val fontFamily = FontFamily(
        Font(font1, FontWeight.Normal),
        Font(font2, FontWeight.Bold)
    )

    //"0xFF followed by hex number for color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x9E423838))
    ) {

        TextStyled(fontFamily = fontFamily)

    }
}

@Composable
fun TextStyled(fontFamily: FontFamily) {
    Text(
        text = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Green,
                    fontSize = 50.sp
                )
            ) {
                append("Some")
            }
            append("-title-")
            withStyle(
                style = SpanStyle(
                    color = Color.Green,
                    fontSize = 50.sp
                )
            ) {
                append("here")
            }
        },
        color = Color.White,
        fontSize = 30.sp,
        fontFamily = fontFamily,
        fontWeight = FontWeight.Bold,
        fontStyle = FontStyle.Normal,
        textAlign = TextAlign.Center,
        textDecoration = TextDecoration.Underline,
        modifier = Modifier
            .padding(top = 5.dp)
            .fillMaxWidth()
    )
}

@Composable
fun ShowTextFieldAndButton() {
    val scaffoldState = rememberScaffoldState()
    var textFieldState by remember {
        mutableStateOf("")
    }
    var scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            TextField(
                value = textFieldState,
                label = {
                    Text("Enter your name")
                },
                onValueChange = {
                    textFieldState = it
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                scope.launch {
                    scaffoldState.snackbarHostState.showSnackbar("Hello $textFieldState")
                }
            }) {
                Text("Say hello to me")
            }
        }
    }
}

@Composable
fun ShowList() {
    ShowLazyList()
    //ShowEagerList()
}

@Composable
fun ShowEagerList() {
    val scroll = rememberScrollState()
    Column(
        modifier = Modifier.verticalScroll(scroll)
    ) {
        for (i in 0..50) {
            Text(
                text = "This is text nº $i",
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),

                )
        }
    }
}

@Composable
fun ShowLazyList() {

    /*LazyColumn {
        items(5000) {
            Text(
                text = "This is item nº $it",
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
            )
        }
    }*/

    LazyColumn {
        itemsIndexed(
            listOf("Some", "Word", "Here", "and", "there")
        ) { index, string ->
            Text(
                text = string,
                color = Color.White,
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
            )
        }
    }

}


@Composable
fun ShowConstraintLayout() {

    val constraintSet = ConstraintSet {
        val greenbox = createRefFor("greenbox")
        val redbox = createRefFor("redbox")

        constrain(greenbox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

        constrain(redbox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }

        createHorizontalChain(greenbox, redbox, chainStyle = ChainStyle.Packed)
    }

    ConstraintLayout(
        constraintSet = constraintSet,
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .background(Color.Green)
                .layoutId("greenbox")
        )

        Box(
            modifier = Modifier
                .background(Color.Red)
                .layoutId("redbox")
        )

    }


}
