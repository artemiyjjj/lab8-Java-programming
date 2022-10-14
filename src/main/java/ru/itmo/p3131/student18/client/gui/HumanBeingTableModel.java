package ru.itmo.p3131.student18.client.gui;

import ru.itmo.p3131.student18.client.gui.clientCollection.HumanBeingCollectionManager;
import ru.itmo.p3131.student18.interim.objectclasses.HumanBeing;
import ru.itmo.p3131.student18.server.collection.CollectionManager;

import javax.swing.table.AbstractTableModel;

public class HumanBeingTableModel extends AbstractTableModel {
    private final HumanBeingCollectionManager colManager;
    private final Object[][] rowData;
    private final String[] fieldNames;

    public HumanBeingTableModel(HumanBeingCollectionManager colManager) {
        this.colManager = colManager;
        this.rowData = colManager.getRowData();
        this.fieldNames = colManager.getRowFieldNames();
    }

    @Override
    public String getColumnName(int column) {
        return fieldNames[column];
    }

    @Override
    public int getRowCount() {
        return colManager.getSize();
    }

    @Override
    public int getColumnCount() {
        return 12;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return rowData[rowIndex][columnIndex];
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return getValueAt(0,columnIndex).getClass();
    }
}
