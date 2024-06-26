package com.example.bioserenity_websocket_test

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.bioserenity_websocket_test.data.repository.ManagerCarInfo
import com.example.bioserenity_websocket_test.data.model.Car
import com.example.bioserenity_websocket_test.data.repository.ManagerConnection
import com.example.bioserenity_websocket_test.data.model.MessageReceiver
import com.example.bioserenity_websocket_test.data.utils.Constant
import com.example.bioserenity_websocket_test.data.websockt.ClientSocket
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI

/**
 * This class uses MockitoJUnitRunner to test the functionality of ManagerCar when interacting with a ClientSocket
 * and updating a list of cars.
 * This test ensures that the ManagerCar correctly processes messages received through the ClientSocket and updates its
 * internal state (cars) accordingly.*/
@RunWith(MockitoJUnitRunner::class)
class TestCarManager {
    @Mock
    lateinit var manager: ManagerCarInfo
    lateinit var managerConnection: ManagerConnection
    lateinit var cars: MutableState<Array<Car>>
    lateinit var clientSocket: ClientSocket

    @Before
    fun setup() {
        cars = mutableStateOf(arrayOf())

        // Mock dependencies
        clientSocket = ClientSocket(URI(Constant.url), true)
        managerConnection = ManagerConnection(
            socket = clientSocket,
            callback = {},
            forTest = true
        )
        manager = ManagerCarInfo(
            clientSocket,
            forTest = true,
            managerConnection = managerConnection
        )
    }

    @Test
    fun testOnMessage() {
        // Create a test message in JSON format
        val testMessage = MessageReceiver(
            type = "start",
            userToken = 42,
            payload = listOf(
                Car(
                    name = "name",
                    brand = "brand",
                    cv = 0,
                    speedMax = 1.0,
                    currentSpeed = 1.0
                )
            )
        ).toJson()

        // Simulate receiving the message on the client socket
        clientSocket.onMessage(testMessage)

        // Add the car to the manager's list
        manager.addCarToList(testMessage)

        // Assertions
        assert(cars.value.isNotEmpty()) // Check if cars list is not empty
        assert(cars.value[0].name == "name") // Check if the first car's name matches expected
    }
}
