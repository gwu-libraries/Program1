/*
 * DesktopApplication2View.java
 */

package desktopapplication2;

import org.jdesktop.application.Action;
import org.jdesktop.application.ResourceMap;
import org.jdesktop.application.SingleFrameApplication;
import org.jdesktop.application.FrameView;
import org.jdesktop.application.TaskMonitor;
import java.awt.event.ActionEvent;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.ArrayList;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;
import javax.swing.JFileChooser;
import javax.persistence.EntityManager;
import javax.swing.table.DefaultTableModel;
import java.util.Date;
import java.util.Collection ;
import javax.swing.DefaultListModel;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JTable;
import javax.persistence.Query;
import javax.swing.JOptionPane;
import java.lang.NumberFormatException;


/**
 * The application's main frame.
 */
public class DesktopApplication2View extends FrameView {

    public DesktopApplication2View(SingleFrameApplication app) {
        super(app);
        
        initComponents();

       
        // status bar initialization - message timeout, idle icon and busy animation, etc
        jMenu1.setText("Edit");
        Redo_Book.setText("Redo Book");
        Foldout.setText("Foldout");
        ResourceMap resourceMap = getResourceMap();
        entityManager =  javax.persistence.Persistence.createEntityManagerFactory("DesktopApplication2PU").createEntityManager(); // NOI18N
        int messageTimeout = resourceMap.getInteger("StatusBar.messageTimeout");
        m1=(DefaultTableModel)jTable1.getModel();
       m2=(DefaultTableModel)jTable2.getModel();
       listener=new SelectionListener(jTable2,entityManager,m2,m1);

       jTable2.getSelectionModel().addListSelectionListener(listener);
       jTable2.getColumnModel().getSelectionModel().addListSelectionListener(listener) ;
       jList1.setModel(l1);
       jList2.setModel(l2);
        
        messageTimer = new Timer(messageTimeout, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                statusMessageLabel.setText("");
            }
        });
        messageTimer.setRepeats(false);
        int busyAnimationRate = resourceMap.getInteger("StatusBar.busyAnimationRate");
        for (int i = 0; i < busyIcons.length; i++) {
            busyIcons[i] = resourceMap.getIcon("StatusBar.busyIcons[" + i + "]");
        }
        busyIconTimer = new Timer(busyAnimationRate, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                busyIconIndex = (busyIconIndex + 1) % busyIcons.length;
                statusAnimationLabel.setIcon(busyIcons[busyIconIndex]);
            }
        });
        idleIcon = resourceMap.getIcon("StatusBar.idleIcon");
        statusAnimationLabel.setIcon(idleIcon);
        progressBar.setVisible(false);

        // connecting action tasks to status bar via TaskMonitor
        TaskMonitor taskMonitor = new TaskMonitor(getApplication().getContext());
        taskMonitor.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                String propertyName = evt.getPropertyName();
                if ("started".equals(propertyName)) {
                    if (!busyIconTimer.isRunning()) {
                        statusAnimationLabel.setIcon(busyIcons[0]);
                        busyIconIndex = 0;
                        busyIconTimer.start();
                    }
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(true);
                } else if ("done".equals(propertyName)) {
                    busyIconTimer.stop();
                    statusAnimationLabel.setIcon(idleIcon);
                    progressBar.setVisible(false);
                    progressBar.setValue(0);
                } else if ("message".equals(propertyName)) {
                    String text = (String)(evt.getNewValue());
                    statusMessageLabel.setText((text == null) ? "" : text);
                    messageTimer.restart();
                } else if ("progress".equals(propertyName)) {
                    int value = (Integer)(evt.getNewValue());
                    progressBar.setVisible(true);
                    progressBar.setIndeterminate(false);
                    progressBar.setValue(value);
                }
            }
        });
    }

    @Action
    public void showAboutBox() {
        if (aboutBox == null) {
            JFrame mainFrame = DesktopApplication2.getApplication().getMainFrame();
            aboutBox = new DesktopApplication2AboutBox(mainFrame);
            aboutBox.setLocationRelativeTo(mainFrame);
        }
        DesktopApplication2.getApplication().show(aboutBox);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mainPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        menuBar = new javax.swing.JMenuBar();
        javax.swing.JMenu fileMenu = new javax.swing.JMenu();
        New_Project = new javax.swing.JMenuItem();
        Open_Project = new javax.swing.JMenuItem();
        Close_Project = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        javax.swing.JMenuItem exitMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        Redo_Book = new javax.swing.JMenuItem();
        Redo_Book_Pages = new javax.swing.JMenuItem();
        Foldout = new javax.swing.JMenuItem();
        changeDpi = new javax.swing.JMenuItem();
        Manual_Conversion = new javax.swing.JMenuItem();
        update_Database = new javax.swing.JMenuItem();
        remaining_Books = new javax.swing.JMenuItem();
        javax.swing.JMenu helpMenu = new javax.swing.JMenu();
        javax.swing.JMenuItem aboutMenuItem = new javax.swing.JMenuItem();
        statusPanel = new javax.swing.JPanel();
        javax.swing.JSeparator statusPanelSeparator = new javax.swing.JSeparator();
        statusMessageLabel = new javax.swing.JLabel();
        statusAnimationLabel = new javax.swing.JLabel();
        progressBar = new javax.swing.JProgressBar();
        jFileChooser1 = new javax.swing.JFileChooser();
        DesktopApplication2PUEntityManager = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("DesktopApplication2PU").createEntityManager();
        bookQuery = java.beans.Beans.isDesignTime() ? null : DesktopApplication2PUEntityManager.createQuery("SELECT b FROM Book b");
        bookList = java.beans.Beans.isDesignTime() ? java.util.Collections.emptyList() : bookQuery.getResultList();
        jFrame1 = new javax.swing.JFrame();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jPanel2 = new javax.swing.JPanel();
        jButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jFrame2 = new javax.swing.JFrame();
        jButton7 = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable3 = new javax.swing.JTable();
        update_database = new javax.swing.JButton();

        mainPanel.setName("mainPanel"); // NOI18N

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(desktopapplication2.DesktopApplication2.class).getContext().getResourceMap(DesktopApplication2View.class);
        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Barcode", "TiffDate", "JP2", "Jpeg", "OCRed"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setName("jTable1"); // NOI18N
        jScrollPane1.setViewportView(jTable1);
        jTable1.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable1.columnModel.title0")); // NOI18N
        jTable1.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable1.columnModel.title1")); // NOI18N
        jTable1.getColumnModel().getColumn(2).setResizable(false);
        jTable1.getColumnModel().getColumn(2).setHeaderValue(resourceMap.getString("jTable1.columnModel.title2")); // NOI18N
        jTable1.getColumnModel().getColumn(3).setHeaderValue(resourceMap.getString("jTable1.columnModel.title3")); // NOI18N
        jTable1.getColumnModel().getColumn(4).setHeaderValue(resourceMap.getString("jTable1.columnModel.title4")); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Project_id", "Project_name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable2.setName("jTable2"); // NOI18N
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable2);
        jTable2.getColumnModel().getColumn(0).setHeaderValue(resourceMap.getString("jTable2.columnModel.title0")); // NOI18N
        jTable2.getColumnModel().getColumn(1).setHeaderValue(resourceMap.getString("jTable2.columnModel.title1")); // NOI18N

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(57, 57, 57))
                    .addGroup(mainPanelLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(14, 14, 14)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(mainPanelLayout.createSequentialGroup()
                .addGroup(mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, 0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, mainPanelLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(51, 51, 51))
        );

        menuBar.setName("menuBar"); // NOI18N

        fileMenu.setText(resourceMap.getString("fileMenu.text")); // NOI18N
        fileMenu.setName("fileMenu"); // NOI18N

        New_Project.setText(resourceMap.getString("New_Project.text")); // NOI18N
        New_Project.setName("New_Project"); // NOI18N
        New_Project.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                New_ProjectActionPerformed(evt);
            }
        });
        fileMenu.add(New_Project);

        Open_Project.setText(resourceMap.getString("Open_Project.text")); // NOI18N
        Open_Project.setName("Open_Project"); // NOI18N
        Open_Project.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Open_ProjectActionPerformed(evt);
            }
        });
        fileMenu.add(Open_Project);

        Close_Project.setText(resourceMap.getString("Close_Project.text")); // NOI18N
        Close_Project.setName("Close_Project"); // NOI18N
        Close_Project.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Close_ProjectActionPerformed(evt);
            }
        });
        fileMenu.add(Close_Project);

        jSeparator1.setName("jSeparator1"); // NOI18N
        fileMenu.add(jSeparator1);

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(desktopapplication2.DesktopApplication2.class).getContext().getActionMap(DesktopApplication2View.class, this);
        exitMenuItem.setAction(actionMap.get("quit")); // NOI18N
        exitMenuItem.setName("exitMenuItem"); // NOI18N
        fileMenu.add(exitMenuItem);

        menuBar.add(fileMenu);

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N

        Redo_Book.setText(resourceMap.getString("Redo_Book.text")); // NOI18N
        Redo_Book.setName("Redo_Book"); // NOI18N
        Redo_Book.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Redo_BookActionPerformed(evt);
            }
        });
        jMenu1.add(Redo_Book);

        Redo_Book_Pages.setText(resourceMap.getString("Redo_Book_Pages.text")); // NOI18N
        Redo_Book_Pages.setName("Redo_Book_Pages"); // NOI18N
        Redo_Book_Pages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Redo_Book_PagesActionPerformed(evt);
            }
        });
        jMenu1.add(Redo_Book_Pages);

        Foldout.setText(resourceMap.getString("Foldout.text")); // NOI18N
        Foldout.setName("Foldout"); // NOI18N
        Foldout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FoldoutActionPerformed(evt);
            }
        });
        jMenu1.add(Foldout);

        changeDpi.setText(resourceMap.getString("changeDpi.text")); // NOI18N
        changeDpi.setName("changeDpi"); // NOI18N
        changeDpi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeDpiActionPerformed(evt);
            }
        });
        jMenu1.add(changeDpi);

        Manual_Conversion.setText(resourceMap.getString("Manual_Conversion.text")); // NOI18N
        Manual_Conversion.setName("Manual_Conversion"); // NOI18N
        Manual_Conversion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Manual_ConversionActionPerformed(evt);
            }
        });
        jMenu1.add(Manual_Conversion);

        update_Database.setText(resourceMap.getString("update_Database.text")); // NOI18N
        update_Database.setName("update_Database"); // NOI18N
        update_Database.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_DatabaseActionPerformed(evt);
            }
        });
        jMenu1.add(update_Database);

        remaining_Books.setText(resourceMap.getString("remaining_Books.text")); // NOI18N
        remaining_Books.setName("remaining_Books"); // NOI18N
        remaining_Books.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                remaining_BooksActionPerformed(evt);
            }
        });
        jMenu1.add(remaining_Books);

        menuBar.add(jMenu1);

        helpMenu.setText(resourceMap.getString("helpMenu.text")); // NOI18N
        helpMenu.setName("helpMenu"); // NOI18N

        aboutMenuItem.setAction(actionMap.get("showAboutBox")); // NOI18N
        aboutMenuItem.setName("aboutMenuItem"); // NOI18N
        helpMenu.add(aboutMenuItem);

        menuBar.add(helpMenu);

        statusPanel.setName("statusPanel"); // NOI18N

        statusPanelSeparator.setName("statusPanelSeparator"); // NOI18N

        statusMessageLabel.setName("statusMessageLabel"); // NOI18N

        statusAnimationLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        statusAnimationLabel.setName("statusAnimationLabel"); // NOI18N

        progressBar.setName("progressBar"); // NOI18N

        javax.swing.GroupLayout statusPanelLayout = new javax.swing.GroupLayout(statusPanel);
        statusPanel.setLayout(statusPanelLayout);
        statusPanelLayout.setHorizontalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statusPanelSeparator, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(statusMessageLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 422, Short.MAX_VALUE)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusAnimationLabel)
                .addContainerGap())
        );
        statusPanelLayout.setVerticalGroup(
            statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(statusPanelLayout.createSequentialGroup()
                .addComponent(statusPanelSeparator, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(statusPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusMessageLabel)
                    .addComponent(statusAnimationLabel)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3))
        );

        jFileChooser1.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        jFileChooser1.setName("jFileChooser1"); // NOI18N

        jFrame1.setName("jFrame1"); // NOI18N

        jPanel1.setName("jPanel1"); // NOI18N

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList1.setName("jList1"); // NOI18N
        jScrollPane3.setViewportView(jList1);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jList2.setName("jList2"); // NOI18N
        jScrollPane4.setViewportView(jList2);

        jPanel2.setName("jPanel2"); // NOI18N

        jButton2.setText(resourceMap.getString("jButton2.text")); // NOI18N
        jButton2.setToolTipText(resourceMap.getString("jButton2.toolTipText")); // NOI18N
        jButton2.setIconTextGap(6);
        jButton2.setName("jButton2"); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton4.setText(resourceMap.getString("jButton4.text")); // NOI18N
        jButton4.setToolTipText(resourceMap.getString("jButton4.toolTipText")); // NOI18N
        jButton4.setIconTextGap(6);
        jButton4.setName("jButton4"); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton1.setText(resourceMap.getString("jButton1.text")); // NOI18N
        jButton1.setToolTipText(resourceMap.getString("jButton1.toolTipText")); // NOI18N
        jButton1.setIconTextGap(6);
        jButton1.setName("jButton1"); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText(resourceMap.getString("jButton3.text")); // NOI18N
        jButton3.setToolTipText(resourceMap.getString("jButton3.toolTipText")); // NOI18N
        jButton3.setIconTextGap(6);
        jButton3.setName("jButton3"); // NOI18N
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(jButton2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE))
                .addGap(35, 35, 35))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel3.setName("jPanel3"); // NOI18N

        jButton5.setText(resourceMap.getString("jButton5.text")); // NOI18N
        jButton5.setMaximumSize(new java.awt.Dimension(71, 33));
        jButton5.setMinimumSize(new java.awt.Dimension(71, 33));
        jButton5.setName("jButton5"); // NOI18N
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText(resourceMap.getString("jButton6.text")); // NOI18N
        jButton6.setName("jButton6"); // NOI18N
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addGap(47, 47, 47))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(60, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(124, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(119, 119, 119))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(43, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jFrame2.setName("jFrame2"); // NOI18N

        jButton7.setText(resourceMap.getString("jButton7.text")); // NOI18N
        jButton7.setName("jButton7"); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jScrollPane5.setName("jScrollPane5"); // NOI18N

        jTable3.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Project", "Barcode"
            }
        ));
        jTable3.setName("jTable3"); // NOI18N
        jScrollPane5.setViewportView(jTable3);

        update_database.setText(resourceMap.getString("update_database.text")); // NOI18N
        update_database.setName("update_database"); // NOI18N
        update_database.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_databaseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jFrame2Layout = new javax.swing.GroupLayout(jFrame2.getContentPane());
        jFrame2.getContentPane().setLayout(jFrame2Layout);
        jFrame2Layout.setHorizontalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jFrame2Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jButton7)
                .addGap(30, 30, 30)
                .addComponent(update_database)
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame2Layout.createSequentialGroup()
                .addContainerGap(15, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jFrame2Layout.setVerticalGroup(
            jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jFrame2Layout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jFrame2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton7)
                    .addComponent(update_database))
                .addGap(54, 54, 54))
        );

        setComponent(mainPanel);
        setMenuBar(menuBar);
        setStatusBar(statusPanel);
    }// </editor-fold>//GEN-END:initComponents
synchronized void recievepath(Server s, String path1,String Path2,Process p,int option,int id)
        {
            int i;

          
              if (option !=3)
              {
           for(i=0;i<data.size();i++)
           {
              
               if(s==data.get(i).getServer())
               {
                    System.out.println("object found");
                   data.get(i).setPath(path1);
                   data.get(i).setProcess(p);
               }
           }
           if(i>data.size())
               System.out.println("server object " + s + "not found in array");
              }
           if(option ==1)
           {
               try
          {
              int index=path1.lastIndexOf("\\");
              index=index+1;
              String pname=path1.substring(index, path1.length());
              Project project = new Project();
              System.out.println("Project Name: "+pname);
              //project.setProjectid(count+1);
              project.setProjectname(pname);
              String ppath = "z:\\backup\\"+pname;
            
              //DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
              Date date = new Date();
               Boolean res= (new File("d:\\backup\\"+pname).mkdir());
               if(res==true)
                   project.setProjectPath(path1);
              project.setProjectdate(date);

             /* Book b= new Book();
                int ind=Path2.lastIndexOf("\\");
                System.out.println("String is "+ Path2 +"index of \\ is "+ ind);
                ind=ind+1;
                String temp=Path2.substring(ind, Path2.length());
                System.out.println("String is "+ temp +"index of \\ is "+ ind);
                //Project proj=entityManager.find(Project.class, pname);
                
                //int id=projid.intValue();
                b.setProjectid(project);
                b.setBarcode(temp);
                Date d= new Date();
                b.setDatecreated(d);
                b.setPath(Path2);
                b.setTiffstart(d);*/
                entityManager.getTransaction().begin();
                
                entityManager.persist(project);
                entityManager.getTransaction().commit();
                //entityManager.getTransaction().begin();
                //entityManager.persist(b);
                //entityManager.getTransaction().commit();
                Vector v = new Vector();
                v.add(project.getProjectid());
                v.add(project.getProjectname());
                int rowindex;
                rowindex=m2.getRowCount();
                rowindex=rowindex+1;
                m2.addRow(v);
                m2.fireTableRowsInserted(rowindex, rowindex);
                /*Vector v2 = new Vector();
                v2.add(b.getBarcode());
                v2.add(b.getTiffend());
                v2.add(b.getJpc());
                v2.add(b.getJpeg());
                v2.add(b.getOCRed());
                rowindex=m2.getRowCount();
                rowindex=rowindex+1;
                m1.addRow(v2);
                m1.fireTableRowsInserted(rowindex, rowindex);*/


                
 }
          catch (Exception e)
    {
        System.out.println(e);
        e.printStackTrace();
    }



           }
           else if (option ==2)
           {
               try
          {
                int index=path1.lastIndexOf("\\");
                index=index+1;
                Project pro=null;
                int proj_id=0;
                String pname=path1.substring(index, path1.length());

                Query query = entityManager.createNamedQuery("Project.findByProjectname");
                query.setParameter("projectname", pname);
                Iterator result = query.getResultList().iterator();
                if(result.hasNext())
                {
                    pro = (Project) result.next();
                    proj_id=pro.getProjectid();
                }
                System.out.println("proj_id in recievepath is: "+proj_id);
                Book b= new Book();
                int ind=Path2.lastIndexOf("\\");
                System.out.println("String is "+ Path2 +"index of \\ is "+ ind);
                ind=ind+1;
                String temp=Path2.substring(ind, Path2.length());
                System.out.println("String is "+ temp +"index of \\ is "+ ind);
                String temp1=Path2.substring(ind, Path2.length());
                //Boolean res= (new File("C:\\Backup\\"+pname+"\\"+temp).mkdir());

                Book booktemp=entityManager.find(Book.class,temp);
                if(booktemp==null)
                {
                b.setBarcode(temp);
                b.setProjectid(pro);
                Date d= new Date();
                b.setDatecreated(d);
                b.setPath(Path2);
                b.setTiffstart(d);
                entityManager.getTransaction().begin();
                entityManager.persist(b);
                entityManager.getTransaction().commit();
                Vector v2 = new Vector();
                v2.add(b.getBarcode());
                v2.add(b.getTiffend());
                v2.add(b.getJpc());
                v2.add(b.getJpeg());
                v2.add(b.getOCRed());
                int count1=m1.getRowCount();
                m1.addRow(v2);
                m1.fireTableRowsInserted(count1+1, count1+1);
                }
                else
                {
                    System.out.println("Book already added");
                }

           }
             
          
          catch (Exception e)
    {
        System.out.println(e);
        e.printStackTrace();
    }
           }
            else if(option ==3)
            {
            int ind=Path2.lastIndexOf("\\");
                System.out.println("String is "+ Path2 +"index of \\ is "+ ind);
                ind=ind+1;
                String temp=Path2.substring(ind, Path2.length());
                Book b = entityManager.find(Book.class,temp);
                Vector v2 = new Vector();
                
                v2.add(b.getBarcode());
                v2.add(b.getTiffend());
                v2.add(b.getJpc());
                v2.add(b.getJpeg());
                v2.add(b.getOCRed());
                int count1=m1.getRowCount();
                m1.addRow(v2);
                m1.fireTableRowsInserted(count1+1, count1+1);


            }
            else if (option ==4)
            {
                int barind=Path2.lastIndexOf("\\");
                Path2=Path2.substring(barind+1, Path2.length());
                System.out.println("barcode "+Path2);
                Book b = entityManager.find(Book.class,Path2);
                Vector v2 = new Vector();
                v2.add(b.getBarcode());
                v2.add(b.getTiffend());
                v2.add(b.getJpc());
                v2.add(b.getJpeg());
                v2.add(b.getOCRed());
                int count1=m1.getRowCount();
                m1.addRow(v2);
                m1.fireTableRowsInserted(count1+1, count1+1);
            }



        }

    private void New_ProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_New_ProjectActionPerformed
        // TODO add your handling code here:
        File file=null;
        DataStore d = null;
        try
        {
        int returnVal = jFileChooser1.showOpenDialog(menuBar);
    if (returnVal == JFileChooser.APPROVE_OPTION  )
    {
        //get the new Project's Parent folder path and store it in a file object
        file = jFileChooser1.getSelectedFile();
        //Instantiate a server object to start monitoring new Project's Parent
        //New Project option is identified by a value 1 passed as second argument
        //to server's constructor
        //count is the static variable that keeps track of the number of current open projects
        //the fourth argument is not required in this case as it represents a new project
        boolean dpi;
        int opt = JOptionPane.showInternalConfirmDialog(menuBar,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;
        d = new DataStore();
        s=new Server(file,1,count,this,0,d,dpi);
         count++;
         data.add(d);
         //create the server obect thread and then start the thread
         Thread t = new Thread(s);
         t.start();
         System.out.println(file.getAbsolutePath());
    }
        }
         catch(Exception e)
         {
             System.out.println("filechooser Exception"+e);
         }
      
        
        d.setPath(file.getAbsolutePath());
        data.add(d);
        
        System.out.println("Server object "+ s +" created in new");
        


    }//GEN-LAST:event_New_ProjectActionPerformed

    public void recieveindex(int first,int last)
    {
        this.first=first;
        this.last=last;
    }
    public void update_tiff(String path)
    {
      int ind=path.lastIndexOf("\\");

      Project p=null;
       
       path=path.substring(ind+1, path.length());
     System.out.println("in update_tiff "+path);
     try{
     entityManager.getTransaction().begin();
      
      Book b=entityManager.find(Book.class, path);
     // p=b.getProjectid();
      //int id=p.getProjectid();

      
          b.setTiffend(new Date());
          entityManager.merge(b);
          entityManager.getTransaction().commit();
     }
     catch(Exception e)
     {
         System.out.println("in uodate_tiff Project p: "+"Exception is: "+e);
         e.printStackTrace();
     }
          refresh(path);
      }

    
    void refresh(String path)
    {
       int rows = m1.getRowCount();
       int col = 0;
       System.out.println("number of rows:"+ rows);
try
{
    for(int i=0;i<rows;i++)
        {
            String value = (String)(m1.getValueAt(i, col));
     //If the item is already in the table
            if(value.equals(path))
     {
           Book b=entityManager.find(Book.class,path);
           System.out.println("value at matched location: " +m1.getValueAt(i, col));
           m1.setValueAt(b.getTiffend(), i, col+1);
     }
    }
}
     catch(Exception e)
     {
         System.out.println("Refresh Exception :"+e);
         e.printStackTrace();
     }// int f=jTable2.getSelectedRow();
       // int l=jTable2.getSelectedColumn();

       /* int c=0;
        Query q;
        Vector v = null;
        //clear table1
        while(m1.getRowCount()>0)
            m1.removeRow(0);
        //if (f!=-1)
        //{
          //  id=(Integer)jTable2.getModel().getValueAt(f, l);
           

        //}
        try
        {
        CriteriaBuilder qb=entityManager.getCriteriaBuilder();
        CriteriaQuery <Book> crit =qb.createQuery(Book.class);
        Root <Book> candidateRoot= crit.from(Book.class);
        candidateRoot.alias("b");
        crit.select(candidateRoot);
        Path namefield = candidateRoot.get("barcode");
        crit.select(namefield);
       Metamodel model =entityManager.getMetamodel();
       EntityType<Book> book_ = model.entity(Book.class);
       Attribute proattr=book_.getAttribute("Project_id");



        //Predicate idEquals = qb.equal(namefield, path);
        //crit.where(idEquals);

        System.out.println(qb.toString());
        q=entityManager.createQuery(crit);


        
            entityManager.getTransaction().begin();

           // q= entityManager.createQuery("select b.barcode,b.Tiff_end,b.JPC,b.JPEG,b.OCRed from book b where b.Project_id= "+proj_id);
            //entityManager.find(Book.class,proj_id);
            Iterator i=q.getResultList().iterator();
            entityManager.getTransaction().commit();

            while (i.hasNext())
            {
                Book b=(Book)i.next();
                System.out.println("refresh Barcode: "+ b.getBarcode());
                v = new Vector();
                String bar=b.getBarcode();
                v.add(bar);
                Date d=b.getTiffend();
                 System.out.println("refresh Barcode: "+d);
                v.add(d);
               // boolean jpc=b.getJpc();
                 //System.out.println("refresh Barcode: "+ jpc);
                //v.add(jpc);
                //boolean jpeg=b.getJpeg();
                 //System.out.println("refresh Barcode: "+ jpeg);
                //v.add(jpeg);
                //boolean ocr=b.getOCRed();
                 //System.out.println("refresh Barcode: "+ ocr);
                //v.add(ocr);

                m1.addRow(v);
               c++;

            }
            
            System.out.println(v.size());
            m1.fireTableRowsInserted(v.size()-1,v.size()-1);
        }
            catch(Exception e)
            {
                System.out.println("firetable exception "+e);
            }*/
        
    }

    private void Open_ProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Open_ProjectActionPerformed
        // TODO add your handling code here:
        int id=0;
        File file=null;
        try
        {


        //int returnVal = jFileChooser1.showOpenDialog(menuBar);
    //if (returnVal == JFileChooser.APPROVE_OPTION  ) {
      //   file = jFileChooser1.getSelectedFile();
        // System.out.println(file.getAbsolutePath());
    //}
        }
         catch(Exception e)
         {
             System.out.println("filechooser Exception"+e);
         }
        
        Container p=jFrame1.getContentPane();
        Dimension d =new Dimension(484,382);
        p.setPreferredSize(d);
        //DefaultListModel m1=new DefaultListModel();

            l1.removeAllElements();
            l2.removeAllElements();
        jFrame1.pack();
        jFrame1.setVisible(true) ;
        jFrame1.validate();
        jFrame1.setLocationRelativeTo(null);
        jButton1.setText(">");
        jButton2.setText(">>");
        jButton3.setText("<");
        jButton4.setText("<<");
        jButton5.setText("Open");
        jButton6.setText("Cancel");
Query q=entityManager.createNamedQuery("Project.findAll");
        Iterator result = q.getResultList().iterator();
           while(result.hasNext())
                {
                    Project proj = (Project) result.next();
                    l1.addElement(proj.getProjectname());
                }
                
                
        
      
        //jFrame1.show();
       /* first=jTable2.getSelectedRow();
        last=jTable2.getSelectedColumn();
        check the values to see if they are right
        if (first!=-1)
        {
            Vector v=(Vector)jTable2.getModel().getValueAt(first, last);
            Iterator i=(Iterator)v.iterator();
            id=(Integer)i.next();

        }
        //jTable2.get
        Project p=entityManager.find(Project.class,id);
        file=new File(p.getProjectPath());
        System.out.println(file.getAbsolutePath());
        s=new Server(file,2,count,this,id);
        data.add(new ArrayList<Object>());
        data.get(count).add(s);
        System.out.println(data.get(count).get(count));
        count++;*/
    }//GEN-LAST:event_Open_ProjectActionPerformed

    private void Close_ProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Close_ProjectActionPerformed
        // TODO add your handling code here:
        File file=null;
        String pro_id=null;
         int index=0,i;
         while(pro_id==null)
         {
         try
         {
         pro_id=JOptionPane.showInputDialog("enter Project ID");
        
         }
         catch(NumberFormatException e)
         {
             JOptionPane.showMessageDialog(mainPanel, "You must enter an integer value");
            
         }
         }
         index=Integer.parseInt(pro_id);
         System.out.println("project_id: "+ index);

         String p;
         Process process=null;
        //try
        //{
        //int returnVal = jFileChooser1.showOpenDialog(menuBar);
    //if (returnVal == JFileChooser.APPROVE_OPTION  ) {
      //   file = jFileChooser1.getSelectedFile();
        // System.out.println(file.getAbsolutePath());
    //}
        
       //  catch(Exception e)
         //{
           //  System.out.println("filechooser Exception"+e);
         //}
//         first=jTable2.getSelectedRow();
  //      last=jTable2.getSelectedColumn();
    //    if (first!=-1)
      //  {
        //    Vector v=(Vector)jTable2.getModel().getValueAt(first, last);
          //  Iterator it=(Iterator)v.iterator();
            //index=(Integer)it.next();


        //System.out.println("First "+first+ "Last"+last+ "index"+index);
        Project proj=entityManager.find(Project.class, index);
        file=new File(proj.getProjectPath());
        System.out.println("Project path: "+file.getAbsolutePath());
        for(i=0;i<m2.getRowCount();i++)
        {
            int temp=(Integer)m2.getValueAt(i, 0);
            if (index==temp)
            {
                first=i;
                System.out.println("found row "+i);
                m2.removeRow(first);
                   m2.fireTableRowsDeleted(first, first) ;
            break;
            }

        }

         for(i=0;i<data.size();i++)
           {
               p=(String)data.get(i).getPath();
               System.out.println("Path is "+p+"Process is "+data.get(i).getProcess()+"Server is "+data.get(i).getServer());
               if(p.equalsIgnoreCase(file.getAbsolutePath() ) )
               {
                   Process p1=data.get(i).getProcess();
                   p1.destroy();
                   p1=null;
                   p=null;
                   Server s1=data.get(i).getServer();
                   s1=null;
                   data.remove(i);
                   int j=m1.getRowCount();
                   while(m1.getRowCount()>0)
                       m1.removeRow(0);
                   m1.fireTableRowsDeleted(0, j);

                   break;
                   //m2.removeRow(first);
                   //m2.fireTableRowsDeleted(first, first) ;
               }
               
               
                   

               

         }

           


    }//GEN-LAST:event_Close_ProjectActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Object[] a=jList1.getSelectedValues();
        for(int i=0;i<a.length;i++)
        {
            l2.addElement(a[i]);
            l1.removeElement(a[i]);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:

        int size=l1.getSize();
              for (int i=0;i<size;i++)
        {
            l2.addElement(l1.getElementAt(i) );
        }
        l1.removeAllElements();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
       Object[] a=jList2.getSelectedValues();
        for(int i=0;i<a.length;i++)
        {
            l1.addElement(a[i]);
            l2.removeElement(a[i]);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        int size=l2.getSize();
              for (int i=0;i<size;i++)
        {
            l1.addElement(l2.getElementAt(i) );
        }
        l2.removeAllElements();

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        jFrame1.hide();;
        for (int i=0;i<l2.size();i++)
        {
            String pname=(String)l2.getElementAt(i);
            Query q=entityManager.createNamedQuery("Project.findByProjectname");
            q.setParameter("projectname",pname );
            Iterator result = q.getResultList().iterator();
                if(result.hasNext())
                {
                    Project pro = (Project) result.next();
                    File f1= new File(pro.getProjectPath());
                    count++;
                    DataStore d1= new DataStore();
                    boolean dpi;
        int opt = JOptionPane.showInternalConfirmDialog(menuBar,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;
                    Server s1=new Server(f1,2,count,this,pro.getProjectid(),d1,dpi);
                    Thread t = new Thread(s1);
                    t.start();
                    d1.setPath(pro.getProjectPath());
                    //d1.setPath(pro.getProjectPath());

                    data.add(d1);
                    Vector v= new Vector();
                    v.add(pro.getProjectid());
                    v.add(pro.getProjectname());
                    m2.addRow(v);
                    if(i==0)
                    {

                        Query q1=entityManager.createNamedQuery("Book.findMatch");
                        q1.setParameter("projId",pro );
                        Iterator result1 = q1.getResultList().iterator();
                        int j=0;
                        while(result1.hasNext())
                        {
                            Vector v1 = new Vector();
                            Book b=(Book)result1.next();
                        //    System.out.println(b.getProjectname());
                            System.out.println(b.getBarcode());
                            System.out.println(b.getTiffend());
                            System.out.println(b.getJpc());
                            System.out.println(b.getJpeg());
                            System.out.println(b.getOCRed());
                            v1.add(b.getBarcode());
                            v1.add(b.getTiffend());
                            v1.add(b.getJpc());
                            v1.add(b.getJpeg());
                            v1.add(b.getOCRed());
                            m1.addRow(v1);
                            j++;
                        }
                        m1.fireTableRowsInserted(0,j-1 );

                    }
                    
                }
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        jFrame1.hide();
    }//GEN-LAST:event_jButton6ActionPerformed

    private void Redo_BookActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Redo_BookActionPerformed
        // TODO add your handling code here:
        boolean dpi;
        int opt = JOptionPane.showInternalConfirmDialog(menuBar,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;
        int col=jTable1.getSelectedColumn();
        int row=jTable1.getSelectedRow();
        String bar = (String)jTable1.getValueAt(row, col);
        System.out.println(bar);
        Book b=entityManager.find(Book.class, bar);
        Project p=b.getProjectid();
        String p1=b.getPath();
        String p2=b.getBarcode();
        File f=new File(p1);
        entityManager.getTransaction().begin();
        
        
        entityManager.remove(b);
        entityManager.flush();
        entityManager.getTransaction().commit();
        
        m1.removeRow(row);
        m1.fireTableRowsDeleted(row, row);
        //Have to figure out whther a folder gets deleted and recreated from Greg
        String Path = p.getProjectPath();
        count++;
        DataStore d = new DataStore();
        Server s1 = new Server(f,3,count,this,p.getProjectid(),d,dpi);
        Thread t = new Thread(s1);
        t.start();
        d.setPath(Path);
        System.out.println("book is "+ p2);
                Book b1 = new Book();
        b1.setBarcode(p2);
        b1.setProjectid(p);
        b1.setPath(p1);
        Date d1= new Date();
                b1.setDatecreated(d1);
        entityManager.getTransaction().begin();
        entityManager.persist(b1);
        entityManager.flush();
        entityManager.getTransaction().commit();
        
        //Query q=entityManager.createNamedQuery("Book.deleteBook");
        //q.setParameter("barcode", bar);

    }//GEN-LAST:event_Redo_BookActionPerformed

    private void FoldoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FoldoutActionPerformed
        // TODO add your handling code here:
        File f=null;
        int returnVal=0;
        ThreadPool pool = new ThreadPool(8);
        returnVal = jFileChooser1.showOpenDialog(menuBar);
        if (returnVal == JFileChooser.APPROVE_OPTION  )
        {
        //get the new Project's Parent folder path and store it in a file object
        f = jFileChooser1.getSelectedFile();
        }
        boolean dpi;
        int opt = JOptionPane.showInternalConfirmDialog(Foldout,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;
        String fpath = f.getAbsolutePath();
        String bcode,projectname;
        int ind = fpath.lastIndexOf("\\");
        String temp= fpath.substring(0,ind);
        ind = temp.lastIndexOf("\\");
        bcode = temp.substring(ind+1,temp.length());
        System.out.println(f.getAbsolutePath());
        System.out.println(temp);
        System.out.println(bcode);
        ind = temp.lastIndexOf("\\");
        temp =temp.substring(0,ind);
        System.out.println(temp);
        ind = temp.lastIndexOf("\\");
        projectname = temp.substring(ind+1, temp.length());
        System.out.println("projectname : "+projectname+" barcode: "+bcode);
        

        String[] Child = f.list();
        try
        {
            for (int i = 0; i < Child.length;i++)
            {
                if(Child[i].endsWith("cr2"))
                    {
                     File   file = new File(f.getAbsolutePath()+"\\"+Child[i]);
                    System.out.println(file.getAbsolutePath());


                    pool.assign(new TestWorkerThread(file,dpi));

                    }

            }
                 pool.complete();
                 /*File pfile = new File("d:\\backup\\"+projectname);
                 if(!pfile.exists())
                     pfile.mkdir();
                 File bfile = new File("d:\\backup\\"+projectname+"\\"+bcode);
                 if (!bfile.exists())
                     bfile.mkdir();
                 File ifile = new File ("d:\\backup\\"+projectname+"\\"+bcode+"\\RAWJPEG2K");
                 File mfile = new File("d:\\backup\\"+projectname+"\\"+bcode+"\\RAWJPEG2K\\IMAGES");
                 if(!mfile.exists())
                     mfile.mkdir();
                 ind = fpath.lastIndexOf("\\");
                 temp= fpath.substring(0,ind);
                 File src = new File (temp+"\\IMAGES");
                copyDirectory(f,mfile);*/
                // copyDirectory(src,mfile);
        }
        
        catch (Exception e)
        {
            System.out.println(e);
        }
    }//GEN-LAST:event_FoldoutActionPerformed
public boolean moveDirectory(File src,File dst)
    {
           boolean success = src.renameTo(new File(dst, src.getName()));
           return success;



    }
public boolean delete(File resource) throws IOException {
		if(resource.isDirectory()) {
			File[] childFiles = resource.listFiles();
			for(File child : childFiles) {
				delete(child);
			}

		}
		return resource.delete();

	}


    void copyDirectory(File srcPath, File dstPath)
                               throws IOException{

  if (srcPath.isDirectory()){

      if (!dstPath.exists()){

        dstPath.mkdir();

     }


      System.out.println("dsetination path is "+dstPath.getAbsolutePath());
     String files[] = srcPath.list();

    for(int i = 0; i < files.length; i++){


        if(files[i].endsWith("tiff") || files[i].endsWith("jpg"))
        {
            continue;
        }
           System.out.println("file in "+ srcPath.getAbsolutePath()+"is"+files[i]);

        copyDirectory(new File(srcPath, files[i]),
                     new File(dstPath, files[i]));


      }

    }

   else{

      if(!srcPath.exists()){

        System.out.println("File or directory does not exist.");

       System.exit(0);

      }

else

      {

       InputStream in = new FileInputStream(srcPath);
       OutputStream out = new FileOutputStream(dstPath);
                     // Transfer bytes from in to out

            byte[] buf = new byte[1024];

              int len;

           while ((len = in.read(buf)) > 0) {

          out.write(buf, 0, len);

        }

       in.close();

           out.close();

      }

   }



    }
        private void Redo_Book_PagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Redo_Book_PagesActionPerformed
            // TODO add your handling code here:
            boolean dpi;
        int opt = JOptionPane.showInternalConfirmDialog(menuBar,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;
            int col=jTable1.getSelectedColumn();
        int row=jTable1.getSelectedRow();

        String bar = (String)jTable1.getValueAt(row, col);
        m1.removeRow(row);
        m1.fireTableRowsDeleted(row, row);
        System.out.println(bar);
        Book b=entityManager.find(Book.class, bar);
        Project proj=b.getProjectid();
        int pro_id=proj.getProjectid();
        String book_path=b.getPath();

        File f1= new File(book_path);
                    count++;
                    DataStore d1= new DataStore();
                    Server s1=new Server(f1,4,count,this,pro_id,d1,dpi);
                    Thread t = new Thread(s1);
                    t.start();
                    d1.setPath(book_path);


        }//GEN-LAST:event_Redo_Book_PagesActionPerformed

        private void changeDpiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeDpiActionPerformed
            // TODO add your handling code here:
            File f=null;
        int returnVal=0;
        ThreadPool pool = new ThreadPool(8);
        returnVal = jFileChooser1.showOpenDialog(menuBar);
        if (returnVal == JFileChooser.APPROVE_OPTION  )
        {
        //get the new Project's Parent folder path and store it in a file object
        f = jFileChooser1.getSelectedFile();
        }
        boolean dpi;
        int opt = JOptionPane.showInternalConfirmDialog(menuBar,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;


        String[] Child = f.list();
        try
        {
            for (int i = 0; i < Child.length;i++)
            {
                if(Child[i].endsWith("tiff"))
                    {
                     File   file = new File(f.getAbsolutePath()+"\\"+Child[i]);
                    System.out.println(file.getAbsolutePath());


                    pool.assign(new TestWorkerThread(file,dpi));

                    }

            }
                 pool.complete();
            }
        catch (Exception e)
        {
            System.out.println(e);
        }

        }//GEN-LAST:event_changeDpiActionPerformed

        private void Manual_ConversionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Manual_ConversionActionPerformed
            // TODO add your handling code here:

            ThreadPool pool= new ThreadPool(8);
            File f=null;
            int returnVal=0;
            returnVal = jFileChooser1.showOpenDialog(menuBar);
        if (returnVal == JFileChooser.APPROVE_OPTION  )
        {
        //get the new Project's Parent folder path and store it in a file object
        f = jFileChooser1.getSelectedFile();

            boolean dpi;
            int option = JOptionPane.showInternalConfirmDialog(menuBar,
"Is the File Type JPEG?", "Please select Yes or No",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(option==JOptionPane.YES_OPTION)
            {
        int opt = JOptionPane.showInternalConfirmDialog(menuBar,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;
        File[] List= f.listFiles();
        String Project_id = f.getName();
        Project p = new Project();
        p.setProjectname(Project_id);
        p.setProjectPath(f.getAbsolutePath());
        Date d= new Date();
        p.setProjectdate(d);
        entityManager.getTransaction().begin();
        entityManager.persist(p);
        entityManager.getTransaction().commit();
        int proj_id=0;
        Project pro=null;
      Query query = entityManager.createNamedQuery("Project.findByProjectname");
                query.setParameter("projectname", Project_id);
                Iterator result = query.getResultList().iterator();
                if(result.hasNext())
                {
                    pro = (Project) result.next();
                    proj_id=pro.getProjectid();
                }
        for(int i = 0; i<List.length;i++)
        {
            String Barcode = List[i].getName();
            Book b  = new Book();
            b.setBarcode(Barcode);
            b.setProjectid(pro);
            b.setPath(List[i].getAbsolutePath());
            Date d1 = new Date();
            b.setDatecreated(d1);
            b.setTiffstart(d1);
            File Images= new File(List[i].getAbsolutePath()+"\\IMAGES");
            String[] Children = Images.list();
             boolean bres=false,mres=false,pres=false;;
                File pfile = new File("d:\\backup\\"+Project_id);
                if(!pfile.exists())
                    pres=pfile.mkdir();

                     File bfile = new File("d:\\backup\\"+Project_id+"\\"+Barcode);
                     if(!bfile.exists())//create the barcode folder if it doesnt exist
                     {
                     bres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode).mkdir() );
                     }
                     File rfile = new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K");
                     if(!rfile.exists())
                     {
                    boolean rres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K").mkdir() );
                     }
                     File ifile = new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES");
                     if(!rfile.exists())
                     {
                    boolean rres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES").mkdir() );
                     }
                     try
                     {
                         File src = new File(List[i].getAbsolutePath()+"\\IMAGES\\");
                         File dst = new File ("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES");

                         //copyDirectory(src,dst);
                        //boolean test=new File(List[i].getAbsolutePath()+"\\temp").mkdir();
                            Children=src.list();
            for(int j =0;j<Children.length;j++)
            {
                if (Children[j].endsWith("cr2"))
                {
                    File file = new File(List[i].getAbsolutePath()+"\\IMAGES\\"+Children[j]);
                    copyfile(file.getAbsolutePath(),"d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES\\"+Children[j]);
                    //file.delete();
                    //System.out.println(file.getAbsolutePath()+"is deleted");

                    //pool.assign(new TestWorkerThread1(file,dpi));
                }
            }
               // pool.complete();
            for(int j =0;j<Children.length;j++)
            {
                if (Children[j].endsWith("cr2"))
                {
                    File file = new File(List[i].getAbsolutePath()+"\\IMAGES\\"+Children[j]);
                    //System.out.println(file.getAbsolutePath());
                    //copyfile(file.getAbsolutePath(),"d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES\\"+Children[j]);
                    file.delete();
                    System.out.println(List[i].getAbsolutePath()+"\\IMAGES\\"+Children[j]+"is deleted");
                    //pool.assign(new TestWorkerThread1(file,dpi));
                }
            }
                //boolean success=delete(src);
                //File temp =new File(List[i].getAbsolutePath()+"\\temp");
                //temp.renameTo(src);
                d1=new Date();
                b.setTiffend(d1);
                entityManager.getTransaction().begin();
                entityManager.persist(b);
                entityManager.getTransaction().commit();
              }
                     catch(Exception e)
                     {
                         System.out.println("Exception in Manual Conversion "+e);
                         e.printStackTrace();
                     }
            
            }
            }
 else
            {
                   int opt = JOptionPane.showInternalConfirmDialog(menuBar,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;
        File[] List= f.listFiles();
        String Project_id = f.getName();
        Project p = new Project();
        p.setProjectname(Project_id);
        p.setProjectPath(f.getAbsolutePath());
        Date d= new Date();
        p.setProjectdate(d);
        entityManager.getTransaction().begin();
        entityManager.persist(p);
        entityManager.getTransaction().commit();
        int proj_id=0;
        Project pro=null;
      Query query = entityManager.createNamedQuery("Project.findByProjectname");
                query.setParameter("projectname", Project_id);
                Iterator result = query.getResultList().iterator();
                if(result.hasNext())
                {
                    pro = (Project) result.next();
                    proj_id=pro.getProjectid();
                }
        for(int i = 0; i<List.length;i++)
        {
            String Barcode = List[i].getName();
            Book b  = new Book();
            b.setBarcode(Barcode);
            b.setProjectid(pro);
            b.setPath(List[i].getAbsolutePath());
            Date d1 = new Date();
            b.setDatecreated(d1);
            b.setTiffstart(d1);
            File Images= new File(List[i].getAbsolutePath()+"\\IMAGES");
            String[] Children = Images.list();
             boolean bres=false,mres=false,pres=false;;
                File pfile = new File("d:\\backup\\"+Project_id);
                if(!pfile.exists())
                    pres=pfile.mkdir();

                     File bfile = new File("d:\\backup\\"+Project_id+"\\"+Barcode);
                     if(!bfile.exists())//create the barcode folder if it doesnt exist
                     {
                     bres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode).mkdir() );
                     }
                     File rfile = new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K");
                     if(!rfile.exists())
                     {
                    boolean rres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K").mkdir() );
                     }
                     try
                     {
                         File src = new File(List[i].getAbsolutePath()+"\\IMAGES\\");
                         File dst = new File ("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES");

                         copyDirectory(src,dst);
                        boolean test=new File(List[i].getAbsolutePath()+"\\temp").mkdir();
                            Children=dst.list();
            for(int j =0;j<Children.length;j++)
            {
                if (Children[j].endsWith("cr2"))
                {
                    File file = new File(List[i].getAbsolutePath()+"\\IMAGES\\"+Children[j]);
                    System.out.println(file.getAbsolutePath());


                    pool.assign(new TestWorkerThread1(file,dpi));
                }
            }
                pool.complete();

                boolean success=delete(src);
                File temp =new File(List[i].getAbsolutePath()+"\\temp");
                temp.renameTo(src);
                d1=new Date();
                b.setTiffend(d1);
                entityManager.getTransaction().begin();
                entityManager.persist(b);
                entityManager.getTransaction().commit();
              }
                     catch(Exception e)
                     {
                         System.out.println("Exception in Manual Conversion "+e);
                         e.printStackTrace();
                     }

            }
            }


        }

        }//GEN-LAST:event_Manual_ConversionActionPerformed

        private  void copyfile(String srFile, String dtFile){
    try{
      File f1 = new File(srFile);
      File f2 = new File(dtFile);
      InputStream in = new FileInputStream(f1);

      //For Append the file.
//      OutputStream out = new FileOutputStream(f2,true);

      //For Overwrite the file.
      OutputStream out = new FileOutputStream(f2);

      byte[] buf = new byte[1024];
      int len;
      while ((len = in.read(buf)) > 0){
        out.write(buf, 0, len);
      }
      in.close();
      out.close();
      System.out.println("File copied.");
    }
    catch(FileNotFoundException ex){
      System.out.println(ex.getMessage() + " in the specified directory.");
      System.exit(0);
    }
    catch(IOException e){
      System.out.println(e.getMessage());
    }
  }

        private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
            // TODO add your handling code here:

            File pFile=null,bFile=null;
            DefaultTableModel model = (DefaultTableModel)jTable3.getModel();
            JFileChooser fc = new JFileChooser();
            fc.setFileSelectionMode(fc.DIRECTORIES_ONLY);
            fc.setDialogTitle("Project Path");
            int val=fc.showOpenDialog(jFrame2);
            if(val==fc.APPROVE_OPTION)
             pFile=fc.getSelectedFile();
            fc.setDialogTitle("Barcode Path");
            
            val=fc.showOpenDialog(jFrame2);
            if(val==fc.APPROVE_OPTION)
                bFile=fc.getSelectedFile();
            int index=pFile.getAbsolutePath().lastIndexOf("\\");


            String p_name=pFile.getAbsolutePath().substring(index+1);
            index=bFile.getAbsolutePath().lastIndexOf("\\");
            String barcode=bFile.getAbsolutePath().substring(index+1);
            Vector v = new Vector();

            v.add(pFile.getAbsolutePath());
            v.add(bFile.getAbsolutePath());


            model.addRow(v);


        }//GEN-LAST:event_jButton7ActionPerformed

        private void update_databaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_databaseActionPerformed
            // TODO add your handling code here:
            EntityManager em=javax.persistence.Persistence.createEntityManagerFactory("DesktopApplication2PU").createEntityManager();
            int count=jTable3.getRowCount();
            for ( int i =0;i<count;i++)
            {
                Book book =null;
                String project_path=(String)jTable3.getModel().getValueAt(i,0);
                System.out.println("Project Path:"+project_path);
                String Barcode_path=(String )jTable3.getModel().getValueAt(i, 1);
                System.out.println("Barcode Path:"+Barcode_path);
                int index=project_path.lastIndexOf("\\");
                String pname=project_path.substring(index+1);
                System.out.println(pname);
                index=Barcode_path.lastIndexOf("\\");
                String barcode=Barcode_path.substring(index+1);
                System.out.println(barcode);
                try
                {
                Project proj=null;
                Query query = entityManager.createNamedQuery("Book.findByBarcode");
                query.setParameter("barcode", barcode);
                Iterator result = query.getResultList().iterator();
                if(result.hasNext())
                {
                    book = (Book) result.next();
                    //proj_id=pro.getProjectid();
                }
                query = entityManager.createNamedQuery("Project.findByProjectname");
                query.setParameter("projectname", pname);
                result = query.getResultList().iterator();
                if(result.hasNext())
                {
                    proj = (Project) result.next();
                    //proj_id=pro.getProjectid();
                }
                if(book==null)
                if(proj==null)
                {
                Project p=new Project();
                p.setProjectPath(project_path);
                p.setProjectname(pname);
                p.setProjectdate(new Date());
                em.getTransaction().begin();
                em.persist(p);
                em.getTransaction().commit();
                Book b = new Book();
                b.setBarcode(barcode);
                b.setDatecreated(new Date());
                b.setPath(Barcode_path);
                b.setTiffstart(new Date());
                b.setTiffend(new Date());
                b.setProjectid(p);
                em.getTransaction().begin();
                em.persist(b);
                em.getTransaction().commit();
                }
                else
                {
                    Book b = new Book();
                b.setBarcode(barcode);
                b.setDatecreated(new Date());
                b.setPath(Barcode_path);
                b.setTiffstart(new Date());
                b.setTiffend(new Date());
                b.setProjectid(proj);
                em.getTransaction().begin();
                em.persist(b);
                em.getTransaction().commit();

                }
                else
                    JOptionPane.showMessageDialog(jFrame2, "This book is already in the database");
                }
                catch(Exception e)
                {
                    System.out.println(e);
                    e.printStackTrace();
                }
                

            }
        }//GEN-LAST:event_update_databaseActionPerformed

        private void update_DatabaseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_DatabaseActionPerformed
            // TODO add your handling code here:
            jFrame2.pack();
        jFrame2.setVisible(true) ;
        jFrame2.validate();
        }//GEN-LAST:event_update_DatabaseActionPerformed

        private void remaining_BooksActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_remaining_BooksActionPerformed
            // TODO add your handling code here:
            ThreadPool pool= new ThreadPool(8);
            File f=null;
            int returnVal = jFileChooser1.showOpenDialog(menuBar);
        if (returnVal == JFileChooser.APPROVE_OPTION  )
        {
        //get the new Project's Parent folder path and store it in a file object
        f = jFileChooser1.getSelectedFile();

            boolean dpi;
            int option = JOptionPane.showInternalConfirmDialog(menuBar,
"Is the File type JPEG?", "Please select Yes or No",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
            if(option==JOptionPane.NO_OPTION)
            {
        int opt = JOptionPane.showInternalConfirmDialog(menuBar,
"please choose one", "change to 400DPI",
JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        if(opt == JOptionPane.YES_OPTION)
            dpi = true;
        else
            dpi = false;
        File[] List= f.listFiles();
        String Project_id = f.getName();
        Project p = new Project();
        p.setProjectname(Project_id);
        p.setProjectPath(f.getAbsolutePath());
        Date d= new Date();
        p.setProjectdate(d);
        int proj_id=0;
        Project pro=null;
      Query query = entityManager.createNamedQuery("Project.findByProjectname");
                query.setParameter("projectname", Project_id);
                Iterator result = query.getResultList().iterator();
                if(result.hasNext())
                {
                    pro = (Project) result.next();
                    proj_id=pro.getProjectid();
                }
                System.out.println("Project name "+proj_id);

        for(int i = 0; i<List.length;i++)
        {
            System.out.println(f.getAbsolutePath()+"\\"+List[i].getName()+"\\IMAGES");
            File test1= new File(f.getAbsolutePath()+"\\"+List[i].getName()+"\\IMAGES");
            File[] child=test1.listFiles();
            boolean found=false;
            for(int j=0;j<child.length;j++)
            {
                if(child[j].getName().endsWith("tif"))
                {
                    found=true;
                    break;
                }

            }
            if(found==true)
                continue;
            String Barcode = List[i].getName();
            Book b  = new Book();
            b.setBarcode(Barcode);
            b.setProjectid(pro);
            b.setPath(List[i].getAbsolutePath());
            Date d1 = new Date();
            b.setDatecreated(d1);
            b.setTiffstart(d1);
            File Images= new File(List[i].getAbsolutePath()+"\\IMAGES");
            String[] Children = Images.list();
             boolean bres=false,mres=false,pres=false;;
                File pfile = new File("d:\\backup\\"+Project_id);
                if(!pfile.exists())
                    pres=pfile.mkdir();

                     File bfile = new File("d:\\backup\\"+Project_id+"\\"+Barcode);
                     if(!bfile.exists())//create the barcode folder if it doesnt exist
                     {
                     bres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode).mkdir() );
                     }
                     File rfile = new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K");
                     if(!rfile.exists())
                     {
                    boolean rres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K").mkdir() );
                     }
                     try
                     {
                         File src = new File(List[i].getAbsolutePath()+"\\IMAGES\\");
                         File dst = new File ("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES");

                         copyDirectory(src,dst);
                        boolean test=new File(List[i].getAbsolutePath()+"\\temp").mkdir();
                            //Children=dst.list();
            for(int j =0;j<Children.length;j++)
            {
                if (Children[j].endsWith("cr2"))
                {
                    File file = new File(List[i].getAbsolutePath()+"\\IMAGES\\"+Children[j]);
                    System.out.println(file.getAbsolutePath());


                    pool.assign(new TestWorkerThread1(file,dpi));
                }
            }
                pool.complete();

                boolean success=delete(src);
                File temp =new File(List[i].getAbsolutePath()+"\\temp");
                temp.renameTo(src);
                d1=new Date();
                b.setTiffend(d1);
                entityManager.getTransaction().begin();
                entityManager.persist(b);
                entityManager.getTransaction().commit();
              }
                     catch(Exception e)
                     {
                         System.out.println("Exception in Manual Conversion "+e);
                         e.printStackTrace();
                     }


        }
            }
 else
            {
                 File[] List= f.listFiles();
        String Project_id = f.getName();
        Project p = new Project();
        p.setProjectname(Project_id);
        p.setProjectPath(f.getAbsolutePath());
        Date d= new Date();
        p.setProjectdate(d);
        int proj_id=0;
        Project pro=null;
      Query query = entityManager.createNamedQuery("Project.findByProjectname");
                query.setParameter("projectname", Project_id);
                Iterator result = query.getResultList().iterator();
                if(result.hasNext())
                {
                    pro = (Project) result.next();
                    proj_id=pro.getProjectid();
                }
                System.out.println("Project name "+proj_id);

        for(int i = 0; i<List.length;i++)
        {
            /*System.out.println(f.getAbsolutePath()+"\\"+List[i].getName()+"\\IMAGES");
            File test1= new File(f.getAbsolutePath()+"\\"+List[i].getName()+"\\IMAGES");
            File[] child=test1.listFiles();
            boolean found=false;
            for(int j=0;j<child.length;j++)
            {
                if(child[j].getName().endsWith("tif"))
                {
                    found=true;
                    break;
                }

            }
            if(found==true)
                continue;*/
            String Barcode = List[i].getName();
            Book b  = new Book();
            b.setBarcode(Barcode);
            b.setProjectid(pro);
            b.setPath(List[i].getAbsolutePath());
            Date d1 = new Date();
            b.setDatecreated(d1);
            b.setTiffstart(d1);
            File Images= new File(List[i].getAbsolutePath()+"\\IMAGES");
            String[] Children = Images.list();
             boolean bres=false,mres=false,pres=false;;
                File pfile = new File("d:\\backup\\"+Project_id);
                if(!pfile.exists())
                    pres=pfile.mkdir();

                     File bfile = new File("d:\\backup\\"+Project_id+"\\"+Barcode);
                     if(!bfile.exists())//create the barcode folder if it doesnt exist
                     {
                     bres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode).mkdir() );
                     }
                     File rfile = new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K");
                     if(!rfile.exists())
                     {
                    boolean rres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K").mkdir() );
                     }
                     File ifile = new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES");
                     if(!ifile.exists())
                     {
                    boolean rres=(new File("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES").mkdir() );
                     }
                     try
                     {
                         File src = new File(List[i].getAbsolutePath()+"\\IMAGES\\");
                         File dst = new File ("d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES");

                         //copyDirectory(src,dst);
                        //boolean test=new File(List[i].getAbsolutePath()+"\\temp").mkdir();
                            Children=src.list();
            for(int j =0;j<Children.length;j++)
            {
                if (Children[j].endsWith("cr2"))
                {
                    File file = new File(List[i].getAbsolutePath()+"\\IMAGES\\"+Children[j]);
                    System.out.println(file.getAbsolutePath());
                    copyfile(file.getAbsolutePath(),"d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES\\"+Children[j]);
                    
                    System.out.println(file.getAbsolutePath()+"is deleted");
                    //pool.assign(new TestWorkerThread1(file,dpi));
                }
            }
                //pool.complete();
             for(int j =0;j<Children.length;j++)
            {
                if (Children[j].endsWith("cr2"))
                {
                    File file = new File(List[i].getAbsolutePath()+"\\IMAGES\\"+Children[j]);
                    //System.out.println(file.getAbsolutePath());
                    //copyfile(file.getAbsolutePath(),"d:\\backup\\"+Project_id+"\\"+Barcode+"\\RAWJPEG2K\\IMAGES\\"+Children[j]);
                    file.delete();
                    System.out.println(List[i].getAbsolutePath()+"\\IMAGES\\"+Children[j]+"is deleted");
                    //pool.assign(new TestWorkerThread1(file,dpi));
                }
            }
                //boolean success=delete(src);
                //File temp =new File(List[i].getAbsolutePath()+"\\temp");
                //temp.renameTo(src);
                d1=new Date();
                b.setTiffend(d1);
                entityManager.getTransaction().begin();
                entityManager.persist(b);
                entityManager.getTransaction().commit();
              }
                     catch(Exception e)
                     {
                         System.out.println("Exception in Manual Conversion "+e);
                         e.printStackTrace();
                     }


        }
            }
            }
        }//GEN-LAST:event_remaining_BooksActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Close_Project;
    private javax.persistence.EntityManager DesktopApplication2PUEntityManager;
    private javax.swing.JMenuItem Foldout;
    private javax.swing.JMenuItem Manual_Conversion;
    private javax.swing.JMenuItem New_Project;
    private javax.swing.JMenuItem Open_Project;
    private javax.swing.JMenuItem Redo_Book;
    private javax.swing.JMenuItem Redo_Book_Pages;
    private java.util.List<desktopapplication2.Book> bookList;
    private javax.persistence.Query bookQuery;
    private javax.swing.JMenuItem changeDpi;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JFrame jFrame2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTable jTable3;
    private javax.swing.JPanel mainPanel;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JProgressBar progressBar;
    private javax.swing.JMenuItem remaining_Books;
    private javax.swing.JLabel statusAnimationLabel;
    private javax.swing.JLabel statusMessageLabel;
    private javax.swing.JPanel statusPanel;
    private javax.swing.JMenuItem update_Database;
    private javax.swing.JButton update_database;
    // End of variables declaration//GEN-END:variables
    private ArrayList <DataStore> data = new ArrayList<DataStore>();
    private final Timer messageTimer;
    private final Timer busyIconTimer;
    private final Icon idleIcon;
    private final Icon[] busyIcons = new Icon[15];
    private int busyIconIndex = 0;
    private static int count = 0;
    private ArrayList servers = new ArrayList();
    private Server s;
    private EntityManager entityManager ;
    private JDialog aboutBox;
    private DefaultTableModel m1;
    private DefaultTableModel m2;
    private int first,last;
    private SelectionListener listener;
    private DefaultListModel l1=new DefaultListModel();
    private DefaultListModel l2=new DefaultListModel();
    private boolean open=false;
    

}


class Server implements Runnable{
    ServerSocket s1;
    Socket c;
    String data;
    DataStore d;
    //the first Listening socket gets created at port 5000
    int port=5000;
    //DefaultListModel m1,m2;
    //JList list1,list2;
    File f,file;
    boolean dpi;
    int option,count;
    ThreadPool pool;
    DesktopApplication2View gui;
    int id;

//the Server class is used to communicate with the C# client  
//it gets a message from the C# client whenever a new project folder or a book 
//folder gets created
//It also creates a thread pool for each of the created projects and 
//creates worker threads in thread pool for converting the Raw files into TIFF's
    public Server(File f,int option,int count,DesktopApplication2View gui,int id,DataStore d,boolean dpi)
{
    //f is the file object to be monitored
    this.f=f;
    //count is the number used for creating the port for creating a new listening socket
    //for each new project
    this.count=count;
    //gui is the refence to the DesktopApplication2View used for invoking methods on it
    this.gui=gui;
    //option is the value that indicates whether its a new project ()a value of 1)
    //or opening an already created project (a value of two)
    this.option=option;
    //id is used to store the Project_id for an already created project
    this.id=id;
    this.d=d;
    this.dpi=dpi;

}
    BufferedReader r; // used to read from socket
    PrintWriter w;//used for writing to socket
    String path,path1=null;
    String last;//Path is the path for project and path1 is for a book
    public void run()
    {
        
        Process p=null;
       try{

               int pt=port+count;
               s1=new ServerSocket(pt);
               System.out.println("server Socket created at port: "+pt);
               p =null;
               }
       catch (Exception e)
       {
        System.out.println("main server Thread error"+ e);
        e.printStackTrace() ;
       }
               
        if(option ==1)
        {
        
            //create the thread pool
            pool = new ThreadPool(8);
            //wait for client connection
                    try{
                      // start the C# client
                        System.out.println("creating client app");
      p=Runtime.getRuntime().exec("consoleApplication1 1 " + (port+count));
        System.out.println("created client app");
       //d.setPath(path);
      // System.out.println(d.getPath());
        d.setProcess(p);
        d.setServer(this);

        }
        catch(Exception e)
        {
            System.out.println("failed to launch client"+e);
        }
           try
           {
             c=s1.accept();
             //start the C# client
            System.out.println("client connection created");
             //create the reader for reading from socket
             r= new BufferedReader(new InputStreamReader(c.getInputStream()));
             //create the writer for writing to socket
             w = new PrintWriter(c.getOutputStream());
             //send the parent folder path for ther new project to C# client
             w.println(f.getAbsolutePath());
             w.flush();
             //Wait for the project folder to be created
             //readline would return with the Path to the new project
             path=r.readLine();
             System.out.println("Project Path: "+path);

             //wait for a book folder to be created
             //readline would return with a path for a new book in the project
             //path1=r.readLine();
             
             //System.out.println("Book Path: "+path1);
             //add the recieved project and Path info to both the database and the user interface
             gui.recievepath(this, path,path1,p,1,0);
           }
           catch(Exception e)
           {
               System.out.println(e);
           }
        }
        else if (option ==2)//this is the case for opening an already created project
        {

            System.out.println("in option 2");
            try{
                       //start the C# client
    p=Runtime.getRuntime().exec("consoleApplication1 2 " + (port+count));
   // d.setPath(path);
   // System.out.println(d.getPath());
     d.setProcess(p);
     d.setServer(this);

        }
        catch(Exception e)
        {
            System.out.println("failed to launch client"+e);
        }
               //create the therad pool
               pool = new ThreadPool(8);
               //wait for client connection
              try
              {
               c=s1.accept();
               System.out.println("client connection created");
             //create reader and wirter for the socket
             r= new BufferedReader(new InputStreamReader(c.getInputStream()));
             w = new PrintWriter(c.getOutputStream());
             w.println(f.getAbsolutePath());
             w.flush();
              }
              catch(Exception e)
              {

                  System.out.println(e.getMessage());
                  e.printStackTrace();
              }
             //read the book's name
             //path1=r.readLine();
             //create a file object for the new book
             //File test=new File(path1);
             //path1=test.getAbsolutePath();
             //int index=path1.lastIndexOf("\\");
             //path=path1.substring(0, index);
              //System.out.println("Book Path: "+path1);
             //System.out.println("Project Path: "+path);
             //add the recieved project and Path info to both the database and the user interface
            // gui.recievepath(this,path,path1,p,2,id);
        }
        else if (option ==3)
        {
                   try{
                       //start the C# client
      p=Runtime.getRuntime().exec("consoleApplication1 3 " + (port+count));
     d.setPath(path);
      d=null;

        }
        catch(Exception e)
        {
            System.out.println("failed to launch client"+e);
        }
            String pname="";
            String barcode ="";
                   try
            {
                pool = new ThreadPool(8);
                   c=s1.accept();
             //start the C# client

             //create the reader for reading from socket
             r= new BufferedReader(new InputStreamReader(c.getInputStream()));
             //create the writer for writing to socket
             w = new PrintWriter(c.getOutputStream());
            
             //send the parent folder path for ther new project to C# client
             w.println(f.getAbsolutePath());
             w.flush();
             //Wait for the project folder to be created
             //readline would return with the Path to the new project
             path1=r.readLine();
             int indx = path1.lastIndexOf("\\");
             barcode=path1.substring(indx+1,path1.length());
             System.out.println("barcode is "+barcode);
             path=path1.substring(0, indx);
             indx = path.lastIndexOf("\\");
             pname=path.substring(indx+1,path.length());
             gui.recievepath(this, path,path1,p,3,0);
             //System.out.println("Project Path: "+path);
             //System.out.println("Last: "+last+"Path1 "+path1);
               //  int index=path1.lastIndexOf("\\");
             //path=path1.substring(0, index);
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }

                 f= new File(path1+"\\"+"IMAGES");
                 //m1.addElement(f);
                 String[] children=f.list();

                for (int i = 0;i<children.length;i++)
                {
                    if(children[i].endsWith("cr2"))
                    {
                        file = new File(path1+"\\"+"IMAGES"+"\\"+children[i]);
                    System.out.println(file.getAbsolutePath());


                    pool.assign(new TestWorkerThread(file,dpi));

                    }

                }

                 try

                {
                     boolean bres=false,mres=false;
                     File bfile = new File("d:\\backup\\"+pname+"\\"+barcode);
                     if(!bfile.exists())//create the barcode folder if it doesnt exist
                     {
                     bres=(new File("d:\\backup\\"+pname+"\\"+barcode).mkdir() );
                     }
                     File rfile = new File("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K");
                     if(!rfile.exists())
                     {
                    boolean rres=(new File("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K\\IMAGES").mkdir() );
                     }
                         File src = new File(path1+"\\IMAGES");
                         File dst = new File ("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K\\IMAGES");
                         copyDirectory(src,dst);
                         //src= new File(path1+"\\METADATA");
                         //dst=new File("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K\\METADATA");
                         //copyDirectory(src,dst);

                }
catch(Exception e)
{
System.out.println(e);
}
                 pool.complete();
                   gui.update_tiff(path1);
                
                   p.destroy();
                   return;
        }
        else if(option ==4)
        {
            try{
                       //start the C# client
    p=Runtime.getRuntime().exec("consoleApplication1 4 " + (port+count));
   // d.setPath(path);
   // System.out.println(d.getPath());
     d.setProcess(p);
     d.setServer(this);

        }
        catch(Exception e)
        {
            System.out.println("failed to launch client"+e);
        }
               //create the therad pool
               pool = new ThreadPool(8);
               //wait for client connection
              String pname="";
               try
              {
               c=s1.accept();
               System.out.println("client connection created");
             //create reader and wirter for the socket
             r= new BufferedReader(new InputStreamReader(c.getInputStream()));
             w = new PrintWriter(c.getOutputStream());
             w.println(f.getAbsolutePath());
             w.flush();
              }
              catch(Exception e)
              {

                  System.out.println(e);
              }
               System.out.println("waiting for book name to arrive");
                       try{
             while((path1=r.readLine()) !=null)//add the job to the queue
             {
                 System.out.println("in while loop waiting for dATA");
                 System.out.println("Last: "+last+"Path1 "+path1);

                 int index=f.getAbsolutePath().lastIndexOf("\\");
                 

                 String barcode=f.getAbsolutePath().substring(index+1,f.getAbsolutePath().length());
                //int indx = temp.lastIndexOf(("\\"));
                //String barcode = temp.substring(indx+1,temp.length());
             path=f.getAbsolutePath().substring(0, index);
             System.out.println(path);
index = path.lastIndexOf("\\");
             pname=path.substring(index+1,path.length());

                 gui.recievepath(this, path, f.getAbsolutePath(), p, 4, id);
                 File f1= new File(path1+"\\"+"IMAGES");
                 //m1.addElement(f);
                 String[] children=f1.list();

                for (int i = 0;i<children.length;i++)
                {
                    if(children[i].endsWith("cr2"))
                    {
                        file = new File(path1+"\\"+"IMAGES"+"\\"+children[i]);
                    System.out.println(file.getAbsolutePath());


                    pool.assign(new TestWorkerThread(file,dpi));

                    }

                }
                 pool.complete();
                   //m1.removeElement(f);
                   //m2.addElement(f);
                 try

                {
                     boolean bres=false,mres=false,rres=false;
                     File bfile = new File("d:\\backup\\"+pname+"\\"+barcode);
                     if(!bfile.exists())//create the barcode folder if it doesnt exist
                     {
                     bres=(new File("d:\\backup\\"+pname+"\\"+barcode).mkdir() );
                     }
                     File rfile = new File("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K");
                     if(!rfile.exists())
                     {
                    rres=(new File("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K").mkdir() );
                     }

                         File src = new File(path1+"\\IMAGES");
                         File dst = new File ("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJpeg2K"
                                 + "\\IMAGES");
                         System.out.println("Source path is "+src.getAbsolutePath());
                          System.out.println("Destination path is "+dst.getAbsolutePath());
                         copyDirectory(src,dst);
                         //src= new File(path1+"\\METADATA");
                         //dst=new File("d:\\backup\\"+pname+"\\"+barcode+"RAWJPEG2K\\METADATA");
                          //System.out.println("Source path is "+src.getAbsolutePath());
                          //System.out.println("Destination path is "+dst.getAbsolutePath());
                         //copyDirectory(src,dst);

                }
catch(Exception e)
{
System.out.println(e);
}
                   gui.update_tiff(f.getAbsolutePath());
                   p.destroy();
                   
                   return;
             }
        }
catch(Exception e)
{
System.out.println(e);
e.printStackTrace();
}
        }

        try{
            
             while((path1=r.readLine()) !=null)//add the job to the queue
             {
                 System.out.println("in while loop waiting for dATA");
                 System.out.println("Last: "+last+"Path1 "+path1);
                 int index1=path1.lastIndexOf("\\");
                 
             path=path1.substring(0, index1);
               
                   
                 gui.recievepath(this, path, path1, p, 2, id);
                 f= new File(path1+"\\"+"IMAGES");
                 //m1.addElement(f);
                 String[] children=f.list();

                for (int i = 0;i<children.length;i++)
                {
                    if(children[i].endsWith("cr2"))
                    {
                        file = new File(path1+"\\"+"IMAGES"+"\\"+children[i]);
                    System.out.println(file.getAbsolutePath());


                    pool.assign(new TestWorkerThread(file,dpi));

                    }

                }
                 pool.complete();
                   //m1.removeElement(f);
                   //m2.a1ddElement(f);
                 /*if(dpi)
                 {
                     children = f.list();
                     for (int i = 0;i<children.length;i++)
                {
                    if(children[i].endsWith("tiff"))
                    {
                        file = new File(path1+"\\"+"IMAGES"+"\\"+children[i]);
                    System.out.println(file.getAbsolutePath());


                    pool.assign(new TiffDpi(file));

                    }

                }
                 pool.complete();

                 }*/
                 gui.update_tiff(path1);
                 index1=path1.lastIndexOf("\\");
                 String barcode=path1.substring(index1+1,path1.length());
                 System.out.println("barcode is  "+ barcode);
                 int index = path.lastIndexOf("\\");
             String pname=path.substring(index+1,path.length());

                try

                {
                     boolean bres=false,mres=false;
                     File bfile = new File("d:\\backup\\"+pname+"\\"+barcode);
                     if(!bfile.exists())//create the barcode folder if it doesnt exist
                     {
                     bres=(new File("d:\\backup\\"+pname+"\\"+barcode).mkdir() );
                     }
                     File rfile = new File("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K");
                     if(!rfile.exists())
                     {
                    boolean rres=(new File("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K").mkdir() );
                     }

                         File src = new File(path1+"\\IMAGES");
                         File dst = new File ("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K\\IMAGES");
                         copyDirectory(src,dst);
                         //src= new File(path1+"\\METADATA");
                         //dst=new File("d:\\backup\\"+pname+"\\"+barcode+"\\RAWJPEG2K\\METADATA");
                         //copyDirectory(src,dst);

                }
catch(Exception e)
{
System.out.println(e);
}
               
             }

        }
catch(Exception e)
{
System.out.println(e);
}
                   
                 






           }
    
    void copyDirectory(File srcPath, File dstPath)
                               throws IOException{

  if (srcPath.isDirectory()){

      if (!dstPath.exists()){

        dstPath.mkdir();

     }


      System.out.println("dsetination path is "+dstPath.getAbsolutePath());
     String files[] = srcPath.list();

    for(int i = 0; i < files.length; i++){


        if(files[i].endsWith("tif") || files[i].endsWith("jpg"))
        {
            continue;
        }
           System.out.println("file in "+ srcPath.getAbsolutePath()+"is"+files[i]);
            
        copyDirectory(new File(srcPath, files[i]),
                     new File(dstPath, files[i]));


      }

    }

   else{

      if(!srcPath.exists()){

        System.out.println("File or directory does not exist.");

       System.exit(0);

      }

else

      {

       InputStream in = new FileInputStream(srcPath);
       OutputStream out = new FileOutputStream(dstPath);
                     // Transfer bytes from in to out
       
            byte[] buf = new byte[1024];

              int len;

           while ((len = in.read(buf)) > 0) {

          out.write(buf, 0, len);

        }

       in.close();

           out.close();

      }

   }



    }
}

    

class ThreadPool {
 /**
  * The threads in the pool.
  */
 protected Thread threads[] = null;
 /**
  * The backlog of assignments, which are waiting
  * for the thread pool.
  */
 Collection assignments = new ArrayList(3);
 /**
  * A Done object that is used to track when the
  * thread pool is done, that is has no more work
  * to perform.
  */
 protected Done done = new Done();

 /**
  * The constructor.
  *
  * @param size  How many threads in the thread pool.
  */
 public ThreadPool(int size)
 {
   
  threads = new WorkerThread[size];
  for (int i=0;i<threads.length;i++) {
   threads[i] = new WorkerThread(this);
   threads[i].start();

  }
 }

 /**
  * Add a task to the thread pool. Any class
  * which implements the Runnable interface
  * may be assienged. When this task runs, its
  * run method will be called.
  *
  * @param r   An object that implements the Runnable interface
  */
 public synchronized void assign(Runnable r)
 {

  done.workerBegin();
  assignments.add(r);
  notify();
 }

 /**
  * Get a new work assignment.
  *
  * @return A new assignment
  */
 public synchronized Runnable getAssignment()
 {
  try {
   while ( !assignments.iterator().hasNext() )
    wait();

   Runnable r = (Runnable)assignments.iterator().next();
   assignments.remove(r);
   return r;
  } catch (InterruptedException e) {
   done.workerEnd();
   return null;
  }
 }

 /**
  * Called to block the current thread until
  * the thread pool has no more work.
  */
 public void complete()
 {
  done.waitBegin();
  done.waitDone();
 }


 protected void finalize()
 {
  done.reset();
  for (int i=0;i<threads.length;i++) {
   threads[i].interrupt();
   done.workerBegin();
   threads[i].destroy();
  }
  done.waitDone();
 }
}

/**
 * The worker threads that make up the thread pool.
 *
 * @author Jeff Heaton
 * @version 1.0
 */
class WorkerThread extends Thread {
 /**
  * True if this thread is currently processing.
  */
 public boolean busy;
 /**
  * The thread pool that this object belongs to.
  */
 public ThreadPool owner;

 /**
  * The constructor.
  *
  * @param o the thread pool
  */
 WorkerThread(ThreadPool o)
 {
  owner = o;
 }

 /**
  * Scan for and execute tasks.
  */
 public void run()
 {
     
  Runnable target = null;

  do {
   target = owner.getAssignment();
   if (target!=null) {
    target.run();
    owner.done.workerEnd();
   }
  } while (target!=null);
 }
}
/**
 *
 * This is a thread pool for Java, it is
 * simple to use and gets the job done. This program and
 * all supporting files are distributed under the Limited
 * GNU Public License (LGPL, http://www.gnu.org).
 *
 * This is a very simple object that
 * allows the TheadPool to determine when
 * it is done. This object implements
 * a simple lock that the ThreadPool class
 * can wait on to determine completion.
 * Done is defined as the ThreadPool having
 * no more work to complete.
 *
 * Copyright 2001 by Jeff Heaton
 *
 * @author Jeff Heaton (http://www.jeffheaton.com)
 * @version 1.0
 */
 class Done {

 /**
  * The number of Worker object
  * threads that are currently working
  * on something.
  */
 private int _activeThreads = 0;

 /**
  * This boolean keeps track of if
  * the very first thread has started
  * or not. This prevents this object
  * from falsely reporting that the ThreadPool
  * is done, just because the first thread
  * has not yet started.
  */
 private boolean _started = false;
 /**
  * This method can be called to block
  * the current thread until the ThreadPool
  * is done.
  */

 synchronized public void waitDone()
 {
  try {
   while ( _activeThreads>0 ) {
    wait();
   }
  } catch ( InterruptedException e ) {
  }
 }
 /**
  * Called to wait for the first thread to
  * start. Once this method returns the
  * process has begun.
  */

 synchronized public void waitBegin()
 {
  try {
   while ( !_started ) {
    wait();
   }
  } catch ( InterruptedException e ) {
  }
 }


 /**
  * Called by a Worker object
  * to indicate that it has begun
  * working on a workload.
  */
 synchronized public void workerBegin()
 {

  _activeThreads++;
  _started = true;
  notify();
 }

 /**
  * Called by a Worker object to
  * indicate that it has completed a
  * workload.
  */
 synchronized public void workerEnd()
 {
  _activeThreads--;
  notify();
 }

 /**
  * Called to reset this object to
  * its initial state.
  */
 synchronized public void reset()
 {
  _activeThreads = 0;
 }

}

class TestWorkerThread implements Runnable
{


 /**
  *
  * @param done
  */
File f;
boolean dpi;
public TestWorkerThread(File f,boolean dpi)
{
    this.f=f;
    this.dpi=dpi;
}

 public void run()
 {

   try {
       String name = f.getName();
     int ind =name.lastIndexOf(".");
     name = name.substring(0,ind+1);
     name=name+"tif";

       String path = f.getParent();
       System.out.println("Tiff file path is "+path);
       System.out.println("Tiff file name is "+ name);
       System.out.println(" DPI is "+dpi);

      
if(dpi)
{
Process theProcess = Runtime.getRuntime().exec("C:\\Program Files (x86)\\IrfanView\\i_view32.exe " + f.getAbsolutePath()+ " /dpi=(400,400) /resize=(5568,3712) /resample /aspectratio /convert="+name);
System.out.println("C:\\Program Files (x86)\\IrfanView\\i_view32.exe " + f.getAbsolutePath()+ " /dpi=(400,400) /resize=(5200,3467) /resample /aspectratio /convert="+name);
InputStream in = theProcess.getInputStream();
BufferedReader bin = new BufferedReader(new InputStreamReader(in));
String msg=bin.readLine();
while(bin.readLine()!=null){
    System.out.println(msg);
    msg=bin.readLine();
    }


theProcess.waitFor();
       }
 else
{
    Process theProcess = Runtime.getRuntime().exec("C:\\Program Files (x86)\\IrfanView\\i_view32.exe " + f.getAbsolutePath()+ " /dpi=(300,300) /resize=(5568,3712) /resample /aspectratio /convert="+name);

InputStream in = theProcess.getInputStream();
BufferedReader bin = new BufferedReader(new InputStreamReader(in));
String msg=bin.readLine();
while(bin.readLine()!=null){
    System.out.println(msg);
    msg=bin.readLine();
    }
            theProcess.waitFor();

 }




   }
   catch (Exception e) {
       System.out.println("dcraw failure"+e);
                        }
  }

}
class TestWorkerThread1 implements Runnable
{


 /**
  *
  * @param done
  */
File f;
boolean dpi;
public TestWorkerThread1(File f,boolean dpi)
{
    this.f=f;
    this.dpi=dpi;
}

 public void run()
 {

   try {
       String name = f.getAbsolutePath();
       name=name.replace("\\IMAGES", "\\temp");
     name=name.replace("cr2","tif");
     //name = name.substring(0,ind+1);
     //name=name+"tif";

       String path = f.getParent();
       System.out.println("Tiff file path is "+name);
       //System.out.println("Tiff file name is "+ name);
       System.out.println(" DPI is "+dpi);


if(dpi)
{
Process theProcess = Runtime.getRuntime().exec("C:\\Program Files (x86)\\IrfanView\\i_view32.exe " + f.getAbsolutePath()+ " /dpi=(400,400) /resize=(5568,3712) /resample /aspectratio /convert="+name);
System.out.println("C:\\Program Files (x86)\\IrfanView\\i_view32.exe " + f.getAbsolutePath()+ " /dpi=(400,400) /resize=(5200,3467) /resample /aspectratio /convert="+name);
InputStream in = theProcess.getInputStream();
BufferedReader bin = new BufferedReader(new InputStreamReader(in));
String msg=bin.readLine();
while(bin.readLine()!=null){
    System.out.println(msg);
    msg=bin.readLine();
    }


theProcess.waitFor();
       }
 else
{
    Process theProcess = Runtime.getRuntime().exec("C:\\Program Files (x86)\\IrfanView\\i_view32.exe " + f.getAbsolutePath()+ " /dpi=(300,300) /resize=(5568,3712) /resample /aspectratio /convert="+name);

InputStream in = theProcess.getInputStream();
BufferedReader bin = new BufferedReader(new InputStreamReader(in));
String msg=bin.readLine();
while(bin.readLine()!=null){
    System.out.println(msg);
    msg=bin.readLine();
    }
            theProcess.waitFor();

 }




   }
   catch (Exception e) {
       System.out.println("dcraw failure"+e);
                        }
  }

}

class SelectionListener implements ListSelectionListener {
    JTable table;
    EntityManager em;
        DefaultTableModel m1,m2;
        

    // It is necessary to keep the table since it is not possible
    // to determine the table from the event's source
  public SelectionListener(JTable table,EntityManager em,DefaultTableModel m1,DefaultTableModel m2) {
        this.table = table;
        this.em=em;
        this.m1=m1;
        this.m2=m2;
    }
    public void valueChanged(ListSelectionEvent e) {
        // If cell selection is enabled, both row and column change events are fired
        int i=0;
        String path;
        //if ((e.getSource() == table.getSelectionModel() || e.getSource() == table.getColumnModel().getSelectionModel())
        		//&& !e.getValueIsAdjusting()
        		//&& table.getSelectedColumn() != table.getSelectedRow()
        		//&& table.getSelectedColumn() != 0
        		//&& table.getSelectedRow() != 0 )
        //{
            int first=table.getSelectedRow();
        int last=table.getSelectedColumn();
        

        System.out.println("row index: "+ first);
        System.out.println("column index: "+ last);


        if ( first!=-1 && last!=-1)
        		
        {
            i=(Integer)table.getModel().getValueAt(first, last);
            

        
        System.out.println("VAlue of i: "+i);
        Project proj=em.find(Project.class, i);
        path=proj.getProjectPath();
        int count=m1.getRowCount() ;
        //clear table1
        while(m2.getRowCount()>0)
            m2.removeRow(0);
        m1.fireTableRowsDeleted(0, count-1);
        /*em.getTransaction().begin();
        Query query = em.createQuery("select p.Project_id,b.Barcode,b.Tiff_end,b.JPC,b.JPEG,b.OCRed from Project p join IMLS.dbo.book b on p.project_id=b.Project_id");
        Iterator results = query.getResultList().iterator();*/


         Query q1=em.createNamedQuery("Book.findMatch");
                        q1.setParameter("projId",proj );
                        Iterator result1 = q1.getResultList().iterator();
        count =0;
        while(result1.hasNext())
        {
            Vector v= new Vector();
            Book b = (Book)result1.next();

            String Barcode=b.getBarcode();
            v.add(Barcode);
            
            Date tiffd=b.getTiffend();
            if(tiffd!=null)
                v.add(tiffd);
           if(b.getJpc()!=null)
           {
            boolean jpc=b.getJpc();
                  v.add(jpc);
           }
            if(b.getJpeg()!=null)
            {
            boolean jpeg=b.getJpeg();
                v.add(jpeg);
            }
            if(b.getOCRed()!=null )
            {
            boolean ocred=b.getOCRed();
            if(ocred!=false)
            v.add(ocred);
            }
            m2.addRow(v);
            count++;


        }
        m2.fireTableRowsInserted(0, count-1);
        //em.getTransaction().commit();
        }
    }
    //}
}
  
class DataStore
{
    private Process p;
    private String path;
    private Server s;
public DataStore(Server s)
{
 this.s=s;
}
public DataStore()
{
   
}
public void setProcess(Process p)
{
    this.p=p;
}
public void setPath(String path)
{
    this.path=path;
}
public Server getServer()
{
    return s;
}
public void setServer(Server s)
{
    this.s=s;
}
public String getPath()
{
    return path;
}
public Process getProcess()
{
    return p;
}
}

class TiffDpi implements Runnable

{
    File f;
    public TiffDpi(File f)
    {
        this.f = f;
    }
    public void run()
    {
        try
        {
        String path = f.getParent();
        String name = f.getName();
        int ind = name.lastIndexOf("f");
        String temp = name.substring(0,ind);
        System.out.println("Temp file name "+ temp);
        Process p = Runtime.getRuntime().exec("C:\\Program Files (x86)\\IrfanView\\i_view32.exe "+f.getAbsolutePath()+" /dpi=(400,400) /convert=c:\\"+temp);
        p.waitFor();
        
        f.delete();
        Process p1 = Runtime.getRuntime().exec("cmd /c move c:\\"+temp+" "+ path+"\\"+name);
        p1.waitFor();
        File f2 = new File("c:\\"+temp);
        f2.delete();


        }
        catch(Exception e)
        {
            System.out.println("Exception in TiffDpi "+e);
        }
    }
    public void setFile(File f)
    {
        this.f=f;
    }

}

