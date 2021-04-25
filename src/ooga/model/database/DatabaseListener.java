package ooga.model.database;

public interface DatabaseListener {

  void codeAreaChanged();

  void checkLevelEndedForCurrentTeam();

  void checkLevelEndedForBothTeams();

  void checkLevelStarted();

}
