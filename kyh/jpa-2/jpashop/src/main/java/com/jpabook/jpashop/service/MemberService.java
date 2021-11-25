package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OldMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
@Service
안에 Component 애너테이션이 있다. 사용하면 ComponentScan의 대상이 된다
==============================================================
@Transactional(readOnly = true)
데이터 변경은 반드시 트랜잭션 안에서 이루어져야 한다
public메서드에는 트랜잭션이 적용된다
javax와 스프링에서 제공하는 트랜잭셔널 애너테이션이 있는데, 우리는 스프링을 쓰니까 스프링꺼를 쓰자
* */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    /*
    필드주입을 하면 테스트할 때 구현체를 바꿀 수 없다.
    이에 대한 해결책으로 Setter주입이나 생성자 주입을 사용할 수 있다. 가짜 객체를 넣을수도 있다
    근데 Setter주입도 좋은 방법은 아니다. 보통 이런 빈은 런타임에 다른 빈으로 갈아끼워질 일이 없다.
    보통 final이다. 근데 수정자를 열어두면 실수할 수 있는 부분을 하나 만들게 되니까 좋지 않다
    반면 생성자주입을 사용하면 final키워드를 사용할 수 있으므로 실수할 부분을 줄이는데다가, 테스트를 위해 다른 빈을 넣을수도 있다
    또, 생성시점에 의존성이 주입되지 않는다면 컴파일 오류가 발생하므로, 실수로 의존성을 넣지 않고 테스트하는 실수를 막을 수 있다.
    생성자가 단 하나라면 @Autowired가 없어도 생성자 주입이 된다
    또, 롬복을 사용하면 개발자가 직접 생성자를 만들지 않아도 된다
    RequiredArgsConstructor는 final있는 필드만 가지고 생성자를 만들어준다.
    생성자 주입을 롬복으로 한다면 RAC를 쓰는게 좋겠다
    * */
    /*
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }
    * */

    /*
    회원가입
    데이터 변경이 필요하므로 readonly가 아닌 트랜잭셔널을 붙인다
    기본값이 readonly = false이다
    */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    /*
    검증로직. 근데 문제가 하나 있다.
    WAS는 두대 이상이 가동하다보니, 동시에 디비에 같은 정보를 인서트하면 동시에 이 검증메서드를 통과하게된다.
    그러면 검증이 무의미해진다
    따라서 실무에서는 추가적인 대책이 필요하다.
    보통 디비 제약조건으로 UNIQUE조건을 주어서 해결한다
    * */
    private void validateDuplicateMember(Member member) {
        //중복회원인지 검사. 중복회원이면 예외 발생
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if (!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    /*
    회원 전체 조회
    @Transactional(readOnly = true)//더티체킹을 안하거나 디비한테 읽기전용임을 알려줘서 좀 더 빠르게 읽을 수 있도록 함
    읽기에는 가급적 이런 설정을 쓰자
    근데 조회가 더 많으니까 클래스에 전역으로 읽기전용 트랜잭션 설정을 붙이고 변경하는 메서드에만 따로 부착하자
    */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findById(memberId).get();
    }

    @Transactional
    public void updateMember(Long memberId, String newName) {
        Member findMember = findOne(memberId);
        findMember.setName(newName);
    }
}
