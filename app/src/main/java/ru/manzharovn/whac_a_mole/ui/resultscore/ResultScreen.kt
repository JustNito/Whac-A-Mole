package ru.manzharovn.whac_a_mole.ui.resultscore

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import ru.manzharovn.whac_a_mole.R

@Composable
fun ResultScreen(
    score: Int,
    resultViewModel: ResultViewModel,
    toMainMenu: () -> Unit,
    playAgain: () -> Unit
) {
    Result(
        score = score,
        highScore = resultViewModel.highScore,
        toMainMenu = toMainMenu,
        playAgain = playAgain
    )
}

@Composable
fun Result(
    score: Int,
    highScore: Int,
    toMainMenu: () -> Unit,
    playAgain: () -> Unit
) {
    val paddingModifier = Modifier.padding(8.dp)
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.primaryVariant,
                fontWeight = FontWeight.Bold
            ),
            modifier = paddingModifier,
            text = stringResource(id = R.string.congratulations)
        )
        Text(
            style = MaterialTheme.typography.subtitle2.copy(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            ),
            modifier = paddingModifier,
            text = stringResource(id = R.string.score, score)
        )
        Text(
            style = MaterialTheme.typography.subtitle2.copy(
                color = MaterialTheme.colors.primary,
                fontWeight = FontWeight.Bold
            ),
            modifier = paddingModifier,
            text = stringResource(id = R.string.high_score, highScore)
        )
        OutlinedButton(
            shape = RoundedCornerShape(20.dp),
            modifier = paddingModifier,
            onClick = playAgain
        ) {
            Text(text = stringResource(id = R.string.play_again_button))
        }
        OutlinedButton(
            shape = RoundedCornerShape(20.dp),
            modifier = paddingModifier,
            onClick = toMainMenu
        ) {
            Text(text = stringResource(id = R.string.main_menu_button))
        }
    }
}