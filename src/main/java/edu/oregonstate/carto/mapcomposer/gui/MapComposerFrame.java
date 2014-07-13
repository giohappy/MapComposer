/*
 * MapComposerFrame.java
 *
 * Created on July 31, 2007, 9:39 AM
 */
package edu.oregonstate.carto.mapcomposer.gui;

import edu.oregonstate.carto.mapcomposer.Map;
import edu.oregonstate.carto.utils.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBException;

/**
 * Main window for composing a map consisting of multiple layers. The UI
 * elements mostly handled by a MapComposerPanel.
 *
 * @author Bernhard Jenny, Institute of Cartography, ETH Zurich.
 * @author Charlotte Hoarau, COGIT Laboratory, IGN France
 */
public class MapComposerFrame extends javax.swing.JFrame {

    /**
     * Creates new form MapComposerFrame
     */
    public MapComposerFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mapComposerPanel = new edu.oregonstate.carto.mapcomposer.gui.MapComposerPanel();
        menuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        saveMapMenuItem = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator2 = new javax.swing.JPopupMenu.Separator();
        loadStyleMenuItem = new javax.swing.JMenuItem();
        saveStyleMenuItem = new javax.swing.JMenuItem();
        mapMenu = new javax.swing.JMenu();
        addLayerMenuItem = new javax.swing.JMenuItem();
        removeLayerMenuItem = new javax.swing.JMenuItem();
        javax.swing.JPopupMenu.Separator jSeparator1 = new javax.swing.JPopupMenu.Separator();
        reloadMapPreviewMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        mapComposerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 20));
        getContentPane().add(mapComposerPanel, java.awt.BorderLayout.CENTER);

        fileMenu.setText("File");

        saveMapMenuItem.setText("Save Tiles");
        saveMapMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveMapMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveMapMenuItem);
        fileMenu.add(jSeparator2);

        loadStyleMenuItem.setText("Load Map Settings");
        loadStyleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadStyleMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(loadStyleMenuItem);

        saveStyleMenuItem.setText("Save Map Settings");
        saveStyleMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveStyleMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(saveStyleMenuItem);

        menuBar.add(fileMenu);

        mapMenu.setText("Map");

        addLayerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PLUS, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        addLayerMenuItem.setText("Add Layer");
        addLayerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLayerMenuItemActionPerformed(evt);
            }
        });
        mapMenu.add(addLayerMenuItem);

        removeLayerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MINUS,    java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        removeLayerMenuItem.setText("Remove Layer");
        removeLayerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeLayerMenuItemActionPerformed(evt);
            }
        });
        mapMenu.add(removeLayerMenuItem);
        mapMenu.add(jSeparator1);

        reloadMapPreviewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        reloadMapPreviewMenuItem.setText("Reload Map Preview");
        reloadMapPreviewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reloadMapPreviewMenuItemActionPerformed(evt);
            }
        });
        mapMenu.add(reloadMapPreviewMenuItem);

        menuBar.add(mapMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadStyleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadStyleMenuItemActionPerformed
        String filePath = FileUtils.askFile(null, "Load Style.xml file", null, true, "xml");
        if (filePath != null) {
            try {
                mapComposerPanel.setMap(Map.unmarshal(filePath));
            } catch (JAXBException | FileNotFoundException ex) {
                Logger.getLogger(MapComposerFrame.class.getName()).log(Level.SEVERE, null, ex);
                String msg = "Could not load style file";
                String title = "Error";
                ErrorDialog.showErrorDialog(msg, title, ex, rootPane);
            }
        }
    }//GEN-LAST:event_loadStyleMenuItemActionPerformed

    private void saveMapMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveMapMenuItemActionPerformed
        try {
            String directoryPath = FileUtils.askDirectory(null, "Save Tiled Images", false, null);
            if (directoryPath == null) {
                // user canceled
                return;
            }
            mapComposerPanel.readGUI();
            mapComposerPanel.renderTilesWithProgressDialog(new File(directoryPath));
        } catch (IOException ex) {
            Logger.getLogger(MapComposerFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveMapMenuItemActionPerformed

    private void saveStyleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveStyleMenuItemActionPerformed
        String filePath = FileUtils.askFile(null, "Save XML Style", null, false, "xml");
        if (filePath == null) {
            return;
        }
        File file = new File(filePath);
        try {
            mapComposerPanel.getMap().marshal(file.getAbsolutePath());
        } catch (JAXBException | FileNotFoundException ex) {
            Logger.getLogger(MapComposerFrame.class.getName()).log(Level.SEVERE, null, ex);
            String msg = "Could not save style file";
            String title = "Error";
            ErrorDialog.showErrorDialog(msg, title, ex, rootPane);
        }
    }//GEN-LAST:event_saveStyleMenuItemActionPerformed

    private void addLayerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLayerMenuItemActionPerformed
        mapComposerPanel.addLayer();
    }//GEN-LAST:event_addLayerMenuItemActionPerformed

    private void removeLayerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeLayerMenuItemActionPerformed
        mapComposerPanel.removeLayer();
    }//GEN-LAST:event_removeLayerMenuItemActionPerformed

    private void reloadMapPreviewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reloadMapPreviewMenuItemActionPerformed
        mapComposerPanel.reloadMap();
    }//GEN-LAST:event_reloadMapPreviewMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addLayerMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem loadStyleMenuItem;
    private edu.oregonstate.carto.mapcomposer.gui.MapComposerPanel mapComposerPanel;
    private javax.swing.JMenu mapMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem reloadMapPreviewMenuItem;
    private javax.swing.JMenuItem removeLayerMenuItem;
    private javax.swing.JMenuItem saveMapMenuItem;
    private javax.swing.JMenuItem saveStyleMenuItem;
    // End of variables declaration//GEN-END:variables
}
