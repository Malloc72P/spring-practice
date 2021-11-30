package study.datajpa.repository;

public interface MemberProjection {
    Long getId();

    String getUsername();

    TeamInfo getTeam();

    interface TeamInfo {
        String getName();
    }
}
