package ru.manzharovn.whac_a_mole.ui.game

import android.media.MediaPlayer
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PauseCircleOutline
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.manzharovn.whac_a_mole.R
import ru.manzharovn.whac_a_mole.model.Hole
import ru.manzharovn.whac_a_mole.model.MoleStatus
import ru.manzharovn.whac_a_mole.ui.theme.Green500
import ru.manzharovn.whac_a_mole.ui.theme.WhacAMoleTheme
import ru.manzharovn.whac_a_mole.utils.GameStatus
import ru.manzharovn.whac_a_mole.utils.intToTime

@Composable
fun GameScreen(
    gameViewModel: GameViewModel,
    toResultScreen: () -> Unit
) {
    Game(
        gameViewModel.score,
        gameViewModel.gameTimer,
        gameViewModel.currentFrame,
        gameViewModel.holes,
        gameViewModel::tapOnMole,
        gameViewModel::pauseGame,
    )
    if(gameViewModel.gameStatus == GameStatus.PAUSE) {
        Pause(gameViewModel::startGame)
    }
    if(gameViewModel.gameStatus == GameStatus.START_COUNTDOWN) {
        CountDown(countDown = gameViewModel.countDown)
    }
    if (gameViewModel.gameStatus == GameStatus.END_GAME) {
        LaunchedEffect(key1 = gameViewModel.gameStatus) {
            toResultScreen()
        }
    }
}

@Composable
fun Game(
    score: Int,
    time: Int,
    currentFrame: Int,
    holes: List<Hole>,
    tapOnMole: (Hole) -> Unit,
    onPause: () -> Unit,
) {
    Column{
        Header(
            score = score,
            time = time,
            onPause = onPause
        )
        Holes(
            holes = holes,
            currentFrame = currentFrame,
            tapOnMole = tapOnMole
        )
    }



}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Holes(
    holes: List<Hole>,
    currentFrame: Int,
    tapOnMole: (Hole) -> Unit
) {
    Surface(
        modifier = Modifier.padding(8.dp).padding(top = 40.dp),
        shape = RoundedCornerShape(10.dp),
        color = Green500,
        elevation = 8.dp
    ) {
        LazyVerticalGrid(
            modifier = Modifier.padding(vertical = 32.dp),
            cells = GridCells.Fixed(3)
        ) {
            items(items = holes) { hole ->
                HoleOrMole(
                    modifier = Modifier.size(90.dp),
                    hole = hole,
                    currentFrame = currentFrame,
                    tapOnMole = tapOnMole
                )
            }
        }
    }
}

@Composable
fun Header(
    score: Int,
    time: Int,
    onPause: () -> Unit
) {
    Row(Modifier.padding(8.dp)) {
        Text(
            modifier = Modifier.weight(1f),
            color = MaterialTheme.colors.primaryVariant,
            text = stringResource(id = R.string.score, score),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
        IconButton(
            modifier = Modifier.weight(1f),
            onClick = onPause
        ) {
            Icon(
                tint = MaterialTheme.colors.primaryVariant,
                modifier = Modifier.size(50.dp),
                imageVector = Icons.Outlined.PauseCircleOutline,
                contentDescription = "pause button"
            )
        }
        Text(
            textAlign = TextAlign.Right,
            color = MaterialTheme.colors.primaryVariant,
            modifier = Modifier.weight(1f),
            text = stringResource(id = R.string.time, intToTime(time)),
            style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun HoleOrMole(
    modifier: Modifier = Modifier,
    hole: Hole,
    currentFrame: Int,
    tapOnMole: (Hole) -> Unit
) {
    val context = LocalContext.current
    Image(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() }
            ) {
                if(hole.moleStatus == MoleStatus.Show) {
                    tapOnMole(hole)
                    val mediaPlayer = MediaPlayer.create(context, R.raw.punch)
                    mediaPlayer.start()
                }
            },
        painter = painterResource(
            id =  when(hole.moleStatus) {
                MoleStatus.None -> R.drawable.ic_hole
                MoleStatus.Whacked -> R.drawable.ic_whacked_mole
                MoleStatus.Show -> R.drawable.ic_hole_with_mole
                MoleStatus.Animation -> currentFrame
            }
        ),
        contentDescription = "hole"
    )
}

@Composable
fun CountDown(countDown: Int) {

    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                style = MaterialTheme.typography.h2.copy(
                    shadow = Shadow(
                        color = MaterialTheme.colors.secondaryVariant,
                        offset = Offset(5.0f, 10.0f),
                        blurRadius = 3f
                    )
                ),
                color = MaterialTheme.colors.secondary,
                text = if(countDown == 0) stringResource(R.string.after_pause_text) else countDown.toString()
            )
        }
    }
}

@Composable
fun Pause(
    onResume: () -> Unit
) {
    Surface(
        color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                style = MaterialTheme.typography.h2.copy(
                    shadow = Shadow(
                    color = MaterialTheme.colors.secondaryVariant,
                    offset = Offset(5.0f, 10.0f),
                    blurRadius = 3f
                )
                ),
                color = MaterialTheme.colors.secondary,
                text = stringResource(R.string.pause)
            )
            OutlinedButton(
                modifier = Modifier.padding(vertical = 16.dp),
                onClick = onResume,
            ) {
                Text(
                    color = MaterialTheme.colors.secondaryVariant,
                    text = stringResource(id = R.string.resume)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHeader(){
    WhacAMoleTheme() {
        Header(score = 0, time = 0, {})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPause() {
    WhacAMoleTheme() {
        Pause({})
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHoles() {
    WhacAMoleTheme() {

    }
}