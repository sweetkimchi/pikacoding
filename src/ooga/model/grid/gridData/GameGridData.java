package ooga.model.grid.gridData;

import ooga.model.grid.GameGrid;
import ooga.model.grid.Structure;
import ooga.model.grid.Tile;

public class GameGridData {

  private Structure[][] structures;
  private GameGrid gameGrid;
  private int rows;
  private int columns;

  public GameGridData(GameGrid gameGrid, int rows, int columns) {
    this.gameGrid = gameGrid;
    this.rows = rows;
    this.columns = columns;
    structures = new Structure[rows][columns];
    setStructures();
  }

  private void setStructures()  {
    for (int i=0; i<rows; i++) {
      for (int j=0; j<columns; j++) {
        structures[i][j] = gameGrid.getStructure(i,j);
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
