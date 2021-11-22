package nas.core.singleton;

public class SingletonService {

    private static final SingletonService instance = new SingletonService();
    //자기자신을 내부에 private static으로 가지고 있다.
    //클래스 레벨로 갖고있다. 그래서 스테틱 영역에 하나만 생긴다. 공유변수처럼
    //SingletonService가 스테틱 영역에 생성될 때, instance 정적변수를 생성한다.

    //실수로 외부에서 추가 인스턴스화하는걸 막기 위해 private 생성자를 만든다
    //추가생성하는 순간 컴파일 에러가 발생한다. 이는 의도된 에러이고, 좋은 설계이다(실수방지)
    private SingletonService() {
    }

    public static SingletonService getInstance() {
        //이러면 JVM이 시작될 때, 내부적으로 실행되어서 정적변수 instance에 객체를 생성해서 저장한다.
        return instance;
    }
}
