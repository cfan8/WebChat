

import com.linangran.webchat.base.data.ApplicationConfig
import com.linangran.webchat.base.data.UserSession
import com.linangran.webchat.base.interfaces.ApplicationContextInterface
import com.linangran.webchat.imp.plugins.BasePlugin

/**
 * Created by linangran on 3/30/2015.
 */
class MultiplyPlugin extends BasePlugin {
    @Override
    String onRequest(ApplicationConfig config, ApplicationContextInterface context, UserSession session, def params) {
        return params[0].toString().toInteger() * params[1].toString().toInteger();
    }
}
