package nas.core.web;

import lombok.RequiredArgsConstructor;
import nas.core.common.MyLogger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogDemoService {

        private final MyLogger myLogger;
//    private final ObjectProvider<MyLogger> loggerObjectProvider;

    public void logic(String id) {
//        MyLogger myLogger = loggerObjectProvider.getObject();
        System.out.println("myLogger = " + myLogger.getClass());
        myLogger.log("service id = " + id);
    }
}
