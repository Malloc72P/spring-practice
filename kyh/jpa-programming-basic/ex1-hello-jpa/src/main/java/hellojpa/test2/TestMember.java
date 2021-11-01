package hellojpa.test2;

import javax.persistence.*;

@Entity
@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq"
)
//@TableGenerator(
//        name = "MEMBER_SEQ_GENERATOR", table = "MY_SEQUENCES",
//        pkColumnValue = "MEMBER_SEQ", allocationSize = 1
//)
public class TestMember {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @GeneratedValue(strategy = GenerationType.TABLE, generator = "MEMBER_SEQ_GENERATOR")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
    private Long id;

    private String name;

    public TestMember() {
    }

    public TestMember(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
