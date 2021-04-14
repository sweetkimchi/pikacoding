package ooga.model.commands;

import java.util.Map;
import ooga.model.grid.ElementInformationBundle;
import ooga.model.player.Avatar;
import ooga.model.player.Block;
import ooga.model.player.DataCube;

public class Add extends BasicCommands {

  private final ElementInformationBundle elementInformationBundle;

  public Add(ElementInformationBundle elementInformationBundle, Map<String, String> parameters) {
    super(elementInformationBundle,parameters);
    this.elementInformationBundle = elementInformationBundle;
  }

  @Override
  public void execute(int ID) {

  }
}
