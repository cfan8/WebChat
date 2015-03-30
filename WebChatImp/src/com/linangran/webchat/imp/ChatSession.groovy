package com.linangran.webchat.imp

import com.linangran.webchat.base.data.UserSession

/**
 * Created by linangran on 3/30/2015.
 */
class ChatSession extends UserSession {
    String username;
    boolean isLoggedin;

    @Override
    boolean shouldReceiveBroadcast() {
        return isLoggedin
    }
}
