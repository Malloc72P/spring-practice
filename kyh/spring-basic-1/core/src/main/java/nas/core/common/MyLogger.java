package nas.core.common;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Component
@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class MyLogger {

    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void log(String message) {
        System.out.println("[" + uuid + "]" + " [" + requestURL + "]" + " [" + message + "]");
    }

    @PostConstruct
    public void init() {
        //거의 중복되지 않는다고 한다. 확률이 매우매우매우 낮다고 한다.
        this.uuid = UUID.randomUUID().toString();
        System.out.println("[" + uuid + "]" + " [" + requestURL + "]" + " [" + "init" + "]" + " [" + this + "]");
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "]" + " [" + requestURL + "]" + " [" + "destroy " + "]" + " [" + this + "]");
    }
}
