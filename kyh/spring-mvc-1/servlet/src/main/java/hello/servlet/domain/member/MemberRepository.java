package hello.servlet.domain.member;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * 원래는 해시맵 대신 ConcurrentHashMap을 사용해야 함. 동시성 문제가 있음
 * */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberRepository {

    private static final MemberRepository instance = new MemberRepository();

    private Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    public static MemberRepository getInstance() {
        return instance;
    }

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
