package ru.manzharovn.whac_a_mole

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.manzharovn.whac_a_mole.ui.game.GameScreen
import ru.manzharovn.whac_a_mole.ui.game.GameViewModel
import ru.manzharovn.whac_a_mole.ui.game.GameViewModelFactory
import ru.manzharovn.whac_a_mole.ui.resultscore.ResultScreen
import ru.manzharovn.whac_a_mole.ui.resultscore.ResultViewModel
import ru.manzharovn.whac_a_mole.ui.resultscore.ResultViewModelFactory
import ru.manzharovn.whac_a_mole.ui.startmenu.StartMenuViewModel
import ru.manzharovn.whac_a_mole.ui.startmenu.StartMenuScreen
import ru.manzharovn.whac_a_mole.ui.startmenu.StartMenuViewModelFactory
import ru.manzharovn.whac_a_mole.ui.theme.WhacAMoleTheme
import ru.manzharovn.whac_a_mole.utils.GameScreens
import javax.inject.Inject

class MainActivity : ComponentActivity() {

    @Inject
    lateinit var startMenuViewModelFactory: StartMenuViewModelFactory
    private val startMenuViewModel: StartMenuViewModel by viewModels {
        startMenuViewModelFactory
    }

    @Inject
    lateinit var gameViewModelFactory: GameViewModelFactory
    private val gameViewModel: GameViewModel by viewModels {
        gameViewModelFactory
    }

    @Inject
    lateinit var resultViewModelFactory: ResultViewModelFactory
    private val resultViewModel: ResultViewModel by viewModels {
        resultViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApp).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            WhacAMoleTheme {
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
        }
    }

    override fun onPause() {
        super.onPause()
        gameViewModel.pauseGame()
    }

    override fun onBackPressed() {}
}