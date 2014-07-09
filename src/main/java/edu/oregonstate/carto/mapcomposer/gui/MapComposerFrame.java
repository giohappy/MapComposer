/*
 * MapComposerFrame.java
 *
 * Created on July 31, 2007, 9:39 AM
 */
package edu.oregonstate.carto.mapcomposer.gui;

import edu.oregonstate.carto.mapcomposer.Map;
import edu.oregonstate.carto.utils.FileUtils;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        javax.swing.JPopupMenu.Separator jSeparator3 = new javax.swing.JPopupMenu.Separator();
        previewMenuItem = new javax.swing.JMenuItem();
        textureMenu = new javax.swing.JMenu();
        createTextureMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

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

        addLayerMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,    java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        addLayerMenuItem.setText("Add Layer");
        addLayerMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addLayerMenuItemActionPerformed(evt);
            }
        });
        mapMenu.add(addLayerMenuItem);
        mapMenu.add(jSeparator3);

        previewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P,    java.awt.Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        previewMenuItem.setText("Preview");
        previewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                previewMenuItemActionPerformed(evt);
            }
        });
        mapMenu.add(previewMenuItem);

        menuBar.add(mapMenu);

        textureMenu.setText("Texture");

        createTextureMenuItem.setText("Create Texture");
        createTextureMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createTextureMenuItemActionPerformed(evt);
            }
        });
        textureMenu.add(createTextureMenuItem);

        menuBar.add(textureMenu);

        setJMenuBar(menuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loadStyleMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadStyleMenuItemActionPerformed
        String filePath = FileUtils.askFile(null, "Load Style.xml file", null, true, "xml");
        if (filePath != null) {
            mapComposerPanel.setMap(Map.unmarshal(filePath));
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
            mapComposerPanel.getMap().marshal(file.getAbsolutePath());
    }//GEN-LAST:event_saveStyleMenuItemActionPerformed

    private void createTextureMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createTextureMenuItemActionPerformed
        // FIXME new TextureComposerFrame().setVisible(true);
    }//GEN-LAST:event_createTextureMenuItemActionPerformed

    private void addLayerMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addLayerMenuItemActionPerformed
        mapComposerPanel.addLayer();
    }//GEN-LAST:event_addLayerMenuItemActionPerformed

    private void previewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previewMenuItemActionPerformed
        mapComposerPanel.previewMap();
    }//GEN-LAST:event_previewMenuItemActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem addLayerMenuItem;
    private javax.swing.JMenuItem createTextureMenuItem;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JMenuItem loadStyleMenuItem;
    private edu.oregonstate.carto.mapcomposer.gui.MapComposerPanel mapComposerPanel;
    private javax.swing.JMenu mapMenu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem previewMenuItem;
    private javax.swing.JMenuItem saveMapMenuItem;
    private javax.swing.JMenuItem saveStyleMenuItem;
    private javax.swing.JMenu textureMenu;
    // End of variables declaration//GEN-END:variables
}
