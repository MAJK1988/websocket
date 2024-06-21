package com.example.bioserenity_websocket_test

import com.example.bioserenity_websocket_test.connection.ManagerConnection
import com.example.bioserenity_websocket_test.utils.Constant
import com.example.bioserenity_websocket_test.websockt.ClientSocket
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.net.URI
/**
 * This class uses MockitoJUnitRunner to test the interaction between ClientSocket and ManagerConnection through the ConnectionInterface.
 * These tests validate that the ManagerConnection correctly handles the onOpen, onError, and onClose events from the ClientSocket through
 * the ConnectionInterface. The mocks and assertions*/
@RunWith(MockitoJUnitRunner::class)
class TestConnectionInterface {
    @Mock
    lateinit var managerConnectionn: ManagerConnection
    lateinit var socket: ClientSocket

    @Before
    fun setup(){
        socket= ClientSocket(URI(Constant.url),true)
        socket.initializeConnectionInterface(managerConnectionn)
    }

    @Test
    fun testInterFaceOnOpen(){
        socket.onOpen(null)
        Mockito.verify(managerConnectionn).onConnect()
    }


    @Test
    fun testInterFaceOnClose(){
        socket.onClose(1,null,true)
        Mockito.verify(managerConnectionn).onClose()
    }

}