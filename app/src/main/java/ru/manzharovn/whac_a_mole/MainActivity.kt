package ru.manzharovn.whac_a_mole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.manzharovn.whac_a_mole.model.Hole
import ru.manzharovn.whac_a_mole.model.MoleStatus
import ru.manzharovn.whac_a_mole.ui.game.GameScreen
import ru.manzharovn.whac_a_mole.ui.game.GameViewModel
import ru.manzharovn.whac_a_mole.ui.game.HoleOrMole
import ru.manzharovn.whac_a_mole.ui.resultscore.ResultScreen
import ru.manzharovn.whac_a_mole.ui.resultscore.ResultViewModel
import ru.manzharovn.whac_a_mole.ui.startmenu.StartMenuViewModel
import ru.manzharovn.whac_a_mole.ui.startmenu.StartMenuScreen
import ru.manzharovn.whac_a_mole.ui.theme.WhacAMoleTheme
import ru.manzharovn.whac_a_mole.utils.GameScreens
import ru.manzharovn.whac_a_mole.utils.ViewModelFactory
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val startMenuViewModel: StartMenuViewModel by viewModels {
        viewModelFactory
    }
    private val gameViewModel: GameViewModel by viewModels {
        viewModelFactory
    }
    private val resultViewModel: ResultViewModel by viewModels {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            WhacAMoleTheme {
                WhacAMole(
                    startMenuViewModel = startMenuViewModel,
                    gameViewModel = gameViewModel,
                    resultViewModel = resultViewModel
                )
            }
        }
    }


    override fun onPause() {
        super.onPause()
        gameViewModel.pauseGame()
    }

    override fun onBackPressed() {}
}

@Composable
fun TestMole() {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

    }
}

@Composable
fun WhacAMole(
    startMenuViewModel: StartMenuViewModel,
    gameViewModel: GameViewModel,
    resultViewModel: ResultViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = GameScreens.StartMenu.name
    ) {
        composable(GameScreens.StartMenu.name) {
            StartMenuScreen(startMenuViewModel = startMenuViewModel) {
                navController.navigate(GameScreens.Game.name)
                gameViewModel.startGame()
            }
        }
        composable(GameScreens.Game.name) {
            GameScreen(gameViewModel = gameViewModel) {
                resultViewModel.updateHighScore()
                navController.navigate(GameScreens.ResultScore.name)
            }
        }
        composable(GameScreens.ResultScore.name) {
            ResultScreen(
                score = gameViewModel.score,
                resultViewModel = resultViewModel,
                toMainMenu = {
                    startMenuViewModel.updateHighScore()
                    navController.navigate(GameScreens.StartMenu.name)
                },
                playAgain = {
                    gameViewModel.startGame()
                    navController.navigate(GameScreens.Game.name)
                }
            )
        }
    }
}