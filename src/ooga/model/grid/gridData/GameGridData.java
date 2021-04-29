package ooga.model.grid.gridData;

import ooga.model.grid.ElementInformationBundle;
import ooga.model.grid.Structure;

/**
 * @author billyluqiu
 * Class representing gameGrid object for the structures of the grid itself.
 * Depends on the gameGrid object from elementinformationbundle, and the Structure class for the enums.
 * Assumes only one structure per game grid tile.
 */
public class GameGridData {

  private final Structure[][] structures;
  private final ElementInformationBundle elementInformationBundle;
  private final int rows;
  private final int columns;

  /**
   * Constructor for gameGridData that takes in a gameGrid and fills in the instance varibales
   * @param elementInformationBundle gameGrid object
   * @param x number of columns (>0)
   * @param y number or rows (>0)
   */
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

  /**
   * Same array size as the grid size.
   * Return two 2D array of structures.
   * @return 2D structure array.
   */
  public Structure[][] getStructures() {
    return structures;
  }

  /**
   * get number of rows.
   * @return num of rows.
   */
  public int getRows() {
    return rows;
  }

  /**
   * get num of columsn.
   * @return num of columns.
   */
  public int getColumns() {
    return columns;
  }

}
