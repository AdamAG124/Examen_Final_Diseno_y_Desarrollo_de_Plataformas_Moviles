package com.panini.support.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.panini.support.ui.createticket.CreateTicketScreen
import com.panini.support.ui.login.LoginScreen
import com.panini.support.ui.ticketdetail.TicketDetailScreen
import com.panini.support.ui.ticketlist.TicketListScreen

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.LOGIN) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.TICKET_LIST) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Routes.TICKET_LIST) {
            TicketListScreen(
                onTicketClick = { id -> navController.navigate(Routes.ticketDetail(id)) },
                onCreateClick = { navController.navigate(Routes.CREATE_TICKET) }
            )
        }

        composable(
            route = Routes.TICKET_DETAIL,
            arguments = listOf(navArgument(Routes.ARG_TICKET_ID) { type = NavType.StringType })
        ) { entry ->
            val id = entry.arguments?.getString(Routes.ARG_TICKET_ID).orEmpty()
            TicketDetailScreen(ticketId = id, onBack = { navController.popBackStack() })
        }

        composable(Routes.CREATE_TICKET) {
            CreateTicketScreen(
                onCreated = { navController.popBackStack() },
                onBack = { navController.popBackStack() }
            )
        }
    }
}