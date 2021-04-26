package ooga.model.grid.gridData;

import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;

public class GameGridData {

  private final Structure[][] structures;
  private final ElementInformationBundle elementInformationBundle;
  private final int rows;
  private final int columns;

  public GameGridData(ElementInformationBundle elementInformationBundle, int x, int y) {
    this.elementInformationBundle = elementInformationBundle;
    this.rows = y;
    this.columns = x;
    structures = new Structure[x][y];
    setStructures();
  }

  private void setStructures() {
    for (int i = 0; i < columns; i++) {
      for (int j = 0; j < rows; j++) {
        structures[i][j] = elementInformationBundle.getStructure(i, j);
      }
    }
  }

  public Structure[][] getStructures() {
    return structures;
  }

  public int getRows() {
    return rows;
  }

  public int getColumns() {
    return columns;
  }

}
