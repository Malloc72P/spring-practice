package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void testMember() {
        Member member = new Member("memberA");

        Member savedMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(savedMember.getId())
                .orElseThrow(NoSuchElementException::new);

        assertThat(findMember)
                .extracting(
                        Member::getId,
                        Member::getUsername
                )
                .containsExactly(
                        savedMember.getId(),
                        savedMember.getUsername()
                );
        assertThat(findMember).isEqualTo(savedMember);
    }
}
