package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;

public interface CommandInterface {

  void execute(int ID);

  ElementInformationBundle getElementInformationBundle();

  Map<String, String> getParameters();

  Avatar getAvatar(int id);

}
