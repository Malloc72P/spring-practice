package nas.core.beandefinition;

import nas.core.AppConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

public class BeanDefinitionTest {
    AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
    GenericXmlApplicationContext xmlAc = new GenericXmlApplicationContext("appConfig.xml");

    @Test
    @DisplayName("빈 설정 메타정보 확인")
    void findApplicationBeanDefinition() {
//        String[] beanDefinitionNames = ac.getBeanDefinitionNames();
        String[] beanDefinitionNames = xmlAc.getBeanDefinitionNames();

        for (String definitionName : beanDefinitionNames) {
            BeanDefinition beanDefinition = ac.getBeanDefinition(definitionName);
            if (beanDefinition.getRole() == BeanDefinition.ROLE_APPLICATION) {
                System.out.println();
                System.out.println("definitionName = " + definitionName);
                System.out.println("beanDefinition = " + beanDefinition);
                System.out.println();
            }
        }
    }
}
