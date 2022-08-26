package ru.manzharovn.whac_a_mole.ui.startmenu

import android.text.Layout
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ru.manzharovn.whac_a_mole.R
import ru.manzharovn.whac_a_mole.ui.theme.WhacAMoleTheme
import ru.manzharovn.whac_a_mole.utils.AMOUNT_OF_TIME
import ru.manzharovn.whac_a_mole.utils.SCORE_PER_MOLE

@Composable
fun StartMenuScreen(
    startMenuViewModel: StartMenuViewModel,
    toGameScreen: () -> Unit
) {
    StartMenu(
        highScore = startMenuViewModel.highScore,
        toGameScreen = toGameScreen
    )
}

@Composable
fun StartMenu(
    highScore: Int,
    toGameScreen: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GameRules()
        Text(
            style = MaterialTheme.typography.subtitle2.copy(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(8.dp),
            text = stringResource(id = R.string.high_score, highScore)
        )
        OutlinedButton(
            shape = RoundedCornerShape(20.dp),
            onClick = toGameScreen
        ) {
            Text(
                style = MaterialTheme.typography.subtitle2,
                color = MaterialTheme.colors.secondaryVariant,
                text = stringResource(R.string.play_button_text)
            )
        }
    }
}

@Composable
fun GameRules() {
    Surface(
        elevation = 4.dp,
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            modifier = Modifier.padding(4.dp),
            style = MaterialTheme.typography.body1.copy(color = MaterialTheme.colors.primary),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.game_rules, SCORE_PER_MOLE, AMOUNT_OF_TIME)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewStartMenu(){
    WhacAMoleTheme {
        StartMenu(highScore = 10, {})
    }
}