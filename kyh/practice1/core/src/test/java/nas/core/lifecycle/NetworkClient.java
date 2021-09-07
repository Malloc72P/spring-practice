package nas.core.lifecycle;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class NetworkClient {

    private String url;

    public NetworkClient() {
        System.out.println("생성자 호출. url : " + url);
//        connect();
//        call("초기화 연결 메세지");

    }

    @PostConstruct
    public void init() throws Exception {
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }

    @PreDestroy
    public void close() throws Exception {
        System.out.println("NetworkClient.close");
        disconnect();
    }
//    @Override
//    public void destroy() throws Exception {
//        System.out.println("NetworkClient.destroy");
//        disconnect();
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        System.out.println("NetworkClient.afterPropertiesSet");
//        connect();
//        call("초기화 연결 메세지");
//    }

    public void connect() {
        System.out.println("connect. url = " + url);
    }

    public void call(String message) {
        System.out.println("url = " + url + " message = " + message);
    }

    public void disconnect() {
        System.out.println("NetworkClient.disconnect : connection closed");
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
