package hellojpa;

import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("A")
public class Album extends Item {
    private String artists;
}
