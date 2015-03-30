# WebChat
This project is written using IntelliJ 14. You can open it directly with intelliJ 14. It also requires Groovy 2.4 to function normally.

To start the server, run com.linangran.webchat.imp.Server in WebChatImp.

## Structure
This project has two parts. One is WebChat and one is WebChatImp. WebChat implemented a Socket Server using Java NIO. It is written by Java. 
WebChatImp is written by Groovy. All kinds of applications are implemented here.
WebChat and WebChatImp are connected by server.json and config.json. In config.json, classes that are to deal with different funciton calls are defined. By dispatching server and application, we are able to create loosely dependent modules.

## Dynamic Loading
Thanks to the great dynamic power of Groovy, we are able to dynamically load plugins to this system. Simply edit extensions.json and use "/reload" command in the telnet, all plugins will be loaded on the fly! Remember to use "url" keyword to specify the plugin source file path.
More interestingly, it is a plugin named "reload" that reloads all plugins!

## Performance
Since we used groovy, the performance may not be as good as pure Java code. However, the good news is that since Groovy is 100% compatible with Java, we can migrate our code from Groovy to Java gradually when we have more customers. We can start from the bottleneck and then to those that are less frequently visited while at the same time the whole project will still function as normal.
To further increase the performance, we could partition users by IP or topics so that the burden on one machine is not too heavy. We can deploy a nginx server to forward requests to different machines.

## To be improved
The main thread will be blocked when plugins are fetching Internet data. The best practice here is to change this part to event-driven non-blocking code.
Since disconnected users are not handled, there is a potential of memory leak in the HashMap of WebChatServer and HashSet of ApplicationContext.
 
